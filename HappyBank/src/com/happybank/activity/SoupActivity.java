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

	// 返回按钮
	private Button button_back;
	// 获得按钮
	private Button button_getSoup;
	// 列表显示
	private ListView listview = null;

	// 创建soupservice对象
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
		// 用列表展示心灵鸡汤
		ShowSoupByList();

		// 返回主界面
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

		// 获得新的心灵鸡汤
		button_getSoup = (Button) this.findViewById(R.id.soup_btn_getsoup);
		button_getSoup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(SoupActivity.this).setTitle("心灵鸡汤")
						.setMessage("您可以通过存款获得心灵鸡汤~")
						.setPositiveButton("好的", null).show();
			}
		});
	}

	private void ShowSoupByList() {

		List<Soup> soups;
		soups = Global.souplist;

		// 获取到集合数据
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

		// 实现列表的显示
		listview.setAdapter(adapter);

	}
}
