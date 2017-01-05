package com.jsp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.dto.ReviewDto;
import com.jsp.model.ReviewDao;

public class ReviewEditController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String seqNo = req.getParameter("seq");
		int seq = Integer.parseInt(seqNo);
		req.setAttribute("seq", seq);

		ReviewDao rdao = new ReviewDao();
		ReviewDto list = rdao.selectReviewDetail(seq);
		req.setAttribute("list", list);
		
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/reviewEdit.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String seq = req.getParameter("seq");
	    String title = req.getParameter("title");
	    String content = req.getParameter("content");	    
	    
		ReviewDto rdto = new ReviewDto();
		rdto.setTitle(title);
		rdto.setContent(content);
		rdto.setSeq(Integer.parseInt(seq));
		
		ReviewDao rdao = new ReviewDao();
		int result = rdao.editReview(rdto);
		
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/reviewUpload.jsp");
		rd.forward(req, resp);
	}

}