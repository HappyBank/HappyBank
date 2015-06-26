package com.happybank.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.happybank.R;


import com.happybank.config.Global;
import com.happybank.model.Bank;
import com.happybank.model.Deposit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;


public class SearchActivity extends Activity implements SearchView.OnQueryTextListener{

	private SearchView search;
	private ListView lv;
	private LinearLayout back;
	private List<Bank> banks;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Global.theme == 0) {
			setTheme(R.style.WhiteTheme);
		} else {
			setTheme(R.style.BlackTheme);
		}
		setContentView(R.layout.activity_search);
		
		search = (SearchView) findViewById(R.id.search_view);
		int id = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);  
		TextView textView = (TextView) search.findViewById(id);  
		textView.setTextColor(Color.WHITE);  
		textView.setHintTextColor(Color.parseColor("#ffffff")); 
		textView.setTextSize(20);
		
		banks = (List<Bank>) getIntent().getSerializableExtra("banks");
		
		lv = (ListView) findViewById(R.id.search_listView);
		// ��Ŀ����¼�
		lv.setOnItemClickListener(new ItemClickListener());
		
		back = (LinearLayout) findViewById(R.id.search_back_btn);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		//���ø�SearchViewĬ���Ƿ��Զ���СΪͼ��
		search.setIconifiedByDefault(true);
		//Ϊ��SearchView��������¼�������
		search.setOnQueryTextListener(this);
        //���ø�SearchView��ʾ������ť
		search.setSubmitButtonEnabled(true);
		//��ʼչ��
		search.onActionViewExpanded();
        //���ø�SearchView��Ĭ����ʾ����ʾ�ı�
		search.setQueryHint("����");

	}
	// ��ȡ����¼�
	final class ItemClickListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			ListView listView = (ListView) parent;
			@SuppressWarnings("unchecked")
			HashMap<String, Object> data = (HashMap<String, Object>) listView
					.getItemAtPosition(position);
			// �����ת
			Intent intent = new Intent(SearchActivity.this,
					DepositActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("bankID", (int) data.get("bankid"));
			intent.putExtras(bundle);
			startActivity(intent);
			finish();
		}
	}
	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onQueryTextChange(String newText) {
//		// TODO Auto-generated method stub
		 List<Bank> bankresult = searchItem(newText);  
	     updateLayout(bankresult);  
	     return false;
	}
	
	public List<Bank> searchItem(String name) {  
		List<Bank> mSearchList = new ArrayList();  
        for (int i = 0; i < banks.size(); i++) {  
            int index = banks.get(i).getBankName().indexOf(name);  
            // ����ƥ�������  
            if (index != -1) {  
                mSearchList.add(banks.get(i));  
            }  
        }  
        return mSearchList; 
    }  
  
    public void updateLayout(List<Bank> banks) {  
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
		
		lv.setTextFilterEnabled(true);
		// ʵ���б����ʾ
		lv.setAdapter(adapter);
    }  
}
