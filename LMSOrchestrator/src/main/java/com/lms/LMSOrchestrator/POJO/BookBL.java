package com.lms.LMSOrchestrator.POJO;

public class BookBL {
	private Integer bookId;
	private String title;
	
	public BookBL() {
	}

	public BookBL(Integer bookId, String title) {
		super();
		this.bookId = bookId;
		this.title = title;
	}
	
	public Integer getBookId() {
		return bookId;
	}
	
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


}