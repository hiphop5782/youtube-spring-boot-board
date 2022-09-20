package com.hacademy.board.dto;

import java.sql.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.hacademy.board.entity.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BoardDto {
	private Integer no;
	private String writer;
	private String title;
	private String content;
	private String begin, end;
	
	public Specification<Board> spec(){
		BoardDto dto = this;
		return new Specification<Board>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<Board> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate p = builder.conjunction();
				if(dto.no != null) 
					builder.and(builder.equal(root.get("no"), dto.no));
				if(dto.writer != null && !dto.writer.isEmpty())
					builder.and(builder.like(root.get("writer"), dto.writer));
				if(dto.title != null && !dto.title.isEmpty())
					builder.and(builder.like(root.get("title"), dto.title));
				if(dto.content != null && !dto.content.isEmpty())
					builder.and(builder.like(root.get("content"), dto.content));
				if(dto.begin != null && !dto.begin.isEmpty() && dto.end != null && !dto.end.isEmpty())
					builder.and(builder.between(root.get("writeTime"), Date.valueOf(dto.begin), Date.valueOf(dto.end)));
				return p;
			}
		};
	}
}
