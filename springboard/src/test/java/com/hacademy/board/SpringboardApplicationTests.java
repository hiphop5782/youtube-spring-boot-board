package com.hacademy.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hacademy.board.dto.BoardDto;
import com.hacademy.board.entity.Board;
import com.hacademy.board.repository.BoardRepository;

@SpringBootTest
class SpringboardApplicationTests {

	@Autowired
	private BoardRepository repo;
	
	@Test
	void contextLoads() {
		for(Board board : repo.findAll(BoardDto.builder().writer("hello").build().spec())) {
			System.out.println(board);
		};
	}

}
