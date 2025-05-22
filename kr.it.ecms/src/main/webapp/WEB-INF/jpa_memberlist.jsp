<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="cr" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원리스트 및 페이징</title>
</head>
<body>
<table border="1">
<thead>
<tr>
<th>번호</th>
<th>아이디</th>
<th>이름</th>
<th>성별</th>
</tr>
</thead>
<tbody>
<cr:forEach var="data" items="${result}">
<tr>
<td></td>
<td>${data.uid}</td>
<td>${data.uname}</td>
<td>${data.gernder}</td>
</tr>
</cr:forEach>
</tbody>
</table>
<table border="1">
<tr>
<cr:forEach var="pno" begin="1" end="${pgno}">
<td onclick="page_go(${pno})">${pno}</td>
</cr:forEach>
</tr>
</table>
</body>
<script>
function page_go(p){
	console.log(p);
}

</script>
</html>