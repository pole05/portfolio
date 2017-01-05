package com.jsp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.dto.ReviewDto;
import com.jsp.model.ReviewDao;

public class ReviewDeleteController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String seq = req.getParameter("seq");

		ReviewDao dao = new ReviewDao();
		
		if(dao.checkReply(Integer.parseInt(seq)) == 1) {
			ReviewDto dto = new ReviewDto();
			dto.setTitle("글이 삭제되었습니다.");
			dto.setContent("본문이 삭제되었습니다.");
			dto.setSeq(Integer.parseInt(seq));
			dao.editReview(dto);			// 답변글이 있다면
		} else {
			dao.deleteReview(Integer.parseInt(seq)); // 답변글이 없다면	
		}
		
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/reviewDelete.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}