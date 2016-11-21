package com.yanbober.support_library_demo;

import android.animation.*;
import android.content.*;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.*;
import android.os.*;
import android.provider.Telephony;
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
import android.support.design.widget.FloatingActionButton;

import java.io.*;
import java.net.*;
import java.util.*;

import android.support.v7.widget.Toolbar;
import android.view.View.OnClickListener;

import com.yanbober.support_library_demo.DataHelpers.DataHelper;

import org.w3c.dom.Text;

/**
 * 一个中文版Demo App搞定所有Android的Support Library新增所有兼容控件
 * 支持最新2015 Google I/O大会Android Design Support Library
 */


public class MainActivity extends AppCompatActivity implements InfoDetailsFragment.OnActivityebent{
    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                  //获取视频列表

                    ArrayList<HashMap<String,Object>> videoList=new ArrayList<HashMap<String,Object>>();
                    //一次接收10条数据
                    user.VideoList=videoList;
                    //上面那句将１０条数据的list转给user了

                    break;
                case 1:
                    if (count < 100) {
                        count++;
                        button2.setX(button2.getX() - 2);
                        button3.setY(button3.getY() - 2);
                    }
                    break;
                case 2:
                    if (count > 0) {
                        count--;
                        button2.setX(button2.getX() + 2);
                        button3.setY(button3.getY() + 2);
                    }

