package com.yanbober.support_library_demo;

import android.content.*;
import android.graphics.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import java.util.*;

/**
 * Created by 晓勇 on 2015/7/4 0004.
 */
public class MyreclerAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<HashMap<String,Object>> lists,addrs;
    private Context context;
	private List<Object> alllist;
	
	private List<String> data=new ArrayList<>();
	
    private List<Integer> counttype=new ArrayList<>();
	
	int[] layout={R.layout.recy_card_item,R.layout.share_item,R.layout.headlayout};
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

    /**
     * 构造函数
     * @param
     * @param
     */
	
	
    public MyreclerAdapter(Context context,ArrayList<HashMap<String,Object>> lists,List<String> strs,List<Integer> countType) {
        this.context = context;
        this.data = strs;
		this.lists=lists;
		this.counttype=countType;
		
		alllist=new ArrayList<>();
		alllist.clear();
		/*for(int i=0;i<strs.size();i++)
		{
			alllist.add(strs.get(i));
			alllist.add(countType.get(i));
		}*/
		
		
        getRandomHeight(this.lists);
    }
    private void getRandomHeight(ArrayList<HashMap<String,Object>> lists){//得到随机item的高度
        
    }
//    public interface OnItemClickListener{
//        void ItemClickListener(View view,int postion);
//        void ItemLongClickListener(View view,int postion);
//    }
//    public void setOnClickListener(OnItemClickListener listener){
//        this.mListener = listener;
//    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view;
	MyViewHolder holder=null;
	//从getitemviewtype中获得了布局
		switch(viewType)
		{
			
			case 0:
				view=LayoutInflater.from(context).inflate(R.layout.recy_card_item,parent,false);
				holder=new MyViewHolder(view);
				
				break;
			case 1:
				view=LayoutInflater.from(context).inflate(R.layout.share_item,parent,false);
				holder=new MyViewHolder(view);
				

				break;
				
				
		}
		//view = LayoutInflater.from(context).inflate(layout[(int)lists.get(2).get("layout")],
			//										parent,false);

		//total++;
	/*	//循环每一条item的person
        MyViewHolder viewHolder = getViewHolderByViewType(viewType);  //new MyViewHolder(view);
		
		
		
		
		TypedValue typedValue = new TypedValue();
		context.getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
		//view.setBackgroundResource(typedValue.resourceId);
		
	
		*/
		
		
        return holder;
		//由position也就是位置硬加载布局
    }
	
	private MyViewHolder getViewHolderByViewType(int ViewType)
	{
		View type0=View.inflate(context,R.layout.recy_card_item,null);
		
		View type1=View.inflate(context,R.layout.recy_card_item,null);
		View type2=View.inflate(context,R.layout.recy_card_item,null);
		
		View type3=View.inflate(context,R.layout.recy_card_item,null);
		MyViewHolder holder=null;
		switch(ViewType)
		{
			case 0:
				holder=new MyViewHolder(type0);
				
				break;
			
		}
		return holder;
	}
	protected void setUpItemEvent(final MyViewHolder holder) {
        if (mOnItemClickListener!=null){
			holder.itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int layoutPosition=holder.getLayoutPosition();
						mOnItemClickListener.onItemClickListener(holder.itemView,layoutPosition);
					}

				});
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						int layoutPosition=holder.getLayoutPosition();
						mOnItemClickListener.onItemLongClickListener(holder.itemView,layoutPosition);
						return false;
					}
				});
        }
    }
	
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ViewGroup.LayoutParams params =  holder.itemView.getLayoutParams();//得到item的LayoutParams布局参数
		setUpItemEvent(holder);
		//通过返回的viewtype获得硬性布局
		switch(getItemViewType(position))
		{
			
			case 0:
				holder.img.setImageResource((Integer)lists.get(position).get("image"));
				
				
				break;
				
				
		}
		
		//holder.mTv.setText((String)alllist.get(position));
	//holder.img.setImageResource((Integer)alllist.get(position));
		/*	holder.provent=(String[])lists.get(0).get("province");
		 params.height =120;// heights.get(100);//把随机的高度赋予item布局

		 if((int)lists.get(0).get("layout")==0)
		 params.height =350;// heights.get(100);//把随机的高度赋予item布局

		 holder.addredit.setOnItemSelectedListener(new OnItemSelectedListener() {
		 @Override
		 public void onItemSelected(AdapterView<?> parent, View view, 
		 int pos, long id) {
		 addrs.add(total,holder.provent[pos]);

		 }
		 @Override
		 public void onNothingSelected(AdapterView<?> parent) {
		 // Another interface callback
		 }
		 });		   
		 //   holder.itemView.setLayoutParams(params);//把params设置给item布局
		 int u=(int)lists.get(position).get("layout");
		switch(u)
		{

			case 1: 
				int c1=lists.get(position).get("person");
				if(c1==1)
				{
				//share fragment
				//将image以图片二进制方式解析出来
				byte[] bitmapArray1;
				bitmapArray1 = Base64.decode(lists.get(position).get("image").toString(), Base64.DEFAULT);
				Bitmap bitmap1=BitmapFactory.decodeByteArray(bitmapArray1, 0,
															bitmapArray1.length);
				holder.img1.setImageBitmap(bitmap1);
				holder.name.setText(lists.get(position).get("name").toString());
				holder.ps.setText(lists.get(position).get("ps").toString());
				holder.data.setText(lists.get(position).get("data").toString());
				}else
				{//或者以资源id解析
					holder.img1.setBackgroundResource((Integer)lists.get(position).get("image"));
					holder.name.setText(lists.get(position).get("name").toString());
					holder.ps.setText(lists.get(position).get("ps").toString());
					holder.data.setText(lists.get(position).get("data").toString());
					
					
				}

				 
				break;

			case 2:


				break;



			case 0:	
				//Info fragment
				int c=lists.get(position).get("person");
				if(c==1)
				{//将image以图片二进制方式解析出来
					byte[] bitmapArray;
					bitmapArray = Base64.decode(lists.get(position).get("image").toString(), Base64.DEFAULT);
					Bitmap bitmap=BitmapFactory.decodeByteArray(bitmapArray, 0,
																bitmapArray.length);
					holder.img.setImageBitmap(bitmap);
					holder.mTv.setText(lists.get(position).get("chat").toString());
					
				}else
				{//或者以资源id解析
					holder.mTv.setText(lists.get(position).get("chat").toString());
					
					holder.img.setBackgroundResource((Integer)lists.get(position).get("image"));
				}
//				holder.addredit.setText(lists.get(position).get("value").toString());
//				holder.addredit.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {  
//						@Override  
//						public void onFocusChange(View v, boolean hasFocus) {  
//							if(hasFocus) {
//								// 此处为得到焦点时的处理内容
//								holder.addritemtext.setTextColor(Color.parseColor("#00A1E5"));
//							} else {
//								// 此处为失去焦点时的处理内容
//								holder.addritemtext.setTextColor(Color.parseColor("#727272"));
//							}
//						}
//					});
				
//				if(mListener!=null){//如果设置了监听那么它就不为空，然后回调相应的方法
//
//					holder.itemView.setOnClickListener(new View.OnClickListener() {
//							@Override
//							public void onClick(View v) {
//								int pos = holder.getLayoutPosition();//得到当前点击item的位置pos
//								mListener.ItemClickListener(holder.itemView,pos);//把事件交给我们实现的接口那里处理
//							}
//						});
//					holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//							@Override
//							public boolean onLongClick(View v) {
//								int pos = holder.getLayoutPosition();//得到当前点击item的位置pos
//								mListener.ItemLongClickListener(holder.itemView,pos);//把事件交给我们实现的接口那里处理
//								return true;
//							}
//						});
//				}//if c==1
				break;
		}//switch
*/
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
	public int getItemViewType(int position) {
		// 最后一个item设置为footerView
		/*if (position + 1 == getItemCount()) {
			return 1;
		} else {
			return 0;
		}*/
		
		//去除所有其他方法，直接确定array里的布局
		return (int)lists.get(position).get("layout");
		
		
		
	}
	//添加数据
    public void addItem(List<String> newDatas) {
        //mTitles.add(position, data);
        //notifyItemInserted(position);
        //newDatas.addAll(lists);
		//  fot.removeAll(mTitles);
		// mTitles.addAll(newDatas);
        notifyDataSetChanged();
    }

    public void addMoreItem(List<String> newDatas) {
		//   mTitles.addAll(newDatas);
        notifyDataSetChanged();
    }

    /**
     * //上拉加载更多
     * PULLUP_LOAD_MORE=0;
     * //正在加载中
     * LOADING_MORE=1;
     * //加载完成已经没有更多数据了
     * NO_MORE_DATA=2;
     * @param status
     */
    public void changeMoreStatus(int status){
		// load_more_status=status;
        notifyDataSetChanged();
    }



}//myrecleradapter
class MyViewHolder extends RecyclerView.ViewHolder{
	
	////viewtype0
    TextView mTv,name,data,ps;
	ImageView img,img1;

	String[] provent;

    public MyViewHolder(View itemView) {
        super(itemView);
		img1=(ImageView)itemView.findViewById(R.id.shareitemButton1);
		name=(TextView)itemView.findViewById(R.id.shareitemTextView1);
		ps=(TextView)itemView.findViewById(R.id.share_itemTextView);
		data=(TextView)itemView.findViewById(R.id.share_itemTextViewData);
		
		
		//	img=(ImageView)itemView.findViewById(R.id.recy_card_itemImageView);
			mTv=(TextView)itemView.findViewById(R.id.recy_card_itemTextView);
    }
	}
	
