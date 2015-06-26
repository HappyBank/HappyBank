package com.happybank.activity;

import java.io.File;

import com.example.happybank.R;
import com.happybank.config.Global;
import com.happybank.service.DepositService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DepositInfoActivity extends Activity {

	//对象
	private DepositService ds = null;
	
	// 参数
	private int mood;
	private int depositId;
	private int bankId;
	private float depositBegin;
	private float interest;
	private String depositName;
	private String time;
	private String depositContent;
	private boolean isWithdraw;
	private String picName;

	// 控件
	private TextView titleName = null;
	private TextView allDeposit = null;
	private TextView titleTime = null;
	private TextView content = null;
	private ImageView moodPic = null;
	private ImageView picture = null;
	private LinearLayout back = null;
	private Button withdraw = null;
	private ImageButton button_share;
		

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Global.theme == 0){
			setTheme(R.style.WhiteTheme);
		}else{
			setTheme(R.style.BlackTheme);
		}
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_deposit_info);

		//初始化对象
		ds = new DepositService();
		
		// 获取传递的数据
		Bundle bundleFromLastActivity = this.getIntent().getExtras();
		bankId = bundleFromLastActivity.getInt("bankID");
		depositId = bundleFromLastActivity.getInt("depositID");
		mood = bundleFromLastActivity.getInt("mood");
		depositBegin = bundleFromLastActivity.getFloat("depositBegin");
		interest = bundleFromLastActivity.getFloat("interest");
		depositName = bundleFromLastActivity.getString("depositName");
		time = bundleFromLastActivity.getString("depositTime");
		if (mood == 1){
			depositContent = bundleFromLastActivity.getString("depositContent");
		} else {
			depositContent = bundleFromLastActivity.getString("realContent");
		}
		isWithdraw = bundleFromLastActivity.getBoolean("isWithdraw");
		picName = bundleFromLastActivity.getString("picName");

		// 添加组件和设置参数
		back = (LinearLayout) super.findViewById(R.id.depositinfo_title1_btn_back);
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
//				Intent intent = new Intent(DepositInfoActivity.this,
//						DepositActivity.class);
//				Bundle bundle = new Bundle();
//				bundle.putInt("bankID", bankId);
//				intent.putExtras(bundle);
//				startActivity(intent);
				finish();
			}
		});

		titleName = (TextView) super.findViewById(R.id.depositinfo_title1_tv_title);
		titleName.setText(depositName);

		allDeposit = (TextView) super
				.findViewById(R.id.depositinfo_title1_tv_allDeposit);
		String tmp = String.valueOf(depositBegin) + "+" + String.valueOf(interest);
		allDeposit.setText(tmp);
		
		moodPic = (ImageView) super.findViewById(R.id.depositinfo_title2_image_mood);
		if (mood == 1) {
			moodPic.setImageResource(R.drawable.happy);
		} else {
			moodPic.setImageResource(R.drawable.unhappy);
		}
		
		titleTime = (TextView) super.findViewById(R.id.depositinfo_title2_tv_time);
		titleTime.setText(time);
		
		withdraw = (Button) super.findViewById(R.id.depositinfo_title2_btn_withdraw);
		if (isWithdraw == true) {
			withdraw.setVisibility(View.INVISIBLE);
		} else {
			withdraw.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					if (ds.withdrawals(DepositInfoActivity.this, depositId) == true){
						Toast.makeText(getApplicationContext(), "已取款", Toast.LENGTH_SHORT).show();
						
						Intent i = new Intent().setAction("action.withdrawDeposit");  
						sendBroadcast(i);
						
						withdraw.setVisibility(View.INVISIBLE);
					} else {
						Toast.makeText(getApplicationContext(), "取款失败", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
		
		content = (TextView) super.findViewById(R.id.depositinfo_content_tv_content);
		content.setText(depositContent);
		
		picture = (ImageView) super.findViewById(R.id.depositinfo_content_image_picture);
		if (picName != null && picName.length() != 0){
//			if (isSdcardExisting()) {
//				showImage(picName);
//			} else {
//				Toast.makeText(DepositInfoActivity.this, "未找到存储卡，无法显示照片",
//						Toast.LENGTH_LONG).show();
//			}
			final String state = Environment.getExternalStorageState();
			if (state.equals(Environment.MEDIA_MOUNTED)) {
		        Bitmap bitmap = null;
				bitmap = BitmapFactory.decodeFile(picName);
		        picture.setImageBitmap(bitmap);
			}
			
		}
		
		// 分享按钮
		button_share = (ImageButton) this.findViewById(R.id.soup_btn_share);
		button_share.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_SEND); 
				
		        if (picName == null || picName.equals("")) {  
		            intent.setType("text/plain"); // 纯文本  
		        } else {  
		        	intent.setType("image/*");
		        	//ComponentName comp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.tools.ShareToTimeLineUI");
		        	//intent.setComponent(comp);
		        	File file = new File(picName);
		        	intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));  
		        }  
		        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_GRANT_READ_URI_PERMISSION);
		        intent.putExtra(Intent.EXTRA_TEXT, "#"+depositName +"#"+depositContent);  
		        startActivity(Intent.createChooser(intent, "选择您想要分享的应用")); 
//				Intent sendIntent = new Intent();
//				sendIntent.setAction(Intent.ACTION_SEND);
//				if(picName != null){
//					//这里是你发送的文本
//					//当用户选择短信时使用sms_body取得文字  
//					sendIntent.putExtra("sms_body", "#"+depositName +"#"+depositContent+"草泥马");
//					sendIntent.putExtra(Intent.EXTRA_STREAM,picName);
//					sendIntent.setType("image/*");
//				}else{
//					sendIntent.setType("text/plain"); 
//				}
//				sendIntent.putExtra(Intent.EXTRA_TEXT,"#"+depositName +"#"+depositContent+"操你吧");
//				
//				startActivity(Intent.createChooser(sendIntent, "选择您想要分享的应用"));
			}
		});
	
	}

//	private void showImage(String path) {
//        //ContentResolver cr = this.getContentResolver();    
//        Bitmap bitmap = null;
//		bitmap = BitmapFactory.decodeFile(path);
//        picture.setImageBitmap(bitmap);
//	}
//	
//	private boolean isSdcardExisting() {
//		final String state = Environment.getExternalStorageState();
//		if (state.equals(Environment.MEDIA_MOUNTED)) {
//			return true;
//		} else {
//			return false;
//		}
//	}

}
