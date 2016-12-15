package com.yanbober.support_library_demo;

import android.content.*;
import android.os.*;
import android.support.v4.view.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.yanbober.support_library_demo.Http_Util.*;
import java.io.*;
import java.util.*;
import org.json.*;

import android.support.v7.widget.Toolbar;
import android.widget.AdapterView.*;

public class Collect_ extends AppCompatActivity {
	public Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0:
					try{/*
						 >>  返回全部收藏
						 {
						 {
						 "_id" : "***",
						 "collector" : "***",    // 收藏者
						 "author" : "***",    //作者
						 "videoTitle" : "***",    //视频名
						 "cost" : ***,    //支付费用
						 "vdo_id" : "***"    //视频id
						 },
						 {...},
						 ...*/
					//获取列表等详细数据
					Bundle bun=msg.getData();
					JSONArray jsa=(JSONArray)msg.obj;
						ArrayList<HashMap<String,Object>> listmap=new ArrayList<HashMap<String,Object>>();
				if(jsa.length()>0)
					for(int y=0;y<jsa.length();y++)
					{
						JSONObject jso=jsa.getJSONObject(y);
						Log.e("colllllll",jso.toString());
						HashMap<String,Object> map=new HashMap<String,Object>();
						map.put("_id",jso.getString("_id"));
						map.put("collector",jso.getString("collector"));
						map.put("auther",jso.getString("auther"));
						map.put("videoTitle",jso.getString("videoTitle"));
						map.put("cost",jso.getString("cost"));
						map.put("vdo_id",jso.getString("vdo_id"));
						listmap.add(map);
						
					}
					user.Collect_List=listmap;

						
					}catch(JSONException e)
					{
						
					}
					adapter.notifyDataSetChanged();
					 break;
					
				case 3:
						break;
					case 4:
						//验证支付密码成功
					Bundle bun=msg.getData();

						if(bun.getString("?").equals("继续下一步")) {

								//那么接下来就是看余额是否足够扣除,如果足够则发送信息，扣除视频的要求金额，然后加入已支付列表了
								//一系列动作获取了余额，然后减去cost

									//如果足够发送数据
									HashMap<String, Object> maphttp = new HashMap<String, Object>();
									ArrayList<HashMap<String, Object>> videos = user.maps;
									String video_id=bun.getString("video_id");
									String cost=null;
									for (int i=0;i<videos.size();i++)
									{
										if (video_id.equals(videos.get(i).get("_id")))
										{
												cost=videos.get(i).get("price").toString();
										}
									}
							if (cost.equals("null"))
							cost="0";
							Toast.makeText(Collect_.this,"该视频价格为　"+cost+" 元",Toast.LENGTH_SHORT).show();
									maphttp.put("cost",Integer.parseInt( cost));
									maphttp.put("handler", mHandler);
									maphttp.put("price",cost);
									Http_UploadFile_ htt = new Http_UploadFile_
											(
													"http://trying-video.herokuapp.com/user/pay/"+video_id+"?token=" + user.token
													, maphttp
													, "16");
									Thread c = new Thread(htt);
									c.start();





						}
						
						break;

				case 5:

					Bundle bundle=msg.getData();
					if (bundle.getString("?").equals("paidVideos change"))
				{

				}

