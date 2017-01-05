package com.jsp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jsp.dto.MenuDto;
import com.jsp.dto.MenuPosterDto;
import com.jsp.model.MenuDao;
import com.jsp.model.MenuPosterDao;

public class MenuListController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("userId");
		if(id == null) {
			id = "";
		}
		
		String text = req.getParameter("text");		
		String category = req.getParameter("category");
		String order = req.getParameter("order");
		if(order == null || order.equals("")) {
			order = "no";
		}
		
		req.setAttribute("category", category);
		req.setAttribute("order", order);
		
		/* 페이징 */
		String nowPage = req.getParameter("nowPage"); // 페이지 번호
		if (nowPage == null || nowPage.equals("")) {
			nowPage = "1"; // nowPage= 값이 없거나 공백일 때는 1페이지를 띄워라
		}
		int pageNum = Integer.parseInt(nowPage);

		int startRow = (pageNum-1) * 6; // x 페이지일 때 게시물 시작 번호

		MenuDao dao = new MenuDao();
		List<MenuDto> list = null;
		
		// 카테고리에 따라서 보기
		int totalCount = 0;
		
		if(category == null || category.equals("")) {	// 카테고리 없을 때
			if(text != null && !text.equals("")) {		// 카테고리 없을 때, 검색 있을 때
				list = dao.findMenu(text, startRow);
				totalCount = dao.findCount(text);
			} else {	// 카테고리 없을 때, 검색 없을 때 (전체 게시판)
				list = dao.selectBoard(startRow, order, id);
				totalCount = dao.getTotalCount(); // 전체 게시물 수
			}
		} else {		// 카테고리 있을 때
			req.setAttribute("category", category);
			if(text != null && !text.equals("")) {		// 카테고리 있을 때, 검색 있을 때
				list = dao.findCategoryMenu(text, startRow, Integer.parseInt(category));				
				totalCount = dao.findCategoryCount(text,Integer.parseInt(category));
			} else {	// 카테고리 있을 때, 검색 없을 때
				list = dao.categoryBoard(id, startRow, Integer.parseInt(category), order);
				totalCount = dao.categoryCount(Integer.parseInt(category));			
			}
		}
		
		req.setAttribute("list", list);
		MenuPosterDao MPdao = new MenuPosterDao();
		for(int i = 0; i < list.size(); i++){
			int no = list.get(i).getNo();
//			System.out.println("here!"+MPdao.selectImageBySeqNo(no));
			if(MPdao.selectImageBySeqNo(no)!=null && !MPdao.selectImageBySeqNo(no).isEmpty()){
				list.get(i).setP_poster_s(MPdao.selectImageBySeqNo(no).get(0).getP_poster_s());
			}
		}
		
		int totalPage = 0;
		if (totalCount % 6 == 0) {
			totalPage = totalCount / 6; // 페이지 번호 몇번까지 써 줄 것인가
		} else {
			totalPage = totalCount / 6 + 1;
		}

		int startPage = 0;
		int endPage = 0;
		if (pageNum % 10 == 0) { // 10 20 30 40 50
			startPage = pageNum / 10 * 10 - 9;
		} else {
			startPage = pageNum / 10 * 10 + 1;
		}
		endPage = startPage + 9;

		// total = 2 endPage = 10
		if (totalPage < endPage) {
			endPage = totalPage;
		}

		req.setAttribute("startPage", startPage);
		req.setAttribute("endPage", endPage);
		req.setAttribute("totalPage", totalPage);
		req.setAttribute("totalCount", totalCount);
		req.setAttribute("pageNum", pageNum);

		// 로그인 정보 받아오기
		req.setAttribute("userId", id);

		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/menuList.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}