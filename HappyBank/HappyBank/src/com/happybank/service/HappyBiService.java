package com.happybank.service;

import android.content.Context;
import android.database.Cursor;

import com.happybank.dal.UserDAO;

public class HappyBiService {

	private UserDAO ud = null;
	
	public float getHappyBi(Context ctx) {
		ud = new UserDAO(ctx);
		ud.open();
		Cursor c = ud.QueryUserALL();
		c.moveToNext();
		float h = c.getFloat(c.getColumnIndex("happybi"));
		ud.close();
		return h;
	}

	
	public boolean setHappyBi(Context ctx, float happyBi) {
		ud = new UserDAO(ctx);
		ud.open();
		ud.updataHappyBi(happyBi);
		ud.close();
		return true;
	}
	
	public boolean addHappyBi(Context ctx, float add){
		float happybi = getHappyBi(ctx);
		happybi += add;
		setHappyBi(ctx, happybi);
		return true;
	}
}
