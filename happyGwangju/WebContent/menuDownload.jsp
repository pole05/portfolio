<%@page import="java.util.List"%>
<%@page import="com.jsp.dto.MenuPosterDto"%>
<%@page import="com.jsp.model.MenuPosterDao"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String filePath = "/dev";
	String no = request.getParameter("no");
	String p_no = request.getParameter("p_no");
		MenuPosterDao fDao = new MenuPosterDao();
		MenuPosterDto fDto = fDao.selectImage(Integer.parseInt(no), Integer.parseInt(p_no));
		if(fDto== null || fDto.getP_poster_o() == null){
			fDto.setP_poster_s("no_img.gif");
			fDto.setP_poster_o("no_img.gif");
		}
		String fileName = fDto.getP_poster_s();
		out.print(fileName);
		InputStream in = new FileInputStream(filePath + "/" + fileName);

		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attatchment; filename=" + fDto.getP_poster_o());

		out.clear();
		out = pageContext.pushBody();

		OutputStream os = response.getOutputStream();
		
		while (true) {
			int data = in.read();
			if (data == -1)
				break;
			os.write(data);
		}
		os.flush();
		os.close();
		in.close();
		
%>