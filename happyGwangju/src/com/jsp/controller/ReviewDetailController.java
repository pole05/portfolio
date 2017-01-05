package com.jsp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.dto.ReviewCommentDto;
import com.jsp.dto.ReviewDto;
import com.jsp.model.ReviewCommentDao;
import com.jsp.model.ReviewDao;

public class ReviewDetailController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String seqNo = req.getParameter("seq");

		ReviewDao dao = new ReviewDao();
		
		// 조회수 증가 메소드 실행
		dao.updateReviewByHit(Integer.parseInt(seqNo));

		// 보여줄 내용 조회	
		ReviewDto dto = dao.selectReviewDetail(Integer.parseInt(seqNo));
		req.setAttribute("Review", dto);
		
		// TODO 댓글 개수 구하기
		ReviewCommentDao cDao = new ReviewCommentDao();
		int csize = cDao.selectCommentForCount(Integer.parseInt(seqNo));
		req.setAttribute("csize", csize);
				
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/reviewDetail.jsp");	//TODO
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}