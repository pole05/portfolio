package com.jsp.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ParsingHTML_page {
	 public List<Integer> parsingGJCFpage() {

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int mon = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		// System.out.println(year +"M" + mon +"D"+ day);
		int page = 1;
		List<Integer> gjcfIds = new ArrayList<Integer>();

		while (true) {
			String result = Util.request("http://www.gjcf.or.kr/bbs/board.php?bo_table=cal&year=" + year + "&month="
					+ mon + "&day=" + day + "&page=" + page);
			if (result == null || result.equals("")) {
				return null;
			}
			int pre_idx1 = result.indexOf("empty_list");
			 if(pre_idx1 >0)
			 {
				 System.out.println("Page Is Not Exist");	
				 break;
				}

			String[] SResult = result.split("<li class=\"bo_subject\"");

			for (int i = 1; i < SResult.length; i++) {
				int idx3 = SResult[i].indexOf("class=\"bo_cate_link\"");
				int idx4 = SResult[i].indexOf("class=\"bo_img_subject\"", idx3);
				 if(idx3<0 || idx4<0){
				System.out.println("error point in 3,4"); return null;}
				SResult[i] = SResult[i].substring(idx3, idx4);
				int idx5 = SResult[i].indexOf("<a href=\"");
				int idx6 = SResult[i].indexOf("</a>", idx5);
				 if(idx5<0 || idx6<0){System.out.println("error point in 5,6"); return null;}
				SResult[i] = SResult[i].substring(idx5, idx6);
				int idx7 = SResult[i].indexOf("wr_id=");
				int idx8 = SResult[i].indexOf("&", idx7 + "wr_id=".length());
				 if(idx7<0 || idx8<0){System.out.println("error point in 7,8"); return null;}
				SResult[i] = SResult[i].substring(idx7, idx8);
				SResult[i] = SResult[i].replace("wr_id=", "");
				SResult[i] = SResult[i].trim();

//				System.out.println("gjcfIds : " + SResult[i]);
				gjcfIds.add(Integer.parseInt(SResult[i]));
			}
			page++;
		}
		 return gjcfIds;
	}
}