package com.jsp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jsp.dto.MemberInfoDto;
import com.jsp.model.MemberInfoDao;

public class JoinEditController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 세션값 받아오기
		HttpSession session = req.getSession();		
		String id = (String) session.getAttribute("userId");
//		String id = "a";
		
		// 회원 정보 받아오기
		MemberInfoDao dao = new MemberInfoDao();
		MemberInfoDto list = dao.SelectPersonInfo(id);
		req.setAttribute("list", list);

		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/joinForm_Edit.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");		
		HttpSession session = req.getSession();		
		String id = (String) session.getAttribute("userId");
		String pw = req.getParameter("pw"); // 현재 패스워드 값
		String pw2 = req.getParameter("pw2");	// 바꿀 패스워드 값
		String nickname = req.getParameter("nickname");
		String email = req.getParameter("email");

		MemberInfoDao dao = new MemberInfoDao();
		MemberInfoDto dto = dao.SelectPersonInfo(id); // 비밀번호 비교를 위해서
		String getPw = dto.getPw(); // 원래 비밀번호 받아옴

		int result = 0;

		if (getPw.equals(pw)) {			// 비밀번호가 일치함
			// 비밀번호를 새로 변경했을 때, 변경하지 않았을 때
			result = 1;
			if (pw2 == null || pw2.equals("")) {
				dto.setNickname(nickname);
				dto.setEmail(email);
				dao.UpdateMemberInfo(dto);	// 비밀번호를 제외한 정보 업뎃
			} else {
				dto.setNickname(nickname);
				dto.setEmail(email);
				dao.UpdateMemberInfo(dto);
				dao.UpdatePw(pw2, id);		// (비밀번호 외의 정보 + 비밀번호) 업뎃
			}						
		} else { 							// 비밀번호가 일치하지 않음
			result = 0;
		}
			// 수정된 회원 정보 받아오기
			MemberInfoDto list = dao.SelectPersonInfo(id);
			req.setAttribute("list", list);		
			req.setAttribute("result", result);				

			resp.setContentType("application/json; charset=utf-8;"); 
			PrintWriter out = resp.getWriter();
			out.print("{\"result\" : \"" + result + "\"}");
			
			
//		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/joinForm_Edit.jsp");
//		rd.forward(req, resp);
	}
}