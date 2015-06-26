package com.happybank.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.happybank.R;
import com.happybank.config.Global;
import com.happybank.model.Bank;
import com.happybank.service.BankService;
import com.happybank.service.HappyBiService;
import com.happybank.service.MusicService;
import com.happybank.service.UserService;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainViewActivity extends Activity {

	private Button button_newbank; // 新建银行
	private Button button_searchbank; // 搜索银行
	private Button button_mood; // 心情曲线
	private Button button_soup; // 心灵鸡汤
	private Button button_set; // 设置按钮
	private ListView listview = null; // 列表显示

	// 用户输入的bankname和bankdescription
	private EditText et_newbank_name;
	private EditText et_newbank_description;
	private String newbank_name = null;
	private String newbank_description = null;

	// 创建bankservice对象
	private BankService banksercice = null;

	// 创建userservice对象
	private UserService userservice = null;

	// 用户点击的当前银行的bankid, bankname和bankdescription
	private int s_bankid = 0;
	private String s_bankname = null;
	private String s_bankdescription = null;

	// 删除银行的当前存款
	private float d_bankdeposit = 0;

	// 用户输入的搜索银行信息
	private EditText et_searchbankname = null;
	private String searchbankname = null;

	// 音乐相关
	private Intent intent = new Intent();
	private ImageButton imageBtn_music;
	private boolean music_on = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Global.theme == 0){
			setTheme(R.style.WhiteTheme);
		}else{
			setTheme(R.style.BlackTheme);
		}
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_view);
		
		IntentFilter intentFilter = new IntentFilter();  
	    intentFilter.addAction("action.themeChange");  
	    registerReceiver(mRefreshBroadcastReceiver, intentFilter); 

		// 初始化背景音乐
		intent.setClass(MainViewActivity.this, MusicService.class);
		startService(intent);

		userservice = new UserService();
		banksercice = new BankService();
		listview = (ListView) findViewById(R.id.bankinfo_listView);
		// 用列表展示银行
		ShowBanksByList(null);

		// 获取点击事件
		final class ItemClickListener implements OnItemClickListener {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ListView listView = (ListView) parent;
				@SuppressWarnings("unchecked")
				HashMap<String, Object> data = (HashMap<String, Object>) listView
						.getItemAtPosition(position);
				// 完成跳转
				Intent intent = new Intent(MainViewActivity.this,
						DepositActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("bankID", (int) data.get("bankid"));
				intent.putExtras(bundle);
				startActivity(intent);
				// finish();
			}
		}

		// 条目点击事件
		listview.setOnItemClickListener(new ItemClickListener());

		// 获取长击事件
		// 做数据处理，获得当前长击银行数据，这一步至关重要
		final class ItemLongClickListener implements OnItemLongClickListener {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				ListView listView = (ListView) parent;
				@SuppressWarnings("unchecked")
				HashMap<String, Object> data = (HashMap<String, Object>) listView
						.getItemAtPosition(position);
				s_bankid = (Integer) data.get("bankid");
				s_bankname = data.get("bankname").toString();
				s_bankdescription = data.get("bankdesc").toString();
				d_bankdeposit = (Float) data.get("bankdeposit");

				return false;
			}
		}

		listview.setOnItemLongClickListener(new ItemLongClickListener());

		final class OnCreateContextMenuListener implements
				android.view.View.OnCreateContextMenuListener {
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				menu.setHeaderTitle("选择操作");
				menu.add(0, 1, 0, "更新银行");
				menu.add(0, 2, 0, "删除银行");
			}
		}

		listview.setOnCreateContextMenuListener(new OnCreateContextMenuListener());

		// ////获取游标
		// Cursor c = bank.QurryBankInfo(MainViewActivity.this);
		// //创建SimpleCursorAdapter适配器将数据绑定到item显示控件上
		// SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
		// R.layout.banklist_show, c,
		// new String[]{"_id"}, new int[]{R.id.bankinfo_name});
		// listview.setAdapter(adapter);
		// bank.QurryBnakinfo();

		// 取得页面按钮并添加监听事件
		// 新建银行
		button_newbank = (Button) findViewById(R.id.button_newbank);
		button_newbank.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AddBank(MainViewActivity.this);

			}
		});

		// 查找银行
		button_searchbank = (Button) findViewById(R.id.main_view_bt_searchbank);
		button_searchbank.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				searchBank(MainViewActivity.this);
			}
		});

		// 心情曲线
		button_mood = (Button) this.findViewById(R.id.button_mood);
		button_mood.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		// 心灵鸡汤
		button_soup = (Button) this.findViewById(R.id.button_soup);
		button_soup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 完成跳转
				Intent intent = new Intent(MainViewActivity.this,
						SoupActivity.class);
				startActivity(intent);
			}
		});

		// 设置
		button_set = (Button) this.findViewById(R.id.button_set);
		button_set.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainViewActivity.this, SettingActivity.class);
				startActivity(intent);
			}
		});

		// 音乐
		imageBtn_music = (ImageButton) this.findViewById(R.id.image_btn_music);
		imageBtn_music.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (music_on) {
					music_on = false;
					stopService(intent);
					imageBtn_music.setImageResource(R.drawable.btn_music_off);
				} else {
					music_on = true;
					startService(intent);
					imageBtn_music.setImageResource(R.drawable.btn_music_on);
				}
			}
		});

	}
	
	// broadcast receiver  
		 private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {  
		  
		      @Override  
		      public void onReceive(Context context, Intent intent) {  
		          String action = intent.getAction();  
		          if (action.equals("action.themeChange"))  
		          {  
		        	  onCreate(null);
		          }  
		      }  
		  }; 

	private void ShowBanksByList(String searchbankname) {
		// 判断是否为搜索
		String bankname = searchbankname;
		List<Bank> banks;
		if (bankname == null)
			banks = banksercice.viewBank(MainViewActivity.this);
		else
			banks = banksercice.searchBank(bankname, MainViewActivity.this);
		// 获取到集合数据
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

		for (Bank bank : banks) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("bankid", bank.getBankID());
			item.put("bankname", bank.getBankName());
			item.put("bankdesc", bank.getBankDesc());
			item.put("bankdeposit", bank.getBankDeposit());
			data.add(item);
		}

		// ListViewAdapter adapter = new ListViewAdapter(this, data,
		// R.layout.banklist_item, new String[] { "bankname", "bankdesc",
		// "bankdeposit", "extrainterest" }, new int[] {
		// R.id.bankinfo_name, R.id.bankinfo_desc,
		// R.id.bankinfo_deposit, R.id.bankinfo_extralinterest });
		// 创建SimpleAdapter适配器将数据绑定到item显示控件上
		SimpleAdapter adapter = new SimpleAdapter(this, data,
				R.layout.banklist_item,
				new String[] { "bankname", "bankdesc" }, new int[] {
						R.id.bankinfo_name, R.id.bankinfo_desc });

		// 实现列表的显示
		listview.setAdapter(adapter);

		// listview.setOnItemClickListener(this);
	}

	// 给菜单项添加响应事件
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
		// .getMenuInfo();

		// info.id得到listview中选择的条目绑定的id, id+1与数据库保持一致
		// String temp = String.valueOf(info.id);
		// int id = Integer.valueOf(temp).intValue() + 1;
		// Log.i("info", id);
		// System.out.println(i);
		// String txt = listview.getChildAt(i).getTag(1).toString();
		switch (item.getItemId()) {
		// case 0:
		// // 打开银行的方法
		// // System.out.println(txt);
		// return true;
		case 1:
			// 更新银行的方法
			UpdateBank(MainViewActivity.this);
			ShowBanksByList(null);
			return true;
		case 2:
			// 删除银行的方法
			dialog();
		default:
			return super.onContextItemSelected(item);
		}
	}

	// 显示基于Layout的AlertDialog，新建银行
	@SuppressLint("InflateParams")
	private void AddBank(Context context) {
		LayoutInflater inflater = LayoutInflater.from(this);
		final View textEntryView = inflater.inflate(R.layout.newbank, null);
		// final EditText
		// edtInput=(EditText)textEntryView.findViewById(R.id.name_Input);
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		builder.setIcon(R.drawable.newbank);
		builder.setTitle("新建银行");
		builder.setView(textEntryView);
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// setTitle("");
				// Toast.makeText(MainViewActivity.this, "您已取消操作！",
				// Toast.LENGTH_SHORT).show();
			}
		});
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// setTitle(edtInput.getText());
				// 得到新建银行名称与描述
				// 增加textEntryView以消除此处空指针bug
				et_newbank_name = (EditText) textEntryView
						.findViewById(R.id.newnank_et_bankname_Input);
				et_newbank_description = (EditText) textEntryView
						.findViewById(R.id.newnank_et_bankdescription_Input);
				// 数据类型转换（EditText to String）
				newbank_name = et_newbank_name.getText().toString();
				newbank_description = et_newbank_description.getText()
						.toString();
				// 调用service对象中addBank方法
				banksercice.addBank(newbank_name, newbank_description,
						MainViewActivity.this);
				ShowBanksByList(null);
				Toast.makeText(MainViewActivity.this, "新建银行成功！",
						Toast.LENGTH_SHORT).show();
			}
		});
		builder.show();
	}

	// 更新银行
	@SuppressLint("InflateParams")
	private void UpdateBank(Context context) {
		LayoutInflater inflater = LayoutInflater.from(this);
		final View textEntryView = inflater.inflate(R.layout.newbank, null);
		// final EditText
		// edtInput=(EditText)textEntryView.findViewById(R.id.name_Input);

		// 得到当前银行名称与描述的控件
		// 增加textEntryView以消除此处空指针bug
		et_newbank_name = (EditText) textEntryView
				.findViewById(R.id.newnank_et_bankname_Input);
		et_newbank_description = (EditText) textEntryView
				.findViewById(R.id.newnank_et_bankdescription_Input);
		et_newbank_name.setText(s_bankname);
		et_newbank_description.setText(s_bankdescription);

		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		builder.setIcon(R.drawable.newbank);
		builder.setTitle("更新银行");
		builder.setView(textEntryView);
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// setTitle("");
				// Toast.makeText(MainViewActivity.this, "您已取消操作！",
				// Toast.LENGTH_SHORT).show();
			}
		});
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// setTitle(edtInput.getText());
				// 数据类型转换（EditText to String）
				newbank_name = et_newbank_name.getText().toString();
				newbank_description = et_newbank_description.getText()
						.toString();
				// 调用service对象中updateBank方法
				banksercice.updateBank(newbank_name, newbank_description,
						s_bankid, MainViewActivity.this);
				ShowBanksByList(null);
				Toast.makeText(MainViewActivity.this, "更新银行成功！",
						Toast.LENGTH_SHORT).show();
			}
		});
		builder.show();
	}

	// 搜索银行
	private void searchBank(Context context) {
		// 得到用户的输入信息
		et_searchbankname = (EditText) findViewById(R.id.main_view_et_searchbank_Input);
		searchbankname = et_searchbankname.getText().toString();

		if (searchbankname.equals("") || searchbankname == null)
			// Toast.makeText(MainViewActivity.this, "请输入银行信息！",
			// Toast.LENGTH_SHORT).show();
			ShowBanksByList(null);
		else {
			// 调用service对象中updateBank方法
			banksercice.searchBank(searchbankname, MainViewActivity.this);
			ShowBanksByList(searchbankname);
			// Toast.makeText(MainViewActivity.this, "已显示银行结果！",
			// Toast.LENGTH_SHORT).show();
		}
	}

	// 删除银行确认
	protected void dialog() {
		AlertDialog.Builder builder = new Builder(MainViewActivity.this);
		builder.setMessage("该银行要倒闭了...");

		builder.setTitle("提示");

		builder.setPositiveButton("就该倒闭", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				banksercice.deleteBank(s_bankid, MainViewActivity.this);
				ShowBanksByList(null);
				Toast.makeText(MainViewActivity.this, "银行已倒闭",
						Toast.LENGTH_SHORT).show();
			}
		});

		builder.setNegativeButton("我再想想", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Toast.makeText(MainViewActivity.this, "您已取消操作！",
				// Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

	@Override  
	protected void onDestroy() {  
		super.onDestroy();  
		Toast.makeText(this, "销毁了", Toast.LENGTH_LONG).show();
	    unregisterReceiver(mRefreshBroadcastReceiver);  
	} 
}
