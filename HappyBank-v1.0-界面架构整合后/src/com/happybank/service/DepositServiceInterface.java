package com.happybank.service;

import java.sql.Time;
import java.util.ArrayList;

import com.happybank.model.Deposit;

public interface DepositServiceInterface {
	public boolean addDeposit();
	public boolean deleteDeposit();
	public ArrayList<Deposit> viewDeposit();
	public boolean withdrawals();
	public Deposit searchDeposit();
	public ArrayList<Deposit> getRightDeposit(Time t);//Date t?
}
