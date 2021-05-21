package com.mfi.formmodel;

import java.sql.Date;
import java.time.LocalDate;


import com.mfi.model.LoanInfo;

public class LoanScheduleForm {

	private int paymentTermNo;
	private Date repaymentDate;
	private Double beginningBalance;
	private Date dueDate;
	private Double totalRepaymentAmount;
	private Double interestRate;
	private Double lateFees;
	private Double endingBalance;
	private String status;
	private int createdUser;
	private LocalDate createdDate;
	private int updateUser;
	private LocalDate updateDate;
	private LoanInfo loanInfo_id;
	private Double principal;
	
	public LoanScheduleForm() {
		super();
	}


	public LoanScheduleForm(Integer paymentTermNo, Date repaymentDate, Double beginningBalance, Date dueDate,
			Double totalRepaymentAmount, Double interestRate, Double lateFees, Double endingBalance, String status,
			int createdUser, LocalDate createdDate, int updateUser, LocalDate updateDate, LoanInfo loanInfo_id,Double principal) {
		super();
		this.paymentTermNo = paymentTermNo;
		this.repaymentDate = repaymentDate;
		this.beginningBalance = beginningBalance;
		this.dueDate = dueDate;
		this.totalRepaymentAmount = totalRepaymentAmount;
		this.interestRate = interestRate;
		this.lateFees = lateFees;
		this.endingBalance = endingBalance;
		this.status = status;
		this.createdUser = createdUser;
		this.createdDate = createdDate;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
		this.loanInfo_id = loanInfo_id;
		this.principal = principal;
	}


	public Integer getPaymentTermNo() {
		return paymentTermNo;
	}


	public void setPaymentTermNo(Integer paymentTermNo) {
		this.paymentTermNo = paymentTermNo;
	}


	public Date getRepaymentDate() {
		return repaymentDate;
	}


	public void setRepaymentDate(Date repaymentDate) {
		this.repaymentDate = repaymentDate;
	}


	public Double getBeginningBalance() {
		return beginningBalance;
	}


	public void setBeginningBalance(Double beginningBalance) {
		this.beginningBalance = beginningBalance;
	}


	public Date getDueDate() {
		return dueDate;
	}


	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


	public Double getTotalRepaymentAmount() {
		return totalRepaymentAmount;
	}


	public void setTotalRepaymentAmount(Double totalRepaymentAmount) {
		this.totalRepaymentAmount = totalRepaymentAmount;
	}


	public Double getInterestRate() {
		return interestRate;
	}


	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}


	public Double getLateFees() {
		return lateFees;
	}


	public void setLateFees(Double lateFees) {
		this.lateFees = lateFees;
	}


	public Double getEndingBalance() {
		return endingBalance;
	}


	public void setEndingBalance(Double endingBalance) {
		this.endingBalance = endingBalance;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	

	public LocalDate getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}





	public int getCreatedUser() {
		return createdUser;
	}


	public void setCreatedUser(int createdUser) {
		this.createdUser = createdUser;
	}


	public int getUpdateUser() {
		return updateUser;
	}


	public void setUpdateUser(int updateUser) {
		this.updateUser = updateUser;
	}


	public void setPaymentTermNo(int paymentTermNo) {
		this.paymentTermNo = paymentTermNo;
	}


	public LocalDate getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}


	public LoanInfo getLoanInfo_id() {
		return loanInfo_id;
	}


	public void setLoanInfo_id(LoanInfo loanInfo_id) {
		this.loanInfo_id = loanInfo_id;
	}


	public Double getPrincipal() {
		return principal;
	}


	public void setPrincipal(Double principal) {
		this.principal = principal;
	}
	
	
	
	
}
