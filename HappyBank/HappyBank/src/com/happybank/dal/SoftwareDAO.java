package com.happybank.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class SoftwareDAO extends BaseDAO {

	public SoftwareDAO(Context ctx) {
		super(ctx);
	}

	// ≤Â»Î
	public long insertSoftwareInfo(int theme, int soupStart, String musicState) {// Returns the row ID of the newly inserted
									// row, or -1 if an error occurred
		ContentValues cv = new ContentValues();
		cv.put("theme", theme);
		cv.put("soupstart", soupStart);
		cv.put("musicstate", musicState);
		return db.insert("softwareinfo", null, cv);
	}

	// –ﬁ∏ƒ
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

	public int updataMusicState(String musicState) {
		ContentValues cv = new ContentValues();
		cv.put("musicstate", musicState);
		return db.update("softwareinfo", cv, null, null);
	}

	// ≤È—Ø
	public Cursor QuerySoftwareinfoALL() {// Returns A Cursor object, which is
		// positioned before the first entry. Note
		// that Cursors are not synchronized, see
		// the documentation for more details.
		return db.query("softwareinfo", null, null, null, null, null, null,
				null);
	}

}
