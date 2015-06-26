package com.happybank.service;

import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

import com.happybank.config.Config;
import com.happybank.dal.DepositDAO;
import com.happybank.dal.UserDAO;
import com.happybank.model.Deposit;

@SuppressLint("SimpleDateFormat")
public class DepositService {

	private DepositDAO dd;
	private UserDAO ud;
	private BankService bs = null;

	public boolean addDeposit(Context ctx, int bankID, String depositName,
			int mood, String depositContent, String picName) {

		dd = new DepositDAO(ctx);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String depositTime = sdf.format(new Date()).toString();
		float depositBegin = 1.0f;
		if (depositContent.length() > 100) {
			depositBegin += 0.2;
		}
		if (picName != null && picName.length() != 0) {
			depositBegin += 0.5;
		}
		float interest = 0;
		boolean isWithdraw = false;
		if (mood == 0) {
			isWithdraw = true;
			depositBegin = 0;
		}
		dd.open();
		dd.insertDeposit(bankID, depositName, depositTime, mood,
				depositContent, depositBegin, interest, picName, isWithdraw);
		dd.close();
		bs = new BankService();
		bs.calculateDeposit(ctx, bankID);
		return true;
	}

	public boolean deleteDeposit(Context ctx, int depositID) {

		dd = new DepositDAO(ctx);
		dd.open();
		Cursor c = dd.QueryDepositByDepositID(depositID);
		c.moveToNext();
		int bankID = c.getInt(c.getColumnIndex("bankid"));
		dd.close();
		dd.open();
		if (dd.deleteDeposit(depositID) == 0) {
			dd.close();
			return false;
		} else {
			dd.close();
			bs = new BankService();
			bs.calculateDeposit(ctx, bankID);
			return true;
		}
	}

	// public List<HashMap<String, Object>> viewDepositHashMap(Context ctx, int
	// bankID) {
	// Deposit deposit = new Deposit();
	// ArrayList<Deposit> list = new ArrayList<Deposit>();
	// List<HashMap<String, Object>> data = null;
	// int isWithdraw;
	// String depositTime;
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// int count = 0;
	//
	// dd = new DepositDAO(ctx);
	// dd.open();
	// Cursor c = dd.QueryDepositByBankID(bankID);
	// count = c.getCount();
	// if (count == 0){
	// return null;
	// }
	// for (int i = 0; i < count; i++){
	// c.moveToNext();
	// deposit.setBankID(c.getInt(c.getColumnIndex("bankid")));
	// deposit.setDepositBegin(c.getFloat(c.getColumnIndex("depositbegin")));
	// deposit.setDepositContent(c.getString(c.getColumnIndex("depositcontent")));
	// deposit.setDepositID(c.getInt(c.getColumnIndex("depositid")));
	// deposit.setDepositName(c.getString(c.getColumnIndex("depositname")));
	// depositTime = c.getString(c.getColumnIndex("deposittime"));
	// try {
	// deposit.setDepositTime(sdf.parse(depositTime));
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	// deposit.setInterest(c.getFloat(c.getColumnIndex("interest")));
	// deposit.setMood(c.getInt(c.getColumnIndex("mood")));
	// deposit.setPicName(c.getString(c.getColumnIndex("picname")));
	// isWithdraw = c.getInt(c.getColumnIndex("iswithdraw"));
	// if (isWithdraw == 1){
	// deposit.setWithdraw(true);
	// } else {
	// deposit.setWithdraw(false);
	// }
	// list.add(deposit);
	// }
	// dd.close();
	//
	// data = new ArrayList<HashMap<String,Object>>();
	// for(Deposit dep : list){
	// HashMap<String, Object> item = new HashMap<String, Object>();
	// item.put("bankid", dep.getBankID());
	// item.put("depositbegin", dep.getDepositBegin());
	// item.put("depositcontent", dep.getDepositContent());
	// item.put("depositid", dep.getDepositID());
	// item.put("depositname", dep.getDepositName());
	// item.put("deposittime", dep.getDepositTime());
	// item.put("interest", dep.getInterest());
	// item.put("mood", dep.getMood());
	// item.put("picname", dep.getPicName());
	// item.put("iswithdraw", dep.getInterest());
	// data.add(item);
	// }
	//
	// return data;
	// }

