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

	 /* ������ */  
    private static final int DOWNLOAD = 1;  
    /* ���ؽ��� */  
    private static final int DOWNLOAD_FINISH = 2;
    /* ���Ӵ���*/
    private static final int SERVICE_ERROR = 3;
    /* �������汾 */
    private String serviseVersion;
    /* ������� */
    private String appName;
    /* ���ص�ַ */
    private String downlaodUrl;
    /* ���ر���·�� */  
    private String mSavePath;  
    /* ��¼���������� */  
    private int progress;  
    /* �Ƿ�ȡ������ */  
    private boolean cancelUpdate = false;  
  
    private Context mContext;  
    /* ���½����� */  
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
            // ��������  
            case DOWNLOAD:  
                // ���ý�����λ��  
				mProgress.setProgress(progress);
                break;  
            case DOWNLOAD_FINISH:  
                // ��װ�ļ�  
                installApk();  
                break;  
            case SERVICE_ERROR:
            	Toast.makeText(mContext,"�޷������������������Ժ����ԣ�",Toast.LENGTH_LONG).show();
            	break;
            default:  
                break;  
            }  
        };  
    };  
  
    /** 
     * ���������� 
     */  
    public void checkUpdate()  
    {  
        if (isUpdate())  
        {  
            // ��ʾ��ʾ�Ի���  
            showNoticeDialog();  
        	//Toast.makeText(mContext,"�и���Ŷ", Toast.LENGTH_LONG).show();  
        } else  
        {  
            Toast.makeText(mContext,"����ǰ�İ汾Ϊ���°汾!", Toast.LENGTH_LONG).show();  
        }  
    }  
  
    /** 
     * �������Ƿ��и��°汾 
     *  
     * @return 
     */  
    private boolean isUpdate()  
    {  
        // ��ȡ��ǰ����汾  
        double version = Config.LOCAL_VERSION;//getVersionCode(mContext);   
		if(version < Double.valueOf(serviseVersion)){
			return true;
		}else{
			return false;    
		}
    } 
  
    /** 
     * ��ȡ����汾�� 
     *  
     * @param context 
     * @return 
     */  
    private int getVersionCode(Context context)  
    {  
        int versionCode = 0;  
        try  
        {  
            // ��ȡ����汾�ţ���ӦAndroidManifest.xml��android:versionCode  
            versionCode = context.getPackageManager().getPackageInfo("com.iteye.androidtoast", 0).versionCode;  
        } catch (NameNotFoundException e)  
        {  
            e.printStackTrace();  
        }  
        return versionCode;  
    }  
  
    /** 
     * ��ʾ������¶Ի��� 
     */  
    private void showNoticeDialog()  
    {  
        // ����Ի���  
        AlertDialog.Builder builder = new Builder(mContext);  
        builder.setTitle("�������");  
        builder.setMessage("�Ҹ������и���Ŷ��Ҫ���ף�");  
        // ����  
        builder.setPositiveButton("��������", new OnClickListener()  
        {  
            @Override  
            public void onClick(DialogInterface dialog, int which)  
            {  
                dialog.dismiss();  
                // ��ʾ���ضԻ���  
                showDownloadDialog();  
            }  
        });  
        // �Ժ����  
        builder.setNegativeButton("�Ժ����", new OnClickListener()  
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
     * ��ʾ������ضԻ��� 
     */  
    private void showDownloadDialog()  
    {  
        // ����������ضԻ���  
        AlertDialog.Builder builder = new Builder(mContext);  
        builder.setTitle("��������");  
        // �����ضԻ������ӽ�����  
        final LayoutInflater inflater = LayoutInflater.from(mContext);  
        View v = inflater.inflate(R.layout.softupdate_progress, null);  
        mProgress = (NumberProgressBar) v.findViewById(R.id.update_progress);  
        builder.setView(v);  
        // ȡ������  
        builder.setNegativeButton("ȡ��", new OnClickListener()  
        {  
            @Override  
            public void onClick(DialogInterface dialog, int which)  
            {  
                dialog.dismiss();  
                // ����ȡ��״̬  
                cancelUpdate = true;  
            }  
        });  
        mDownloadDialog = builder.create();  
        mDownloadDialog.show();  
        // �����ļ�  
        downloadApk();  
    }  
  
    /** 
     * ����apk�ļ� 
     */  
    private void downloadApk()  
    {  
        // �������߳��������  
        new downloadApkThread().start();  
    }  
  
    /** 
     * �����ļ��߳� 
     */  
    private class downloadApkThread extends Thread  
    {  
        @Override  
        public void run()  
        {  
            try  
            {  
                // �ж�SD���Ƿ���ڣ������Ƿ���ж�дȨ��  
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))  
                {  
                    // ��ô洢����·��  
                    String sdpath = Environment.getExternalStorageDirectory() + "/";  
                    mSavePath = sdpath + "download";  
                    URL url = new URL(downlaodUrl);  
                    // ��������  
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    int code = conn.getResponseCode();
                    	//conn.connect();  
                    	if(code == 200){
                    		// ��ȡ�ļ���С  
                    		int length = conn.getContentLength();  
                    		// ����������  
                    		InputStream is = conn.getInputStream();  
  
                    		File file = new File(mSavePath);  
                    		// �ж��ļ�Ŀ¼�Ƿ����  
                    		if (!file.exists())  
                    		{  
                    			file.mkdir();  
                    		}  
                    		File apkFile = new File(mSavePath, appName);  
                    		FileOutputStream fos = new FileOutputStream(apkFile);  
                    		int count = 0;  
                    		// ����  
                    		byte buf[] = new byte[1024];  
                    		// д�뵽�ļ���  
                    		do  
                    		{  
                    			int numread = is.read(buf);  
                    			count += numread;  
                    			// ���������λ��  
                    			progress = (int) (((float) count / length) * 100);  
                    			// ���½���  
                    			Thread.sleep(1); 
                    			mHandler.sendEmptyMessage(DOWNLOAD);  
                    			if (numread <= 0)  
                    			{  
                    				// �������  
                    				mHandler.sendEmptyMessage(DOWNLOAD_FINISH);  
                    				break;  
                    			}  
                    			// д���ļ�  
                    			fos.write(buf, 0, numread);  
                    		} while (!cancelUpdate);// ���ȡ����ֹͣ����.  
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
            // ȡ�����ضԻ�����ʾ  
            mDownloadDialog.dismiss();  
        }  
    };  
  
    /** 
     * ��װAPK�ļ� 
     */  
    private void installApk()  
    {  
        File apkfile = new File(mSavePath, appName);  
        if (!apkfile.exists())  
        {  
            return;  
        }  
        // ͨ��Intent��װAPK�ļ�  
        Intent i = new Intent(Intent.ACTION_VIEW);  
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");  
        mContext.startActivity(i);  
    }
}
