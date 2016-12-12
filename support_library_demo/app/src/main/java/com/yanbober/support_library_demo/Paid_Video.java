package com.yanbober.support_library_demo;

import android.content.*;
import android.database.sqlite.SQLiteDatabase;
import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

import java.util.*;

import org.json.*;

import android.support.v7.widget.Toolbar;

import com.yanbober.support_library_demo.DataHelpers.DataHelper;

public class Paid_Video extends AppCompatActivity

{
    RecyclerView rv = null;
    Toolbar tb;
    public ArrayList<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>();
    public FirstAdapter adapter;
    //右上角新消息提示红点
    TextView Message_point;
    User user = new User();
    ImageView message;
    LinearLayoutManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paid_video);
        init();
    }

    public void init() {
        rv = (RecyclerView) findViewById(R.id.paidvideorv);
        tb = (Toolbar) findViewById(R.id.paidToolbar);
        Message_point = (TextView) findViewById(R.id.tTextView);
        message = (ImageView) this.findViewById(R.id.activitymainTextView1);

        //初始化ToolBar
        setSupportActionBar(tb);
        tb.setNavigationIcon(R.drawable.back_purple);
        tb.setNavigationOnClickListener(new OnClickListener() {//返回监听，按钮返回，不过不知道会回哪里
            public void onClick(View v) {
                onBackPressed();

            }

        });
        rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        //设置RecyclerView布局管理器为1列垂直排布

        User u = new User();

           //addTextToList("本 拉登教你打仗",2,R.drawable.qq,"901人付款");
        message.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Message_point.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(Paid_Video.this, Message_c.class);
                startActivity(intent);


            }
        });

        adapter = new FirstAdapter(Paid_Video.this, lists);

        rv.setAdapter(adapter);
        adapter.setOnClickListener(new FirstAdapter.OnItemClickListener() {
            public void onItemClickListener(View v, int position) {
                Intent intent = new Intent(Paid_Video.this, Run_Video_.class);
                startActivity(intent);
              }

            public void onItemLongClickListener(View v, int position) {


            }
        });

        try {
            //JSONObject jsonObject=(JSONObject)
            JSONArray jss = new JSONArray(user.mydata.get("notices").toString());

            if (jss.length() < 2) {
                Message_point.setVisibility(View.INVISIBLE);
            } else {


                Message_point.setText(String.valueOf(jss.length()));


            }
        } catch (JSONException e) {

        }
SetAllVideo();
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState==0)//0停止　1开始滑动 2放手依旧滑动中
                {
                    lm = (LinearLayoutManager) rv.getLayoutManager();
                    User u=new User();
                    HashMap<String,Object> map=new HashMap<String, Object>();
                    map.put("?","Paid_Video");

                    if (u.getBitmapurl!=null) {
                        u.getBitmapurl.loadListViewImage(lm.findFirstVisibleItemPosition(), lm.findLastVisibleItemPosition(),rv,Paid_Video.this,map);
                    }
                }else
                {
                    User u=new User();
                    if (u.getBitmapurl!=null)
                        u.getBitmapurl.cancelAllTask();
                }


            }
        });

    }
private void SetAllVideo()
{
    //通过所有视频的信息里的购买者id 对比
    User u=new User();
    DataHelper dataHelper=new DataHelper(Paid_Video.this);
    SQLiteDatabase db;




    if (u.maps.size()>2)
    {



        for (int i = 0; i <u.maps.size() ; i++) {
            //从数据库获取id而不是User类


            //拿出第i个maps中的_id
            String videoID=u.maps.get(i).get("_id").toString();
            if (dataHelper.ispaidVideosID(videoID)) {//如果是则添加到列表，并显示出来
                HashMap<String,Object> map=new HashMap<String,Object>();
                map=u.maps.get(i);//将这个视频的所有信息加到map
                u.paid_Videos_List.add(map);//将map 加到List
                //最终一个个保存到这个list中
            }

        }
        //所有视频遍历完毕后开始加载
        if (u.paid_Videos_List.size()>2)
        {
            for (int i = 0; i <u.paid_Videos_List.size() ; i++) {
                addTextToList(
                        u.paid_Videos_List.get(i).get("title").toString()
                        ,2
                        ,R.drawable.qq
                        ,u.paid_Videos_List.get(i).get("paidppnumber").toString()
                ,u.paid_Videos_List.get(i).get("vdoPhotourl").toString());
            }
            adapter.notifyDataSetChanged();
        }
    }
}
    public void addTextToList(String text, int who, int id, String data,String vdoPhotourl) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("image", id);
        map.put("text", text);
        map.put("vdoPhotourl",vdoPhotourl);
        map.put("data", data);
        map.put("layout", who);

        lists.add(map);
    }
}
