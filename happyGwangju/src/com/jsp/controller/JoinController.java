package com.jsp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jsp.dto.MemberInfoDto;
import com.jsp.model.MemberInfoDao;

public class JoinController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//join화면 보여줄시 session에 저장된 userid, loginresult 지움
		req.getSession().removeAttribute("userId");
		req.getSession().removeAttribute("loginResult");
		
		// 로그인 화면 보여주기 (포워딩: 이어서 작업)
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/joinForm.jsp");	//TODO
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		// 입력값들을 받아오자
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String pw = req.getParameter("pw");
		String email = req.getParameter("email");
		
		// dto에 받아온 값 넣어 주고
		MemberInfoDto mDto = new MemberInfoDto();
		mDto.setId(id);
		mDto.setName(name);
		mDto.setPw(pw);
		mDto.setEmail(email);

		// dto를 dao에 넣는다 (db에 들어가게 된다)
		MemberInfoDao mDao = new MemberInfoDao();
		mDao.insertPersonInfo(mDto);
		
		// 가입 누른 후 보여질 화면
		req.setAttribute("result", "가입을 환영합니다!");
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/loginForm.jsp");	//TODO
		rd.forward(req, resp);
		
	}

}