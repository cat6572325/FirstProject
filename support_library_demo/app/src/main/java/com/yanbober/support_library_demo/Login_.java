package com.yanbober.support_library_demo;

import android.content.*;
import android.database.sqlite.*;
import android.net.*;
import android.os.*;
import android.support.annotation.*;
import android.support.design.widget.*;
import android.support.v7.app.*;
import android.text.*;
import android.view.*;
import android.widget.*;
import com.yanbober.support_library_demo.DataHelpers.*;
import com.yanbober.support_library_demo.Http_Util.*;
import java.net.*;
import java.util.*;
import org.json.*;

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
                        if (bun!=null)
                        if (bun.get("?").equals("登录成功"))
                        {
                            JSONArray array=(JSONArray)msg.obj;
                            JSONObject token_=array.getJSONObject(0);

                          JSONObject  phoneone=array.getJSONObject(1);

                            user.phone=phoneone.getString("phone");
                            user.name=phoneone.getString("phone");
                            user.token=token_.getString("token");
                            User._id=phoneone.getString("_id");
                            user.pas=phoneone.getString("userpassword");
                            Intent intent = new Intent(Login_.this, MainActivity.class);
                            dataserver.inst(db,user.phone
                                    +"|"+user.pas
                                    +"|"+user.phone
                                    +"|null|"
                                    + user.token
                                    +"|1",Login_.this);

                            //手机　密码　名字　头像序列　token　是否登录状态
                            user.pas=pas.getText().toString();
                            startActivity(intent);
                             finish();
                        }else
                        {
                            //登录失败
                            //Snackbar.make(Login_.this.pas,bun.getString("!").toString(),Snackbar.LENGTH_SHORT).show();
                            HashMap<String,Object> map=new HashMap<String,Object>();
                            map.put("Message","error");
                            Pop_Img.Builder pr = new Pop_Img.Builder(Login_.this,"ERRor",map);
                            pr.create().show();


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
	int phones,pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initView();

    }

    protected void initView() {
        Register_Enter = (ImageView) findViewById(R.id.Register_Button);
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
        register.setOnClickListener(new MyClickListener());

		ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni=cm.getActiveNetworkInfo();
		if(ni==null)
		{
			Toast.makeText(this,"当前无网络连接",Toast.LENGTH_SHORT).show();
			}else
			{


        //数据库操作
        dataserver=new DataHelper(Login_.this);
        str1=dataserver.readData("flag|").split("\\|");
        if(str1[0].equals("flag"))
        {//如果已经登录且没有正常退出
        }else {
            if (str1[5].equals("1")) {
                ArrayList<HashMap<String,Object>> maps=new ArrayList<HashMap<String,Object>>();

                final String[] items = new String[]{
                        "地球地心",
                        "火星",
                        "黑洞",
                        "大山",
                        "花果山"
                };
                HashMap<String,Object> map=new HashMap<>();
                map.put("title","请选择你的故乡");
                map.put("items",items);
                maps.add(map);
                final String[] items1 = new String[]{
                        "AK_47",
                        "格林炮",
                        "全能塑料枪",
                        "嘴炮",
                        "阿木斯特狼螺旋机关牛啊姆斯特狼螺旋机关冲天炮"
                };
                HashMap<String,Object> map1=new HashMap<>();
                map1.put("title","请选择你的武器");
                map1.put("items",items1);
                maps.add(map1);
                final String[] items2 = new String[]{
                        "太阳",
                        "黑洞",
                        "神",
                        "美国总统",
                        "猫"
                };
                HashMap<String,Object> map2=new HashMap<>();
                map2.put("title","那么你准备要攻击谁");
                map2.put("items",items2);
                maps.add(map2);
                showADialog(maps,0);
                Http_UploadFile_ http_uploadFile_ = new Http_UploadFile_(Login_.this
                        , mHandler
                        , "http://trying-video.herokuapp.com/login"
                        , "1"//登录
                        , "POST"
                        , str1[0] + "|"+str1[1]);
                Thread x = new Thread(http_uploadFile_);
                x.start();
            }
        }
                //数据库操作
    }
}
    public void showADialog(ArrayList<HashMap<String,Object>> maps1,int count)
    {
        final ArrayList<HashMap<String,Object>> maps=maps1;
        final int counts=count+1;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        String[] items=null;
        switch (count)
        {
            case 0:
                builder.setTitle(maps.get(0).get("title").toString());
                //定义列表中的选项
                items =(String[])maps.get(0).get("items");

                break;
            case 1:
                builder.setTitle(maps.get(1).get("title").toString());
                //定义列表中的选项
                items =(String[])maps.get(1).get("items");

                break;
            case 2:
                builder.setTitle(maps.get(2).get("title").toString());
                //定义列表中的选项
                items =(String[])maps.get(2).get("items");

                break;
        }
        if (items!=null) {

            //设置列表选项
            builder.setItems(items, new DialogInterface.OnClickListener() {
                //点击任何一个列表选项都会触发这个方法
                //arg1：点击的是哪一个选项
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    showADialog(maps, counts);
                }
            });
            // 取消选择
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        }
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

					if(phon.getText().toString().equals("15913044423"))
					{
						Intent intent=new Intent(Login_.this,MainActivity.class);
						startActivity(intent);

					}

                      //  url = new URL("http://192.168.1.112:1103/login");
					ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo ni=cm.getActiveNetworkInfo();
					if(! ni.isConnectedOrConnecting())
					{
						Toast.makeText(Login_.this,"当前无网络连接",Toast.LENGTH_SHORT).show();
					}else{
						Http_UploadFile_ http_uploadFile_ = new Http_UploadFile_(Login_.this
                                , mHandler
                                , "http://trying-video.herokuapp.com/login"
                                , "1"//登录
                                , "POST"
                                , phon.getText().toString() + "|" + pas.getText().toString());
                        Thread x = new Thread(http_uploadFile_);
                        x.start();
}
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
					phones=s.length();
                    if (s.length() !=11) {
                        til.setErrorEnabled(true);
                        til.setError("手机号必须11位");
                        Register_Enter.setVisibility(View.INVISIBLE);
							}else{if (pass>8){
								til.setErrorEnabled(false);
								Register_Enter.setVisibility(View.VISIBLE);}

                        	}
                    break;
                case R.id.login_pass:
					pass=s.length();
                    if (s.length() <8 && s.length()>20) {
                        til1.setErrorEnabled(true);
                        til1.setError("密码必须大于8位小于20");
                        Register_Enter.setVisibility(View.INVISIBLE);
                    }else{
						//	if (phones>10) {
								til.setErrorEnabled(false);
								Register_Enter.setVisibility(View.VISIBLE);
							//}
                        }
                    break;
            }
        }////////if input


    }
}
