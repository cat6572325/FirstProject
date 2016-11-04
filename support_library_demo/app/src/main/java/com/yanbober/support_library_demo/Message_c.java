package com.yanbober.support_library_demo;

import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import java.util.*;

import android.support.v7.widget.Toolbar;

public class Message_c extends AppCompatActivity
{
	MyChatAdapter ladapter;
	int[] layout={R.layout.message_item,R.layout.line_item};
ListView lv=null;
Toolbar tb=null;
	public ArrayList<HashMap<String,Object>> lists=new ArrayList<HashMap<String,Object>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_layout);
		lv=(ListView)findViewById(R.id.message_layoutListView);
		tb=(Toolbar)findViewById(R.id.messageToolbar);
		addTextToList("King arthur payment $3 to your of video","september13",0,R.drawable.image);
		
		
		ladapter=new MyChatAdapter(Message_c.this,lists,layout);
		lv.setAdapter(ladapter);
		
		setSupportActionBar(tb);
		
		tb.setNavigationIcon(R.drawable.menu);
	}
	public void addTextToList(String text, String time,int who,int id)
	{
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("person", who);
		map.put("image", id);
		map.put("text", text);
		map.put("time",time);
		map.put("layout",who);

		lists.add(map);
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
					ImageView img=(ImageView)convertView.findViewById(R.id.messageitemImageView1);
					TextView tv=(TextView)convertView.findViewById(R.id.messageitemTextView2);
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
}

