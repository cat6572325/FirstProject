package com.yanbober.support_library_demo.Http_Util;

import android.util.*;
import cn.edu.zafu.coreprogress.listener.*;
import cn.edu.zafu.coreprogress.listener.impl.*;
import com.yanbober.support_library_demo.*;
import java.io.*;
import java.util.concurrent.*;
import okhttp3.*;
public class NewOkhttp extends Thread
{
	String url=null;
	File file=null;
	public NewOkhttp(String url,File file)
	{
		this.url=url;
		this.file=file;
	}
	@Override
    public void run() {
	upload();
		}
	private static final OkHttpClient client = new OkHttpClient.Builder()
	//设置超时，不设置可能会报异常
	.connectTimeout(1000, TimeUnit.MINUTES)
	.readTimeout(1000, TimeUnit.MINUTES)
	.writeTimeout(1000, TimeUnit.MINUTES)
	.build();
	//OkHttpClient请求Client
	private void upload() {
        File file = new File("/sdcard/newbitmap.png");
        //此文件必须在手机上存在，实际情况下请自行修改，这个目录下的文件只是在我手机中存在。


        //这个是非ui线程回调，不可直接操作UI
        final ProgressListener progressListener = new ProgressListener() {
            @Override
            public void onProgress(long bytesWrite, long contentLength, boolean done) {
                Log.e("TAG", "bytesWrite:" + bytesWrite);
                Log.e("TAG", "contentLength" + contentLength);
                Log.e("TAG", (100 * bytesWrite) / contentLength + " % done ");
                Log.e("TAG", "done:" + done);
                Log.e("TAG", "================================");
            }
        };


        //这个是ui线程回调，可直接操作UI
        final UIProgressListener uiProgressRequestListener = new UIProgressListener() {
            @Override
            public void onUIProgress(long bytesWrite, long contentLength, boolean done) {
                Log.e("TAG", "bytesWrite:" + bytesWrite);
                Log.e("TAG", "contentLength" + contentLength);
                Log.e("TAG", (100 * bytesWrite) / contentLength + " % done ");
                Log.e("TAG", "done:" + done);
                Log.e("TAG", "================================");
                //ui层回调
             //   uploadProgress.setProgress((int) ((100 * bytesWrite) / contentLength));
                //Toast.makeText(getApplicationContext(), bytesWrite + " " + contentLength + " " + done, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onUIStart(long bytesWrite, long contentLength, boolean done) {
                super.onUIStart(bytesWrite, contentLength, done);
             //   Toast.makeText(getApplicationContext(),"start",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUIFinish(long bytesWrite, long contentLength, boolean done) {
                super.onUIFinish(bytesWrite, contentLength, done);
               // Toast.makeText(getApplicationContext(),"end",Toast.LENGTH_SHORT).show();
            }
        };

        //构造上传请求，类似web表单
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
			.addFormDataPart("hello", "android")
			.addFormDataPart("photofile", file.getName(), RequestBody.create(null, file))
			.addPart(Headers.of("photofile", "form-data; name=\"another\";filename=\"another.dex\""), RequestBody.create(MediaType.parse("application/octet-stream"), file))
			.build();

        //进行包装，使其支持进度回调
		User user=new User();
        final Request request = new Request.Builder().url("http://trying-video.herokuapp.com/user/image/replace?token="+user.token).patch(requestBody)/*  (ProgressHelper.addProgressRequestListener(requestBody, uiProgressRequestListener))*/.build();
        //开始请求
        client.newCall(request).enqueue(new Callback() {
			@Override
			public void onResponse(okhttp3.Call p1,okhttp3.Response r2)
			{
				try
				{
					Log.e("错误", r2.body().string());
				}
				catch (IOException e)
				{}

				
			}
			@Override
			public void onFailure(okhttp3.Call c,IOException e)
			
			{
				Log.e("错误",e.toString());
			}
	
//				@Override
//				public void onFailure(Request request, IOException e) {
//					Log.e("TAG", "error ", e);
//				}
//
//				@Override
//				public void onResponse(Response response) throws IOException {
//					Log.e("TAG", response.body().string());
//				}
			});

    }
}
