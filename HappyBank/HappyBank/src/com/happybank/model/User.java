package com.happybank.model;

import java.util.Date;

public class User {
	private String password;
	private String email;
	private float happyBi;
	private Date loginDate;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public float getHappyBi() {
		return happyBi;
	}
	public void setHappyBi(float happyBi) {
		this.happyBi = happyBi;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	
}
