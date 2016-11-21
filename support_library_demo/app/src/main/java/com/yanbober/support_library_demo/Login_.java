package com.yanbober.support_library_demo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import com.yanbober.support_library_demo.DataHelpers.DataHelper;
import com.yanbober.support_library_demo.Http_Util.Get_LastData_Util;
import com.yanbober.support_library_demo.Http_Util.Http_UploadFile_;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import com.yanbober.support_library_demo.DataHelpers.*;
/**
 * Created by cat6572325 on 16-11-18.
 */
public class Login_ extends AppCompatActivity {
    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Bundle bun;
            switch (msg.what) {
                case 0:
                    bun=msg.getData();
                    try {
                        if (bun.get("?").equals("登录成功"))
                        {
                            JSONArray array=(JSONArray)msg.obj;
                            JSONObject token_=array.getJSONObject(0);

                          JSONObject  phoneone=array.getJSONObject(1);

                            user.phone=phoneone.getString("phone");
                            user.name=phoneone.getString("phone");
                            User._id=phoneone.getString("_id");
                            Intent intent = new Intent(Login_.this, MainActivity.class);
                            startActivity(intent);
                            // finish();
                        }else
                        {
                            //注册失败
                            Looper.prepare();
                            Toast.makeText(Login_.this,"登录失败："+bun.get("!").toString(),Toast.LENGTH_SHORT).show();
                            Looper.loop();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;


            }
        }
    };
    DataHelper dataserver;
    String[] str1;
    SQLiteDatabase db=null;
    User user=new User();
    ImageView Register_Enter,regis_enter;
    Button backbtn,register,findpass;
    EditText phon, pas;
    TextInputLayout til,til1;
    Get_LastData_Util httpUtil;
    URL url;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initView();

    }

    protected void initView() {
        Register_Enter = (ImageView) findViewById(R.id.Register_Button);
        backbtn = (Button) findViewById(R.id.Login_Back);
        phon = (EditText) findViewById(R.id.login_phone);
        pas = (EditText) findViewById(R.id.login_pass);
        til=(TextInputLayout)findViewById(R.id.login_phone1);
        til1=(TextInputLayout)findViewById(R.id.login_pass1);
        register=(Button)findViewById(R.id.Login_Regi_Button);
        findpass=(Button)findViewById(R.id.Login_findPassword_Button);
        phon=til.getEditText();
        pas=til1.getEditText();


        phon.addTextChangedListener(new Mytextwatcher());
        pas.addTextChangedListener(new Mytextwatcher());
        Register_Enter.setVisibility(View.INVISIBLE);
        Register_Enter.setOnClickListener(new MyClickListener());

        backbtn.setOnClickListener(new MyClickListener());


        //数据库操作
        dataserver=new DataHelper(Login_.this);

        str1=dataserver.readData("flag|").split("\\|");
        if(str1[0].equals("flag"))
        {//如果已经登录且没有正常退出

        }else {
            if (str1[5].equals("1")) {

            }
        }

                //数据库操作

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
                    dataserver.inst(db,user.phone+"|"+user.pas+"|"+user.phone+"|null|1",Login_.this);
                    //手机　密码　名字　头像序列　是否登录状态
                    try {
                        url = new URL("http://192.168.1.112:1103/login");


                        Http_UploadFile_ http_uploadFile_ = new Http_UploadFile_(Login_.this
                                , mHandler
                                , url
                                , "1"//登录
                                , "POST"
                                , "phone=" + phon.getText().toString() + "&userpassword=" + pas.getText().toString());
                        Thread x = new Thread(http_uploadFile_);
                        x.start();
                         }catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.Register_back:
                    //TODO 左上角返回
                    finish();
                    break;
                case R.id.Login_Regi_Button:
                    Intent intent=new Intent(Login_.this,Register_.class);
                    startActivity(intent);
                    break;
            }
        }
    }


    class Mytextwatcher implements TextWatcher {
        public void afterTextChanged(Editable s) {


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
                case R.id.login_phone:
                    if (s.length() !=11) {
                        til.setErrorEnabled(true);
                        til.setError("手机号必须11位");
                        Register_Enter.setVisibility(View.INVISIBLE);
                    }else
                        til.setErrorEnabled(false);
                    Register_Enter.setVisibility(View.VISIBLE);
                    break;
                case R.id.login_pass:
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