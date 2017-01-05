<%@page import="com.jsp.model.ReviewCommentDao"%>
<%@page import="com.jsp.dto.ReviewCommentDto"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="org.json.simple.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");

	String comment = request.getParameter("comment");
	String seqNo = request.getParameter("seqNo");
	String id = (String) request.getSession().getAttribute("userId");
	
	ReviewCommentDto dto = new ReviewCommentDto();
	dto.setContent(comment);
	dto.setId(id);
	dto.setSeq(Integer.parseInt(seqNo));
	
	ReviewCommentDao dao = new ReviewCommentDao();
	int result = dao.insertComment(dto);

%>