package com.happybank.dal;

import android.content.Context;
import android.database.Cursor;

public class SoupDAO extends BaseDAO{

	public SoupDAO(Context ctx) {
		super(ctx);
	}

	// ≤È—Ø
	public Cursor QuerySoupByID() {
		return db.query("soup", null, null, null, null, null, null, null);
	}
}
