package com.yanbober.support_library_demo;

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
import org.json.*;

import android.support.v7.widget.Toolbar;

import com.yanbober.support_library_demo.Http_Util.Http_UploadFile_;

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
					finish();
					break;
				case 3:
					User_name.setText(user.name);
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
	public ImageView head;
	//右上角新消息提示红点
	TextView Message_point,User_name;

	ImageView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_video);
        //初始化控件及布局
		try {
			initView();
		} catch (JSONException e) {
			e.printStackTrace();
		}






    }
	    private void initView() throws JSONException {
			user.my_video_ = null;
			user.my_video_ = My_Video_.this;
			mHandler.sendEmptyMessage(3);
			//MainActivity的布局文件中的主要控件初始化
			mToolbar = (Toolbar) this.findViewById(R.id.myvideotoolbar);
			mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
			//   mNavigationView = (NavigationView) this.findViewById(R.id.navigation_view);
			mTabLayout = (TabLayout) this.findViewById(R.id.myvideotab);
			mViewPager = (ViewPager) this.findViewById(R.id.view_pager);
			rl = (ListView) this.findViewById(R.id.tRecyclerView1);
			Message_point = (TextView) findViewById(R.id.tTextView);
			message = (ImageView) this.findViewById(R.id.activitymainTextView1);
			User_name = (TextView) this.findViewById(R.id.User_name);
			head = (ImageView) this.findViewById(R.id.drawer_headerImageView);

			User u = new User();
			if (u.headBitmap != null) {
				head.setImageBitmap(u.headBitmap);

			}
			if (u.mydata.get("nickname")!="")
			{
				User_name.setText(u.mydata.get("nickname").toString());
			}
			head.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					User u = new User();
					if (u._id != "null") {
						Intent intent = new Intent(My_Video_.this, Personal_.class);
						startActivity(intent);
					} else {
						Intent intent = new Intent(My_Video_.this, Login_.class);
						startActivity(intent);
					}
				}
			});


			//初始化ToolBar
			setSupportActionBar(mToolbar);
			ActionBar actionBar = getSupportActionBar();
			actionBar.setHomeAsUpIndicator(R.drawable.menu);//android.R.drawable.ic_dialog_alert);
			actionBar.setDisplayHomeAsUpEnabled(true);
			message.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					Message_point.setVisibility(View.INVISIBLE);
					Intent intent = new Intent(My_Video_.this, Message_c.class);
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
			addTextToList("首页", 0, R.drawable.home);
			addTextToList("已付", 0, R.drawable.paid);

			//addTextToList("我的",0,R.drawable.fab_bg_normal);
			addTextToList("收藏", 0, R.drawable.collect);
			addTextToList("余额", 0, R.drawable.balance);
			addTextToList("分割贱", 1, R.drawable.fab_bg_normal);

			addTextToList("设置", 0, R.drawable.fab_bg_normal);
			addTextToList("反馈", 0, R.drawable.feedback);


			ladapter = new MyChatAdapter(My_Video_.this, lists, layout);
			rl.setAdapter(ladapter);
			rl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					TextView tv = (TextView) view.findViewById(R.id.leftlistitemTextView1);

					if (tv != null) {

						String str = tv.getText().toString();

						if (str.equals("首页")) {
							Intent intent = new Intent(My_Video_.this, MainActivity.class);
							startActivity(intent);

						}
						if (str.equals("已付")) {
							Intent intent = new Intent(My_Video_.this, Paid_Video.class);
							startActivity(intent);
						}

						if (str.equals("收藏")) {
							Intent intent = new Intent(My_Video_.this, Collect_.class);
							startActivity(intent);
						}
						if (str.equals("我的")) {
							Intent intent = new Intent(My_Video_.this, My_Video_.class);
							startActivity(intent);
						}

						if (str.equals("余额")) {
							Intent intent = new Intent(My_Video_.this, Balance_.class);
							startActivity(intent);
						}

						if (str.equals("设置")) {
							Intent intent = new Intent(My_Video_.this, Setting_.class);
							startActivity(intent);
						}
					}

				}
			});
			try {
				JSONObject jsonObject = null;
				JSONArray jss = new JSONArray(user.mydata.get("notices").toString());

				if (jss.length() < 2) {
					Message_point.setVisibility(View.INVISIBLE);
				} else {


					Message_point.setText(String.valueOf(jss.length()));


				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	private void getvideo()
	{

		HashMap<String ,Object> map=new HashMap<String ,Object>();
		map.put("Context",My_Video_.this);

		map.put("handler",mHandler);
		//map.put("paypassword",u.mydata.get("paypassword").toString());
		String url="http://trying-video.herokuapp.com/user/oldpayword?token="+user.token;
		Http_UploadFile_ http=new Http_UploadFile_(url,map,"16");
		Thread xx=new Thread(http);
		//xx.start();
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
	
	class OnclickListener implements OnClickListener
	{
		public void onClick(View v)
		{
			Toast.makeText(My_Video_.this,"iii",Toast.LENGTH_LONG).show();
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


