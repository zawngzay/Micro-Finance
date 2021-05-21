package com.mfi.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements 
ConstraintValidator<Password, String> {

  @Override
  public void initialize(Password pw) {
  }

  @Override
  public boolean isValid(String pw,
    ConstraintValidatorContext cxt) {
	  if (pw.isEmpty()) {
		  	System.out.println("empty");
			return true;
		}else {
			try {
					System.out.println("condition");
				  return  pw.matches(".*[0-9].*")&&pw.matches(".*[a-zA-Z].*")&&pw.length()>=8;
					//return pw.contains("^[A-Z0-9]+$")&&pw.length()>=8;
			  }catch(Exception e) {
				  System.out.println("exception");
				  return false;
			  }
		}
	  
      
  }

}