package com.yanbober.support_library_demo;

import android.content.Context;
import android.os.*;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOError;
import java.util.*;

public class Collect_ extends AppCompatActivity

{
	MyChatAdapter ladapter;
	ListView rl;
	private DrawerLayout mDrawerLayout;
	//Tab菜单，主界面上面的tab切换菜单
	int[] layout={R.layout.left_list_item,R.layout.line_item};
	ImageView menu_img;
	public ArrayList<HashMap<String,Object>> lists1=new ArrayList<HashMap<String,Object>>();


	RecyclerView rv=null;
	Toolbar tb;
	public ArrayList<HashMap<String,Object>> lists=new ArrayList<HashMap<String,Object>>();
	public FirstAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collect_layout);
		init();
	}
	public void init()
	{
		try {
		rv=(RecyclerView)findViewById(R.id.collectrv);
		rl=(ListView)this.findViewById(R.id.tRecyclerView1);
			mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
			menu_img=(ImageView)this.findViewById(R.id.Collect_menu);
			menu_img.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					//TODO 点击左上角打开侧边栏
					mDrawerLayout.openDrawer(GravityCompat.START);
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
	//	ladapter=new MyChatAdapter(Collect_.this,lists,layout);
	//	rl.setAdapter(ladapter);
		}catch(IOError error)
		{
		}
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
	public void addTextToList(String text, int who,int id)
	{
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("person", who);
		map.put("image", id);
		map.put("text", text);

		map.put("layout",who);

		lists1.add(map);
	}
	private class MyChatAdapter extends BaseAdapter
	{

		Context context=null;
		ArrayList<HashMap<String,Object>> chatList=null;
		int[] layout;
		String[] from;
		int[] to;



		public MyChatAdapter(Context context,
							 ArrayList<HashMap<String, Object>> chatList, int[] layout
		)
		{
			super();
			this.context = context;
			this.chatList = chatList;
			this.layout = layout;

		}


		public int getCount()
		{
			// TODO Auto-generated method stub
			return chatList.size();
		}


		public Object getItem(int arg0)
		{
			// TODO Auto-generated method stub
			return null;
		}


		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return position;
		}


		////////////
		class ViewHolder
		{
			public ImageView imageView=null;
			public TextView textView=null;
			public String title;
			private ImageView i_icon,i_icon2;
			public TextView T_title,T_red;
		}
		////////////

		public View getView(int position, View convertView, ViewGroup parent)
		{
			// TODO Auto-generated method stub
			ViewHolder holder=null;
			final TextView tt;
			LinearLayout re;
			int who=(Integer)chatList.get(position).get("person");


			switch(who)
			{
				case 0:
					convertView = LayoutInflater.from(context).inflate(
							layout[who], null);
					ImageView img=(ImageView)convertView.findViewById(R.id.left_list_itemImageView);
					TextView tv=(TextView)convertView.findViewById(R.id.leftlistitemTextView1);
					img.setImageResource((Integer)chatList.get(position).get("image"));
					tv.setText((String)chatList.get(position).get("text"));


					break;
				case 1:
					isEnabled(position);
					convertView = LayoutInflater.from(context).inflate(
							layout[who], null);
					View v=(View)convertView.findViewById(R.id.lineitemView1);
					v.setOnClickListener(new OnClickListener()
					{
						public void onClick(View view)
						{

						}
					});

					break;


			}
			return convertView;

		}
	}
}

