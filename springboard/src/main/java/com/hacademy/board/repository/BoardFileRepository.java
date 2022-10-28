package com.hacademy.board.repository;

import java.util.Date;
import java.util.List;

//import 할 때 jdbc 하지 않도록 조심하세요(ㅜ.ㅜ)
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hacademy.board.entity.Board;
import com.hacademy.board.entity.BoardFile;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long>{

	List<BoardFile> findAllByBoard(Board board);
	
	//조회를 먼저 하니 필요가 없어졌습니다.. 아까우니까 놔두도록 합시다
	//@Modifying
	//@Transactional
	//@Query("delete BoardFile bf where bf.board is null and bf.uploadTime < :time")
	//void autoClear(@Param("time") Date time);

	//bf.board is null - 게시글 정보가 없는 파일(임시파일)
	//bf.uploadTime < :time - 전달된 시간보다 이전인 파일(오래된 파일)
	@Query("select bf from BoardFile bf where bf.board is null and bf.uploadTime < :time")
	List<BoardFile> getOldData(@Param("time") Date time);

}
