package com.yanbober.support_library_demo;

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

import java.io.IOException;
import java.util.TimerTask;

public class Run_Video_ extends ActionBarActivity {
	public Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
				case 0:
					hideButtons.setVisibility(View.INVISIBLE);
					break;


			}
		}
	};
	private SurfaceView surfaceView;
	private Button btnPause, btnStop;
	ImageView btnPlayUrl;
	private SeekBar skbProgress;
	private Player player;
	RelativeLayout hideButtons;
	CollapsingToolbarLayout collapsingToolbar;
	String count=null;
	final User user=new User();
	JSONObject jsonObject=null;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.run_video_layout);
			initView();
		Bundle bun=this.getIntent().getExtras();
		if (bun != null && bun.containsKey("count"))
		{

		count=bun.getString("url");
		
			//strs=str.split("\\|");
		}
		}
		
		
		public void initView()
		{
			Toolbar toolbar = (Toolbar) this.findViewById(R.id.tool_bar);
			hideButtons=(RelativeLayout)this.findViewById(R.id.Run_Video_hide);
			setSupportActionBar(toolbar);
			ActionBar actionBar = getSupportActionBar();
			actionBar.setHomeAsUpIndicator(R.drawable.back_purple);
			actionBar.setDisplayHomeAsUpEnabled(true);
			
		
		collapsingToolbar =
		(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
       // collapsingToolbar.setTitle("this s Run Video Pager !!");
		
		
		//down is full scrren
		///setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		surfaceView = (SurfaceView) this.findViewById(R.id.surfaceView1);

		btnPlayUrl = (ImageView) this.findViewById(R.id.btnPlayUrl);
		btnPlayUrl.setOnClickListener(new ClickEvent());

		//btnPause = (Button) this.findViewById(R.id.btnPause);
		surfaceView.setOnClickListener(new ClickEvent());


		skbProgress = (SeekBar) this.findViewById(R.id.skbProgress);
		skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());
		player = new Player(surfaceView, skbProgress);
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
	class ClickEvent implements OnClickListener {
		@Override
		public void onClick(View arg0) {

				if (arg0 == surfaceView) {
					hideButtons.setVisibility(View.VISIBLE);
					//延时３秒再隐藏布局
					player.pause();
					mHandler.sendEmptyMessageDelayed(0, 3000);

				} else if (arg0 == btnPlayUrl) {
					if(!count.substring(count.lastIndexOf(".")+1).equals("3gp"))
					{
						Toast.makeText(Run_Video_.this,"目标url并不指向视频文件",Toast.LENGTH_LONG).show();
					}else
					{player.playUrl(count);}//user.maps.get(Integer.getInteger(count)).get("vdourl").toString());
				}

		}
	}
	class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
		int progress;
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
									  boolean fromUser) {
			// 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
			this.progress = progress * player.mediaPlayer.getDuration()
				/ seekBar.getMax();
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
			player.mediaPlayer.seekTo(progress);
		}
	}


}

