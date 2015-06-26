package com.example.happybank;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TableRow;

public class PasswordActivity extends Activity {

	private TableRow tr_password_set,tr_patternlock_set;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_password);

		tr_password_set = (TableRow) findViewById(R.id.password_table_row1);
		tr_password_set.setOnClickListener(new ClickEvent());
		tr_patternlock_set = (TableRow) findViewById(R.id.password_table_row2);
		tr_patternlock_set.setOnClickListener(new ClickEvent());
	}

	// 统一处理按键事件
	class ClickEvent implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (v == tr_password_set) {
				Intent intent = new Intent();
				intent.setClass(PasswordActivity.this,
						PasswordModifyActivity.class);
				startActivity(intent);
			}
			if (v == tr_patternlock_set) {

			}

		}
	}

}
