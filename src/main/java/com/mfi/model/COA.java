package com.mfi.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class COA {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int CoaId;
	private int bankId;
	private String glType;
	private String accountNumber;
	private Double amount;
	private int createdUser;
	private Date createdDate;
	private int updateUser;
	private Date updateDate;

	public COA() {
		super();
	}

	public int getCoaId() {
		return CoaId;
	}

	public void setCoaId(int coaId) {
		CoaId = coaId;
	}

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public String getGlType() {
		return glType;
	}

	public void setGlType(String glType) {
		this.glType = glType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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

	public COA(int coaId, int bankId, String glType, String accountNumber, Double amount, int createdUser,
			Date createdDate, int updateUser, Date updateDate) {
		super();
		CoaId = coaId;
		this.bankId = bankId;
		this.glType = glType;
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.createdUser = createdUser;
		this.createdDate = createdDate;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
	}
	
	
}
