package com.islam.spark2;

import lombok.Data;

@Data
public class NewCommentPayload {
	private String author;
	private String content;
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isValid() {
	     return author != null && !author.isEmpty() && content != null && !content.isEmpty();
	}
}
