package com.hacademy.board.controller;

import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.hacademy.board.dto.BoardDto;
import com.hacademy.board.entity.Board;
import com.hacademy.board.repository.BoardRepository;

@Controller
public class BoardController {

	@Autowired
	private BoardRepository boardRepository;
	
	@GetMapping("/")
	public String list(Model model, @ModelAttribute BoardDto boardDto) {
//		model.addAttribute("list", boardRepository.findAll());
//		model.addAttribute("list", boardRepository.findAllByOrderByNoDesc());
		model.addAttribute("list", boardRepository.findAll(boardDto.spec()));
		return "list";
	}
	
	@GetMapping("/write")
	public String write() {
		return "write";
	}
	
	@PostMapping("/write")
	public String write(@ModelAttribute Board board) {
		Board result = boardRepository.save(board);
//		return "redirect:/";
		return "redirect:detail?no="+result.getNo();
	}
	
	@GetMapping("/detail")
	public String detail(@RequestParam long no, Model model) {
		Board board = boardRepository.findById(no).orElseThrow();
		
		//조회수 증가
		board.setReadcount(board.getReadcount()+1);
		Board result = boardRepository.save(board);
		
		model.addAttribute("board", result);
		return "detail";
	}
	
	@GetMapping("/password/{mode:edit|delete}/{no}")
	public String password(@PathVariable String mode, @PathVariable long no) {
		return "password";
	}
	
	@PostMapping("/password/{mode:edit|delete}/{no}")
	public String password(@PathVariable String mode, @PathVariable long no, 
			@RequestParam String password, RedirectAttributes attr) {
		Board board = boardRepository.findById(no).orElseThrow();
		boolean isValid = board.getPassword().equals(password);
		if(isValid) {
			attr.addFlashAttribute("no", no);
			return "redirect:/"+mode;
		}
		else {
			return "redirect:/password/"+mode+"/"+no+"?error";
		}
	}
	
	@GetMapping("/edit")
	public String edit(HttpServletRequest request, Model model) {
		Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
		if(map == null) throw new RuntimeException();
		Long no = (Long)map.get("no");
		Board board = boardRepository.findById(no).orElseThrow();
		model.addAttribute("board", board);
		return "edit";
	}
	
	@PostMapping("/edit")
	public String edit(@ModelAttribute Board board, RedirectAttributes attr) {
		Board origin = boardRepository.findById(board.getNo()).orElseThrow();
		origin.setTitle(board.getTitle());
		origin.setWriter(board.getWriter());
		origin.setContent(board.getContent());
		origin.setPassword(board.getPassword());
		Board result = boardRepository.save(origin);
		attr.addAttribute("no", result.getNo());
		return "redirect:detail";
	}
	
	@GetMapping("/delete")
	public String delete(HttpServletRequest request, RedirectAttributes attr) {
		Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
		if(map == null) throw new RuntimeException();
		Long no = (Long)map.get("no");
		
		Board board = boardRepository.findById(no).orElseThrow();
		boardRepository.deleteById(no);
		return "redirect:/";
	}
	
}









