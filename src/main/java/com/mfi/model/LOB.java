package com.mfi.model;


public class LOB {
	private int paymentTermNo;

	private String nrc;
	private String customerName;
	
	private int loanInfo_id;
	private int allLoan;
	private Double complete;
	private Double outstanding;
	public int getPaymentTermNo() {
		return paymentTermNo;
	}
	public void setPaymentTermNo(int paymentTermNo) {
		this.paymentTermNo = paymentTermNo;
	}
	public String getNrc() {
		return nrc;
	}
	public void setNrc(String nrc) {
		this.nrc = nrc;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getLoanInfo_id() {
		return loanInfo_id;
	}
	public void setLoanInfo_id(int loanInfo_id) {
		this.loanInfo_id = loanInfo_id;
	}
	public int getAllLoan() {
		return allLoan;
	}
	public void setAllLoan(int allLoan) {
		this.allLoan = allLoan;
	}
	public Double getComplete() {
		return complete;
	}
	public void setComplete(Double complete) {
		this.complete = complete;
	}
	public Double getOutstanding() {
		return outstanding;
	}
	public void setOutstanding(Double outstanding) {
		this.outstanding = outstanding;
	}
	
	
	
}
