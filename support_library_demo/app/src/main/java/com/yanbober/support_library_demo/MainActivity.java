package com.yanbober.support_library_demo;

import android.animation.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.AdapterView.*;
import java.io.*;
import java.net.*;
import java.util.*;

import android.support.v7.widget.Toolbar;
import android.view.View.OnClickListener;
/**
 * 一个中文版Demo App搞定所有Android的Support Library新增所有兼容控件
 * 支持最新2015 Google I/O大会Android Design Support Library
 */
 
 
public class MainActivity extends ActionBarActivity {
	public Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
				case 0:
					Bundle bun=new Bundle();
					bun=msg.getData();
					String[] group=bun.getStringArray("group");
					String[] phones=bun.getStringArray("phones");
					setgroup(group,phones);
					
					break;


			}
		}
	};
	InfoDetailsFragment idf=new InfoDetailsFragment();
	ShareFragment sf=new ShareFragment();
	AgendaFragment af=new AgendaFragment();
	private DataInputStream in;
	private DataOutputStream out;
	private Socket socketClien;
	User user=new User();
	private OnButtonClickedListener buttonClickedListener;
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
	ListView rl;
	MyChatAdapter ladapter;
	int[] layout={R.layout.left_list_item,R.layout.line_item};
	
	
	public ArrayList<HashMap<String,Object>> lists=new ArrayList<HashMap<String,Object>>();
	
LinearLayout ll;
	public ImageView heard,left_head;
	
	ImageView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
			.addSubActionView(rLSubBuilder.setContentView(rlIcon1).build())
			.addSubActionView(rLSubBuilder.setContentView(rlIcon2).build())
			.addSubActionView(rLSubBuilder.setContentView(rlIcon3).build())
			.addSubActionView(rLSubBuilder.setContentView(rlIcon4).build())
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
    /*    int redActionButtonSize = getResources().getDimensionPixelSize(R.dimen.red_action_button_size);
        int redActionButtonMargin = getResources().getDimensionPixelOffset(R.dimen.action_button_margin);
        int redActionButtonContentSize = getResources().getDimensionPixelSize(R.dimen.red_action_button_content_size);
        int redActionButtonContentMargin = getResources().getDimensionPixelSize(R.dimen.red_action_button_content_margin);
        int redActionMenuRadius = getResources().getDimensionPixelSize(R.dimen.red_action_menu_radius);
        int blueSubActionButtonSize = getResources().getDimensionPixelSize(R.dimen.blue_sub_action_button_size);
        int blueSubActionButtonContentMargin = getResources().getDimensionPixelSize(R.dimen.blue_sub_action_button_content_margin);

        ImageView fabIconStar = new ImageView(this);
        fabIconStar.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_important));

        Popup_Button.LayoutParams starParams = new Popup_Button.LayoutParams(redActionButtonSize, redActionButtonSize);
        starParams.setMargins(redActionButtonMargin,
                              redActionButtonMargin,
                              redActionButtonMargin,
                              redActionButtonMargin);
        fabIconStar.setLayoutParams(starParams);

        Popup_Button.LayoutParams fabIconStarParams = new Popup_Button.LayoutParams(redActionButtonContentSize, redActionButtonContentSize);
        fabIconStarParams.setMargins(redActionButtonContentMargin,
									 redActionButtonContentMargin,
									 redActionButtonContentMargin,
									 redActionButtonContentMargin);

        final Popup_Button leftCenterButton = new Popup_Button.Builder(this)
			.setContentView(fabIconStar, fabIconStarParams)
			.setBackgroundDrawable(R.drawable.button_action_red_selector)
			.setPosition(Popup_Button.POSITION_LEFT_CENTER)
			.setLayoutParams(starParams)
			.build();

        // Set up customized SubActionButtons for the right center menu
        SubActionButton.Builder lCSubBuilder = new SubActionButton.Builder(this);
        lCSubBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_action_blue_selector));

        FrameLayout.LayoutParams blueContentParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        blueContentParams.setMargins(blueSubActionButtonContentMargin,
									 blueSubActionButtonContentMargin,
									 blueSubActionButtonContentMargin,
									 blueSubActionButtonContentMargin);
        lCSubBuilder.setLayoutParams(blueContentParams);
        // Set custom layout params
        FrameLayout.LayoutParams blueParams = new FrameLayout.LayoutParams(blueSubActionButtonSize, blueSubActionButtonSize);
        lCSubBuilder.setLayoutParams(blueParams);

        ImageView lcIcon1 = new ImageView(this);
        ImageView lcIcon2 = new ImageView(this);
        ImageView lcIcon3 = new ImageView(this);
        ImageView lcIcon4 = new ImageView(this);
        ImageView lcIcon5 = new ImageView(this);

        lcIcon1.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_camera));
        lcIcon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_picture));
        lcIcon3.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_video));
        lcIcon4.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_location_found));
        lcIcon5.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_headphones));

        // Build another menu with custom options
   /*     final FloatingActionMenu leftCenterMenu = new FloatingActionMenu.Builder(this)
			.addSubActionView(lCSubBuilder.setContentView(lcIcon1, blueContentParams).build())
			.addSubActionView(lCSubBuilder.setContentView(lcIcon2, blueContentParams).build())
			.addSubActionView(lCSubBuilder.setContentView(lcIcon3, blueContentParams).build())
			.addSubActionView(lCSubBuilder.setContentView(lcIcon4, blueContentParams).build())
			.addSubActionView(lCSubBuilder.setContentView(lcIcon5, blueContentParams).build())
			.setRadius(redActionMenuRadius)
			.setStartAngle(70)
			.setEndAngle(-70)
			.attachTo(leftCenterButton)
			.build();
*/
		
		
		
		
		
    }
