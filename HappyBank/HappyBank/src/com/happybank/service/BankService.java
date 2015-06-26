package com.happybank.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.happybank.activity.MainViewActivity;
import com.happybank.dal.BankDAO;
import com.happybank.dal.DepositDAO;
import com.happybank.model.Bank;
import com.happybank.model.Deposit;

public class BankService{

	private BankDAO bd = null;
	private DepositDAO dd = null;

	public boolean addBank(String newbank_name, String newbank_description,
			Context ctx) {
		bd = new BankDAO(ctx);
		bd.open();
		bd.insertBank(newbank_name, newbank_description, 0, 0);
		bd.close();
		return true;
	}

	public ArrayList<Bank> viewBank(Context ctx) {
		ArrayList<Bank> list = new ArrayList<Bank>();

		int happyDepositNum = 0,unhappyDepositNum = 0;
		bd = new BankDAO(ctx);
		dd = new DepositDAO(ctx);
		bd.open();
		Cursor c = bd.QueryBankALL();

		while (c.moveToNext()) {
			Bank bank = new Bank();
			int bankID = c.getInt(c.getColumnIndex("bankid"));
			bank.setBankID(bankID);
			bank.setBankName(c.getString(c.getColumnIndex("bankname")));
			bank.setBankDesc(c.getString(c.getColumnIndex("bankdesc")));
			bank.setBankDeposit(c.getFloat(c.getColumnIndex("bankdeposit")));
			bank.setExtraInterest(c.getFloat(c.getColumnIndex("extrainterest")));
			//统计各项存款数目
			dd.open();
			Cursor c1 = dd.QueryDepositByBankID(bankID);
			bank.setDepositNum(c1.getCount());
			while(c1.moveToNext()){
				if(c1.getInt(c1.getColumnIndex("mood")) == 1){
					happyDepositNum+=1;
				}else{
					unhappyDepositNum+=1;
				}
			}
			bank.setHappyDepositNum(happyDepositNum);
			bank.setUnhappyDepositNum(unhappyDepositNum);
			
			dd.close();
			list.add(bank);
			happyDepositNum = 0;
			unhappyDepositNum = 0;
		}
		
		bd.close();
		return list;
	}

	public Bank searchBank(Context ctx, int bankID) {
		Bank bank = new Bank();
		byte bytes[];
		String sn = null;
		
		bd = new BankDAO(ctx);
		bd.open();
		Cursor c = bd.QueryBankByID(bankID);
		if (c.getCount() == 0){
			bd.close();
			return null;
		}
		c.moveToNext();
		bank.setBankID(c.getInt(c.getColumnIndex("bankid")));
//		try {
//			bytes = c.getBlob(c.getColumnIndex("bankname"));
//			sn = new String(bytes,"gb2312");
//		} catch (UnsupportedEncodingException e2) {
//			e2.printStackTrace();
//		}
//		bank.setBankName(sn);
		if (c.getString(c.getColumnIndex("bankname")).matches(
				"[0-9]+")) {// 判断是否是纯数字
			bank.setBankName(c.getString(c
					.getColumnIndex("bankname")));// 直接读取
		} else {// 转为汉语读入
			try {
				bytes = c.getBlob(c.getColumnIndex("bankname"));
				sn = new String(bytes, "utf-8");
			} catch (UnsupportedEncodingException e2) {
				e2.printStackTrace();
			}
			bank.setBankName(sn);
		}
//		try {
//			bytes = c.getBlob(c.getColumnIndex("bankdesc"));
//			sn = new String(bytes,"gb2312");
//		} catch (UnsupportedEncodingException e2) {
//			e2.printStackTrace();
//		}
//		bank.setBankDesc(sn);
		if (c.getString(c.getColumnIndex("bankdesc")).matches(
				"[0-9]+")) {// 判断是否是纯数字
			bank.setBankDesc(c.getString(c
					.getColumnIndex("bankdesc")));// 直接读取
		} else {// 转为汉语读入
			try {
				bytes = c.getBlob(c.getColumnIndex("bankdesc"));
				sn = new String(bytes, "utf-8");
			} catch (UnsupportedEncodingException e2) {
				e2.printStackTrace();
			}
			bank.setBankDesc(sn);
		}

		bank.setBankDeposit(c.getFloat(c.getColumnIndex("bankdeposit")));
		bank.setExtraInterest(c.getFloat(c.getColumnIndex("extrainterest")));
		bd.close();
		return bank;
	}
	
	public ArrayList<Bank> searchBank(String searchbankname, Context ctx) {
		ArrayList<Bank> list = new ArrayList<Bank>();

		bd = new BankDAO(ctx);
		bd.open();
		Cursor c = bd.QueryBankByName(searchbankname);

		while (c.moveToNext()) {
			Bank bank = new Bank();
			bank.setBankID(c.getInt(c.getColumnIndex("bankid")));
			bank.setBankName(c.getString(c.getColumnIndex("bankname")));
			bank.setBankDesc(c.getString(c.getColumnIndex("bankdesc")));
			bank.setBankDeposit(c.getFloat(c.getColumnIndex("bankdeposit")));
			bank.setExtraInterest(c.getFloat(c.getColumnIndex("extrainterest")));
			list.add(bank);
		}
		bd.close();
		return list;
	}

	public boolean deleteBank(int bankid, Context ctx) {
		bd = new BankDAO(ctx);
		bd.open();
		bd.deleteBank(bankid);
		bd.close();
		return true;
	}

	public boolean updateBank(String updatebank_name,
			String updatebank_description, int bankid, Context ctx) {
		bd = new BankDAO(ctx);
		bd.open();
		bd.updataBankName(bankid, updatebank_name);
		bd.updataBankDesc(bankid, updatebank_description);
		bd.close();
		return true;
	}
	
	public boolean calculateDeposit(Context ctx, int bankID){
		bd = new BankDAO(ctx);
		dd = new DepositDAO(ctx);
		float deposit = 0f;
		
		dd.open();
		Cursor c = dd.QueryDepositByBankID(bankID);
		while (c.moveToNext()){
			if (c.getInt(c.getColumnIndex("iswithdraw")) == 0
					&& c.getInt(c.getColumnIndex("mood")) == 1){
				deposit += c.getFloat(c.getColumnIndex("depositbegin"));
				deposit += c.getFloat(c.getColumnIndex("interest"));
			}
		}
		dd.close();
		bd.open();
		bd.updataBankDeposit(bankID, deposit);
		bd.close();
		return true;
	}

	public int getDepositNum(Context ctx, int bankID) {
		// TODO Auto-generated method stub
		dd = new DepositDAO(ctx);
		dd.open();
		Cursor c = dd.QueryDepositByBankID(bankID);
		int depositnum = c.getCount();
		dd.close();
		return depositnum;
	}

	public List<Bank> orderByBankDepositNum(List<Bank> banks) {
		// TODO Auto-generated method stub
		Comparator <Bank> comparator = new Comparator<Bank>() {
			public int compare(Bank b1, Bank b2) {
				//先排存款数量，再排ID
				if(b2.getDepositNum() != b1.getDepositNum()){
					return b2.getDepositNum() - b1.getDepositNum();
				}else{
					return b2.getBankID() - b1.getBankID();
				}
			}
		};
		Collections.sort(banks, comparator);
		return banks;
	}
	
}
