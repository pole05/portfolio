package com.jsp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jsp.dto.MenuCommentDto;
import com.jsp.dto.MenuDto;
import com.jsp.dto.MenuPosterDto;
import com.jsp.model.MenuCommentDao;
import com.jsp.model.MenuDao;
import com.jsp.model.MenuPosterDao;

public class MenuEditController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("userId");
		if(id == null) {
			id = "";
		}
		
		String no_o = req.getParameter("no");
		int no = Integer.parseInt(no_o);
		req.setAttribute("no", no);

		MenuDao dao = new MenuDao();
		MenuDto list = dao.selectDetailBoard(id, no);
		String content = list.getContent();
		content = content.replace("<br>","\r\n");
		
		req.setAttribute("list", list);
		req.setAttribute("content", content);

		MenuPosterDao iDao = new MenuPosterDao();
		List<MenuPosterDto> iDto = iDao.selectImageBySeqNo(no);
		req.setAttribute("poster", iDto);
		

		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/menuEdit.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}