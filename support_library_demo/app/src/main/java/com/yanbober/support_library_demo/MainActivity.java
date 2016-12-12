package com.yanbober.support_library_demo;

import android.app.Instrumentation;
import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.graphics.*;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import java.text.SimpleDateFormat;
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
            JSONObject jso = null;
            JSONArray jsa = null;

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
                            videos_list = maps;
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
                    //获得通知
                    /*
                    [{
                    "noticetime":"2016-12-09T00:44:38.876Z"
                    ,"other":"2016年12月09日   08:56:57"
                    ,"_id":"584a016ebc2fce00115c54de"
                    ,"kinds":1
                    ,"videoTitle":null
                    ,"videoId":""
                    ,"__v":0
                    ,"payorId":null
                    ,"owner":"话"
                    ,"payor":null //付款人
                    ,"IrrelevantTF":false
                    ,"outlay":0
                    }]
                     */
                    try {
                        if (bundle.getString("?").equals("获取成功")) {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
                            Date curDate = new Date(System.currentTimeMillis());
//获取当前时间
                            String str = formatter.format(curDate);
                            jsa = (JSONArray) msg.obj;
                            if (jsa.length() > 0)
                                for (int i = 1; i < jsa.length(); i++) {//处理多条通知

                                    JSONArray jsonArray = jsa.getJSONArray(i);
                                    for (int y = 0; y < jsonArray.length(); y++) {
                                        jso = jsonArray.getJSONObject(y);
                                        HashMap<String, Object> map = new HashMap<String, Object>();
                                        map.put("_id", jso.getString("_id"));
                                        map.put("noticetime", jso.getString("noticetime"));
                                        map.put("kinds", jso.getString("kinds"));
                                        map.put("videoTitle", jso.getString("videoTitle"));
                                        map.put("videoId", jso.getString("videoId"));
                                        map.put("payorId", jso.getString("payorId"));
                                        map.put("payor", jso.getString("payor"));
                                        map.put("outlay", jso.getString("outlay"));
                                        map.put("owner", jso.getString("owner"));//昵称
                                        map.put("IrrelevantTF", jso.getString("IrrelevantTF"));
                                        map.put("other", jso.getString("other"));
                                        //map.put("other",str);
                                        if (user.notices_list == null)
                                            user.notices_list = new ArrayList<>();
                                        user.notices_list.add(map);
                                        //赋给user
                                    }
                                }


                        } else {

                        }

                    } catch (JSONException e) {
                        Log.e("获取所有通知的Hand", e.toString());
                    }
                    break;
                case 4:
                    // ImageMessage(bundle.getString("?"));
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

