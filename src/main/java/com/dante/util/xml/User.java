package com.dante.util.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

	public User() {

	}

	public User(int userId, String userName, String role) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.role = role;
	}

	private int userId;
	private String userName;
	private String role;

	public int getUserId() {
		return userId;
	}

	@XmlElement
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	@XmlElement
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	@XmlElement
	public void setRole(String role) {
		this.role = role;
	}

	public String toString() {
		return String.format("Name: %s - Role: %s", userName, role);
		
	}
}
