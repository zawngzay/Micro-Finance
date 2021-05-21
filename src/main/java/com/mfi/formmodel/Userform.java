package com.mfi.formmodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.mfi.customvalidator.NRC;
import com.mfi.customvalidator.Password;
import com.mfi.customvalidator.Phone;
import com.mfi.model.Permission;

public class Userform {
	
	
	private int user_id;
	@NotBlank(message="Name should not be null")
	private String name;
	@NotEmpty(message="Password should not be null")
	@Password
	private String password;
	@Transient
	@Password(message="Confirm Password must be combination of letters & numbers and at least 8 character in length")
	@NotEmpty(message="Confirm Password should not be null")
	private String conPass;
	@NotEmpty(message = "Phone No should not be null")
	@Phone(message="Invalid Phone Number")
	private String phone;
	@Email(message="Invalid email format")
	@NotBlank(message="Email should not be null")
	private String email;
	@NotEmpty(message = "NRC should not be null")
	@NRC(message="Invalid NRC format")
	private String nrc;
	@NotEmpty(message="Position should not be null")
	private String position;
	//public String permission;
	@NotEmpty(message="Persmission should not be null")
	private List<String> permission = new ArrayList<String>();
	private int createdUser;
	private Date createdDate;
	private int updateUser;
	private Date updateDate;
	
	
	@Min(value=1,message = "Role Name should not be null")
	private int role;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConPass() {
		return conPass;
	}

	public void setConPass(String conPass) {
		this.conPass = conPass;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	
	 
	 

	

	

	
	
	 
	

	public List<String> getPermission() {
		return permission;
	}

	public void setPermission(List<String> permission) {
		this.permission = permission;
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

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}



}