package com.Horted.app.Controllers;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Horted.app.Login;
import com.Horted.app.Domain.Order;
import com.Horted.app.Domain.PlantHappiness;
import com.Horted.app.Domain.PlantLog;
import com.Horted.app.Domain.Plants;
import com.Horted.app.Domain.Users;
import com.Horted.app.Repository.PlantHappinessRepo;
import com.Horted.app.Repository.PlantLogRepo;
import com.Horted.app.Repository.PlantRepo;
import com.Horted.app.Repository.UserRepo;

@Controller
@RequestMapping("/u/")
public class UserController {

	@Autowired
	UserRepo Urepo;

	@Autowired
	PlantRepo Prepo;

	@Autowired
	private PasswordEncoder pe;

	@Autowired
	private JavaMailSender mailSender;

	/**
	 * Return the home page to the user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String Ulogged(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users user = Urepo.findByUsername(auth.getName());
		List<Plants> plantList = user.getPlants();
		if (!plantList.isEmpty()) {
			LocalDate nextWater = LocalDate.now();
			LocalDate now = LocalDate.now();
			List<Integer> overdueList = new ArrayList<>();
			
			//calculate the number of days the plants have to be watered.
			for (int i = 0; i < plantList.size(); i++) {
				nextWater = plantList.get(i).getWateringTime();
				Date date = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
				Date date2 = Date.from(nextWater.atStartOfDay(ZoneId.systemDefault()).toInstant());
				int daysToNextWater = (int) ChronoUnit.DAYS.between(date.toInstant(), date2.toInstant());
				if(daysToNextWater <= 0)
				{
					int overdue = daysToNextWater * -1;
					overdueList.add(overdue);
					daysToNextWater = 0; //cant have negative days to water
				}
				plantList.get(i).setDaysToNextWater(daysToNextWater);
			}
			Urepo.save(user);
			model.addAttribute("plants", plantList); // passes the list of user's plants to the webpage
			model.addAttribute("overdueList" , overdueList); //passes the plants' overdue values to the webpage
			
		}
		model.addAttribute("name", auth.getName()); // passes the username to the webpage
		model.addAttribute("role", user.getRole());
		return "home/Userhome";
	}

	/**
	 * When a user clicks on the 'I've watered this plant'
	 * This marks their plant as watered and re-calculates a new watering date
	 * Then the user is redirected to the home page
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/water/{id}", method = RequestMethod.GET)
	public String waterPlant(@PathVariable String id, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users user = Urepo.findByUsername(auth.getName());
		List<Plants> plantList = user.getPlants();
		for (int i = 0; i < plantList.size(); i++) {
			if (plantList.get(i).getDaysToNextWater() == 0 && plantList.get(i).getApiId().equals(id)) {
				LocalDate nextWater = LocalDate.now();
				LocalDate now = LocalDate.now();
				String wateringFrequency = plantList.get(i).getWateringFrequency();
				if (wateringFrequency.equals("Daily")) {
					nextWater = nextWater.plusDays(1);
				} else if (wateringFrequency.equals("Weekly")) {
					nextWater = nextWater.plusWeeks(1);
				} else if (wateringFrequency.equals("Monthly")) {
					nextWater = nextWater.plusMonths(1);
				}
				plantList.get(i).setWateringTime(nextWater);
				Date date = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
				Date date2 = Date.from(nextWater.atStartOfDay(ZoneId.systemDefault()).toInstant());
				int daysToNextWater = (int) ChronoUnit.DAYS.between(date.toInstant(), date2.toInstant());
				plantList.get(i).setDaysToNextWater(daysToNextWater);
				Urepo.save(user);
				return "redirect:/u/home";
			}
		}
		return "redirect:/u/home";
	}

	/**
	 * Displays the user's garden page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/garden", method = RequestMethod.GET)
	public String ShowGarden(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Users user = Urepo.findByUsername(auth.getName());
		model.addAttribute("User", user);
		if (user.getPlants().isEmpty()) {
			model.addAttribute("borderCount", user.getBorderCount());
			model.addAttribute("role", user.getRole());
			model.addAttribute("log", new PlantLog());
			return "User/garden";

		} else {
			List<Plants> plantList = user.getPlants();
			LocalDate today = LocalDate.now();
			for (int i = 0; i < plantList.size(); i++) { //calculates if the plant has been logged today
				List<PlantHappiness> happyList = plantList.get(i).getHappy();

				if (!happyList.isEmpty()) {
					for (int j = 0; j < happyList.size(); j++) {

						if (happyList.get(j).getDateOfEntry().equals(today)) {
							plantList.get(i).setHappinessToday(true);
							break;
						}
					}
					Urepo.save(user);
				}

			}
			model.addAttribute("log", new PlantLog());
			model.addAttribute("borderCount", user.getBorderCount());
			model.addAttribute("UserPlants", user.getPlants());
			model.addAttribute("role", user.getRole());
		}

		return "User/garden";
	}

	
	/**
	 * Displays the user's account page and passes in a new 'user'
	 * So that if the user updates their account their current account can be reconfigured
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public String ShowAccountDetails(@ModelAttribute("user") @Validated Users user, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users user1 = Urepo.findByUsername(auth.getName());
		model.addAttribute("user", user1);
		model.addAttribute("username", user1.getUsername());
		return "User/account";
	}

	/**
	 * Creates a new order and redirects users to the checkout page.
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "/upgradeUser", method = RequestMethod.GET)
	public String upgradeUserPay(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users User = Urepo.findByUsername(auth.getName());
		model.addAttribute("Order", new Order());
		model.addAttribute("User", User);
		return "User/upgrade";

	}

	/**
	 * If a user clicks on the 'sad' plant then this logs the plant as looking sad for todays date
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sad/{id}", method = RequestMethod.GET)
	public String setSad(@PathVariable int id, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users user = Urepo.findByUsername(auth.getName());
		List<Plants> plantList = user.getPlants();
		LocalDate today = LocalDate.now();
		for (int i = 0; i < plantList.size(); i++) {
			if (plantList.get(i).getId() == id && plantList.get(i).isHappinessToday() == false) {
				PlantHappiness todaysHappiness = new PlantHappiness();
				todaysHappiness.setDateOfEntry(today);
				todaysHappiness.setHappy(false);
				List<PlantHappiness> happyList = plantList.get(i).getHappy();
				happyList.add(todaysHappiness);
				plantList.get(i).setHappy(happyList);
				Prepo.save(plantList.get(i));
				return "redirect:/u/garden";
			}
		}

		return "redirect:/u/garden";
	}

	/**
	 * If a user clicks on the 'happy' plant then this logs the plant as looking happy for todays date
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/happy/{id}", method = RequestMethod.GET)
	public String setHappy(@PathVariable int id, Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users user = Urepo.findByUsername(auth.getName());
		List<Plants> plantList = user.getPlants();
		LocalDate today = LocalDate.now();
		for (int i = 0; i < plantList.size(); i++) {
			if (plantList.get(i).getId() == id && plantList.get(i).isHappinessToday() == false) {
				PlantHappiness todaysHappiness = new PlantHappiness();
				todaysHappiness.setDateOfEntry(today);
				todaysHappiness.setHappy(true);
				List<PlantHappiness> happyList = plantList.get(i).getHappy();
				happyList.add(todaysHappiness);
				plantList.get(i).setHappy(happyList);
				Prepo.save(plantList.get(i));
				return "redirect:/u/garden";
			}
		}
		return "redirect:/u/garden";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String UpdateComplete(@Valid @ModelAttribute("user") Users user, BindingResult result, Model model,
			final HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users currentUser = Urepo.findByUsername(auth.getName());
		String Updated = "";
		if (!user.getUsername().equals(currentUser.getUsername())) // check if the user has entered a new username
		{
			if (Urepo.findByUsername(user.getUsername()) != null) {
				result.rejectValue("username", "", "Username Already exists");
			} else {
				if (Updated == "") {
					Updated += " Username";
				} else {
					Updated += " and Username";
				}
			}
		}

		if (!user.getEmail().equals(currentUser.getEmail()))// check if the user has entered a new email
		{
			if (Urepo.findByEmail(user.getEmail()) != null) {
				result.rejectValue("email", "", "email already exists");
			} else {
				if (Updated == "") {
					Updated += " email";
				} else {
					Updated += " and email";
				}

			}
		}
		if (!pe.matches(user.getPassword(), currentUser.getPassword())) // Check that the user's passwords match
		{
			if (user.getPassword() == "") {
				result.rejectValue("password", "", "You need to enter your current password to update your details");
			} else {
				result.rejectValue("password", "", "Your password is incorrect");
			}
		}

		if (user.getnewPass() != "") // Check if the user has entered a new password
		{
			if (pe.matches(user.getnewPass(), currentUser.getPassword())) {
				result.rejectValue("newPass", "", "Your new password is the same as your old password");
			}

			if (!user.getconfirmPass().equals(user.getnewPass())) {
				result.rejectValue("newPass", "", "Your passwords do not match");
				result.rejectValue("confirmPass", "", "Your passwords do not match");
			} else {
				user.setPassword(pe.encode(user.getnewPass()));
				if (Updated == "") {
					Updated += " password";
				} else {
					Updated += " and password";
				}
			}
		} else {
			user.setPassword(pe.encode(user.getPassword()));
		}
		// if there are errors then refresh the page
		if (result.hasErrors()) {
			model.addAttribute("username", currentUser.getUsername());
			return "User/account";
		} else {
			user.setnewPass(pe.encode(user.getnewPass()));
			user.setVerified(true);
			Urepo.delete(currentUser); // delete the old user
			Urepo.save(user);// save the updated user into the database

			model.addAttribute("update", "Account Updated!");
			model.addAttribute("username", user.getUsername());

			Authentication newAuth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
					auth.getAuthorities()); // New updated user needs to be authenticated

			SecurityContextHolder.getContext().setAuthentication(newAuth);
			createUpdatedEmail(Updated, user, request);
			return "User/account";
		}
	}

	@RequestMapping(value = "/AboutUs", method = RequestMethod.GET)
	public String AboutUs(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users user1 = Urepo.findByUsername(auth.getName());
		model.addAttribute("role", user1.getRole());
		return "User/AboutUs";
	}

	@RequestMapping(value = "/contactUs", method = RequestMethod.GET)
	public String ContactUs(@ModelAttribute("user") @Validated Users user, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users user1 = Urepo.findByUsername(auth.getName());
		model.addAttribute("user", user1);
		model.addAttribute("username", user1.getUsername());
		model.addAttribute("role", user1.getRole());
		return "User/ContactUs";
	}

	/**
	 * Creates an email for the user to send to gardens digital
	 * @param user
	 * @param result
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ContactSend", method = RequestMethod.POST)
	public String SendContact(@Valid @ModelAttribute("user") Users user, BindingResult result, Model model,
			final HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users user1 = Urepo.findByUsername(auth.getName());
		model.addAttribute("user", user1);
		model.addAttribute("username", user1.getUsername());
		String subject = user.getnewPass();
		String message = user.getconfirmPass();
		createContactUsEmail(subject, message, user1);
		model.addAttribute("update", "Email Sent!");
		model.addAttribute("role", user1.getRole());
		return "User/ContactUs";
	}

	@RequestMapping(value = "/OurWork", method = RequestMethod.GET)
	public String OurWork(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Users user = Urepo.findByUsername(auth.getName());
		model.addAttribute("User", user);
		model.addAttribute("role", user.getRole());

		return "User/OurWork";
	}

	/**
	 * This function gets the plant that was added by the user and saves it in the database.
	 */
	@RequestMapping(value = "/AddPlant/{details}/**", method = RequestMethod.GET)
	public String AddPlant(@PathVariable String details, Model model, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users user1 = Urepo.findByUsername(auth.getName());
		String url = request.getRequestURL().toString();
		Plants plant = new Plants();
		String id = details.split(",")[0];
		String border = details.split(",")[2];
		String wateringFrequency = details.split(",")[3];
		String name = details.split(",")[1];
		String imageURL = "https://" + url.split(wateringFrequency)[1];
		LocalDate nextWater = LocalDate.now();
		LocalDate now = LocalDate.now();
		if (wateringFrequency.equals("Daily")) {
			nextWater = nextWater.plusDays(1);
		} else if (wateringFrequency.equals("Weekly")) {
			nextWater = nextWater.plusWeeks(1);
		} else if (wateringFrequency.equals("Monthly")) {
			nextWater = nextWater.plusMonths(1);
		}
		plant.setApiId(id);
		plant.setImageURL(imageURL);
		plant.setName(name);
		plant.setBorder(border);
		plant.setWateringFrequency(wateringFrequency);
		plant.setWateringTime(nextWater);
		Prepo.save(plant);
		user1.getPlants().add(plant);
		user1.getBorderCount().put(border, user1.getBorderCount().get(border) + 1);
		Urepo.save(user1);

		return "redirect:/u/garden";
	}

