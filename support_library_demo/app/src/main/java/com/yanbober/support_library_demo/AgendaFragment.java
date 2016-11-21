package com.yanbober.support_library_demo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.yanbober.support_library_demo.Http_Util.Http_UploadFile_;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author       : yanbo
 * Date         : 2015-06-01
 * Time         : 15:09
 * Description  :
 */
public class AgendaFragment extends Fragment {
    private RecyclerView mRecyclerView, rv;
    View v;
    public ArrayList<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>(), lists2 = new ArrayList<HashMap<String, Object>>();
    FirstAdapter adapter, adapter2;
    MainActivity activity;
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

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:

                    break;


            }
        }
    };

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        Bitmap bitmap= BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.ic_launcher);
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,out);
        byte[] bytes=out.toByteArray();
        String str= Base64.encodeToString(bytes,Base64.DEFAULT);
        addTextToList("sssss",1,str,0,"skajdhksaj");
        addTextToList("sssss",1,str,0,"skajdhksaj");
        addTextToList("sssss",1,str,0,"skajdhksaj");
        addTextToList("sssss",1,str,0,"skajdhksaj");addTextToList("sssss",1,str,0,"skajdhksaj");
        addTextToList("sssss",1,str,0,"skajdhksaj");
        addTextToList("sssss",1,str,0,"skajdhksaj");


        if(user.VideoList!=null) {

            //获取10条数据
            for (int i = 0; i <user.VideoList.size(); i++) {

                //addTextToList("广东",1,android.R.drawable.ic_lock_lock,"ps","data",0,"功能方面实在太少，布局也是太戳了。再多用点心设计啊");
                addTextToList(user.VideoList.get(i).get("paid_count").toString()
                        , 1
                        ,user.VideoList.get(i).get("head_image").toString()
                        , 0
                        ,user.VideoList.get(i).get("title").toString());
                //暂定内容，参数....购买人数,布局,头像,是否显示红点,标题
                //头像和内容壁纸需要在适配器以二进制转为图片
            }
            //  addTextToList("uuu", 1, "android.R.drawable.ic_lock_lock", 0, "name");
        }
        adapter = new FirstAdapter(getActivity(), lists);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

        mRecyclerView.setAdapter(adapter);
        // ebent.Onebent("aaaaaaaaaaaaaaaa");
        try {
            Http_UploadFile_ http_uploadFile_=new Http_UploadFile_((MainActivity)getActivity(),mHandler,new URL("http://192.168.1.112:1103/"),"4","POST","data");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        adapter.setmOnItemClickListener(new FirstAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Toast.makeText(getActivity(), position + "========Click:", Toast.LENGTH_SHORT).show();
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

    public void addTextToList2(String text, int who, String id, int count, String name) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("person", who);
        map.put("image", id);
        map.put("data", text);
        map.put("count", count);
        map.put("layout", who);
        map.put("name", name);

        lists2.add(map);
    }
    private void initView()
    {
        try {
            MainActivity mainActivity;
            mainActivity= (MainActivity)getActivity();
            URL url = new URL("http://192.168.1.112:1103/");
            Http_UploadFile_ http_uploadFile_ = new Http_UploadFile_(mainActivity,mHandler, url,"4","POST","getvideos");
        }catch (IOException e)
        {

        }
    }

    public interface OnActivityebent
    {
        public void Onebent(String str);

    }
    public void Onebent(String str)
    {
        String str1=str;

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
                addTextToList(items[0], 0, "android.R.drawable.ic_lock_lock", 0, "name");
            }

        if (phones != null)
            for (int i = 0; i < phones.length; i++) {
                items = null;
                items = group[i].split("\\#");

                addTextToList(items[0], 0, "android.R.drawable.ic_lock_lock", 0, "name");
            }

    }

}
