package com.yanbober.support_library_demo;

import android.content.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import android.support.v7.widget.Toolbar;

public class Home extends ActionBarActivity
{
	CollapsingToolbarLayout collapsingToolbar;
	
Button card1;
User user=new User();
FloatingActionButton fab;
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
		fab=(FloatingActionButton)findViewById(R.id.home_pageFloatingActionButt);
		final ImageView fabIconNew = new ImageView(this);
        fabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_new_light));
        final Popup_Button rightLowerButton = new Popup_Button.Builder(this)
			.setContentView(fabIconNew)
			.build();
		
		
		
		fab.setOnClickListener(new OnClickListener() {
				/////////login
				public void onClick(View v) {
					Pop_Img.Builder p=new Pop_Img.Builder(Home.this);
					p.setPositiveButton("[潮汕揭]初版\n问题反馈:(qq) 1213965634\n\n", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which)
							{
								dialog.dismiss();
								//login
								
							}
							//设置你的操作事项
							

						});
				
					p.create().show();
					
					}});//fab.click
		
					
					fab.setImageResource(android.R.drawable.ic_popup_reminder);
		card1.setOnClickListener(new OnClickListener() {
				/////////login
				public void onClick(View v) {
					Intent intent =new Intent(Home.this,MainActivity.class);
					Bundle bundle=new Bundle();
					bundle.putString("title","i");
					intent.putExtras(bundle);
					startActivityForResult(intent,9);
					//Uri u=Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+"://"+r.getResourcePackageName(R.drawable.intent)+"/"+r.getResourceTypeName(R.drawable.ic_launcher)+"/"+r.getResourceEntryName(R.drawable.ic_launcher));
					
					}});
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
