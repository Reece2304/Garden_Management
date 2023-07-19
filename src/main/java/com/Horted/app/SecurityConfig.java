package com.Horted.app;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.Horted.app.Domain.Role;
import com.Horted.app.Repository.UserRepo;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserRepo Urepo;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.requiresChannel().anyRequest().requiresSecure().and().formLogin()
				// to show the page where we enter login credentials
				.loginPage("/login-form")
				// to process authentication: /login handler method implemented by Spring
				// Security
				.loginProcessingUrl("/login")
				// where to go after successful login
				.defaultSuccessUrl("/success-login", true) // the second parameter is for enforcing this url always
				// to show an error page if the authentication failed
				.failureHandler(new AuthenticationFailureHandler() {
					// pass the correct error message to the page
					public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
							AuthenticationException exception) throws IOException, ServletException {
						String username = request.getParameter("username");
						if (Urepo.findByUsername(username) == null) {
							response.sendRedirect("/login-form?error=username");
						} else {
							response.sendRedirect("/login-form?error=password");
						}
					}
				})
				// everyone can access these requests
				.permitAll().and().authorizeRequests().antMatchers("/Signup").permitAll() // allows non logged in users
																							// to access the sign up
																							// page
				.and().authorizeRequests().antMatchers("/ChangeCredentials").permitAll().and().authorizeRequests()
				.antMatchers("/login-form/**").permitAll().and().authorizeRequests().antMatchers("/Horted").permitAll()
				.and().authorizeRequests().antMatchers("/resetPasswordEmail").permitAll().and().authorizeRequests()
				.antMatchers("/SignupComplete").permitAll().and().authorizeRequests().antMatchers("/changePassword/**")
				.permitAll().and().authorizeRequests().antMatchers("/confirmReset/**").permitAll().and()
				.authorizeRequests().antMatchers("/Verify/**").permitAll().and().authorizeRequests()
				.antMatchers("/resendVerification/**").permitAll().and().authorizeRequests()
				.antMatchers("/resendReset/**").permitAll().and().authorizeRequests()
				.antMatchers("/css/**", "/images/**").permitAll() // allows non logged in users to still use CSS and
																	// images

				.and().logout().invalidateHttpSession(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login-form").permitAll().and().authorizeRequests().antMatchers("/a/**")
				.hasRole(Role.Admin.name()).antMatchers("/u/**")
				.hasAnyRole(Role.Free.name(), Role.Premium.name(), Role.Admin.name()).antMatchers("/u/log/home")
				.hasAnyRole(Role.Free.name(), Role.Premium.name(), Role.Admin.name()).antMatchers("/u/log/Free")
				.hasAnyRole(Role.Free.name(), Role.Admin.name()).antMatchers("/u/log/**")
				.hasAnyRole(Role.Admin.name(), Role.Premium.name()).anyRequest().authenticated() // all requests ABOVE
																									// this statement
																									// require
																									// authentication
				.and()
				// to redirect the user when trying to access a resource to which access is not
				// granted
				.exceptionHandling().accessDeniedPage("/access-denied");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

}