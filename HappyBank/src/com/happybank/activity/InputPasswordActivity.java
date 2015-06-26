package com.happybank.activity;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import com.example.happybank.R;
import com.happybank.config.Config;
import com.happybank.config.Global;
import com.happybank.dal.MD5DAO;
import com.happybank.service.LoginService;
import com.happybank.service.NetworkHelperService;
import com.happybank.service.ParseXmlService;
import com.happybank.service.RegistService;
import com.happybank.service.UpdateService;

import android.app.Activity;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class InputPasswordActivity extends Activity {

	private TextView tip;
	private LinearLayout pfild;
	private EditText p1, p2, p3, p4;
	private ImageView point1,point2,point3,point4;
	private RegistService regist = null;
	private LoginService login = null;
	private MD5DAO md5 = null;
	private NetworkHelperService network = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_input_password);
		regist = new RegistService();
		login = new LoginService();
		md5 = new MD5DAO();
		network = new NetworkHelperService();
		
		initView();
		
//		if(network.isNetworkConnected(InputPasswordActivity.this)){
//			new HttpGetTask().execute();
//		}
	}
	
	private void initView() {
		p1 = (EditText)findViewById(R.id.password1);
		p2 = (EditText)findViewById(R.id.password2);
		p3 = (EditText)findViewById(R.id.password3);
		p4 = (EditText)findViewById(R.id.password4);

		point1 = (ImageView)findViewById(R.id.point1);
		point2 = (ImageView)findViewById(R.id.point2);
		point3 = (ImageView)findViewById(R.id.point3);
		point4 = (ImageView)findViewById(R.id.point4);
		
		tip = (TextView)findViewById(R.id.tip);
		
		pfild = (LinearLayout)findViewById(R.id.passwordfild);

	}
	
	public void one(View v){
		setValue(1+"");
	}
	
	public void tow(View v){
		setValue(2+"");
	}
	
	public void three(View v){
		setValue(3+"");
	}
	
	public void four(View v){
		setValue(4+"");
	}
	
	public void five(View v){
		setValue(5+"");
	}
	
	public void six(View v){
		setValue(6+"");
	}
	
	public void seven(View v){
		setValue(7+"");
	}
	
	public void eight(View v){
		setValue(8+"");
	}
	
	public void nine(View v){
		setValue(9+"");
	}
	
	public void zero(View v){
		setValue(0+"");
	}
	
	//设值
		private void setValue(String text){
			if (TextUtils.isEmpty(p1.getText())) {
				p1.setText(text);
				addPoint(1);
			}else if (TextUtils.isEmpty(p2.getText())) {
				p2.setText(text);
				addPoint(2);
			}else if (TextUtils.isEmpty(p3.getText())) {
				p3.setText(text);
				addPoint(3);
			}else if (TextUtils.isEmpty(p4.getText())) {
				p4.setText(text);
				addPoint(4);
				submit();
			}
		}
		
		//提交
		public void submit() {
			String password = p1.getText().toString() + p2.getText().toString()
					+ p3.getText().toString() + p4.getText().toString();
			System.out.println(password + "");
			if (!TextUtils.isEmpty(password) && password.length() == 4) {
				if(Global.exist){//已有用户，检测密码正确性
					if(login.CheckPassword(InputPasswordActivity.this,md5.getMD5(password))){
						Log.i("info", "登陆成功！");
						Toast.makeText(this, "欢迎使用！", Toast.LENGTH_SHORT).show();
						startActivity(new Intent(InputPasswordActivity.this, MainViewActivity.class));
						finish();
					}else{
						Toast.makeText(this, "密码不对哦！！", Toast.LENGTH_SHORT).show();
						p4.setText("");
						p3.setText("");
						p2.setText("");
						p1.setText("");
						addPoint(0);
					}
				}else{//尚未有用户，注册
					regist.RegistUser(InputPasswordActivity.this,md5.getMD5(password));
					Log.i("info", "注册成功！");
					Toast.makeText(this, "欢迎使用！", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(InputPasswordActivity.this, MainViewActivity.class));
					finish();
				}
//				Toast.makeText(this, "密码正确！", Toast.LENGTH_SHORT).show();
//				//做页面跳转
//				startActivity(new Intent(InputPasswordActivity.this, MainViewActivity.class));
//				finish();
			} else {
				Toast.makeText(this, "密码不对哦！！", Toast.LENGTH_SHORT).show();
				p4.setText("");
				p3.setText("");
				p2.setText("");
				p1.setText("");
				addPoint(0);
			}
		}
		
		private void addPoint(int position){
			switch (position) {
			case 0:
				point1.setVisibility(View.GONE);
				point2.setVisibility(View.GONE);
				point3.setVisibility(View.GONE);
				point4.setVisibility(View.GONE);
				break;
			case 1:
				point1.setVisibility(View.VISIBLE);
				point2.setVisibility(View.GONE);
				point3.setVisibility(View.GONE);
				point4.setVisibility(View.GONE);
				break;
			case 2:
				point2.setVisibility(View.VISIBLE);
				point1.setVisibility(View.VISIBLE);
				point3.setVisibility(View.GONE);
				point4.setVisibility(View.GONE);
				break;
			case 3:
				point3.setVisibility(View.VISIBLE);
				point1.setVisibility(View.VISIBLE);
				point2.setVisibility(View.VISIBLE);
				point4.setVisibility(View.GONE);
				break;
			case 4:
				point4.setVisibility(View.VISIBLE);
				point1.setVisibility(View.VISIBLE);
				point2.setVisibility(View.VISIBLE);
				point3.setVisibility(View.VISIBLE);
				break;

			default:
				break;
			}
		}
		
		class HttpGetTask extends AsyncTask<Void, Void, HashMap<String,String>>{

			AndroidHttpClient mClient = AndroidHttpClient.newInstance("");
			@Override
			protected HashMap<String,String> doInBackground(Void... params) {
				// TODO Auto-generated method stub
				HttpGet request = new HttpGet(Config.APP_VERSION_XML);
//				Log.i("xml","打开连接，准备解析");
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
				UpdateService us = new UpdateService(InputPasswordActivity.this,result.get("version"),result.get("name"),result.get("url"));
				us.checkUpdate();
//				Log.i("xml","解析完毕");
//				Log.i("xml",result.get("version"));
//				Log.i("xml",result.get("name"));
//				Log.i("xml",result.get("url"));
			}
			
		}
}
