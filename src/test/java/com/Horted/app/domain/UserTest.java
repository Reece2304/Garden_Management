package com.Horted.app.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Horted.app.Domain.Plants;
import com.Horted.app.Domain.Role;
import com.Horted.app.Domain.Users;
import com.Horted.app.Repository.PasswordResetTokenRepo;
import com.Horted.app.Repository.PlantRepo;
import com.Horted.app.Repository.UserRepo;
import com.Horted.app.Repository.VerificationTokenRepo;


@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration
@TestInstance(Lifecycle.PER_CLASS)
class UserTest {

	@Autowired
	UserRepo Urepo;
	
	@Autowired
	PlantRepo Prepo;

	@Autowired
	private PasswordEncoder pe;

	@Autowired
	private PasswordResetTokenRepo PTRrepo;

	@Autowired
	private VerificationTokenRepo Vrepo;

	@Autowired
	private JavaMailSender mailSender;
		
	private static boolean UserCreated = false;
	  @BeforeAll
		public void init() {
			if (!UserCreated) {
				HashMap<String, Integer> borderCount = new HashMap<String, Integer>();
				borderCount.put("top", 0);
				borderCount.put("left", 0);
				borderCount.put("right", 0);
				borderCount.put("bottom", 0);
				Users U = new Users();
				U.setUsername("User");
				U.setFullName("Reece");
				U.setRole(Role.Free);
				U.setPassword(pe.encode("password"));
				U.setEmail("be@gmail.com");
				U.setVerified(false);
				U.setRemoved(false);
				U.setBorderCount(borderCount);
				U.setconfirmPass(pe.encode("password"));
				U.setnewPass(pe.encode("password"));
				Urepo.save(U);
				UserCreated = true;
				
			}		
		}
	
	@Test
	public void UserRepo() throws Exception //check user exists
	{
		Users U = Urepo.findByUsername("User");
		assert(U!= null);
		assert(!U.getPassword().equals("password"));
		assert(!U.getconfirmPass().equals("password"));
		assert(!U.getnewPass().equals("password"));
		assert(U.getBorderCount() != null);
		assert(U.getFullName() != null);
		assert(U.getUsername() != null);
		assert(U.getEmail() != null);
		assert(U.getRole() == Role.Free);
		assert(U.isremoved() == false);
		assert(U.isVerified() == false);
	}
	
	@Test
	public void UserPassword() throws Exception //check user's password is encrypted
	{
		
	}
	
	@Test
	public void UserPlants() throws Exception
	{
		Plants plant = new Plants();
		Prepo.save(plant);
		Users U = Urepo.findByUsername("User");
		List <Plants> pList = new ArrayList<>();
		pList.add(plant);
		U.setPlants(pList);
		Urepo.save(U);
		assert(U.getPlants().get(0) == plant);
		
	}
}
