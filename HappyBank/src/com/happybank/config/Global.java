package com.happybank.config;

import java.util.ArrayList;

import android.app.Application;

import com.happybank.model.Bank;
import com.happybank.model.Soup;

public class Global extends Application {
	public static Global instance = null;
	public static boolean exist = false; // �ж��Ƿ�Ϊ��һ��ʹ��
	public static int theme;

	public static Global getInstance() {
		if (instance == null)
			instance = new Global();
		return instance;
	}

	// ������ʾ���鼦��
	public static ArrayList<Soup> souplist;
	public static int soupID;

	public ArrayList<Soup> getSoupList() {
		return souplist;
	}

	public void setSoupList(ArrayList<Soup> al) {
		this.souplist = al;
	}

	public ArrayList<Bank> bankList = null;
	public float averageMood = -1;
	
}