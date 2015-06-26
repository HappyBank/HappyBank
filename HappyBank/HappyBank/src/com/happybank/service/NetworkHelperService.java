package com.happybank.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkHelperService {

	/* ����Ƿ����������� */
	public boolean isNetworkConnected(Context context) { 
		if (context != null) { 
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context 
					.getSystemService(Context.CONNECTIVITY_SERVICE); 
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo(); 
			if (mNetworkInfo != null) { 
				return mNetworkInfo.isAvailable(); 	
			} 
		} 
		return false; 
	} 
	
	/* ���wifi�����Ƿ����� */
	public boolean isWifiConnected(Context context) { 
		if (context != null) { 
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context 
					.getSystemService(Context.CONNECTIVITY_SERVICE); 
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager 
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI); 
			if (mWiFiNetworkInfo != null) { 
				return mWiFiNetworkInfo.isAvailable(); 
			} 
		} 
		return false; 
	}
	
	/* �ж�MOBILE�����Ƿ����  */
	public boolean isMobileConnected(Context context) { 
		if (context != null) { 
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context 
					.getSystemService(Context.CONNECTIVITY_SERVICE); 
			NetworkInfo mMobileNetworkInfo = mConnectivityManager 
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); 
			if (mMobileNetworkInfo != null) { 
				return mMobileNetworkInfo.isAvailable(); 
			} 
		} 
		return false; 
	} 
}
