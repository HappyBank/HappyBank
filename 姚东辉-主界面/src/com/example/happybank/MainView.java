package com.example.happybank;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainView extends Activity{
	private Button button_newbank; //新建银行
	private Button button_bank1;   //银行1
	private Button button_bank2;   //银行2
	private Button button_bank3;   //银行3
	private Button button_mood;    //心情曲线
	private Button button_soup;    //心灵鸡汤
	private Button button_set;     //设置按钮
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//隐藏标题栏

		//添加事件监听器
		//新建银行
		button_newbank = (Button)this.findViewById(R.id.button_newbank);
		button_newbank.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//银行1
		button_bank1 = (Button)this.findViewById(R.id.button_bank1);
		button_bank1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//心情曲线
		button_mood = (Button)this.findViewById(R.id.button_mood);
		button_mood.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//心灵鸡汤
		button_soup = (Button)this.findViewById(R.id.button_soup);
		button_soup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//设置
		button_set = (Button)this.findViewById(R.id.button_set);
		button_set.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
