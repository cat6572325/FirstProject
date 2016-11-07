package com.yanbober.support_library_demo;




/**




***/




import android.content.*;
import android.graphics.*;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.AdapterView.*;
import java.util.*;

import android.view.View.OnClickListener;

public class Setting_ extends AppCompatActivity
{
	ListView rl;
	MyChatAdapter ladapter;
	int[] layout={R.layout.setting_list_item,R.layout.line_item};


	public ArrayList<HashMap<String,Object>> lists=new ArrayList<HashMap<String,Object>>();


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_);
		initView();
	}/////onCreate

	public void initView()
	{
		rl = (ListView)this.findViewById(R.id.tRecyclerView1);
		addTextToList("夜愿", "昵称",0, R.drawable.home,0);
		
		addTextToList("分割贱","没有", 1, R.drawable.fab_bg_normal,0);
	
		addTextToList("***","登陆密码", 0, R.drawable.home,1);
		addTextToList("***","支付密码", 0, R.drawable.home,1);
		
		
		
			ladapter = new MyChatAdapter(Setting_.this, lists, layout);
		rl.setAdapter(ladapter);
		rl.setOnItemClickListener(new OnItemClickListener(){
				public void onItemClick(AdapterView<?> parent, View view, int position, long id)
				{
					TextView tv=(TextView)view.findViewById(R.id.settinglistitemTextView1);
					String str=tv.getText().toString();
					if (str.equals("昵称"))
					{
						Intent intent=new Intent(Setting_.this,Modify_Name_.class);
						startActivity(intent);
					}
					if (str.equals("登陆密码"))
					{
						Intent intent=new Intent(Setting_.this,Modify_Password_.class);
						startActivity(intent);
					}
					if (str.equals("支付密码"))
					{
						HashMap<String,Object> 	map=new HashMap<String,Object>();
						map.put("isprogress",0);
						
						Pop_Img.Builder p=new Pop_Img.Builder(Setting_.this,map);
						p.setPositiveButton("[潮汕揭]初版\n问题反馈:(qq) 1213965634\n\n", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which)
								{
									dialog.dismiss();
									//login
									//Intent intent=new Intent(Setting_.this,Set_Pay_pwd_.class);
									//startActivity(intent);
									HashMap<String,Object> 	map1=new HashMap<String,Object>();
									map1.put("isprogress",1);
									map1.put("progress",25);
									map1.put("max",100);
								map1.put("color",Color.rgb(555,333,333));
									Pop_Img.Builder p1=new Pop_Img.Builder(Setting_.this,map1);
									p1.create().show();
									
									
								}
								//设置你的操作事项


							});

						p.create().show();
					}
					
				}
			});

	}
	public void addTextToList(String text,String name,int who, int id,int isarrow)
	{
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("who", who);
		map.put("image", id);
		map.put("text", text);
		map.put("name",name);
		map.put("isarrow",isarrow);
		map.put("layout", who);

		lists.add(map);
    }///addtextToList


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
			public TextView name=null,data=null,flag;
        }
        ////////////

        public View getView(int position, View convertView, ViewGroup parent)
		{
			// TODO Auto-generated method stub
			ViewHolder holder=new ViewHolder();
			
			int who=(Integer)chatList.get(position).get("who");


			switch (who)
			{
				case 0:
				
					convertView = LayoutInflater.from(context).inflate(
						layout[who], null);
						holder.name=(TextView)convertView.findViewById(R.id.settinglistitemTextView1);
					holder.data=(TextView)convertView.findViewById(R.id.settinglistitemTextView2);
					holder.flag=(TextView)convertView.findViewById(R.id.settinglistitemTextView3);
					
						
					if((Integer)chatList.get(position).get("isarrow")==1)
					{//是否显示右边箭头
						holder.flag.setVisibility(View.VISIBLE);
					}
					holder.data.setText((String)chatList.get(position).get("text"));
					holder.name.setText((String)chatList.get(position).get("name"));
					break;
				case 1:
					isEnabled(position);
					/////分隔线，挺大条的，但无伤大雅
					convertView = LayoutInflater.from(context).inflate(
						layout[who], null);
					View v=(View)convertView.findViewById(R.id.lineitemView1);
					v.setVisibility(View.INVISIBLE);
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
	}///////MyChatAdapter


}
