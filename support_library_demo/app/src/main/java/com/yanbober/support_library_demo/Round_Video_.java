package com.yanbober.support_library_demo;

import android.app.*;
import android.content.DialogInterface;
import android.graphics.*;
import android.hardware.*;
import android.media.*;
import android.media.MediaRecorder.*;
import android.os.*;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.TimeUtils;
import android.view.*;
import android.view.View.*;
import android.webkit.WebView;
import android.widget.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import android.hardware.Camera;
import android.support.design.widget.*;
public class Round_Video_ extends Activity

{
    public Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case 0:

                    break;


            }
        }
    };
RoundProgressBar bar;
static int message =0;
	private Button startButton, stopButton, playButton;
	private SurfaceView mSurfaceView;
	private boolean isRecording;
	private MediaRecorder mediaRecorder;
	private Camera camera;
	public RelativeLayout HideLayout;
	static int flag=0;
	int count=0;
	Timer mTimer=new Timer();
	TimeUnit t;
	File file = new File("/sdcard/RoundVideo/video.3gp");

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.round_video_layout);
		mSurfaceView=(SurfaceView)findViewById(R.id.camera_surfaceview);
		bar=(RoundProgressBar)findViewById(R.id.round_bar);
		bar.setMax(100);
		initView();
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.roundbutton);  

		//bar.setBitmap(bitmap,0);

		bar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			
			{////点击开始录像
				if(flag==0)
					
					{
						start();
						bar.setBackgroundResource(R.drawable.roundbuttoning);
					flag=1;
					setprog();
					
				}else
				{//点击结束录像
				
					stop();
					bar.setBackgroundResource(R.drawable.roundbutton);
					HideLayout.setVisibility(View.VISIBLE);
					//解除隐藏
					flag=0;
				}
				bar.invalidate();
				
			}
		});
	
	// 声明Surface不维护自己的缓冲区，针对Android3.0以下设备支持
	mSurfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}//oncreate()
	public void initView()
{
HideLayout=(RelativeLayout)this.findViewById(R.id.HideLayout);
//初始隐藏这个布局
	HideLayout.setVisibility(View.INVISIBLE);
	//隐藏(不可见)

}//initView
	public void setprog()
	{
		task t=new task(count,bar);
		//新建一个方法
		mTimer.schedule(t, 0, 1000);
		//设每一秒调用一次
		
		
		
	}
	class  task extends TimerTask {  
		public View v;
		int x,y;
		boolean b;
		int count;
		RoundProgressBar bar;
		public task(int count,RoundProgressBar bar)
		{
			this.count=count;
			this.bar=bar;
		}
		@Override  
		public void run() {  

			runOnUiThread(new Runnable() {      // UI thread   
					@Override  
					public void run() {  
					if(bar.getProgress()==70)
						maxtimemessage(bar);
						if(bar.getProgress()==80)
							maxtimemessage(bar);
						if(bar.getProgress()==90)
							maxtimemessage(bar);



						bar.setProgress(bar.getProgress()+1);
						 
					}
				});  
		}  
	};  
    public void maxtimemessage(RoundProgressBar bar)
	{
		if(message==0)
			Snackbar.make(bar,"请注意录制时长",Snackbar.LENGTH_SHORT).setAction("不再提示",
                    new View.OnClickListener()
				{
					public void onClick(View v)
					{
						message=1;
					}
				}).show();
	}

    protected void start() {
		try {

			if (file.exists()) {
				// 如果文件存在，删除它，演示代码保证设备上只有一个录音文件
				file.delete();
			}
            DisplayMetrics dm = new DisplayMetrics();

            getWindowManager().getDefaultDisplay().getMetrics(dm);
			mediaRecorder = new MediaRecorder();// 创建mediarecorder对象
			//摄像头旋转90度
			camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
			camera.setDisplayOrientation(90);
            camera.unlock();

            mediaRecorder.setCamera(camera);
            // 设置录制视频源为Camera(相机)
			mediaRecorder.setVideoSource(Camera.CameraInfo.CAMERA_FACING_BACK);
			//设置音频采集方式
			mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			//设置输出格式
			mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			
			//设置audio编码方式
			mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			//设置最大限时
			
			//mediaRecorder.setMaxDuration(60*1000);
			//录像旋转90度
			mediaRecorder.setOrientationHint(90);
			// 设置录制完成后视频的封装格式THREE_GPP为3gp.MPEG_4为mp4
			//mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			// 设置录制的视频编码h263 h264
			mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
			//设置高质量录制,改变码率
			mediaRecorder.setVideoEncodingBitRate(5*600*600);
			//设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
			mediaRecorder.setVideoSize(640, 480);
			// 设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
			mediaRecorder.setVideoFrameRate(20);
			// 设置视频文件输出的路径
			mediaRecorder.setOutputFile("/sdcard/video.3gp");
			// 设置捕获视频图像的预览界面

			mediaRecorder.setPreviewDisplay(mSurfaceView.getHolder().getSurface());

			mediaRecorder.setOnErrorListener(new OnErrorListener() {

					@Override
					public void onError(MediaRecorder mr, int what, int extra) {
						// 发生错误，停止录制
						mediaRecorder.stop();
						mediaRecorder.release();
						mediaRecorder = null;
						isRecording = false;
						startButton.setEnabled(true);
						stopButton.setEnabled(false);
						playButton.setEnabled(false);
					}
				});

			// 准备、开始
			mediaRecorder.prepare();
			mediaRecorder.start();

			//startButton.setEnabled(false);
			//stopButton.setEnabled(true);
			isRecording = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void stop() {
		if (isRecording) {
			// 如果正在录制，停止并释放资源
			mediaRecorder.stop();
			mediaRecorder.release();
			mediaRecorder = null;
			isRecording = false;
			//startButton.setEnabled(true);
			//stopButton.setEnabled(false);
		//	playButton.setEnabled(true);
			if (camera != null) {
				camera.release();
			}
		}
	}

	@Override
	protected void onDestroy() {
		if (isRecording) {
			mediaRecorder.stop();
			mediaRecorder.release();
			mediaRecorder = null;
		}
		if (camera != null) {
			camera.release();
		}
		super.onDestroy();
	}
//////////////////sucess Onclick
public void delete(View v)
{

    Message_Dialog editNameDialog = new Message_Dialog();
    editNameDialog.show(getFragmentManager(), "EditNameDialog");




}
public void upload(View v)
{

    HashMap<String,Object> 	map1=new HashMap<String,Object>();
    map1.put("isprogress",1);
    map1.put("progress",25);
    map1.put("max",100);
    map1.put("textsize",25);
	map1.put("upload",0);
    map1.put("color",Color.rgb(555,333,333));
    Pop_Img.Builder p=new Pop_Img.Builder(Round_Video_.this,map1);
   // p.setPositiveButton("[潮汕揭]初版\n问题反馈:(qq) 1213965634\n\n", new DialogInterface.OnClickListener() {
    //    public void onClick(DialogInterface dialog, int which)
     //   {
     //       dialog.dismiss();
            //需要将数据传给对话框然后实时更新进度条

      //  }
        //设置你的操作事项


  //  });


   p.create().show();
  /* WebView wv=new WebView(this);
    wv.getSettings().setJavaScriptEnabled(true);
    //设置可操作js
    wv.addJavascriptInterface(new Mytest(),"test");
wv.loadUrl("http://192.168.1.112:1103/home/with/Try-videos-master/login.js");
    wv.loadUrl("javascript : router.post('cat')" );
*/
}
public void edit(View v)
{
	HashMap<String,Object> 	map1=new HashMap<String,Object>();
	map1.put("isprogress",1);
	map1.put("progress",25);
	map1.put("max",100);
	map1.put("textsize",25);
	map1.put("upload",1);
	map1.put("color",Color.rgb(555,333,333));
	Pop_Img.Builder p=new Pop_Img.Builder(Round_Video_.this,map1);

	p.setaddMiusic(new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			//dialog.dismiss();



			//设置你的操作事项


		}
	});//setaddMiusic()
	p.setrevioce(new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			//dialog.dismiss();



			//设置你的操作事项


		}
	});//setaddvioce()
	p.setUploadclick(0,new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {

		}

	});

p.create().show();

	int count=100;
	task1 t=new task1(count,p);
	//新建一个方法
	mTimer.schedule(t, 0, 1000);
	//设每一秒调用一次







}
//////////////////sucess Onclick

class  task1 extends TimerTask {
	public View v;
	int x,y;
	boolean b;
	int count;
	Pop_Img.Builder bar;
	public task1(int count,Pop_Img.Builder bar)
	{
		this.count=count;
		this.bar=bar;
	}
	@Override
	public void run() {

		runOnUiThread(new Runnable() {      // UI thread
			@Override
			public void run() {
bar.OnProgressChanged(10);
			}
		});
	}
};

public class Mytest
    {
        public int test()
        {
            return 1000;
        }
    }

	
	
	
}



