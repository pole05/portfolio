<%@page import="com.jsp.dto.ReviewCommentDto"%>
<%@page import="java.util.List"%>
<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	List<ReviewCommentDto> list = (List<ReviewCommentDto>) request.getAttribute("list");

	ObjectMapper om = new ObjectMapper();
	String json = om.writeValueAsString(list);
	out.print(json);
%>