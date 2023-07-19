package com.Horted.app;

import java.security.Principal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Horted.app.Controllers.UserValidator;
import com.Horted.app.Domain.Role;
import com.Horted.app.Domain.Users;
import com.Horted.app.Domain.passwordResetToken;
import com.Horted.app.Domain.verificationToken;
import com.Horted.app.Repository.PasswordResetTokenRepo;
import com.Horted.app.Repository.UserRepo;
import com.Horted.app.Repository.VerificationTokenRepo;
import com.Horted.app.Services.Utility;

/**
 * This class contains all endpoints for the login and signup page
 * 
 * @author Reece Cripps (rc424)
 **/

@Controller
public class Login {

	@Autowired
	UserRepo Urepo;

	@Autowired
	private PasswordEncoder pe;

	@Autowired
	private PasswordResetTokenRepo PTRrepo;

	@Autowired
	private VerificationTokenRepo Vrepo;

	@Autowired
	private JavaMailSender mailSender;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new UserValidator());

	}

	@RequestMapping("/")
	public String index() {
		return "redirect:/Horted";
	}

	// These functions handle what happens to the user on login
	@RequestMapping(value = "/Userloggedin", method = RequestMethod.GET)
	public String Ulogged(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("name", auth.getName()); // passes the username to the webpage
		return "redirect:/u/home";
	}

	@RequestMapping(value = "/access-denied", method = RequestMethod.GET)
	public String error() {
		return "denied";
	}

	@RequestMapping(value = "/login-form", method = RequestMethod.GET)
	public String loginForm(@RequestParam(required = false) String error, Model model) {
		if (error != null) {
			if (error.equals("username")) {
				error = "Username not found please try again";
				model.addAttribute("error", error);

			}
			if (error.equals("password")) {
				error = "password is incorrect please try again";
				model.addAttribute("error", error);
			}
		}
		return "login/login";
	}

	@RequestMapping(value = "/user-logout", method = RequestMethod.GET)
	public String logout(Model model) {
		model.addAttribute("logout", true);
		return "ClientsSite";
	}

	@RequestMapping(value = "/Horted", method = RequestMethod.GET)
	public String home() {
		return "ClientsSite";
	}

	// redirect users to the correct homepage (admin or standard user)
	@RequestMapping(value = "/success-login", method = RequestMethod.GET)
	public String mainPage(Principal principal, Model model) {
		Users u = Urepo.findByUsername(principal.getName());
		if (u.isVerified()) {
			if (u.getRole() == Role.Admin) {
				return "redirect:/a/AdminHome";
			} else {
				return "redirect:/Userloggedin";
			}
		} else {
			String error = "Account has not been verified please check your emails";
			model.addAttribute("error", error);
			return "login/login";
		}

	}

	@ModelAttribute("user")
	public Users createUserModel() {
		return new Users();
	}

	@RequestMapping(value = "/Signup", method = RequestMethod.GET)
	public String Signup(Model model) {
		model.addAttribute("Users", new Users()); // A new user to be poplulated with the form data
		return "login/sign-up";
	}

	/**
	 * Controller function that checks if the user signup details are correct and
	 * then saves them in the system
	 * This then creates a verification token and emails the user
	 * 
	 * @param users
	 * @param result
	 * @param model
	 * @return "login/CompleteSignup" returns a login page
	 */

	@RequestMapping(value = "/SignupComplete", method = RequestMethod.POST)
	public String SignupComplete(@Valid @ModelAttribute("user") Users users, BindingResult result, Model model,
			final HttpServletRequest request) {
		if (Urepo.findByUsername(users.getUsername()) != null) {
			result.rejectValue("username", "", "Username Already exists");
		}
		if (Urepo.findByEmail(users.getEmail()) != null) {
			result.rejectValue("email", "", "email already exists");
		}
		// if there are errors then refresh the page
		if (result.hasErrors()) {
			return "login/sign-up";
		} else {
			// encode the users password and then save them in the database.
			users.setPassword(pe.encode(users.getPassword()));
			users.setPassword(pe.encode(users.getconfirmPass()));
			users.setRole(Role.Free);
			HashMap<String, Integer> borderCount = new HashMap<String, Integer>();
			borderCount.put("top", 0);
			borderCount.put("left", 0);
			borderCount.put("right", 0);
			borderCount.put("bottom", 0);
			users.setBorderCount(borderCount);
			Urepo.save(users);
			model.addAttribute("Users", users);
			String token = UUID.randomUUID().toString();
			createVerificationToken(users, token);
			createVerificationEmail(token, users, request);
			return "login/CompleteSignup";
		}
	}

	/**
	 * finds the verification token based on the path variable and then recreates
	 * the email and resends it to the user
	 * 
	 * @param username
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/resendVerification/{username}")
	public String resendVerification(@PathVariable String username, Model model, final HttpServletRequest request) {
		try {
			Users u1 = Urepo.findByUsername(username);
			verificationToken t1 = Vrepo.findByUser(u1);
			createVerificationEmail(t1.getToken(), u1, request);
			model.addAttribute("Users", u1);
			String sent = "email resent";
			model.addAttribute("sent", sent);
			return "login/resendComplete";
		} catch (Exception e) {
			return "/";
		}
	}

	/**
	 * finds the reset token based on the path variable and then recreates the email
	 * and resends it to the user
	 * 
	 * @param username
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/resendReset/{username}")
	public String resendReset(@PathVariable String username, Model model, final HttpServletRequest request) {
		try {
			Users u1 = Urepo.findByUsername(username);
			passwordResetToken t1 = PTRrepo.findByUser(u1);
			createResetTokenEmail(t1.getToken(), u1, request);
			model.addAttribute("Users", u1);
			String sent = "email resent";
			model.addAttribute("sent", sent);
			return "login/resendComplete";
		} catch (Exception e) {
			return "/";
		}
	}

	/**
	 * Once the user clicks the email verification link this method will find the
	 * user and set their verified to true so they can now login
	 * 
	 * @param token
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/Verify/{token}")
	public String verifyUser(@PathVariable String token, Model model) {
		try {
			verificationToken token2 = Vrepo.findByToken(token);
			Users User = token2.getUser();
			User.setVerified(true);
			Urepo.save(User);
			Vrepo.delete(token2); // allows users to be deleted once the token is deleted
			model.addAttribute("username", User.getUsername());
			return "login/userVerified";
		} catch (Exception e) {
			String UserEmail = "Something Went wrong try again";
			model.addAttribute("email", UserEmail);
			return "login/login";
		}
	}

	@RequestMapping(value = "/ChangeCredentials")
	public String ChangeCredentials() {
		return "login/Forgot-U-P";
	}

	/**
	 * Finds the user by email and then sends them an email with a unique password
	 * reset token
	 * 
	 * @param UserEmail
	 * @param model
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/resetPasswordEmail")
	public String resetPassword(@Valid @RequestParam("email") String UserEmail, Model model,
			final HttpServletRequest request) {
		Users User = Urepo.findByEmail(UserEmail);
		if (User == null) {
			UserEmail = "Email not found Try again";
			model.addAttribute("email", UserEmail);
			return "login/Forgot-U-P";
		} else if (PTRrepo.findByUser(User) != null) {
			UserEmail = "You already have a password reset token, check your emails";
			model.addAttribute("email", UserEmail);
			return "login/Forgot-U-P";
		}
		String token = UUID.randomUUID().toString();
		createPasswordResetToken(User, token);
		createResetTokenEmail(token, User, request);
		model.addAttribute("User", User);
		return "login/resetLink";
	}

	/**
	 * Gets the user's token sent in the email and finds the user related to that
	 * token once found it calls the next page for the user to change their password
	 * 
	 * @param token
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/changePassword/{token}")
	public String ChangePassword(@PathVariable String token, Model model) {
		try {
			Users User = PTRrepo.findByToken(token).getUser();
			model.addAttribute("email", User.getEmail());
			model.addAttribute("Error", "");
			return "login/changePassword";
		} catch (Exception e) {
			String UserEmail = "This token has already been used or is invalid, try again";
			model.addAttribute("email", UserEmail);
			return "login/Forgot-U-P";
		}
	}

	/**
	 * gets the user related to their email and then updates their password with the
	 * one provided
	 * 
	 * @param email
	 * @param Pass1
	 * @param Pass2
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/confirmReset/{email}")
	public String ConfirmPassword(@PathVariable String email, @RequestParam("Pass1") String Pass1,
			@RequestParam("Pass2") String Pass2, Model model) {
		if (!Pass1.equals(Pass2)) {
			String Error = "Passwords do not match";
			model.addAttribute("Error", Error);
			return "login/changePassword";
		}
		if (Pass1.length() == 0) {
			String Error = "Please Enter a password";
			model.addAttribute("Error", Error);
			return "login/changePassword";
		}
		if (Pass1.length() < 8 || Pass1.length() > 16) {
			String Error = "Please Enter a password between 8 and 16 characters";
			model.addAttribute("Error", Error);
			return "login/changePassword";
		}
		Users User = Urepo.findByEmail(email);
		User.setPassword(pe.encode(Pass1));
		User.setconfirmPass(pe.encode(Pass2));
		passwordResetToken Token = PTRrepo.findByUser(User);
		PTRrepo.delete(Token);
		Urepo.save(User);
		return "login/confirmReset";
	}

	// Calls the constructor of password token to create a new token (related to the
	// user provided)
	public void createPasswordResetToken(Users user, String token) {
		passwordResetToken Token = new passwordResetToken(token, user);
		PTRrepo.save(Token);
	}

	public void createVerificationToken(Users user, String token) {
		verificationToken Token = new verificationToken(token, user);
		Vrepo.save(Token);
	}

	private void createResetTokenEmail(String token, Users user, HttpServletRequest request) {
		String url = Utility.getSiteURL(request) + "/changePassword/" + token;
		String message = "<H2> Your Username is: " + "<b>" + user.getUsername() + "</b>"
				+ MessageFormat.format(", Click <a href=\"{0}\">here</a> to reset your password </H2>", url);
		createEmail("Reset Password", message, user);
	}

	private void createVerificationEmail(String token, Users user, HttpServletRequest request) {

		String url = Utility.getSiteURL(request) + "/Verify/" + token;
		String message = MessageFormat.format("<H2> Click <a href=\"{0}\">here</a> to verify your account </H2>", url);
		createEmail("Verify your account", message, user);
	}

	private void createEmail(String subject, String body, Users user) {
		try {

			MimeMessage message = mailSender.createMimeMessage();

			message.setSubject(subject);
			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true);
			helper.setFrom("gardensdigital@gmail.com");
			helper.setTo(user.getEmail());
			helper.setText(body, true);

			// This mail has 2 part, the BODY and the embedded image
			MimeMultipart multipart = new MimeMultipart("related");

			// create the html message
			BodyPart messageBodyPart = new MimeBodyPart();
			String text = "<html>" + "<head>" + "<style type =\"text/css\">" + "" + "table, th, td {"
					+ "  border-collapse: collapse;" + "  background-color: white;" + "   margin-left: auto;"
					+ "  margin-right: auto;" + "}" + "" + "th, td {" + "  padding: 10px;" + "}" + ".Form_title {"
					+ "  font-family: Arial;" + "  font-size: 40px;" + "  color: #333333;" + "  line-height: 1.2;"
					+ "  text-align: center;" + "  width: 100%;" + "  display: block;" + "  padding-bottom: 54px;" + "}"
					+ ".container_login {" + "  width: 90%;  " + "  height: 90%; " + "  min-height: 100vh;"
					+ "  display: flex;" + "  flex-wrap: wrap;" + "  justify-content: center;"
					+ "  align-items: center;" + "  padding: 15px;"
					+ "  background-image: url(\"https://horted.co.uk/wp-content/uploads/2018/04/104_0109.jpg\");"
					+ "  background-color: #cccccc;" + "  background-position: center;"
					+ "  background-repeat: no-repeat;" + "  background-size: cover;" + "  position: relative;" + "}"
					+ ".container2_login {" + "  height: 50%;" + "  width: 50%;" + "  background: #fff;"
					+ "  border-radius: 10px;" + "  padding: 50px 130px 33px 95px;" + "   margin-left: auto;"
					+ "  margin-right: auto;" + " margin-top: auto;" + " margin-bottom: auto;" + "}" + "</style>"
					+ "</head>" + "<body>" + "		<div class=\"container_login\">"
					+ "			<div class=\"container2_login\">" + "					<span class=\"Form_title\">"
					+ "" + "<img src=\"cid:image\">" + "" + "					</span>" + "					<table>"
					+ "	<tr>" + "		<th class=\"Form_title\">"
					+ MessageFormat.format("Hello, {0}", user.getFullName()) + "</th>" + "	</tr>" + "	<tr>"
					+ "	<td>" + body + "</td>" + "	</tr>" + "</table>" + "			</div>" + "		</div>" + "</body>"
					+ "</html>";

			messageBodyPart.setContent(text, "text/html");
			// add the html to the message
			multipart.addBodyPart(messageBodyPart);

			// configure the image
			messageBodyPart = new MimeBodyPart();
			DataSource image = new FileDataSource("src/main/webapp/images/horted-logo.png");
			messageBodyPart.setDataHandler(new DataHandler(image));
			messageBodyPart.addHeader("Content-ID", "<image>");
			multipart.addBodyPart(messageBodyPart);
			// add the image to the message
			message.setContent(multipart);
			// Send message
			mailSender.send(message);

		} catch (MessagingException ex) {
			Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
