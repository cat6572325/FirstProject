package com.yanbober.support_library_demo.Http_Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.*;
import android.widget.Toast;

import cn.edu.zafu.coreprogress.listener.*;
import cn.edu.zafu.coreprogress.listener.impl.*;
import com.yanbober.support_library_demo.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.concurrent.*;
import okhttp3.*;
public class NewOkhttp extends Thread
{
	String url=null;
	File file=null;
	HashMap<String ,Object> map=null;
	public NewOkhttp(String url,File file,HashMap<String ,Object> map)
	{
		this.url=url;
		this.file=file;
		this.map=map;
	}
	@Override
    public void run() {
		switch ((Integer)map.get("count"))
		{
			case 0:
				upload();
				break;
			case 1:
				patchHead();
				break;
		}

		}
	private static final OkHttpClient client = new OkHttpClient.Builder()
	//设置超时，不设置可能会报异常
	.connectTimeout(3000, TimeUnit.MINUTES)
	.readTimeout(3000, TimeUnit.MINUTES)
	.writeTimeout(3000, TimeUnit.MINUTES)
	.build();
	//OkHttpClient请求Client
	private void upload() {

        //此文件必须在手机上存在，实际情况下请自行修改，这个目录下的文件只是在我手机中存在。


        //构造上传请求，类似web表单
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
			//.addFormDataPart("hello", "android")
			.addFormDataPart("photofile", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
			//.addPart(Headers.of("photofile", "form-data; name=\"another\";filename=\"another.dex\""), RequestBody.create(MediaType.parse("application/octet-stream"), file))
			.build();

		final Context context=(Context)map.get("Context");
        //进行包装，使其支持进度回调
		User user=new User();
        final Request request = new Request
				.Builder()
				.url(url)
				.post(requestBody)/*  (ProgressHelper.addProgressRequestListener(requestBody, uiProgressRequestListener))*/
				.build();
        //开始请求
        client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(okhttp3.Call p1,okhttp3.Response r2)
			{
				try
				{
					//上传头像，目前只在首页面对第一次登录的用户，上传预置头像用的
					final String str=r2.body().string();
					JSONObject jsonObject=new JSONObject(str);
					Handler handler=(Handler)map.get("handler");
					Bundle bundle=new Bundle();
					bundle.putString("?","上传");
					bundle.putString("url",jsonObject.getString("headprturl"));
					handler.sendEmptyMessage(9);
					Log.e("成功", str);
				}
				catch (IOException e)
				{

					if (context!=null)
					{
						final String exception=e.toString();


					}
					Log.e("在上传预置头像的时候", e.toString());
				} catch (JSONException e) {
					Log.e("在上传预置头像的时候", e.toString());
				}


			}
			@Override
			public void onFailure(okhttp3.Call c,IOException e)
			
			{
				Log.e("错误",e.toString());
			}

			});

    }
	private void patchHead()
	{
		String key=null;
		if (map.containsKey("key"))
		{
			key=map.get("key").toString();
		}
	//构造上传请求，类似web表单
	RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
			//.addFormDataPart("hello", "android")
			.addFormDataPart("photofile", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))//申明图片类型
			//.addPart(Headers.of("photofile", file.getName()), RequestBody.create(MediaType.parse("application/octet-stream"), file))
			.build();
Log.e("头像",file.getPath());
	final Context context=(Context)map.get("Context");
	//进行包装，使其支持进度回调
	User user=new User();

	final Request request = new Request.Builder().url(url).patch(requestBody).build(); //.patch(requestBody)/*  (ProgressHelper.addProgressRequestListener(requestBody, uiProgressRequestListener))*/.build();
	//开始请求
			if (key!=null)
	client.newCall(request).enqueue(new Callback() {
		@Override
		public void onResponse(okhttp3.Call p1, okhttp3.Response r2) {
			try {

				final String str = r2.body().string();
				Handler handler=(Handler)map.get("handler");
				handler.sendEmptyMessage(0);
			//	dialog.cancel();
				if (context != null) {

					new Thread(new Runnable() {
						@Override
						public void run() {
							Looper.prepare();
							Toast.makeText(context, str, Toast.LENGTH_LONG).show();
							Looper.loop();
						}
					});
				}
				Log.e("成功", str);
			} catch (IOException e) {

				if (context != null) {
					final String exception = e.toString();

				//	dialog.cancel();

				}
				Log.e("错误1", e.toString());
				//dialog.cancel();
			}


		}

		@Override
		public void onFailure(okhttp3.Call c, IOException e)

		{
			Log.e("错误", e.toString());
		}

	});
	}

}
