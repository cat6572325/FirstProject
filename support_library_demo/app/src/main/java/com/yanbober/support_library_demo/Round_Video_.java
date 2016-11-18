package com.yanbober.support_library_demo;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.*;
import android.hardware.*;
import android.hardware.Camera.Size;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Policy;
import java.util.*;
import java.util.concurrent.TimeUnit;

import android.hardware.Camera;
import android.support.design.widget.*;

import com.yanbober.support_library_demo.Http_Util.Http_UploadFile_;

public class Round_Video_ extends Activity

{
    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:

                    break;


            }
        }
    };
    RoundProgressBar bar;
    static int message = 0;
    private Button startButton, stopButton, playButton;
    private SurfaceView mSurfaceView;
    private boolean isRecording;
    private MediaRecorder mediaRecorder;
    private Camera camera;
    public RelativeLayout HideLayout, bottom_hide_layout;
    static int flag = 0;
    int count = 0;
    Timer mTimer = new Timer();
    TimeUnit t;
    File file = new File("/sdcard/RoundVideo/video.3gp");
    File_with_ file_with;
    String COMMA_PATTERN = ",";

    Size size;
    task Prog_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);

        setContentView(R.layout.round_video_layout);
        mSurfaceView = (SurfaceView) findViewById(R.id.camera_surfaceview);
        bar = (RoundProgressBar) findViewById(R.id.round_bar);
        bar.setMax(100);
        initView();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.roundbutton);

        //bar.setBitmap(bitmap,0);

        bar.setOnClickListener(new OnClickListener() {
            public void onClick(View v)

            {////点击开始录像
                if (flag == 0)

                {
                    start();
                    bar.setBackgroundResource(R.drawable.roundbuttoning);
                    flag = 1;
                    setprog();

                } else {//点击结束录像

                    stop();
                    bar.setBackgroundResource(R.drawable.roundbutton);
                    bottom_hide_layout.setVisibility(View.INVISIBLE);
                    //隐藏录制按钮的布局
                    HideLayout.setVisibility(View.VISIBLE);
                    //显示上层的上传按钮的布局
                    //解除隐藏
                    flag = 0;
                    Prog_task.cancel();
                }
                bar.invalidate();

            }
        });

        // 声明Surface不维护自己的缓冲区，针对Android3.0以下设备支持
        mSurfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }//oncreate()


        public void initView() {
            HideLayout = (RelativeLayout) this.findViewById(R.id.HideLayout);

            bottom_hide_layout=(RelativeLayout)this.findViewById(R.id.bottom_hide_layout);

                //初始隐藏这个布局
            HideLayout.setVisibility(View.INVISIBLE);
            //隐藏(不可见)


        }//initView

    public void setprog() {
        Prog_task = new task(count, bar);
        //新建一个方法
        mTimer.schedule(Prog_task, 2000, 1000);
        //设每一秒调用一次


    }

    class task extends TimerTask {
        public View v;
        int x, y;
        boolean b;
        int count;
        RoundProgressBar bar;

        public task(int count, RoundProgressBar bar) {
            this.count = count;
            this.bar = bar;
        }

        @Override
        public void run() {

            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    if (bar.getProgress() == 70)
                        maxtimemessage(bar);
                    if (bar.getProgress() == 80)
                        maxtimemessage(bar);
                    if (bar.getProgress() == 90)
                        maxtimemessage(bar);
                    if (bar.getProgress() == 100)
                        cancel();
                    bar.setProgress(bar.getProgress() + 1);

                }
            });
        }
    }

    ;

    public void maxtimemessage(RoundProgressBar bar) {
        if (message == 0)
            Snackbar.make(bar, "请注意录制时长", Snackbar.LENGTH_SHORT).setAction("不再提示",
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            message = 1;
                        }
                    }).show();
    }

    protected void start() {
        try {
            file_with=new File_with_();
            //新建对象

            DisplayMetrics dm = new DisplayMetrics();

            getWindowManager().getDefaultDisplay().getMetrics(dm);
            mediaRecorder = new MediaRecorder();// 创建mediarecorder对象
            //摄像头旋转90度
            WindowManager wm = (WindowManager) Round_Video_.this
                    .getSystemService(Context.WINDOW_SERVICE);

            int width = wm.getDefaultDisplay().getWidth();
            int height = wm.getDefaultDisplay().getHeight();
            Camera.Size bestSize = null;


            camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            Camera.Parameters mParams = camera.getParameters();
            List<Camera.Size> sizeList = camera.getParameters().getSupportedPreviewSizes();
            bestSize = sizeList.get(0);

            for (int i = 1; i < sizeList.size(); i++) {
                if ((sizeList.get(i).width * sizeList.get(i).height) >
                        (bestSize.width * bestSize.height)) {
                    bestSize = sizeList.get(i);
                }
            }

            //	mParams.setPreviewSize(mSurfaceView.getWidth(),mSurfaceView.getHeight());
            //camera.setParameters(mParams);
            //camera.startPreview();
            camera.setDisplayOrientation(90);
            camera.unlock();
            //	Size pictureS = MyCampara.getInstance().getPictureSize(pictureSizes, 800);
            //	mParams.setPictureSize(pictureS.width, pictureS.height);
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
            //mediaRecorder.setOrientationHint(90);
            // 设置录制完成后视频的封装格式THREE_GPP为3gp.MPEG_4为mp4
            //mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            // 设置录制的视频编码h263 h264
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            //设置高质量录制,改变码率
            mediaRecorder.setVideoEncodingBitRate(5 * 1024 * 1024);
            //设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
            mediaRecorder.setVideoSize(640, 480);
            // 设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
            mediaRecorder.setVideoFrameRate(20);
            // 设置视频文件输出的路径
            mediaRecorder.setOutputFile(file_with.TestFile(file).getPath());
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
    public void delete(View v) {
        //TODO 删除按钮
        Message_Dialog editNameDialog = new Message_Dialog(Round_Video_.this,file_with.TestFile(file).getPath());
        //向对话框传递上下文和文件路径(用于删除）
        editNameDialog.show(getFragmentManager(), "EditNameDialog");
    }
    public void upload(View v) {
        //TODO　上传按钮
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("isprogress", 1);
        map1.put("progress", 25);
        map1.put("max", 100);
        map1.put("textsize", 25);
        map1.put("upload", 0);
        map1.put("color", Color.rgb(555, 333, 333));
        try {
            URL url=new URL("http://192.168.1.112:1103/");
            Http_UploadFile_ http_uploadFile_=new Http_UploadFile_(mHandler,new File("/sdcard/RoundVideo/video.mp4"),url,"Login","44|99");
        //    http_uploadFile_.post();
        Pop_Img.Builder p = new Pop_Img.Builder(Round_Video_.this, map1,http_uploadFile_);
         p.setPositiveButton("[潮汕揭]初版\n问题反馈:(qq) 1213965634\n\n", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which)
          {
               dialog.dismiss();
          //需要将数据传给对话框然后实时更新进度条

         }
          // 设置你的操作事项


          });
        p.create().show();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

          /* WebView wv=new WebView(this);
            wv.getSettings().setJavaScriptEnabled(true);
            //设置可操作js
            wv.addJavascriptInterface(new Mytest(),"test");
        wv.loadUrl("http://192.168.1.112:1103/home/with/Try-videos-master/login.js");
            wv.loadUrl("javascript : router.post('cat')" );
        */
            }
    public void edit(View v) throws MalformedURLException {
        //TODO 编辑按钮
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("isprogress", 1);
        map1.put("progress", 25);
        map1.put("max", 100);
        map1.put("textsize", 25);
        map1.put("upload", 1);
        map1.put("color", Color.rgb(555, 333, 333));
        URL url=new URL("http://192.168.1.112:1103/video");
        //////TODO　暂时先放着
        Http_UploadFile_ http_uploadFile_=new Http_UploadFile_(Round_Video_.this, mHandler
                ,url
                , "0"//注册
                , "ToKen=");

        Pop_Img.Builder p = new Pop_Img.Builder(Round_Video_.this, map1,http_uploadFile_);
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
        p.setUploadclick(0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        p.create().show();
        int count = 100;
        task1 t = new task1(count, p);
        //新建一个方法
        mTimer.schedule(t, 0, 1000);
        //设每一秒调用一次


    }
//////////////////sucess Onclick

    class task1 extends TimerTask {
        public View v;
        int x, y;
        boolean b;
        int count;
        Pop_Img.Builder bar;

        public task1(int count, Pop_Img.Builder bar) {
            this.count = count;
            this.bar = bar;
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
    }

    ;

    public class Mytest {
        public int test() {
            return 1000;
        }
    }


    protected DisplayMetrics getScreenWH() {
        DisplayMetrics dMetrics = new DisplayMetrics();
        dMetrics = this.getResources().getDisplayMetrics();
        return dMetrics;
    }


}



