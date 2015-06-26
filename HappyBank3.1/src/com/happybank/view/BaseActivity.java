package com.happybank.view;

import java.util.LinkedList;
import android.app.Activity;
import android.os.Bundle;

//没了服务器了，暂时先不要管这个类了
public abstract class BaseActivity extends Activity{

	protected static LinkedList<BaseActivity> queue= new LinkedList<BaseActivity>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!queue.contains(this)){
			queue.add(this);
		}
	}

}
