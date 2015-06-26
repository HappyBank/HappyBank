package com.happybank.service;

import android.content.Context;

import com.happybank.activity.ThemeActivity;
import com.happybank.dal.UserDAO;


public class UserService {

	private UserDAO ud = null;

	public void updateUser(float d_bankdeposit, Context ctx) {
		// TODO Auto-generated method stub
		ud = new UserDAO(ctx);
		ud.open();
		ud.updataHappyBi(d_bankdeposit);
		ud.close();
	}

	public void updateTheme(Context ctx, int theme) {
		// TODO Auto-generated method stub
		ud = new UserDAO(ctx);
		ud.open();
		ud.updateTheme(theme);
		ud.close();
	}

}