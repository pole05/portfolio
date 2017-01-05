<%@page import="com.jsp.model.ReviewCommentDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	int seq = Integer.parseInt(request.getParameter("seq"));
	int c_no = Integer.parseInt(request.getParameter("c_no"));
	
	ReviewCommentDao cDao = new ReviewCommentDao();
	cDao.deleteComment(seq, c_no);
	
	// 댓글 쓰고 나서 그 게시물 보이게 하는 방법
	response.sendRedirect("reviewDetail.do?seq=" + seq);	
%>