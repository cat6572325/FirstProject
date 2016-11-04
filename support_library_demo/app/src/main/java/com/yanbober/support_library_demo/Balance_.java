package com.yanbober.support_library_demo;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.yanbober.support_library_demo.*;
import java.util.*;

public class Balance_ extends Activity
{
	HashMap<String,Object> map=new HashMap<String,Object>();
	ArrayList<String> alist;
	ExpandableListView exlv=null;
	String[] groups;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.balance_layout);
		exlv=(ExpandableListView)findViewById(R.id.balance_layoutExpandableListView);
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
		
	}
	
	public void addTextToList(ArrayList<String> list,String name)
	{
		
		
		map.put(name,list);

		
    }
	
	//自定义适配器
	class Adapter extends BaseExpandableListAdapter{
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
				//childholder.mImage = (ImageView) view.findViewById(R.id.image);
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
			}else{
				view = View.inflate(Balance_.this,R.layout.balance_group_item, null);
				groupholder =new GroupHolder();
				
				//加载父布局
				view.setTag(groupholder);
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
	}

	static class ChildHolder{
		ImageView mImage;
		TextView mStateText;
		TextView mPrice;
		TextView mSecondPrice;
	}
	
	
}
