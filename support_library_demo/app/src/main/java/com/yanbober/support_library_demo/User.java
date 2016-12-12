package com.yanbober.support_library_demo;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;

/*
		这个类会暂时保存一些数据，比如用户名什么的

 */
public class User
{
	public static String phone="null",pas="null",name="null",flag="null",picture="null"
	,vid="null",
	chatdata="null",city="null",sex="null",old="null",_id="null",token="null",paidPwd="null";
	;
	public static String[] urls;
	public static Bitmap headBitmap=null;
	public static GetBitmapurl getBitmapurl;
	public static ArrayList<HashMap<String,Object>> Collect_List,notices_list,notLoadforVideo_list,AlreadyLoadforVideo_list=null;
	public static HashMap<String,Object> mydata=new HashMap<String,Object>();
	public static MainActivity mainActivity;
	public static My_Video_ my_video_;
	public static Collect_ collect_;
	public static ArrayList<HashMap<String, Object>> maps,Datas,paid_Videos_List=new ArrayList<>();
	public static ArrayList<Integer> paid_Videos=new ArrayList<>();
}
