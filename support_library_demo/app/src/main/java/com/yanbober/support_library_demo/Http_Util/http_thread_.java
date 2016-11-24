package com.yanbober.support_library_demo.Http_Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;



import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MultipartBuilder;
import com.yanbober.support_library_demo.User;

import java.io.File;

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.edu.zafu.coreprogress.helper.ProgressHelper;
import cn.edu.zafu.coreprogress.listener.ProgressRequestListener;
import cn.edu.zafu.coreprogress.listener.impl.UIProgressRequestListener;
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
    URL url;
    public com.squareup.okhttp.OkHttpClient client1=new com.squareup.okhttp.OkHttpClient();

    public http_thread_()
    {

        this.str=str;
        this.handler=handler;
        this.url=url;
    }
    @Override
    public void run() {
        super.run();


        File file = new File("/sdcard/RoundVideo/video1.3gp");
        //此文件必须在手机上存在，实际情况下请自行修改，这个目录下的文件只是在我手机中存在。


        //这个是非ui线程回调，不可直接操作UI
        final ProgressRequestListener progressListener = new ProgressRequestListener() {
            @Override
            public void onRequestProgress(long bytesWrite, long contentLength, boolean done) {
                Log.e("TAG", "bytesWrite:" + bytesWrite);
                Log.e("TAG", "contentLength" + contentLength);
                Log.e("TAG", (100 * bytesWrite) / contentLength + " % done ");
                Log.e("TAG", "done:" + done);
                Log.e("TAG", "================================");
            }
        };


        //这个是ui线程回调，可直接操作UI
        final UIProgressRequestListener uiProgressRequestListener = new UIProgressRequestListener() {
            @Override
            public void onUIRequestProgress(long bytesWrite, long contentLength, boolean done) {
                Log.e("TAG", "bytesWrite:" + bytesWrite);
                Log.e("TAG", "contentLength" + contentLength);
                Log.e("TAG", (100 * bytesWrite) / contentLength + " % done ");
                Log.e("TAG", "done:" + done);
                Log.e("TAG", "================================");
                //ui层回调
                // uploadProgress.setProgress((int) ((100 * bytesWrite) / contentLength));
                Message msg = new Message();
                msg.arg1 = (int) ((100 * bytesWrite) / contentLength);
                handler.sendMessage(msg);
                //Toast.makeText(getApplicationContext(), bytesWrite + " " + contentLength + " " + done, Toast.LENGTH_LONG).show();
            }
        };

        //构造上传请求，类似web表单
        com.squareup.okhttp.RequestBody requestBody = new MultipartBuilder().type(MultipartBuilder.FORM)

                .addFormDataPart("video", "filename", com.squareup.okhttp.RequestBody.create(com.squareup.okhttp.MediaType.parse("application/octet-stream"), file))
                .build();
        User user = new User();
        //进行包装，使其支持进度回调
        final com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder().url("http://trying-video.herokuapp.com/user/video?token=" + user.token)//"http://121.41.119.107:81/test/result.php")
                .post(ProgressHelper.addProgressRequestListener(requestBody, uiProgressRequestListener))
                .build();
        client1.newCall(request).enqueue(new com.squareup.okhttp.Callback() {

            @Override
            public void onFailure(com.squareup.okhttp.Request request, IOException e) {

            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {

            }
        });

        //开始请求
       /* client1.newCall(request).enqueue(new com.squareup.okhttp.Callback() {

            public void onFailure(com.squareup.okhttp.Call call, IOException e) {

            }

            public void onResponse(com.squareup.okhttp.Call call, com.squareup.okhttp.Response response) throws IOException {
            }

            @Override
            public void onFailure(com.squareup.okhttp.Request request, IOException e) {
                Log.e("TAG", "error ", e);
            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                Log.e("TAG", response.body().string());
            }

        });*/

    }

}
