package com.happybank.config;

import java.util.ArrayList;

import com.happybank.model.Bank;

public class Global {
	public static Global instance = null;
	public static Global getInstance(){
		if (instance == null)
			instance = new Global();
		return instance;
	}
	
	public ArrayList<Bank> bankList = null;
	public float averageMood = -1;
}
