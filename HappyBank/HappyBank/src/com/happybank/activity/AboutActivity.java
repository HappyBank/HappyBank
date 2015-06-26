package com.happybank.activity;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import com.example.happybank.R;
import com.happybank.config.Config;
import com.happybank.config.Global;
import com.happybank.service.NetworkHelperService;
import com.happybank.service.ParseXmlService;
import com.happybank.service.UpdateService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableRow;

public class AboutActivity extends Activity {

	private LinearLayout back;
	private TableRow function,update;//显示欢迎，功能介绍，检查更新 按钮
	private NetworkHelperService network;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Global.theme == 0){
			setTheme(R.style.WhiteTheme);
		}else{
			setTheme(R.style.BlackTheme);
		}
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about);
		
		network = new NetworkHelperService();
		
		back = (LinearLayout) findViewById(R.id.title_btn);
		back.setOnClickListener(new ClickEvent());
		function = (TableRow) findViewById(R.id.setting_table_row2);
		function.setOnClickListener(new ClickEvent());
		update = (TableRow) findViewById(R.id.setting_table_row3);
		update.setOnClickListener(new ClickEvent());
	}
	
	// 统一处理按键事件
	class ClickEvent implements OnClickListener {
		@Override
		public void onClick(View v) {
			if(v == back){
//				Intent intent = new Intent();
//				intent.setClass(AboutActivity.this, SettingActivity.class);
//				startActivity(intent);
				finish();
			}
			if(v == function){
				Intent intent = new Intent();
				intent.setClass(AboutActivity.this, AboutFunctionActivity.class);
				startActivity(intent);
				//finish();
			}
			if(v == update){
				if(network.isNetworkConnected(AboutActivity.this)){
					new HttpGetTask().execute();
				}else{
					final AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
					builder.setCancelable(false);
					builder.setTitle("提示");
					builder.setMessage("无法连接到服务器,请检查您的网络连接！");
					builder.setNegativeButton("确  定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							// setTitle("");
//							Toast.makeText(MainViewActivity.this, "您已取消操作！",
//									Toast.LENGTH_SHORT).show();
							dialog.dismiss();
						}
					});
					builder.show();
				}
			}
			
		}
	}

	class HttpGetTask extends AsyncTask<Void, Void, HashMap<String,String>>{

		AndroidHttpClient mClient = AndroidHttpClient.newInstance("");
		@Override
		protected HashMap<String,String> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			HttpGet request = new HttpGet(Config.APP_VERSION_XML);
//			Log.i("xml","打开连接，准备解析");
			ParseXmlService parseXml = new ParseXmlService();
			try {
				return mClient.execute(request, parseXml);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(HashMap<String,String> result){
			if (null != mClient)
				mClient.close();
			UpdateService us = new UpdateService(AboutActivity.this,result.get("version"),result.get("name"),result.get("url"));
			us.checkUpdate();
//			Log.i("xml","解析完毕");
//			Log.i("xml",result.get("version"));
//			Log.i("xml",result.get("name"));
//			Log.i("xml",result.get("url"));
		}
		
	}
}
