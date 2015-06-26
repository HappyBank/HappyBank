package com.example.happybank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TableRow;

public class SettingActivity extends Activity {

	private TableRow tr_password_set, tr_theme_set, tr_about_set;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setting);

		tr_password_set = (TableRow) findViewById(R.id.setting_table_row1);
		tr_password_set.setOnClickListener(new ClickEvent());
		tr_theme_set = (TableRow) findViewById(R.id.setting_table_row2);
		tr_theme_set.setOnClickListener(new ClickEvent());
		tr_about_set = (TableRow) findViewById(R.id.setting_table_row3);
		tr_about_set.setOnClickListener(new ClickEvent());
	}

	// 统一处理按键事件
	class ClickEvent implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == tr_password_set) {
				Intent intent = new Intent();
				intent.setClass(SettingActivity.this, PasswordActivity.class);
				startActivity(intent);
			}
			if (v == tr_theme_set) {
				Intent intent = new Intent();
				intent.setClass(SettingActivity.this, ThemeActivity.class);
				startActivity(intent);
			}
			if (v == tr_about_set) {
				Intent intent = new Intent();
				intent.setClass(SettingActivity.this, AboutActivity.class);
				startActivity(intent);
			}
		}
	}

}
