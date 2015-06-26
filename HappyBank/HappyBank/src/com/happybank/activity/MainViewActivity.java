package com.happybank.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.happybank.R;
import com.happybank.config.Global;
import com.happybank.model.Bank;
import com.happybank.service.BankService;
import com.happybank.service.HappyBiService;
import com.happybank.service.MusicService;
import com.happybank.service.SoupService;
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
import android.util.Log;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainViewActivity extends Activity {

	private LinearLayout button_newbank; // �½�����
	private LinearLayout button_searchbank; // ��������
	private LinearLayout button_mood; // ��������
	private LinearLayout button_soup; // ���鼦��
	private LinearLayout button_set; // ���ð�ť
	private ListView listview = null; // �б���ʾ
	private List<Bank> banks;
	
	// broadcast receiver
	private MyMainReceiver mRefreshBroadcastReceiver = null;

	// �û������bankname��bankdescription
	private EditText et_newbank_name;
	private EditText et_newbank_description;
	private String newbank_name = null;
	private String newbank_description = null;

	// ����bankservice����
	private BankService banksercice = null;

	// ����userservice����
	private UserService userservice = null;

	// �û�����ĵ�ǰ���е�bankid, bankname��bankdescription
	private int s_bankid = 0;
	private String s_bankname = null;
	private String s_bankdescription = null;

	// ɾ�����еĵ�ǰ���
	private float d_bankdeposit = 0;

	// �û����������������Ϣ
	//private EditText et_searchbankname = null;
	private String searchbankname = null;

	// �������
	private Intent intent = new Intent();
	private ImageButton imageBtn_music;
	private boolean music_on = true;
	private MusicService musicservice = null;
	private String musicState = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Global.theme == 0) {
			setTheme(R.style.WhiteTheme);
		} else {
			setTheme(R.style.BlackTheme);
		}
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_view);

		mRefreshBroadcastReceiver = new MyMainReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("action.themeChanged");
		intentFilter.addAction("action.passwordChanged");
		intentFilter.addAction("action.addDeposit");
		this.registerReceiver(mRefreshBroadcastReceiver, intentFilter);

		// ��ʼ����������
		intent.setClass(MainViewActivity.this, MusicService.class);
		musicservice = new MusicService();
		musicState = musicservice.getMusicState(MainViewActivity.this);
		// ��ʼ����ť
		imageBtn_music = (ImageButton) this.findViewById(R.id.image_btn_music);

		if (musicState.equals("on")) {
			music_on = true;
			imageBtn_music.setImageResource(R.drawable.btn_music_on);
			startService(intent);
		} else if (musicState.equals("off")) {
			music_on = false;
			imageBtn_music.setImageResource(R.drawable.btn_music_off);
			stopService(intent);
		}

		userservice = new UserService();
		banksercice = new BankService();

		listview = (ListView) findViewById(R.id.bankinfo_listView);
		// ���б�չʾ����
		ShowBanksByList(null);

		// ��ȡ����¼�
		final class ItemClickListener implements OnItemClickListener {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ListView listView = (ListView) parent;
				@SuppressWarnings("unchecked")
				HashMap<String, Object> data = (HashMap<String, Object>) listView
						.getItemAtPosition(position);
				// �����ת
				Intent intent = new Intent(MainViewActivity.this,
						DepositActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("bankID", (int) data.get("bankid"));
				intent.putExtras(bundle);
				startActivity(intent);
				// finish();
			}
		}

		// ��Ŀ����¼�
		listview.setOnItemClickListener(new ItemClickListener());

		// ��ȡ�����¼�
		// �����ݴ�����õ�ǰ�����������ݣ���һ��������Ҫ
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
				menu.setHeaderTitle("ѡ�����");
				menu.add(0, 1, 0, "��������");
				menu.add(0, 2, 0, "ɾ������");
			}
		}

		listview.setOnCreateContextMenuListener(new OnCreateContextMenuListener());
		// ȡ��ҳ�水ť����Ӽ����¼�
		// �½�����
		button_newbank = (LinearLayout) findViewById(R.id.button_newbank);
		button_newbank.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AddBank(MainViewActivity.this);

			}
		});

		// ��������
		button_searchbank = (LinearLayout) findViewById(R.id.main_view_bt_searchbank);
		button_searchbank.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				searchBank(MainViewActivity.this);
				Intent intent = new Intent();
				intent.setClass(MainViewActivity.this,SearchActivity.class);
				//intent.putExtra("searchtype", "bank");
				intent.putExtra("banks", (Serializable) banks);
				startActivity(intent); 
			}
		});

		// ��������
		button_mood = (LinearLayout) this.findViewById(R.id.button_mood);
		button_mood.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainViewActivity.this,StatisticsActivity.class);
				startActivity(intent); 
			}
		});

		// ���鼦��
		button_soup = (LinearLayout) this.findViewById(R.id.button_soup);
		button_soup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// �����ת
				Intent intent = new Intent(MainViewActivity.this,
						SoupActivity.class);
				startActivity(intent);
			}
		});

		// ����
		button_set = (LinearLayout) this.findViewById(R.id.button_set);
		button_set.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainViewActivity.this, SettingActivity.class);
				startActivity(intent);
			}
		});

		// ���ֵ���¼�
		imageBtn_music.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (music_on) {
					music_on = false;
					musicservice.updateMusicState("off", MainViewActivity.this);
					stopService(intent);
					imageBtn_music.setImageResource(R.drawable.btn_music_off);
				} else {
					music_on = true;
					musicservice.updateMusicState("on", MainViewActivity.this);
					startService(intent);
					imageBtn_music.setImageResource(R.drawable.btn_music_on);
				}
			}
		});

	}
	
	/* 
	 * ��Ϊ�ڲ���Ĺ㲥������
	 */
	private class MyMainReceiver extends BroadcastReceiver{
		
	    // Empty constructor
		public MyMainReceiver(){
			System.out.println(" Create MyMainReceiver---------------->");
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			switch (action){
				case "action.themeChanged":
					//new MainViewActivity().onCreate(null);
					Log.i("info","main flash by change theme");
					unregisterReceiver(mRefreshBroadcastReceiver); 
					onCreate(null);
					break;
				case "action.passwordChanged":
					Log.i("info","main close by change pwd");
					unregisterReceiver(mRefreshBroadcastReceiver); 
					finish();
					break;
				case "action.addDeposit":
					Log.i("info","main flash by adddeposit");
					unregisterReceiver(mRefreshBroadcastReceiver); 
					onCreate(null);
					break;
			}
		}	
	}
	
	private void ShowBanksByList(String searchbankname) {
		// �ж��Ƿ�Ϊ����
		if (searchbankname == null)
			banks = banksercice.viewBank(MainViewActivity.this);
		else
			banks = banksercice.searchBank(searchbankname, MainViewActivity.this);
		
			banks = banksercice.orderByBankDepositNum(banks); //��������idpaixu 
			
		// ��ȡ����������
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

		for (Bank bank : banks) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("bankid", bank.getBankID());
			item.put("bankname", bank.getBankName());
			item.put("bankdesc", bank.getBankDesc());
			item.put("bankdeposit", bank.getBankDeposit());
			item.put("bankdepositnum", bank.getDepositNum());
			item.put("bankhappydepositnum", bank.getHappyDepositNum());
			item.put("bankunhappydepositnum", bank.getUnhappyDepositNum());
			data.add(item);
		}
		
		// ����SimpleAdapter�����������ݰ󶨵�item��ʾ�ؼ���
		SimpleAdapter adapter = new SimpleAdapter(this, data,
				R.layout.banklist_item,
				new String[] { "bankname", "bankdesc", "bankdepositnum" }, new int[] {
						R.id.bankinfo_name, R.id.bankinfo_desc, R.id.bankinfo_num_of_deposit});

		// ʵ���б����ʾ
		listview.setAdapter(adapter);

		// listview.setOnItemClickListener(this);
	}

	// ���˵��������Ӧ�¼�
	@Override
	public boolean onContextItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case 1:
			// �������еķ���
			UpdateBank(MainViewActivity.this);
			ShowBanksByList(null);
			return true;
		case 2:
			// ɾ�����еķ���
			dialog();
		default:
			return super.onContextItemSelected(item);
		}
	}

	// ��ʾ����Layout��AlertDialog���½�����
	@SuppressLint("InflateParams")
	private void AddBank(Context context) {
		LayoutInflater inflater = LayoutInflater.from(this);
		final View textEntryView = inflater.inflate(R.layout.newbank, null);
		// final EditText
		// edtInput=(EditText)textEntryView.findViewById(R.id.name_Input);
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		//builder.setIcon(R.drawable.newbank);
		builder.setTitle("�½�����");
		builder.setView(textEntryView);
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// setTitle("");
				// Toast.makeText(MainViewActivity.this, "����ȡ��������",
				// Toast.LENGTH_SHORT).show();
			}
		});
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// setTitle(edtInput.getText());
				// �õ��½���������������
				// ����textEntryView�������˴���ָ��bug
				et_newbank_name = (EditText) textEntryView
						.findViewById(R.id.newnank_et_bankname_Input);
				et_newbank_description = (EditText) textEntryView
						.findViewById(R.id.newnank_et_bankdescription_Input);
				
				// ��������ת����EditText to String)
				newbank_name = et_newbank_name.getText().toString();
				newbank_description = et_newbank_description.getText()
						.toString();
				// ����service������addBank����
				banksercice.addBank(newbank_name, newbank_description,
						MainViewActivity.this);
				ShowBanksByList(null);
				Toast.makeText(MainViewActivity.this, "�½����гɹ���",
						Toast.LENGTH_SHORT).show();
			}
		});
		builder.show();
	}

	// ��������
	@SuppressLint("InflateParams")
	private void UpdateBank(Context context) {
		LayoutInflater inflater = LayoutInflater.from(this);
		final View textEntryView = inflater.inflate(R.layout.newbank, null);
		// final EditText
		// edtInput=(EditText)textEntryView.findViewById(R.id.name_Input);

		// �õ���ǰ���������������Ŀؼ�
		// ����textEntryView�������˴���ָ��bug
		et_newbank_name = (EditText) textEntryView
				.findViewById(R.id.newnank_et_bankname_Input);
		et_newbank_description = (EditText) textEntryView
				.findViewById(R.id.newnank_et_bankdescription_Input);
		et_newbank_name.setText(s_bankname);
		et_newbank_description.setText(s_bankdescription);

		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		//builder.setIcon(R.drawable.newbank);
		builder.setTitle("��������");
		builder.setView(textEntryView);
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// setTitle("");
				// Toast.makeText(MainViewActivity.this, "����ȡ��������",
				// Toast.LENGTH_SHORT).show();
			}
		});
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// setTitle(edtInput.getText());
				// ��������ת����EditText to String��
				newbank_name = et_newbank_name.getText().toString();
				newbank_description = et_newbank_description.getText()
						.toString();
				// ����service������updateBank����
				banksercice.updateBank(newbank_name, newbank_description,
						s_bankid, MainViewActivity.this);
				ShowBanksByList(null);
				Toast.makeText(MainViewActivity.this, "�������гɹ���",
						Toast.LENGTH_SHORT).show();
			}
		});
		builder.show();
	}

	// ɾ������ȷ��
	protected void dialog() {
		AlertDialog.Builder builder = new Builder(MainViewActivity.this);
		builder.setMessage("������Ҫ������...");

		builder.setTitle("��ʾ");

		builder.setPositiveButton("�͸õ���", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				banksercice.deleteBank(s_bankid, MainViewActivity.this);
				ShowBanksByList(null);
				Toast.makeText(MainViewActivity.this, "�����ѵ���",
						Toast.LENGTH_SHORT).show();
			}
		});

		builder.setNegativeButton("��������", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Toast.makeText(MainViewActivity.this, "����ȡ��������",
				// Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ֹͣ��������
		stopService(intent);
		// Toast.makeText(this, "������", Toast.LENGTH_LONG).show();
		if(mRefreshBroadcastReceiver!=null)
		    unregisterReceiver(mRefreshBroadcastReceiver); 
	}
}
