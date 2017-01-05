package com.jsp.dto;

public class ReviewCommentDto {
	int seq;
	int c_no;
	String id;
	String content;
	String time;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getC_no() {
		return c_no;
	}
	public void setC_no(int c_no) {
		this.c_no = c_no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}