支
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
                            map.put("balance", jsonObject.getString("balance"));
                            map.put("paypassword", jsonObject.getString("paypassword"));
                            User_name.setText(jsonObject.getString("nickname"));
                            map.put("notices", jsonObject.getString("notices"));//数组
                            map.put("collects", jsonObject.getString("collects"));//数组

                            maps.add(map);
                            user.mydata = map;
                            //  thread h=new thread(jsonObject.getString("headPortrait"));
                            //    Thread b=new Thread(h);
                            //    b.start();
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
                        if (bundle.getString("?").equals("上传")) {
                            SetHead(bundle.getString("url"));
                        } else {
                            Bitmap bitmap2 = null;
                            JSONObject js = (JSONObject) msg.obj;
                            user.picture = js.get("headprturl").toString();
                            user.mydata.put("headprturl",js.get("headprturl").toString());
                            SetHead(js.getString("headprturl").toString());
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
                        jsa = (JSONArray) msg.obj;
                        ArrayList<HashMap<String, Object>> listmap = new ArrayList<HashMap<String, Object>>();
                        if (jsa.length() > 0)
                            for (int y = 0; y < jsa.length(); y++) {
                                jso = jsa.getJSONObject(y);
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
                case 9:
                    //设置头像
                    left_head.setImageBitmap((Bitmap) msg.obj);
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


    public ArrayList<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>(), videos_list;

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
        Log.e("返回如果由输出则由是bug","--------------------------------");
        animation = new RotateAnimation(0f, 90f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        user.getBitmapurl=new GetBitmapurl();
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
                if (user.phone.equals("null"))
                {
                    Intent intent = new Intent(MainActivity.this,Login_.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(MainActivity.this, Personal_.class);
                    startActivity(intent);
                }
            }
        });
        rl.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view.findViewById(R.id.leftlistitemTextView1);
                if (tv != null) {
                    String str = tv.getText().toString();
                    if (str.equals("首页")) {
                        mDrawerLayout.closeDrawer(mNavigationView);
                    }
                    if (str.equals("已付")) {
                        if (!user.phone.equals("null")&&!user.phone.equals("15913044423")) {
                            Intent intent = new Intent(MainActivity.this, Paid_Video.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MainActivity.this, Login_.class);
                            startActivity(intent);

                        }
                    }

                    if (str.equals("收藏")) {
                        if (!user.phone.equals("null")&&!user.phone.equals("15913044423")) {
                            Intent intent = new Intent(MainActivity.this, Collect_.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MainActivity.this, Login_.class);
                            startActivity(intent);

                        }
                    }
                    if (str.equals("我的")) {
                        if (!user.phone.equals("null")&&!user.phone.equals("15913044423")) {
                            Intent intent = new Intent(MainActivity.this, My_Video_.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MainActivity.this, Login_.class);
                            startActivity(intent);

                        }
                    }

                    if (str.equals("余额")) {
                        if (!user.phone.equals("null")&&!user.phone.equals("15913044423")) {
                            Intent intent = new Intent(MainActivity.this, Balance_.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MainActivity.this, Login_.class);
                            startActivity(intent);

                        }
                    }

                    if (str.equals("设置")) {
                        if (!user.phone.equals("null")&&!user.phone.equals("15913044423")) {
                            Intent intent = new Intent(MainActivity.this, Setting_.class);
                            startActivityForResult(intent, 0);
                        } else {
                            Intent intent = new Intent(MainActivity.this, Login_.class);
                            startActivity(intent);

                        }
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
                if (!user.phone.equals("null")&&!user.phone.equals("15913044423")) {

                    Intent intent = new Intent(MainActivity.this, Message_c.class);
                    startActivity(intent);
                }else
                {
                    Intent intent = new Intent(MainActivity.this, Login_.class);
                    startActivity(intent);
                }

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
        ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni=cm.getActiveNetworkInfo();
        if(ni==null)
        {//判断是否有网络
            Toast.makeText(this,"当前无网络连接",Toast.LENGTH_SHORT).show();
        }else {
            //数据库操作
            dataserver = new DataHelper(MainActivity.this);
            str1 = dataserver.readData("flag|").split("\\|");
            if (str1[0].equals("flag")) {

            } else {
                if (str1[5].equals("1")) {
                    //判断是否已保持登录状态

                    ArrayList<HashMap<String, Object>> maps = new ArrayList<HashMap<String, Object>>();

                    byte[] bytebitmap;
                    bytebitmap = Base64.decode(str1[3], Base64.DEFAULT);
                    user.phone = str1[0];//phone
                    user.pas = str1[1];
                    user.name = str1[2];
                    user.headBitmap = BitmapFactory.decodeByteArray(bytebitmap, 0, bytebitmap.length);
                    //头像反序列化
                    user.token = str1[4];
                    //str1[5]  flag＝０
                    User._id = str1[6];

                }
            }
            if (user.phone.equals("null")) {
                getAllvideos();
                User_name.setText("未登录");
                loginout.setText("");
                loginout.setClickable(false);
            } else {
                if (user.phone.equals("15913044423")) {
                    getAllvideos();
                    User_name.setText("未登录");
                    loginout.setText("");
                    loginout.setClickable(false);
                } else {

                    Bundle bun = this.getIntent().getExtras();
                    //if (bun != null && bun.containsKey("isfirst"))


                    //String str=bun.getString("isfirst");
                    //if(str.equals("0"))

                    setMyFirstData();
                    CheckHead();
                    CheckData();
                    getAllvideos();
                    getcollect();
                    getAllMessage();

         /*   HashMap<String, Object> log_map = new HashMap<String, Object>();
            log_map.put("isprogress", 0);

            Pop_Img.Builder p = new Pop_Img.Builder(MainActivity.this, log_map);
            p.setPositiveButton("[潮汕揭]初版\n问题反馈:(qq) 1213965634\n\n", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //	dialog.dismiss();
                    //login


                }
                //设置你的操作事项


            });

            p.create().show();*/
                }
            }
        }
        //


        //  CheckHead();
        // http_thread_ htt=new http_thread_("http://trying-video.herokuapp.com/user/image?token=","/sdcard/DCIM/Camera/IMG_20160926_183708.jpg",mHandler);
        // Thread b=new Thread(htt);
        // b.start();
    }//initView

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
                // doUploadImg("http://trying-video.herokuapp.com/user/image?token=", "/sdcard/DCIM/Camera/IMG_20160926_183708.jpg");
            }

        });
    }
