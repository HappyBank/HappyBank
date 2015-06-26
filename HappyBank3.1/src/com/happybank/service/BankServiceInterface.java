package com.happybank.service;

import java.util.ArrayList;

import com.happybank.model.Bank;

public interface BankServiceInterface {
	public boolean addBank();
	public boolean deleteBank();
	public boolean updateBank();
	public ArrayList<Bank> viewBank();
	public Bank searchBank();
}
