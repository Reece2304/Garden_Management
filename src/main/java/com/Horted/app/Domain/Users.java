package com.Horted.app.Domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * This class contains all the information stored about a user
 * @author Reece Cripps (rc424)
 **/
@Entity
public class Users {
	
	/**
	 * The field 'username' is the primary key for User
	 */
	@Id
	@NotNull
	private String username;
	
	/**
	 *  The field 'email' to store the user's email address
	 */
	@Email
	@NotEmpty
	private String email;
	/**
	 * The field 'fullname' is the users full name
	 */
	@NotNull
	private String fullName;
	
	/**
	 * The field 'password' is a string that stores the users password to login to the web application
	 */
	@NotNull
	private String password;
	
	private String confirmPass;
	
	private String newPass;
	/**
	 * The field 'Role' is {@link com.Horted.app.Domain.Role} what type of permissions the user has.
	 */
	private Role role;
	
	/**
	 * When an account is created it needs to be verified (by email link) so default value is false
	 */
	@NotNull
	private boolean verified = false;
	
	@NotNull
	private boolean removed = false;
	/**
	 * The field 'plants' is a list of {@link com.Horted.Domain.Plants} plants that the user has added
	 * The OneToMany annotation means that one User can have many Plants
	 * The join column annotation means that User will link to Plant in the database with a foreign key
	 */
	@OneToMany
	@JoinColumn
	private List<Plants> plants = new ArrayList<>();
	
	/**
	 * This field stores how many plants a user has in each border e.g (top, 2)
	 */
	private HashMap<String, Integer> borderCount = new HashMap<String, Integer>();
	
		
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Plants> getPlants() {
		return plants;
	}
	
	public void setPlants(List<Plants> plants) {
		
		this.plants = plants;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getconfirmPass() {
		return confirmPass;
	}
	public void setconfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean Verified) {
		verified = Verified;
	}
	public boolean isremoved() {
		return removed;
	}
	public void setRemoved(boolean Removed) {
		removed = Removed;
	}
	public String getnewPass() {
		return newPass;
	}
	public void setnewPass(String newPass) {
		this.newPass = newPass;
	}
	public HashMap<String, Integer> getBorderCount() {
		return borderCount;
	}
	public void setBorderCount(HashMap<String, Integer> borderCount) {
		this.borderCount = borderCount;
	}
}
