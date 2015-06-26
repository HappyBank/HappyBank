package com.happybank.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.example.happybank.R;
import com.example.happybank.R.anim;
import com.example.happybank.R.drawable;
import com.example.happybank.R.id;
import com.example.happybank.R.layout;
import com.example.happybank.R.menu;
import com.happybank.config.Global;
import com.happybank.service.SoupService;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;
import android.os.Build;

public class GestureActivity extends Activity implements OnGestureListener {

	private Button firstuse_continue;
	// ViewFlipper实例
	private ViewFlipper flipper;
	// 定义手势检测器实例
	private GestureDetector detector;
	// 定义一个动画数组，用于为ViewFlipper指定切换动画效果
	private Animation[] animations = new Animation[4];
	// 定义手势动作两点之间的最小距离
	private final int FLIP_DISTANCE = 50;

	// 心灵鸡汤的相关属性
	private String str_soupID;
	private String str_soupName;
	private String str_soupContent;
	private String str_soupPic;

	// 创建soupservice对象
	private SoupService soupservice = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.firstuse_anime);

		soupservice = new SoupService();

		// 读取并写入exel数据
		ArrayList<HashMap<String, String>> al = new ArrayList<HashMap<String, String>>();
		AssetManager am = this.getAssets();
		InputStream is = null;
		try {
			is = am.open("soup.xls");
			Workbook wb = Workbook.getWorkbook(is);
			Sheet sheet = wb.getSheet(0);
			int row = sheet.getRows();
			// String r = String.valueOf(row);
			// Log.i("info", r);
			// Cell cellarea = sheet.getCell(2, 1);
			// str_soupContent = cellarea.getContents();
			// Log.i("info", str_soupContent);

			for (int i = 1; i < row; ++i) {
				Cell cellSoupID = sheet.getCell(0, i);
				Cell cellSoupName = sheet.getCell(1, i);
				Cell cellSoupContent = sheet.getCell(2, i);
				Cell cellSoupPic = sheet.getCell(3, i);

				str_soupID = cellSoupID.getContents();
				str_soupName = cellSoupName.getContents();
				str_soupContent = cellSoupContent.getContents();
				str_soupPic = cellSoupPic.getContents();

//				Log.i("info", str_soupContent);
//
//				Resources r = new Resources(am, null, null);
//				Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
//						+ "://" + r.getResourcePackageName(R.drawable.qsmy)
//						+ "/" + r.getResourceTypeName(R.drawable.qsmy) + "/"
//						+ r.getResourceEntryName(R.drawable.qsmy));
//				String aaa = uri.toString();
//				Log.i("info", aaa);

				soupservice.initSoupList(str_soupID, str_soupName,
						str_soupContent, str_soupPic, GestureActivity.this);
			}

			// 心灵鸡汤列表初始化
			Global.soupID = 1;
			Global.souplist = soupservice.getSoupList(Global.soupID,
					GestureActivity.this);
			// if (Global.souplist == null)
			// Log.i("info", "123");
			// else
			// Log.i("info", "456");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 创建手势检测器
		detector = new GestureDetector(this);
		// 获得ViewFlipper实例
		flipper = (ViewFlipper) findViewById(R.id.flipper);
		// 为ViewFlipper添加3个ImageView组件
		flipper.addView(addImageView(R.drawable.welcome_anime_1));
		flipper.addView(addImageView(R.drawable.welcome_anime_2));
		flipper.addView(addImageView(R.drawable.welcome_anime_3));

		// 初始化Animation数组
		animations[0] = AnimationUtils.loadAnimation(this, R.anim.left_in);
		animations[1] = AnimationUtils.loadAnimation(this, R.anim.left_out);
		animations[2] = AnimationUtils.loadAnimation(this, R.anim.right_in);
		animations[3] = AnimationUtils.loadAnimation(this, R.anim.right_out);

		firstuse_continue = (Button) findViewById(R.id.firstuse_continue);
		// 按钮事件监听
		firstuse_continue.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(GestureActivity.this,
						InputPasswordActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	// 定义添加ImageView的工具方法
	private View addImageView(int resId) {
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(resId);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		return imageView;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		/*
		 * 如果第一个触点事件的X座标大于第二个触点事件的X座标超过FLIP_DISTANCE 也就是手势从右向左滑。
		 */
		if (e1.getX() - e2.getX() > FLIP_DISTANCE) {
			// 为flipper设置切换的的动画效果
			flipper.setInAnimation(animations[0]);
			flipper.setOutAnimation(animations[1]);
			flipper.showPrevious();
			return true;
		}
		/*
		 * 如果第二个触点事件的X座标大于第一个触点事件的X座标超过FLIP_DISTANCE 也就是手势从右向左滑。
		 */
		else if (e2.getX() - e1.getX() > FLIP_DISTANCE) {
			// 为flipper设置切换的的动画效果
			flipper.setInAnimation(animations[2]);
			flipper.setOutAnimation(animations[3]);
			flipper.showNext();
			return true;
		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 将该Activity上的触碰事件交给GestureDetector处理
		return detector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

}
