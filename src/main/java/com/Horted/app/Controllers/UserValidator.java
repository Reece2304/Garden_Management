package com.Horted.app.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.Horted.app.Domain.Users;
import com.Horted.app.Repository.UserRepo;

/**
 * This class contains validation for the sign up page
 * @author Reece Cripps (rc424)
 **/

public class UserValidator implements Validator {
	@Autowired
	UserRepo Urepo;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Users.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		/*
		 * Make sure the fields aren't empty
		 */
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "", "Please enter a name!");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "", "Please enter an email!");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "", "Please enter a username!");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "", "Please enter a password!");
			
	
		Users user = (Users) target;

		//check if the password and confirm passwords match
		if(!user.getPassword().equals(user.getconfirmPass()))
		{
			errors.rejectValue("confirmPass", "", "Your passwords do not match");
		}
		
		if(user.getPassword().length() < 8 || user.getPassword().length() > 16)
		{
			errors.rejectValue("password", "", "Length of password should be between 8 and 16 characters.");
		}
		
	}

}