/*

返回的activity数据

 */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1://设置头像
                //把setting_返回的图片设置给头像，但是并不联网，联网环图只在setting_中
                User u = new User();
                left_head.setImageBitmap((Bitmap) u.headBitmap);
                Log.e("User中的phone",u.phone);
                Log.e("User中的headPicture",u.picture);
                Log.e("User中的notices列表",String.valueOf(u.notices_list.size()));
                break;
        }
    }

    @Override
    public void Onebent(String str) {
        //  idf.Onebent("sssss");
        // String str1=str;
    }

    /*

    设置头像
     */ Bitmap bitmap2;

    private void SetHead(String url) {

            left_head.setTag(url);
            HashMap<String, Object> map = new HashMap<>();
            map.put("Context", MainActivity.this);
            map.put("dataserver", dataserver);
            map.put("sql", db);
           User u=new User();
            u.getBitmapurl.loadImageViewurl(url, left_head, map);


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

    /*

    获得视频列表
     */
    private void getAllvideos() {
        Http_UploadFile_ http_uploadFile_ = new Http_UploadFile_(MainActivity.this
                , mHandler
                , "http://trying-video.herokuapp.com/user/video/all/detail"
                , "4"
                , "POST"
                , "getvideos");
        Thread t = new Thread(http_uploadFile_);
        t.start();

    }


    private void setMyFirstData() {
        Http_UploadFile_ http_uploadFile_;
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
    private String randomProdoction()
    {
        int random=0;
        String str=null;
        Random ran=new Random(System.currentTimeMillis());
        for (int i=0;i<20;i++)
        {

            random=ran.nextInt(10);
            str+=String.valueOf(random);
            //数字加一个字母
            switch (random)
            {
                case 0:
                    str+="a";
                    break;

                case 1:
                    str+="b";
                    break;

                case 2:
                    str+="c";
                    break;

                case 3:
                    str+="e";
                    break;

                case 4:
                    str+="f";
                    break;

                case 5:
                    str+="g";
                    break;

                case 6:
                    str+="h";
                    break;

                case 7:
                    str+="i";
                    break;
            }
        }
        return str+String.valueOf(random);
    }

    public void CheckHead() {
        //数据库操作
        dataserver = new DataHelper(MainActivity.this);
        str1 = dataserver.readData("flag|").split("\\|");
        File dirFile = new File("/sdcard/180s/headpicture/" + randomProdoction() + ".png");
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);

            File file1 = new File("/sdcard/180s");
            if (!file1.exists()) {
                file1.mkdirs();
            }
            File file2 = new File("/sdcard/180s/headpicture");
            if (!file2.exists()) {
                file2.mkdirs();
            }
            if (!dirFile.exists()) {


                dirFile.createNewFile();


            } else {
                dirFile.delete();
                dirFile.createNewFile();
            }
            //	File myCaptureFile = new File(path + fileName);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dirFile));
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, bos);


            bos.flush();
            bos.close();
         }catch (IOException e)
        {

        }
            //    MainActivity,       handler,  url,connectType,token,data) {
        HashMap<String ,Object> map=new HashMap<>();
        map.put("handler",mHandler);
        map.put("Context",MainActivity.this);
        map.put("headfile",dirFile);
        Http_UploadFile_ http_uploadFile_ = new Http_UploadFile_(
                 "http://trying-video.herokuapp.com/user/image?token=" + user.token
                ,map
                , "9");
        Thread x = new Thread(http_uploadFile_);
        x.start();

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


    public void getAllMessage() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("Context", MainActivity.this);

        map.put("handler", mHandler);

        Http_UploadFile_ htt = new Http_UploadFile_("http://trying-video.herokuapp.com/user/notice/all?token=" + user.token
                , map
                , "15");
        Thread x = new Thread(htt);
        x.start();


		/*>>  返回全部通知
		通知数据少了头像
		{
			{
				"_id" : "***",
				"videoTitle" : "***",
				"outlay" : ***,
				"costTF" : "***",
				"operaTF" : "***",
				"rmoveTF" : "***",
				"IrrelevantTF" : "***",
				"other" : "***"
			},
			{...},*/
    }

    public void getcollect() {


        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("Context", MainActivity.this);
        map.put("handler", mHandler);
        String url = "http://trying-video.herokuapp.com/user/allcollect?token=" + user.token;
        Http_UploadFile_ http = new Http_UploadFile_(url, map, "13");
        Thread xx = new Thread(http);
        xx.start();

    }

    public void getbalance() {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("Context", MainActivity.this);
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

    public class thread implements Runnable {/////chat socket

        String str;

        public thread(String str) {

            this.str = str;

        }

        public void run() {
            try {

                URL url = new URL(str);

                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = new BufferedInputStream(conn.getInputStream());
                bitmap2 = BitmapFactory.decodeStream(is);// BitmapFactory.decodeStream(is);
                Message msg = new Message();
                msg.obj = bitmap2;
                msg.what = 9;
                mHandler.sendMessage(msg);
                is.close();
                conn.disconnect();

            } catch (IOException e) {
                bitmap2 = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.down);
                Message msg = new Message();
                msg.what = 9;
                msg.obj = bitmap2;
                mHandler.sendMessage(msg);


            }
        }
    }
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
