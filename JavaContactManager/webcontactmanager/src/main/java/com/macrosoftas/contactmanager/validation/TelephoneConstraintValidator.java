package com.macrosoftas.contactmanager.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TelephoneConstraintValidator implements ConstraintValidator<Telephone, String> {

	
	@Override
	public void initialize(Telephone String) { }

	@Override
	public boolean isValid(String phoneField, ConstraintValidatorContext cxt) {
		if(phoneField == null) {
			return false;
		}
		return phoneField.matches("[0-9()-]*");
	}

}
