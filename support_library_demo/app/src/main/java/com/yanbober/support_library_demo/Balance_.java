package com.yanbober.support_library_demo;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.*;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.view.View.*;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yanbober.support_library_demo.*;
import java.util.*;

public class Balance_ extends AppCompatActivity
{
	HashMap<String,Object> map=new HashMap<String,Object>();
	ArrayList<String> alist;
	ExpandableListView exlv=null;
	Toolbar toolbar;

	private DrawerLayout mDrawerLayout;
	//Tab菜单，主界面上面的tab切换菜单

	//右上角新消息提示红点
	TextView Message_point;



	//v4中的ViewPager控件
	private ViewPager mViewPager;
	ListView rl;
	MyChatAdapter ladapter;
	int[] layout={R.layout.left_list_item,R.layout.line_item};


	public ArrayList<HashMap<String,Object>> lists=new ArrayList<HashMap<String,Object>>();
	public ImageView heard,left_head,message;
	String[] groups;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.balance_layout);
		exlv=(ExpandableListView)findViewById(R.id.balance_layoutExpandableListView);
		groups=new String[2];
		alist=new ArrayList<String>();
		alist.add("卧槽");
		alist.add("欧买噶");
		alist.add("地块");
		map.put("group",alist);
		groups[0]="group";
		alist=null;
		alist=new ArrayList<String>();
		alist.add("fucking");
		alist.add("o My god");
		alist.add("here");
		alist.add("my Syster");
		map.put("group1",alist);
		groups[1]="group1";
		Adapter adapter=new Adapter(map,groups);
		exlv.setAdapter(adapter);
		exlv.setGroupIndicator(null);//隐藏默认箭头
		//int uu=exlv.getCount();
		//for(int i=0;i<uu;i++)
		//exlv.expandGroup(0);
		initView();
		
	}

	protected void initView()
	{
		toolbar=(Toolbar)findViewById(R.id.balanceToolbar);
		mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
		mViewPager = (ViewPager) this.findViewById(R.id.view_pager);
		rl=(ListView)this.findViewById(R.id.tRecyclerView1);
		left_head=(ImageView)this.findViewById(R.id.drawer_headerImageView);
		message=(ImageView)this.findViewById(R.id.activitymainTextView1);
		Message_point=(TextView)findViewById(R.id.tTextView);
		addTextToList("首页",0,R.drawable.home);
		addTextToList("已付",0,R.drawable.paid);

		addTextToList("我的",0,R.drawable.my_video);
		addTextToList("收藏",0,R.drawable.collect);
	//	addTextToList("余额",0,R.drawable.balance);
		addTextToList("分割贱",1,R.drawable.fab_bg_normal);

		addTextToList("设置",0,R.drawable.fab_bg_normal);
		addTextToList("反馈",0,R.drawable.feedback);


		ladapter=new MyChatAdapter(Balance_.this,lists,layout);
		rl.setAdapter(ladapter);
		left_head.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Intent intent=new Intent(Balance_.this,Personal_.class);
				startActivity(intent);
			}
		});
		rl.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent,View view,int position,long id)
			{
				TextView tv=(TextView)view.findViewById(R.id.leftlistitemTextView1);
				String str=tv.getText().toString();
				if(str.equals("首页"))
				{

				}
				if(str.equals("已付"))
				{
					Intent intent=new Intent(Balance_.this,Paid_Video.class);
					startActivity(intent);
				}

				if(str.equals("收藏"))
				{
					Intent intent=new Intent(Balance_.this,Collect_.class);
					startActivity(intent);
				}
				if(str.equals("我的"))
				{
					Intent intent=new Intent(Balance_.this,My_Video_.class);
					startActivity(intent);
				}

				if(str.equals("余额"))
				{
					Intent intent=new Intent(Balance_.this,Balance_.class);
					startActivity(intent);
				}

				if(str.equals("设置"))
				{
					Intent intent=new Intent(Balance_.this,Setting_.class);
					startActivity(intent);
				}

			}
		});

		//TODO 右上角按钮
		message.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View v)
			{
				Message_point.setVisibility(View.INVISIBLE);
				Intent intent=new Intent(Balance_.this,Message_c.class);
				startActivity(intent);

			}
		});



		//初始化ToolBar
		setSupportActionBar(toolbar);
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setHomeAsUpIndicator(R.drawable.menu);//android.R.drawable.ic_dialog_alert);
		actionBar.setDisplayHomeAsUpEnabled(true);



	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				//主界面左上角的icon点击反应
				mDrawerLayout.openDrawer(GravityCompat.START);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void addTextToList(ArrayList<String> list,String name)
	{
		
		
		map.put(name,list);

		
    }
	
	//自定义适配器
	class Adapter extends BaseExpandableListAdapter {
		HashMap<String,Object> map;
		String[] groups;
		
		public Adapter(HashMap<String,Object> map,String[] groups)
		
		{
			this.map=map;
			this.groups=groups;
			
			
		}
        //获取子元素对象
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return null;
		}
        //获取子元素Id
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}
        //加载子元素并显示
		@Override
		public View getChildView(final int groupPosition, final int childPosition,
								 boolean isLastChild, View convertView, ViewGroup parent) {
			View view=null;
			ChildHolder childholder = null;
			if(convertView!=null){
				view = convertView;
				childholder = (ChildHolder) view.getTag();
			}else{
				view = View.inflate(Balance_.this,R.layout.balance_child_item, null);
				//加载子布局
				childholder = new ChildHolder();
				//childholder.mImage = (ImageView) view.findViewById(R.id.balancechilditemTextView1);
			   	}
			/*childholder.mImage.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						}
				});*/
			return view;
		}
        //获取子元素数目
		@Override
		public int getChildrenCount(int groupPosition) {
			return ((ArrayList<String>)map.get(groups[groupPosition].toString())).size();
			//待修改
		}
		//获取组元素对象
		@Override
		public Object getGroup(int groupPosition) {
			return map.get(groupPosition);
			//待修改
			
			}
        //获取组元素数目
		@Override
		public int getGroupCount() {
			return groups.length;
		}
		//获取组元素Id
		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}
        //加载并显示组元素
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
								 View convertView, ViewGroup parent) {
			View view=null;
			GroupHolder groupholder = null;
			if(convertView!=null){
				view = convertView;
				groupholder = (GroupHolder) view.getTag();
			}else {
				view = View.inflate(Balance_.this, R.layout.balance_group_item, null);
				groupholder = new GroupHolder();
				//groupholder.Rightimage=(ImageView)view.findViewById(R.id.Balance_Group_Image);
				//groupholder.Rightimage.setBackgroundResource(R.drawable.);
				//加载父布局
				view.setTag(groupholder);

			}
			groupholder.Rightimage=(ImageView)view.findViewById(R.id.Balance_Group_Image);
			if (isExpanded) {
				//展开
				groupholder.Rightimage.setBackgroundResource(R.drawable.down);
			}else
			{
				groupholder.Rightimage.setBackgroundResource(R.drawable.ahead);
			}
			return view;
		}

		@Override
		public boolean hasStableIds() {

			return true;
		}


		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {

			return false;
		}

	}

	static class GroupHolder{
		TextView mSpaceText;
		ImageView Rightimage;
	}

	static class ChildHolder{
		ImageView mImage;
		TextView mStateText;
		TextView mPrice;
		TextView mSecondPrice;
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
	{//TODO 侧滑栏　列表适配类

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
