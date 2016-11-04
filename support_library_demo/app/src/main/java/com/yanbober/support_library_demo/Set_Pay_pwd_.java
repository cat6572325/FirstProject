package com.yanbober.support_library_demo;

import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

import android.support.v7.widget.Toolbar;

public class Set_Pay_pwd_ extends ActionBarActivity
{
	Toolbar tb;
	Button btn;
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
					Intent intent=new Intent(Set_Pay_pwd_.this,Pay_Complete_.class);
					startActivity(intent);
				}
				
			});
	}
}
