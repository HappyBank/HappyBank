package com.example.happybank;

import com.example.happybank.SettingActivity.ClickEvent;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.os.Build;

public class AboutActivity extends ActionBarActivity {

	private ImageButton back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_about_main);

		back = (ImageButton) findViewById(R.id.title_btn);
		back.setOnClickListener(new ClickEvent());
	}
	
	//统一处理按键事件  
	class ClickEvent implements OnClickListener{

	   @Override
	   public void onClick(View v) {
		
	   }
	}
}
