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

public class MenuDetailController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("userId");
		if(id == null) {
			id = "";
		}
		req.setAttribute("id", id);
		
		String o_no = req.getParameter("no");
		int no = Integer.parseInt(o_no);
		req.setAttribute("no", no);

		MenuDao dao = new MenuDao();
		MenuDto dto = dao.selectDetailBoard(id, no);
		req.setAttribute("list", dto);
		
		
		MenuPosterDao iDao = new MenuPosterDao();
		List<MenuPosterDto> fList = iDao.selectImageBySeqNo(no);
		req.setAttribute("fList", fList);
		
		
		// TODO 댓글 개수 구하기
		MenuCommentDao cDao = new MenuCommentDao();
		List<MenuCommentDto> comments = cDao.selectComment(no);
		int csize = comments.size();	// list의 길이 구해서 넘기기
		req.setAttribute("csize", csize);
		
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/menuDetail.jsp");
		rd.forward(req, resp);
	}

	private int size(List<MenuCommentDto> comments) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}