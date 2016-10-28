package com.yanbober.support_library_demo;

import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.view.*;
import java.util.*;
import android.widget.*;

/**
 * Author       : yanbo
 * Date         : 2015-06-01
 * Time         : 15:09
 * Description  :
 */
public class ShareFragment extends Fragment {
    private View mParentView;
	public ArrayList<HashMap<String,Object>> lists=new ArrayList<HashMap<String,Object>>();
	private List<Integer> counttype=new ArrayList<>();
	private List<String> data=new ArrayList<>();
	
	public MyreclerAdapter adapter;
	RelativeLayout rv;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.share_fragment, container, false);
        return mParentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = (RecyclerView) mParentView.findViewById(R.id.recycler_view);
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		 //mRecyclerView.addItemDecoration(new div
       
       // LinearLayoutManager manager = new LinearLayoutManager(mRecyclerView.getContext());
       // manager.setOrientation(2,LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
		//设置RecyclerView布局管理器为2列垂直排布
	//	addTextToList2("广东",2,android.R.drawable.ic_lock_lock,"ps","data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊");
		addTextToList2("广东",1,android.R.drawable.ic_lock_lock,"ps","data",0,"这点略大，颜色不一样，标题栏缺少图标和点击效果，");
		addTextToList2("广东",2,android.R.drawable.ic_lock_lock,"ps","data",0,"这点略大，颜色不一样，标题栏缺少图标和点击效果，");
		
		
		for(int i=0;i<android.R.drawable.class.getDeclaredFields().length;i++)
		{
			addTextToList2("广东"
			,0
			,android.R.drawable.ic_lock_lock,"ps"
			,"data",0
			,"这点略大，颜色不一样，标题栏缺少图标和点击效果，"
			);
			counttype.add(R.drawable.icon_people);
			data.add("uuu");
		}
		String str;
		
		adapter = new MyreclerAdapter(getActivity(),lists,data,counttype);
		
        mRecyclerView.setAdapter(adapter);
		ThreadEx ex=new ThreadEx((MainActivity)getActivity(),"getshare");
		Thread x=new Thread(ex);
		x.start();
		
		
    }
	public void addTextToList3(String text, int who, String id,String ps,String data,int person,String name)
	{
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("person", person);
		map.put("image", id);
		map.put("text", text);
		map.put("ps", ps);
		map.put("data",data);
		map.put("layout",who);
		map.put("name",name);
		lists.add(map);
    }
	public void addTextToList2(String text, int who, int id,String ps,String data,int person,String name)
	{
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("person", person);
		map.put("image", id);
		map.put("text", text);
		map.put("ps", ps);
		map.put("data",data);
		map.put("layout",who);
		map.put("name",name);

		lists.add(map);
    }
	public void addTextToList(String text, int who, String id,String ps,String data,int person,String name)
	{
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("person", person);
		map.put("image", id);
		map.put("text", text);
		map.put("ps", ps);
		map.put("data",data);
		map.put("layout",who);
		map.put("name",name);
		lists.add(map);
    }
	
}
