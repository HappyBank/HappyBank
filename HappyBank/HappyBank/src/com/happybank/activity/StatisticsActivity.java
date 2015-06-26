package com.happybank.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.happybank.R;
import com.happybank.config.Global;
import com.happybank.service.MoodStatisticalService;

public class StatisticsActivity extends Activity{
	
	//心情指数
	private int temp1,temp2,temp3;
	
	// 对象
	private MoodStatisticalService mss = null;
	
	// 控件
	private RelativeLayout rl1 = null;
	private LinearLayout back = null;
	private TextView tv_happybi = null;
	private TextView tv_bank = null;
	private TextView tv_deposittiaoshu = null;
	private TextView tv_depositzonge = null;
	private TextView tv_deposithistory = null;
	private TextView tv_happy = null;
	private TextView tv_unhappy = null;
	
	private TextView tv_mood_ri = null;
	private TextView tv_mood_zhou = null;
	private TextView tv_mood_yue = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Global.theme == 0){
			setTheme(R.style.WhiteTheme);
		}else{
			setTheme(R.style.BlackTheme);
		}
		setContentView(R.layout.activity_statistics);

		// 创建对象
		mss = new MoodStatisticalService();
		
		back = (LinearLayout) findViewById(R.id.title_btn);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		tv_happybi = (TextView) super.findViewById(R.id.statistics_1_tv_happybi);
		tv_happybi.setText(String.valueOf(mss.getHappyBi(StatisticsActivity.this)));
		
		tv_bank = (TextView) super.findViewById(R.id.statistics_1_tv_bank);
		tv_bank.setText(String.valueOf(mss.getBankNum(StatisticsActivity.this)));
		
		tv_deposittiaoshu = (TextView) super.findViewById(R.id.statistics_1_tv_deposittiaoshu);
		tv_deposittiaoshu.setText(String.valueOf(mss.getDeposit(StatisticsActivity.this)));
		
		tv_depositzonge = (TextView) super.findViewById(R.id.statistics_1_tv_depositzonge);
		tv_depositzonge.setText(String.valueOf(mss.getDepositZonge(StatisticsActivity.this)));
		
		tv_deposithistory = (TextView) super.findViewById(R.id.statistics_1_tv_deposithistory);
		tv_deposithistory.setText(String.valueOf(mss.getDepositAll(StatisticsActivity.this)));
		
		tv_happy = (TextView) super.findViewById(R.id.statistics_1_tv_happy);
		tv_happy.setText(String.valueOf(mss.getHappyAll(StatisticsActivity.this)));
		
		tv_unhappy = (TextView) super.findViewById(R.id.statistics_1_tv_unhappy);
		tv_unhappy.setText(String.valueOf(mss.getUnhappyAll(StatisticsActivity.this)));
		
		tv_mood_ri = (TextView) super.findViewById(R.id.statistics_2_tv_ri);
		int temp1 = mss.getMoodRi(StatisticsActivity.this);
		if(temp1>=90){
			tv_mood_ri.setTextColor(Color.parseColor("#00CC00"));
		}else if(temp1>=75 && temp1<90){
			tv_mood_ri.setTextColor(Color.parseColor("#FFCC00"));
		}else if((temp1>=60 && temp1<75) || temp1==0){
			tv_mood_ri.setTextColor(Color.parseColor("#000000"));
		}else{
			tv_mood_ri.setTextColor(Color.parseColor("#CC0000"));
		}
		tv_mood_ri.setText(String.valueOf(temp1));
		
		tv_mood_zhou = (TextView) super.findViewById(R.id.statistics_2_tv_zhou);
		int temp2 = mss.getMoodZhou(StatisticsActivity.this);
		if(temp2>=90){
			tv_mood_zhou.setTextColor(Color.parseColor("#00CC00"));
		}else if(temp2>=75 && temp2<90){
			tv_mood_zhou.setTextColor(Color.parseColor("#FFCC00"));
		}else if((temp2>=60 && temp2<75) || temp2==0){
			tv_mood_zhou.setTextColor(Color.parseColor("#000000"));
		}else{
			tv_mood_zhou.setTextColor(Color.parseColor("#CC0000"));
		}
		tv_mood_zhou.setText(String.valueOf(temp2));

		
		tv_mood_yue = (TextView) super.findViewById(R.id.statistics_2_tv_yue);
		int temp3 = mss.getMoodYue(StatisticsActivity.this);
		if(temp3>=90){
			tv_mood_yue.setTextColor(Color.parseColor("#00CC00"));
		}else if(temp3>=75 && temp3<90){
			tv_mood_yue.setTextColor(Color.parseColor("#FFCC00"));
		}else if((temp3>=60 && temp3<75) || temp3==0){
			tv_mood_yue.setTextColor(Color.parseColor("#000000"));
		}else{
			tv_mood_yue.setTextColor(Color.parseColor("#CC0000"));
		}
		tv_mood_yue.setText(String.valueOf(temp3));
	}
}
