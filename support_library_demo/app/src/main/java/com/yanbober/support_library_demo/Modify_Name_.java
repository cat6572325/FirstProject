package com.yanbober.support_library_demo;

import android.os.*;
import android.support.v4.view.GravityCompat;
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
		//初始化ToolBar
		setSupportActionBar(mToolbar);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setHomeAsUpIndicator(R.drawable.back_purple);//android.R.drawable.ic_dialog_alert);
		actionBar.setDisplayHomeAsUpEnabled(true);


	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_info_details:
				////mViewPager.setCurrentItem(0);
				break;
			case R.id.menu_share:
				//mViewPager.setCurrentItem(1);
				break;
			case R.id.menu_agenda:
			//	mViewPager.setCurrentItem(2);
				break;
			case android.R.id.home:
				//主界面左上角的icon点击反应
			//	mDrawerLayout.openDrawer(GravityCompat.START);
				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}
