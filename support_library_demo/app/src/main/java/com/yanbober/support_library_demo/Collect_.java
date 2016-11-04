package com.yanbober.support_library_demo;

import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import java.util.*;

public class Collect_ extends AppCompatActivity

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
		setContentView(R.layout.collect_layout);
		init();
	}
	public void init()
	{
		rv=(RecyclerView)findViewById(R.id.collectrv);
		tb=(Toolbar)findViewById(R.id.collectToolbar);
		//初始化ToolBar
        setSupportActionBar(tb);
		tb.setNavigationIcon(R.drawable.menu);
		tb.setNavigationOnClickListener(new OnClickListener()
			{//返回监听，按钮返回，不过不知道会回哪里
				public void onClick(View v)
				{
					onBackPressed();

				}

			});
		rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
		//设置RecyclerView布局管理器为1列垂直排布
		addTextToList("本 拉登教你打仗",3,R.drawable.qq,"901人付款",0,0);
		addTextToList("King arthur",3,R.drawable.qq,"901人付款",1,0);
		addTextToList("高文",3,R.drawable.qq,"901人付款",0,1);
		addTextToList("lancelot",3,R.drawable.qq,"901人付款",1,1);
		
		
		adapter = new FirstAdapter(Collect_.this,lists);

        rv.setAdapter(adapter);

	}
	public void addTextToList(String text, int who, int id,String data,int isspot,int ispay)
	{
		HashMap<String,Object> map=new HashMap<String,Object>();

		map.put("image", id);
		map.put("text", text);
		map.put("isspot",isspot);
		map.put("ispay",ispay);
		
		map.put("data",data);
		map.put("layout",who);

		lists.add(map);
    }
}

