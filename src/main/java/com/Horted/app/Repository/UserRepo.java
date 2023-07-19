package com.Horted.app.Repository;

import org.springframework.data.repository.CrudRepository;

import com.Horted.app.Domain.Users;

public interface UserRepo extends CrudRepository<Users, String> {

	public Users findByUsername(String username);
	
	public Users findByEmail(String email);

}
