package com.lms.LMSOrchestrator.POJO;

public class BookCopiesBL {
	private Integer branchId;
	private Integer bookId;
	private Integer noOfCopies;
	
	public BookCopiesBL() {
	}

	public BookCopiesBL(Integer branchId, Integer bookId, Integer noOfCopies) {
		super();
		this.branchId = branchId;
		this.bookId = bookId;
		this.noOfCopies = noOfCopies;
	}

	/**
	 * @return the branchId
	 */
	public Integer getBranchId() {
		return branchId;
	}

	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return the bookId
	 */
	public Integer getBookId() {
		return bookId;
	}

	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return the noOfCopies
	 */
	public Integer getNoOfCopies() {
		return noOfCopies;
	}

	/**
	 * @param noOfCopies the noOfCopies to set
	 */
	public void setNoOfCopies(Integer noOfCopies) {
		this.noOfCopies = noOfCopies;
	}
	


}