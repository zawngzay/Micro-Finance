package com.mfi.formmodel;

import javax.validation.constraints.NotEmpty;

public class UserChangePassword {

	@NotEmpty(message = "New password should not be null")
	private String newPassword;
	@NotEmpty(message = "Confirm password should not be null")
	private String confirmPassword;
	
	
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
}
