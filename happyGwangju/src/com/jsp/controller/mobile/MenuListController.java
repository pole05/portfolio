package com.jsp.controller.mobile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

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
		
		String order = req.getParameter("order");
		if(order == null || order.equals("")) {
			order = "no";
		}

		MenuDao dao = new MenuDao();
		List<MenuDto> list = dao.mobileSelectBoard();
		
		Map<String, List<MenuDto>> map = new HashMap<>();
		map.put("list", list);
		
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(map);
		
		req.setAttribute("list", json);
		
		
//		MenuPosterDao MPdao = new MenuPosterDao();
//		for(int i = 0; i < list.size(); i++){
//			int no = list.get(i).getNo();
//			if(MPdao.selectImageBySeqNo(no)!=null && !MPdao.selectImageBySeqNo(no).isEmpty()){
//				list.get(i).setP_poster_s(MPdao.selectImageBySeqNo(no).get(0).getP_poster_s());
//			}
//		}		

		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/mobile/MenuList.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}