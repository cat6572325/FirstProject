package com.yanbober.support_library_demo;
/*
		这个类显示一个信息列表，点击可以进入另一activity查看全文或者跳到主题
		恐怕是这个项目第二简单的了

		当点击某一项时将通知id加入数据库，表示已读
		当初始时调用数据库信息，判断每一项有没有这个通知的id，有则表示已读
		加入已读后除了id在服务器改变　数据库信息被清空　甚至换手机之外一直保存不变



 */





import android.content.*;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

import com.yanbober.support_library_demo.DataHelpers.DataHelper;
import com.yanbober.support_library_demo.Http_Util.*;
import java.text.*;
import java.util.*;
import org.json.*;

import android.support.v7.widget.Toolbar;

public class Message_c extends AppCompatActivity
{
	public Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			Bundle bundle=msg.getData();
			JSONArray jsa=null;
			JSONObject jso=null;
			switch (msg.what)
			{
				case 0:
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


					try{
					if(bundle.getString("?").equals("获取成功"))
					{
						  SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");   
  Date curDate =  new Date(System.currentTimeMillis());
//获取当前时间   
  String   str   =   formatter.format(curDate);
						jsa=(JSONArray)msg.obj;
						for(int i=0;i<jsa.length();i++)
						{//处理多条通知
							jso=jsa.getJSONObject(i);
							HashMap<String,Object> map=new HashMap<String,Object>();
							map.put("_id",jso.getString("_id"));
							map.put("videoTitle",jso.getString("videoTitle"));
							map.put("outlay",jso.getString("outlay"));
							map.put("costTF",jso.getString("costTF"));
							map.put("operaTF",jso.getString("operaTF"));
							map.put("rmoveTF",jso.getString("rmoveTF"));
							map.put("IrrelevantTF",jso.getString("IrrelevantTF"));
							map.put("other",jso.getString("other"));
							map.put("titme",str);
							user.notices_list.add(map);
							
							
						}
						
					}else
					{
						
					}
					
				}catch(JSONException e)
				{
					Log.e("获取所有通知的Hand",e.toString());
				}
				break;
				case 1:
					//更新
					lists1.clear();
					lists1.addAll(lists);
					lists.clear();
					lists.addAll(lists1);
					ladapter.notifyDataSetChanged();
					//lv.setAdapter(ladapter);
					break;

			}
		}
	};
	MyChatAdapter ladapter;
	int[] layout1={R.layout.message_item,R.layout.line_item};
