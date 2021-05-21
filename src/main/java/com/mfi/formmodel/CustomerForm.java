package com.mfi.formmodel;

import java.sql.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.mfi.customvalidator.NRC;
import com.mfi.customvalidator.Phone;

public class CustomerForm {
	
	
	
	private String customerCode;
	@NotBlank(message="Customer Name should not be null")
	private String name;
	@NotEmpty(message="NRC should not be null")
	@NRC(message="Invalid NRC format")
	private String nrc;
	@NotEmpty(message="Phone No should not be null")
	@Phone(message="Invalid Phone Number")
	private String phone;
	@NotEmpty(message="DOB should not be null")
	private String dob;
	@Email(message="Enter valid email format")
	@NotEmpty(message = "Email should not be null")
	private String email;
	@NotEmpty(message = "Gender should not be null")
	private String gender;
	@NotEmpty(message="Address should not be null")
	private String address;
	@NotEmpty(message="Current Job should not be null")
	private String currentJob;
	@NotEmpty(message="Position should not be null")
	private String positon;
	@NotEmpty(message="Company Name should not be null")
	private String companyName;
	@DecimalMin("100000.00")
	private double monthlyIncome;
	private boolean delStatus;
	private int createdUser;
	private Date createdDate;
	private int updateUser;
	private Date updateDate;
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNrc() {
		return nrc;
	}
	public void setNrc(String nrc) {
		this.nrc = nrc;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCurrentJob() {
		return currentJob;
	}
	public void setCurrentJob(String currentJob) {
		this.currentJob = currentJob;
	}
	public String getPositon() {
		return positon;
	}
	public void setPositon(String positon) {
		this.positon = positon;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public double getMonthlyIncome() {
		return monthlyIncome;
	}
	public void setMonthlyIncome(double monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}
	public boolean isDelStatus() {
		return delStatus;
	}
	public void setDelStatus(boolean delStatus) {
		this.delStatus = delStatus;
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
	
	
	
	
	

}