<%@page import="com.jsp.model.HeartDao"%>
<%@page import="com.jsp.dto.HeartDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	//no 받아주기
		String no = request.getParameter("no");
		String id = (String) session.getAttribute("userId");
		
		HeartDao dao = new HeartDao();
		HeartDto dto = new HeartDto();
		
		dto.setNo(Integer.parseInt(no));		
		dto.setId(id);

		dao.deleteheart(dto);

%>
