package com.yanbober.support_library_demo;

import android.content.*;
import android.os.*;
import android.support.v4.view.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.webkit.*;
import android.widget.*;
import android.widget.AdapterView.*;
import java.util.*;

import android.support.v7.widget.Toolbar;
import android.view.View.OnClickListener;

public class Message_c extends AppCompatActivity
{
	MyChatAdapter1 ladapter1;
	int[] layout1={R.layout.message_item,R.layout.line_item};
ListView lv=null;
Toolbar tb=null;
	public ArrayList<HashMap<String,Object>> lists1=new ArrayList<HashMap<String,Object>>();
	ListView rl;
	MyChatAdapter ladapter;
	int[] layout={R.layout.left_list_item,R.layout.line_item};
DrawerLayout mDrawerLayout;

	public ArrayList<HashMap<String,Object>> lists=new ArrayList<HashMap<String,Object>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_layout);
		
		//setSupportActionBar(tb);
		
		//tb.setNavigationIcon(R.drawable.menu);
		//ActionBarDrawerToggle mDrawerToggle;
		//初始化ToolBar
        //mDrawerToggle = new ActionBarDrawerToggle(Message_c.this,mDrawerLayout,tb,"open","close");
//声明mDrawerToggle对象,其中R.string.open和R.string.close简单可以用"open"和"close"替代
		//mDrawerToggle.syncState();//实现箭头和三条杠图案切换和抽屉拉合的同步

		//mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		initView();
	}
	
	public void initView()
	{
		
		lv=(ListView)this.findViewById(R.id.message_listview);
		tb=(Toolbar)this.findViewById(R.id.tool_bar);
		mDrawerLayout=(DrawerLayout)this.findViewById(R.id.drawer_layout);
		addTextToList1("King arthur payment $3 to your of video","september13",0,R.drawable.image);
WebView wv=new WebView(Message_c.this);
WebSettings wset=wv.getSettings();
wset.setJavaScriptEnabled(true);

wv.loadUrl("http://192.168.1.112:1103/with/Try-videos-master/api/Login/login.js");

		ladapter1=new MyChatAdapter1(Message_c.this,lists1,layout1);
		lv.setAdapter(ladapter1);
		
		rl=(ListView)this.findViewById(R.id.tRecyclerView1);
		
		addTextToList("首页",0,R.drawable.home);
		addTextToList("已付",0,R.drawable.paid);

		addTextToList("我的",0,R.drawable.fab_bg_normal);
		addTextToList("收藏",0,R.drawable.collect);
		addTextToList("余额",0,R.drawable.balance);
		addTextToList("分割贱",1,R.drawable.fab_bg_normal);

		addTextToList("设置",0,R.drawable.fab_bg_normal);
		addTextToList("反馈",0,R.drawable.feedback);


		
//初始化ToolBar
        setSupportActionBar(tb);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.menu);//android.R.drawable.ic_dialog_alert);
        actionBar.setDisplayHomeAsUpEnabled(true);
		ladapter=new MyChatAdapter(Message_c.this,lists,layout);
		rl.setAdapter(ladapter);
		rl.setOnItemClickListener(new OnItemClickListener(){
				
	public void onItemClick(AdapterView<?> parent,View view,int position,long id)
	{
		TextView tv=(TextView)view.findViewById(R.id.leftlistitemTextView1);
		String str=tv.getText().toString();
		if(str.equals("首页"))
		{

		}
		if(str.equals("已付"))
		{
			Intent intent=new Intent(Message_c.this,Paid_Video.class);
			startActivity(intent);
		}

		if(str.equals("收藏"))
		{
			Intent intent=new Intent(Message_c.this,Collect_.class);
			startActivity(intent);
		}
		if(str.equals("我的"))
		{
			Intent intent=new Intent(Message_c.this,My_Video_.class);
			startActivity(intent);
		}

		if(str.equals("余额"))
		{
			Intent intent=new Intent(Message_c.this,Balance_.class);
			startActivity(intent);
		}

		if(str.equals("设置"))
		{
			Intent intent=new Intent(Message_c.this,Setting_.class);
			startActivity(intent);
		}

	}
	});
		
	}
	public void addTextToList1(String text, String time,int who,int id)
	{
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("person", who);
		map.put("image", id);
		map.put("text", text);
		map.put("time",time);
		map.put("layout",who);

		lists1.add(map);
    }
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_info_details:
             //   mViewPager.setCurrentItem(0);
                break;
            case R.id.menu_share:
           //     mViewPager.setCurrentItem(1);
                break;
            case R.id.menu_agenda:
           //     mViewPager.setCurrentItem(2);
                break;
            case android.R.id.home:
                //主界面左上角的icon点击反应
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
	private class MyChatAdapter1 extends BaseAdapter
	{

        Context context=null;
        ArrayList<HashMap<String,Object>> chatList=null;
        int[] layout;
        String[] from;
        int[] to;



        public MyChatAdapter1(Context context,
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
					ImageView img=(ImageView)convertView.findViewById(R.id.messageitemImageView1);
					TextView tv=(TextView)convertView.findViewById(R.id.messageitemTextView2);
					img.setImageResource((Integer)chatList.get(position).get("image"));
					tv.setText((String)chatList.get(position).get("text"));


					break;
				case 1:
					isEnabled(position);
					convertView = LayoutInflater.from(context).inflate(
						layout[who], null);
					View v=(View)convertView.findViewById(R.id.lineitemView1);
				
					break;


			}
			return convertView;

		
	}
	}
	
	
	
	public void addTextToList(String text, int who,int id)
	{
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("person", who);
		map.put("image", id);
		map.put("text", text);

		map.put("layout",who);

		lists.add(map);
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

