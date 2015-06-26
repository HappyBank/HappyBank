package com.happybank.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.example.happybank.R;
import com.example.happybank.R.layout;
import com.happybank.config.Config;
import com.happybank.config.Global;
import com.happybank.dal.BankDAO;
import com.happybank.dal.UserDAO;
import com.happybank.service.RegistService;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

public class WelcomeActivity extends Activity {
	private RegistService regist = null;
	private TextView tv;
//	private boolean user_exist;
//	private BankDAO bd = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		//setContentView(R.layout.activity_search);
		tv = (TextView) findViewById(R.id.welcome_version);
		tv.setText("HappyBank V"+Config.LOCAL_VERSION);

		regist = new RegistService();
		Global.exist = regist.QurryUser(this);
		
//		bd = new BankDAO(this);
//		bd.open();
//		bd.insertBank("金针菇", "金针菇是撒比", 0, 0);
//		bd.insertBank("小旋旋", "小旋旋是撒比", 0, 0);
//		bd.insertBank("小辉辉", "小辉辉是撒比", 0, 0);
//		bd.insertBank("傻逼", "真TM是撒比", 0, 0);
//		bd.close();
		
		Timer timer = new Timer();
		TimerTask task = new TimerTask(){
			public void run() {
				if(Global.exist){
					Intent intent = new Intent(WelcomeActivity.this, InputPasswordActivity.class);
					startActivity(intent);
					finish();
				}else{
					Intent intent = new Intent(WelcomeActivity.this, GestureActivity.class);
					startActivity(intent);
					finish();
				}
			}
		};
		timer.schedule(task, 1000);
	}

}
