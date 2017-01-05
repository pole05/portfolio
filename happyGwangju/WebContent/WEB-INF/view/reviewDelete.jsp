<%@page import="com.jsp.dto.ReviewDto"%>
<%@page import="com.jsp.model.ReviewDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	int seq = Integer.parseInt(request.getParameter("seq"));
	
	ReviewDao dao = new ReviewDao();

	if(dao.checkReply(seq) == 1) {
		ReviewDto dto = new ReviewDto();
		dto.setTitle("글이 삭제되었습니다");
		dto.setContent("본문이 삭제되었습니다.");
		dao.editReview(dto);					// 답변글이 있다면
	} else {
		dao.deleteReview(seq); // 답변글이 없다면	
	}

	// 댓글 쓰고 나서 그 게시물 보이게 하는 방법
	response.sendRedirect("reviewList.do");	
%>