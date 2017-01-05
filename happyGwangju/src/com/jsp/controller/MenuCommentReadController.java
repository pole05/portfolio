package com.jsp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.dto.MenuCommentDto;
import com.jsp.model.MenuCommentDao;

public class MenuCommentReadController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String no = req.getParameter("no");

		MenuCommentDao dao = new MenuCommentDao();
		List<MenuCommentDto> list = dao.selectComment(Integer.parseInt(no));
		req.setAttribute("list", list);
		
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/menuCommentRead.jsp");
		rd.forward(req, resp);
	}

}