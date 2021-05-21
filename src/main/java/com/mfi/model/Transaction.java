package com.mfi.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;



@Entity
public class Transaction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int transactionId;
	@NotEmpty(message="Account number should not be null")
	private String accountNumber;
	private int coaId;
	private Date date;
	
	@NotNull(message="Deposit Amount should not be null")
	private Double amount;
	private String transactionType;
	private String accountName;
	private String nrc;
	private String narration;
	private int createdUser;
	private Date createdDate;
	private int updateUser;
	private Date updateDate;
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public int getCoaId() {
		return coaId;
	}
	public void setCoaId(int coaId) {
		this.coaId = coaId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getNrc() {
		return nrc;
	}
	public void setNrc(String nrc) {
		this.nrc = nrc;
	}
	public String getNarration() {
		return narration;
	}
	public void setNarration(String narration) {
		this.narration = narration;
	}
	public int getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(int createdUser) {
		this.createdUser = createdUser;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(int updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Transaction(int transactionId, String accountNumber, int coaId, Date date, Double amount,
			String transactionType, String accountName, String nrc, String narration, int createdUser, Date createdDate,
			int updateUser, Date updateDate) {
		super();
		this.transactionId = transactionId;
		this.accountNumber = accountNumber;
		this.coaId = coaId;
		this.date = date;
		this.amount = amount;
		this.transactionType = transactionType;
		this.accountName = accountName;
		this.nrc = nrc;
		this.narration = narration;
		this.createdUser = createdUser;
		this.createdDate = createdDate;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
	}
	public Transaction() {
		super();
	}
	
	

}