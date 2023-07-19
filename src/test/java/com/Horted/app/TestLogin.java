package com.Horted.app;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.Horted.app.Domain.Role;
import com.Horted.app.Domain.Users;
import com.Horted.app.Repository.UserRepo;

//@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration
@TestInstance(Lifecycle.PER_CLASS)
public class TestLogin extends HortedGardeningAppApplicationTests{

	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	  
	  @BeforeEach
	  public void setup() {
	    mockMvc = MockMvcBuilders.webAppContextSetup(context)
	            .apply(springSecurity())
	            .build();
	  }
	  
	  private static boolean UserCreated = false;
	  @Autowired
	  private PasswordEncoder pe;
	  
	  @Autowired
	  private UserRepo Urepo;
	  
	  @BeforeAll
		public void init() {
			if (!UserCreated) {
				Users U = new Users();
				U.setUsername("User");
				U.setFullName("Reece");
				U.setRole(Role.Free);
				U.setPassword(pe.encode("password"));
				
				Urepo.save(U);
				UserCreated = true;
				
			}		
		}

	  
	  @Test
		public void loginAuthenticationControllerLoginForm() throws Exception {
			this.mockMvc.perform(get("/login-form").secure(true)).andExpect(status().is(200))
					.andExpect(view().name("login"));
		}
	  

	  @Test
		public void loginAuthenticationControllerSuccessLoginUser() throws Exception {
			this.mockMvc.perform(get("/success-login").secure(true).with(user("User").roles("User")))
					.andExpect(status().is(302)).andExpect(redirectedUrl("/Userloggedin"));
		}

}