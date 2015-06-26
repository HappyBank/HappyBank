package com.happybank.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.happybank.R;
import com.happybank.config.Config;
import com.happybank.config.Global;
import com.happybank.model.Bank;
import com.happybank.model.Deposit;
import com.happybank.service.BankService;
import com.happybank.service.DepositService;
import com.happybank.service.HappyBiService;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class DepositActivity extends Activity {
	
	// broadcast receiver
	private MyDepositReceiver mRefreshBroadcastReceiver = null;

	// 对象
	private BankService bs = null;
	private DepositService ds = null;
	private HappyBiService hs = null;
	
	// 参数
	private int bankID;
	private Bank bank = null;
	private ArrayList<Deposit> mData = null;
	private List<HashMap<String, Object>> data = null;
	private String bankDesc = null;
	private SimpleAdapter adapter = null;
	private String searchDepositInput = null;
	private float happyBi = 0;
	
	// 控件
	private TextView title = null;
	private TextView allDeposit = null;
	private TextView bankDescribe = null;
	private ListView listView = null;
	private LinearLayout back = null;
	private ImageButton addDeposit = null;
	private Button searchDeposit = null;
	private EditText searchInput = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Global.theme == 0){
			setTheme(R.style.WhiteTheme);
		}else{
			setTheme(R.style.BlackTheme);
		}
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_deposit);
		
		mRefreshBroadcastReceiver = new MyDepositReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("action.withdrawDeposit");
		this.registerReceiver(mRefreshBroadcastReceiver, intentFilter);

		// 创建对象
		bs = new BankService();
		ds = new DepositService();

		// 获得表头数据
		Bundle bundleFromMainViewActivity = this.getIntent().getExtras();
		bankID = bundleFromMainViewActivity.getInt("bankID");
		bank = bs.searchBank(this, bankID);

		// 添加组件和设置参数
		back = (LinearLayout) findViewById(R.id.deposit_title_btn_back);
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Intent in = new Intent(DepositActivity.this,
				// MainViewActivity.class);
				// startActivity(in);
				finish();
			}
		});

		title = (TextView) super.findViewById(R.id.deposit_title_tv_title);
		title.setText(bank.getBankName());

		allDeposit = (TextView) super
				.findViewById(R.id.deposit_title_tv_allDeposit);
		float a = bank.getBankDeposit();
	    float b = (float)(Math.round(a*100))/100;//减成两位小数
		allDeposit.setText(String.valueOf(b));

		bankDesc = bank.getBankDesc();
		bankDescribe = (TextView) super
				.findViewById(R.id.deposit_title_tv_bankDescribe);
		bankDescribe.setText(bankDesc);

		searchDeposit = (Button) findViewById(R.id.deposit_title_btn_searchdeposit);
		searchDeposit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				searchInput = (EditText) findViewById(R.id.deposit_title_et_input);
				searchDepositInput = searchInput.getText().toString();

				if (searchDepositInput.equals("") || searchDepositInput == null){
					mData = ds.viewDeposit(DepositActivity.this, bankID);
				} else {
					mData = ds.searchDeposit(DepositActivity.this, searchDepositInput, bankID);
				}
				if (mData != null){
					changeToMap();
					if (listView != null){
						listView.setVisibility(View.VISIBLE);
						adapter.notifyDataSetChanged();//更新视图
					}
				} else {
					if (listView != null){
						listView.setVisibility(View.INVISIBLE);
					}
				}
			}
		});
		
		addDeposit = (ImageButton) findViewById(R.id.deposit_bottom_btn_addDeposit);
		addDeposit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(DepositActivity.this,
						AddDepositActivity.class);
				Bundle bundle = new Bundle();  
	            bundle.putInt("bankID", bankID); // 压入数据  
	            intent.putExtras(bundle);
				startActivity(intent);
				finish();
			}
		});

		// 获取存款列表
		mData = ds.viewDeposit(this, bankID);
		if (mData != null){
			changeToMap();
		}

		if (data != null){
			// 显示列表
			// 取得ListView
			listView = (ListView) findViewById(R.id.deposit_content_lv_main);
			// 创建SimpleAdapter适配器将数据绑定到item显示控件上
			adapter = new SimpleAdapter(this, data,
					R.layout.listview_show_deposit, new String[] { "depositname",
							"listview_mood", "alldeposit", "listview_withdraw",
							"depositcontent","deposittime"}, new int[] {
							R.id.listview_showdeposit_tv_name,
							R.id.listview_showdeposit_image_mood,
							R.id.listview_showdeposit_tv_deposit,
							R.id.listview_showdeposit_image_iswithdraw,
							R.id.listview_showdeposit_tv_content ,
							R.id.listview_showdeposit_tv_time}) {
				public View getView(int position, View convertView, ViewGroup parent) {
					View view = super.getView(position, convertView, parent);
					@SuppressWarnings("unchecked")
					HashMap<String, Object> data = (HashMap<String, Object>) listView
							.getItemAtPosition(position);
					if ((int) data.get("mood") == 1) {
						view.setBackgroundColor(Color.parseColor("#EEB422"));
						//view.setBackgroundResource(R.drawable.happy_backpic);
					} else {
						view.setBackgroundColor(Color.parseColor("#BDBDBD"));
						//view.setBackgroundResource(R.drawable.unhappy_backpic);
					}
					return view;
				}
			};

			// 实现列表的显示
			listView.setAdapter(adapter);
			listView.setVisibility(View.VISIBLE);
			// 使ListView支持过滤搜索
			listView.setTextFilterEnabled(true);
			// 设置事件
			listView.setOnItemClickListener(new ItemClickListener());
			listView.setOnItemLongClickListener(new ItemLongClickListener());
		}
	}

	private final class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			hs = new HappyBiService();
			ListView listView = (ListView) parent;
			@SuppressWarnings("unchecked")
			HashMap<String, Object> data = (HashMap<String, Object>) listView
					.getItemAtPosition(position);

			//先判断心情
			if ((int) data.get("mood") == 0){
				happyBi = hs.getHappyBi(DepositActivity.this);
				if (happyBi < Config.VIEW_BAD_MOOD_DEPOSIT){
					Toast.makeText(getApplicationContext(), "幸福币余额不足~\n你还需要"+
							(float)(Math.round((Config.VIEW_BAD_MOOD_DEPOSIT-happyBi)*100))/100+"幸福币", Toast.LENGTH_SHORT).show();
					//(float)(Math.round((Config.VIEW_BAD_MOOD_DEPOSIT-happyBi)*100))/100
					return ;
				} else {
					happyBi = happyBi - Config.VIEW_BAD_MOOD_DEPOSIT;
					if (hs.setHappyBi(DepositActivity.this, happyBi) == true){
						Toast.makeText(getApplicationContext(), "已支付幸福币", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getApplicationContext(), "支付失败", Toast.LENGTH_SHORT).show();
						return ;
					}
				}
			}
			
			//完成跳转
			Intent intent = new Intent(DepositActivity.this,
					DepositInfoActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("depositID", (int) data.get("depositid"));
			bundle.putInt("bankID", (int) data.get("bankid"));
			bundle.putString("depositName", (String) data.get("depositname"));
			bundle.putString("depositTime", data.get("deposittime").toString());
			bundle.putInt("mood", (int) data.get("mood"));
			bundle.putString("depositContent", (String) data.get("depositcontent"));
			bundle.putFloat("depositBegin", (float) data.get("depositbegin"));
			bundle.putFloat("interest", (float) data.get("interest"));
			bundle.putString("picName", (String) data.get("picname"));
			bundle.putBoolean("isWithdraw", (boolean) data.get("iswithdraw"));
			if ((int)data.get("mood") == 0){
				bundle.putString("realContent", (String) data.get("realContent"));
			}
			intent.putExtras(bundle);
			startActivity(intent);
			//finish();
		}
	}
	
	public final class ItemLongClickListener implements OnItemLongClickListener {
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			ds = new DepositService();
			hs = new HappyBiService();
			final ListView listView = (ListView) parent;
			@SuppressWarnings("unchecked")
			final HashMap<String, Object> data2 = (HashMap<String, Object>) listView
					.getItemAtPosition(position);
			AlertDialog.Builder builder = new Builder(DepositActivity.this);

			builder.setMessage("确认删除存款？");
			builder.setTitle("提示");
			
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
				
						public void onClick(DialogInterface dialog, int which) {
							if (ds.deleteDeposit(DepositActivity.this,(int)data2.get("depositid")) == false)
							{
								Toast.makeText(getApplicationContext(), "删除失败", Toast.LENGTH_SHORT).show();
							} else {//删除成功
								if ((int)data2.get("mood") == 0){
									Toast.makeText(getApplicationContext(), "恭喜您忘记了一段不愉快的经历！", Toast.LENGTH_SHORT).show();
									hs.addHappyBi(DepositActivity.this, Config.DELETE_BAD_MOOD_DEPOSIT_TO_GENERATE_HAPPYBI);
								}
								data.remove(data2);
								adapter.notifyDataSetChanged();//更新视图
								bank = bs.searchBank(DepositActivity.this, bankID);
								float a = bank.getBankDeposit();
							    float b = (float)(Math.round(a*100))/100;//减成两位小数
								allDeposit.setText(String.valueOf(b));
							}
						}
					});

			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			
			builder.create().show();
			
			return true;
		}
	}
	
	public void changeToMap() {
		// 排序
		mData = ds.orderByMood(mData);
		// 转换为map并添加控件数据
		if (data != null){
			data.clear();
		} else {
			data = new ArrayList<HashMap<String, Object>>();
		}
		for (Deposit dep : mData) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("bankid", dep.getBankID());
			item.put("depositbegin", dep.getDepositBegin());
			if (dep.getMood() == 1){
				item.put("depositcontent", dep.getDepositContent());
			} else {
				item.put("depositcontent", "不开心的事情不给看哦(＞﹏＜)");
			}
			item.put("depositid", dep.getDepositID());
			item.put("depositname", dep.getDepositName());
			SimpleDateFormat f=new SimpleDateFormat("记录于	"+"yyyy 年 MM 月 dd 日");
			item.put("deposittime", f.format(dep.getDepositTime()));
			float a = dep.getInterest();
			float b = (float)(Math.round(a*100))/100;//减成两位小数
			item.put("interest", b);
			item.put("mood", dep.getMood());
			item.put("picname", dep.getPicName());
			item.put("iswithdraw", dep.getIsWithdraw());
			if (dep.getMood() == 1) {
				item.put("listview_mood", R.drawable.happy);
			} else {
				item.put("listview_mood", R.drawable.unhappy);
			}
			if (dep.getIsWithdraw() == false && dep.getMood() == 1) {
				item.put("listview_withdraw", R.drawable.withdraw);
			} else {
				item.put("listview_withdraw", R.drawable.cannotwithdraw);
			}
			float c = dep.getDepositBegin() + dep.getInterest();
			float d = (float)(Math.round(c*100))/100;//减成两位小数
			item.put("alldeposit", d);
			if (dep.getMood() == 0){
				item.put("realContent", dep.getDepositContent());
			}
			data.add(item);
		}
	}
	
	/* 
	 * 作为内部类的广播接收者
	 */
	private class MyDepositReceiver extends BroadcastReceiver{
		
	    // Empty constructor
		public MyDepositReceiver(){
			System.out.println(" Create MyDepositReceiver---------------->");
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			switch (action){
				case "action.withdrawDeposit":
					//new MainViewActivity().onCreate(null);
					Log.i("info","deposit flash by withdraw deposit");
					unregisterReceiver(mRefreshBroadcastReceiver); 
					onCreate(null);
					break;
			}
		}	
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mRefreshBroadcastReceiver!=null)
		    unregisterReceiver(mRefreshBroadcastReceiver); 
	}

}