public void setdate()
{
	//设置头像,将序列化的图片反序列化
	byte[] bitmapArray;
	bitmapArray = Base64.decode(user.name, Base64.DEFAULT);
	Bitmap bitmap=BitmapFactory.decodeByteArray(bitmapArray, 0,bitmapArray.length);
	if(bitmap!=null)
	heard.setImageBitmap(bitmap);
	
}
public void setgroup(String[] group,String[] phones)
{
	//将线程返回的值发送到fragment去
	idf.setgroup(MainActivity.this,group,phones);
	
}
    private void initView() {
        //MainActivity的布局文件中的主要控件初始化
        mToolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
     //   mNavigationView = (NavigationView) this.findViewById(R.id.navigation_view);
        mTabLayout = (TabLayout) this.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) this.findViewById(R.id.view_pager);
		rl=(ListView)this.findViewById(R.id.tRecyclerView1);
		ll=(LinearLayout)this.findViewById(R.id.activitymainLinearLayout1);
		message=(ImageView)this.findViewById(R.id.activitymainTextView1);
		left_head=(ImageView)this.findViewById(R.id.drawer_headerImageView);
		ThreadEx c1=new ThreadEx(MainActivity.this,"loginAndPass");
		Thread x1=new Thread(c1);
		//x1.start();
		//rl.setItemAnimator(new DefaultItemAnimator());
		 //mRecyclerView.addItemDecoration(new div
       
       // LinearLayoutManager manager = new LinearLayoutManager(mRecyclerView.getContext());
       // manager.setOrientation(2,LinearLayoutManager.HORIZONTAL);
      //  rl.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
		//设置RecyclerView布局管理器为2列垂直排布
	
		
		addTextToList("首页",0,R.drawable.home);
		addTextToList("已付",0,R.drawable.paid);
		
		addTextToList("我的",0,R.drawable.fab_bg_normal);
		addTextToList("收藏",0,R.drawable.collect);
		addTextToList("余额",0,R.drawable.balance);
		addTextToList("分割贱",1,R.drawable.fab_bg_normal);
		
		addTextToList("设置",0,R.drawable.fab_bg_normal);
		addTextToList("反馈",0,R.drawable.feedback);
		
		
		ladapter=new MyChatAdapter(MainActivity.this,lists,layout);
		rl.setAdapter(ladapter);
		
		left_head.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Intent intent=new Intent(MainActivity.this,Personal_.class);
				startActivity(intent);
			}
		});
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
			Intent intent=new Intent(MainActivity.this,Paid_Video.class);
			startActivity(intent);
		}
		
		if(str.equals("收藏"))
		{
			Intent intent=new Intent(MainActivity.this,Collect_.class);
			startActivity(intent);
		}
		if(str.equals("我的"))
		{
			Intent intent=new Intent(MainActivity.this,My_Video_.class);
			startActivity(intent);
		}
		
		if(str.equals("余额"))
		{
			Intent intent=new Intent(MainActivity.this,Balance_.class);
			startActivity(intent);
		}
		
		if(str.equals("设置"))
		{
			Intent intent=new Intent(MainActivity.this,Setting_.class);
			startActivity(intent);
		}
		
		}
		});
        //初始化ToolBar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.menu);//android.R.drawable.ic_dialog_alert);
        actionBar.setDisplayHomeAsUpEnabled(true);
