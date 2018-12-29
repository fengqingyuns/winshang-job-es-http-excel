package com.example.demo.modules.xiaohua.entity;

public class Joke {

	private String title;
	
	private String content;
	
	private String time;
	
	

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Joke [title=" + title + ", content=" + content + ", time=" + time + "]";
	}
	

}