	public ArrayList<Deposit> viewDeposit(Context ctx, int bankID) {
		Deposit deposit = null;
		ArrayList<Deposit> list = new ArrayList<Deposit>();
		int isWithdraw;
		String depositTime;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int count = 0;
		byte bytes[];
		String sn = null;

		dd = new DepositDAO(ctx);
		dd.open();
		Cursor c = dd.QueryDepositByBankID(bankID);
		count = c.getCount();
		if (count == 0) {
			return null;
		}
		for (int i = 0; i < count; i++) {
			c.moveToNext();
			deposit = new Deposit();
			deposit.setBankID(c.getInt(c.getColumnIndex("bankid")));
			deposit.setDepositBegin(c.getFloat(c.getColumnIndex("depositbegin")));
			if (c.getString(c.getColumnIndex("depositcontent")).matches(
					"[0-9]+")) {// 判断是否是纯数字
				deposit.setDepositContent(c.getString(c
						.getColumnIndex("depositcontent")));// 直接读取
			} else {// 转为汉语读入
				try {
					bytes = c.getBlob(c.getColumnIndex("depositcontent"));
					sn = new String(bytes, "utf-8");
				} catch (UnsupportedEncodingException e2) {
					e2.printStackTrace();
				}
				deposit.setDepositContent(sn);
			}
			deposit.setDepositID(c.getInt(c.getColumnIndex("depositid")));
			if (c.getString(c.getColumnIndex("depositname")).matches("[0-9]+")) {
				deposit.setDepositName(c.getString(c
						.getColumnIndex("depositname")));
			} else {
				try {
					bytes = c.getBlob(c.getColumnIndex("depositname"));
					sn = new String(bytes, "utf-8");
				} catch (UnsupportedEncodingException e2) {
					e2.printStackTrace();
				}
				deposit.setDepositName(sn);
			}
			depositTime = c.getString(c.getColumnIndex("deposittime"));
			try {
				deposit.setDepositTime(sdf.parse(depositTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			deposit.setInterest(c.getFloat(c.getColumnIndex("interest")));
			deposit.setMood(c.getInt(c.getColumnIndex("mood")));
			deposit.setPicName(c.getString(c.getColumnIndex("picname")));
			isWithdraw = c.getInt(c.getColumnIndex("iswithdraw"));
			if (isWithdraw == 1) {
				deposit.setWithdraw(true);
			} else {
				deposit.setWithdraw(false);
			}
			list.add(deposit);
		}
		dd.close();
		return list;
	}

	public ArrayList<Deposit> orderByMood(ArrayList<Deposit> list) {
		Comparator<Deposit> comparator = new Comparator<Deposit>() {
			public int compare(Deposit d1, Deposit d2) {
				if (d1.getMood() != d2.getMood()) {// 先排心情
					return d2.getMood() - d1.getMood();
				} else {// 心情相同则按id排序
					return d1.getDepositID() - d2.getDepositID();
				}
			}
		};
		Collections.sort(list, comparator);
		return list;
	}

	public boolean withdrawals(Context ctx, int depositID) {

		dd = new DepositDAO(ctx);
		ud = new UserDAO(ctx);
		float deposit = 0;
		float happybi = 0;
		int bankID;
		
		dd.open();
		Cursor c = dd.QueryDepositByDepositID(depositID);
		c.moveToNext();
		deposit = c.getFloat(c.getColumnIndex("depositbegin"))
				+ c.getFloat(c.getColumnIndex("interest"));
		bankID = c.getInt(c.getColumnIndex("bankid"));
		dd.close();

		ud.open();
		Cursor d = ud.QueryUserALL();
		d.moveToNext();
		happybi = d.getFloat(d.getColumnIndex("happybi"));
		ud.close();

		happybi = happybi + deposit * Config.DEPOSIT_GENERATE_HAPPYBI;

		ud.open();
		ud.updataHappyBi(happybi);
		ud.close();

		dd.open();
		dd.updataIsWithdraw(depositID, true);
		dd.close();
		
		bs = new BankService();
		bs.calculateDeposit(ctx, bankID);

		return true;
	}

	public ArrayList<Deposit> searchDeposit(Context ctx, String depositName,
			int bankID) {
		ArrayList<Deposit> list = new ArrayList<Deposit>();
		int isWithdraw;
		String depositTime;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		byte bytes[];
		String sn = null;

		dd = new DepositDAO(ctx);
		dd.open();
		Cursor c = dd.QueryDepositByName(bankID, depositName);
		int count = c.getCount();
		if (count == 0) {
			return null;
		}
		while (c.moveToNext()) {
			Deposit deposit = new Deposit();
			deposit.setBankID(c.getInt(c.getColumnIndex("bankid")));
			deposit.setDepositBegin(c.getFloat(c.getColumnIndex("depositbegin")));
			if (c.getString(c.getColumnIndex("depositcontent")).matches(
					"[0-9]+")) {// 判断是否是纯数字
				deposit.setDepositContent(c.getString(c
						.getColumnIndex("depositcontent")));// 直接读取
			} else {// 转为汉语读入
				try {
					bytes = c.getBlob(c.getColumnIndex("depositcontent"));
					sn = new String(bytes, "utf-8");
				} catch (UnsupportedEncodingException e2) {
					e2.printStackTrace();
				}
				deposit.setDepositContent(sn);
			}
			deposit.setDepositID(c.getInt(c.getColumnIndex("depositid")));
			if (c.getString(c.getColumnIndex("depositname")).matches("[0-9]+")) {
				deposit.setDepositName(c.getString(c
						.getColumnIndex("depositname")));
			} else {
				try {
					bytes = c.getBlob(c.getColumnIndex("depositname"));
					sn = new String(bytes, "utf-8");
				} catch (UnsupportedEncodingException e2) {
					e2.printStackTrace();
				}
				deposit.setDepositName(sn);
			}
			depositTime = c.getString(c.getColumnIndex("deposittime"));
			try {
				deposit.setDepositTime(sdf.parse(depositTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			deposit.setInterest(c.getFloat(c.getColumnIndex("interest")));
			deposit.setMood(c.getInt(c.getColumnIndex("mood")));
			deposit.setPicName(c.getString(c.getColumnIndex("picname")));
			isWithdraw = c.getInt(c.getColumnIndex("iswithdraw"));
			if (isWithdraw == 1) {
				deposit.setWithdraw(true);
			} else {
				deposit.setWithdraw(false);
			}
			list.add(deposit);
		}
		dd.close();
		return list;
	}

	public ArrayList<Deposit> getRightDeposit(Time t) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAPicName(Context ctx) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");// 设置日期格式
		String depositTime = sdf.format(new Date()).toString();
		return depositTime;
	}

}
