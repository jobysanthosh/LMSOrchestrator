package com.lms.LMSOrchestrator.POJO;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_book_loans")
public class BookLoans implements Serializable{
	
	private static final long serialVersionUID = 6397133592854929238L;

	@EmbeddedId
	private BookLoansCompositeKey blCompKey;
	
	@Column(name = "dateOut")
	private Timestamp dateOut;
	
	@Column(name = "dueDate")
	private Timestamp dueDate;
	
	public BookLoans() {}
	
	public BookLoans(BookLoansCompositeKey blCompKey, Timestamp dateOut, Timestamp dueDate) {
		super();
		this.blCompKey = blCompKey;
		this.dateOut = dateOut;
		this.dueDate = dueDate;
	}

	public BookLoansCompositeKey getBlCompKey() {
		return blCompKey;
	}

	public void setBlCompKey(BookLoansCompositeKey blCompKey) {
		this.blCompKey = blCompKey;
	}

	public Timestamp getDateOut() {
		return dateOut;
	}

	public void setDateOut(Timestamp dateOut) {
		this.dateOut = dateOut;
	}

	public Timestamp getDueDate() {
		return dueDate;
	}

	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blCompKey == null) ? 0 : blCompKey.hashCode());
		result = prime * result + ((dateOut == null) ? 0 : dateOut.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
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
		BookLoans other = (BookLoans) obj;
		if (blCompKey == null) {
			if (other.blCompKey != null)
				return false;
		} else if (!blCompKey.equals(other.blCompKey))
			return false;
		if (dateOut == null) {
			if (other.dateOut != null)
				return false;
		} else if (!dateOut.equals(other.dateOut))
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BookLoans [blCompKey=" + blCompKey + ", dateOut=" + dateOut + ", dueDate=" + dueDate + "]";
	}
}