	/**
	 * When a user clicks save on an edited plant this function gets called
	 * The plant is found in the database and its new watering date is calculated
	 * @param updated
	 * @param id
	 * @param border
	 * @param previous
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/EditPlant/{updated}/{id}/{border}/{previous}", method = RequestMethod.GET)
	public String EditPlant(@PathVariable String updated, @PathVariable String id, @PathVariable String border,
			@PathVariable String previous, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users user1 = Urepo.findByUsername(auth.getName());
		List<Plants> userPlants = user1.getPlants();
		LocalDate nextWater = LocalDate.now();
		for (int i = 0; i < userPlants.size(); i++) {
			if (userPlants.get(i).getBorder().equals(border) && userPlants.get(i).getApiId().equals(id)
					&& userPlants.get(i).getWateringFrequency().equals(previous) && previous != updated) {
				if (updated.equals("Daily")) {
					nextWater = nextWater.plusDays(1);
				} else if (updated.equals("Weekly")) {
					nextWater = nextWater.plusWeeks(1);
				} else if (updated.equals("Monthly")) {
					nextWater = nextWater.plusMonths(1);
				}
				userPlants.get(i).setWateringFrequency(updated);
				userPlants.get(i).setWateringTime(nextWater);
				Urepo.save(user1);
				return "redirect:/u/garden";
			}
		}
		return "redirect:/u/garden";
	}

	/**
	 * Removes a given plant from the databased.
	 * @param id
	 * @param border
	 * @param water
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/RemovePlant/{id}/{border}/{water}", method = RequestMethod.GET)
	public String RemovePlant(@PathVariable String id, @PathVariable String border, @PathVariable String water,
			Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users user1 = Urepo.findByUsername(auth.getName());
		List<Plants> userPlants = user1.getPlants();
		for (int i = 0; i < userPlants.size(); i++) {
			if (userPlants.get(i).getBorder().equals(border) && userPlants.get(i).getApiId().equals(id)
					&& userPlants.get(i).getWateringFrequency().equals(water)) {
				userPlants.remove(i);
				Prepo.delete(userPlants.get(i));
				user1.getBorderCount().put(border, user1.getBorderCount().get(border) - 1);

				Urepo.save(user1);
				return "redirect:/u/garden";
			}
		}
		return "redirect:/u/garden";
	}

	private void createUpdatedEmail(String Updated, Users user, HttpServletRequest request) {
		String message = "<H2> Your " + Updated + " has been changed successfully ";
		createEmail(Updated + " Updated!", message, user);
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

	private void createContactUsEmail(String subject, String body, Users user) {
		try {

			MimeMessage message = mailSender.createMimeMessage();

			message.setSubject(subject);
			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true);
			helper.setFrom(user.getEmail());
			helper.setTo("gardensdigital@gmail.com");
			helper.setText(body, true);

			// This mail has 2 part, the BODY and the embedded image
			MimeMultipart multipart = new MimeMultipart("related");

			// create the html message
			BodyPart messageBodyPart = new MimeBodyPart();
			String text = "<html>" + "<head>" + "<style type =\"text/css\">" + "" + "table, th, td {"
					+ "  border-collapse: collapse;" + "  background-color: white;" + "   margin-left: auto;"
					+ "  margin-right: auto;" + "}" + "" + "th, td {" + "  padding: 10px;" + " font-size:20px;" + "}"
					+ ".Form_title {" + "  font-family: Arial;" + "  font-size: 40px;" + "  color: #333333;"
					+ "  line-height: 1.2;" + "  text-align: center;" + "  width: 100%;" + "  display: block;"
					+ "  padding-bottom: 54px;" + "}" + ".container_login {" + "  width: 90%;  " + "  height: 90%; "
					+ "  min-height: 100vh;" + "  display: flex;" + "  flex-wrap: wrap;" + "  justify-content: center;"
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
					+ MessageFormat.format("{0} has sent you a message: ", user.getFullName()) + "</th>" + "	</tr>"
					+ "	<tr>" + "	<td>" + body + "</td>" + "	</tr>" + "</table>" + "			</div>" + "		</div>"
					+ "</body>" + "</html>";

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
