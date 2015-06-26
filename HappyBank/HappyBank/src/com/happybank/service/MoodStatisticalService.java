package com.happybank.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.happybank.dal.BankDAO;
import com.happybank.dal.DepositDAO;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

@SuppressLint("SimpleDateFormat")
public class MoodStatisticalService{

	private HappyBiService hs = null;
	private BankDAO bd = null;
	private DepositDAO dd;
	
	public float getHappyBi(Context ctx){
		hs = new HappyBiService();
		float happybi = hs.getHappyBi(ctx);
		happybi = (float)(Math.round(happybi*100))/100;//减成两位小数
		return happybi;
	}
	
	public int getBankNum(Context ctx){
		bd = new BankDAO(ctx);
		bd.open();
		Cursor c = bd.QueryBankALL();
		int num = c.getCount();
		bd.close();
		return num;
	}
	
	public int getDepositAll(Context ctx){
		dd = new DepositDAO(ctx);
		dd.open();
		Cursor c = dd.QueryDepositALL();
		int num = c.getCount();
		dd.close();
		return num;
	}
	
	public int getDeposit(Context ctx){
		dd = new DepositDAO(ctx);
		int num = 0;
		dd.open();
		Cursor c = dd.QueryDepositALL();
		while (c.moveToNext()) {
			if (c.getInt(c.getColumnIndex("mood")) == 1
					&&c.getInt(c.getColumnIndex("iswithdraw")) == 0){
				num++;
			}
		}
		dd.close();
		return num;
	}
	
	public float getDepositZonge(Context ctx){
		bd = new BankDAO(ctx);
		float num = 0;
		bd.open();
		Cursor c = bd.QueryBankALL();
		while (c.moveToNext()) {
			num += c.getFloat(c.getColumnIndex("bankdeposit"));
		}
		bd.close();
		num = (float)(Math.round(num*100))/100;//减成两位小数
		return num;
	}
	
	public int getHappyAll(Context ctx){
		dd = new DepositDAO(ctx);
		int num = 0;
		dd.open();
		Cursor c = dd.QueryDepositALL();
		while (c.moveToNext()) {
			if (c.getInt(c.getColumnIndex("mood")) == 1){
				num++;
			}
		}
		dd.close();
		return num;
	}
	
	public int getUnhappyAll(Context ctx){
		dd = new DepositDAO(ctx);
		int num = 0;
		dd.open();
		Cursor c = dd.QueryDepositALL();
		while (c.moveToNext()) {
			if (c.getInt(c.getColumnIndex("mood")) == 0){
				num++;
			}
		}
		dd.close();
		return num;
	}
	
	public int getMoodRi(Context ctx){
		return getMood(ctx,24);
	}
	
	public int getMoodZhou(Context ctx){
		return getMood(ctx,24*7);
	}
	
	public int getMoodYue(Context ctx){
		return getMood(ctx,24*7*30);
	}
	
	public int getMood(Context ctx, int def){
		int averageMood = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ArrayList<Integer> list = new ArrayList<Integer>();
		Date curDate = new Date(System.currentTimeMillis());
		Date d = new Date();
		long diff = 0;
		long hours = 0;
		dd = new DepositDAO(ctx);
		
		dd.open();
		Cursor c = dd.QueryDepositALL();
		while (c.moveToNext()) {
			try {
				d = sdf.parse(c.getString(c.getColumnIndex("deposittime")));
			} catch (ParseException e) {
			}
			diff = curDate.getTime() - d.getTime();
			hours = diff / (1000 * 60 * 60);
			if (hours < def && hours >= 0){
				list.add(c.getInt(c.getColumnIndex("mood")));
			}
		}
		dd.close();
		if (list.size() == 0)
			return 0;
		
		for (int i :list){
			averageMood += i;
		}
		averageMood = averageMood * 100 / list.size();
		return averageMood;
	}

}
