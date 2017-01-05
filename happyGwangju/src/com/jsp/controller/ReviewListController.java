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

public class ReviewListController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String text = req.getParameter("text");
		String searchtype = req.getParameter("searchtype"); 
		
		/* 페이징 */
		String nowPage = req.getParameter("nowPage");	// 페이지 번호
		if(nowPage == null || nowPage.equals("")) {
			nowPage = "1";			// nowPage= 값이 없거나 공백일 때는 1페이지를 띄워라
		}
		int pageNum = Integer.parseInt(nowPage);
		
		int startRow = pageNum * 10 - 9;	// x 페이지일 때 게시물 시작 번호

		ReviewDao dao = new ReviewDao();
		List<ReviewDto> list = null;
		
		String countid = "";
		int totalCount = 0;	// 전체 게시물 수
		
		if(text == null || text.equals("")) {			// 그냥 볼 때
			list = dao.selectReview(startRow - 1);
			totalCount = dao.getTotalCount(countid);
		} else {										// 검색할 때
			list = dao.findReview(text, searchtype, startRow - 1);
			totalCount = dao.findCountReview(text, searchtype);
		}
		
		req.setAttribute("list", list);
		
	 	
	 	int totalPage = 0;
	 	if(totalCount % 10 == 0) {
	 		totalPage = totalCount / 10;		// 페이지 번호 몇번까지 써 줄 것인가
	 	} else {
	 		totalPage = totalCount / 10 + 1;
	 	}
		
	 	int startPage = 0;
	 	int endPage = 0;
	 	if(pageNum % 10 == 0) { // 10 20 30 40 50
	 		startPage = pageNum / 10 * 10 - 9;
	 	} else {
	 		startPage = pageNum / 10 * 10 + 1;
	 	}
	 	endPage = startPage + 9;
		
	 	// total = 2    endPage = 10
	 	if(totalPage < endPage) {
	 		endPage = totalPage;
	 	}
		
		req.setAttribute("startPage", startPage);
		req.setAttribute("endPage", endPage);
		req.setAttribute("totalPage", totalPage);
		req.setAttribute("totalCount", totalCount);
		req.setAttribute("pageNum", pageNum);
				
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/reviewList.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

}