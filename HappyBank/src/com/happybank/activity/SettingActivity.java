package com.happybank.activity;

import com.example.happybank.R;
import com.happybank.config.Global;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.Toast;

public class SettingActivity extends Activity {

	private TableRow tr_password_set, tr_theme_set, tr_about_set;
	private ImageButton back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		if(Global.theme == 0){
			setTheme(R.style.WhiteTheme);
		}else{
			setTheme(R.style.BlackTheme);
		}
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setting);
		
		IntentFilter intentFilter = new IntentFilter();  
	    intentFilter.addAction("action.themeChange");  
	    registerReceiver(mRefreshBroadcastReceiver, intentFilter); 
		
		back = (ImageButton) findViewById(R.id.title_btn);
		back.setOnClickListener(new ClickEvent());
		tr_password_set = (TableRow) findViewById(R.id.setting_table_row1);
		tr_password_set.setOnClickListener(new ClickEvent());
		tr_theme_set = (TableRow) findViewById(R.id.setting_table_row2);
		tr_theme_set.setOnClickListener(new ClickEvent());
		tr_about_set = (TableRow) findViewById(R.id.setting_table_row3);
		tr_about_set.setOnClickListener(new ClickEvent());
		
	}
	
	// broadcast receiver  
	  private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {  
	  
	      @Override  
	      public void onReceive(Context context, Intent intent) {  
	          String action = intent.getAction();  
	          if (action.equals("action.themeChange"))  
	          {  
	        	  onCreate(null);
	          }  
	      }  
	  };  

	// 统一处理按键事件
	class ClickEvent implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == back){
//				Intent intent = new Intent();
//				intent.setClass(SettingActivity.this, MainViewActivity.class);
//				startActivity(intent);
				finish();
			}
			if (v == tr_password_set) {
				Intent intent = new Intent();
				intent.setClass(SettingActivity.this, PasswordActivity.class);
				startActivity(intent);
				//finish();
			}
			if (v == tr_theme_set) {
				Intent intent = new Intent();
				intent.setClass(SettingActivity.this, ThemeActivity.class);
				startActivity(intent);
				//finish();
			}
			if (v == tr_about_set) {
				Intent intent = new Intent();
				intent.setClass(SettingActivity.this, AboutActivity.class);
				startActivity(intent);
				//finish();
			}
		}
	}
	
	@Override  
	protected void onDestroy() {  
		super.onDestroy();  
		Toast.makeText(this, "销毁了", Toast.LENGTH_LONG).show();
	    unregisterReceiver(mRefreshBroadcastReceiver);  
	} 
}