ListView lv=null;
Toolbar tb=null;
User user=new User();
	public ArrayList<HashMap<String,Object>> lists1=new ArrayList<HashMap<String,Object>>();
	ListView rl;
	ImageView left_button;
	SQLiteDatabase db = null;
	DataHelper dataserver;

	int[] layout={R.layout.message_item,R.layout.line_item};
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
	
	public void initView() {
//postmessage();
		lv = (ListView) this.findViewById(R.id.message_listview);
		tb = (Toolbar) this.findViewById(R.id.tool_bar);
		left_button=(ImageView)this.findViewById(R.id.messagelayoutImageView1);
		
		mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
		ladapter = new MyChatAdapter(Message_c.this, lists, layout);
		lv.setAdapter(ladapter);
		LoadthisMessage();
//初始化ToolBar
		setSupportActionBar(tb);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setHomeAsUpIndicator(R.drawable.back_purple);//android.R.drawable.ic_dialog_alert);
		actionBar.setDisplayHomeAsUpEnabled(true);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				User u =new User();
				if (dataserver.isHavethisID(u.notices_list.get(i).get("_id").toString()))
				{//如果是已读的

				}else {//如果是未读的
					HashMap<String,Object> map=new HashMap<String, Object>();
					map.put("_id",u.notices_list.get(i).get("_id").toString());
					dataserver.addisReadSQL(map);
				}
			}
		});
		left_button.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				finish();
			}
			
		});

		//datas();


	}
	public void onItemClick(AdapterView<?> parent,View view,int position,long id)
	{
		TextView tv=(TextView)view.findViewById(R.id.leftlistitemTextView1);
		String str=tv.getText().toString();

		
	}
	public void addTextToList(String text, String time,int who,int id,int isPort)
	{
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("person", who);
		map.put("image", id);
		map.put("text", text);
		map.put("time",time);
		map.put("layout",who);
		map.put("isPort",isPort);
		lists.add(map);
    }
	private void LoadthisMessage()
	{
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

		User u=new User();
		ArrayList<HashMap<String,Object>> map=u.notices_list;
		Http_UploadFile_ http_uploadFile_;

		dataserver=new DataHelper(Message_c.this);
		if (map.size()>0)
		{
			for (int i=0;i<map.size();i++)
			{
				int isPort=0;
				if (!dataserver.isHavethisID(map.get(i).get("_id").toString()))
				{//如果返回的是false则表示未读，则显示红点
					isPort=1;
				}
				switch (Integer.parseInt(map.get(i).get("kinds").toString()))
				{
					case 1:
						//收入
						addTextToList(
								map.get(i).get("payor").toString()
										+"购买了你的"+map.get(i).get("videoTitle").toString()
										+"视频花费了"+map.get(i).get("outlay").toString()
										+"元"
								,map.get(i).get("noticetime").toString()
								,0
								,R.drawable.image
								,isPort

						);

						break;

					case 2:
						//支
						addTextToList(
								"你购买了"+map.get(i).get("videoTitle").toString()
										+"视频花费"
										+map.get(i).get("outlay").toString()
										+"元"
								,map.get(i).get("noticetime").toString()
								,0
								,R.drawable.image
								,isPort
						);

					case 3:
						//上传
						addTextToList(
								"你上传了视频"+map.get(i).get("videoTitle").toString()



								,map.get(i).get("noticetime").toString()
								,0
								,R.drawable.image
								,isPort
						);
						break;

					case 4:
						//删除
						addTextToList(
								"你删除了视频"+map.get(i).get("videoTitle").toString()
										,map.get(i).get("noticetime").toString()
								,0
								,R.drawable.image
								,isPort
						);

					case 5:
						//系统
						addTextToList(
								map.get(i).get("other").toString()
								,map.get(i).get("noticetime").toString()
								,0
								,R.drawable.image
								,isPort
						);


				}
			}
			mHandler.sendEmptyMessage(1);
		}
	}
	private Bitmap getPayorhead(String id)
	{
		if (id!=null) {
			HashMap<String,Object> map=new HashMap<>();
			map.put("handler",mHandler);
			map.put("Context",Message_c.this);
			Http_UploadFile_ http_uploadFile_ = new Http_UploadFile_(
					"http://trying-video.herokuapp.com/user/information/" + id
					,map
					, "8"
			);
			Thread x = new Thread(http_uploadFile_);
			x.start();
		}
		return  null;
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
               // mDrawerLayout.openDrawer(GravityCompat.START);
				finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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



        class ViewHolder
		{
            public ImageView imageView=null;
            public TextView textView=null;
			public String title;
			private ImageView i_icon,i_icon2;
			public TextView T_title,T_red;
        }


        public View getView(int position, View convertView, ViewGroup parent)
		{
			// TODO Auto-generated method stub
			ViewHolder holder=null;
			final TextView tt,titme;

			LinearLayout re;
		
			int who=(Integer)chatList.get(position).get("person");


			switch(who)
			{
				case 0:
					convertView = LayoutInflater.from(context).inflate(
						layout[who], null);
				TextView	isport=(TextView)convertView.findViewById(R.id.messageitemTextView1);//红点
					ImageView img=(ImageView)convertView.findViewById(R.id.messageitemImageView1);
					TextView tv=(TextView)convertView.findViewById(R.id.messageitemTextView2);
					TextView titmeary=(TextView)convertView.findViewById(R.id.messageitemTextView3);
					titmeary.setText(chatList.get(position).get("time").toString());
					//设置时间
					if ((Integer)chatList.get(position).get("isPort")==0)
					isport.setVisibility(View.INVISIBLE);//隐藏这个红点，表示已读信息

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
	
	public void postmessage(View view)
	{
		/*提交新通知

POST   http://localhost:1103/user/notice?token=${token}
{
    "videoTitle" : ${videoTitle},    //视频名(String)
    "outlay" : ${outlay},    //支付收入数目(Number)
    "costTF" : ${costTF},    //花费 收入判断(Boolean)
    "operaTF" : ${operaTF},    //视频操作 或 花费判断(Boolean)
    "rmoveTF" : ${rmoveTF},    //上传 删除判断(Boolean)
    "IrrelevantTF" : ${IrrelevantTF},    //其他信息 或 相关信息判断(Boolean)
    "other" : ${other}    //其他信息(String)
}
>>  返回 message: '通知已更新'
获取用户全部通知
		 */
		 HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("Context",Message_c.this);
		map.put("handler",mHandler);
		map.put("videoId",user.maps.get(0).get("_id"));
		map.put("outlay","100");
		map.put("kinds","1");//收入 支
		map.put("IrrelevantTF","0");
		map.put("other","nothing");
		SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
		Date curDate =  new Date(System.currentTimeMillis());
//获取当前时间
		String   str   =   formatter.format(curDate);

		map.put("other",str);
		
		Http_UploadFile_ htt=new Http_UploadFile_("http://trying-video.herokuapp.com/user/notice?token="+user.token, map,"14");
		Thread x=new Thread(htt);
		x.start();


		
	}
	public void pay(View view)
	{
		/*提交新通知

POST   http://localhost:1103/user/notice?token=${token}
{
    "videoTitle" : ${videoTitle},    //视频名(String)
    "outlay" : ${outlay},    //支付收入数目(Number)
    "costTF" : ${costTF},    //花费 收入判断(Boolean)
    "operaTF" : ${operaTF},    //视频操作 或 花费判断(Boolean)
    "rmoveTF" : ${rmoveTF},    //上传 删除判断(Boolean)
    "IrrelevantTF" : ${IrrelevantTF},    //其他信息 或 相关信息判断(Boolean)
    "other" : ${other}    //其他信息(String)
}
>>  返回 message: '通知已更新'
获取用户全部通知
		 */
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("Context",Message_c.this);
		map.put("handler",mHandler);
		map.put("videoId",user.maps.get(0).get("_id"));
		map.put("outlay","100");
		map.put("kinds","2");//收入 支
		map.put("IrrelevantTF","0");
		map.put("other","nothing");
		SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
		Date curDate =  new Date(System.currentTimeMillis());
//获取当前时间
		String   str   =   formatter.format(curDate);

		map.put("other",str);

		Http_UploadFile_ htt=new Http_UploadFile_("http://trying-video.herokuapp.com/user/notice?token="+user.token, map,"14");
		Thread x=new Thread(htt);
		x.start();



	}
	public void load(View view)
	{
		/*提交新通知

POST   http://localhost:1103/user/notice?token=${token}
{
    "videoTitle" : ${videoTitle},    //视频名(String)
    "outlay" : ${outlay},    //支付收入数目(Number)
    "costTF" : ${costTF},    //花费 收入判断(Boolean)
    "operaTF" : ${operaTF},    //视频操作 或 花费判断(Boolean)
    "rmoveTF" : ${rmoveTF},    //上传 删除判断(Boolean)
    "IrrelevantTF" : ${IrrelevantTF},    //其他信息 或 相关信息判断(Boolean)
    "other" : ${other}    //其他信息(String)
}
>>  返回 message: '通知已更新'
获取用户全部通知
		 */
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("Context",Message_c.this);
		map.put("handler",mHandler);
		map.put("videoId",user.maps.get(0).get("_id"));
		map.put("outlay","100");
		map.put("kinds","3");//收入 支
		map.put("IrrelevantTF","0");
		map.put("other","nothing");
		SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
		Date curDate =  new Date(System.currentTimeMillis());
//获取当前时间
		String   str   =   formatter.format(curDate);

		map.put("other",str);

		Http_UploadFile_ htt=new Http_UploadFile_("http://trying-video.herokuapp.com/user/notice?token="+user.token, map,"14");
		Thread x=new Thread(htt);
		x.start();



	}
	public void delete1(View view)
	{
		/*提交新通知

POST   http://localhost:1103/user/notice?token=${token}
{
    "videoTitle" : ${videoTitle},    //视频名(String)
    "outlay" : ${outlay},    //支付收入数目(Number)
    "costTF" : ${costTF},    //花费 收入判断(Boolean)
    "operaTF" : ${operaTF},    //视频操作 或 花费判断(Boolean)
    "rmoveTF" : ${rmoveTF},    //上传 删除判断(Boolean)
    "IrrelevantTF" : ${IrrelevantTF},    //其他信息 或 相关信息判断(Boolean)
    "other" : ${other}    //其他信息(String)
}
>>  返回 message: '通知已更新'
获取用户全部通知
		 */
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("Context",Message_c.this);
		map.put("handler",mHandler);
		map.put("videoId",user.maps.get(0).get("_id"));
		map.put("outlay","100");
		map.put("kinds","4");//收入 支
		map.put("IrrelevantTF","0");
		map.put("other","nothing");
		SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
		Date curDate =  new Date(System.currentTimeMillis());
//获取当前时间
		String   str   =   formatter.format(curDate);

		map.put("other",str);

		Http_UploadFile_ htt=new Http_UploadFile_("http://trying-video.herokuapp.com/user/notice?token="+user.token, map,"14");
		Thread x=new Thread(htt);
		x.start();



	}
	public void system(View view)
	{
		/*提交新通知

POST   http://localhost:1103/user/notice?token=${token}
{
    "videoTitle" : ${videoTitle},    //视频名(String)
    "outlay" : ${outlay},    //支付收入数目(Number)
    "costTF" : ${costTF},    //花费 收入判断(Boolean)
    "operaTF" : ${operaTF},    //视频操作 或 花费判断(Boolean)
    "rmoveTF" : ${rmoveTF},    //上传 删除判断(Boolean)
    "IrrelevantTF" : ${IrrelevantTF},    //其他信息 或 相关信息判断(Boolean)
    "other" : ${other}    //其他信息(String)
}
>>  返回 message: '通知已更新'
获取用户全部通知
		 */
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("Context",Message_c.this);
		map.put("handler",mHandler);
		map.put("videoId",user.maps.get(0).get("_id"));
		map.put("outlay","100");
		map.put("kinds","2");//收入 支
		map.put("IrrelevantTF","0");
		map.put("other","nothing");
		SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
		Date curDate =  new Date(System.currentTimeMillis());
//获取当前时间
		String   str   =   formatter.format(curDate);

		map.put("other",str);

		Http_UploadFile_ htt=new Http_UploadFile_("http://trying-video.herokuapp.com/user/notice?token="+user.token, map,"14");
		Thread x=new Thread(htt);
		x.start();



	}

	public void datasSetary()
	{
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("Context",Message_c.this);
		
		map.put("handler",mHandler);
		
		Http_UploadFile_ htt=new Http_UploadFile_("http://trying-video.herokuapp.com/user/allnotices?token="+user.token, map,"15");
		Thread x=new Thread(htt);
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
	


	
}

