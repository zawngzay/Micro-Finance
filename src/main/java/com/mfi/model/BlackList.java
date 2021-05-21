package com.mfi.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import com.mfi.customvalidator.NRC;

@Entity
public class BlackList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int blackListId;
	@NotEmpty(message="Name should not be null")
	private String name;
	@NotEmpty(message="NRC should not be null")
	@NRC(message="Invalid NRC format")
	private String nrc;
	@NotEmpty(message="Address should not be null")
	private String address;
	@NotEmpty(message="Father Name should not be null")
	private String fatherName;
	@NotEmpty(message="Remark should not be null")
	private String remark;
	
	private int createdUser;
	private Date createdDate;
	private int updateUser;
	private Date updateDate;

	public BlackList() {
		super();
	}

	public BlackList(int blackListId, String name, String nrc, String address, String fatherName, int createdUser,
			Date createdDate, int updateUser, Date updateDate,String remark) {
		super();
		this.blackListId = blackListId;
		this.name = name;
		this.nrc = nrc;
		this.address = address;
		this.fatherName = fatherName;
		this.createdUser = createdUser;
		this.createdDate = createdDate;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
		this.remark=remark;
	}

	public int getBlackListId() {
		return blackListId;
	}

	public void setBlackListId(int blackListId) {
		this.blackListId = blackListId;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}