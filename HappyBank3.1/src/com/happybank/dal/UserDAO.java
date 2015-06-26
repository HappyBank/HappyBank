package com.happybank.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class UserDAO extends BaseDAO{

	public UserDAO(Context ctx) {
		super(ctx);
	}
	
	//≤Â»Î
	public long insertUser(String password, String email, float happyBi,
			String loginDate) {
		ContentValues cv = new ContentValues();
		cv.put("password", password);
		cv.put("email", email);
		cv.put("happybi", happyBi);
		cv.put("logindate", loginDate);
		return db.insert("user", null, cv);
	}
	
	//–ﬁ∏ƒ
	public int updataPassword(String password) {
		ContentValues cv = new ContentValues();
		cv.put("password", password);
		return db.update("user", cv, null, null);
	}
	
	public int updataHappyBi(float happyBi) {
		ContentValues cv = new ContentValues();
		cv.put("happybi", happyBi);
		return db.update("user", cv, null, null);
	}
	
	public int updataLoginDate(String loginDate) {
		ContentValues cv = new ContentValues();
		cv.put("logindate", loginDate);
		return db.update("user", cv, null, null);
	}

	// ≤È—Ø
	public Cursor QueryUserALL() {
		return db.query("user", null, null, null, null, null, null, null);
	}
}
