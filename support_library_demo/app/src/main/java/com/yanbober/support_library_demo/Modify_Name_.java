package com.yanbober.support_library_demo;

import android.content.*;
import android.os.*;
import android.support.v4.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import java.util.*;

import android.support.v7.widget.Toolbar;
import com.yanbober.support_library_demo.Http_Util.*;

public class Modify_Name_ extends AppCompatActivity {
	
	public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Bundle bundle=msg.getData();
            switch (msg.what) {
                case 0:
                    Intent intent=new Intent(Modify_Name_.this,MainActivity.class);
					   startActivity(intent);
					 finish();
					break;
					}
					}
					};
        
						
	
    //将ToolBar与TabLayout结合放入AppBarLayout
    private Toolbar mToolbar;
    User user=new User();
    private DrawerLayout mDrawerLayout;
    EditText modify_e;
    ImageView Ok_img;
    TextView name;
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
        name=(TextView)this.findViewById(R.id.modifynameTextView1);
        modify_e=(EditText)this.findViewById(R.id.Modify_e);
        User u=new User();

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

		if(!modify_e.getText().toString().equals(""))
		{
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("name",modify_e.getText().toString());
		user.mydata.put("nickname",modify_e.getText().toString());
			user.name=modify_e.getText().toString();
			
        Message msg=new Message();
        Bundle bundle=new Bundle();
			Log.e("modify_name",user.mydata.get("nickname").toString());
			Http_UploadFile_ htt=new Http_UploadFile_(mHandler, "http://trying-video.herokuapp.com/user/nickname?token="+user.token, user.name, "6") ;
			Thread x=new Thread(htt);
			x.start();
				
        msg.what=3;
        msg.obj=map;

     //   bundle.putString("nickname",str);
        msg.setData(bundle);
//        if (user.mainActivity!=null)
//            user.mainActivity.mHandler.sendMessage(msg);
//        if (user.my_video_!=null)
//            user.my_video_.mHandler.sendEmptyMessage(3);
//        if (user.collect_!=null)
//            user.collect_.mHandler.sendEmptyMessage(3);
//
     //   Intent intent=new Intent(Modify_Name_.this,MainActivity.class);
     //   startActivity(intent);
      //  finish();
        //结束activity防止无限退回
		}
		else
		{
			Intent intent=new Intent(Modify_Name_.this,MainActivity.class);
			startActivity(intent);
			finish();
		}
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
