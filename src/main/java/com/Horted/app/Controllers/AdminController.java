package com.Horted.app.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Horted.app.Domain.Role;
import com.Horted.app.Domain.Users;
import com.Horted.app.Domain.verificationToken;
import com.Horted.app.Repository.UserRepo;
import com.Horted.app.Repository.VerificationTokenRepo;

@Controller
@RequestMapping("/a/")
public class AdminController {

	@Autowired
	UserRepo Urepo;

	@Autowired
	VerificationTokenRepo Vrepo;

	@GetMapping("/AdminHome")
	public String showUsers(Model model) {
		model.addAttribute("Users", Urepo.findAll());// passes a list of all users to the admin home page
		return "home/Adminhome";
	}

	/*
	 * Deletes a user based on the name clicked
	 */
	@GetMapping("/RemoveUser")
	public String removeUser(@RequestParam String Username, Model model) {

		Users user = Urepo.findByUsername(Username);
		if (user != null) {
			if (user.getRole() != Role.Admin) {
				try {
					verificationToken token = Vrepo.findByUser(user);
					Vrepo.delete(token);
					Urepo.deleteById(Username); // remove the user clicked on by the admin
				} catch (Exception e) {
					Urepo.deleteById(Username); // remove the user clicked on by the admin
				}

			} else {
				String error = "You cannot remove an admin"; // ensure that the admin doesn't delete another admin
				model.addAttribute("error", error);
				model.addAttribute("Users", Urepo.findAll());
				return "home/Adminhome";
			}
		}
		model.addAttribute("Username", Username);
		return "Admin/UserRemoved";
	}

	/*
	 * Upgrades a user to premium based on the name clicked
	 */

	// TODO: Email user once they have been upgraded
	@GetMapping("/UpgradePremium")
	public String upgradeUserToPremium(@RequestParam String Username, Model model) {
		Users user = Urepo.findByUsername(Username);
		if (user != null) {
			if (user.getRole() != Role.Admin) {
				user.setRole(Role.Premium); // upgrade the user clicked on to a premium user
				Urepo.save(user);
			} else {
				String error = "You cannot promote an admin to premium"; // make sure the admin doesn't demote another
																			// admin to a premium user
				model.addAttribute("error", error);
				model.addAttribute("Users", Urepo.findAll());
				return "home/Adminhome";
			}
		}
		model.addAttribute("Username", Username);
		return "Admin/UserUpgradedPremium";
	}

	/*
	 * Upgrades a user to admin based on the name clicked
	 */
	@GetMapping("/UpgradeAdmin")
	public String upgradeUserToAdmin(@RequestParam String Username, Model model) {
		Users user = Urepo.findByUsername(Username);
		if (user != null) {
			if (user.getRole() != Role.Admin) {
				user.setRole(Role.Admin);
				Urepo.save(user);
			} else {
				String error = "User is already an admin";
				model.addAttribute("error", error);
				model.addAttribute("Users", Urepo.findAll());
				return "home/Adminhome";
			}

		}
		model.addAttribute("Username", Username);
		return "Admin/UserUpgradedAdmin";
	}

	/**
	 * Shows the admin all the soft removed users
	 * 
	 * @param model
	 * @return
	 */

	@GetMapping("/RemovedUsers")
	public String showRemovedUsers(Model model) {
		model.addAttribute("Users", Urepo.findAll());// passes a list of all users to the admin home page
		return "Admin/RemovedUsers";
	}

	/*
	 * Soft deletes a user based on the name clicked
	 */
	@GetMapping("/SoftRemoveUser")
	public String softRemoveUser(@RequestParam String Username, Model model) {

		Users user = Urepo.findByUsername(Username);
		if (user != null) {
			if (user.getRole() != Role.Admin) {
				try {
					verificationToken token = Vrepo.findByUser(user);
					Vrepo.delete(token);
					user.setVerified(false);// when the user is soft removed, their verified is now false
					user.setRemoved(true);
					Urepo.save(user);
				} catch (Exception e) {
					user.setVerified(false);// when the user is soft removed, their verified is now false
					user.setRemoved(true);
					Urepo.save(user);
				}

			} else {
				String error = "You cannot remove an admin"; // ensure that the admin doesn't delete another admin
				model.addAttribute("error", error);
				model.addAttribute("Users", Urepo.findAll());
				return "home/Adminhome";
			}
		}
		model.addAttribute("Username", Username);
		return "Admin/UserSoftRemoved";
	}

	/**
	 * Restores the user back to the system so they are now able to login again
	 * 
	 * @param Username
	 * @param model
	 * @return
	 */
	@GetMapping("/RestoreUser")
	public String RestoreUser(@RequestParam String Username, Model model) {

		Users user = Urepo.findByUsername(Username);
		if (user != null) {
			try {
				user.setVerified(true);// when the user is soft removed, their verified is now false
				user.setRemoved(false);
				Urepo.save(user);
			} catch (Exception e) {
				user.setVerified(true);// when the user is soft removed, their verified is now false
				user.setRemoved(false);
				Urepo.save(user);
			}
		}
		model.addAttribute("Username", Username);
		return "Admin/UserRestored";
	}
}
