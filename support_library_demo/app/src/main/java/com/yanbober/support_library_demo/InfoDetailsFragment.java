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
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.*;
import android.util.Base64;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.yanbober.support_library_demo.Http_Util.Http_UploadFile_;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


public class InfoDetailsFragment extends Fragment {
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
    private RecyclerView mRecyclerView, rv;
    View v;
    public ArrayList<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>(), lists2 = new ArrayList<HashMap<String, Object>>(), lists3,lists4 = new ArrayList<HashMap<String, Object>>();
    FirstAdapter2 adapter, adapter2;
    MainActivity activity;
    public static String[] urls;
    Bitmap bitmap;

    private List<Integer> counttype = new ArrayList<>();
    private List<String> data = new ArrayList<>();
    LinearLayoutManager lm;
    int lastVisibleItem;
    Http_UploadFile_ http_uploadFile_ = null;
    OnActivityebent ebent;
    LinearLayout linearLayouthide1;
    SwipeRefreshLayout swipeRefreshLayout;
    int itemCount=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.info_details_fragment, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        //rv=(RecyclerView)v.findViewById(R.id.groupRecyclerView2);
        linearLayouthide1 = (LinearLayout) v.findViewById(R.id.HideLayout1);
        swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.onReferesh);

        return v;
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new FirstAdapter2(getActivity(), lists);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
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
        // ebent.Onebent("aaaaaaaaaaaaaaaa");
        initView();
        adapter.setOnClickListener(new FirstAdapter2.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                try {
                    //跳往Run_Video_播放视频
                    JSONObject jsonObject;
                    Bundle bundle = new Bundle();
                    User user = new User();
                    if (user.maps.size() < 1) {
                        Toast.makeText(getActivity(), position + "数据加载未完成", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONArray jsonArraycollect = (JSONArray) user.maps.get(position).get("cocerPerson");
                        String iscollect = "1";
                        bundle.putString("iscollect", "1");//显示未收藏的星星
                        if (!user.phone.equals("null"))
                        for (int i = 0; i < jsonArraycollect.length(); i++) {
                            String str = jsonArraycollect.getString(i);
                            if (str.equals(user.mydata.get("_id").toString())) {//如果是本人收藏
                                iscollect = "0";
                                bundle.putString("iscollect", "0");//显示已收藏的星星
                            }
                            Log.e("付款人id", str);
                        }
                       /*

					>>  返回全部视频信息
                    {
                        {
                            "_id" : "***",    //视频id
                            "uploader" : "***",    //上传者
                            "title" : "***",    //标题
                            "introduction" : "***",    //简介
                            "price" : ***,    //价格
                            "paidPerson" : "***",    //付款人ID
                            "cocerPerson" : "***",    //收藏人名字
                            "paidppnumber" : ***,    //付款人数
                            "concernednumber" : ***    //收藏人数
                        },
                        {...},,*/
                        Intent intent = new Intent(getContext(), Run_Video_.class);
                        bundle.putString("count", String.valueOf(position));
                        bundle.putString("vid", user.maps.get(position).get("_id").toString());
                        bundle.putString("title", user.maps.get(position).get("title").toString());
                        bundle.putString("paidppnumber", user.maps.get(position).get("paidppnumber").toString());
                        //由于目前有一项内容是没有视频id所以会出错
                        bundle.putString("introduction", user.maps.get(position).get("introduction").toString());
                        bundle.putString("uploader", user.maps.get(position).get("uploader").toString());
                        bundle.putString("url", user.maps.get(position).get("vdourl").toString());
                        bundle.putString("vdoPhotourl", user.maps.get(position).get("vdoPhotourl").toString());
                        //count是为了定位到一开始时保存的视频列表中某一个视频的数据
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                } catch (JSONException e) {

                }
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

    public void addTextToList(String text, int who, String id, int count, String name, String url) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("person", who);
        map.put("image", id);
        map.put("data", text);
        map.put("count", count);
        map.put("layout", who);
        map.put("name", name);
        map.put("vdoPhotourl", url);


        lists.add(map);
    }

    public void addTextToList2(String text, int who, String id, int count, String name, String url) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("person", who);
        map.put("image", id);
        map.put("data", text);
        map.put("count", count);
        map.put("layout", who);
        map.put("name", name);
        map.put("vdoPhoto", url);
        lists2.add(map);
    }

    private void initView() {

        MainActivity mainActivity;
        mainActivity = (MainActivity) getActivity();
        Http_UploadFile_ http_uploadFile_ = new Http_UploadFile_(mainActivity
                , mainActivity.mHandler
                , "http://trying-video.herokuapp.com/user/video/all/detail"
                , "4"
                , "POST"
                , "getvideos");
        Thread t = new Thread(http_uploadFile_);
        //   t.start();
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0)//0停止　1开始滑动 2放手依旧滑动中
                {
                    lm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                    User u = new User();
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("?", "InfoDetailsFragment");

                    if (u.getBitmapurl != null) {
                        u.getBitmapurl.loadListViewImage(lm.findFirstVisibleItemPosition(), lm.findLastVisibleItemPosition(), mRecyclerView, getContext(), map);
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
                } else {
                    User u = new User();
                    if (u.getBitmapurl != null)
                        u.getBitmapurl.cancelAllTask();
                }



            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });

    }

    ArrayList<HashMap<String, Object>> map;
    Bitmap bitmap2 = null;
    public String urlbitmap = null;
    String str1, str2, str3;

    public interface OnActivityebent {
        public void Onebent(String str);

    }

    public void Onebent(ArrayList<HashMap<String, Object>> maps) {//MainActivity接口
        User u = new User();
        User user = new User();
        //通过获取maps中所有项中的paidppnumber的数字来进行繁琐的比较，并以从小到大的顺序加入到ui
        if (u.phone.equals("null")) {
            //如果未登录，则提醒一下
            Toast.makeText(getContext(), "当前未登录", Toast.LENGTH_SHORT).show();
        }
        if (u.maps.size() < 1) {
                Toast.makeText(getActivity(), "数据加载失败，请重新登录，如还是卡退，请清楚缓存再使用", Toast.LENGTH_SHORT).show();
            } else {
                lists2 = maps;
                lists3 = new ArrayList<>();
                urls = new String[user.maps.size()];
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
                   /* if (i == 10) {
                        addTextToList(str1
                                , 1
                                , "0"
                                , 0
                                , "9"
                                , "kk"
                        );
                        urls[i] = lists3.get(i).get("vdoPhotourl").toString();

                   // } else {
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
            if (adapter != null) {
                adapter.notifyDataSetChanged();
                //  addTextToList("uuu", 1, "android.R.drawable.ic_lock_lock", 0, "name");
                // Toast.makeText(getContext(),str1,Toast.LENGTH_LONG).show();
                linearLayouthide1.setVisibility(View.INVISIBLE);
            }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            if (adapter!=null)
                linearLayouthide1.setVisibility(View.INVISIBLE);
        }
    }

    public class thread implements Runnable {/////chat socket

        String str;

        public thread(String str) {

            this.str = str;

        }

        public void run() {
            try {

                URL url = new URL(str);

                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = new BufferedInputStream(conn.getInputStream());
                bitmap2 = BitmapFactory.decodeStream(is);// BitmapFactory.decodeStream(is);
                is.close();
                conn.disconnect();
            } catch (IOException e) {
                bitmap2 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.down);


            }
            //addTextToList("广东",1,android.R.drawable.ic_lock_lock,"ps","data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊");


            mHandler.sendEmptyMessage(2);

        }


        //暂定内容，参数....购买人数,布局,头像,是否显示红点,标题
        //头像和内容壁纸需要在适配器以二进制转为图片

    }

    private Bitmap decodeSampleBitmapFromStream(InputStream in, int with, int height) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        options.inSampleSize = caluSize(options, with, height);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(in, null, options);
    }

    private static int caluSize(BitmapFactory.Options options, int width, int height) {
        final int with = options.outWidth;
        final int heih = options.outHeight;
        int size = 1;
        if (heih > height || with > width) {
            final int hetgRatio = Math.round((float) heih / (float) height);
            final int widthRatio = Math.round((float) with / (float) width);
            size = hetgRatio < widthRatio ? hetgRatio : widthRatio;
        }
        return size;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            ebent = (OnActivityebent) activity;


        } catch (ClassCastException e) {

        }
    }

    public void setgroup(Context con, String[] group, String[] phones) {

        //  group | ps | imaheString ~ ...# phone | ps | imageString ~
        //split"\\
        String[] items;
        if (group != null)
            for (int i = 0; i < group.length; i++) {
                items = null;
                items = group[i].split("\\#");
                //group[i] =  "name # ps # imageString
                //  addTextToList(items[0], 0, "android.R.drawable.ic_lock_lock", 0, "name");
            }

        if (phones != null)
            for (int i = 0; i < phones.length; i++) {
                items = null;
                items = group[i].split("\\#");

                // addTextToList(items[0], 0, "android.R.drawable.ic_lock_lock", 0, "name");
            }

    }

}
