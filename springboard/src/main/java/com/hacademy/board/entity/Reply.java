package com.hacademy.board.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//댓글 Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name="REPLY")
public class Reply {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long no;
	
	@Column
	private String writer;
	
	@Column @Lob
	private String content;
	
	@CreationTimestamp
	private Timestamp writeTime;
	
	//게시글과 n:1관계 설정 및 삭제 시 자동 소멸 처리
	//외래키 컬렴명을 board_no로 설정
	@ManyToOne(targetEntity = Board.class, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "board_no")
	private Board board;
	
}
