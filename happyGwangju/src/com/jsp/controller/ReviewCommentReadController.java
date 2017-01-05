package com.jsp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.dto.ReviewCommentDto;
import com.jsp.model.ReviewCommentDao;

public class ReviewCommentReadController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String seq = req.getParameter("seq");

		ReviewCommentDao dao = new ReviewCommentDao();
		List<ReviewCommentDto> list = 
			dao.selectComment(Integer.parseInt(seq));
		 
		req.setAttribute("list", list);
		
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/reviewCommentRead.jsp");	//TODO
		rd.forward(req, resp);
	}

}