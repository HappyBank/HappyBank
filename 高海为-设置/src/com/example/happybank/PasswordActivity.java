package com.example.happybank;

import com.example.happybank.SettingActivity.ClickEvent;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TableRow;
import android.os.Build;

public class PasswordActivity extends ActionBarActivity {

	private TableRow tr_password_set,tr_patternlock_set;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_password_set);
		
		tr_password_set = (TableRow) findViewById(R.id.password_table_row1);
		tr_password_set.setOnClickListener(new ClickEvent()); 
		tr_patternlock_set = (TableRow) findViewById(R.id.password_table_row2);
		tr_patternlock_set.setOnClickListener(new ClickEvent()); 
	}
	

	//统一处理按键事件  
		class ClickEvent implements OnClickListener{

			@Override
			public void onClick(View v) {
				if(v ==  tr_password_set){
					Intent intent = new Intent();
		        	intent.setClass(PasswordActivity.this,PasswordModifyActivity.class);
		        	startActivity(intent);
				}
				if(v == tr_patternlock_set){
					
				}
				
			}
		}
}
