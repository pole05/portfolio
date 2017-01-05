package com.jsp.controller.mobile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.jsp.dto.MenuDto;
import com.jsp.model.MenuDao;

public class HomeController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id = (String) req.getSession().getAttribute("userId");
		if(id==null){id = "";}
		MenuDao MDao = new MenuDao();
		List<MenuDto> list = MDao.selectMainBoard(id);
		
		Map<String, List<MenuDto>> map = new HashMap<>();
		map.put("list", list);
		
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(map);
		
//		req.setAttribute("list", list);
		req.setAttribute("list", json);
		
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/mobile/Main.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}