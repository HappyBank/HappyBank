package com.happybank.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.happybank.config.Config;
import com.happybank.config.Global;
import com.happybank.dal.BankDAO;
import com.happybank.dal.DepositDAO;
import com.happybank.dal.UserDAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class LoginService {

	//���ڸ�ʽ
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	String date_Today = df.format(new Date());//��ý�������
	Date date = null;
	Date dateToday = null;
	private int depositNum = 0;   //����Ѷ��������
	private float updateDepositOfBank = 0;
	private UserDAO ud = null;
	private BankDAO bd = null;
	private DepositDAO dd= null;
	private Cursor c1=null, c2=null, c3 = null;
	
	public boolean CheckPassword(Context ctx,
			String password) {
		ud = new UserDAO(ctx);
		ud.open();
		ud.QueryUserALL();
		c1 = ud.QueryUserALL();
		c1.moveToNext();
		
		//�������·���ؽ��������
		String psd = c1.getString(c1.getColumnIndex("password"));
		int happyBi = c1.getInt(c1.getColumnIndex("happybi"));
		Global.theme = c1.getInt(c1.getColumnIndex("theme"));
		try {
			date = df.parse(c1.getString(c1.getColumnIndex("logindate")));
			dateToday = df.parse(date_Today);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(psd.equals(password)){
			//ÿ���״ε�¼,���µ�½���ڣ�������Ϣ��������
			if(date.getTime() < dateToday.getTime()){
				bd = new BankDAO(ctx);
				bd.open();
				dd = new DepositDAO(ctx);
				dd.open();
				
				c2 = bd.QueryBankALL();
				while(c2.moveToNext()){
					//Log.i("info",c2.getInt(c2.getColumnIndex("bankid"))+"");
					int bankid_temp = c2.getInt(c2.getColumnIndex("bankid"));
					c3 = dd.QueryDepositByBankID(bankid_temp);
					depositNum += c3.getCount();
					while(c3.moveToNext()){
						if (c3.getInt(c3.getColumnIndex("iswithdraw")) == 0
								&& c3.getInt(c3.getColumnIndex("mood")) == 1){
							float tmp = c3.getFloat(c3.getColumnIndex("interest"))+c3.getFloat(c3.getColumnIndex("depositbegin"))*Config.DEPOSIT_GENERATE_INTEREST;
							dd.updataInterest(c3.getInt(c3.getColumnIndex("depositid")), tmp);
							updateDepositOfBank += c3.getFloat(c3.getColumnIndex("depositbegin")) + c3.getFloat(c3.getColumnIndex("interest"));
						}
					}
					bd.updataBankDeposit(bankid_temp, updateDepositOfBank);
					updateDepositOfBank = 0;
				}
				//Log.i("info",depositNum+"%$%$%$%$%$");
				
				if (depositNum <= 10){//����0~x����
					happyBi += Config.DEPOSIT_LIST_TO_HAPPYBI_LESS_THAN_10_RATE * depositNum;
				} else {//����10��
					happyBi += Config.DEPOSIT_LIST_TO_HAPPYBI_LESS_THAN_10_RATE * 10;
					if (depositNum <= 30){//����10~x����
						happyBi += Config.DEPOSIT_LIST_TO_HAPPYBI_10_TO_30_RATE * (depositNum - 10);
					} else {//����30��
						happyBi += Config.DEPOSIT_LIST_TO_HAPPYBI_10_TO_30_RATE * (30 - 10);
						if (depositNum <= 100){//����30~x���� 
							happyBi += Config.DEPOSIT_LIST_TO_HAPPYBI_30_TO_100_RATE * (depositNum - 30);
						} else {//����100+����
							happyBi += Config.DEPOSIT_LIST_TO_HAPPYBI_30_TO_100_RATE * (100 - 30);
							happyBi += Config.DEPOSIT_LIST_TO_HAPPYBI_MORE_THAN_100_RATE * (depositNum - 100);
						}
					}
				}

				 ud.updataHappyBi(happyBi);            //�����Ҹ���
				 ud.updataLoginDate(date_Today);       //���µ�½����
				 Log.i("info","ÿ���״ε�¼��ɣ�");	
				 dd.close();
				 bd.close();
			}
			ud.close();
			return true;
		}else{
			ud.close();
			return false;
		}
		// TODO Auto-generated method stub
	}
}
