package com.yanbober.support_library_demo;

import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.util.*;

import android.support.v7.widget.Toolbar;

public class Paid_Video extends AppCompatActivity

{
RecyclerView rv=null;
Toolbar tb;
	public ArrayList<HashMap<String,Object>> lists=new ArrayList<HashMap<String,Object>>();
	public FirstAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paid_video);
		init();
	}
	public void init()
	{
		rv=(RecyclerView)findViewById(R.id.paidvideorv);
		tb=(Toolbar)findViewById(R.id.paidToolbar);
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
		rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
		//设置RecyclerView布局管理器为1列垂直排布
		addTextToList("本 拉登教你打仗",2,R.drawable.qq,"901人付款");
		adapter = new FirstAdapter(Paid_Video.this,lists);

        rv.setAdapter(adapter);
		adapter.setOnClickListener(new FirstAdapter.OnItemClickListener()
		{
				public void onItemClickListener(View v,int position)
				{
					Intent intent=new Intent(Paid_Video.this,Run_Video_.class);
					startActivity(intent);
					
					ImageView img=(ImageView)v.findViewById(R.id.paiditemImageView1);
					img.setOnClickListener(new OnClickListener()
					{
						public void onClick(View v)
						{
								}
					});
				}
				public void onItemLongClickListener(View v,int position)
				{
					
					
				}
			});
	}
	public void addTextToList(String text, int who, int id,String data)
	{
		HashMap<String,Object> map=new HashMap<String,Object>();
		
		map.put("image", id);
		map.put("text", text);
		
		map.put("data",data);
		map.put("layout",who);
	
		lists.add(map);
    }
}
