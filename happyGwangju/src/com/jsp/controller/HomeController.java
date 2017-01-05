package com.jsp.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.jsp.model.HeartDao;
import com.jsp.model.MenuDao;
import com.jsp.model.MenuPosterDao;

public class HomeController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id = (String) req.getSession().getAttribute("userId");
		if(id==null){id = "";}
		MenuDao MDao = new MenuDao();
		List<MenuDto> list = MDao.selectMainBoard(id);
		
//		String userAgent = req.getHeader("user-agent");
//		System.out.println(userAgent);
//		
//		if(userAgent.indexOf("Android") > -1) { // android 접속
//			Map<String, List<MenuDto>> map = new HashMap<>();
//			map.put("list", list);
//			
//			ObjectMapper om = new ObjectMapper();
//			String json = om.writeValueAsString(map);
//			
////			req.setAttribute("list", list);
//			req.setAttribute("list", json);
//			
//			RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/mobile/Main.jsp");
//			rd.forward(req, resp);
//		} else {
			req.setAttribute("list", list);
			MenuPosterDao MPdao = new MenuPosterDao();
			for(int i = 0; i < list.size(); i++){
				int no = list.get(i).getNo();
//				System.out.println("here!"+MPdao.selectImageBySeqNo(no));
				if(MPdao.selectImageBySeqNo(no)!=null && !MPdao.selectImageBySeqNo(no).isEmpty()){
					list.get(i).setP_poster_s(MPdao.selectImageBySeqNo(no).get(0).getP_poster_s());
				}
			}
			RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/Main.jsp");
			rd.forward(req, resp);
//		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}