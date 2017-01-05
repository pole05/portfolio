package com.example.customlistview;

public class ItemVO {	// 담고 싶은 내용들

	int no;
	String category_s;
	String name;
	String place;
	String call;
	String host;
	String money;
	String homepage;
	String starttime;
	String endtime;
	String p_poster_s;	
	
	// 셋겟 메소드
	// generate toString
	
	public String toString() {
		return "ItemVO [no=" + no + ", category_s=" + category_s + ", name=" + name + ", place=" + place + ", call=" + call
				+ ", host=" + host + ", money" + money + ", homepage=" + homepage + ", starttime=" + starttime + ", endtime=" + endtime 
				+ ", p_poster_s=" + p_poster_s + "]";
	}
	
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getHost() {
		return host;
	}
	
	
	public void setHost(String host) {
		this.host = host;
	}
	
	
	public String getPlace() {
		return place;
	}
	
	
	public void setPlace(String place) {
		this.place = place;
	}
	
	
	public String getCall() {
		return call;
	}
	
	
	public void setCall(String call) {
		this.call = call;
	}
	
	
	public String getMoney() {
		return money;
	}
	
	
	public void setMoney(String money) {
		this.money = money;
	}
	
	
	public String getHomepage() {
		return homepage;
	}
	
	
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	
	
	public String getCategory_s() {
		return category_s;
	}
	
	
	public void setCategory_s(String category_s) {
		this.category_s = category_s;
	}
	
	
	public String getStarttime() {
		return starttime;
	}
	
	
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	
	
	public String getEndtime() {
		return endtime;
	}
	
	
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getP_poster_s() {
		return p_poster_s;
	}

	public void setP_poster_s(String p_poster_s) {
		this.p_poster_s = p_poster_s;
	}
	
}