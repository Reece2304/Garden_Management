package com.Horted.app.Repository;

import org.springframework.data.repository.CrudRepository;

import com.Horted.app.Domain.Users;
import com.Horted.app.Domain.verificationToken;

public interface VerificationTokenRepo extends CrudRepository<verificationToken, Integer> {
	public verificationToken findByToken(String token);
	public verificationToken findByUser(Users user);
}
