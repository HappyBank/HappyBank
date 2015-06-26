package com.happybank.dal;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	// ��û��ʵ����,�ǲ����������๹�����Ĳ���,��������Ϊ��̬
	private static final String name = "test"; // ���ݿ�����
	private static final int version = 9; // ���ݿ�汾
	
	// ���ݿ����
	static final String DATABASE_SET_ENABLE_CH = "PRAGMA encoding=\"UTF-8\"";
	static final String DATABASE_SET_FOREIGN_KEY_ON = "pragma foreign_keys = on;";
	static final String DATABASE_CREATE_USER = "create table user (password string, email string, "
			+ "happybi float, logindate date, theme int);";
	static final String DATABASE_CREATE_SOUP = "create table soup (soupid integer primary key autoincrement,"
			+ " soupname string, soupcontent string, souppic string)";
	static final String DATABASE_CREATE_SOFTWAREINFO = "create table softwareinfo (theme integer,"
			+ " soupstart integer, soupend integer)";
	static final String DATABASE_CREATE_BANK = "create table bank (bankid integer primary key autoincrement,"
			+ " bankname string, bankdesc string, bankdeposit float, extrainterest float)";
	static final String DATABASE_CREATE_DEPOSIT = "create table deposit (depositid integer primary key autoincrement,"
			+ " bankid integer, depositname string, deposittime datetime, mood integer,"
			+ " depositcontent string, depositbegin float, interest float, picname string, iswithdraw binary,"
			+ " foreign key (bankid) references bank(bankid))";
	
	public DatabaseHelper(Context context) {
		// ����������CursorFactoryָ����ִ�в�ѯʱ���һ���α�ʵ���Ĺ�����,����Ϊnull,����ʹ��ϵͳĬ�ϵĹ�����
		super(context, name, null, version);
	}

	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(DATABASE_SET_FOREIGN_KEY_ON);
			db.execSQL(DATABASE_CREATE_USER);
			db.execSQL(DATABASE_CREATE_SOUP);
			db.execSQL(DATABASE_CREATE_SOFTWAREINFO);
			db.execSQL(DATABASE_CREATE_BANK);
			db.execSQL(DATABASE_CREATE_DEPOSIT);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS user");
		db.execSQL("DROP TABLE IF EXISTS soup");
		db.execSQL("DROP TABLE IF EXISTS softwareinfo");
		db.execSQL("DROP TABLE IF EXISTS deposit");
		db.execSQL("DROP TABLE IF EXISTS bank");
		onCreate(db);
	}
	
	public boolean deleteDatabase(Context context) {
		  return context.deleteDatabase(name);
	}
}