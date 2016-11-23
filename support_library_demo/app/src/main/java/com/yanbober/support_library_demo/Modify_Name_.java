package com.yanbober.support_library_demo;

import android.content.Intent;
import android.media.Image;
import android.os.*;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.view.View.*;
import android.widget.*;

import com.yanbober.support_library_demo.*;

import android.support.v7.widget.Toolbar;

import java.util.HashMap;

public class Modify_Name_ extends AppCompatActivity {
    //将ToolBar与TabLayout结合放入AppBarLayout
    private Toolbar mToolbar;
    User user=new User();
    private DrawerLayout mDrawerLayout;
    EditText modify_e;
    ImageView Ok_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_name);
        inintView();
    }
    public void inintView() {
        mToolbar = (Toolbar) this.findViewById(R.id.Modify_Toolbar);
        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);

        modify_e=(EditText)this.findViewById(R.id.Modify_e);
        //初始化ToolBar
        setSupportActionBar(mToolbar);
        //初始化ToolBar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.back_purple);//android.R.drawable.ic_dialog_alert);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }
    public void onBackPressed() {
        //code......

        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("name",modify_e.getText().toString());

        Message msg=new Message();
        Bundle bundle=new Bundle();
        msg.what=3;
        msg.obj=map;

     //   bundle.putString("nickname",str);
        msg.setData(bundle);
        user.name=modify_e.getText().toString();
        if (user.mainActivity!=null)
            user.mainActivity.mHandler.sendMessage(msg);
        if (user.my_video_!=null)
            user.my_video_.mHandler.sendEmptyMessage(3);
        if (user.collect_!=null)
            user.collect_.mHandler.sendEmptyMessage(3);

        Intent intent=new Intent(Modify_Name_.this,MainActivity.class);
        startActivity(intent);
        finish();
        //结束activity防止无限退回
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_info_details:
                ////mViewPager.setCurrentItem(0);
                break;
            case R.id.menu_share:
                //mViewPager.setCurrentItem(1);
                break;
            case R.id.menu_agenda:
                //	mViewPager.setCurrentItem(2);
                break;
            case android.R.id.home:
                //主界面左上角的icon点击反应
                //	mDrawerLayout.openDrawer(GravityCompat.START);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
