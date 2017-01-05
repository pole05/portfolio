<%@page import="com.jsp.dto.MemberInfoDto"%>
<%@page import="com.jsp.model.MemberInfoDao"%>
<%@page import="com.jsp.model.MenuCommentDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("UTF-8");
	String id = request.getParameter("id");
	
	MemberInfoDao dao = new MemberInfoDao();
	dao.deleteMember(id);
	
	out.println("<script>alert('탈퇴되었습니다. ^^');</script>");
	response.sendRedirect("menuList.do");		// 메인화면으로 가야됨
%>