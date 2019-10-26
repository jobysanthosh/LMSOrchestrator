package com.lms.LMSOrchestrator.POJO;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_book_copies")
public class BookCopies implements Serializable{

	private static final long serialVersionUID = 7641796822173416768L;
	
	@EmbeddedId
	private BookCopiesComposite bookCopiesComposite;
	
	@Column(name = "noOfCopies")
	private Integer noOfCopies;

	public BookCopies() {
	}

	/**
	 * @return the bookCopiesComposite
	 */
	public BookCopiesComposite getBookCopiesComposite() {
		return bookCopiesComposite;
	}

	/**
	 * @param bookCopiesComposite the bookCopiesComposite to set
	 */
	public void setBookCopiesComposite(BookCopiesComposite bookCopiesComposite) {
		this.bookCopiesComposite = bookCopiesComposite;
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

	public BookCopies(BookCopiesComposite bookCopiesComposite, Integer noOfCopies) {
		super();
		this.bookCopiesComposite = bookCopiesComposite;
		this.noOfCopies = noOfCopies;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookCopiesComposite == null) ? 0 : bookCopiesComposite.hashCode());
		result = prime * result + ((noOfCopies == null) ? 0 : noOfCopies.hashCode());
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
		BookCopies other = (BookCopies) obj;
		if (bookCopiesComposite == null) {
			if (other.bookCopiesComposite != null)
				return false;
		} else if (!bookCopiesComposite.equals(other.bookCopiesComposite))
			return false;
		if (noOfCopies == null) {
			if (other.noOfCopies != null)
				return false;
		} else if (!noOfCopies.equals(other.noOfCopies))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BookCopies [bookCopiesComposite=" + bookCopiesComposite + ", noOfCopies=" + noOfCopies + "]";
	}
	
	

}