package com.yanbober.support_library_demo;

import android.annotation.TargetApi;
import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import java.util.*;


public class InfoDetailsFragment extends Fragment {
    private RecyclerView mRecyclerView,rv;
	View v;
	public ArrayList<HashMap<String,Object>> lists=new ArrayList<HashMap<String,Object>>(), lists2=new ArrayList<HashMap<String,Object>>();
	FirstAdapter adapter,adapter2;
	MainActivity activity;
	User user=new User();
	private List<Integer> counttype=new ArrayList<>();
	private List<String> data=new ArrayList<>();
	LinearLayoutManager lm;
	int lastVisibleItem;

	@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     v  =  inflater.inflate(R.layout.info_details_fragment, container, false);
		mRecyclerView = (RecyclerView)v.findViewById(R.id.recycler_view);
		//rv=(RecyclerView)v.findViewById(R.id.groupRecyclerView2);
		
        return v;
    }
	private Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
				case 0:
		
					break;
					
					
					}
					}
					};
    @TargetApi(Build.VERSION_CODES.M)
	@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
		for(int i=0;i<10;i++)
		{
			//addTextToList("广东",1,android.R.drawable.ic_lock_lock,"ps","data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊");
			
		}
		addTextToList("uuu",1,"android.R.drawable.ic_lock_lock",0,"name");
		
		adapter=new FirstAdapter(getActivity(),lists);
		
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
		
		
        mRecyclerView.setAdapter(adapter);
		adapter.setmOnItemClickListener(new FirstAdapter.OnItemClickListener() {
				@Override
				public void onItemClickListener(View view, int position) {
					Toast.makeText(getActivity(),position+"========Click:",Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onItemLongClickListener(View view, int position) {
					Toast.makeText(getActivity(),position+"+++++++++LongClick:",Toast.LENGTH_LONG).show();

				}
		});
		//TODO 上拉加载更多

		mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				lm= (LinearLayoutManager) mRecyclerView.getLayoutManager();
				if (newState == RecyclerView.SCROLL_STATE_IDLE
				&& lastVisibleItem + 1 == adapter.getItemCount()) {    //adapter.changeMoreStatus(0);
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							List<String> newDatas = new ArrayList<String>();
							for (int i = 0; i < 5; i++) {
								int index = i + 1;
								newDatas.add("more item" + index);
							}
							addTextToList("uuu", 1, "android.R.drawable.ic_lock_lock", 0, "name");
							//mHandler.sendEmptyMessage(0);
							mRecyclerView.notifyAll();
							
							//// TODO: 16-11-13  
							//	adapter.addMoreItem(newDatas);
							//adapter.changeMoreStatus(0);
						}
					}, 2500);
				}


			}
				@Override
				public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
					super.onScrolled(recyclerView,dx, dy);
					lastVisibleItem = lm.findLastVisibleItemPosition();

				}
			});




			}
	public void addTextToList(String text, int who, String id, int count,String name)
	{
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("person", who);
		map.put("image", id);
		map.put("data", text);
		map.put("count", count);
		map.put("layout",who);
		map.put("name",name);
		
		lists.add(map);
		
    }
	public void addTextToList2(String text, int who, String id, int count,String name)
	{
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("person", who);
		map.put("image", id);
		map.put("data", text);
		map.put("count", count);
		map.put("layout",who);
		map.put("name",name);
		
		lists2.add(map);
    }
	public void setgroup(Context con,String[] group,String[] phones)
	{
		
		//  group | ps | imaheString ~ ...# phone | ps | imageString ~
		//split"\\
		String[] items;
		if(group!=null)
		for(int i=0;i<group.length;i++)
		{
			items=null;
			items=group[i].split("\\#");
			//group[i] =  "name # ps # imageString
			addTextToList(items[0],0,"android.R.drawable.ic_lock_lock",0,"name");
		}
		
		if(phones!=null)
		for(int i=0;i<phones.length;i++)
		{
			items=null;
			items=group[i].split("\\#");
			
		addTextToList(items[0],0,"android.R.drawable.ic_lock_lock",0,"name");
		}
		
		}
	
}
