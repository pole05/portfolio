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

import com.jsp.common.ParsingHTML;
import com.jsp.common.ParsingHTML_page;
import com.jsp.dto.MenuDto;
import com.jsp.dto.MenuPosterDto;
import com.jsp.model.HeartDao;
import com.jsp.model.MenuDao;
import com.jsp.model.MenuPosterDao;

public class AutoSubmitController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = (String) req.getSession().getAttribute("userId");
		if (id == null || id == "") {
			System.out.println("noLogin");
			req.setAttribute("result", "로그인 후 이용하세요!");
			req.getRequestDispatcher("/login.do");
		} else if (!id.equals("admin")) {
			System.out.println("notAdmin");
			req.setAttribute("result", "운영자만 작성 가능합니다!");
			req.getRequestDispatcher("/login.do");
		} else {
			System.out.println("parsingStart");
			List<Integer> gjcfIds = new ParsingHTML_page().parsingGJCFpage();
			for (int i = 0; i < gjcfIds.size(); i++) {
				System.out.println("gjcfIds : " + gjcfIds.get(i));
				MenuDto Mdto = new ParsingHTML().parsingGJCF(gjcfIds.get(i));

				String name = Mdto.getName(); // 행사명
				name = name.replace("<", "&lt;").replace(">", "&gt;"); // 내용 입력할
																		// 때 <>를
																		// 못 넣게
//				System.out.println(name);
				int category = Mdto.getCategory();
//				System.out.println(category);
				String starttime = Mdto.getStarttime();
				starttime = starttime.replace("<", "&lt;").replace(">", "&gt;");
//				System.out.println(starttime);
				String endtime = Mdto.getEndtime();
				endtime = endtime.replace("<", "&lt;").replace(">", "&gt;");
//				System.out.println(endtime);
				String m_time = Mdto.getM_time();
				m_time = m_time.replace("<", "&lt;").replace(">", "&gt;");
//				System.out.println(m_time);
				String place = Mdto.getPlace();
				place = place.replace("<", "&lt;").replace(">", "&gt;");
//				System.out.println(place);
				String host = Mdto.getHost();
				host = host.replace("<", "&lt;").replace(">", "&gt;");
//				System.out.println(host);
				String call = Mdto.getCall();
				call = call.replace("<", "&lt;").replace(">", "&gt;");
//				System.out.println(call);
				String money = Mdto.getMoney();
				money = money.replace("<", "&lt;").replace(">", "&gt;");
//				System.out.println(money);
				String homepage = Mdto.getHomepage();
				homepage = homepage.replace("<", "&lt;").replace(">", "&gt;");
//				System.out.println(homepage);
				String content = Mdto.getContent();
				content = content.replace("<", "&lt;").replace(">", "&gt;");
//				System.out.println(content);

				// MenuDB에 값 있는지 확인!!
				MenuDao bDao = new MenuDao();
				int isExist = bDao.findMenuForAutoSubmit(name);
				if (isExist!=0) {
					// MenuDB에 insert!
					bDao.insertMenu(Mdto);
					String[] posters = Mdto.getP_poster_s().split("&&SEPKEY&&");
					int maxSeqNo = bDao.getMaxSeqNo();
					for (int j = 0; j < posters.length; j++) {
						MenuPosterDto dto = new MenuPosterDto();
						dto.setNo(maxSeqNo); // 어느 게시물의 첨부파일인가? 마지막으로 입력된 게시물 번호
												// 조회
						dto.setP_poster_o(posters[j]);
						dto.setP_poster_s(posters[j]);

						MenuPosterDao dao = new MenuPosterDao();
						int result = dao.insertImage(dto);
						System.out.println(posters[j]);
					}
				}else{System.out.println(isExist);}
			}

		}
		req.setAttribute("logout", "성공적으로 문서가 입력되었습니다");
		req.getRequestDispatcher("/Main.do");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}