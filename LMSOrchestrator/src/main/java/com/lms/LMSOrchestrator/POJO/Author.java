package com.lms.LMSOrchestrator.POJO;

public class Author {
	private Integer authorId;
	private String authorName;
	
	public Author() {}

	public Author(Integer authorId, String authorName) {
		super();
		this.authorId = authorId;
		this.authorName = authorName;
	}
	
	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}


}