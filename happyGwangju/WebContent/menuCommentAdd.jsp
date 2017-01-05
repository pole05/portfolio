<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.jsp.model.MenuCommentDao"%>
<%@page import="com.jsp.dto.MenuCommentDto"%>
<%@page import="org.json.simple.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");

	String comment = request.getParameter("comment");
	String seqNo = request.getParameter("seqNo");
	int category = Integer.parseInt(request.getParameter("category"));
	int size = Integer.parseInt(request.getParameter("csize"));
	
	String id = (String) session.getAttribute("userId");
	if(id == null) {
		id = "";
	}
	
	String inDate = 
			new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
	String inTime = 
			new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date());
	
	MenuCommentDto dto = new MenuCommentDto();
	dto.setContent(comment);
	dto.setNo(Integer.parseInt(seqNo));
	dto.setC_no(1);
	dto.setId(id);
	dto.setCategory(category);
	dto.setTime(inDate + "&nbsp;&nbsp;&nbsp;" + inTime);
	dto.setC_count(size + 1);	// dto 값에서 +1

	MenuCommentDao dao = new MenuCommentDao();
	int result = dao.insertComment(dto);
	
// 	int size_1 = dto.getC_count();

// 	JSONObject obj = new JSONObject();
// 	obj.put("size_1", size_1);
// 	String jsonString = obj.toJSONString();
// 	out.print(jsonString);
	
	// 현재 시간 구하기
	// 	Calendar calendar = Calendar.getInstance();
	//     java.util.Date date = calendar.getTime();
	//     String time = (new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(date));
%>