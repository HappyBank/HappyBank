package com.happybank.service;

import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.net.HttpURLConnection;  
import java.net.MalformedURLException;  
import java.net.URL;  

import com.example.happybank.R;
import com.happybank.activity.NumberProgressBar;
import com.happybank.config.Config;

import android.app.AlertDialog;  
import android.app.AlertDialog.Builder;  
import android.app.Dialog;  
import android.content.Context;  
import android.content.DialogInterface;  
import android.content.DialogInterface.OnClickListener;  
import android.content.Intent;  
import android.content.pm.PackageManager.NameNotFoundException;  
import android.net.Uri;  
import android.os.Environment;  
import android.os.Handler;  
import android.os.Message;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.widget.ProgressBar;  
import android.widget.Toast;  

public class UpdateService {

	 /* 下载中 */  
    private static final int DOWNLOAD = 1;  
    /* 下载结束 */  
    private static final int DOWNLOAD_FINISH = 2;
    /* 连接错误*/
    private static final int SERVICE_ERROR = 3;
    /* 服务器版本 */
    private String serviseVersion;
    /* 软件名称 */
    private String appName;
    /* 下载地址 */
    private String downlaodUrl;
    /* 下载保存路径 */  
    private String mSavePath;  
    /* 记录进度条数量 */  
    private int progress;  
    /* 是否取消更新 */  
    private boolean cancelUpdate = false;  
  
    private Context mContext;  
    /* 更新进度条 */  
    //private ProgressBar mProgress;
    private NumberProgressBar mProgress;
    private Dialog mDownloadDialog;  
  
    public UpdateService(Context context, String version, String name, String url)  
    {  
        this.mContext = context;  
        this.serviseVersion = version;
        this.appName = name;
        this.downlaodUrl = url;
    }  
    
    private Handler mHandler = new Handler()  
    {  
        public void handleMessage(Message msg)  
        {  
            switch (msg.what)  
            {  
            // 正在下载  
            case DOWNLOAD:  
                // 设置进度条位置  
				mProgress.setProgress(progress);
                break;  
            case DOWNLOAD_FINISH:  
                // 安装文件  
                installApk();  
                break;  
            case SERVICE_ERROR:
            	Toast.makeText(mContext,"无法连接至服务器，请稍后再试！",Toast.LENGTH_LONG).show();
            	break;
            default:  
                break;  
            }  
        };  
    };  
  
    /** 
     * 检测软件更新 
     */  
    public void checkUpdate()  
    {  
        if (isUpdate())  
        {  
            // 显示提示对话框  
            showNoticeDialog();  
        	//Toast.makeText(mContext,"有更新哦", Toast.LENGTH_LONG).show();  
        } else  
        {  
            Toast.makeText(mContext,"您当前的版本为最新版本!", Toast.LENGTH_LONG).show();  
        }  
    }  
  
    /** 
     * 检查软件是否有更新版本 
     *  
     * @return 
     */  
    private boolean isUpdate()  
    {  
        // 获取当前软件版本  
        double version = Config.LOCAL_VERSION;//getVersionCode(mContext);   
		if(version < Double.valueOf(serviseVersion)){
			return true;
		}else{
			return false;    
		}
    } 
  
    /** 
     * 获取软件版本号 
     *  
     * @param context 
     * @return 
     */  
    private int getVersionCode(Context context)  
    {  
        int versionCode = 0;  
        try  
        {  
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode  
            versionCode = context.getPackageManager().getPackageInfo("com.iteye.androidtoast", 0).versionCode;  
        } catch (NameNotFoundException e)  
        {  
            e.printStackTrace();  
        }  
        return versionCode;  
    }  
  
    /** 
     * 显示软件更新对话框 
     */  
    private void showNoticeDialog()  
    {  
        // 构造对话框  
        AlertDialog.Builder builder = new Builder(mContext);  
        builder.setTitle("软件更新");  
        builder.setMessage("幸福银行有更新哦！要吗，亲？");  
        // 更新  
        builder.setPositiveButton("立即更新", new OnClickListener()  
        {  
            @Override  
            public void onClick(DialogInterface dialog, int which)  
            {  
                dialog.dismiss();  
                // 显示下载对话框  
                showDownloadDialog();  
            }  
        });  
        // 稍后更新  
        builder.setNegativeButton("稍后更新", new OnClickListener()  
        {  
            @Override  
            public void onClick(DialogInterface dialog, int which)  
            {  
                dialog.dismiss();  
            }  
        });  
        Dialog noticeDialog = builder.create();  
        noticeDialog.show();  
    }  
  
