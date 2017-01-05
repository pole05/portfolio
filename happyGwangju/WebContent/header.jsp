<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
${sessionScope.loginId}님 반갑습니다.
<%
	String menu1 = "";
	String menu2 = "";
	String menu3 = "";
	if(request.getParameter("menu").equals("A")) {
		menu1 = "select";
	} else if(request.getParameter("menu").equals("B")) {
		menu2 = "select";
	} else {
		menu3 = "select";
	}
%>
<style>
	li { float : left; list-style : none; }
	.select { background-color : red; }
</style>
<div>
	<ul>
		<li class="<%=menu1%>"><a href="list.do?menu=A">A</a></li>
		<li class="<%=menu2%>"><a href="list.do?menu=B">B</a></li>
		<li class="<%=menu3%>"><a href="list.do?menu=C">C</a></li>
	</ul>
</div>




