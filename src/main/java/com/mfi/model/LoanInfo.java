package com.mfi.model;



import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class LoanInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer LoanInfoId;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="customer_id")
	private Customer customer;
	private Double loanAmount;
	private Date registerDate;
	private Integer loanTermYear;
	private double interestRate;
	private String description;
	private Integer creditScore;
	private String status;
	private int createdUser;
	private LocalDate createdDate;
	private int updateUser;
	private LocalDate updateDate;
	


	public LoanInfo() {
		super();
	}

	public LoanInfo(Customer customer, Double loanAmount, Date registerDate, Integer loanTermYear, double interestRate,
			String description, Integer creditScore, String status, int createdUser, LocalDate createdDate,
			int updateUser, LocalDate updateDate) {
		super();
		this.customer = customer;
		this.loanAmount = loanAmount;
		this.registerDate = registerDate;
		this.loanTermYear = loanTermYear;
		this.interestRate = interestRate;
		this.description = description;
		this.creditScore = creditScore;
		this.status = status;
		this.createdUser = createdUser;
		this.createdDate = createdDate;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
	}


	public Integer getLoanInfoId() {
		return LoanInfoId;
	}

	public void setLoanInfoId(Integer loanInfoId) {
		LoanInfoId = loanInfoId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}



	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}



	public Integer getLoanTermYear() {
		return loanTermYear;
	}

	public void setLoanTermYear(Integer loanTermYear) {
		this.loanTermYear = loanTermYear;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(Integer creditScore) {
		this.creditScore = creditScore;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(int createdUser) {
		this.createdUser = createdUser;
	}

	

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public int getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(int updateUser) {
		this.updateUser = updateUser;
	}

	public LocalDate getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "LoanInfo [LoanInfoId=" + LoanInfoId + ", customer=" + customer + ", loanAmount=" + loanAmount
				+ ", registerDate=" + registerDate + ", loanTermYear=" + loanTermYear + ", interestRate=" + interestRate
				+ ", description=" + description + ", creditScore=" + creditScore + ", status=" + status
				+ ", createdUser=" + createdUser + ", createdDate=" + createdDate + ", updateUser=" + updateUser
				+ ", updateDate=" + updateDate + "]";
	}
	
	
	

}
