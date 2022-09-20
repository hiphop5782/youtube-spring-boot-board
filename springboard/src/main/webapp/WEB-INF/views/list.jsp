<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>자유 게시판</h1>

<h2><a href="write">글쓰기</a></h2>

<form method="get">
	<input type="text" name="no" placeholder="번호" value="${boardDto.no}"><br>
	<input type="text" name="title" placeholder="제목" value="${boardDto.title}"><br>
	<input type="text" name="writer" placeholder="작성자" value="${boardDto.writer}"><br>
	<input type="text" name="content" placeholder="내용" value="${boardDto.content}"><br>
	<input type="date" name="begin" value="${boardDto.begin}">~
	<input type="date" name="end" value="${boardDto.end}"><br>
	<button type="submit">검색</button>
</form>

<table border="1" width="600">
	<thead>
		<tr>
			<th>번호</th>
			<th width="40%">제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>조회수</th>
		</tr>
	</thead>
	<tbody align="center">
		<c:forEach var="board" items="${list}">
		<tr>
			<td>${board.no}</td>
			<td align="left">
				<a href="detail?no=${board.no}">
					${board.title}
				</a>
			</td>
			<td>${board.writer}</td>
			<td>${board.writeTime}</td>
			<td>${board.readcount}</td>
		</tr>
		</c:forEach>
	</tbody>
</table>