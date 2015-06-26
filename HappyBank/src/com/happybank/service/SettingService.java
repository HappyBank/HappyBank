package com.happybank.service;

import android.content.Context;
import android.database.Cursor;
import com.happybank.dal.UserDAO;

public class SettingService {

	UserDAO ud = null;
	//ÐÞ¸ÄÃÜÂë
	public String modifyPassword(Context ctx,String password, String new_password,
			String new_again) {
		// TODO Auto-generated method stub
		if(password.equals("") || new_password.equals("") || new_again.equals("")){
			return "empty";
		}else{
			if(!new_password.equals(new_again)){
				return "nomatch";
			}else{
				ud = new UserDAO(ctx);
				ud.open();
				Cursor c = ud.QueryUserALL();
				c.moveToNext();
				ud.close();
				
				if(!password.equals(c.getString(c.getColumnIndex("password")))){
					return "error";
				}else{
					ud = new UserDAO(ctx);
					ud.open();
					ud.updataPassword(new_password);
					ud.close();
				}
			}
		}
		return "success";
	}

}
