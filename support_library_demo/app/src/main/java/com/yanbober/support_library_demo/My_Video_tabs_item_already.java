package com.yanbober.support_library_demo;



/*
		这个类显示在My_Video_的一个tab中，显示的内容是表示用户已经上传了的视频


 */
import android.content.Intent;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.view.*;
import java.util.*;
import android.widget.*;

public class My_Video_tabs_item_already extends Fragment {
    private View mParentView;
	public ArrayList<HashMap<String,Object>> lists=new ArrayList<HashMap<String,Object>>();
	private List<Integer> counttype=new ArrayList<>();
	private List<String> data=new ArrayList<>();
	RelativeLayout rel;
	public FirstAdapter adapter;
	RelativeLayout rv;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.uploaded_tabs, container, false);
        return mParentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = (RecyclerView) mParentView.findViewById(R.id.uploaded_tabs_rv);
		rel=(RelativeLayout)mParentView.findViewById(R.id.my_video_tabs_al_rv_carditemRelativeLayout);
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		//mRecyclerView.addItemDecoration(new div

		// LinearLayoutManager manager = new LinearLayoutManager(mRecyclerView.getContext());
		// manager.setOrientation(2,LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
		//设置RecyclerView布局管理器为2列垂直排布
		//	addTextToList2("广东",2,android.R.drawable.ic_lock_lock,"ps","data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊");
		//addTextToList2("广东",1,android.R.drawable.ic_lock_lock,"ps","data",0,"这点略大，颜色不一样，标题栏缺少图标和点击效果，",0);
		addTextToList2("广东",4,android.R.drawable.ic_lock_lock,"ps","data",0,"这点略大，颜色不一样，标题栏缺少图标和点击效果，",1);


		for(int i=0;i<10;i++)
		{
			addTextToList2("广东"
						   ,4
						   ,android.R.drawable.ic_lock_lock,"ps"
						   ,"data",0
						   ,"这点略大，颜色不一样，标题栏缺少图标和点击效果，"
						   ,1
						   );
			
		}
		String str;
		adapter = new FirstAdapter(getActivity(),lists);

        mRecyclerView.setAdapter(adapter);
		adapter.setOnClickListener(new FirstAdapter.OnItemClickListener() {
			@Override
			public void onItemClickListener(View view, int position) {
				Intent intent=new Intent(getContext(),Run_Video_.class);
				startActivity(intent);
			}

			@Override
			public void onItemLongClickListener(View view, int position) {

			}
		});

    }

	public void addTextToList2(String text, int who, int id,String ps,String data,int person,String name,int is)
	{
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("person", person);
		map.put("image", id);
		map.put("text", text);
		map.put("is",is);
		map.put("ps", ps);
		map.put("data",data);
		map.put("layout",who);
		map.put("name",name);

		lists.add(map);
    }
	

}
