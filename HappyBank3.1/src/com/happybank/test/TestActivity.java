package com.happybank.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.database.SQLException;

import com.example.happybank.R;
import com.example.happybank.R.layout;
import com.happybank.dal.BankDAO;
import com.happybank.dal.BaseDAO;
import com.happybank.dal.DepositDAO;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class TestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		TextView tv=new TextView(this);
		//TextView tv2=new TextView(this);
		tv.setText("start");
   		setContentView(tv);
   		BankDAO b = new BankDAO(this);
   		DepositDAO bd = new DepositDAO(this);
   		
//   		b.open();
//   		b.insertBank("fsq", "fsq&bank", 0, 0);
//   		b.close();
   		
   		b.open();
   		Cursor c = b.QueryBankByName("fsq");
   		c.moveToNext();
   		int i = c.getInt(c.getColumnIndex("bankid"));
   		b.close();
   		
   		b.open();
   		b.updataBankDesc(i, "ss");
   		b.close();
   		b.open();
   		b.updataExtraInterest(i, (float)2.22);
   		b.close();
   		
		bd.open();
		bd.insertDeposit(i, "lala", "2015-05", 1, "ssss", (float)1.2, 0, "", true);
		bd.close();
		
		bd.open();
		Cursor d = bd.QueryDepositByName("lala");
		d.moveToNext();
		int di = d.getInt(d.getColumnIndex("depositid"));
		bd.close();
		
		bd.open();
		bd.updataDepositContent(di, "8080");
		bd.close();
		bd.open();
		bd.updataInterest(di, (float)0.8);
		bd.close();
   		
   		
//   		b.open();
//   		Cursor c = b.QueryBankByName("fsq");
//   		c.moveToNext();
//   		int i = c.getInt(c.getColumnIndex("bankid"));
//   		b.close();
//   		b.open();
//   		Cursor d = b.QueryBankALL();
//   		b.close();
   		
//   		bd.open();
//   		Cursor d = bd.QueryDepositByName("lala");
//   		d.moveToNext();
//   		int j = d.getInt(d.getColumnIndex("depositid"));
//   		float k = d.getFloat(d.getColumnIndex("interest"));
//   		bd.close();
   		
//   		bd.open();
//   		bd.deleteDeposit(j);
//   		bd.close();
//   		bd.open();
//   		bd.updataDepositContent(j, "aaa");
//   		bd.close();bd.open();
//   		bd.updataDepositName(j, "lalala");
//   		bd.close();bd.open();
//   		bd.updataInterest(j, (float)1.2);
//   		bd.close();bd.open();
//   		bd.updataIsWithdraw(j, false);
//   		bd.close();
   		
   		tv.setText("ok"+"_");
   		
//		bd.insertMusic();
//		if(bd.readMusic() == true){
//			tv.setText("true!");
//			setContentView(tv);
//		}
		
	

	}
}
