package com.jsp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jsp.model.MemberInfoDao;

public class LoginController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//login화면 보여줄시 session에 저장된 userid, loginresult 지움
		req.getSession().removeAttribute("userId");
		req.getSession().removeAttribute("loginResult");
		
		// 로그인 화면 보여주기 (포워딩: 이어서 작업)
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/loginForm.jsp");	//TODO
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 로그인 정보를 이용하여 로그인 처리
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		
		// 아이디, 비밀번호 확인 로직
		MemberInfoDao pDao = new MemberInfoDao();
		String dbPw = pDao.selectMemberInfoById(id);
		
		System.out.println("DB password: " + dbPw);
		System.out.println("입력한 패스워드: " + pw);
		
		// 가져온 패스워드dbPW가 사용자가 입력한 패스워드pw와 일치하는가?
		if(!dbPw.equals("")) {		// ID가 db에 있을 때 = dbPw값이 존재할 때
			if(dbPw.equals(pw)) {	 // 로그인 성공
				HttpSession session = req.getSession();
				session.setAttribute("userId", id);
				session.setAttribute("loginResult", id+"님 환영합니다");
				
				resp.sendRedirect("/Main.do");
//				
			} else { // PW 틀림
				req.setAttribute("result", "비밀번호를 확인해주세요");
				RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/loginForm2.jsp");	//TODO
				rd.forward(req, resp);
			}
		} else {  	// ID 없음
			req.setAttribute("result", "존재하지 않는 ID입니다");
			RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/loginForm2.jsp");	//TODO
			rd.forward(req, resp);
		}
	}

}