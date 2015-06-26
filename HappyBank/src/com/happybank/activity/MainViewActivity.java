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

	private Button button_newbank; // �½�����
	private Button button_searchbank; // ��������
	private Button button_mood; // ��������
	private Button button_soup; // ���鼦��
	private Button button_set; // ���ð�ť
	private ListView listview = null; // �б���ʾ

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
	private EditText et_searchbankname = null;
	private String searchbankname = null;

	// �������
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

		// ��ʼ����������
		intent.setClass(MainViewActivity.this, MusicService.class);
		startService(intent);

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

		// ////��ȡ�α�
		// Cursor c = bank.QurryBankInfo(MainViewActivity.this);
		// //����SimpleCursorAdapter�����������ݰ󶨵�item��ʾ�ؼ���
		// SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
		// R.layout.banklist_show, c,
		// new String[]{"_id"}, new int[]{R.id.bankinfo_name});
		// listview.setAdapter(adapter);
		// bank.QurryBnakinfo();

		// ȡ��ҳ�水ť����Ӽ����¼�
		// �½�����
		button_newbank = (Button) findViewById(R.id.button_newbank);
		button_newbank.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AddBank(MainViewActivity.this);

			}
		});

		// ��������
		button_searchbank = (Button) findViewById(R.id.main_view_bt_searchbank);
		button_searchbank.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				searchBank(MainViewActivity.this);
			}
		});

		// ��������
		button_mood = (Button) this.findViewById(R.id.button_mood);
		button_mood.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		// ���鼦��
		button_soup = (Button) this.findViewById(R.id.button_soup);
		button_soup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// �����ת
				Intent intent = new Intent(MainViewActivity.this,
						SoupActivity.class);
				startActivity(intent);
			}
		});

		// ����
		button_set = (Button) this.findViewById(R.id.button_set);
		button_set.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainViewActivity.this, SettingActivity.class);
				startActivity(intent);
			}
		});

		// ����
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
		// �ж��Ƿ�Ϊ����
		String bankname = searchbankname;
		List<Bank> banks;
		if (bankname == null)
			banks = banksercice.viewBank(MainViewActivity.this);
		else
			banks = banksercice.searchBank(bankname, MainViewActivity.this);
		// ��ȡ����������
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
		// ����SimpleAdapter�����������ݰ󶨵�item��ʾ�ؼ���
		SimpleAdapter adapter = new SimpleAdapter(this, data,
				R.layout.banklist_item,
				new String[] { "bankname", "bankdesc" }, new int[] {
						R.id.bankinfo_name, R.id.bankinfo_desc });

		// ʵ���б����ʾ
		listview.setAdapter(adapter);

		// listview.setOnItemClickListener(this);
	}

	// ���˵��������Ӧ�¼�
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
		// .getMenuInfo();

		// info.id�õ�listview��ѡ�����Ŀ�󶨵�id, id+1�����ݿⱣ��һ��
		// String temp = String.valueOf(info.id);
		// int id = Integer.valueOf(temp).intValue() + 1;
		// Log.i("info", id);
		// System.out.println(i);
		// String txt = listview.getChildAt(i).getTag(1).toString();
		switch (item.getItemId()) {
		// case 0:
		// // �����еķ���
		// // System.out.println(txt);
		// return true;
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
		builder.setIcon(R.drawable.newbank);
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
				// ��������ת����EditText to String��
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
		builder.setIcon(R.drawable.newbank);
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

	// ��������
	private void searchBank(Context context) {
		// �õ��û���������Ϣ
		et_searchbankname = (EditText) findViewById(R.id.main_view_et_searchbank_Input);
		searchbankname = et_searchbankname.getText().toString();

		if (searchbankname.equals("") || searchbankname == null)
			// Toast.makeText(MainViewActivity.this, "������������Ϣ��",
			// Toast.LENGTH_SHORT).show();
			ShowBanksByList(null);
		else {
			// ����service������updateBank����
			banksercice.searchBank(searchbankname, MainViewActivity.this);
			ShowBanksByList(searchbankname);
			// Toast.makeText(MainViewActivity.this, "����ʾ���н����",
			// Toast.LENGTH_SHORT).show();
		}
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
		Toast.makeText(this, "������", Toast.LENGTH_LONG).show();
	    unregisterReceiver(mRefreshBroadcastReceiver);  
	} 
}
