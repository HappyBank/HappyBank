package com.happybank.activity;

import com.example.happybank.R;
import com.happybank.config.Global;
import com.happybank.dal.MD5DAO;
import com.happybank.service.SettingService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class PasswordModifyActivity extends Activity {

	private ImageButton back;
	private EditText old_pwd,new_pwd,new_pwd_again;
	private RelativeLayout sure;
	private MD5DAO md5 = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Global.theme == 0){
			setTheme(R.style.WhiteTheme);
		}else{
			setTheme(R.style.BlackTheme);
		}
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_password_modify);
		
		old_pwd = (EditText) findViewById(R.id.pwdmodify_old);
		new_pwd = (EditText) findViewById(R.id.pwdmodify_new);
		new_pwd_again = (EditText) findViewById(R.id.pwdmodify_again);
		
		back = (ImageButton) findViewById(R.id.title_btn);
		back.setOnClickListener(new ClickEvent());
		sure = (RelativeLayout) findViewById(R.id.pwdmodify_sure);
		sure.setOnClickListener(new ClickEvent());
	}
	
	// ͳһ�������¼�
		class ClickEvent implements OnClickListener {

			@Override
			public void onClick(View v) {
				if(v == back){
//					Intent intent = new Intent();
//					intent.setClass(PasswordModifyActivity.this,
//							PasswordActivity.class);
//					startActivity(intent);
					finish();
				}
				if(v == sure){
					md5 = new MD5DAO();
					//����û�������
					String password = md5.getMD5(old_pwd.getText().toString());
					String new_password = md5.getMD5(new_pwd.getText().toString());
					String new_again = md5.getMD5(new_pwd_again.getText().toString());
					//����modifyPassword()����
					SettingService set = new SettingService();
					String result = set.modifyPassword(PasswordModifyActivity.this,password,new_password,new_again);
					//�ж�ִ�н��
					if(result.equals("empty")){
						Toast.makeText(PasswordModifyActivity.this, "�뽫��Ϣ��д����!",Toast.LENGTH_SHORT ).show();
					}
					if(result.equals("nomatch")){
						Toast.makeText(PasswordModifyActivity.this, "����������д��һ��!",Toast.LENGTH_SHORT ).show();
					}
					if(result.equals("error")){
						Toast.makeText(PasswordModifyActivity.this, "ԭ�������!",Toast.LENGTH_SHORT ).show();
					}
					if(result.equals("success")){
						Toast.makeText(PasswordModifyActivity.this, "�����޸ĳɹ�!",Toast.LENGTH_SHORT ).show();
						//����Ӧ��
						final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());  
				        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
				        startActivity(intent);
				        finish();
					}
				}
			}
		}


}