package com.yanbober.support_library_demo;

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
import android.widget.*;
import java.io.*;
import java.net.*;
import java.util.*;

import android.support.v7.widget.Toolbar;
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

	public ImageView heard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件及布局
        initView();
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
        mNavigationView = (NavigationView) this.findViewById(R.id.navigation_view);
        mTabLayout = (TabLayout) this.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) this.findViewById(R.id.view_pager);
		ThreadEx c1=new ThreadEx(MainActivity.this,"loginAndPass");
		Thread x1=new Thread(c1);
		//x1.start();
		
		
        //初始化ToolBar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_dialog_alert);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //对NavigationView添加item的监听事件
        mNavigationView.setNavigationItemSelectedListener(naviListener);
        //开启应用默认打开DrawerLayout
        mDrawerLayout.openDrawer(mNavigationView);
		View v=mNavigationView.getHeaderView(0);
		heard=(ImageView)v.findViewById(R.id.drawer_headerImageView);
		heard.setBackgroundResource(android.R.drawable.dark_header);
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
}
