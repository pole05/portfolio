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

public class MyboardController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id = (String) req.getSession().getAttribute("userId");
		
		ReviewDao rdao = new ReviewDao();
		List<ReviewDto> list = rdao.getMyboard(id);
		int totalCount = rdao.getTotalCount(id);
		req.setAttribute("list", list);
		
		String prePage = req.getParameter("prePage");
		if(prePage == null || prePage.equals("")){prePage = "1";}
		int nowPage = Integer.parseInt(prePage);
	
		int startRow = 0;
		startRow = nowPage *10 - 9;
		
		int startPage = 0;
		int endPage = 0;
 		startPage = (int)((nowPage-1)/10)*10+1;
 		endPage = startPage + 9;
 		int totalPage = (int)(totalCount/10) +1;
 		if(totalPage < endPage){
 			endPage = totalPage;
 		}
		req.setAttribute("totalPage", totalCount/10);
		req.setAttribute("nowPage", nowPage);
		req.setAttribute("startPage", startPage);
		req.setAttribute("endPage", endPage);
		
		
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/myboard.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}