						break;

			}
		}


	};

	MyChatAdapter ladapter;
	ListView rl;

	//Tab菜单，主界面上面的tab切换菜单
	int[] layout={R.layout.left_list_item,R.layout.line_item};
	ImageView menu_img;
	public ArrayList<HashMap<String,Object>> lists1=new ArrayList<HashMap<String,Object>>();
	ImageView message;
	
	TextView Message_point,User_name;
	RecyclerView rv=null;
	Toolbar tb;
	private DrawerLayout mDrawerLayout;
	User user=new User();

	ImageView xDheadImage;
	public ArrayList<HashMap<String,Object>> lists=new ArrayList<HashMap<String,Object>>();
	public FirstAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collect_layout);
		init();
	}
	public void init() {

		user.collect_ = null;
		user.collect_ = Collect_.this;
		try {
			rv = (RecyclerView)findViewById(R.id.collectrv);
			tb = (Toolbar) findViewById(R.id.paidToolbar);

			Message_point = (TextView)findViewById(R.id.tTextView);
			message = (ImageView)this.findViewById(R.id.activitymainTextView1);
			mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
			User u=new User();

			message.setOnClickListener(new View.OnClickListener()
				{

					public void onClick(View v) {
						Message_point.setVisibility(View.INVISIBLE);
						Intent intent=new Intent(Collect_.this, Message_c.class);
						startActivity(intent);

					}
				});
			//初始化ToolBar
			setSupportActionBar(tb);
			tb.setNavigationIcon(R.drawable.back_purple);
			tb.setNavigationOnClickListener(new OnClickListener() {//返回监听，按钮返回，不过不知道会回哪里
				public void onClick(View v) {
					onBackPressed();

				}

			});

			rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
			//设置RecyclerView布局管理器为1列垂直排布
			adapter = new FirstAdapter(Collect_.this, lists);
			rv.setAdapter(adapter);
			adapter.setOnClickListener(new FirstAdapter.OnItemClickListener() {
					@Override
					public void onItemClickListener(View view, int position) {
						
					}

					@Override
					public void onItemLongClickListener(View view, int position) {
						Toast.makeText(Collect_.this, position + "长按出现多选删除收藏功能尚未决定:", Toast.LENGTH_LONG).show();

					}
				});

		}
		catch (IOError error) {
		}
		try {
			JSONObject jsonObject=null;
			JSONArray jss= new JSONArray(user.mydata.get("notices").toString());

			if (jss.length() < 2) {
				Message_point.setVisibility(View.INVISIBLE);
			}
			else {


				Message_point.setText(String.valueOf(jss.length()));

				
			}
		}
		catch (JSONException e) {
			e.printStackTrace();
		}

		setData();
		//getcollect();
	}
	public void addTextToList(String text, int who, int id, String data, int isspot, int ispay,int s) {
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("image", id);
		map.put("text", text);
		map.put("isspot", isspot);//是否显示最新
		map.put("ispay", ispay);//是否显示支付按钮
		map.put("data", data);
		map.put("layout", who);
		map.put("$",s); //这个视频多少钱？
		lists.add(map);
    }
	public void addTextToList(String text, int who, int id) {
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("person", who);
		map.put("image", id);
		map.put("text", text);

		map.put("layout", who);

		lists1.add(map);
	}
	private class MyChatAdapter extends BaseAdapter {

		Context context=null;
		ArrayList<HashMap<String,Object>> chatList=null;
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
			public ImageView imageView=null;
			public TextView textView=null;
			public String title;
			private ImageView i_icon,i_icon2;
			public TextView T_title,T_red;
		}
		////////////

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder=null;
			final TextView tt;
			LinearLayout re;
			int who=(Integer)chatList.get(position).get("person");


			switch (who) {
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
							public void onClick(View view) {

							}
						});

					break;


			}
			return convertView;

		}
	}
	public void paythisVideoButton(View view)
	{//支付按钮


	}
	public void getcollect()
	{
		
		
		HashMap<String ,Object> map=new HashMap<String ,Object>();
		map.put("Context",Collect_.this);
		
		String url="http://trying-video.herokuapp.com/user/allcollect?token="+user.token;
		Http_UploadFile_ http=new Http_UploadFile_(url,map,"13");
		Thread xx=new Thread(http);
		xx.start();

	}
	public void testpaipwd()
	{


		HashMap<String ,Object> map=new HashMap<String ,Object>();
		map.put("context",Collect_.this);
		map.put("paypassword",user.mydata.get("paypassword").toString());
		String url="http://trying-video.herokuapp.com/user/oldpayword?token="+user.token;
		Http_UploadFile_ http=new Http_UploadFile_(url,map,"16");
		Thread xx=new Thread(http);
		xx.start();

	}
	public  void setData()
	{
	/*	>>  返回全部收藏
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
		//如果收藏表不为空则加载
		ArrayList<HashMap<String,Object>> listmap=user.Collect_List;
		if (listmap!=null) {
			for (int i=0;i<listmap.size();i++)
			{
				//判断是否已付费，设置已购买人数，设置金额
				addTextToList(listmap.get(i).get("videoTitle").toString(),3,R.drawable.qq
						,"购买人数"//listmap.get(i).get("购买人数？")+"付款"
						,0,0
				,10);

			}
		}


		/*addTextToList("本 拉登教你打仗",3,R.drawable.qq,"901人付款",0,0);
		addTextToList("King arthur",3,R.drawable.qq,"901人付款",1,0);
		addTextToList("高文",3,R.drawable.qq,"901人付款",0,1);
		addTextToList("lancelot",3,R.drawable.qq,"901人付款",1,1);*/

	}
	
	
}

