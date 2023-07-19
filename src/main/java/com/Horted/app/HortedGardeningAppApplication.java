package com.Horted.app;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Horted.app.Domain.Plants;
import com.Horted.app.Domain.Role;
import com.Horted.app.Domain.Users;
import com.Horted.app.Repository.PlantRepo;
import com.Horted.app.Repository.UserRepo;

@SpringBootApplication
public class HortedGardeningAppApplication implements ApplicationRunner {

	@Autowired
	private UserRepo repo;
	
	@Autowired
	private PlantRepo Prepo;

	@Autowired
	private PasswordEncoder pe;

	public static void main(String[] args) {
		SpringApplication.run(HortedGardeningAppApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		HashMap<String, Integer> borderCount = new HashMap<String, Integer>();
		borderCount.put("top", 0);
		borderCount.put("left", 0);
		borderCount.put("right", 0);
		borderCount.put("bottom", 0);
		Users u1 = new Users();
		u1.setUsername("rc424");
		u1.setFullName("Reece");
		u1.setPassword("password");
		u1.setEmail("reececripps23@gmail.com");
		u1.setRole(Role.Admin);
		u1.setPassword(pe.encode("password"));
		u1.setVerified(true);
		u1.setRemoved(false);
		u1.setBorderCount(borderCount);
		repo.save(u1);

		u1 = new Users();
		u1.setUsername("User");
		u1.setFullName("Stuart");
		u1.setPassword("password");
		u1.setEmail("be@gmail.com");
		u1.setRole(Role.Free);
		u1.setPassword(pe.encode("password"));
		u1.setVerified(true);
		u1.setRemoved(false);
		u1.setBorderCount(borderCount);
		List<Plants> plants = new ArrayList<>();
		Plants p1 = new Plants();
		p1.setBorder("top");
		p1.setApiId("1");
		p1.setDaysToNextWater(0);
		p1.setHappinessToday(false);
		p1.setImageURL("https://www.gardeningknowhow.com/wp-content/uploads/2019/11/red-rose.jpg");
		p1.setName("rose");
		p1.setWateringFrequency("Daily");
		p1.setWateringTime(LocalDate.now());
		plants.add(p1);
		Prepo.save(p1);
		Plants p2 = new Plants();
		p2.setBorder("left");
		p2.setApiId("2");
		p2.setDaysToNextWater(2);
		p2.setHappinessToday(false);
		p2.setImageURL("https://upload.wikimedia.org/wikipedia/commons/thumb/4/40/Sunflower_sky_backdrop.jpg/1200px-Sunflower_sky_backdrop.jpg");
		p2.setName("Sunflower");
		p2.setWateringFrequency("Weekly");
		p2.setWateringTime(LocalDate.now());
		plants.add(p2);
		Prepo.save(p2);
		Plants p3 = new Plants();
		p3.setBorder("right");
		p3.setApiId("3");
		p3.setDaysToNextWater(10);
		p3.setHappinessToday(false);
		p3.setImageURL("https://www.almanac.com/sites/default/files/image_nodes/chrysanthemum-orange-4544730_1920.jpg");
		p3.setName("Chrysanthemum");
		p3.setWateringFrequency("Monthly");
		p3.setWateringTime(LocalDate.now());
		plants.add(p3);
		Prepo.save(p3);
		u1.setPlants(plants);
		repo.save(u1);
	}

}
