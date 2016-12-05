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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    MainActivity activity;
    Bitmap bitmap;
    User user = new User();
    private List<Integer> counttype = new ArrayList<>();
    private List<String> data = new ArrayList<>();
    LinearLayoutManager lm;
    int lastVisibleItem;
    Http_UploadFile_ http_uploadFile_=null;
    OnActivityebent ebent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.info_details_fragment, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        //rv=(RecyclerView)v.findViewById(R.id.groupRecyclerView2);

        return v;
    }



    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new FirstAdapter2(getActivity(), lists);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

        mRecyclerView.setAdapter(adapter);
       // ebent.Onebent("aaaaaaaaaaaaaaaa");
initView();
        adapter.setOnClickListener(new FirstAdapter2.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                try {
                    Toast.makeText(getActivity(), position + "========Click:", Toast.LENGTH_SHORT).show();
                    //跳往Run_Video_播放视频
                    JSONObject jsonObject;
                    Bundle bundle = new Bundle();

                    JSONArray jsonArraycollect = (JSONArray)user.maps.get(position).get("paidPerson");
                    String iscollect="1";
                    bundle.putString("iscollect","1");//显示未收藏的星星
                    for (int i=0;i<jsonArraycollect.length();i++)
                    {
                        String str=jsonArraycollect.getString(i);
                        if (str.equals(user.mydata.get("_id").toString()))
                        {//如果是本人收藏
                            iscollect="0";
                            bundle.putString("iscollect","0");//显示已收藏的星星

                        }
                        Log.e("付款人id",str);
                    }
                       /*
					视频列表缺少收藏者id支付者id
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
                    bundle.putString("title",user.maps.get(position).get("title").toString());
                    bundle.putString("paidppnumber",user.maps.get(position).get("paidppnumber").toString());
                    //由于目前有一项内容是没有视频id所以会出错
                    bundle.putString("introduction",user.maps.get(position).get("introduction").toString());
                    bundle.putString("uploader",user.maps.get(position).get("uploader").toString());
                    bundle.putString("url", user.maps.get(position).get("vdourl").toString());
                    //count是为了定位到一开始时保存的视频列表中某一个视频的数据
                    intent.putExtras(bundle);
                    startActivity(intent);
                }catch (JSONException e)
                {

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

    public void addTextToList(String text, int who, String id, int count, String name,Bitmap url) {
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
    private void initView()
    {

            MainActivity mainActivity;
            mainActivity= (MainActivity)getActivity();
            Http_UploadFile_ http_uploadFile_ = new Http_UploadFile_(mainActivity
                    ,mainActivity.mHandler
                    ,"http://trying-video.herokuapp.com/user/video/all/detail"
                    ,"4"
                    ,"POST"
                    ,"getvideos");
        Thread t=new Thread(http_uploadFile_);
     //   t.start();

        }

public interface OnActivityebent
    {
        public void Onebent(String str);

    }
public void Onebent(ArrayList<HashMap<String, Object>> maps)  {//MainActivity接口

    ArrayList<HashMap<String, Object>> map=maps;
    Bitmap bitmap2=null;

    for (int i = 0; i <map.size(); i++) {
        if (i==10) {return ;}
        try {
            URL url = new URL(map.get(i).get("vdoPhotourl").toString());

            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap2 = BitmapFactory.decodeStream(is);
            is.close();
            conn.disconnect();
        }catch (IOException e )
        {
            bitmap2=BitmapFactory.decodeResource(getContext().getResources(), R.drawable.down);


        }
        //addTextToList("广东",1,android.R.drawable.ic_lock_lock,"ps","data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊");
        addTextToList(map.get(i).get("paidppnumber").toString()
                , 1
                ,map.get(i).get("vdourl").toString()
                , 0
                ,map.get(i).get("title").toString()
				,bitmap2
				);

        //暂定内容，参数....购买人数,布局,头像,是否显示红点,标题
        //头像和内容壁纸需要在适配器以二进制转为图片
    }
    adapter.notifyDataSetChanged();
    //  addTextToList("uuu", 1, "android.R.drawable.ic_lock_lock", 0, "name");
   // Toast.makeText(getContext(),str1,Toast.LENGTH_LONG).show();

}
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            ebent = (OnActivityebent) activity;


        }catch (ClassCastException e)
        {

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
