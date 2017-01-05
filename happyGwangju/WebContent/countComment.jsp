<%@page import="com.jsp.dto.MenuDto"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@page import="java.io.Console"%>
<%@page import="com.jsp.dto.MenuCommentDto"%>
<%@page import="java.util.List"%>
<%@page import="com.jsp.model.MenuCommentDao"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String no = request.getParameter("no");

	MenuCommentDao dao = new MenuCommentDao();
	int size = dao.countComment(Integer.parseInt(no));
// 	List<MenuCommentDto> list = dao.selectComment(Integer.parseInt(no));

	
// 	ObjectMapper om = new ObjectMapper();
// 	String json = om.writeValueAsString(list);
// 	out.print(json);
	
	JSONObject obj = new JSONObject();
	obj.put("size", size);
	out.print(obj.toJSONString());
%>