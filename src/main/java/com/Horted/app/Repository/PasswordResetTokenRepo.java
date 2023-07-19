package com.Horted.app.Repository;

import org.springframework.data.repository.CrudRepository;

import com.Horted.app.Domain.Users;
import com.Horted.app.Domain.passwordResetToken;

public interface PasswordResetTokenRepo extends CrudRepository<passwordResetToken, Integer> {
	public passwordResetToken findByToken(String token);
	
	public passwordResetToken findByUser(Users user);
}
