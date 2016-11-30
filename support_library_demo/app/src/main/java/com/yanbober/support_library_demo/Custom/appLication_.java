package com.yanbober.support_library_demo.Custom;

import android.app.*;
import com.bumptech.glide.request.target.*;
import com.yanbober.support_library_demo.*;
import com.zcy.selectpicture.*;

import com.yanbober.support_library_demo.R;


public class appLication_ extends Application
{
	private static App INSTANCE;

    public static App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
      //  INSTANCE = this;
        ViewTarget.setTagId(R.id.glide_tag);
    }
    }

