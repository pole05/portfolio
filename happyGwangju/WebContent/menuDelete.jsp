<%@page import="java.util.List"%>
<%@page import="com.jsp.dto.MenuPosterDto"%>
<%@page import="com.jsp.model.MenuPosterDao"%>
<%@page import="com.jsp.model.MenuDao"%>
<%@page import="com.jsp.model.MenuCommentDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	int no = Integer.parseInt(request.getParameter("no"));
	
	MenuDao cDao = new MenuDao();
	cDao.deleteMenu(no);
	MenuPosterDao pDao = new MenuPosterDao();
	MenuPosterDto pDto = new MenuPosterDto();
	List<MenuPosterDto> list =  pDao.selectImageBySeqNo(no);
	for(int i = 0; i< list.size(); i++){
		pDto.setP_no(list.get(i).getP_no());
		pDto.setP_poster_o("no_cnt.gif");
		pDto.setP_poster_s("no_cnt.gif");
		pDao.updateImage(pDto);
	}
	
	
	// 댓글 쓰고 나서 그 게시물 보이게(리다이렉트),, 자바로 하는 방법
	response.sendRedirect("menuList.do");	
%>

	<!-- 	리다이렉트 스크립트로 하는 방법 (두 방법 중에 하나만 할 것) -->
<!-- 	<script> -->
// 		location.href="menuDetail.jsp?no=" + no;
<!-- 	</script> -->