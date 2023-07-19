package com.Horted.app.Controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Horted.app.Domain.Order;
import com.Horted.app.Domain.PlantLog;
import com.Horted.app.Domain.Plants;
import com.Horted.app.Domain.Users;
import com.Horted.app.Repository.PlantHappinessRepo;
import com.Horted.app.Repository.PlantLogRepo;
import com.Horted.app.Repository.PlantRepo;
import com.Horted.app.Repository.UserRepo;

@Controller
@RequestMapping("/u/log")
public class LogController {

	@Autowired
	UserRepo Urepo;

	@Autowired
	PlantRepo Prepo;

	@Autowired
	PlantHappinessRepo Phrepo;

	@Autowired
	PlantLogRepo Plrepo;

	/**
	 * This is a controller method that displays the home log screen to the user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String upgradeUserPay(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users User = Urepo.findByUsername(auth.getName());
		model.addAttribute("Plants", User.getPlants());
		model.addAttribute("User", User);
		return "Logs/home";

	}

	/**
	 * This gets the specific plant clicked on by the user and displays its logs
	 * @param id - path variable used to find the plant
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/Free/{id}", method = RequestMethod.GET)
	public String DisplayLogFree(@PathVariable int id, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users User = Urepo.findByUsername(auth.getName());
		Plants p1 = Prepo.findById(id);
		model.addAttribute("happyList", p1.getHappy());
		model.addAttribute("User", User);
		model.addAttribute("plant", p1);
		return "Logs/Free";

	}

	/**
	 * This gets the specific plant clicked on by the user and displays its logs
	 * similar to the previous method but this method passes the more detailed log too
	 * @param id - path variable used to find the plant
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/Premium/{id}", method = RequestMethod.GET)
	public String DisplayLogPremium(@PathVariable int id, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users User = Urepo.findByUsername(auth.getName());
		Plants p1 = Prepo.findById(id);
		model.addAttribute("happyList", p1.getHappy());
		model.addAttribute("LogList", p1.getLogs());
		model.addAttribute("User", User);
		model.addAttribute("plant", p1);
		return "Logs/Premium";

	}
	/**
	 * This method creates a detailed log for a specific plant
	 * @param id - path variable used to find the plant
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/CreateLog/{id}", method = RequestMethod.POST)
	public String CreateLogPremium(@PathVariable int id, @ModelAttribute("log") PlantLog log, Model model) {
		LocalDate today = LocalDate.now();
		Plants p1 = Prepo.findById(id);
		List<PlantLog> logsList = p1.getLogs();
		log.setDateOfEntry(today);
		logsList.add(log);
		p1.setLogs(logsList);
		Prepo.save(p1);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users User = Urepo.findByUsername(auth.getName());
		model.addAttribute("User", User);
		return "redirect:/u/garden";

	}

}
