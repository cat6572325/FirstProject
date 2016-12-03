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

public class Set_Pay_pwd_ extends ActionBarActivity
{
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
					if(bun.getString("?").equals("继续下一步"))
					{
						
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
				{
					HashMap<String ,Object> map=new HashMap<String ,Object>();
					map.put("context",Set_Pay_pwd_.this);
					map.put("handler",mHandler);
					map.put("paypassword",user.mydata.get("paypassword").toString());
					String url="http://trying-video.herokuapp.com/user/oldpayword?token="+user.token;
					Http_UploadFile_ http=new Http_UploadFile_(url,map,"11");
					Thread xx=new Thread(http);
					xx.start();
					
					
					
					Intent intent=new Intent(Set_Pay_pwd_.this,Pay_Complete_.class);
					startActivity(intent);
				}
				
			});
	}
}
