package com.hacademy.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacademy.board.entity.Board;
import com.hacademy.board.entity.Reply;
import com.hacademy.board.repository.BoardRepository;
import com.hacademy.board.repository.ReplyRepository;

//비동기 댓글 처리 컨트롤러
//- 별도의 rich text editor는 붙이지 않을 예정(시간관계상;;)
@RestController
@RequestMapping("/reply")
public class ReplyController {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	//댓글 등록 - 게시글 번호(no), 댓글 작성자(writer), 댓글 내용(content) 필요
	@PostMapping("/insert")
	public Reply insert(@RequestBody Reply reply) {
		Board board = boardRepository.findById(reply.getNo()).orElseThrow();
		
		Reply result = replyRepository.save(Reply.builder()
													.writer(reply.getWriter())
													.content(reply.getContent())
													.board(board)
											.build());
		//댓글 개수 갱신
		board.setReplyCount(replyRepository.countAllByBoard(board));
		boardRepository.save(board);
		return result;
	}
	
	@GetMapping("/list/{boardNo}")
	public List<Reply> list(@PathVariable long boardNo){
		Board board = boardRepository.findById(boardNo).orElseThrow();
		return replyRepository.findAllByBoardOrderByNoDesc(board);//최신순
//		return replyRepository.findAllByBoardOrderByNoAsc(board);//작성순
	}
	
	@GetMapping("/count/{boardNo}")
	public long count(@PathVariable long boardNo) {
		Board board = boardRepository.findById(boardNo).orElseThrow();
		return replyRepository.countAllByBoard(board);
	}
	
}
