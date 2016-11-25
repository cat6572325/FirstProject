package com.yanbober.support_library_demo.Http_Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;


import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.OkHttpUtilInterface;
import com.okhttplib.annotation.CacheLevel;
import com.okhttplib.callback.ProgressCallback;
import com.socks.okhttp.plus.OkHttpProxy;

import com.socks.okhttp.plus.listener.UploadListener;
import com.socks.okhttp.plus.model.Progress;
import com.squareup.okhttp.Response;
import com.yanbober.support_library_demo.MainActivity;
import com.yanbober.support_library_demo.User;

import java.io.File;

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by cat6572325 on 16-11-17.
 */
public class http_thread_ extends Thread {
        String str;
    Handler handler;
    String url,path,key;

    User user=new User();
    public com.squareup.okhttp.OkHttpClient client1=new com.squareup.okhttp.OkHttpClient();

    public http_thread_(String url,String path,Handler handler,String key)
    {

        this.str=str;
        this.handler=handler;
        this.url=url;
        this.path=path;
        this.key=key;



    }
    OkHttpClient okHttpClient = OkHttpProxy.getInstance();
    @Override
    public void run() {
        super.run();
       pul();
    }
    private void doUploadImg() {

        OkHttpUtilInterface okHttpUtil = OkHttpUtil.Builder()
                .setCacheLevel(CacheLevel.FIRST_LEVEL)
                .setConnectTimeout(25).build(this);
//一个okHttpUtil即为一个网络连接

        HttpInfo info = HttpInfo.Builder()
                .setUrl(url+user.token)
                .addUploadFile("file", path, new ProgressCallback() {
                    @Override

                    public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
                        // uploadProgress.setProgress(percent);
                        Log.e("ssss","上传进度：" + percent);

                        int i=percent;
                    }
                    @Override
                    public void onResponseMain(String filePath,HttpInfo info)
                    {
                        String str=info.getRetDetail();
                        Log.e("上传信息",str);
                    }
                })
                .build();
        OkHttpUtil.getDefault(this).doUploadFileAsync(info);
    }


    public void pul()
    {
       final Message msg=new Message();
        final Bundle bundle=new Bundle();
        File file = new File(path);//Environment.getExternalStorageDirectory(), "jiandan02.jpg");
        if (!file.exists()) {
          //  Toast.makeText(MainActivity.this, "File not exits！", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> param = new HashMap<>();
        param.put("file","videofile");
        Pair<String, File> pair = new Pair(key, file);

        OkHttpProxy
                .upload()
                .url(url)
                .file(pair)
                .setParams(param)
                .setWriteTimeOut(20)
                .start(new UploadListener() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("完成", e.toString());
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        String str=response.body().string();
                        Log.e("aaaa",str);
                        Log.e("完成", String.valueOf(response.isSuccessful()));
                        bundle.putString("?","success");
                        bundle.putString("!",str);
                        msg.what=0;
                        handler.sendMessage(msg);

                    }


                    @Override
                    public void onSuccess(okhttp3.Response response) {
                        try {

                            String str=response.body().string();
                            bundle.putString("?","success");
                            bundle.putString("!",str);
                            msg.what=0;
                            handler.sendMessage(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {
                      //  tv_response.setText(e.getMessage());
                        Log.e("bbbb",String.valueOf(e));
                        bundle.putString("?","error");
                       bundle.putString("!",e.toString());
                        msg.what=0;
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onUIProgress(Progress progress) {
                        int pro = (int) ((progress.getCurrentBytes() + 0.0) / progress.getTotalBytes() * 100);
                        if (pro > 0) {
                         int o=pro;
                            Log.e("sssss",String.valueOf(pro));
                            bundle.putString("?","ing");
                            msg.arg1=pro;
                            msg.what=0;
                            handler.sendMessage(msg);
                        }
                      //  KLog.d("pro = " + pro + " getCurrentBytes = " + progress.getCurrentBytes() + " getTotalBytes = " + progress.getTotalBytes());
                    }
                });
    }
}
