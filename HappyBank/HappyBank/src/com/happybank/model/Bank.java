package com.happybank.model;

import java.io.Serializable;

public class Bank implements Serializable{
	private int bankID;
	private String bankName;
	private String bankDesc;
	private float bankDeposit;
	private float extraInterest;
	
	private int depositNum;
	private int happyDepositNum;
	private int unhappyDepositNum;
	
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
	
	public int getDepositNum() {
		return depositNum;
	}
	public void setDepositNum(int depositNum) {
		this.depositNum = depositNum;
	}
	
	public int getHappyDepositNum() {
		return happyDepositNum;
	}
	public void setHappyDepositNum(int happyDepositNum) {
		this.happyDepositNum = happyDepositNum;
	}
	
	public int getUnhappyDepositNum() {
		return unhappyDepositNum;
	}
	public void setUnhappyDepositNum(int unhappyDepositNum) {
		this.unhappyDepositNum = unhappyDepositNum;
	}
}
