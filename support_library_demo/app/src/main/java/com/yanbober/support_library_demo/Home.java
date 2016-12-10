package com.yanbober.support_library_demo;

import android.content.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v7.app.*;
import android.view.View.*;
import android.widget.*;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import android.support.v7.widget.Toolbar;

public class Home extends ActionBarActivity
{
	CollapsingToolbarLayout collapsingToolbar;
	
Button card1;
User user=new User();

public Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
				case 0:
					Bundle bun=new Bundle();
					bun=msg.getData();
					String str=bun.getString("lo");
					
					break;
				case 1:
					//Intent inten=new Intent(Task_List.this,publish_Class.class);
					//startActivity(inten);
					break;
				case 2:
					//				Task_List.this.finish();
					break;
				case 3:
					
			}
		};
	};


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_page);
		Pop_Img.Builder p=new Pop_Img.Builder(Home.this);
		p.create().show();
		card1=(Button)findViewById(R.id.home_pagecard1);
		final ImageView fabIconNew = new ImageView(this);
        fabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_new_light));

		

		Toolbar toolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_delete);
        actionBar.setDisplayHomeAsUpEnabled(true);

         collapsingToolbar =
			(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("this s Home Pager !!");
		int a=R.drawable.class.getDeclaredFields().length;
		Snackbar.make(card1, "sss"+a, Snackbar.LENGTH_SHORT)
			.show();
		
	}
	
		public void setlogin(String str)
		{
			String[] strs=str.split("\\~");
			//phone pas name flag picture chatdata old city sex 
			collapsingToolbar.setTitle(strs[3]);
			user.phone=strs[0];
			user.name=strs[2];
			user.flag=strs[3];
			
			
			
		}
				
		public void setuser(String[] strs)
		{
			user.phone=strs[0];
			
			//...
			
		}
		public void setdate()
		{
			collapsingToolbar.setTitle(user.name);
			
		}
}
