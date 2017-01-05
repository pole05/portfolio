<%@page import="com.jsp.model.MenuCommentDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	int no = Integer.parseInt(request.getParameter("no"));
	int c_no = Integer.parseInt(request.getParameter("c_no"));
	
	MenuCommentDao cDao = new MenuCommentDao();
	cDao.deleteComment(no, c_no);
	
	// 댓글 쓰고 나서 그 게시물 보이게(리다이렉트),, 자바로 하는 방법
	response.sendRedirect("menuDetail.do?no=" + no);	
%>

	<!-- 	리다이렉트 스크립트로 하는 방법 (두 방법 중에 하나만 할 것) -->
<!-- 	<script> -->
// 		location.href="menuDetail.jsp?no=" + no;
<!-- 	</script> -->