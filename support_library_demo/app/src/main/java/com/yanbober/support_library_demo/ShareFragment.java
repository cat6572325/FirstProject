package com.yanbober.support_library_demo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.*;
import android.support.annotation.*;
import android.support.design.widget.Snackbar;
import android.support.v4.app.*;
import android.support.v4.widget.SwipeRefreshLayout;
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
			Bundle bundle = new Bundle();
			bundle = msg.getData();
			switch (msg.what) {
				case 0:
					//更新
					adapter.notifyDataSetChanged();

					break;
				case 1:
					//停止刷新
					swipeRefreshLayout.setRefreshing(false);
					break;
			}
		}
	};
	SwipeRefreshLayout swipeRefreshLayout;

	private RecyclerView mRecyclerView, rv;
	View v;
	public ArrayList<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>()
			, lists2 = new ArrayList<HashMap<String, Object>>(),lists3
			,lists4=new ArrayList<>();
	FirstAdapter2 adapter, adapter2;
	Bitmap bitmap;
	MainActivity activity;
	public static String[] urls;
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
		swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.onReferesh);
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
		final GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),1);
		gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
			@Override
			public int getSpanSize(int position) {
				//设置混排

				int type= adapter.getItemViewType(position);
				int Back=1;
				if (position>11) {
					Back = gridLayoutManager.getSpanCount();;
				}else {
					switch (type) {
						case 1:
							//一条的
							Back = gridLayoutManager.getSpanCount();
							break;
					}
				}
				return Back;
			}
		});
		mRecyclerView.setLayoutManager(gridLayoutManager);

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

		mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if (newState==0)//0停止　1开始滑动 2放手依旧滑动中
				{
					lm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
					User u=new User();
					HashMap<String,Object> map=new HashMap<String, Object>();
					map.put("?","AgendaFragment");
					if (u.getBitmapurl!=null) {
						u.getBitmapurl.loadListViewImage(lm.findFirstVisibleItemPosition(), lm.findLastVisibleItemPosition(),mRecyclerView,getContext(),map);
					}
					int lastviewitem=lm.findLastCompletelyVisibleItemPosition();
					int totallcount=lm.getItemCount();
					if (lastviewitem==(totallcount-1))
					{
						Snackbar.make(recyclerView, "已加载全部内容", Snackbar.LENGTH_SHORT).show();
					}
					if (swipeRefreshLayout.isRefreshing())
					{
						mHandler.sendEmptyMessage(1);
						//关闭刷新
					}
				}else
				{
					User u=new User();
					if (u.getBitmapurl!=null)
						u.getBitmapurl.cancelAllTask();
				}
			}


				@Override
				public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
					super.onScrolled(recyclerView,dx, dy);
				//	lastVisibleItem = lm.findLastVisibleItemPosition();

				}
			});


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

		User u = new User();
		String urlbitmap;
		ArrayList<HashMap<String,Object>> maps=new ArrayList<>();
		maps.addAll(u.maps);
		if (u.maps.size()>1) {
			lists2 = maps;
			lists3 = new ArrayList<>();
			urls = new String[u.maps.size()];
			ArrayList<HashMap<String, Object>> maps_list = new ArrayList<>();
			int mind;
			boolean flag = false;
			int size = u.maps.size();

			//首先把为null的都随机放入lists3
			//然后将不为null的放入maps_list然后再转交给lists2
			//循环如果lists2大小
			//比较其中一个是否是里面最小的，如果是就放入lists3并删除lists2中这个项然后结束
			//再继续循环比较第二个，是否是里面最小的．直到lists2为0

			for (int c = 0; c < size; c++)//13/
			{//首先把未null的所有项加入lists3然后从lists2中删除
				if (lists2.get(c).get("paidppnumber").toString().equals("null")) {
					lists3.add(lists2.get(c));//为null
				} else {
					maps_list.add(lists2.get(c));//不null
				}
			}

			// lists2.clear();
			lists4.addAll(maps_list);
			//  u.maps.add(lists2.get(0));
			while (lists4.size() != 0) {
				for (int i = 0; i < lists4.size(); i++) {
					mind = Integer.parseInt(lists4.get(i).get("paidppnumber").toString());
					for (int y = 0; y < lists4.size(); y++) {
						if (mind > Integer.parseInt(lists4.get(i).get("paidppnumber").toString())) {
							break;
						} else {//<=
							flag = false;
						}
						if (mind == Integer.parseInt(lists4.get(i).get("paidppnumber").toString())) {//不管是不是等于都直接放入lists3
							lists3.add(lists4.get(i));
							lists4.remove(i);
							break;
						} else {
							lists3.add(lists4.get(i));
							lists4.remove(i);
							break;
						}

					}
					break;
				}
			}

			//  u.maps.add(lists3.get(0));
			//  u.maps.addAll(lists3);
			///比较的结果将所有的paidppnumber数值以从小到大排序
			//由于里面有null,,,
			for (int i = 0; i < lists3.size(); i++) {
				//所有数据以付款人数排列完毕，然后在这里开始加载
              /*   //if (i == 10) {
                    addTextToList(str1
                            , 1
                            , "0"
                            , 0
                            , "9"
                            , "kk"
                    );
                    urls[i] = lists3.get(i).get("vdoPhotourl").toString();

               } else {
                    if (i > 10) {
                        addTextToList(str1
                                , 0
                                , "示例"
                                , 0
                                , "9"
                                , "示例"
                        );
                        urls[i] = lists3.get(i).get("vdoPhotourl").toString();

                    }
*/
				urls[i] = lists3.get(i).get("vdoPhotourl").toString();


				urlbitmap = lists3.get(i).get("vdoPhotourl").toString();
				str1 = lists3.get(i).get("paidppnumber").toString();
				if (str1.equals("null")) {
					str1 = null;
					str1 = "0";
				}
				str2 = lists3.get(i).get("vdourl").toString();
				str3 = lists3.get(i).get("title").toString();

				addTextToList(str1
						, 0
						, str2
						, 0
						, str3
						, urlbitmap
				);
			}

		}





		//暂定内容，参数....购买人数,布局,头像,是否显示红点,标题
		//头像和内容壁纸需要在适配器以二进制转为图片

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
				initView();
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
