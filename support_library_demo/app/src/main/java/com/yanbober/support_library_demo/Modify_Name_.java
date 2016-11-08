package com.yanbober.support_library_demo;

import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.yanbober.support_library_demo.*;

import android.support.v7.widget.Toolbar;

public class Modify_Name_ extends AppCompatActivity
{
    //将ToolBar与TabLayout结合放入AppBarLayout
    private Toolbar mToolbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.modify_name);
	
		
	}
	public void inintView()
	{
		mToolbar = (Toolbar) this.findViewById(R.id.myvideotoolbar);
		//初始化ToolBar
        setSupportActionBar(mToolbar);
		
		
	}
	
}
