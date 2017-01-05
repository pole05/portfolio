<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.List"%>
<%@page import="com.jsp.model.HeartDao"%>
<%@page import="com.jsp.dto.HeartDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	//no 받아주기
		String no_o = request.getParameter("no");
		int no = Integer.parseInt(no_o);
		String id = "a"; 	// TODO String id = (String)
							// req.getSession().getAttribute("userId");
		
		HeartDao dao = new HeartDao();
		HeartDto dto = new HeartDto();
		
		dto.setNo(no);		
		dto.setId(id);
		
		int find = dao.findHeart(id, no);

		JSONObject obj = new JSONObject();
				
		if(find == no) {	// 있음
			obj.put("no", "1");
		} else {			// 없음
			obj.put("no", "0");
		}
			out.print(obj.toJSONString());	

%>
