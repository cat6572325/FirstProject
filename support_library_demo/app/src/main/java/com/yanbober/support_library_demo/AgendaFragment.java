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
import android.util.Log;
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


public class AgendaFragment extends Fragment{

    private RecyclerView mRecyclerView, rv;

    View v;
    public static String[] urls;
    public ArrayList<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>(), lists2 = new ArrayList<HashMap<String, Object>>(), lists3;
    FirstAdapter2 adapter, adapter2;
    Bitmap bitmap;
    MainActivity activity;
    LinearLayout linearLayouthide1;

    private List<Integer> counttype = new ArrayList<>();
    private List<String> data = new ArrayList<>();
    LinearLayoutManager lm;
    int start,end;
    Http_UploadFile_ http_uploadFile_ = null;
    OnActivityebent2 ebent;
    long itemCount = 0;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.info_details_fragment, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        linearLayouthide1 = (LinearLayout) v.findViewById(R.id.HideLayout1);


        return v;
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Toast.makeText(activity, "ss", Toast.LENGTH_LONG).show();
/*
        addTextToList("sss"
                , 1
                ,"dddddddddd"
                , 0
                , "nonooooo"
                , "dddddddddddd"
        );

        addTextToList("sss"
                , 1
                ,"dddddddddd"
                , 0
                , "nonooooo"
                , "dddddddddddd"
        );

        addTextToList("sss"
                , 1
                ,"dddddddddd"
                , 0
                , "nonooooo"
                , "dddddddddddd"
        );

        addTextToList("sss"
                , 1
                ,"dddddddddd"
                , 0
                , "nonooooo"
                , "dddddddddddd"
        );

        addTextToList("sss"
                , 1
                ,"dddddddddd"
                , 0
                , "nonooooo"
                , "dddddddddddd"
        );

        addTextToList("sss"
                , 1
                ,"dddddddddd"
                , 0
                , "nonooooo"
                , "dddddddddddd"
        );

        addTextToList("sss"
                , 1
                ,"dddddddddd"
                , 0
                , "nonooooo"
                , "dddddddddddd"
        );


        addTextToList("sss"
                , 1
                ,"dddddddddd"
                , 0
                , "nonooooo"
                , "dddddddddddd"
        );*/
        adapter = new FirstAdapter2(getActivity(), lists);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
                mRecyclerView.setAdapter(adapter);
        // ebent.Onebent("aaaaaaaaaaaaaaaa");
        adapter.setOnClickListener(new FirstAdapter2.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Toast.makeText(getActivity(), position + "========Click:", Toast.LENGTH_SHORT).show();
                User user = new User();
                //跳往Run_Video_播放视频
                Intent intent = new Intent(getContext(), Run_Video_.class);
                Bundle bundle = new Bundle();
                bundle.putString("count", String.valueOf(position));
                bundle.putString("vid", user.maps.get(position).get("_id").toString());
                //由于目前有一项内容是没有视频id所以会出错
                bundle.putString("url", user.maps.get(position).get("vdourl").toString());
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
        /*mRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                Log.e("i",""+i);
                Log.e("i1",""+i1);
                Log.e("i2",""+i2);
                Log.e("i3",""+i3);
            }
        });*/
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

				}
			});


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

    Bitmap bitmap2;
    String str1, str2, str3, biturl;
    User user = new User();

    private void initView() {

        urls=new String[user.maps.size()];
        for (int i = 0; i < user.maps.size(); i++) {
            if (i == 10) {
                return;
            }
            urls[i]=user.maps.get(i).get("vdoPhotourl").toString();
            //将加载的所有图片url都保存到数组,供GetBitmapurl调用

            //addTextToList("广东",1,android.R.drawable.ic_lock_lock,"ps","data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊");
            addTextToList(user.maps.get(i).get("paidppnumber").toString()
                    , 1
                    , user.maps.get(i).get("vdourl").toString()
                    , 0
                    , user.maps.get(i).get("title").toString()
                    , user.maps.get(i).get("vdoPhotourl").toString()
            );

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
            if (itemCount==0)
            {
            initView();
                itemCount++;
            }else {

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



    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=(MainActivity)activity;

    }



}