                    break;
                case 3:
                //更新ui
                    User_name.setText(user.name);
                break;
            }


        }
    };
    String[] str1;
    SQLiteDatabase db=null;
    DataHelper dataserver;
    private static Boolean isExit = false;
    InfoDetailsFragment idf = new InfoDetailsFragment();
    ShareFragment sf = new ShareFragment();
    AgendaFragment af = new AgendaFragment();
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socketClien;
    User user = new User();
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

    //右上角新消息提示红点
    TextView Message_point,User_name;

    ListView rl;
    MyChatAdapter ladapter;
    int[] layout = {R.layout.left_list_item, R.layout.line_item};


    public ArrayList<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>();

    LinearLayout ll;
    public ImageView heard, left_head;
    TextView loginout;
    ImageView message;
    Button button1 = null, button2 = null, button3 = null;

    //////// TODO: http网络请求
    HttpURLConnection conn = null;

    String requestHeader = null;//请求头
    byte[] requestBody = null;//请求体
    String responseHeader = null;//响应头
    byte[] responseBody = null;//响应体
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }
        //初始化控件及布局
        initView();
        //上面请求主线程可以使用http
    /*	final ImageView fabIconNew = new ImageView(this);
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
rlIcon1.setOnClickListener(new OnClickListener()
{
	
	public void onClick(View v)
	{
		Toast.makeText(MainActivity.this,"iii",Toast.LENGTH_SHORT).show();
	}
});
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
			});*/
    }
    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            //Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }
    public void setdate() {
        //设置头像,将序列化的图片反序列化
        byte[] bitmapArray;
        bitmapArray = Base64.decode(user.name, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        if (bitmap != null)
            heard.setImageBitmap(bitmap);

    }

    public void setgroup(String[] group, String[] phones) {
        //将线程返回的值发送到fragment去
        idf.setgroup(MainActivity.this, group, phones);

    }


    private void initView() {
        user.mainActivity=null;
        user.mainActivity=MainActivity.this;
        mHandler.sendEmptyMessage(3);
        //MainActivity的布局文件中的主要控件初始化
        mToolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        //   mNavigationView = (NavigationView) this.findViewById(R.id.navigation_view);
        mTabLayout = (TabLayout) this.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) this.findViewById(R.id.view_pager);
        rl = (ListView) this.findViewById(R.id.tRecyclerView1);
        ll = (LinearLayout) this.findViewById(R.id.activitymainLinearLayout1);
        Message_point = (TextView) findViewById(R.id.tTextView);
        message = (ImageView) this.findViewById(R.id.activitymainTextView1);
        left_head = (ImageView) this.findViewById(R.id.drawer_headerImageView);
        loginout=(TextView)this.findViewById(R.id.leftlistitemTextView1);
        User_name=(TextView)this.findViewById(R.id.User_name);
        button1 = (Button) this.findViewById(R.id.f1);
        button2 = (Button) this.findViewById(R.id.f2);
        button3 = (Button) this.findViewById(R.id.f3);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);

        Message_point.setText("1");
        ThreadEx c1 = new ThreadEx(MainActivity.this, "loginAndPass");
        Thread x1 = new Thread(c1);
        //x1.start();
        //rl.setItemAnimator(new DefaultItemAnimator());
        //mRecyclerView.addItemDecoration(new div

        // LinearLayoutManager manager = new LinearLayoutManager(mRecyclerView.getContext());
        // manager.setOrientation(2,LinearLayoutManager.HORIZONTAL);
        //  rl.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        //设置RecyclerView布局管理器为2列垂直排布


       // addTextToList("首页", 0, R.drawable.home);
        addTextToList("已付", 0, R.drawable.paid);

        addTextToList("我的", 0, R.drawable.my_video);
        addTextToList("收藏", 0, R.drawable.collect);
        addTextToList("余额", 0, R.drawable.balance);
        addTextToList("分割贱", 1, R.drawable.fab_bg_normal);

        addTextToList("设置", 0, R.drawable.fab_bg_normal);
        addTextToList("反馈", 0, R.drawable.feedback);

        ladapter = new MyChatAdapter(MainActivity.this, lists, layout);
        rl.setAdapter(ladapter);

        left_head.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Personal_.class);
                startActivity(intent);
            }
        });
        rl.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view.findViewById(R.id.leftlistitemTextView1);
                String str = tv.getText().toString();
                if (str.equals("首页")) {

                }
                if (str.equals("已付")) {
                    Intent intent = new Intent(MainActivity.this, Paid_Video.class);
                    startActivity(intent);
                }

                if (str.equals("收藏")) {
                    Intent intent = new Intent(MainActivity.this, Collect_.class);
                    startActivity(intent);
                }
                if (str.equals("我的")) {
                    Intent intent = new Intent(MainActivity.this, My_Video_.class);
                    startActivity(intent);
                }

                if (str.equals("余额")) {
                    Intent intent = new Intent(MainActivity.this, Balance_.class);
                    startActivity(intent);
                }

                if (str.equals("设置")) {
                    Intent intent = new Intent(MainActivity.this, Setting_.class);
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
                //不管怎么样都直接设置隐藏
                Intent intent = new Intent(MainActivity.this, Message_c.class);
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
        titles.add("推荐");
        titles.add("最热");
        titles.add("最新");
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
        POpFloag();
        threadS s = new threadS(MainActivity.this);
        Thread xx = new Thread(s);
        xx.start();
        loginout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //Onebent("sssssssssssssssssssssssssssss");
                dataserver.inst(db, user.phone + "|" +
                        user.pas + "|" +
                        user.name + "|" +
                        "0", MainActivity.this);//flag为0
                Intent intent =new Intent(MainActivity.this, Login_.class);
                startActivity(intent);

            }
        });
    }//initView
    public void POpFloag() {
        //// TODO: 右下角按钮点击事件
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 100) {
                    new Thread(new ThreadShowback()).start();
                } else {
                    button2.setVisibility(View.VISIBLE);
                    button3.setVisibility(View.VISIBLE);
                    new Thread(new ThreadShow()).start();

                }
            }
        });
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 开始录像
                Intent intent = new Intent(MainActivity.this, Round_Video_.class);
                startActivity(intent);
            }

        });

        button3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });
    }

    @Override
    public void Onebent(String str) {
        String str1=str;
    }

    class OnclickListener implements OnClickListener {
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "iii", Toast.LENGTH_SHORT).show();
        }
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


    // 线程类
    class ThreadShow implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (true) {
                try {
                    if (count < 100) {
                        Thread.sleep(1);
                        mHandler.sendEmptyMessage(1);
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println("thread error...");
                }
            }
        }
    }

    class ThreadShowback implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (true) {
                try {
                    if (count > 0) {
                        Thread.sleep(1);
                        mHandler.sendEmptyMessage(2);
                    } else {
                        button2.setVisibility(View.INVISIBLE);
                        button3.setVisibility(View.INVISIBLE);
                        break;
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println("thread error...");
                }
            }
        }
    }

    public class threadS implements Runnable {
        Context context;

        public threadS(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            try {
                URL url = new URL("http://192.168.1.112:1103/reg/phone");
                conn = (HttpURLConnection) url.openConnection();
                // 设置输入/出流

                conn.setDoOutput(true);

                conn.setDoInput(true);

// 设置请求方式为Post

                conn.setRequestMethod("POST");
                // InputStreamReader read = new InputStreamReader(conn.getInputStream());
                // BufferedReader in = new BufferedReader(read);
                //  OutputStreamWriter writer=new OutputStreamWriter(conn.getOutputStream());
                //  writer.write("phonenumber=15913044423");
                // Post请求不能使用缓存

                conn.setUseCaches(false);

                conn.setInstanceFollowRedirects(true);

// 设置Content-type

                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

// 在调用getOutputStream时会隐式调用conn.connect()

                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

                String content = "phonenumber=15913044423";//+ URLEncoder.encode("15913044423","gb2312");

                dos.writeBytes(content);


                String str, str1 = null;
                //  while ((str = in.readLine()) != null) {
                //      str1 = str;
                //  }
                dos.flush();

                dos.close();

// 连接完成之后可以关闭这个连接

                // conn.disconnect();


                //    InputStreamReader isr = new InputStreamReader(conn.getInputStream());

                // BufferedReader br = new BufferedReader(isr);

                //   String line = null;

                //  while(((line = br.readLine()) != null)) {


                //   }

// 关闭连接

                //  isr.close();

                conn.disconnect();

                //  read.close();
                //writer.close();
                // conn.disconnect();
                // Toast.makeText(MainActivity.this, str1, Toast.LENGTH_LONG).show();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                //  writer.close();
                conn.disconnect();
            } catch (IOException e) {

                e.printStackTrace();
            } finally {
                //最后将conn断开连接
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
    }
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


    public void addTextToList(String text, int who, int id) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("person", who);
        map.put("image", id);
        map.put("text", text);

        map.put("layout", who);

        lists.add(map);
    }

    private class MyChatAdapter extends BaseAdapter {

        Context context = null;
        ArrayList<HashMap<String, Object>> chatList = null;
        int[] layout;
        String[] from;
        int[] to;


        public MyChatAdapter(Context context,
                             ArrayList<HashMap<String, Object>> chatList, int[] layout
        ) {
            super();
            this.context = context;
            this.chatList = chatList;
            this.layout = layout;

        }


        public int getCount() {
            // TODO Auto-generated method stub
            return chatList.size();
        }


        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }


        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }


        ////////////
        class ViewHolder {
            public ImageView imageView = null;
            public TextView textView = null;
            public String title;
            private ImageView i_icon, i_icon2;
            public TextView T_title, T_red;
        }
        ////////////

        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder = null;
            final TextView tt;
            LinearLayout re;
            int who = (Integer) chatList.get(position).get("person");


            switch (who) {
                case 0:
                    convertView = LayoutInflater.from(context).inflate(
                            layout[who], null);
                    ImageView img = (ImageView) convertView.findViewById(R.id.left_list_itemImageView);
                    TextView tv = (TextView) convertView.findViewById(R.id.leftlistitemTextView1);
                    img.setImageResource((Integer) chatList.get(position).get("image"));
                    tv.setText((String) chatList.get(position).get("text"));


                    break;
                case 1:
                    isEnabled(position);
                    convertView = LayoutInflater.from(context).inflate(
                            layout[who], null);
                    View v = (View) convertView.findViewById(R.id.lineitemView1);
                    v.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {

                        }
                    });

                    break;


            }
            return convertView;

        }
    }


}
