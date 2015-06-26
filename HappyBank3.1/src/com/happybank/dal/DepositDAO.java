package com.happybank.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DepositDAO extends BaseDAO {

	public DepositDAO(Context ctx) {
		super(ctx);
	}

	// 插入
	public long insertDeposit(int bankID, String depositName,
			String depositTime, int mood, String depositContent,
			float depositBegin, float interest, String picName,
			boolean isWithdraw) {
		ContentValues cv = new ContentValues();
		cv.put("bankid", bankID);
		cv.put("depositname", depositName);
		cv.put("deposittime", depositTime);
		cv.put("mood", mood);
		cv.put("depositcontent", depositContent);
		cv.put("depositbegin", depositBegin);
		cv.put("interest", interest);
		cv.put("picname", picName);
		cv.put("iswithdraw", isWithdraw);
		return db.insert("deposit", null, cv);
	}

	// 删除
	public int deleteDeposit(int depositID) {// 设置级联删除貌似无效，手动删除Deposit信息
		String[] args = { String.valueOf(depositID) };
		return db.delete("deposit", "depositid = ?", args);
	}

	// 修改
	public int updataDepositName(int depositID, String depositName) {
		ContentValues cv = new ContentValues();
		cv.put("depositname", depositName);
		return db.update("deposit", cv, "depositid="+depositID, null);
	}

	public int updataDepositContent(int depositID, String depositContent) {
		ContentValues cv = new ContentValues();
		cv.put("depositcontent", depositContent);
		return db.update("deposit", cv, "depositid="+depositID, null);
	}

	public int updataInterest(int depositID, float interest) {
		ContentValues cv = new ContentValues();
		cv.put("interest", interest);
		return db.update("deposit", cv, "depositid="+depositID, null);
	}

	public int updataIsWithdraw(int depositID, boolean isWithdraw) {
		ContentValues cv = new ContentValues();
		cv.put("iswithdraw", isWithdraw);
		return db.update("deposit", cv, "depositid="+depositID, null);
	}

	// 查询
	public Cursor QueryDepositALL() {
		return db.query("deposit", null, null, null, null, null, null, null);
	}

	public Cursor QueryDepositByName(String depositName) {
		String[] args = { String.valueOf(depositName) };
		return db.query("deposit", null, "depositname = ?", args, null, null,
				null, null);
	}

}
