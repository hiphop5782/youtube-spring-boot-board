package com.hacademy.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacademy.board.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{
	
	//목록 최신순(번호 내림차순)
	List<Board> findAllByOrderByNoDesc();
	
}
