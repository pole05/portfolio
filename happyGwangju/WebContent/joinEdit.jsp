<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="javafx.scene.control.Alert"%>
<%@page import="com.jsp.dto.MemberInfoDto"%>
<%@page import="com.jsp.model.MemberInfoDao"%>
<%@page import="java.io.Console"%>
<%@page import="com.jsp.dto.MenuCommentDto"%>
<%@page import="java.util.List"%>
<%@page import="com.jsp.model.MenuCommentDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String id = request.getParameter("id");
	String pw = request.getParameter("pw");		// 입력한 패스워드 값
	String pw2 = request.getParameter("pw2");
	String nickname = request.getParameter("nickname"); 
	String email = request.getParameter("email");
	
	MemberInfoDao dao = new MemberInfoDao();
	MemberInfoDto dto = dao.SelectPersonInfo(id);	// 비밀번호 비교를 위해서	
	String getPw = dto.getPw();	//TODO 원래 비밀번호 받아옴
	
	if (getPw.equals(pw)) {
		dto.setNickname(nickname);
		dto.setEmail(email);
		dto.setPw(pw2);
		dao.UpdateMemberInfo(dto);
		out.println("<script>alert('정보가 수정되었습니다.');history.back();</script>");
		
	} else {
		out.println("<script>alert('비밀번호가 일치하지 않습니다. 다시 확인해 주세요.');history.back();</script>");
		
	}
	
%>