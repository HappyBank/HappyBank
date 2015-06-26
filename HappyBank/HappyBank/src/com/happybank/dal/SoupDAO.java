package com.happybank.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class SoupDAO extends BaseDAO {

	public SoupDAO(Context ctx) {
		super(ctx);
	}

	// ��ѯ
	public Cursor QuerySoupByID(int soupID) {
		String[] args = { String.valueOf(soupID) };
		return db.query("soup", null, "soupid = ?", args, null, null, null, null);
	}

	// ��ʼ���ã��������鼦�������ݿ�
	public long initSoup(String soupID, String soupName, String soupContent,
			String soupPic) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put("soupid", soupID);
		cv.put("soupname", soupName);
		cv.put("soupcontent", soupContent);
		cv.put("souppic", soupPic);
		return db.insert("soup", null, cv);
	}
	
}
