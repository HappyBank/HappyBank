package com.happybank.dal;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class BaseDAO {
	DatabaseHelper DBHelper;
	SQLiteDatabase db;
    final Context context;

	public BaseDAO(Context ctx) {  
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
   }

	public BaseDAO open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		DBHelper.close();
	}

}
