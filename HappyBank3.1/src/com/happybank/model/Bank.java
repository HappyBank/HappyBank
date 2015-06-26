package com.happybank.model;

public class Bank {
	private int bankID;
	private String bankName;
	private String bankDesc;
	private float bankDeposit;
	private float extraInterest;
	public int getBankID() {
		return bankID;
	}
	public void setBankID(int bankID) {
		this.bankID = bankID;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankDesc() {
		return bankDesc;
	}
	public void setBankDesc(String bankDesc) {
		this.bankDesc = bankDesc;
	}
	public float getBankDeposit() {
		return bankDeposit;
	}
	public void setBankDeposit(float bankDeposit) {
		this.bankDeposit = bankDeposit;
	}
	public float getExtraInterest() {
		return extraInterest;
	}
	public void setExtraInterest(float extraInterest) {
		this.extraInterest = extraInterest;
	}
}
