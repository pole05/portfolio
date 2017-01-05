<%@page import="org.json.simple.JSONObject"%>
<%@page import="com.jsp.model.MemberInfoDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
	MemberInfoDao dao = new MemberInfoDao();
	int re = dao.checkId(id);
	
	JSONObject obj = new JSONObject();
	obj.put("re", re);
	String jsonString = obj.toJSONString();
	out.print(jsonString);
%>