package com.jsp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jsp.common.Mailer;
import com.jsp.common.SMTPAuthenticator;
import com.jsp.common.mailform;
import com.jsp.dto.MemberInfoDto;
import com.jsp.model.MemberInfoDao;

public class searchIDController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/searchID.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String id = (String) req.getParameter("userId");
		String email = (String) req.getParameter("mail");

		System.out.println(id);// TODO
		System.out.println(email);// TODO

		MemberInfoDao MInfoDao = new MemberInfoDao();
		MemberInfoDto dto = MInfoDao.SelectPersonInfo(id);
		String dbEmail = dto.getEmail();
		
		System.out.println(dbEmail);
		
		if (dbEmail == null) {

			System.out.println("NOID");// TODO
			req.setAttribute("EPWresult", "존재하지 않는 계정입니다");
			RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/searchID.jsp");
			rd.forward(req, resp);
		} else {// 공백이거나 맞는 email이거나 email이 틀렸거나
			if (email.equals(dbEmail)) {

				int pw2 = (int) (Math.random() * 1000000 + 1);

				if (pw2 < 100000) {
					int ran1 = (int) (Math.random() * 9 + 1);
					pw2 = ran1 * 100000;
				}

				System.out.println(pw2);// TODO

				int resultPW = MInfoDao.UpdatePw(pw2 + "", id);

				StringBuffer emailform = new mailform().mailForm(pw2);

				String subject = "[HappyGwangju] 귀하의 임시 비밀번호입니다.";
				String content = emailform.toString();
				SMTPAuthenticator smtp = new SMTPAuthenticator();
				Mailer mailer = new Mailer();
				mailer.sendMail(email, subject, content, smtp);

				String resultPW2 = "";
				if (resultPW == 0) {
					resultPW2 = "죄송합니다.<br>임시 비밀번호를 발급하지 못하였습니다.";
				} else if (resultPW == 1) {
					resultPW2 = "귀하의 이메일로<br>임시 비밀번호를 발급해드렸습니다";
				} else {
					resultPW2 = "죄송합니다.<br>예기지 못한 에러가 발생하였습니다";
				}

				System.out.println(resultPW2);// TODO

				req.setAttribute("EPWresult", resultPW2);
				RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/searchID_resultS.jsp");
				rd.forward(req, resp);

			} else if(dbEmail.equals("")) {
				System.out.println("NOID");// TODO
				req.setAttribute("EPWresult", "가입시 이메일을 제출하지 않으셨습니다.");
				RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/searchID.jsp");
				rd.forward(req, resp);
			} else {
				System.out.println("NOT CORRECT");// TODO
				req.setAttribute("EPWresult", "email이 일치하지 않습니다.");
				RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/searchID.jsp");
				rd.forward(req, resp);

			}
		}
	}

}
