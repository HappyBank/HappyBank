package com.example.happybank;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	private Button newBank;
	private Button setting;
	
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		
		newBank = (Button) this.findViewById(R.id.new_bank);
		newBank.setOnClickListener(new ClickEvent());  
		setting = (Button) this.findViewById(R.id.setting);
		setting.setOnClickListener(new ClickEvent()); 
	}
	
	//统一处理按键事件  
	class ClickEvent implements OnClickListener{  
 
       @Override  
       public void onClick(View v) {  
           // TODO Auto-generated method stub  
          if(v == newBank){
       	      AddBank(MainActivity.this);
          }
          if(v == setting){
        	 Intent intent = new Intent();
        	 intent.setClass(MainActivity.this,SettingActivity.class);
        	 startActivity(intent);
          }
       }
    }
    
    //显示基于Layout的AlertDialog  
    private void AddBank(Context context){
    	 LayoutInflater inflater = LayoutInflater.from(this);  
         final View textEntryView = inflater.inflate(  
                 R.layout.newbank, null);  
        // final EditText edtInput=(EditText)textEntryView.findViewById(R.id.name_Input);  
         final AlertDialog.Builder builder = new AlertDialog.Builder(context);  
         builder.setCancelable(false);  
         builder.setIcon(R.drawable.newbank);  
         builder.setTitle("新建银行");  
         builder.setView(textEntryView);
         builder.setNegativeButton("取消",  
                 new DialogInterface.OnClickListener() {  
                     public void onClick(DialogInterface dialog, int whichButton) {  
                         //setTitle("");  
                     }  
         });
         builder.setPositiveButton("确认",  
                 new DialogInterface.OnClickListener() {  
                     public void onClick(DialogInterface dialog, int whichButton) {  
                         //setTitle(edtInput.getText());  
                     }  
                 });  
         builder.show();  
     } 
}