package com.happybank.service;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;

import com.happybank.dal.SoupDAO;
import com.happybank.model.Soup;

public class SoupService {

	private SoupDAO sd = null;

	public Map<String, String> getSoupLocate() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Soup> getSoupList() {
		// TODO Auto-generated method stub
		return null;
	}

	// 初始设置，导入心灵鸡汤到数据库
	public void initSoupList(String soupID, String soupName,
			String soupContent, String soupPic, Context ctx) {
		sd = new SoupDAO(ctx);
		sd.open();
		sd.initSoup(soupID, soupName, soupContent, soupPic);
		sd.close();
	}

	// 得到心灵鸡汤
	public Soup getSoupByID(int soupid, Context ctx) {
		sd = new SoupDAO(ctx);
		Soup soup = new Soup();
		Cursor c;
		sd.open();
		if (soupid > 100)
			soupid = soupid - 100;
		c = sd.QuerySoupByID(soupid);
		while (c.moveToNext()) {
			soup.setSoupID(c.getInt(c.getColumnIndex("soupid")));
			soup.setSoupName(c.getString(c.getColumnIndex("soupname")));
			soup.setSoupContent(c.getString(c.getColumnIndex("soupcontent")));
			soup.setSoupPic(c.getString(c.getColumnIndex("souppic")));
		}
		sd.close();
		return soup;
	}

	public ArrayList<Soup> getSoupList(int id, Context ctx) {
		// TODO Auto-generated method stub
		ArrayList<Soup> list = new ArrayList<Soup>();

		sd = new SoupDAO(ctx);
		sd.open();
		Cursor c;
		int soupid = 0;
		for (int i = id + 9; i >= id; i--) {
			if (i > 100)
				soupid = i - 100;
			else
				soupid = i;
			c = sd.QuerySoupByID(soupid);
			while (c.moveToNext()) {
				Soup soup = new Soup();
				soup.setSoupID(c.getInt(c.getColumnIndex("soupid")));
				soup.setSoupName(c.getString(c.getColumnIndex("soupname")));
				soup.setSoupContent(c.getString(c.getColumnIndex("soupcontent")));
				soup.setSoupPic(c.getString(c.getColumnIndex("souppic")));
				list.add(soup);
			}
		}
		sd.close();
		return list;
	}

}
