package com.happybank.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class SoftwareDAO extends BaseDAO {

	public SoftwareDAO(Context ctx) {
		super(ctx);
	}

	// ÐÞ¸Ä
	public int updataTheme(int theme) {// Returns the
										// number of rows
										// affected
		ContentValues cv = new ContentValues();
		cv.put("theme", theme);
		return db.update("softwareinfo", cv, null, null);
	}

	public int updataSoupStart(int soupStart) {
		ContentValues cv = new ContentValues();
		cv.put("soupstart", soupStart);
		return db.update("softwareinfo", cv, null, null);
		// db.execSQL("update bank set bankdesc =1 where bankid = 1;");
		// return 0;
	}

	public int updataSoupEnd(int soupEnd) {
		ContentValues cv = new ContentValues();
		cv.put("soupend", soupEnd);
		return db.update("softwareinfo", cv, null, null);
	}

	// ²éÑ¯
	public Cursor QuerySoftwareinfoALL() {// Returns A Cursor object, which is
									// positioned before the first entry. Note
									// that Cursors are not synchronized, see
									// the documentation for more details.
		return db.query("softwareinfo", null, null, null, null, null, null,
				null);
	}

}
