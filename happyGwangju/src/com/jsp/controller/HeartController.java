package com.jsp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jsp.dto.HeartDto;
import com.jsp.model.HeartDao;
import com.jsp.model.MenuPosterDao;

public class HeartController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 로그인 정보
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("userId");

		/* 페이징 */
		String nowPage = req.getParameter("nowPage"); // 페이지 번호
		if (nowPage == null || nowPage.equals("")) {
			nowPage = "1"; // nowPage= 값이 없거나 공백일 때는 1페이지를 띄워라
		}
		int pageNum = Integer.parseInt(nowPage);

		int startRow = (pageNum - 1) * 3; // x 페이지일 때 게시물 시작 번호
		
		// 조회 메소드 실행
		HeartDao dao = new HeartDao();
		List<HeartDto> list = dao.selectBoard(startRow, id);
		req.setAttribute("list", list);
		MenuPosterDao MPdao = new MenuPosterDao();
		for(int i = 0; i < list.size(); i++){
			int no = list.get(i).getNo();
//			System.out.println("here!"+MPdao.selectImageBySeqNo(no));
			if(MPdao.selectImageBySeqNo(no)!=null && !MPdao.selectImageBySeqNo(no).isEmpty()){
				list.get(i).setP_poster_s(MPdao.selectImageBySeqNo(no).get(0).getP_poster_s());
			}
		}
		int totalCount = dao.getTotalCount(id); // 전체 게시물 수

		int totalPage = 0;
		if (totalCount % 3 == 0) {
			totalPage = totalCount / 3; // 페이지 번호 몇번까지 써 줄 것인가
		} else {
			totalPage = totalCount / 3 + 1;
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
		// 페이징 끝

		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/heartList.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}