message.setOnClickListener(new View.OnClickListener()
{
	
	public void onClick(View v)
	{
		Intent intent=new Intent(MainActivity.this,Message_c.class);
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
        titles.add("联系人与组");
        titles.add("留言板");
        titles.add("白块");
        //初始化TabLayout的title
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
        //初始化ViewPager的数据集
		
        List<Fragment> fragments = new ArrayList<>();
		
        fragments.add(idf);
        fragments.add(sf);
        fragments.add(af);
		 //创建ViewPager的adapter
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        //千万别忘了，关联TabLayout与ViewPager
        //同时也要覆写PagerAdapter的getPageTitle方法，否则Tab没有title
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
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

    private NavigationView.OnNavigationItemSelectedListener naviListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
     
		public boolean onNavigationItemSelected(MenuItem menuItem) {
            //点击NavigationView中定义的menu item时触发反应
            switch (menuItem.getItemId()) {
                case R.id.menu_info_details:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.menu_share:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.menu_agenda:
                    mViewPager.setCurrentItem(2);
                    break;
            }
            //关闭DrawerLayout回到主界面选中的tab的fragment页
            mDrawerLayout.closeDrawer(mNavigationView);
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //主界面右上角的menu菜单
      //  getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_info_details:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.menu_share:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.menu_agenda:
                mViewPager.setCurrentItem(2);
                break;
            case android.R.id.home:
                //主界面左上角的icon点击反应
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
	
	///////////////Thread////////
	
//	public class threadEx implements Runnable
//	{/////The get file from server of thread
//		File name;
//		int leng,su,t=0;
//		byte[] sendbyte;
//		FileInputStream fis;
//		FileOutputStream fos;
//		String total;
//
//		public threadEx()
//		{
//
//			this.name = name;
//		}
//
//		public void run()
//		{
//
//
//
//			try{
//				try
//				{
//					///create a new network connection
//					socketClien = new Socket();
//
//
//					SocketAddress sa=new InetSocketAddress("192.168.8.147", 7050);
//					socketClien.connect(sa, 5000);
//
//					in = new DataInputStream(socketClien.getInputStream());
//					out = new DataOutputStream(socketClien.getOutputStream());
//					//File[] fl=name.listFiles();
//					out.writeUTF("u");
//					
//				}
//				finally{
//
//				}
//			}catch (IOException e)
//			{/////if network fail
//				Looper.prepare();
//				Toast.makeText(MainActivity.this, "fail code:\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
//				Looper.loop();
//			}
//
//
//		}
//	}//socket file get thread to server
//	
	
	
	
	///////////////Thread////////
	/**
	 * 定义一个接口
	 * @author zqy
	 *
	 */
	public  interface OnButtonClickedListener{
		/**
		 * 里面传个值
		 * @param s
		 */
		public void onclicked(String s);
	}
	/**
	 * 
	 * @param buttonClickedListener
	 * 写一个对外公开的方法
	 */
	public void setButtonClickedListener(OnButtonClickedListener buttonClickedListener){
		this.buttonClickedListener=buttonClickedListener;
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
