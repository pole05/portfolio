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
	
	String no_o = mRequest.getParameter("no");
	int no = Integer.parseInt(no_o);

	
	// dto에 받아온 값 넣어 주고
	MenuDto bDto = new MenuDto();
	bDto.setNo(no);
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
		File file2 = mRequest.getFile("file2");
		File file3 = mRequest.getFile("file3");
		File file4 = mRequest.getFile("file4");
		File file5 = mRequest.getFile("file5");
		
		
		if (file1 != null) { // 파일이 첨부되었다면
			String file1_Name = file1.getName(); // 변경된 파일명
			String file1Name = mRequest.getOriginalFileName("file1"); // 업로드 전 파일명

			MenuPosterDto dto = new MenuPosterDto();
			dto.setNo(maxSeqNo); // 어느 게시물의 첨부파일인가? 마지막으로 입력된 게시물 번호 조회
			dto.setP_poster_o(file1Name);
			dto.setP_poster_s(file1_Name);

			MenuPosterDao dao = new MenuPosterDao();
			int result = dao.insertImage(dto);
		}
		
		if (file2 != null) {
			String file2_Name = file2.getName();
			String file2Name = mRequest.getOriginalFileName("file2");

			MenuPosterDto dto = new MenuPosterDto();
			dto.setNo(maxSeqNo);
			dto.setP_poster_o(file2Name);
			dto.setP_poster_s(file2_Name);

			MenuPosterDao dao = new MenuPosterDao();
			int result = dao.insertImage(dto);
		}
		
		if (file3 != null) {
			String file3_Name = file3.getName();
			String file3Name = mRequest.getOriginalFileName("file3");

			MenuPosterDto dto = new MenuPosterDto();
			dto.setNo(maxSeqNo);
			dto.setP_poster_o(file3Name);
			dto.setP_poster_s(file3_Name);

			MenuPosterDao dao = new MenuPosterDao();
			int result = dao.insertImage(dto);
		}
		
		if (file4 != null) {
			String file4_Name = file4.getName();
			String file4Name = mRequest.getOriginalFileName("file4");

			MenuPosterDto dto = new MenuPosterDto();
			dto.setNo(maxSeqNo);
			dto.setP_poster_o(file4Name);
			dto.setP_poster_s(file4_Name);

			MenuPosterDao dao = new MenuPosterDao();
			int result = dao.insertImage(dto);
		}
		
		if (file5 != null) {
			String file5_Name = file5.getName();
			String file5Name = mRequest.getOriginalFileName("file5");

			MenuPosterDto dto = new MenuPosterDto();
			dto.setNo(maxSeqNo);
			dto.setP_poster_o(file5Name);
			dto.setP_poster_s(file5_Name);

			MenuPosterDao dao = new MenuPosterDao();
			int result = dao.insertImage(dto);
		}	
	
	response.sendRedirect("menuDetail.do?no=" + no);
%>