package com.mfi.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roleId;
	@NotEmpty(message ="role name should not be null")
	private String roleName;
	@NotEmpty(message = "Position should not be null")
	private String rolePosition;
	private int createdUser;
	private Date createdDate;
	private int updatedUser;
	private Date updatedDate;

	public Role() {
	
	}

	public Role(int roleId, String roleName, int createdUser, Date createdDate, int updatedUser, Date updatedDate) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.createdUser = createdUser;
		this.createdDate = createdDate;
		this.updatedUser = updatedUser;
		this.updatedDate = updatedDate;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public int getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(int updatedUser) {
		this.updatedUser = updatedUser;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getRolePosition() {
		return rolePosition;
	}

	public void setRolePosition(String rolePosition) {
		this.rolePosition = rolePosition;
	}

	
}
