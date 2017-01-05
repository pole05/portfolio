package com.example.customlistview;

import java.io.Serializable;

public class IconTextList implements Serializable {
	
	private int no;
	private String category_s;
	private String name;
	private String place;
	private String call;
	private String host;
	private String money;
	private String homepage;
	private String starttime;
	private String endtime;
	private String p_poster_s;
	
	
	public String getName() {
		return name;
	}
	
	public String getHost() {
		return host;
	}
	
	public String getPlace() {
		return place;
	}
		
	public String getCall() {
		return call;
	}
		
	public String getMoney() {
		return money;
	}
			
	public String getHomepage() {
		return homepage;
	}
		
	public String getCategory_s() {
		return category_s;
	}
	
	public String getStarttime() {
		return starttime;
	}
		
	public String getEndtime() {
		return endtime;
	}
	
	public int getNo() {
		return no;
	}

	public String getP_poster_s() {
		return p_poster_s;
	}

	public IconTextList(int no, String category_s, String name, String place,
			String call, String host, String money, String homepage,
			String starttime, String endtime, String p_poster_s) {
		this.no = no;
		this.category_s = category_s;
		this.name = name;
		this.place = place;
		this.call = call;
		this.host = host;
		this.money = money;
		this.homepage = homepage;
		this.starttime = starttime;
		this.endtime = endtime;
		this.p_poster_s = p_poster_s;
	}

}