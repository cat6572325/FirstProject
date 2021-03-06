package com.yanbober.support_library_demo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yanbober.support_library_demo.Http_Util.Get_LastData_Util;
import com.yanbober.support_library_demo.Http_Util.Http_UploadFile_;
import com.yanbober.support_library_demo.Http_Util.NewOkhttp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
import java.util.Random;

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
                    try {
                    if (bun.getString("?").equals("注册成功"))
                    {
                        JSONObject JS=(JSONObject)msg.obj;
                            User.phone=JS.getString("phone");
                        User._id=JS.getString("_id");
                        dialog.cancel();
                        Intent intent = new Intent(Register_.this, Register_sucess_Message_.class);
                       startActivity(intent);
                      finish();
						
                    }else
                    {
                        //注册失败
                        dialog.cancel();

                    }
            } catch (JSONException e) {
                e.printStackTrace();
            }

                    break;

            }
        }
    };
    User user=new User();
    ProgressDialog dialog=null;
    ImageView Register_Enter,regis_enter;
    Button backbtn;
    EditText phon, pas;
    TextInputLayout til,til1;
    Get_LastData_Util httpUtil;
    URL url;
    int phones=0,pass=0;
    BufferedOutputStream bos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        try {
            initView();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void initView() throws IOException {
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
////刚进注册页面就判断头像文件路径是否存在，不存在就创建并保存一张预置图片
        //注册的同时发送预置头像
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
        backbtn.setOnClickListener(new MyClickListener());


    }
    class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO:注册后跳往
            switch (v.getId()) {
                case R.id.Register_Button:
                    HashMap<String,Object> map=new HashMap<>();
                    map.put("handler",mHandler);
                    map.put("Context",Register_.this);

                    map.put("count",0);
                    map.put("phone",phon.getText().toString());
                    map.put("password",pas.getText().toString());
                    dialog=new ProgressDialog(Register_.this);
                    dialog.setTitle("注册中..");
                    dialog.show();

                    Http_UploadFile_ http_uploadFile_ = new Http_UploadFile_(
                                "http://trying-video.herokuapp.com/reg/user"
                                ,map
                                , "0");
                        Thread x = new Thread(http_uploadFile_);
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
                    phones=s.length();
                    if (s.length() !=11) {
                        til.setErrorEnabled(true);
                        til.setError("手机号必须11位");
                        Register_Enter.setVisibility(View.INVISIBLE);
                    }else
                    if (pass>8){
                    til.setErrorEnabled(false);
                    Register_Enter.setVisibility(View.VISIBLE);}
                    break;
                case R.id.pas_key:
                    pass=s.length();
                    if (s.length() <8) {
                        til1.setErrorEnabled(true);
                        til1.setError("密码必须大于8位小于20");
                        Register_Enter.setVisibility(View.INVISIBLE);
                    }else
                    if (phones>10) {
                        til.setErrorEnabled(false);
                        Register_Enter.setVisibility(View.VISIBLE);
                    }
                    break;
            }




        }////////if input


    }
}
