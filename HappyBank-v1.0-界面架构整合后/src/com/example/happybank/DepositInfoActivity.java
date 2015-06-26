package com.example.happybank;


import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

public class DepositInfoActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_deposit_info);
		
		final Button buttonsave = (Button) findViewById(R.id.buttonSave);
        buttonsave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	LayoutInflater inflater = getLayoutInflater();
            	View layout = inflater.inflate(R.layout.getmoney, (ViewGroup)findViewById(R.id.dialog));
            	
            	// TODO Auto-generated method stub  
                new AlertDialog.Builder(DepositInfoActivity.this).setTitle("取款").setView(layout)//设置对话框标题  
                //.setMessage("取款额度：")//设置显示的内容
                .setPositiveButton("确定",null)
                .setNegativeButton("取消",null).show();
            }
        });
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_view, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
