package com.yanbober.support_library_demo.Custom;

import android.app.*;
import android.content.Context;
import android.content.SharedPreferences;

import com.bumptech.glide.request.target.*;
import com.yanbober.support_library_demo.*;
import com.zcy.selectpicture.*;

import com.yanbober.support_library_demo.R;

import java.util.HashMap;


public class appLication_ extends Application
{
	private static App INSTANCE;
    private static HashMap<String ,Object> map;
    public static GetBitmapurl getBitmapurl;
    private static SharedPreferences sharedPreferences;
    public static App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
      //  INSTANCE = this;
        ViewTarget.setTagId(R.id.glide_tag);
        sharedPreferences=getSharedPreferences("login", Context.MODE_PRIVATE);

    }
    public void setsharePreference(HashMap<String ,Object> map)
    {
        this.map=map;

        SharedPreferences.Editor edit=sharedPreferences.edit();
        edit.putString("phone",map.get("phone").toString());
        edit.putString("nickname",map.get("nickname").toString());
        edit.putString("headbitmap",map.get("headbitmap").toString());
        edit.putString("notices",map.get("notices").toString());
        edit.putString("collects",map.get("collects").toString());
        edit.putString("paidvideos",map.get("paidvideos").toString());
        edit.putString("notpaidvideos",map.get("notpaidvideos").toString());
        //提交
        edit.commit();

    }

    public HashMap<String,Object> getsharePrerence()
    {
        HashMap<String,Object> map=new HashMap<>();
       map .put("phone",sharedPreferences.getString("phone",""));
        map .put("nickname",sharedPreferences.getString("nickname",""));
        map .put("headbitmap",sharedPreferences.getString("headbitmap",""));
        map .put("notices",sharedPreferences.getString("notices",""));
        map .put("collects",sharedPreferences.getString("collects",""));
        map .put("paidvideos",sharedPreferences.getString("paidvideos",""));
        map .put("notpaidvideos",sharedPreferences.getString("notpaidvideos",""));
        return  map;
    }
    public String getshreprenceString(String key)
    {
        String str=null;
        str=sharedPreferences.getString(key,"");
        return str;
    }
    public void setshreprenceString(String key,String value){
        SharedPreferences.Editor edit=sharedPreferences.edit();
        edit.putString(key,value);
    }

    public void SetMyLoadImages(GetBitmapurl getBitmapurl)
    {
        this.getBitmapurl=getBitmapurl;
    }
    }

