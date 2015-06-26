package com.happybank.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class ParseXmlService implements ResponseHandler<HashMap<String,String>>{  
	private static final String VERSION_TAG = "version";
	private static final String NAME_TAG = "name";
	private static final String URL_TAG = "apkurl";
	
	private String mVersion, mName, mUrl;
	private boolean mIsVersion, mIsName, mIsUrl;
	private final HashMap<String,String> mResults = new HashMap<String,String>();
	
	@Override
	public HashMap<String,String> handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		try {
			
//			Log.i("xml","进入ParseXml");
			// Create the Pull Parser
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xpp = factory.newPullParser();

			// Set the Parser's input to be the XML document in the HTTP Response
			xpp.setInput(new InputStreamReader(response.getEntity()
					.getContent()));
			
			// Get the first Parser event and start iterating over the XML document 
			int eventType = xpp.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {

				if (eventType == XmlPullParser.START_TAG) {
					startTag(xpp.getName());
				} else if (eventType == XmlPullParser.END_TAG) {
					endTag(xpp.getName());
				} else if (eventType == XmlPullParser.TEXT) {
					text(xpp.getText());
				}
				eventType = xpp.next();
			}
			return mResults;
		} catch (XmlPullParserException e) {
		}
		return null;
	}

	public void startTag(String localName) {
		if (localName.equals(VERSION_TAG)) {
			mIsVersion = true;
		} else if (localName.equals(NAME_TAG)) {
			mIsName = true;
		} else if (localName.equals(URL_TAG)) {
			mIsUrl = true;
		}
		//Log.i("info","这是判断结果："+ mIsParsingLat+"$$"+mIsParsingLng+"##"+mIsWeather);
	}

	public void text(String text) {
		if (mIsVersion) {
			mVersion = text.trim();
		} else if (mIsName) {
			mName = text.trim();
		} else if (mIsUrl) {
			mUrl = text.trim();
		}
		//Log.i("info","这是获取结果："+ mLat+"$$"+mLng+"##"+mWea);
	}

	public void endTag(String localName) {
		if (localName.equals(VERSION_TAG)) {
			mResults.put("version",mVersion);
			mIsVersion = false;
		} else if (localName.equals(NAME_TAG)) {
			mResults.put("name",mName);
			mIsName = false;
		} else if (localName.equals(URL_TAG)) {
			mResults.put("url",mUrl);
			mIsUrl = false;
		}else if (localName.equals("info")) {
			//mResults.add(mVersion);
			//mResults.add(mName);
			//mResults.add(mUrl);

			mVersion = null;
			mName = null;
			mUrl = null;
		}
		//Log.i("info",mResults.toString()+"$$这是结果！");
	}
}  