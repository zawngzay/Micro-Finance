package com.mfi.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NrcValidator implements ConstraintValidator<NRC, String> {

	@Override
	public void initialize(NRC nrc) {
	}

	@Override
	public boolean isValid(String nrc, ConstraintValidatorContext cxt) {
		

		if (nrc.isEmpty()) {
			return true;
		} else {
			try {
				// FIRST TWO DIGIT

				// split the string by the '/' and take the value from the first array's
				// partition.
				// while splitting array the first value will go to array [0] and second value
				// will go to array[1].
				// as all is spring buffer, have to change that to string.
				// the special character used to split the string will not include in the split
				// value.
				String statenum = new StringBuffer(nrc.split("/")[0]).toString();

				// '/'
				String slashone = Character.toString(nrc.charAt(1));
				String slashtwo = Character.toString(nrc.charAt(2));

				// TOWNSHIP
				String township = new StringBuffer(nrc.split("[(]")[0].split("/")[1]).toString();

				// TYPE
				String type = new StringBuffer(new StringBuffer(nrc.split("[(]")[1]).toString().split("[)]")[0]).toString();

				// LAST DIGITS
				String digit = new StringBuffer(nrc.split("[)]")[1]).toString();
				
				return 	nrc.length() >= 14 && nrc.length() <= 22&& 
						(slashone.equals("/")||slashtwo.equals("/"))&&
						statenum.matches("[0-9]+")&&Integer.valueOf(statenum)<=14 &&
				  		township.matches("[a-zA-Z]+")&&township.length()>=3&&township.length()<=9&&
				  		nrc.contains("(")&&(type.equals("N")||type.equals("M")||type.equals("AC")||type.equals("NC")||type.equals("C"))&&nrc.contains(")")&&
				  		digit.matches("[0-9]+")&&digit.length()==6;
			} catch (Exception e) {
				return false;

			}

		}

	}

}
