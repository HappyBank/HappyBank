package com.happybank.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import com.happybank.config.Global;
import com.happybank.dal.UserDAO;

public class RegistService {

	private UserDAO ud = null;
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	public boolean QurryUser(Context ctx) {
		ud = new UserDAO(ctx);
		Log.i("info", ud.toString());
		ud.open();
		Cursor c = ud.QueryUserALL();
		if(c.getCount() == 0){
			Log.i("info", "新用户");
			ud.close();
			return false;
		}
		Log.i("info", "老用户");
		ud.close();
		return true;
	}

	public void RegistUser(Context ctx,
			String password) {
		ud = new UserDAO(ctx);
		ud.open();
		ud.insertUser(password, "ghw@123", 0, df.format(new Date()), 0);
		ud.close();
	}
}