package com.example.happybank;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainViewActivity extends Activity {

	private Button button_newbank; // 新建银行
	private Button button_bank1; // 银行1
	private Button button_bank2; // 银行2
	private Button button_bank3; // 银行3
	private Button button_mood; // 心情曲线
	private Button button_soup; // 心灵鸡汤
	private Button button_set; // 设置按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_view);
		initMainView();
	}

	private void initMainView() {
		// 添加事件监听器
		// 新建银行
		button_newbank = (Button) this.findViewById(R.id.button_newbank);
		button_newbank.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AddBank(MainViewActivity.this);

			}
		});

		// 银行1
		button_bank1 = (Button) this.findViewById(R.id.button_bank1);
		button_bank1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		
		
		

		// 代新建存款
		button_bank2 = (Button) this.findViewById(R.id.Button_bank2);
		button_bank2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainViewActivity.this, DepositActivity.class);
				startActivity(intent);
			}
		});

		//代查看存款
		button_bank3 = (Button) this.findViewById(R.id.Button_bank3);
		button_bank3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub  DepositInfoActivit
				Intent intent = new Intent();
				intent.setClass(MainViewActivity.this, DepositInfoActivity.class);
				startActivity(intent);
			}
		});
		
		
		
		

		// 心情曲线
		button_mood = (Button) this.findViewById(R.id.button_mood);
		button_mood.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		// 心灵鸡汤
		button_soup = (Button) this.findViewById(R.id.button_soup);
		button_soup.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		// 设置
		button_set = (Button) this.findViewById(R.id.button_set);
		button_set.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainViewActivity.this, SettingActivity.class);
				startActivity(intent);
			}
		});
	}

	// 显示基于Layout的AlertDialog
	private void AddBank(Context context) {
		LayoutInflater inflater = LayoutInflater.from(this);
		final View textEntryView = inflater.inflate(R.layout.newbank, null);
		// final EditText
		// edtInput=(EditText)textEntryView.findViewById(R.id.name_Input);
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		builder.setIcon(R.drawable.newbank);
		builder.setTitle("新建银行");
		builder.setView(textEntryView);
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// setTitle("");
			}
		});
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// setTitle(edtInput.getText());
			}
		});
		builder.show();
	}

}
