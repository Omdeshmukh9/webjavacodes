package com.web.entity;
//this is  a pojo class with setter getter methods
public class User 
{
	String UserName;
	String password;
	String Name;
	String Email;
	String City;
	

	public User(String userName) {
		super();
		// TODO Auto-generated constructor stub
		this.UserName = userName;
	}
	
	public String getName() {
		return Name;
	}
	public String getEmail() {
		return Email;
	}
	public void setName(String name) {
		Name = name;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getUserName() {
		return UserName;
	}
	public String getPassword() {
		return password;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}

	@Override
	public String toString() {
		return "User [UserName=" + UserName + ", password=" + password + ", Name=" + Name + ", Email=" + Email
				+ ", City=" + City + "]";
	}
	

}
