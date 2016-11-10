package com.yanbober.support_library_demo;

import android.animation.*;
import android.content.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.io.*;
import java.net.*;
import java.util.*;

import android.support.v7.widget.Toolbar;
/**
 * 一个中文版Demo App搞定所有Android的Support Library新增所有兼容控件
 * 支持最新2015 Google I/O大会Android Design Support Library
 */


public class My_Video_ extends ActionBarActivity {
	public Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
				case 0:
					Bundle bun=new Bundle();
					bun=msg.getData();
			
					break;


			}
		}
	};
	My_Video_tabs_item_already idf=new My_Video_tabs_item_already();
	My_Video_Not_Uploaded_ sf=new My_Video_Not_Uploaded_();
	AgendaFragment af=new AgendaFragment();
	private DataInputStream in;
	private DataOutputStream out;
	private Socket socketClien;
	User user=new User();
	    //将ToolBar与TabLayout结合放入AppBarLayout
    private Toolbar mToolbar;
    //DrawerLayout中的左侧菜单控件
    private NavigationView mNavigationView;
    //DrawerLayout控件
    private DrawerLayout mDrawerLayout;
    //Tab菜单，主界面上面的tab切换菜单
    private TabLayout mTabLayout;
    //v4中的ViewPager控件
    private ViewPager mViewPager;
	MyChatAdapter ladapter;
	ListView rl;
	int[] layout={R.layout.left_list_item,R.layout.line_item};


	public ArrayList<HashMap<String,Object>> lists=new ArrayList<HashMap<String,Object>>();

	LinearLayout ll;
	public ImageView heard;

	ImageView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_video);
        //初始化控件及布局
        initView();


		final ImageView fabIconNew = new ImageView(this);
        fabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_new_light));
        final Popup_Button rightLowerButton = new Popup_Button.Builder(this)
			.setContentView(fabIconNew)
			.build();

		SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);
        ImageView rlIcon1 = new ImageView(this);
        ImageView rlIcon2 = new ImageView(this);
        ImageView rlIcon3 = new ImageView(this);
        ImageView rlIcon4 = new ImageView(this);

        rlIcon1.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_chat_light));
        rlIcon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_camera_light));
        rlIcon3.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_video_light));
        rlIcon4.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_place_light));

        // Build the menu with default options: light theme, 90 degrees, 72dp radius.
        // Set 4 default SubActionButtons
        final FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder(this)
			.addSubActionView(rLSubBuilder.setContentView(rlIcon1).build(),new OnclickListener())
			.addSubActionView(rLSubBuilder.setContentView(rlIcon2).build(),new OnclickListener())
			.addSubActionView(rLSubBuilder.setContentView(rlIcon3).build(),new OnclickListener())
			.addSubActionView(rLSubBuilder.setContentView(rlIcon4).build(),new OnclickListener())
			.attachTo(rightLowerButton)
			.build();

        // Listen menu open and close events to animate the button content view
        rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
				@Override
				public void onMenuOpened(FloatingActionMenu menu) {
					// Rotate the icon of rightLowerButton 45 degrees clockwise
					fabIconNew.setRotation(0);
					PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
					ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
					animation.start();
				}

				@Override
				public void onMenuClosed(FloatingActionMenu menu) {
					// Rotate the icon of rightLowerButton 45 degrees counter-clockwise
					fabIconNew.setRotation(45);
					PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
					ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
					animation.start();
				}
			});
        // Set up the large red button on the center right side
        // With custom button and content sizes and margins
		





    }
	    private void initView() {
        //MainActivity的布局文件中的主要控件初始化
        mToolbar = (Toolbar) this.findViewById(R.id.myvideotoolbar);
    //    mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
		//   mNavigationView = (NavigationView) this.findViewById(R.id.navigation_view);
        mTabLayout = (TabLayout) this.findViewById(R.id.myvideotab);
        mViewPager = (ViewPager) this.findViewById(R.id.view_pager);
		rl=(ListView)this.findViewById(R.id.tRecyclerView1);
	
			message=(ImageView)this.findViewById(R.id.activitymainTextView1);

		

		  //初始化ToolBar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.menu);//android.R.drawable.ic_dialog_alert);
        actionBar.setDisplayHomeAsUpEnabled(true);
		message.setOnClickListener(new View.OnClickListener()
			{

				public void onClick(View v)
				{
					Intent intent=new Intent(My_Video_.this,Message_c.class);
					startActivity(intent);

				}
			});
        //对NavigationView添加item的监听事件
		// mNavigationView.setNavigationItemSelectedListener(naviListener);
        //开启应用默认打开DrawerLayout
		//  mDrawerLayout.openDrawer(ll);
		/*	View v=mNavigationView.getHeaderView(0);
		 heard=(ImageView)v.findViewById(R.id.drawer_headerImageView);
		 heard.setBackgroundResource(android.R.drawable.dark_header);*/
        //初始化TabLayout的title数据集
        List<String> titles = new ArrayList<>();
        titles.add("已上传");
        titles.add("未上传");
        
        //初始化TabLayout的title
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
       mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
           //初始化ViewPager的数据集

        List<Fragment> fragments = new ArrayList<>();

        fragments.add(idf);
       fragments.add(sf);
      
		//创建ViewPager的adapter
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        //千万别忘了，关联TabLayout与ViewPager
        //同时也要覆写PagerAdapter的getPageTitle方法，否则Tab没有title
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
		
			addTextToList("首页",0,R.drawable.home);
			addTextToList("已付",0,R.drawable.paid);

			addTextToList("我的",0,R.drawable.fab_bg_normal);
			addTextToList("收藏",0,R.drawable.collect);
			addTextToList("余额",0,R.drawable.balance);
			addTextToList("分割贱",1,R.drawable.fab_bg_normal);

			addTextToList("设置",0,R.drawable.fab_bg_normal);
			addTextToList("反馈",0,R.drawable.feedback);


			ladapter=new MyChatAdapter(My_Video_.this,lists,layout);
			rl.setAdapter(ladapter);
			
		
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
	
	class OnclickListener implements OnClickListener
	{
		public void onClick(View v)
		{
			Toast.makeText(My_Video_.this,"iii",5).show();
		}
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


