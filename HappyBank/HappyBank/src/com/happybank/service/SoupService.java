package com.happybank.service;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;

import com.happybank.dal.SoftwareDAO;
import com.happybank.dal.SoupDAO;
import com.happybank.model.Soup;

public class SoupService {

	private SoupDAO sd = null;
	private SoftwareDAO swd = null;

	public Map<String, String> getSoupLocate() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Soup> getSoupList() {
		// TODO Auto-generated method stub
		return null;
	}

	// 初始设置，导入心灵鸡汤到数据库
	public void initSoupList(String str_soupID, String str_soupName,
			String str_soupContent, String str_soupPic, Context ctx) {
		// TODO Auto-generated method stub
		sd = new SoupDAO(ctx);
		sd.open();
		sd.initSoup(str_soupID, str_soupName, str_soupContent, str_soupPic);
		sd.close();
	}

	public void initSoftwareInfo(Context ctx) {
		// TODO Auto-generated method stub
		swd = new SoftwareDAO(ctx);
		swd.open();
		swd.insertSoftwareInfo(1, 1, "on");
		swd.close();
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

	public void updateSoupStart(int soupStart, Context ctx) {
		swd = new SoftwareDAO(ctx);
		swd.open();
		swd.updataSoupStart(soupStart);
		swd.close();
	}

	public int getSoupStart(Context ctx) {
		// TODO Auto-generated method stub
		swd = new SoftwareDAO(ctx);
		swd.open();
		Cursor c;
		int soupStart = 0;
		c = swd.QuerySoftwareinfoALL();
		if (c.moveToFirst()) {
			soupStart = c.getInt(c.getColumnIndex("soupstart"));
		}
		swd.close();
		return soupStart;
	}
	
}
