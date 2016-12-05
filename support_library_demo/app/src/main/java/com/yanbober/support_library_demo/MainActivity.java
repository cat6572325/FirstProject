package com.yanbober.support_library_demo;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
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
import android.view.animation.*;
import android.widget.*;
import android.widget.AdapterView.*;

import com.okhttplib.*;
import com.okhttplib.annotation.*;
import com.okhttplib.callback.*;
import com.yanbober.support_library_demo.DataHelpers.*;
import com.yanbober.support_library_demo.Http_Util.*;
import com.yanbober.support_library_demo.Message_S.*;

import java.io.*;
import java.net.*;
import java.util.*;

import org.json.*;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View.OnClickListener;

/**
 * 一个中文版Demo App搞定所有Android的Support Library新增所有兼容控件
 * 支持最新2015 Google I/O大会Android Design Support Library
 */


public class MainActivity extends AppCompatActivity implements InfoDetailsFragment.OnActivityebent {
    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case 0:
                    /*
					视频列表缺少收藏者id支付者id
					>>  返回全部视频信息
                    {
                        {
                            "_id" : "***",    //视频id
                            "uploader" : "***",    //上传者
                            "title" : "***",    //标题
                            "introduction" : "***",    //简介
                            "price" : ***,    //价格
                            "paidPerson" : "***",    //付款人ID
                            "cocerPerson" : "***",    //收藏人名字
                            "paidppnumber" : ***,    //付款人数
                            "concernednumber" : ***    //收藏人数
                        },
                        {...},,*/
                    try {
                        //获取视频列表
                        if (bundle.getString("?").equals("获取失败")) {


                        } else {
                            JSONArray jsonArray = (JSONArray) msg.obj;
                            HashMap<String, Object> map;
                            ArrayList<HashMap<String, Object>> maps = new ArrayList<HashMap<String, Object>>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                                //获得第i个json
                                map = new HashMap<String, Object>();
                                map.put("time", jsonObject.getString("time"));
                                map.put("title", jsonObject.getString("title"));
                                map.put("price", jsonObject.getString("price"));
                                map.put("_id", jsonObject.getString("_id"));
                                user.vid = null;
                                user.vid = jsonObject.getString("_id");
                                map.put("uploader", jsonObject.getString("uploader"));
                                map.put("introduction", jsonObject.getString("introduction"));
                                map.put("vdourl", jsonObject.getString("vdourl"));
                                map.put("paidppnumber", jsonObject.getString("paidppnumber"));
                                map.put("paidPerson", jsonObject.getJSONArray("paidPerson"));
                                map.put("cocerPerson", jsonObject.getJSONArray("cocerPerson"));
                                try {
                                    if (jsonObject.getString("vdoPhotourl") != null) {
                                        map.put("concernednumber", jsonObject.getString("concernednumber"));
                                        map.put("vdoPhotourl", jsonObject.getString("vdoPhotourl"));

                                    }


                                } catch (JSONException e) {
                                    String str = jsonObject.getString("concernednumber");


                                    map.put("vdoPhotourl", jsonObject.getString("vdourl"));
                                }

                                maps.add(map);

                            }

                            user.maps = maps;
                            videos_list=maps;
                            idf.Onebent(maps);





                        }
                    } catch (JSONException e) {

                    }

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

                    //  UpUI("null", (HashMap<String,Object>)msg.obj, "6");
                    break;
                case 4:
                    ImageMessage(bundle.getString("?"));
                    if (bundle.getString("?").equals("修改成功")) {
                        User_name.setText(user.name);
                    }
                    break;
                case 5:
                    //初始化信息
                    /*
                    >>  返回个人信息及id
                    {
                        "nickname" : "***",
                        "headPortrait" : "***",
                        "phone" : "***",
                        "paypassword" : "***",
                        "balance" : ***,
                        "paidVideos" : "***",
                        "collects" : "***",
                        "notices" : "***"
                    }

                     */
                    try {

                        if (bundle.getString("?").equals("获取失败")) {

                        } else {
                            JSONObject jsonObject = (JSONObject) msg.obj;
                            HashMap<String, Object> map;
                            ArrayList<HashMap<String, Object>> maps = new ArrayList<HashMap<String, Object>>();
                            map = new HashMap<String, Object>();
                            map.put("phone", jsonObject.getString("phone"));
                            map.put("_id", jsonObject.getString("_id"));
                            map.put("headPortrait", jsonObject.getString("headPortrait"));
                            map.put("nickname", jsonObject.getString("nickname"));
                            map.put("balance",jsonObject.getString("balance"));
                            map.put("paypassword",jsonObject.getString("paypassword"));
                            User_name.setText(jsonObject.getString("nickname"));
                            map.put("notices", jsonObject.getString("notices"));//数组
                            map.put("collects", jsonObject.getString("collects"));//数组

                            maps.add(map);
                            user.mydata = map;

                            if (jsonObject.getJSONArray("notices").length() > 0) {


                                Message_point.setText(String.valueOf(jsonObject.getJSONArray("notices").length()));
                            } else {
                                Message_point.setVisibility(View.INVISIBLE);
                            }


                        }
                    } catch (JSONException e) {
                        Log.e("在获取个人信息的时候", e.toString());
                    }
                    break;

                case 6:

                    try {
                        if (bundle.getString("?").equals("获取失败")) {
                        } else {
                            JSONObject jsonObject = (JSONObject) msg.obj;
                            user.picture = jsonObject.get("").toString();

                        }
                    } catch (JSONException e) {
                        Log.e("在获取头像的时候", e.toString());
                    }


                    break;


                case 7:
                    //接收收藏列表
                    try {/*
						>>  返回全部收藏
                        {
                            {
                                "_id" : "***",
                                "collector" : "***",    // 收藏者
                                "author" : "***",    //作者
                                "videoTitle" : "***",    //视频名
                                "vdo_id" : "***"    //视频id
                            },
                            {...},
						 ...*/
                        //获取列表等详细数据
                        Bundle bun = msg.getData();
                        JSONArray jsa = (JSONArray) msg.obj;
                        ArrayList<HashMap<String, Object>> listmap = new ArrayList<HashMap<String, Object>>();
                        if (jsa.length() > 0)
                            for (int y = 0; y < jsa.length(); y++) {
                                JSONObject jso = jsa.getJSONObject(y);
                                Log.e("colllllll", jso.toString());
                                //addTextToList(jso.getString("videoTitle"),3,R.drawable.qq,"901人付款",1,1);
                                HashMap<String, Object> map = new HashMap<String, Object>();
                                map.put("_id", jso.getString("_id"));
                                map.put("collector", jso.getString("collector"));
                                map.put("author", jso.getString("author"));
                                map.put("videoTitle", jso.getString("videoTitle"));
                                map.put("vdo_id", jso.getString("vdo_id"));
                                listmap.add(map);

                            }
                        user.Collect_List = listmap;
                        //addTextToList("无任何收藏",3,R.drawable.qq,"901人付款",1,1);

                    } catch (JSONException e) {
                        Log.e("获取收藏的时候", e.toString());
                    }


                    break;
                case 8:
                    //查余额
                    try {
                        JSONObject jsonObjec = (JSONObject) msg.obj;

                        user.mydata.put("balance", jsonObjec.getString("balance"));
                    } catch (JSONException e) {
                        Log.e("在获取余额的时候", e.toString());
                    }
                    break;
            }


        }
    };
    RotateAnimation animation;
    String[] str1;
    SQLiteDatabase db = null;
    DataHelper dataserver;
    private static Boolean isExit = false;
    InfoDetailsFragment idf = new InfoDetailsFragment();
    ShareFragment sf = new ShareFragment();
    AgendaFragment af = new AgendaFragment();
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socketClien;
    User user = new User();
    int count = 0;
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
    TextView Message_point, User_name;

    ListView rl;
    MyChatAdapter ladapter;
    int[] layout = {R.layout.left_list_item, R.layout.line_item};


    public ArrayList<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>(),videos_list;

    LinearLayout ll;
    public ImageView heard, left_head;
    TextView loginout;
    ImageView message;
    Button button2 = null, button3 = null;

    FloatingActionButton button1 = null;

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
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            //System.exit(0);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }

    public ArrayList<HashMap<String, Object>> gethash() {
        return user.maps;
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
        animation = new RotateAnimation(0f, 90f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        user.mainActivity = null;
        user.mainActivity = MainActivity.this;
        //mHandler.sendEmptyMessage(3);
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
        loginout = (TextView) this.findViewById(R.id.leftlistitemTextView1);
        User_name = (TextView) this.findViewById(R.id.User_name);
        button1 = (FloatingActionButton) this.findViewById(R.id.f1);
        button2 = (Button) this.findViewById(R.id.f2);
        button3 = (Button) this.findViewById(R.id.f3);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        //  if (user.)
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

        // addData();
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
                if (tv != null) {
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
        fragments.add(af);
        fragments.add(sf);

        //创建ViewPager的adapter
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        //千万别忘了，关联TabLayout与ViewPager
        //同时也要覆写PagerAdapter的getPageTitle方法，否则Tab没有title
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
        POpFloag();
        loginout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //Onebent("sssssssssssssssssssssssssssss");
                try {
                    dataserver = new DataHelper(MainActivity.this);
                    dataserver.inst(db, user.phone
                            + "|" + user.pas
                            + "|" + user.phone
                            + "|null|"
                            + user.token
                            + "|0", MainActivity.this);
                    Intent intent = new Intent(MainActivity.this, Login_.class);
                    startActivity(intent);
                    finish();
                } catch (SQLException e) {

                }
            }
        });
        if (user.phone.equals("15913044423")) {

        } else {
            Http_UploadFile_ http_uploadFile_ = new Http_UploadFile_(MainActivity.this
                    , mHandler
                    , "http://trying-video.herokuapp.com/user/video/all/detail"
                    , "4"
                    , "POST"
                    , "getvideos");
            Thread t = new Thread(http_uploadFile_);
            t.start();

            Bundle bun = this.getIntent().getExtras();
            //if (bun != null && bun.containsKey("isfirst"))

            http_uploadFile_ = new Http_UploadFile_(
                    MainActivity.this
                    , mHandler
                    , "http://trying-video.herokuapp.com/user/information?token=" + user.token
                    , "7"
                    , "POST"
                    , "新用户" + System.currentTimeMillis() + "||0|0|0"
            );
            Thread te = new Thread(http_uploadFile_);
            te.start();
            //暂时没有用户填写资料的界面就先设置默认了


            //String str=bun.getString("isfirst");
            //if(str.equals("0"))
            CheckData();
            getcollect();
            ArrayList<HashMap<String,Object>> maps=new ArrayList<HashMap<String,Object>>();

            final String[] items = new String[]{
                    "地球地心",
                    "火星",
                    "黑洞",
                    "大山",
                    "花果山"
            };
            HashMap<String,Object> map=new HashMap<>();
            map.put("title","请选择你的故乡");
            map.put("items",items);
            maps.add(map);
            final String[] items1 = new String[]{
                    "AK_47",
                    "格林炮",
                    "全能塑料枪",
                    "嘴炮",
                    "阿木斯特狼螺旋机关牛啊姆斯特狼螺旋机关冲天炮"
            };
            HashMap<String,Object> map1=new HashMap<>();
            map1.put("title","请选择你的武器");
            map1.put("items",items1);
            maps.add(map1);
            final String[] items2 = new String[]{
                    "太阳",
                    "黑洞",
                    "神",
                    "美国总统",
                    "猫"
            };
            HashMap<String,Object> map2=new HashMap<>();
            map2.put("title","那么你准备要攻击谁");
            map2.put("items",items2);
            maps.add(map2);
            showADialog(maps,0);
        }
        //


        //  CheckHead();
        // http_thread_ htt=new http_thread_("http://trying-video.herokuapp.com/user/image?token=","/sdcard/DCIM/Camera/IMG_20160926_183708.jpg",mHandler);
        // Thread b=new Thread(htt);
        // b.start();
    }//initView
    public void showADialog(ArrayList<HashMap<String,Object>> maps1,int count)
    {
        final ArrayList<HashMap<String,Object>> maps=maps1;
        final int counts=count+1;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        String[] items=null;
        switch (count)
        {
            case 0:
                builder.setTitle(maps.get(0).get("title").toString());
                //定义列表中的选项
                items =(String[])maps.get(0).get("items");

                break;
            case 1:
                builder.setTitle(maps.get(1).get("title").toString());
                //定义列表中的选项
                items =(String[])maps.get(1).get("items");

                break;
            case 2:
                builder.setTitle(maps.get(2).get("title").toString());
                //定义列表中的选项
                items =(String[])maps.get(2).get("items");

                break;
        }
        if (items!=null) {

            //设置列表选项
            builder.setItems(items, new DialogInterface.OnClickListener() {
                //点击任何一个列表选项都会触发这个方法
                //arg1：点击的是哪一个选项
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    showADialog(maps, counts);
                }
            });
            // 取消选择
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        }
    }
    public void POpFloag() {
        //// TODO: 右下角按钮点击事件
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                view.clearAnimation();
                animation.setDuration(500);//设置动画持续时间
                /** 常用方法 */
                //  animation.setRepeatCount(500);//设置重复次数
                animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
//animation.setStartOffset(long startOffset);//执行前的等待时间
                view.startAnimation(animation);
                //iv_drag.setAnimation(animation);
                //	/** 开始动画 */
                //	animation.startNow();
                // 结束动画
                //animation.cancel();
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
                doUploadImg("http://trying-video.herokuapp.com/user/image?token=", "/sdcard/DCIM/Camera/IMG_20160926_183708.jpg");
            }

        });
    }

    @Override
    public void Onebent(String str) {
        //  idf.Onebent("sssss");
        // String str1=str;
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

    private void ImageMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    //更新数据
    private void UpUI(String url, HashMap<String, Object> data, String count) {

        //数据库操作
        dataserver = new DataHelper(MainActivity.this);
        str1 = dataserver.readData("flag|").split("\\|");


        user.phone = str1[0];
        user.pas = str1[1];
        user.name = user.mydata.get("nickname").toString();
        Log.e("main", user.mydata.get("nickname").toString());
        Log.e("main", user.name);


        //数据库操作
        //                         MainActivity,       handler,  url,connectType,token,data) {
        Http_UploadFile_ http_uploadFile_ = new Http_UploadFile_(MainActivity.this, mHandler, url, count, str1[4], user.name);
        Thread x = new Thread(http_uploadFile_);
        x.start();
    }


    public void CheckData() {
        //数据库操作
        dataserver = new DataHelper(MainActivity.this);
        str1 = dataserver.readData("flag|").split("\\|");
        //  "nickname" : ${nickname},    //昵称(String)
        //  "paypassword" : ${paypassword},    //支付密码(String)
        // "balance" : ${balance},    //余额(Number)
        //"notices" : ${notices},    //通知(String)  [存的只是通知id]
        //"collects" : ${collects}    //收藏(String)  [存的只是收藏id]
        //                                                   MainActivity,       handler,  url,connectType,token,data) {
        Http_UploadFile_ http_uploadFile_ = new Http_UploadFile_(MainActivity.this
                , mHandler
                , "http://trying-video.herokuapp.com/user/information/" + user._id
                , "8"
                , str1[4]
                , str1[1] + "||0|0|0"

        );
        Thread x = new Thread(http_uploadFile_);
        x.start();

    }

    public void CheckHead() {
        //数据库操作
        dataserver = new DataHelper(MainActivity.this);
        str1 = dataserver.readData("flag|").split("\\|");
        //    MainActivity,       handler,  url,connectType,token,data) {
        Http_UploadFile_ http_uploadFile_ = new Http_UploadFile_(MainActivity.this
                , mHandler
                , "http://trying-video.herokuapp.com/user/image?token=" + user.token
                , "10"
                , str1[4]
                , str1[1] + "||0|0|0");
        Thread x = new Thread(http_uploadFile_);
        //    x.start();

    }

    public void loadHead() {
        //数据库操作
        dataserver = new DataHelper(MainActivity.this);
        str1 = dataserver.readData("flag|").split("\\|");
        //  "nickname" : ${nickname},    //昵称(String)
        //  "paypassword" : ${paypassword},    //支付密码(String)
        // "balance" : ${balance},    //余额(Number)
        //"notices" : ${notices},    //通知(String)  [存的只是通知id]
        //"collects" : ${collects}    //收藏(String)  [存的只是收藏id]
        //                                                   MainActivity,       handler,  url,connectType,token,data) {
        Http_UploadFile_ http_uploadFile_ = new Http_UploadFile_(MainActivity.this
                , mHandler
                , "http://trying-video.herokuapp.com/user/image?token=" + user.token
                , "10"
                , str1[4]
                , str1[1] + "||0|0|0");
        Thread x = new Thread(http_uploadFile_);
        x.start();


    }


    public void doUploadImg(String url, String path) {
        OkHttpUtilInterface okHttpUtil = OkHttpUtil.Builder()
                .setCacheLevel(CacheLevel.FIRST_LEVEL)
                .setConnectTimeout(25).build(this);
//一个okHttpUtil即为一个网络连接

        HttpInfo info = HttpInfo.Builder()
                .setUrl(url + user.token)
                .addUploadFile("file", path, new ProgressCallback() {
                    @Override

                    public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
                        // uploadProgress.setProgress(percent);
                        Log.e("ssss", "上传进度：" + percent);
                        int i = percent;
                    }

                    @Override
                    public void onResponseMain(String filePath, HttpInfo info) {
                        String str = info.getRetDetail();
                        Log.e("上传信息", str);
                    }
                })
                .build();
        OkHttpUtil.getDefault(this).doUploadFileAsync(info);
    }

    public void getcollect() {


        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("context", MainActivity.this);

        String url = "http://trying-video.herokuapp.com/user/allcollect?token=" + user.token;
        Http_UploadFile_ http = new Http_UploadFile_(url, map, "13");
        Thread xx = new Thread(http);
        xx.start();

    }

    public void getbalance() {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("context", MainActivity.this);
        map.put("hanlder", mHandler);
        String url = "http://trying-video.herokuapp.com/user/allcollect?token=" + user.token;
        Http_UploadFile_ htt = new Http_UploadFile_
                (
                        "http://trying-video.herokuapp.com/user/balance?token=" + user.token
                        , map
                        , "16");
        Thread xx = new Thread(htt);
        xx.start();
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
                    convertView.setClickable(false);
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
