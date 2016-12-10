package com.yanbober.support_library_demo;

import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.util.*;
import org.json.*;

import android.support.v7.widget.Toolbar;

public class Paid_Video extends AppCompatActivity

{
RecyclerView rv=null;
Toolbar tb;
	public ArrayList<HashMap<String,Object>> lists=new ArrayList<HashMap<String,Object>>();
	public FirstAdapter adapter;
	//右上角新消息提示红点
	TextView Message_point;
	User user=new User();
	ImageView message,head;

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
		Message_point=(TextView)findViewById(R.id.tTextView);
		message=(ImageView)this.findViewById(R.id.activitymainTextView1);

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
		head = (ImageView) this.findViewById(R.id.drawer_headerImageView);
		User u = new User();
		if (u.headBitmap != null) {
			head.setImageBitmap(u.headBitmap);

		}
		head.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				User u = new User();
				if (u._id != "null") {
					Intent intent = new Intent(Paid_Video.this, Personal_.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(Paid_Video.this, Login_.class);
					startActivity(intent);
				}
			}
		});
		addTextToList("本 拉登教你打仗",2,R.drawable.qq,"901人付款");
		message.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View v)
			{
				Message_point.setVisibility(View.INVISIBLE);
				Intent intent=new Intent(Paid_Video.this,Message_c.class);
				startActivity(intent);


			}
		});

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
		
		try {
			JSONObject jsonObject=null;
			JSONArray jss= new JSONArray(user.mydata.get("notices").toString());

			if (jss.length() < 2) {
				Message_point.setVisibility(View.INVISIBLE);
			}
			else {


				Message_point.setText(String.valueOf(jss.length()));


			}
		}
		catch (JSONException e) {
			e.printStackTrace();
		}

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
