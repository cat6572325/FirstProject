package com.yanbober.support_library_demo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.util.Base64;
import android.view.*;
import android.widget.*;

import com.yanbober.support_library_demo.Http_Util.Http_UploadFile_;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


public class ShareFragment extends Fragment {
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Bundle bundle=new Bundle();
			bundle=msg.getData();
			switch (msg.what) {
				case 0:
					break;
			}
		}
	};
	private RecyclerView mRecyclerView, rv;
	View v;
	public ArrayList<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>(), lists2 = new ArrayList<HashMap<String, Object>>(),lists3;
	FirstAdapter2 adapter, adapter2;
	Bitmap bitmap;
	MainActivity activity;

	long itemCount=0;
	private List<Integer> counttype = new ArrayList<>();
	private List<String> data = new ArrayList<>();
	LinearLayoutManager lm;
	int lastVisibleItem;
	Http_UploadFile_ http_uploadFile_=null;
	OnActivityebent2 ebent;
	LinearLayout linearLayouthide1;
	Context context;
	public static final AgendaFragment newInstance(String title,int resImageId)
	{
		AgendaFragment agendaFragment=new AgendaFragment();

		return agendaFragment;
	}
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.info_details_fragment, container, false);
		mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
		//rv=(RecyclerView)v.findViewById(R.id.groupRecyclerView2);
		//initView();
		linearLayouthide1=(LinearLayout)v.findViewById(R.id.HideLayout1);
		return v;
	}


	@TargetApi(Build.VERSION_CODES.M)
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter = new FirstAdapter2(getActivity(), lists);

		mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
		addTextToList("广东",1,"data",0,"bug太多，修复不了，好痛苦","ss");
		addTextToList("广东",1,"data",0,"再努力再努力再努力再努力再努力再努力再努力再努力","ss");
		addTextToList("广东",1,"data",0,"再努力再努力再努力再努力再努力再努力再努力再努力","ss");
		addTextToList("广东",1,"data",0,"再努力再努力再努力再努力再努力再努力再努力再努力","ss");
		addTextToList("广东",1,"data",0,"再努力再努力再努力再努力再努力再努力再努力再努力","ss");addTextToList("广东",1,"data",0,"再努力再努力再努力再努力再努力再努力再努力再努力","ss");
		addTextToList("广东",1,"data",0,"再努力再努力再努力再努力再努力再努力再努力再努力","ss");
		addTextToList("广东",1,"data",0,"再努力再努力再努力再努力再努力再努力再努力再努力","ss");
		addTextToList("广东",1,"data",0,"再努力再努力再努力再努力再努力再努力再努力再努力","ss");addTextToList("广东",1,"data",0,"再努力再努力再努力再努力再努力再努力再努力再努力","ss");addTextToList("广东",1,"data",0,"再努力再努力再努力再努力再努力再努力再努力再努力","ss");
		addTextToList("广东",1,"data",0,"再努力再努力再努力再努力再努力再努力再努力再努力","ss");
		addTextToList("广东",1,"data",0,"再努力再努力再努力再努力再努力再努力再努力再努力","ss");
		addTextToList("广东",1,"data",0,"再努力再努力再努力再努力再努力再努力再努力再努力","ss");
		addTextToList("广东",1,"data",0,"再努力再努力再努力再努力再努力再努力再努力再努力","ss");
		addTextToList("广东",1,"data",0,"再努力再努力再努力再努力再努力再努力再努力再努力","ss");
		addTextToList("广东",1,"data",0,"再努力再努力再努力再努力再努力再努力再努力再努力","ss");
		addTextToList("广东",1,"data",0,"再努力再努力再努力再努力再努力再努力再努力再努力","ss");



		addTextToList("广东",1,"data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊","ss");
		addTextToList("广东",1,"data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊","ss");
		addTextToList("广东",1,"data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊","ss");
		addTextToList("广东",1,"data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊","ss");
		addTextToList("广东",1,"data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊","ss");
		addTextToList("广东",1,"data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊","ss");
		addTextToList("广东",1,"data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊","ss");
		addTextToList("广东",1,"data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊","ss");
		addTextToList("广东",1,"data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊","ss");
		addTextToList("广东",1,"data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊","ss");
		addTextToList("广东",1,"data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊","ss");
		addTextToList("广东",1,"data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊","ss");
		addTextToList("广东",1,"data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊","ss");
		addTextToList("广东",1,"data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊","ss");
		addTextToList("广东",1,"data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊","ss");
		addTextToList("广东",1,"data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊","ss");
		addTextToList("广东",1,"data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊","ss");
		mRecyclerView.setAdapter(adapter);

		adapter.setOnClickListener(new FirstAdapter2.OnItemClickListener() {
			@Override
			public void onItemClickListener(View view, int position) {
				User user=new User();

				Toast.makeText(getActivity(), position + "========Click:", Toast.LENGTH_SHORT).show();
				//跳往Run_Video_播放视频
				Intent intent =new Intent(getContext(),Run_Video_.class);
				Bundle bundle=new Bundle();
				bundle.putString("count",String.valueOf(position));
				bundle.putString("vid",user.maps.get(position).get("_id").toString());
				//由于目前有一项内容是没有视频id所以会出错
				bundle.putString("url",user.maps.get(position).get("vdourl").toString());
				//count是为了定位到一开始时保存的视频列表中某一个视频的数据
				intent.putExtras(bundle);
				startActivity(intent);

			}

			@Override
			public void onItemLongClickListener(View view, int position) {
				Toast.makeText(getActivity(), position + "+++++++++LongClick:", Toast.LENGTH_LONG).show();

			}
		});
		//TODO 上拉加载更多

	/*	mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				lm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
				if (lm.findViewByPosition(lm.findFirstVisibleItemPosition()).getTop() == 0
						&&
						lm.findFirstVisibleItemPosition() == 0)


					Toast.makeText(getContext(), "last", Toast.LENGTH_LONG).show();
				/*	new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							List<String> newDatas = new ArrayList<String>();
							for (int i = 0; i < 5; i++) {
								int index = i + 1;
								newDatas.add("more item" + index);
							}
						//	addTextToList("uuu", 1, "android.R.drawable.ic_lock_lock", 0, "name");
							//mHandler.sendEmptyMessage(0);
						//	mRecyclerView.notifyAll();

							//// TODO: 16-11-13
							//	adapter.addMoreItem(newDatas);
							//adapter.changeMoreStatus(0);
						}
					}, 2500);
			}



				@Override
				public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
					super.onScrolled(recyclerView,dx, dy);
					lastVisibleItem = lm.findLastVisibleItemPosition();

				}
			});*/


	}

	public void addTextToList(String text, int who, String id, int count, String name,String url) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("person", who);
		map.put("image", id);
		map.put("data", text);
		map.put("count", count);
		map.put("layout", who);
		map.put("name", name);
		map.put("vdoPhotourl",url);
		lists.add(map);

	}

	public void addTextToList2(String text, int who, String id, int count, String name,String url) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("person", who);
		map.put("image", id);
		map.put("data", text);
		map.put("count", count);
		map.put("layout", who);
		map.put("name", name);
		map.put("vdoPhoto",url);
		lists2.add(map);
	}
	Bitmap bitmap2;
	String str1,str2,str3,biturl;User user=new User();
	private void initView()
	{


				for (int i = 0; i < user.maps.size(); i++) {
					if (i == 10) {
						return;
					}
					//addTextToList("广东",1,android.R.drawable.ic_lock_lock,"ps","data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊");
					addTextToList(user.maps.get(i).get("paidppnumber").toString()
							, 1
							, user.maps.get(i).get("vdourl").toString()
							, 0
							, user.maps.get(i).get("title").toString()
							, user.maps.get(i).get("vdoPhotourl").toString()
					);

				}
				adapter.notifyDataSetChanged();
		//  addTextToList("uuu", 1, "android.R.drawable.ic_lock_lock", 0, "name");
		// Toast.makeText(getContext(),str1,Toast.LENGTH_LONG).show();
		linearLayouthide1.setVisibility(View.INVISIBLE);

	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		//当切换到这个碎片时为true，没有viewpeger不会执行
		if (isVisibleToUser)
		{
			if (itemCount==0) {
				//initView();
				itemCount++;
			}else
			{

			}


		}else
		{

		}
	}
	public interface OnActivityebent2
	{
		public void Onebent2(String str);

	}
	public void Onebent2(ArrayList<HashMap<String, Object>> maps,Bitmap bitmap)  {//MainActivity接口

			lists3=maps;
		this.bitmap=bitmap;

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity=(MainActivity)activity;

	}



}
