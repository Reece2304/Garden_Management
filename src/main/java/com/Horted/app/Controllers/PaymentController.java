package com.Horted.app.Controllers;

import java.text.MessageFormat;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Horted.app.Login;
import com.Horted.app.Domain.Order;
import com.Horted.app.Domain.Role;
import com.Horted.app.Domain.Users;
import com.Horted.app.Repository.UserRepo;
import com.Horted.app.Services.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller

@RequestMapping("/p/")
public class PaymentController {

	@Autowired
	PaypalService service;

	@Autowired
	UserRepo Urepo;

	@Autowired
	private JavaMailSender mailSender;
	
	
	/**
	 * creates a paypal payment to send to the paypal service which will be passed to the API
	 * @param model
	 * @param request
	 * @return
	 * @throws PayPalRESTException
	 */
	@PostMapping(value = "/pay")
	public String upgradeUser(Model model, final HttpServletRequest request) throws PayPalRESTException {
		String url = request.getRequestURL().toString();
		Payment payment = service.createPayment(5.0, "GBP", "Paypal", "sale", "Gardens Digital premium",
				url + "/cancel", url + "/success");
		for (Links link : payment.getLinks()) {

			if (link.getRel().equals("approval_url")) {
				return "redirect:" + link.getHref();

			}
		}

		return "redirect:/u/upgradeUser";

	}

	/**
	 * if the user cancels their payment
	 * @return
	 */
	@RequestMapping(value = "/pay/cancel")
	public String cancelPayment() {
		return "payment/cancel";

	}

	@RequestMapping(value = "/success")
	public String paymentDisplay() {
		return "payment/success";

	}
	/**
	 * If the user completes their payment then update their account to premium
	 * then email the user to tell them that their account has been upgraded
	 * @param paymentId
	 * @param payerId
	 * @param model
	 * @return
	 * @throws PayPalRESTException
	 */
	@RequestMapping(value = "/pay/success")
	public String paymentSuccessful(@RequestParam("paymentId") String paymentId,
			@RequestParam("PayerID") String payerId, Model model) throws PayPalRESTException {

		Payment payment = service.executePayment(paymentId, payerId);
		if (payment.getState().equals("approved")) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Users currentUser = Urepo.findByUsername(auth.getName());
			currentUser.setRole(Role.Premium);
			Urepo.save(currentUser);
			EmailUpgrade(currentUser);
			return "redirect:/p/success";

		}
		return "redirect:/u/upgradeUser";

	}

	private void EmailUpgrade(Users user) {
		String message = "<H2> Your account has been upgraded successfully </H2>"
				+ "<p style='font-size:20px;'> You can now Add an unlimited number of plants to your garden </p>"
				+ "<p style='font-size:20px;'> Adverts have also been removed for you!</p>"
				+ "<p style='font-size:20px;'>Thanks for upgrading! </p>";
		createEmail(user.getFullName() + " Account Upgraded!", message, user);
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
