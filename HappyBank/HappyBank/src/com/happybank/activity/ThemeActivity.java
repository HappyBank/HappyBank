package com.happybank.activity;

import com.example.happybank.R;
import com.example.happybank.R.layout;
import com.happybank.config.Global;
import com.happybank.service.UserService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ThemeActivity extends Activity {

	private LinearLayout back = null;
	private TextView theme_1_click, theme_2_click;
	private Button theme_1_button,theme_2_button;
	private ImageView previewImage = null;
	private UserService us = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Global.theme == 0){
			setTheme(R.style.WhiteTheme);
		}else{
			setTheme(R.style.BlackTheme);
		}
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_theme);
		us = new UserService();
		
		back = (LinearLayout) findViewById(R.id.title_btn);
		back.setOnClickListener(new ClickEvent());
		theme_1_click = (TextView) findViewById(R.id.setting_text_row1);
		theme_1_click.setOnClickListener(new ClickEvent());
		theme_2_click = (TextView) findViewById(R.id.setting_text_row2);
		theme_2_click.setOnClickListener(new ClickEvent());
		theme_1_button = (Button) findViewById(R.id.theme_1_button);
		theme_1_button.setOnClickListener(new ClickEvent());
		theme_2_button = (Button) findViewById(R.id.theme_2_button);
		theme_2_button.setOnClickListener(new ClickEvent());
		previewImage = (ImageView) findViewById(R.id.setting_theme_preview);
		
		if(Global.theme == 0){
			theme_1_button.setText("应用中");
			theme_1_button.setEnabled(false);
			theme_2_button.setText("选择");
			theme_2_button.setEnabled(true);
			previewImage.setBackgroundResource(R.drawable.white);
		}else{
			theme_1_button.setText("选择");
			theme_1_button.setEnabled(true);
			theme_2_button.setText("应用中");
			theme_2_button.setEnabled(false);
			previewImage.setBackgroundResource(R.drawable.black);
		}
	}

	class ClickEvent implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == back){
				finish();
			}else if(v == theme_1_click){
				previewImage.setBackgroundResource(R.drawable.white);
			}else if(v == theme_2_click){
				previewImage.setBackgroundResource(R.drawable.black);
			}
			else if(v == theme_1_button){
				Global.theme = 0;
				us.updateTheme(ThemeActivity.this,Global.theme);
				Intent intent = new Intent().setAction("action.themeChanged");  
		        sendBroadcast(intent);
				onCreate(null);
			}
			else if(v == theme_2_button){
				Global.theme = 1;
				us.updateTheme(ThemeActivity.this,Global.theme);
				Intent intent = new Intent().setAction("action.themeChanged");  
				sendBroadcast(intent);
				onCreate(null);
			}
		}
		
	}
}
