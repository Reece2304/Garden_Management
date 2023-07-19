package com.Horted.app.Repository;

import org.springframework.data.repository.CrudRepository;

import com.Horted.app.Domain.PlantLog;

public interface PlantLogRepo extends CrudRepository<PlantLog, Integer> {

	public PlantLog findById(int id);
	

}
