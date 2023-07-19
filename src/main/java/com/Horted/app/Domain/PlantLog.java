package com.Horted.app.Domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
/**
 * This is used to log more details about a plant
 * @author reece
 *
 */
public class PlantLog {

	
	@Id
	@GeneratedValue
	int Id;
	
	private String Pruning;
	
	private String nutrition;
	
	private String pesticide;
	
	private LocalDate dateOfEntry;
	
	public String getPruning() {
		return Pruning;
	}

	public void setPruning(String pruning) {
		Pruning = pruning;
	}

	public String getNutrition() {
		return nutrition;
	}

	public void setNutrition(String nutrition) {
		this.nutrition = nutrition;
	}

	public String getPesticide() {
		return pesticide;
	}

	public void setPesticide(String pesticide) {
		this.pesticide = pesticide;
	}

	public LocalDate getDateOfEntry() {
		return dateOfEntry;
	}

	public void setDateOfEntry(LocalDate dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
	}

}
