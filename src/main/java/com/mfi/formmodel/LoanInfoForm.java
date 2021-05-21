package com.mfi.formmodel;

import java.sql.Date;
import java.time.LocalDate;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;


public class LoanInfoForm {

	
	private Integer LoanInfoId;

	private String customerCode;
	@NotNull(message="Loan Amount should not be null")
	@DecimalMin("500000.00")
	private Double loanAmount;
	@NotEmpty(message="Register Date should not be null")
	private String registerDate;
	@NotNull(message="Loan Term in Years should not be null")
	private Integer loanTermYear;
	private double interestRate;
	private String description;
	private Integer creditScore;
	private String status;
	private int createdUser;
	private LocalDate createdDate;
	private int updateUser;
	private LocalDate updateDate;	

	public Integer getLoanInfoId() {
		return LoanInfoId;
	}
	public void setLoanInfoId(Integer loanInfoId) {
		LoanInfoId = loanInfoId;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public Double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	public Integer getLoanTermYear() {
		return loanTermYear;
	}
	public void setLoanTermYear(Integer loanTermYear) {
		this.loanTermYear = loanTermYear;
	}
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
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
	
	
	
	
}