package com.hacademy.board.vo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.hacademy.board.entity.Board;
import com.hacademy.board.util.EmptyChecker;

import lombok.Data;

//검색 처리를 위한 VO
@Data
public class BoardVO {
	private Long no;
	private String writer, title, content, begin, end;
	
//	검색 여부 판정
	public boolean isSearch() {
//		return (no != null && no > 0L) || (writer != null && !writer.isEmpty()) ||
//					(title != null && !title.isEmpty()) || (content != null && !content.isEmpty()) ||
//						(begin != null && !begin.isEmpty()) || (end != null && end.isEmpty());
		return !EmptyChecker.isEmpty(no, writer, title, content, begin, end);
	}
	
//	시간 객체 반환
	public Timestamp beginTime() {
		return Timestamp.valueOf(begin+" "+"00:00:00.0");
	}
	public Timestamp endTime() {
		return Timestamp.valueOf(end+" "+"23:59:59.9");
	}
	
//	JPA Specification을 위한 spec 생성
	public Specification<Board> specification(){
		return (root, query, builder) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			//번호 조건 추가
			if(!EmptyChecker.isEmpty(this.no)) {
				predicates.add(builder.equal(root.get("no"), this.no));
			}
			
			//작성자 조건 추가
			if(!EmptyChecker.isEmpty(this.writer)) {
				predicates.add(builder.equal(root.get("writer"), this.writer));
			}
			
			//제목 조건 추가
			if(!EmptyChecker.isEmpty(this.title)) {
				//predicates.add(builder.equal(root.get("title"), this.title));//일치검색
				predicates.add(builder.like(root.get("title"), "%"+this.title+"%"));//유사검색
			}
			
			//내용 조건 추가
			if(!EmptyChecker.isEmpty(this.content)) {
				predicates.add(builder.like(root.get("content"), "%"+this.content+"%"));
			}
			
			//기간 조건 추가
			if(!EmptyChecker.isEmpty(this.begin) && !EmptyChecker.isEmpty(this.end)) {
				predicates.add(builder.between(root.get("writeTime"), this.beginTime(), this.endTime()));
			}
			
			//and로 합성한 뒤 반환
			return builder.and(predicates.stream().toArray(Predicate[]::new));
		};
	}
}






