package com.yanbober.support_library_demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.*;
import android.view.View.*;
import android.widget.*;

import android.support.v7.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TimerTask;
import android.widget.SearchView.*;

import com.bumptech.glide.Glide;
import com.yanbober.support_library_demo.Http_Util.*;
import java.util.*;
import com.yanbober.support_library_demo.Message_S.*;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class Run_Video_ extends ActionBarActivity {
	public Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			Bundle bundle=msg.getData();
			switch (msg.what)
			{
				case 0:
					hideButtons.setVisibility(View.INVISIBLE);
					break;
					case 1:
						if(msg.arg1==0)
						{//是收藏还是取消
							collect_star.setBackgroundResource(R.drawable.start_yellow);
						tcollect.setTextColor(Color.rgb(244,139,8));
						
						}else
						{
							collect_star.setBackgroundResource(R.drawable.star_gray);
							tcollect.setTextColor(0xff887777);
							

							
						}
						break;




				case 5:
					//获取上传者信息
					try {
						JSONObject jsonObject = null;
						Bitmap bitmap2=null;
						if (msg.obj != null) {
							jsonObject = (JSONObject) msg.obj;
							try {
								URL url = new URL(jsonObject.getString("headPortrait").toString());

								HttpURLConnection conn = (HttpURLConnection) url
										.openConnection();
								conn.setDoInput(true);
								conn.connect();
								InputStream is = conn.getInputStream();
								bitmap2 = BitmapFactory.decodeStream(is);
								is.close();
								conn.disconnect();
							} catch (IOException e) {
							}
							if (bitmap2!=null)
							{
								uploader_img.setImageBitmap(bitmap2);
							}
							//----------//
							upload_name.setText(jsonObject.getString("nickname"));



						}
					}catch (JSONException e)
					{

					}
					break;

			}
		}
	};

	

	ImageView collect_star,uploader_img;

	RelativeLayout hideButtons;
	CollapsingToolbarLayout collapsingToolbar;
	String count=null,vid=null,upLoader_id=null;
	final User user=new User();
	JSONObject jsonObject=null;
	TextView tcollect,paid_count,vdoTitle,upload_name;
	LinearLayout upder_ll;
	JCVideoPlayerStandard jcVideoPlayerStandard;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.run_video_layout);
		jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.custom_videoplayer_standard);
		//jcVideoPlayerStandard.thumbImageView.setThumbInCustomProject("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");

			initView();
		Bundle bun=this.getIntent().getExtras();
		if (bun != null && bun.containsKey("count"))
		{

		count=bun.getString("url");
			if(bun.containsKey("vid"))
			{
				vid=bun.getString("vid");
			}
			if (bun.containsKey("iscollect")) {
				if (bun.getString("iscollect").equals("0")) {//是收藏还是取消
					collect_star.setBackgroundResource(R.drawable.start_yellow);
					tcollect.setTextColor(Color.rgb(244,139,8));

				} else {
					collect_star.setBackgroundResource(R.drawable.star_gray);
					tcollect.setTextColor(0xff887777);
				}
			}
				if (bun.containsKey("title"))
				{
					vdoTitle.setText(bun.getString("title"));
				}else
				{
					vdoTitle.setText("数据错误");
					vdoTitle.setTextColor(Color.RED);
				}
			if (bun.containsKey("paidppnumber"))
			{
				paid_count.setText(bun.getString("paidppnumber")+" 人付款");
			}else
			{
				paid_count.setText(bun.getString("数据错误"));
				paid_count.setTextColor(Color.RED);
			}
			if (bun.containsKey("uploader"))
			{
				upLoader_id=bun.getString("uploader");
			}
			if (bun.containsKey("url"))
			{

					jcVideoPlayerStandard.setUp(bun.getString("url")
							, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "");


			}
			if (bun.containsKey("vdoPhotourl"))
			{
				Glide.with(this)
						.load(bun.getString("vdoPhotourl"))
						.into(jcVideoPlayerStandard.thumbImageView);

			}

			getandsetUploaderData();
				//strs=str.split("\\|");
		}
		}

	@Override
	public void onBackPressed() {
		if (JCVideoPlayer.backPress()) {
			return;
		}
		super.onBackPressed();
	}
	@Override
	protected void onPause() {
		super.onPause();
		JCVideoPlayer.releaseAllVideos();
	}
		public void initView()
		{
			Toolbar toolbar = (Toolbar) this.findViewById(R.id.tool_bar);

			collect_star=(ImageView)this.findViewById(R.id.run_video_layoutImageView);
			tcollect=(TextView)this.findViewById(R.id.run_video_layoutTextView);

			upder_ll=(LinearLayout)this.findViewById(R.id.Run_Video_upder_ll);
			paid_count=(TextView)this.findViewById(R.id.paid_count);
			vdoTitle=(TextView)this.findViewById(R.id.Run_Video_vdoTitle);
			uploader_img=(ImageView)this.findViewById(R.id.Run_Video_upder_Img);
			upload_name=(TextView)this.findViewById(R.id.Run_Video_upder_name);
			setSupportActionBar(toolbar);
			ActionBar actionBar = getSupportActionBar();
			actionBar.setHomeAsUpIndicator(R.drawable.back_purple);
			actionBar.setDisplayHomeAsUpEnabled(true);

			//缓冲圈隐藏
			collect_star.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					if(vid!=null)
					{
						HashMap<String,Object> map=new HashMap<String,Object>();
						map.put("context",Run_Video_.this);
						map.put("vid",vid);
						Http_UploadFile_ htt=new Http_UploadFile_("http://trying-video.herokuapp.com/user/collect/"+vid+"?token="+user.token, map,"12");
						Thread x=new Thread(htt);
						x.start();
						
						//_vid为视频id/
						/*{
							"cost" : ${cost}    //支付费用(Number)
						}
						//如果没有 cost 一般默认为0
						>>  返回 message: '已添加进收藏'*/
						}else
						{
							Toast.makeText(Run_Video_.this,"该视频无登陆id无法收藏",Toast.LENGTH_SHORT).show();
						}
				}
			});
		
		collapsingToolbar =
		(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
       // collapsingToolbar.setTitle("this s Run Video Pager !!");
		
		


	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_info_details:
				//   mViewPager.setCurrentItem(0);
				break;
			case R.id.menu_share:
				//     mViewPager.setCurrentItem(1);
				break;
			case R.id.menu_agenda:
				//     mViewPager.setCurrentItem(2);
				break;
			case android.R.id.home:
				//主界面左上角的icon点击反应
				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}


		
		public void delete_collect()
		{
			HashMap<String,Object> map=new HashMap<String,Object>();
			
			Http_UploadFile_ htt=new Http_UploadFile_(
			"http://trying-video.herokuapp.com/user/collect/:_cid?token="
			,map
			,"14"
			);
			
			Thread x=new Thread(htt);
			x.start();
			
		}


	public void getandsetUploaderData()
	{
		if (upLoader_id!=null)
		{
			HashMap<String,Object> map=new HashMap<String,Object>();
			map.put("handler",mHandler);
			map.put("Context",Run_Video_.this);
			Http_UploadFile_ htt=new Http_UploadFile_(
					"http://trying-video.herokuapp.com/user/information/" + upLoader_id
					,map
					,"8"
			);

			Thread x=new Thread(htt);
			x.start();
		}
	}
}

