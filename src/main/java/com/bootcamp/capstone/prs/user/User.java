package com.bootcamp.capstone.prs.user;

import javax.persistence.*;

@Entity
@Table(name="Users", uniqueConstraints=@UniqueConstraint(name="UIDX_Username", columnNames= {"username"}))
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(length=30, nullable=false)
	private String username;
	
	@Column(length=30, nullable=false)
	private String password;
	
	@Column(length=30, nullable=false)
	private String firstName;
	
	@Column(length=30, nullable=false)
	private String lastName;
	
	@Column(length=12, nullable=true)
	private String phone;
	
	@Column(length=255, nullable=true)
	private String email;
	
	private boolean isReviewer;
	
	private boolean isAdmin;
	
	public User() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getReviewer() {
		return isReviewer;
	}

	public void setReviewer(boolean isReviewer) {
		this.isReviewer = isReviewer;
	}

	public boolean getAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	
	
	
	

}
