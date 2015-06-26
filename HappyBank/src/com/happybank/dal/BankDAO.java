package com.happybank.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class BankDAO extends BaseDAO {

	public BankDAO(Context ctx) {
		super(ctx);
	}

	// 插入
	public long insertBank(String bankName, String bankDesc, float bankDeposit,
			float extraInterest) {// Returns the row ID of the newly inserted
									// row, or -1 if an error occurred
		ContentValues cv = new ContentValues();
		cv.put("bankname", bankName);
		cv.put("bankdesc", bankDesc);
		cv.put("bankdeposit", bankDeposit);
		cv.put("extrainterest", extraInterest);
		return db.insert("bank", null, cv);
	}

	// 删除
	public int deleteBank(int bankID) {// 设置级联删除貌似无效，手动删除Deposit信息
		String[] args = { String.valueOf(bankID) };
		db.delete("deposit", "bankid = ?", args);
		return db.delete("bank", "bankid = ?", args);// Returns the number of
														// rows affected if a
														// whereClause is passed
														// in, 0 otherwise. To
														// remove all rows and
														// get a count pass "1"
														// as the whereClause.
	}

	// 修改
	public int updataBankName(int bankID, String bankName) {// Returns the
															// number of rows
															// affected
		ContentValues cv = new ContentValues();
		cv.put("bankname", bankName);
		return db.update("bank", cv, "bankid="+bankID, null);
	}

	public int updataBankDesc(int bankID, String bankDesc) {
		ContentValues cv = new ContentValues();
		cv.put("bankdesc", bankDesc);
		return db.update("bank", cv, "bankid="+bankID, null);
	}

	public int updataBankDeposit(int bankID, float bankDeposit) {
		ContentValues cv = new ContentValues();
		cv.put("bankdeposit", bankDeposit);
		return db.update("bank", cv, "bankid="+bankID, null);
	}

	public int updataExtraInterest(int bankID, float ExtraInterest) {
		ContentValues cv = new ContentValues();
		cv.put("extrainterest", ExtraInterest);
		return db.update("bank", cv, "bankid="+bankID, null);
	}

	// 查询
	public Cursor QueryBankALL() {// Returns A Cursor object, which is
									// positioned before the first entry. Note
									// that Cursors are not synchronized, see
									// the documentation for more details.
		return db.query("bank", null, null, null, null, null, null, null);
	}

	public Cursor QueryBankByName(String bankName) {
//		String[] args = { String.valueOf(bankName) };
		return db.query("bank", null, "bankname like '%" + bankName + "%'", null, null,
				null, null, null);
	}

	
	public Cursor QueryBankByID(int bankID) {
		String[] args = { String.valueOf(bankID) };
		return db.query("bank", null, "bankid = ?", args, null,
				null, null, null);
	}

}
