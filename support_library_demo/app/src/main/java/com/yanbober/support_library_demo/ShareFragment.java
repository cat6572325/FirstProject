package com.yanbober.support_library_demo;

import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.view.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import android.widget.*;
import com.yanbober.support_library_demo.Http_Util.*;
import com.yanbober.support_library_demo.Custom.RoundProgressBar;
/**
 * Author       : yanbo
 * Date         : 2015-06-01
 * Time         : 15:09
 * Description  :
 */
public class ShareFragment extends Fragment {

	public Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
				case 0:
					Bundle bun=new Bundle();
					bun=msg.getData();

					break;


			}
		}
	};
	private View mParentView;
	public ArrayList<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>();
	private List<Integer> counttype = new ArrayList<>();
	private List<String> data = new ArrayList<>();
	LinearLayoutManager lm;
	public FirstAdapter adapter;
	RelativeLayout rv;
	private RecyclerView mRecyclerView;
	Get_LastData_Util httpUtil;
	User user=new User();
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
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
		//设置RecyclerView布局管理器为2列垂直排布
		//	addTextToList2("广东",2,android.R.drawable.ic_lock_lock,"ps","data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊");
		//addTextToList2("广东",1,android.R.drawable.ic_lock_lock,"ps","data",0,"这点略大，颜色不一样，标题栏缺少图标和点击效果，",0);
	/*	addTextToList2("广东", 0, android.R.drawable.ic_lock_lock, "ps", "data", 0, "这点略大，颜色不一样，标题栏缺少图标和点击效果，", 1);
		for (int i = 0; i < 10; i++) {
			addTextToList2("广东"
					, 0
					, android.R.drawable.ic_lock_lock, "ps"
					, "data", 0
					, "这点略大，颜色不一样，标题栏缺少图标和点击效果，"
					, 0
			);
			counttype.add(R.drawable.icon_people);
			data.add("uuu");
		}*/
		String str;
		adapter = new FirstAdapter(getActivity(), lists);
		mRecyclerView.setAdapter(adapter);
		ThreadEx ex = new ThreadEx((MainActivity) getActivity(), "getshare");
		Thread x = new Thread(ex);
		x.start();
		mRecyclerView.setOnScrollListener(new OnRcvScrollListener(){
			@Override
			public void onBottom() {
				super.onBottom();
				// 到底部自动加载
				Toast.makeText(getContext(),"last",Toast.LENGTH_LONG).show();
			}
		});
initView();

    }
public void initView()
{
	if (user.maps!=null) {
		ArrayList<HashMap<String, Object>> map = user.maps;
		for (int i = 0; i < map.size(); i++) {
			if (i == 10) {
				return;
			}
			//addTextToList("广东",1,android.R.drawable.ic_lock_lock,"ps","data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊");
			addTextToList(map.get(i).get("paidppnumber").toString()
					, 1
					, map.get(i).get("vdourl").toString()
					, 0
					, map.get(i).get("title").toString());

			//暂定内容，参数....购买人数,布局,头像,是否显示红点,标题
			//头像和内容壁纸需要在适配器以二进制转为图片
		}
		adapter.notifyDataSetChanged();
		//  addTextToList("uuu", 1, "android.R.drawable.ic_lock_lock", 0, "name");
		// Toast.makeText(getContext(),str1,Toast.LENGTH_LONG).show();
	}
}
	public void addTextToList(String text, int who, String id, int count, String name) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("person", who);
		map.put("image", id);
		map.put("data", text);
		map.put("count", count);
		map.put("layout", who);
		map.put("name", name);

		lists.add(map);

	}


	public void Onebent(ArrayList<HashMap<String, Object>> maps)
	{//MainActivity接口
		ArrayList<HashMap<String, Object>> map=maps;
		for (int i = 0; i <map.size(); i++) {
			if (i==10)
				return ;
			//addTextToList("广东",1,android.R.drawable.ic_lock_lock,"ps","data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊");
			addTextToList(map.get(i).get("paidppnumber").toString()
					, 1
					,map.get(i).get("vdourl").toString()
					, 0
					,map.get(i).get("title").toString());

			//暂定内容，参数....购买人数,布局,头像,是否显示红点,标题
			//头像和内容壁纸需要在适配器以二进制转为图片
		}
		adapter.notifyDataSetChanged();
		//  addTextToList("uuu", 1, "android.R.drawable.ic_lock_lock", 0, "name");
		// Toast.makeText(getContext(),str1,Toast.LENGTH_LONG).show();

	}
}
