package com.islam.spark2;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class Post {
    private int post_uuid;
    private String title;
    private String content;
    private Date publishing_date;
    private List categories;
    
	public int getPost_uuid() {
		return post_uuid;
	}
	public void setPost_uuid(int post_uuid) {
		this.post_uuid = post_uuid;
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
	public Date getPublishing_date() {
		return publishing_date;
	}
	public void setPublishing_date(Date publishing_date) {
		this.publishing_date = publishing_date;
	}
	public List getCategories() {
		return categories;
	}
	public void setCategories(List categories) {
		this.categories = categories;
	}
	
}
