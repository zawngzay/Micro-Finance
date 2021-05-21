package com.mfi.customvalidator;



import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;  

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
	@Constraint(validatedBy = NrcValidator.class)
	@Target( { ElementType.METHOD, ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface NRC {
	    String message() default "Invalid NRC";
	    Class<?>[] groups() default {};
	    Class<? extends Payload>[] payload() default {};
	}



