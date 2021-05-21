package com.mfi.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements 
ConstraintValidator<Phone, String> {

  @Override
  public void initialize(Phone ph) {
  }

  @Override
  public boolean isValid(String ph,
    ConstraintValidatorContext cxt) {
	  if (ph.isEmpty()) {
			return true;
		}else {
			try {
				  return  new StringBuffer(ph).deleteCharAt(0).toString().matches("[0-9]+")&&
						  (Character.toString(ph.charAt(0)).equals("0")||ph.substring(0, 3).equals("+95"))
					        && (ph.length() >= 8) && (ph.length() <= 13);
			  }catch(Exception e) {
				  return false;
			  }
		}
	  
      
  }

}