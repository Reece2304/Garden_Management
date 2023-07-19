package com.Horted.app.Repository;

import org.springframework.data.repository.CrudRepository;

import com.Horted.app.Domain.Plants;

public interface PlantRepo extends CrudRepository<Plants, Integer> {

	public Plants findById(int id);
	
	public Plants findByapiId(int apiId);

}
