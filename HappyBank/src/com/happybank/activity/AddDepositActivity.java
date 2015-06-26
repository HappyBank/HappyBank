package com.happybank.activity;

import java.io.File;
import java.io.FileNotFoundException;

import com.example.happybank.R;
import com.happybank.config.Config;
import com.happybank.config.Global;
import com.happybank.service.DepositService;
import com.happybank.service.GetPathFromUri4kitkat;
import com.happybank.service.HappyBiService;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import android.provider.MediaStore;

public class AddDepositActivity extends Activity {

	// 对象
	private DepositService ds = null;
	private HappyBiService hs = null;

	// 参数
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESIZE_REQUEST_CODE = 2;
	private int bankID;
	private String name;
	private int mood = 1;
	private String content;
	private String picName = null;
	private String tmpPicName = null;

	// 控件
	private ImageButton back = null;
	private Button save = null;
	private Button addPhoto = null;
	private Button camera = null;
	private RadioGroup mRadioGroup = null;
	private RadioButton mRadio1 = null;
	private RadioButton mRadio2 = null;
	private EditText depositName = null;
	private EditText depositContent = null;
	private ImageView pic = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Global.theme == 0){
			setTheme(R.style.WhiteTheme);
		}else{
			setTheme(R.style.BlackTheme);
		}
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_deposit);

		// 获得表头数据
		Bundle bundleFromMainViewActivity = this.getIntent().getExtras();
		bankID = bundleFromMainViewActivity.getInt("bankID");

		// 添加组件和设置参数
		back = (ImageButton) findViewById(R.id.adddeposit_title_btn_back);
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(AddDepositActivity.this,
						DepositActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("bankID", bankID); // 压入数据
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
			}
		});

		save = (Button) findViewById(R.id.adddeposit_title_btn_save);
		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				name = depositName.getText().toString();
				content = depositContent.getText().toString();
				if (name == null || name.length() == 0) {
					Toast.makeText(getApplicationContext(), "请输入存款名称",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (mood == 0) {
					AlertDialog.Builder builder = new Builder(
							AddDepositActivity.this);
					builder.setMessage("不开心的事还是忘记的好");
					builder.setTitle("提示");
					builder.setPositiveButton("忘记",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO
									hs = new HappyBiService();
									hs.addHappyBi(
											AddDepositActivity.this,
											Config.DELETE_BAD_MOOD_DEPOSIT_TO_GENERATE_HAPPYBI);
									Toast.makeText(getApplicationContext(),
											"恭喜您忘记了一段不愉快的经历",
											Toast.LENGTH_SHORT).show();
									Intent intent = new Intent(
											AddDepositActivity.this,
											DepositActivity.class);
									Bundle bundle = new Bundle();
									bundle.putInt("bankID", bankID); // 压入数据
									intent.putExtras(bundle);
									startActivity(intent);
									finish();
								}
							});

					builder.setNegativeButton("仍然继续",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									ds = new DepositService();
									ds.addDeposit(AddDepositActivity.this, bankID, name, mood,
											content, picName);
									Toast.makeText(getApplicationContext(), "存款成功",
											Toast.LENGTH_SHORT).show();
									Intent intent = new Intent(AddDepositActivity.this,
											DepositActivity.class);
									Bundle bundle = new Bundle();
									bundle.putInt("bankID", bankID); // 压入数据
									intent.putExtras(bundle);
									startActivity(intent);
									finish();
								}
							});
					builder.create().show();
				} else {
					ds = new DepositService();
					ds.addDeposit(AddDepositActivity.this, bankID, name, mood,
							content, picName);
					Toast.makeText(getApplicationContext(), "存款成功",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(AddDepositActivity.this,
							DepositActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("bankID", bankID); // 压入数据
					intent.putExtras(bundle);
					startActivity(intent);
					finish();
				}
			}
		});

		depositName = (EditText) findViewById(R.id.adddeposit_content_et_name);

		mRadioGroup = (RadioGroup) findViewById(R.id.adddeposit_content_rg_mood);
		mRadio1 = (RadioButton) findViewById(R.id.adddeposit_content_rg_mood_happy);
		mRadio2 = (RadioButton) findViewById(R.id.adddeposit_content_rg_mood_unhappy);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == mRadio1.getId())
					mood = 1;
				else if (checkedId == mRadio2.getId())
					mood = 0;
			}
		});

		depositContent = (EditText) findViewById(R.id.adddeposit_content_et_content);

		addPhoto = (Button) findViewById(R.id.adddeposit_content_btn_photo);
		addPhoto.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
				galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
				galleryIntent.setType("image/*");
				startActivityForResult(galleryIntent, IMAGE_REQUEST_CODE);
			}
		});

		 camera = (Button) findViewById(R.id.adddeposit_content_btn_camera);
		 camera.setOnClickListener(new View.OnClickListener() {
		 public void onClick(View v) {
		 if (isSdcardExisting()) {
		 // Intent cameraIntent = new Intent(
		 // "android.media.action.IMAGE_CAPTURE");
		 Intent cameraIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
		 cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
		 getImageUri());
		 cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
		 startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
		 } else {
		 Toast.makeText(v.getContext(), "请插入sd卡", Toast.LENGTH_LONG)
		 .show();
		 }
		 }
		 });

		pic = (ImageView) findViewById(R.id.adddeposit_content_image_picture);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (resultCode != RESULT_OK) {
			return;
		} else {
			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				showResizeImage(Uri.parse(intent.getDataString()));
				break;
			 case CAMERA_REQUEST_CODE:
			 if (isSdcardExisting()) {
			 //Log.i("DEBUG-2",getImageUri().toString());
			 showResizeImage(getImageUri());
			 //resizeImage(getImageUri());
			 } else {
			 Toast.makeText(AddDepositActivity.this, "未找到存储卡，无法存储照片！",
			 Toast.LENGTH_SHORT).show();
			 }
			 break;
			 case RESIZE_REQUEST_CODE:
			 if (intent != null) {
			 showResizeImage(intent);
			 //Log.i("DEBUG-3",intent.getDataString());
			 }
			 break;
			}
		}
		super.onActivityResult(requestCode, resultCode, intent);
	}

	 private boolean isSdcardExisting() {
		 final String state = Environment.getExternalStorageState();
		 if (state.equals(Environment.MEDIA_MOUNTED)) {
			 return true;
		 } else {
			 return false;
		 }
	 }

	// public void resizeImage(Uri uri) {
	// picName = uri.toString();
	// Intent intent = new Intent("com.android.camera.action.CROP");
	// intent.setDataAndType(uri, "image/*");
	// intent.putExtra("crop", "true");
	// intent.putExtra("aspectX", 1);
	// intent.putExtra("aspectY", 1);
	// intent.putExtra("outputX", 150);
	// intent.putExtra("outputY", 150);
	// intent.putExtra("return-data", true);
	// startActivityForResult(intent, RESIZE_REQUEST_CODE);
	// }
	 @SuppressWarnings("deprecation")
	 private void showResizeImage(Intent data) {
		 Bundle extras = data.getExtras();
		 if (extras != null) {
			 Bitmap photo = extras.getParcelable("data");
			 Drawable drawable = new BitmapDrawable(photo);
			 pic.setImageDrawable(drawable);
		 }
	 }
	private void showResizeImage(Uri uri) {
		//Log.i("Debug-uri", uri.toString());
		picName = GetPathFromUri4kitkat.getPath(AddDepositActivity.this, uri);// uri.toString();
		ContentResolver cr = this.getContentResolver();
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
		} catch (FileNotFoundException e) {
		}
		pic.setImageBitmap(bitmap);
	}

	 private Uri getImageUri() {
		 if (tmpPicName == null){
			 ds = new DepositService();
			 tmpPicName = ds.getAPicName(AddDepositActivity.this);
			 tmpPicName += ".jpg";
		 }
		 //Log.i("Debug-picName",picName);
		 return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
				 tmpPicName));
	 }

}
