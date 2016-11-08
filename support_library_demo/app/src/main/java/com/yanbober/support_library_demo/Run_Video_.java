package com.yanbober.support_library_demo;

import android.os.*;
import android.support.design.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

import android.support.v7.widget.Toolbar;
public class Run_Video_ extends ActionBarActivity {
	private SurfaceView surfaceView;
	private Button btnPause, btnPlayUrl, btnStop;
	private SeekBar skbProgress;
	private Player player;
	CollapsingToolbarLayout collapsingToolbar;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.run_video_layout);
			initView();
		}
		
		
		public void initView()
		{
			Toolbar toolbar = (Toolbar) this.findViewById(R.id.tool_bar);
			setSupportActionBar(toolbar);
			ActionBar actionBar = getSupportActionBar();
			actionBar.setHomeAsUpIndicator(R.drawable.back_purple);
			actionBar.setDisplayHomeAsUpEnabled(true);
			
		
		collapsingToolbar =
		(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("this s Home Pager !!");
		
		
		//down is full scrren
		///setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		surfaceView = (SurfaceView) this.findViewById(R.id.surfaceView1);

		btnPlayUrl = (Button) this.findViewById(R.id.btnPlayUrl);
		btnPlayUrl.setOnClickListener(new ClickEvent());

		btnPause = (Button) this.findViewById(R.id.btnPause);
		btnPause.setOnClickListener(new ClickEvent());

		btnStop = (Button) this.findViewById(R.id.btnStop);
		btnStop.setOnClickListener(new ClickEvent());
btnPlayUrl.setVisibility(View.INVISIBLE);
			btnPause.setVisibility(View.INVISIBLE);
			btnStop.setVisibility(View.INVISIBLE);
			
		skbProgress = (SeekBar) this.findViewById(R.id.skbProgress);
		skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());
		player = new Player(surfaceView, skbProgress);
	}
	
	class ClickEvent implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			if (arg0 == btnPause) {
				player.pause();
			} else if (arg0 == btnPlayUrl) {
				String url="http://www.sciencep.com/movies/1411711067.mp4";
				player.playUrl(url);
			} else if (arg0 == btnStop) {
				player.stop();
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

