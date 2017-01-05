package com.jsp.common;

import java.util.List;

import com.jsp.dto.MenuDto;

public class testFile {

	public static void main(String[] args) {
		List<Integer> gjcfIds = new ParsingHTML_page().parsingGJCFpage();
		for (int i = 0; i < gjcfIds.size(); i++) {
			MenuDto Mdto = new ParsingHTML().parsingGJCF(gjcfIds.get(i));
//			 MenuDto Mdto = new ParsingHTML().parsingGJCF(6863);//5375//6871
			System.out.println("gjcfIds : " + gjcfIds.get(i));
			System.out.println("call" + Mdto.getCall());
			System.out.println("category" + Mdto.getCategory());
			System.out.println("content" + Mdto.getContent());
			System.out.println("endTime" + Mdto.getEndtime());
			System.out.println("homepage" + Mdto.getHomepage());
			System.out.println("host" + Mdto.getHost());
			System.out.println("time" + Mdto.getM_time());
			System.out.println("money" + Mdto.getMoney());
			System.out.println("name" + Mdto.getName());
			String[] posters = Mdto.getP_poster_s().split("&&SEPKEY&&");
			for (int j = 0; j < posters.length; j++) {
				System.out.println(posters[j]);
			}
			System.out.println("place" + Mdto.getPlace());
			System.out.println("startTime" + Mdto.getStarttime());

		}
	}

}
