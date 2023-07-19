package com.Horted.app.Domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
/**
 * This is used to log the happiness of a given plant
 * @author reece
 *
 */
public class PlantHappiness {
	
	@Id
	@GeneratedValue
	int Id;
	
	
	private boolean happy;
	
	private LocalDate dateOfEntry;
	
	


	public boolean isHappy() {
		return happy;
	}


	public void setHappy(boolean happy) {
		this.happy = happy;
	}


	public LocalDate getDateOfEntry() {
		return dateOfEntry;
	}


	public void setDateOfEntry(LocalDate dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
	}

}
