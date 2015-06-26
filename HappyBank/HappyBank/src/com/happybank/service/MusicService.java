package com.happybank.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.happybank.R;
import com.happybank.dal.SoftwareDAO;

public class MusicService extends Service {

	private MediaPlayer mediaPlayer;
	private SoftwareDAO swd = null;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		if (mediaPlayer == null) {

			// R.raw.mmp是资源文件，MP3格式的
			mediaPlayer = MediaPlayer.create(this, R.raw.music);
			mediaPlayer.setLooping(true);
			mediaPlayer.start();

		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mediaPlayer.stop();
	}
	
	//更新音乐状态
	public void updateMusicState(String musicState, Context ctx) {
		swd = new SoftwareDAO(ctx);
		swd.open();
		swd.updataMusicState(musicState);
		swd.close();
	}

	//获得音乐状态
	public String getMusicState(Context ctx) {
		swd = new SoftwareDAO(ctx);
		swd.open();
		Cursor c;
		String musicState = null;
		c = swd.QuerySoftwareinfoALL();
		if (c.moveToFirst()) {
			musicState = c.getString(c.getColumnIndex("musicstate"));
		}
		swd.close();
		return musicState;
	}

}
