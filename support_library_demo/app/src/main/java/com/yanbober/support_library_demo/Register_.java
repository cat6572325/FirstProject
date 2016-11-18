package com.yanbober.support_library_demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yanbober.support_library_demo.Http_Util.Get_LastData_Util;
import com.yanbober.support_library_demo.Http_Util.Http_UploadFile_;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 注册
 *
 *
 */
public class Register_ extends AppCompatActivity {
    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Bundle bun;
            switch (msg.what) {




                case 0:
                    bun=msg.getData();

                    if (bun.get("?").equals("注册成功"))
                    {
                        user.phone=bun.get("phone").toString();
                        Intent intent = new Intent(Register_.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else
                    {
                        //注册失败
                        Toast.makeText(Register_.this,"注册失败："+bun.get("!").toString(),Toast.LENGTH_SHORT).show();
                    }


                    break;


            }
        }
    };
    User user=new User();
    ImageView Register_Enter,regis_enter;
    Button backbtn;
    EditText phon, pas;
    TextInputLayout til,til1;
    Get_LastData_Util httpUtil;
    URL url;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        initView();

    }

    protected void initView() {
        Register_Enter = (ImageView) findViewById(R.id.Register_Button);
        backbtn = (Button) findViewById(R.id.Register_back);
        phon = (EditText) findViewById(R.id.phone_key);
        pas = (EditText) findViewById(R.id.pas_key);
        til=(TextInputLayout)findViewById(R.id.Register_TextInput);
        til1=(TextInputLayout)findViewById(R.id.Register_TextInput1);
        phon=til.getEditText();
        pas=til1.getEditText();


        phon.addTextChangedListener(new Mytextwatcher());
        pas.addTextChangedListener(new Mytextwatcher());
        Register_Enter.setVisibility(View.INVISIBLE);
        Register_Enter.setOnClickListener(new MyClickListener());

        backbtn.setOnClickListener(new MyClickListener());

    }

    class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO:注册后跳往
            switch (v.getId()) {
                case R.id.Register_Button:
                    //首先执行写入手机号
                    //  networkAsyncTask = new NetworkAsyncTask();
                    //新建一个发送POST的class
                    //  networkAsyncTask.execute("NETWORK_POST_JSON",phon.getText().toString(),pas.getText().toString());
                    //执行
                    try { url= new URL("http://192.168.1.112:1103/reg/user"); }
                    catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                        Http_UploadFile_ http_uploadFile_ = new Http_UploadFile_(Register_.this, mHandler
                                ,url
                                , "0"//注册
                                , "phone="+phon.getText().toString()+"&userpassword="+pas.getText().toString());
                    Thread x=new Thread(http_uploadFile_);
                    x.start();

                    break;
                case R.id.Register_back:
                    //TODO 左上角返回
                    finish();
                    break;
            }
        }
    }


    class Mytextwatcher implements TextWatcher {
        public void afterTextChanged(Editable s) {
// TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged
                (CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged
                (CharSequence s, int start, int before, int count) {

            int a = getCurrentFocus().getId();
            //当前焦点
            switch (a)
            {
                case R.id.phone_key:
                    if (s.length() !=11) {
                        til.setErrorEnabled(true);
                        til.setError("手机号必须11位");
                        Register_Enter.setVisibility(View.INVISIBLE);
                    }else
                    til.setErrorEnabled(false);
                    Register_Enter.setVisibility(View.VISIBLE);
                    break;
                case R.id.pas_key:
                    if (s.length() <8 && s.length()>20) {
                        til1.setErrorEnabled(true);
                        til1.setError("密码必须大于8位小于20");
                        Register_Enter.setVisibility(View.INVISIBLE);
                    }else
                        til.setErrorEnabled(false);
                    Register_Enter.setVisibility(View.VISIBLE);
                    break;
            }




        }////////if input


    }
}