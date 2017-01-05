<%@page import="com.jsp.model.MenuPosterDao"%>
<%@page import="com.jsp.dto.MenuPosterDto"%>
<%@page import="com.jsp.model.MenuDao"%>
<%@page import="com.jsp.dto.MenuDto"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	MultipartRequest mRequest = new MultipartRequest(request, "/dev", 1024 * 1024 * 1, "utf-8",
			new DefaultFileRenamePolicy());
		

	// 입력값들을 받아오자
	String name = mRequest.getParameter("name"); // 행사명
	name = name.replace("<", "&lt;").replace(">", "&gt;"); // 내용 입력할 때 <>를 못 넣게

	int category = Integer.parseInt(mRequest.getParameter("category"));

	String starttime = mRequest.getParameter("starttime");
	starttime = starttime.replace("<", "&lt;").replace(">", "&gt;");

	String endtime = mRequest.getParameter("endtime");
	endtime = endtime.replace("<", "&lt;").replace(">", "&gt;");
	
	String m_time = mRequest.getParameter("time");
	m_time = m_time.replace("<", "&lt;").replace(">", "&gt;");

	String place = mRequest.getParameter("place");
	place = place.replace("<", "&lt;").replace(">", "&gt;");

	String host = mRequest.getParameter("host");
	host = host.replace("<", "&lt;").replace(">", "&gt;");

	String call = mRequest.getParameter("call");
	call = call.replace("<", "&lt;").replace(">", "&gt;");

	String money = mRequest.getParameter("money");

	String homepage = mRequest.getParameter("homepage");
	homepage = homepage.replace("<", "&lt;").replace(">", "&gt;");

	String content = mRequest.getParameter("content");
	content = content.replace("<", "&lt;").replace(">", "&gt;").replace("\r\n","<br>");
	
	String no = mRequest.getParameter("no");

	
	// dto에 받아온 값 넣어 주고
	MenuDto bDto = new MenuDto();
	bDto.setNo(Integer.parseInt(no));
	bDto.setName(name);
	bDto.setCategory(category);
	bDto.setStarttime(starttime);
	bDto.setEndtime(endtime);
	bDto.setM_time(m_time);	
	bDto.setPlace(place);
	bDto.setHost(host);
	bDto.setCall(call);
	bDto.setMoney(money);
	bDto.setHomepage(homepage);
	bDto.setContent(content);

	// dto를 dao에 넣는다 (db에 들어가게 된다)
	MenuDao bDao = new MenuDao();
	bDao.ModifyMenu(bDto);

	// 마지막으로 입력된 게시물 번호 조회 ()
	int maxSeqNo = bDao.getMaxSeqNo();

	// 파일을 받아온다
	File file1 = mRequest.getFile("file1");
	
	
	if (file1 != null) { // 파일이 첨부되었다면
		// 업로드 된 이후 변경된 파일명
		String file2Name = file1.getName();
		// 업로드 전 사용자가 선택한 파일명
		String file1Name = mRequest.getOriginalFileName("file1");
// 		out.print("파일명 : " + file1Name);
// 		out.print("변경 파일명 : " + file2Name);
// 		out.print("<br><a href=http://localhost:8080/menuList.do>목록으로</a>");

		// dto에 값을 넣어주고
		MenuPosterDto dto = new MenuPosterDto();
		dto.setNo(maxSeqNo); // 어느 게시물의 첨부파일인가? 마지막으로 입력된 게시물 번호 조회
		dto.setP_poster_o(file1Name);
		dto.setP_poster_s(file2Name);

		// dto에 넣은 값을 db에 넣어주기
		MenuPosterDao dao = new MenuPosterDao();
		int result = dao.insertImage(dto);
	}
	
	response.sendRedirect("menuDetail.do?no=" + no);
%>