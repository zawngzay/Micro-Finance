package com.mfi.customvalidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;  

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
	@Constraint(validatedBy = PhoneValidator.class)
	@Target( { ElementType.METHOD, ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Phone {
	    String message() default "Invalid phone number";
	    Class<?>[] groups() default {};
	    Class<? extends Payload>[] payload() default {};
	}


