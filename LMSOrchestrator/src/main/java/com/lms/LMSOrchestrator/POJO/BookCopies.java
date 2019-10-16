package com.lms.LMSOrchestrator.POJO;

public class BookCopies {
	private Book book;
	private LibraryBranch branch;
	private Integer noOfCopies;
	
	public BookCopies() {}

	public BookCopies(Book book, LibraryBranch branch, Integer noOfCopies) {
		super();
		this.book = book;
		this.branch = branch;
		this.noOfCopies = noOfCopies;
	}
	
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public LibraryBranch getBranch() {
		return branch;
	}

	public void setBranch(LibraryBranch branch) {
		this.branch = branch;
	}

	public int getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(Integer noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

}