    /** 
     * 显示软件下载对话框 
     */  
    private void showDownloadDialog()  
    {  
        // 构造软件下载对话框  
        AlertDialog.Builder builder = new Builder(mContext);  
        builder.setTitle("正在下载");  
        // 给下载对话框增加进度条  
        final LayoutInflater inflater = LayoutInflater.from(mContext);  
        View v = inflater.inflate(R.layout.softupdate_progress, null);  
        mProgress = (NumberProgressBar) v.findViewById(R.id.update_progress);  
        builder.setView(v);  
        // 取消更新  
        builder.setNegativeButton("取消", new OnClickListener()  
        {  
            @Override  
            public void onClick(DialogInterface dialog, int which)  
            {  
                dialog.dismiss();  
                // 设置取消状态  
                cancelUpdate = true;  
            }  
        });  
        mDownloadDialog = builder.create();  
        mDownloadDialog.show();  
        // 现在文件  
        downloadApk();  
    }  
  
    /** 
     * 下载apk文件 
     */  
    private void downloadApk()  
    {  
        // 启动新线程下载软件  
        new downloadApkThread().start();  
    }  
  
    /** 
     * 下载文件线程 
     */  
    private class downloadApkThread extends Thread  
    {  
        @Override  
        public void run()  
        {  
            try  
            {  
                // 判断SD卡是否存在，并且是否具有读写权限  
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))  
                {  
                    // 获得存储卡的路径  
                    String sdpath = Environment.getExternalStorageDirectory() + "/";  
                    mSavePath = sdpath + "download";  
                    URL url = new URL(downlaodUrl);  
                    // 创建连接  
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    int code = conn.getResponseCode();
                    	//conn.connect();  
                    	if(code == 200){
                    		// 获取文件大小  
                    		int length = conn.getContentLength();  
                    		// 创建输入流  
                    		InputStream is = conn.getInputStream();  
  
                    		File file = new File(mSavePath);  
                    		// 判断文件目录是否存在  
                    		if (!file.exists())  
                    		{  
                    			file.mkdir();  
                    		}  
                    		File apkFile = new File(mSavePath, appName);  
                    		FileOutputStream fos = new FileOutputStream(apkFile);  
                    		int count = 0;  
                    		// 缓存  
                    		byte buf[] = new byte[1024];  
                    		// 写入到文件中  
                    		do  
                    		{  
                    			int numread = is.read(buf);  
                    			count += numread;  
                    			// 计算进度条位置  
                    			progress = (int) (((float) count / length) * 100);  
                    			// 更新进度  
                    			Thread.sleep(1); 
                    			mHandler.sendEmptyMessage(DOWNLOAD);  
                    			if (numread <= 0)  
                    			{  
                    				// 下载完成  
                    				mHandler.sendEmptyMessage(DOWNLOAD_FINISH);  
                    				break;  
                    			}  
                    			// 写入文件  
                    			fos.write(buf, 0, numread);  
                    		} while (!cancelUpdate);// 点击取消就停止下载.  
                    		fos.close();  
                    		is.close();
                    	}else{
                    		mHandler.sendEmptyMessage(SERVICE_ERROR);
                    	}
                }  
            } catch (MalformedURLException e)  
            {  
                e.printStackTrace();  
            } catch (IOException e)  
            {  
                e.printStackTrace();  
            } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
            // 取消下载对话框显示  
            mDownloadDialog.dismiss();  
        }  
    };  
  
    /** 
     * 安装APK文件 
     */  
    private void installApk()  
    {  
        File apkfile = new File(mSavePath, appName);  
        if (!apkfile.exists())  
        {  
            return;  
        }  
        // 通过Intent安装APK文件  
        Intent i = new Intent(Intent.ACTION_VIEW);  
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");  
        mContext.startActivity(i);  
    }
}
