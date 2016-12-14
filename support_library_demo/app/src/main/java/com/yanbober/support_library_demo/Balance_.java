package com.yanbober.support_library_demo;

import android.content.*;
import android.os.*;
import android.support.v4.view.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.*;
import android.view.View.*;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.*;
import org.json.*;

import android.support.v7.widget.Toolbar;

import com.yanbober.support_library_demo.Http_Util.Http_UploadFile_;

public class Balance_ extends AppCompatActivity
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
					/*{
    "videoId" : ${videoId},    //视频ID(String)
    "outlay" : ${outlay},    //支付收入数目(Number)
    "costTF" : ${costTF},    //花费 收入判断(Boolean)
    "operaTF" : ${operaTF},    //视频操作 或 花费判断(Boolean)
    "rmoveTF" : ${rmoveTF},    //上传 删除判断(Boolean)
    "IrrelevantTF" : ${IrrelevantTF},    //其他信息 或 相关信息判断(Boolean)
    "other" : ${other}    //其他信息(String)
}
>>  返回 message: '通知已更新'
					 >>  返回全部通知
{
    {
        "_id" : "***",
        "videoId" : "***",
        "videoTitle" : "***",
        "payor" : "***",
        "payorId" : "***",
        "outlay" : ***,
        "costTF" : "***",
        "operaTF" : "***",
        "rmoveTF" : "***",
        "IrrelevantTF" : "***",
        "other" : "***"  //日期　和　时间
    },
    {...},
    ...*/

					try{
						if(bundle.getString("?").equals("获取成功"))
						{
							SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
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

			}
		}
	};
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
	TextView balances=null;
	int[] layout={R.layout.left_list_item,R.layout.line_item};
	User user=new User();
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
		balances=(TextView)findViewById(R.id.balance_layoutTextView);
		balances.setText(user.mydata.get("balance").toString());
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
		message=(ImageView)this.findViewById(R.id.activitymainTextView1);
		Message_point=(TextView)findViewById(R.id.tTextView);

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
		actionBar.setHomeAsUpIndicator(R.drawable.back_purple);//android.R.drawable.ic_dialog_alert);
		actionBar.setDisplayHomeAsUpEnabled(true);


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
	}
	public void datasSetary()
	{
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("context",Balance_.this);

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
	private void timejudge(String str)
	{
		int year=20, mon=1,day=1,hour=0,minu=0,con=0;




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



}
