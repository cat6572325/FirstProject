package com.yanbober.support_library_demo;

import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.yanbober.support_library_demo.Http_Util.*;
import java.util.*;

import android.support.v7.widget.Toolbar;
import com.yanbober.support_library_demo.Message_S.*;
/*

直接修改，
验证由对话框进行
 */
public class Set_Pay_pwd_ extends ActionBarActivity
{
	EditText pass,pass1;
	public Handler mHandler=new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			// TODO: Implement this method
			super.handleMessage(msg);
			switch(msg.what)
			{
				case 0:
					Bundle bun=msg.getData();
					if(bun.getString("?").equals("成功"))
					{
						Intent intent = new Intent(Set_Pay_pwd_.this, Pay_Complete_.class);
						startActivity(intent);
						finish();
					}
					break;
			}
		}
		
	};
	
	
	
	Toolbar tb;
	Button btn;
	User user=new User();
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_new_pay_pwd_layout);
		initView();
	}
	public void initView()
	{
		btn=(Button)findViewById(R.id.setnewpaypwdlayoutButton1);
		pass=(EditText)findViewById(R.id.set_new_pay_pwd_layoutEditText);
		pass1=(EditText)findViewById(R.id.set_new_pay_pwd_layoutEditText1);
		tb=(Toolbar)findViewById(R.id.collectToolbar);
		//初始化ToolBar
        setSupportActionBar(tb);
		tb.setNavigationIcon(R.drawable.back_purple);
		tb.setNavigationOnClickListener(new OnClickListener()
			{//返回监听，按钮返回，不过不知道会回哪里
				public void onClick(View v)
				{
					onBackPressed();

				}

			});
			btn.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{//点击发送请求，修改支付密码
					HashMap<String ,Object> map=new HashMap<String ,Object>();
					map.put("context",Set_Pay_pwd_.this);
					map.put("handler",mHandler);

				map.put("paypassword", pass.getText().toString());
					String url = "http://trying-video.herokuapp.com/user/payword?token=" + user.token;
					  Http_UploadFile_ http_uploadFile_=new Http_UploadFile_(url
					        ,map,"10");

					//	Http_UploadFile_ http = new Http_UploadFile_(url, map, "11");
					Thread xx = new Thread(http_uploadFile_);
					xx.start();



				}
				
			});
	}
}
