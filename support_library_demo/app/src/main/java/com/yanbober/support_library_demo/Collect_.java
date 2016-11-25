package com.yanbober.support_library_demo;

import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOError;
import java.util.*;

public class Collect_ extends AppCompatActivity

{
	public Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 3:
					//更新ui
					User_name.setText(user.name);
					break;

			}
		}


	};

		MyChatAdapter ladapter;
	ListView rl;
	//Tab菜单，主界面上面的tab切换菜单
	int[] layout={R.layout.left_list_item,R.layout.line_item};
	ImageView menu_img;
	public ArrayList<HashMap<String,Object>> lists1=new ArrayList<HashMap<String,Object>>();
	ImageView message;
	TextView Message_point,User_name;
	RecyclerView rv=null;
	Toolbar tb;
	private DrawerLayout mDrawerLayout;
		User user=new User();

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

	user.collect_=null;
		user.collect_=Collect_.this;
		try {
		rv=(RecyclerView)findViewById(R.id.collectrv);
		rl=(ListView)this.findViewById(R.id.tRecyclerView1);
			Message_point=(TextView)findViewById(R.id.tTextView);
			message=(ImageView)this.findViewById(R.id.activitymainTextView1);
			mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
			User_name=(TextView)this.findViewById(R.id.User_name);
			menu_img=(ImageView)this.findViewById(R.id.Collect_menu);
			menu_img.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					//TODO 点击左上角打开侧边栏
					mDrawerLayout.openDrawer(GravityCompat.START);
				}
			});

			message.setOnClickListener(new View.OnClickListener()
			{

				public void onClick(View v)
				{
					Message_point.setVisibility(View.INVISIBLE);
					Intent intent=new Intent(Collect_.this,Message_c.class);
					startActivity(intent);

				}
			});

			rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
		//设置RecyclerView布局管理器为1列垂直排布
		//addTextToList("本 拉登教你打仗",3,R.drawable.qq,"901人付款",0,0);
		//addTextToList("King arthur",3,R.drawable.qq,"901人付款",1,0);
		//addTextToList("高文",3,R.drawable.qq,"901人付款",0,1);
		//addTextToList("lancelot",3,R.drawable.qq,"901人付款",1,1);


			//left_list
			addTextToList("首页", 0, R.drawable.home);
			addTextToList("已付", 0, R.drawable.paid);

			addTextToList("我的", 0, R.drawable.my_video);
			//addTextToList("收藏", 0, R.drawable.collect);
			addTextToList("余额", 0, R.drawable.balance);
			addTextToList("分割贱", 1, R.drawable.fab_bg_normal);

			addTextToList("设置", 0, R.drawable.fab_bg_normal);
			addTextToList("反馈", 0, R.drawable.feedback);

			adapter = new FirstAdapter(Collect_.this,lists);
        rv.setAdapter(adapter);
		ladapter=new MyChatAdapter(Collect_.this,lists1,layout);
		rl.setAdapter(ladapter);
			ladapter.isEnabled(3);
			rl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					TextView tv = (TextView) view.findViewById(R.id.leftlistitemTextView1);
					String str = tv.getText().toString();
					if (str.equals("首页")) {

					}
					if (str.equals("已付")) {
						Intent intent = new Intent(Collect_.this, Paid_Video.class);
						startActivity(intent);
					}

					if (str.equals("收藏")) {
						Intent intent = new Intent(Collect_.this, Collect_.class);
						startActivity(intent);
					}
					if (str.equals("我的")) {
						Intent intent = new Intent(Collect_.this, My_Video_.class);
						startActivity(intent);
					}

					if (str.equals("余额")) {
						Intent intent = new Intent(Collect_.this, Balance_.class);
						startActivity(intent);
					}

					if (str.equals("设置")) {
						Intent intent = new Intent(Collect_.this, Setting_.class);
						startActivity(intent);
					}

				}
			});
		}catch(IOError error)
		{
		}
		JSONObject jsonObject= null;
		try {
			if(jsonObject.getString("notices").equals("0"))
			{
				Message_point.setVisibility(View.INVISIBLE);
			}else {
				jsonObject = new JSONObject(user.mydata.get("notices").toString());
				if (jsonObject.getJSONArray("notices").length() > 1) {
					Message_point.setText("1");
				} else {
					Message_point.setVisibility(View.INVISIBLE);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
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

