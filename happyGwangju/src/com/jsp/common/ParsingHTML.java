package com.jsp.common;

import java.util.Calendar;

import com.jsp.dto.MenuDto;

public class ParsingHTML {
	public MenuDto parsingGJCF(int gjcfid) {
//		System.out.println("start!!");
		MenuDto Mdto = new MenuDto();
		String result = Util.request("http://www.gjcf.or.kr/bbs/board.php?bo_table=cal&wr_id="+gjcfid);
		if(result==null||result.equals("")){
//			System.out.println("결과값없음");
			return null;
		}
//		System.out.println("뭐지");
		for (int i = 1; i <= 9; i++) {
//			System.out.println("for구문 안쪽입니다");
			String target = "";
			String target2 = "";
			String EventInfo = "";
			switch (i) {
			case 1:
				target = "행사기간";
				target2 = "<span class=\"bo_date\">";
				
				int idx1_1 = result.indexOf("<span class=\"bold\">" + target + "</span>");
				int idx1_2 = result.indexOf(target2, idx1_1);
				if(idx1_2 < 0){
					System.out.println("행사기간");
					break;
				}
				EventInfo = result.substring(idx1_1, idx1_2);
				while (EventInfo.contains("<") && EventInfo.contains(">")) {
					int idx1 = EventInfo.indexOf("<");
					int idx2 = EventInfo.indexOf(">", idx1);
					String letter = EventInfo.substring(idx1, idx2 + 1);
					boolean isMatch = letter.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*");
					if(!isMatch){
						EventInfo = EventInfo.replace(letter, "");
					}else{
						break;
					}
//					System.out.println("혹시 무한루프에 빠졌나요?");
				}
				EventInfo = EventInfo.replace(target, "");
				String[] duration = EventInfo.split("~");
				duration[0] = duration[0].trim();
				duration[1] = duration[1].trim();
				
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				int mon = cal.get(Calendar.MONTH)+1;
				int day = cal.get(Calendar.DAY_OF_MONTH);
				
				String[] eachCal = duration[1].split("-");
				if(Integer.parseInt(eachCal[0]) < year){
					System.out.println("행사기간연도");
					return null;
				}else if(Integer.parseInt(eachCal[1]) < mon && Integer.parseInt(eachCal[0]) == year){
					System.out.println("행사기간월");
					return null;
				}else if(Integer.parseInt(eachCal[2]) < day && Integer.parseInt(eachCal[1]) == mon && Integer.parseInt(eachCal[0]) == year){
					System.out.println("행사기간일");
					return null;
				}
//				System.out.println(duration[0]);
//				System.out.println(duration[1]);
				
				Mdto.setStarttime(duration[0]);
				Mdto.setEndtime(duration[1]);
				break;
			case 2:
				target = "행사명";
				target2 = "</h1>";
				break;
			case 3:
				target = "문의전화";
				target2 = "<span class=\"bo_date\">";
				break;
			case 4:
				target = "홈페이지";
				target2 = "<span class=\"bo_date\">";
				break;
			case 5:
				target = "주최 / 주관";
				target2 = "</section>";
				break;
			case 6:
				target = "행사장소";
				target2 = "<span class=\"bo_date\">";
				break;
			case 7:
				target = "장 르";
				target2 = "</section>";
				break;
			case 8:
				target = "관람시간";
				target2 = "</section>";
				break;
			case 9:
				target = "관람료";
				target2 = "</section>";
				break;
			}
			int idx1_1 = result.indexOf("<span class=\"bold\">" + target + "</span>");
			int idx1_2 = result.indexOf(target2, idx1_1);
			if(idx1_2 < 0){
				System.out.println(target+"없음");
				break;
			}
//			System.out.println(target+"을 찾았다!");
			EventInfo = result.substring(idx1_1, idx1_2);
			while (EventInfo.contains("<") && EventInfo.contains(">")) {
				int idx1 = EventInfo.indexOf("<");
				int idx2 = EventInfo.indexOf(">", idx1);
				String letter = EventInfo.substring(idx1, idx2 + 1);
				boolean isMatch = letter.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*");
				if(!isMatch){
				EventInfo = EventInfo.replace(letter, "");
				}else{
					break;
				}
//				System.out.println("혹시 무한루프에 빠졌나요");
			}
//			System.out.println("괄호 지우는 구문을 탈출했따!");
			EventInfo = EventInfo.replace(target, "");
			EventInfo = EventInfo.trim();
//			System.out.println(EventInfo);
			
			switch(i){
			case 2:
				Mdto.setName(EventInfo);
//				System.out.println(EventInfo+"값을 넣어줌");
				break;
			case 3:
				Mdto.setCall(EventInfo);
//				System.out.println(EventInfo+"값을 넣어줌");
				break;
			case 4:
				Mdto.setHomepage(EventInfo);
//				System.out.println(EventInfo+"값을 넣어줌");
				break;
			case 5:
				Mdto.setHost(EventInfo);
//				System.out.println(EventInfo+"값을 넣어줌");
				break;
			case 6:
				Mdto.setPlace(EventInfo);
//				System.out.println(EventInfo+"값을 넣어줌");
				break;
			case 7:
				if(EventInfo.equals("공연")){
					Mdto.setCategory(1);
					break;
				}else if(EventInfo.equals("전시")){
					Mdto.setCategory(2);
					break;
				}else if(EventInfo.equals("축제행사")){
					Mdto.setCategory(3);
					break;
				}else{
					Mdto.setCategory(5);
					break;
				}
			case 8:
				Mdto.setM_time(EventInfo);
//				System.out.println(EventInfo+"값을 넣어줌");
				break;
			case 9:
				Mdto.setMoney(EventInfo);
//				System.out.println(EventInfo+"값을 넣어줌");
				break;
			}
		}
		
//		System.out.println("for구문을 탈출!");
		int idx1_1 = result.indexOf("<article id=\"bo_v_atc\">");
		int idx1_2 = result.indexOf("</article>", idx1_1);
		if(idx1_1 < 0 || idx1_2 < 0){
			System.out.println("내용박스없음!");
			return null;
		}else{
//			System.out.println("내용박스 안의 내용물을 탈취하겠다!");
			String content = result.substring(idx1_1, idx1_2+"</article>".length());
			
			String[] imgs2 = content.split("<img");
			for(int i = 1; i<imgs2.length; i++){
				imgs2[i] = "<img"+imgs2[i];
			}
			String preImgString = "";
			for(int i = 1; i<imgs2.length; i++){
				int idx1_3 = imgs2[i].indexOf("src=\"");
				int idx1_4 = imgs2[i].indexOf(".jpg", idx1_3);
				int idx1_5 = imgs2[i].indexOf(".gif", idx1_3);
				int idx1_6 = imgs2[i].indexOf(".png", idx1_3);
				if(idx1_4 > 0){
					imgs2[i] = imgs2[i].substring(idx1_3, idx1_4) + ".jpg";
				}else if(idx1_5 > 0){
					imgs2[i] = imgs2[i].substring(idx1_3, idx1_5) + ".gif";
				}else if(idx1_6 > 0){
					imgs2[i] = imgs2[i].substring(idx1_3, idx1_6) + ".png";
				}else{
					imgs2[i] = "";
				}
				imgs2[i] = imgs2[i].replace("src=\"", "");
//				System.out.println("img들입니다"+imgs2[i]);
				preImgString +=imgs2[i]+"&&SEPKEY&&";
			}
				Mdto.setP_poster_s(preImgString);
			int idx_c = content.indexOf("<div id=\"bo_v_con\">");
			int idx_c2 = content.indexOf("</article>", idx_c);
			content = content.substring(idx_c, idx_c2);
			for(int i = 1; i<imgs2.length; i++){
				if(content.contains(imgs2[i])){
					content = content.replaceAll(imgs2[i], "");
				}
			}
			while (content.contains("<") && content.contains(">")) {
				int idx1 = content.indexOf("<");
				int idx2 = content.indexOf(">", idx1);
				String letter = content.substring(idx1, idx2 + 1);
				boolean isMatch = letter.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*");
				if(!isMatch){
					content = content.replace(letter, "");
				}else{
					break;
				}
//				System.out.println("혹시 무한루프에 빠졌나요?");
			}
			content = content.trim();
			Mdto.setContent(content);
//			System.out.println("내용입니다!"+content);
		}
		return Mdto;
	}
}