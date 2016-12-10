package com.yanbober.support_library_demo;


/*
	由MyrecyviewAdapter稍加修改的一个RecyclerView的专用适配器



 */

import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.*;
import android.widget.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.*;

public class FirstAdapter2 extends RecyclerView.Adapter<MyViewHolder3> {
    private ArrayList<HashMap<String,Object>> lists,addrs;
    private Context context;
    private List<Object> alllist;

    private List<String> data=new ArrayList<>();

    private List<Integer> counttype=new ArrayList<>();

    int[] layout={R.layout.recy_card_item,R.layout.share_item,R.layout.paid_item};
    private OnItemClickListener mListener;
    /**
     * 点击事件的接口
     */
    public interface  OnItemClickListener{
        void onItemClickListener(View view,int position);//点击
        void onItemLongClickListener(View view,int position);//长按
    }
    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    public void setOnClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
    /**
     * 构造函数
     * @param
     * @param
     */


    public FirstAdapter2(Context context,ArrayList<HashMap<String,Object>> lists) {
        this.context = context;

        this.lists=lists;

    }

    @Override
    public MyViewHolder3 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        MyViewHolder3 holder=null;
        //从getitemviewtype中获得了布局

              view=LayoutInflater.from(context).inflate(R.layout.recy_card_item,parent,false);

                holder=new MyViewHolder3(view);





        return holder;
        //由position也就是位置硬加载布局
    }




    @Override
    public void onBindViewHolder(final MyViewHolder3 holder, int position) {
        ViewGroup.LayoutParams params =  holder.itemView.getLayoutParams();//得到item的LayoutParams布局参数

        holder.itemView.setLayoutParams(params);//把params设置给item布局
        final int count=position;
        //通过返回的viewtype获得硬性布局
        switch((int)lists.get(position).get("layout"))
        {

            case 0:
                //holder.img.setImageResource(lists.get(position).get("image"));
                if((Integer)lists.get(position).get("is")!=0)
                {
                    holder.is.setVisibility(View.INVISIBLE);//控件不可见
                    // View.VISIBLE控件可见，View.GONE控件隐藏

                }
                break;
            case 1:
                holder.infoDetails_paidcount_tv.setText(lists.get(position).get("data").toString());
                holder.infoDetails_title_tv.setText(lists.get(position).get("name").toString());
                byte[] bitmapArray1;
                //bitmapArray1 = Base64.decode(lists.get(position).get("image").toString(), Base64.DEFAULT);
                //Bitmap bitmap1= BitmapFactory.decodeByteArray(bitmapArray1, 0,
                //		bitmapArray1.length);
                //以url解析图片
                Bitmap bitmap2=null;
                URL url;


                 holder.infoDetails_relativelayout.setTag(lists.get(position).get("vdoPhotourl").toString());
                GetBitmapurl getBitmapurl=new GetBitmapurl();
                getBitmapurl.loadurl(lists.get(position).get("vdoPhotourl").toString(),holder.infoDetails_relativelayout);
               // Drawable drawable=new BitmapDrawable(bitmap2);
                //以url解析图片　

                //holder.infoDetails_relativelayout.setBackground(drawable);
                //设置内容背景图
                if(mListener!=null){//如果设置了监听那么它就不为空，然后回调相应的方法
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = holder.getLayoutPosition();//得到当前点击item的位置pos
                            mListener.onItemClickListener(holder.itemView,pos);//把事件交给我们实现的接口那里处理
                        }
                    });
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            int pos = holder.getLayoutPosition();//得到当前点击item的位置pos
                            mListener.onItemLongClickListener(holder.itemView,pos);//把事件交给我们实现的接口那里处理
                            return true;
                        }
                    });
                }
                break;



        }



    }


    @Override
    public int getItemCount() {
        return lists.size();
    }


}//myrecleradapter
class MyViewHolder3 extends RecyclerView.ViewHolder {

    ////viewtype0
    TextView mTv, name, data, ps, is, paid_name, paid_ps, collect_spot, collect_name, collect_ps, infoDetails_paidcount_tv, infoDetails_title_tv;
    RelativeLayout rll;
    ImageView img, img1, pay_run_img;
    RelativeLayout rv, collect_rv, collect_back, my_video_al_rv, infoDetails_relativelayout;
    String[] provent;
    Button collect_paid_button = null;

    public MyViewHolder3(View itemView) {
        super(itemView);
        img1 = (ImageView) itemView.findViewById(R.id.shareitemButton1);
        name = (TextView) itemView.findViewById(R.id.shareitemTextView1);
        ps = (TextView) itemView.findViewById(R.id.share_itemTextView);
        data = (TextView) itemView.findViewById(R.id.share_itemTextViewData);
        rv = (RelativeLayout) itemView.findViewById(R.id.recycarditemRelativeLayout1);
        is = (TextView) itemView.findViewById(R.id.tTextView);
        //	img=(ImageView)itemView.findViewById(R.id.recy_card_itemImageView);
        mTv = (TextView) itemView.findViewById(R.id.recy_card_itemTextView);
        //////Paid_Video
        paid_name = (TextView) itemView.findViewById(R.id.paiditemTextView1);
        paid_ps = (TextView) itemView.findViewById(R.id.recy_card_itemTextView);
        rll = (RelativeLayout) itemView.findViewById(R.id.paid_itemRelativeLayout);
        pay_run_img = (ImageView) itemView.findViewById(R.id.paiditemImageView1);

        //////Paid_Video
        /////Collect_
        collect_rv = (RelativeLayout) itemView.findViewById(R.id.collectitemRelativeLayout1);
        //支付按钮(rela)
        collect_paid_button = (Button) itemView.findViewById(R.id.collect_paid_button);
        //支付按钮
        collect_spot = (TextView) itemView.findViewById(R.id.tTextView);
        //小圆点
        collect_name = (TextView) itemView.findViewById(R.id.paiditemTextView1);
        //名字
        collect_ps = (TextView) itemView.findViewById(R.id.recy_card_itemTextView);
        //说明
        collect_back = (RelativeLayout) itemView.findViewById(R.id.collectitemRelativeLayout2);
        //背景(海报)
        //////Collect_

        /////My_Video_
        my_video_al_rv = (RelativeLayout) itemView.findViewById(R.id.my_video_tabs_al_rv_carditemRelativeLayout);


        ////My_Video_


        ////InfoDetailsFragment

        infoDetails_title_tv = (TextView) itemView.findViewById(R.id.recy_card_Title);
        infoDetails_paidcount_tv = (TextView) itemView.findViewById(R.id.recy_card_itemTextView);
        infoDetails_relativelayout = (RelativeLayout) itemView.findViewById(R.id.Video_item_Relativelayout);
        ////InfoDetailsFragment
    }


}
