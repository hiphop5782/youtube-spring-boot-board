package com.hacademy.board.service;

import java.util.List;

import com.hacademy.board.entity.Board;

public interface BoardService {
	Board write(Board board, List<Long> images);
	void delete(Long no);
	void autoClearTempFile();
	void edit(Board board, List<Long> images);
}
