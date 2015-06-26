package com.happybank.config;

import java.util.ArrayList;

import android.app.Application;

import com.happybank.model.Bank;
import com.happybank.model.Soup;

public class Global extends Application {
	public static Global instance = null;
	public static boolean exist = false; // 判断是否为第一次使用
	public static int theme;

	public static Global getInstance() {
		if (instance == null)
			instance = new Global();
		return instance;
	}

	// 用来显示心灵鸡汤
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