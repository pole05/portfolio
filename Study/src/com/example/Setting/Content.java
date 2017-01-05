package com.example.Setting;

public class Content {
	int image;
	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	String text;

	public Content(String string, int image) {
		this.text = string;
		this.image = image;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
