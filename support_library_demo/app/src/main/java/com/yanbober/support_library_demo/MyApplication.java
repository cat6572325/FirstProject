package com.yanbober.support_library_demo;

import android.app.Application;

import com.socks.okhttp.plus.OkHttpProxy;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import okhttp3.OkHttpClient;


/**
 * Created by cat6572325 on 16-11-25.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient okHttpClient = OkHttpProxy.getInstance();
       // okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
      //  okHttpClient.setReadTimeout(15, TimeUnit.SECONDS);
      //  okHttpClient.setWriteTimeout(15, TimeUnit.SECONDS);
    }
}
