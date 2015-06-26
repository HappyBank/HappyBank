package com.happybank.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.happybank.R;
import com.example.happybank.R.id;
import com.example.happybank.R.layout;
import com.example.happybank.R.menu;
import com.happybank.config.Global;
import com.happybank.model.Soup;
import com.happybank.service.SoupService;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.os.Build;

public class SoupActivity extends Activity {

	// ���ذ�ť
	private Button button_back;
	// ��ð�ť
	private Button button_getSoup;
	// �б���ʾ
	private ListView listview = null;

	// ����soupservice����
	private SoupService soupservice = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Global.theme == 0){
			setTheme(R.style.WhiteTheme);
		}else{
			setTheme(R.style.BlackTheme);
		}
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_soup);

		listview = (ListView) findViewById(R.id.soupinfo_listView);
		// ���б�չʾ���鼦��
		ShowSoupByList();

		// ����������
		button_back = (Button) findViewById(R.id.soup_bt_back);
		button_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent(SoupActivity.this,
				// MainViewActivity.class);
				// startActivity(intent);
				finish();
			}
		});

		// ����µ����鼦��
		button_getSoup = (Button) this.findViewById(R.id.soup_btn_getsoup);
		button_getSoup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(SoupActivity.this).setTitle("���鼦��")
						.setMessage("������ͨ����������鼦��~")
						.setPositiveButton("�õ�", null).show();
			}
		});
	}

	private void ShowSoupByList() {

		List<Soup> soups;
		soups = Global.souplist;

		// ��ȡ����������
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

		for (Soup soup : soups) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("soupid", soup.getSoupID());
			item.put("soupname", soup.getSoupName());
			item.put("soupcontent", soup.getSoupContent());
			item.put("souppic", soup.getSoupPic());
			data.add(item);
		}

		SimpleAdapter adapter = new SimpleAdapter(this, data,
				R.layout.souplist_item,
				new String[] { "souppic", "soupcontent" }, new int[] {
						R.id.soup_icon, R.id.soupinfo_content });

		// ʵ���б����ʾ
		listview.setAdapter(adapter);

	}
}
