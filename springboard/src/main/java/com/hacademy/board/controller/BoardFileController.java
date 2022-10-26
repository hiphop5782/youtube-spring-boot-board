package com.hacademy.board.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hacademy.board.entity.BoardFile;
import com.hacademy.board.properties.BoardFileProperties;
import com.hacademy.board.repository.BoardFileRepository;

//비동기 파일 업로드, 다운로드, 삭제를 처리할 컨트롤러
@RestController
@RequestMapping("/rest")
public class BoardFileController {
	
	@Autowired
	private BoardFileRepository boardFileRepository;
	
	@Autowired
	private BoardFileProperties boardFileProperties;

	//임시 파일 업로드
	//- 서머노트 에디터에서 D&D 혹은 메뉴로 추가한 이미지를 저장하는 역할
	//- 임시 파일로 저장되며 유효기간은 1일로 설정(24시간)
	//- 임시파일 등록 시 DB에 게시글 번호를 제외한 정보를 저장
	//- 게시글이 등록될 때 확정 저장(게시글 번호 설정)
	//- 등록된 후 프론트엔드에 이미지 링크 주소와 이미지 번호를 반환하여 추가 처리가 가능하도록 구현
	//- urls : 이미지 링크 주소 , numbers : 이미지 번호
	@PostMapping("/image")
	public Map<String, List<? extends Object>> upload(@RequestParam List<MultipartFile> files) throws IllegalStateException, IOException {
		File dir = boardFileProperties.getImagePath();
		
		List<Long> numbers = new ArrayList<>();
		List<String> urls = new ArrayList<>();
		for(MultipartFile file : files) {
			if(file.isEmpty()) continue;//파일이 빈 경우 통과
			
			//database 저장
			BoardFile result = boardFileRepository.save(BoardFile.builder()
												.name(file.getOriginalFilename())//이름
												.type(file.getContentType())//유형
												.size(file.getSize())//크기
											.build());
			
			//실물 저장 - 파일명은 충돌방지를 위해 DB의 시퀀스 값으로 설정
			File target = new File(dir, String.valueOf(result.getSeq()));
			file.transferTo(target);
			
			//url저장 - ServletUriComponentBuilder를 사용하여 현재 서버의 주소와 일치하도록 주소 빌드
			urls.add(ServletUriComponentsBuilder.fromCurrentContextPath()
											.path("/rest/image/").path(String.valueOf(result.getSeq()))
										.build().toUriString());
			
			//번호 저장
			numbers.add(result.getSeq());
		}
		
		//urls와 numbers를 첨부한 Map을 반환
		return Map.of("urls", urls, "numbers", numbers);
	}
	
	@GetMapping("/image/{seq:[0-9]+}")//다운로드
	public ResponseEntity<ByteArrayResource> download(@PathVariable long seq) throws IOException{
		//파일정보 탐색
		BoardFile boardFile = boardFileRepository.findById(seq).orElseThrow();
		//실물파일 탐색
		File target = new File(boardFileProperties.getImagePath(), String.valueOf(boardFile.getSeq()));
		//파일 불러오기(apache commons io 사용)
		byte[] data = FileUtils.readFileToByteArray(target);
		//Wrapping
		ByteArrayResource resource = new ByteArrayResource(data);
		//반환
		return ResponseEntity.ok()
											//header
											.contentLength(boardFile.getSize())//파일크기
											.header(HttpHeaders.CONTENT_TYPE, boardFile.getType())//파일유형
											.header(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8.name())//인코딩
											.header(HttpHeaders.CONTENT_DISPOSITION, 
														ContentDisposition.attachment()
															.filename(boardFile.getName(), StandardCharsets.UTF_8)//파일명(원래이름+UTF-8)
														.build().toString()
													)
											//body
											.body(resource);
	}
	
	@PostMapping("/image/delete")//삭제
	public List<Long> delete(@RequestParam List<Long> numbers){
		//반복하여 삭제
		File dir = boardFileProperties.getImagePath();
		if(numbers != null && !numbers.isEmpty()) {
			for(Long seq : numbers) {
				BoardFile boardFile = boardFileRepository.findById(seq).orElseThrow();
				//실물 삭제
				File target = new File(dir, String.valueOf(seq));
				target.delete();
				//DB 삭제
				boardFileRepository.delete(boardFile);
			}
		}
		return numbers;
	}
	
}






