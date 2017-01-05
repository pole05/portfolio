package com.jsp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.model.ReviewCommentDao;

public class ReviewCommentDeleteController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String seq = req.getParameter("seq");
		String c_no = req.getParameter("c_no");

		ReviewCommentDao dao = new ReviewCommentDao();
		
		dao.deleteComment(Integer.parseInt(seq), Integer.parseInt(c_no));
		
		PrintWriter out = resp.getWriter();
		out.print("{\"result\" : \"삭제가 완료되었습니다.\"}");
		
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/reviewCommentDelete.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}