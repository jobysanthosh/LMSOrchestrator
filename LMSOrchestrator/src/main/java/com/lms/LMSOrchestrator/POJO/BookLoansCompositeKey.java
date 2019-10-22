package com.lms.LMSOrchestrator.POJO;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class BookLoansCompositeKey implements Serializable {
	
	private static final long serialVersionUID = 4189838118899642993L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cardNo")
	private Borrower borrower;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "branchId")
	private LibraryBranch branch;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bookId")
	private Book book;
	
	public BookLoansCompositeKey() {}

	public BookLoansCompositeKey(Borrower borrower, LibraryBranch branch, Book book) {
		super();
		this.borrower = borrower;
		this.branch = branch;
		this.book = book;
	}

	public Borrower getBorrower() {
		return borrower;
	}

	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
	}

	public LibraryBranch getBranch() {
		return branch;
	}

	public void setBranch(LibraryBranch branch) {
		this.branch = branch;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		result = prime * result + ((borrower == null) ? 0 : borrower.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookLoansCompositeKey other = (BookLoansCompositeKey) obj;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		if (borrower == null) {
			if (other.borrower != null)
				return false;
		} else if (!borrower.equals(other.borrower))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BookLoansCompositeKey [borrower=" + borrower + ", branch=" + branch + ", book=" + book + "]";
	}
}
