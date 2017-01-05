<%@page import="com.jsp.model.MemberInfoDao"%>
<%@page import="com.jsp.dto.MemberInfoDto"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");

	// 입력값들을 받아오자
	String id = request.getParameter("id");
	String name = request.getParameter("name");
	String pw = request.getParameter("pw");
	String email = request.getParameter("email");
	
	// dto에 받아온 값 넣어 주고
	MemberInfoDto mDto = new MemberInfoDto();
	mDto.setId(id);
	mDto.setName(name);
	mDto.setPw(pw);
	mDto.setEmail(email);

	// dto를 dao에 넣는다 (db에 들어가게 된다)
	MemberInfoDao mDao = new MemberInfoDao();
	mDao.insertPersonInfo(mDto);
	
	// 가입 누른 후 보여질 화면
	response.sendRedirect("menuList.do");
%>