package com.example.demo.modules.xiaohua.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="zgdream",type="dream",indexStoreType="fs",shards=2,replicas=1,refreshInterval="-1")
public class Dream {
	
	
	@Id
	private String title;
	
	private String des;
	
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

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Dream [title=" + title + ", des=" + des + ", content=" + content + ", time=" + time + "]";
	}


	
	
	
}
