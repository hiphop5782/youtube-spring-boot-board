<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>비밀번호 확인</h1>

<form action="${action}" method="post">
	<input type="hidden" name="no" value="${no}">
	<input type="password" name="password" placeholder="비밀번호 입력">
	<button type="submit">확인</button>
</form>

<c:if test="${param.error != null}">
	<h2>비밀번호 오류</h2>
</c:if>