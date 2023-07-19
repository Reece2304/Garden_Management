package com.Horted.app.Repository;

import org.springframework.data.repository.CrudRepository;

import com.Horted.app.Domain.PlantHappiness;

public interface PlantHappinessRepo extends CrudRepository<PlantHappiness, Integer> {

	public PlantHappiness findById(int id);
	
}
