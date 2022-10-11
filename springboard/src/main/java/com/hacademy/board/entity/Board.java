package com.hacademy.board.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "BOARD")
public class Board {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long no;
	
	@Column(length = 60)
	private String writer;
	
	@Column(length = 300)
	private String title;
	
	@Column @Lob
	private String content;
	
	@Column(length = 20)
	private String password;
	
	@Column
	private int readcount;
	
	@CreationTimestamp
	private Date writeTime;
	
	@UpdateTimestamp
	private Date editTime;
	
	//계층형 게시판을 위한 상태값
	@Column
	private Long grp;
	
	@Column
	private long seq, dep;
	
}





