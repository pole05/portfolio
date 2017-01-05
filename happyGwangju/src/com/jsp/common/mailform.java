package com.jsp.common;

public class mailform {
	public StringBuffer mailForm(int pw2){
		
		StringBuffer form = new StringBuffer();
		form.append("<section style='text-align:center; width: 500px ;height: 500px;border-style: solid; border-width: 1px;border-radius: 10px;'>");
		form.append("<header style='width: 100%; position: relative; height: 150px; background-color: aliceblue;'>");
		form.append("<img class='logo' src='http://beehouse92.dothome.co.kr/image/happyGJlogo.png' style='top: 50px; position: relative;'/>");
		form.append("</header><div style='width: 100%; height: 250px; top: 150px; padding-top: 50px;'>");
		form.append("<p>귀하의 임시 비밀번호는</p>");
		form.append("<p style='background-color: lemonchiffon; width: 50%; height: 50px; margin-left: 25%; margin-right: 25%;");
		form.append("line-height: 2em; font-size: 20pt; color: red; font-weight: bold;'>");;
		form.append(pw2+"</p>");
	    form.append("<p>입니다. 이 임시 비밀번호로 로그인해주세요.</p>");
		form.append("</div>");
		form.append("<footer style='position: relative; width: 100%; background-color: black; height: 100px;");
		form.append("bottom:-50px; border-bottom-left-radius: 10px; border-bottom-right-radius: 10px;'>");
		form.append("</footer></section>");
		
		return form;
	}
}
