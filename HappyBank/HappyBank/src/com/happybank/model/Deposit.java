package com.happybank.model;

import java.io.Serializable;
import java.util.Date;

public class Deposit implements Serializable{
	private int bankID;
	private int depositID;
	private String depositName;
	private Date depositTime;
	private int mood;
	private String depositContent;
	private float depositBegin;
	private float interest;
	private String picName;
	private boolean isWithdraw;
	
	public int getBankID() {
		return bankID;
	}
	public void setBankID(int bankID) {
		this.bankID = bankID;
	}
	public int getDepositID() {
		return depositID;
	}
	public void setDepositID(int depositID) {
		this.depositID = depositID;
	}
	public String getDepositName() {
		return depositName;
	}
	public void setDepositName(String depositName) {
		this.depositName = depositName;
	}
	public Date getDepositTime() {
		return depositTime;
	}
	public void setDepositTime(Date depositTime) {
		this.depositTime = depositTime;
	}
	public int getMood() {
		return mood;
	}
	public void setMood(int mood) {
		this.mood = mood;
	}
	public String getDepositContent() {
		return depositContent;
	}
	public void setDepositContent(String depositContent) {
		this.depositContent = depositContent;
	}
	public float getDepositBegin() {
		return depositBegin;
	}
	public void setDepositBegin(float depositBegin) {
		this.depositBegin = depositBegin;
	}
	public float getInterest() {
		return interest;
	}
	public void setInterest(float interest) {
		this.interest = interest;
	}
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public boolean getIsWithdraw() {
		return isWithdraw;
	}
	public void setWithdraw(boolean isWithdraw) {
		this.isWithdraw = isWithdraw;
	}
	
}
