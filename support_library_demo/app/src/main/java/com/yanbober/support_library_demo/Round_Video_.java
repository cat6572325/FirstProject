package com.yanbober.support_library_demo;

import android.annotation.TargetApi;
import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.*;
import android.hardware.*;
import android.hardware.Camera.Size;
import android.media.*;
import android.media.MediaRecorder.*;
import android.media.audiofx.EnvironmentalReverb;
import android.os.*;

import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.*;
import android.support.v7.app.AlertDialog;
import android.system.ErrnoException;
import android.text.Layout;
import android.util.DisplayMetrics;

import android.util.Log;
import android.util.TimeUtils;
import android.view.*;
import android.view.View.*;
import android.webkit.WebView;
import android.widget.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.security.Policy;
import java.util.*;
import java.util.concurrent.TimeUnit;

import android.hardware.Camera;
import android.support.design.widget.*;

import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.OkHttpUtilInterface;
import com.okhttplib.annotation.CacheLevel;
import com.okhttplib.callback.ProgressCallback;
import com.yanbober.support_library_demo.Http_Util.Http_UploadFile_;
import com.yanbober.support_library_demo.Http_Util.http_thread_;
import com.yanbober.support_library_demo.Message_S.View_One;

import java.io.IOException;
import java.util.List;


import android.app.Activity;
import android.content.Context;

import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Size;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Round_Video_ extends Activity

{
    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case 0:

                    p.OnProgressChanged(101);
                    if (bundle.getString("?").equals("success")) {

                    }
                    if (bundle.getString("?").equals("ing")) {
                    }
                    if (bundle.getString("?").equals("error")) {
                    }

                    finish();
                    break;


                case 1:

                    ResetCamera();
                    break;


                case 3:

                    if (bundle != null)
                        if (bundle.getString("?").equals("已保存")) {
                            dialog.cancel();
                            finish();
                        }
                    break;

            }
        }
    };

    User user = new User();
    RoundProgressBar bar;
    static int message = 0;
    private Button startButton, stopButton, playButton;
    private SurfaceView mSurfaceView;
    private boolean isRecording;
    private MediaRecorder mediaRecorder;
    private Camera camera;
    private SurfaceHolder surfaceHolder1;
    public RelativeLayout HideLayout, bottom_hide_layout;
    static int flag = 0, recordarl = 0, turncamera = 0;
    int count = 0;
    Timer mTimer = new Timer();
    TimeUnit t;
    File file = new File("/sdcard/RoundVideo/RoudVideos" + randomProdoction() + ".3gp");
    File_with_ file_with;
    String COMMA_PATTERN = ",";
    TextView top_time;
    int minute, min;
    Size size;
    task Prog_task;
    ImageView sound, turnC, round_back_img, rounding_time_img, round_delete, round_upload, round_edit;
    AlertDialog.Builder waitdialog;
    AlertDialog dialog;
    public Pop_Img.Builder p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);

        setContentView(R.layout.round_video_layout);
        mSurfaceView = (SurfaceView) findViewById(R.id.camera_surfaceview);
        bar = (RoundProgressBar) findViewById(R.id.round_bar);
        bar.setMax(300);
        // 声明Surface不维护自己的缓冲区，针对Android3.0以下设备支持
        mSurfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        initView();


        bar.setOnClickListener(new OnClickListener() {
            public void onClick(View v)

            {////点击开始录像
                if (flag == 0) {

                    if (camera != null)
                        sound.setVisibility(View.INVISIBLE);
                    turnC.setVisibility(View.INVISIBLE);

                    if (camera != null) {
                        camera.stopPreview();
                        camera.release();
                        camera = null;
                    }
                    bar.setBackgroundResource(R.drawable.roundbuttoning);
                    start();
                    flag = 1;
                    setprog();


                } else {//点击结束录像
                    stop();
                    bar.setBackgroundResource(R.drawable.roundbutton);
                    flag = 0;
                    Prog_task.cancel();
                    mTimer.cancel();
                }
                bar.invalidate();

            }
        });

    }//oncreate()


    public void initView() {
        HideLayout = (RelativeLayout) this.findViewById(R.id.HideLayout);
        top_time = (TextView) this.findViewById(R.id.RoundTop_time);
        bottom_hide_layout = (RelativeLayout) this.findViewById(R.id.bottom_hide_layout);
        sound = (ImageView) this.findViewById(R.id.Round_sound);
        turnC = (ImageView) this.findViewById(R.id.Round_turn);
        round_back_img = (ImageView) this.findViewById(R.id.round_back);
        rounding_time_img = (ImageView) this.findViewById(R.id.rounding_time_img);
        round_delete = (ImageView) this.findViewById(R.id.round_delete);
        round_upload = (ImageView) this.findViewById(R.id.round_upload);
        round_edit = (ImageView) this.findViewById(R.id.round_edit);
        round_delete.setClickable(false);//删除按钮初始不可点击
        round_back_img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //初始隐藏这个布局
        HideLayout.setVisibility(View.INVISIBLE);
        //隐藏(不可见)
        mHandler.sendEmptyMessage(1);


    }//initView
    //开始录制前设置不把声音录进去
    public void SetsoundState(View view)
    {
        if (recordarl==1) {
            recordarl = 0;
            //录音
            Toast.makeText(Round_Video_.this,"开启录音",Toast.LENGTH_SHORT).show();
        }else {
            recordarl=1;
            //关闭录音
            Toast.makeText(Round_Video_.this,"关闭录音",Toast.LENGTH_SHORT).show();
        }
    }

    //开始录制前设置不把声音录进去
    private String randomProdoction() {
        int random = (int) Math.random() * 10;
        String str = null;
        Random ran = new Random(System.currentTimeMillis());
        for (int i = 0; i < 20; i++) {

            random = ran.nextInt(10);
            str += String.valueOf(random);
            //数字加一个字母
            switch (random) {
                case 0:
                    str += "a";
                    break;

                case 1:
                    str += "b";
                    break;

                case 2:
                    str += "c";
                    break;

                case 3:
                    str += "e";
                    break;

                case 4:
                    str += "f";
                    break;

                case 5:
                    str += "g";
                    break;

                case 6:
                    str += "h";
                    break;

                case 7:
                    str += "i";
                    break;
            }

        }
        return str;
    }
    private void setWaitdialog()
    {
        waitdialog=new android.support.v7.app.AlertDialog.Builder(Round_Video_.this,R.style.Dialog);
        LayoutInflater inflater=getLayoutInflater();
        final View layout=inflater.inflate(R.layout.login_wait_dialog,null);
        TextView textView=(TextView)layout.findViewById(R.id.text_dialog);

        textView.setText("加载中..");
        waitdialog.setView(layout);
        dialog=waitdialog.create();
        dialog.show();

    }
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

                    if (bar.getProgress() == 150)
                        maxtimemessage(bar);
                    if (bar.getProgress() == 200)
                        maxtimemessage(bar);
                    if (bar.getProgress() == 290)
                        maxtimemessage(bar);
                    if (bar.getProgress() == 300) {
                        stop();
                        mTimer.cancel();
                        cancel();
                    }else {
                        bar.setProgress(bar.getProgress() + 1);
                        min++;
                        if (min == 60) {
                            min = 0;
                            minute++;
                        }
                        if (min > 9) {
                            top_time.setText(minute + ":" + min);
                        } else {
                            top_time.setText(minute + ":0" + min);
                        }
                    }
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
        round_back_img.setBackgroundResource(R.drawable.close);
        round_back_img.setClickable(false);
        rounding_time_img.setBackgroundResource(R.drawable.times);
        try {

            if (file_with != null) {//如果先前已经申请过一个文件
                file_with.DeleteFile();
            }
            file_with = new File_with_();
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

            if (turncamera == 0) {
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            } else {
                camera = Camera.open(CameraInfo.CAMERA_FACING_FRONT);
            }
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
            if (recordarl == 0) {
                //等于0则设置录音
                mediaRecorder.setVideoSource(Camera.CameraInfo.CAMERA_FACING_BACK);
                //设置音频采集方式
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                //设置输出格式
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                //设置audio编码方式
            }
            mediaRecorder.setOrientationHint(90);
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
        rounding_time_img.setBackgroundResource(R.drawable.timer);
        bottom_hide_layout.setVisibility(View.INVISIBLE);
        //隐藏录制按钮的布局
        HideLayout.setVisibility(View.VISIBLE);
        //显示上层的上传按钮的布局
        //解除隐藏
        top_time.setText("录制完成");
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
        round_delete.setClickable(true);
        round_back_img.setClickable(true);
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

    public void ResetCamera() {


        //  mSurfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        try {
            if (camera != null) {
                camera.stopPreview();
                camera.release();
                camera = null;
            }
            if (turncamera == 0) {
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            } else {
                camera = Camera.open(CameraInfo.CAMERA_FACING_FRONT);
            }
            camera.setDisplayOrientation(90);
            camera.setPreviewDisplay(mSurfaceView.getHolder());
            camera.startPreview();


            //	Size pictureS = MyCampara.getInstance().getPictureSize(pictureSizes, 800);
            //	mParams.setPictureSize(pictureS.width, pictureS.height);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("初始化摄像头时",e.toString());
        }
    }

    //////////////////sucess Onclick
    public void Turncamera(View v) {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
        //先停下当前摄像
        if (turncamera == 0) {
            turncamera = 1;
        } else {
            turncamera = 0;
        }
        ResetCamera();
        // ResetCamera();
        //再重新开始
       /* int cameraCount = 0;
        CameraInfo cameraInfo = new CameraInfo();
        cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
            if (turncamera == 0) {
                //现在是后置，变更为前置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    camera.stopPreview();//停掉原来摄像头的预览
                    camera.release();//释放资源
                    camera = null;//取消原来摄像头
                    camera = Camera.open(i);//打开当前选中的摄像头
                    try {
                        deal(camera);
                        camera.setPreviewDisplay(surfaceHolder1);//通过surfaceview显示取景画面
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    camera.startPreview();//开始预览
                    turncamera = 1;
                    break;
                }
            } else {
                //现在是前置， 变更为后置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    camera.stopPreview();//停掉原来摄像头的预览
                    camera.release();//释放资源
                    camera = null;//取消原来摄像头
                    camera = Camera.open(i);//打开当前选中的摄像头
                    try {
                        deal(camera);
                        camera.setPreviewDisplay(surfaceHolder1);//通过surfaceview显示取景画面
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    camera.startPreview();//开始预览
                    turncamera = 0;
                    break;
                }
            }
}*/
    }

    public void record(View v) {//是否录音
        if (recordarl == 0) {
            recordarl = 1;
        } else {
            recordarl = 0;
        }
    }

    public void onInvalitorProgress(int counttime) {
        int y = counttime;
    }

    public void delete(View v) {
        //TODO 删除按钮

        if(file_with.GetFile().exists()) {
            Message_Dialog editNameDialog = new Message_Dialog(Round_Video_.this, file_with.TestFile(file).getPath(), v);
            //向对话框传递上下文和文件路径(用于删除）
            editNameDialog.show(getFragmentManager(), "EditNameDialog");
            bottom_hide_layout.setVisibility(View.INVISIBLE);
            turncamera = 0;
            ResetCamera();
        }else
        {
            Toast.makeText(Round_Video_.this,"没有视频可删除,需要录制吗？",Toast.LENGTH_SHORT).show();
        }
    }

    public void upload(View v) {
        //TODO　上传按钮
        /*对话框uploader: String,   //上传者
        title: String,  //视频名
                vdourl: String,   //视频路径
                introduction: String,   //简介
                price: Number,   //视频价格
                paidppnumber: Number,　 　//付费人数
        concernednumber: Number,   //收藏人数
                time: {                   // 创建时间*/

        User u = new User();
        if (u.notLoadforVideo_list != null) {

            round_delete.setClickable(false);
            round_upload.setClickable(false);
            round_edit.setClickable(false);

            HashMap<String, Object> map1 = new HashMap<String, Object>();
            map1.put("isprogress", 1);
            map1.put("progress", 0);
            map1.put("max", 100);
            map1.put("textsize", 25);
            map1.put("upload", 0);//０表示显示一个进度
            map1.put("color", Color.rgb(555, 333, 333));
            map1.put("count", 0);
            map1.put("key", "photofile");
            p = new Pop_Img.Builder(Round_Video_.this, map1, file_with);

            p.create().show();

            http_thread_ htt = new http_thread_(Round_Video_.this, "http://trying-video.herokuapp.com/user/video?token=" + user.token
                    , file_with.GetFile().getPath()
                    , mHandler
                    , user.notLoadforVideo_list.get(0));
            Thread c = new Thread(htt);
            c.start();
        } else {
            Toast.makeText(Round_Video_.this, "未填写视频信息，请先编辑", Toast.LENGTH_LONG).show();
            edit(null);
        }
          /* WebView wv=new WebView(this);
            wv.getSettings().setJavaScriptEnabled(true);
            //设置可操作js
            wv.addJavascriptInterface(new Mytest(),"test");
        wv.loadUrl("http://192.168.1.112:1103/home/with/Try-videos-master/login.js");
            wv.loadUrl("javascript : router.post('cat')" );
        */
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //
        // 按钮保存数据，未上传时按下返回键就会开始联网发送未上传视频的信息，发送保存成功后收到一个handler的message然后直接finish
        //大多需要的参数都在User的对象里了，一旦上传则清空
        //为保证点了开始录制的按钮后没有停止就想退出界面，在这里判断如果camera不为空，则释放
        if (camera != null) {
           camera=null;
        }

        User u = new User();
        if (u.notLoadforVideo_list != null) {
            setWaitdialog();
            HashMap<String, Object> map = u.notLoadforVideo_list.get(0);
            map.put("handler", mHandler);
            map.put("Context", Round_Video_.this);
            map.put("count", 20);
            map.put("vdoPath", file_with.GetFile().getPath());

            Http_UploadFile_ http_uploadFile_ = new Http_UploadFile_("http://trying-video.herokuapp.com/user/video/unput?token=" + u.token
                    , map
                    , "20");
            Thread c = new Thread(http_uploadFile_);
            c.start();

        }else {
            finish();
        }
    }

    public void edit(View v) {
        //TODO 编辑按钮

        try {
            HashMap<String, Object> map1 = new HashMap<String, Object>();
            map1.put("isprogress", 1);
            map1.put("progress", 25);
            map1.put("max", 100);
            map1.put("textsize", 25);
            map1.put("upload", 1);
            map1.put("color", Color.rgb(555, 333, 333));

            p = new Pop_Img.Builder(Round_Video_.this, map1, file_with);
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
                    //  Toast.makeText(Round_Video_.this,"click",Toast.LENGTH_SHORT).show();
                    //发送http请求，上传视频


                }
            });

            Dialog dialog=p.create();
            Window window=dialog.getWindow();
            window.setWindowAnimations(R.style.dialog_popupStyle);
                    dialog.show();
            int count = 100;
            //    task1 t = new task1(count);
            //新建一个方法
            //   mTimer.schedule(t, 0, 1000);
            //设每一秒调用一次
        } catch (Error e2) {
        }
    }

    //////////////////sucess Onclick
    class task1 extends TimerTask {
        public View v;
        int x, y;
        boolean b;
        int count;
        Pop_Img.Builder bar;

        public task1(int count) {
            this.count = count;

        }

        @Override
        public void run() {

            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    //  bar.OnProgressChanged(10);
                }
            });
        }
    }

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


    //TODO 视频处理　　vvvvvvvvvv
    private void exactorMedia(String... data) {
        FileOutputStream videoOutputStream = null;
        FileOutputStream audioOutputStream = null;
        MediaExtractor mediaExtractor = null;
        try {
            //分离的视频文件
            File videoFile = new File(data[1]);
            //分离的音频文件
            File audioFile = new File(data[2]);
            videoOutputStream = new FileOutputStream(videoFile);
            audioOutputStream = new FileOutputStream(audioFile);
            //源文件
            mediaExtractor.setDataSource(data[0]);
            //信道总数
            int trackCount = mediaExtractor.getTrackCount();
            int audioTrackIndex = -1;
            int videoTrackIndex = -1;
            for (int i = 0; i < trackCount; i++) {
                MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
                String mineType = trackFormat.getString(MediaFormat.KEY_MIME);
                //视频信道
                if (mineType.startsWith("video/")) {
                    videoTrackIndex = i;
                }
                //音频信道
                if (mineType.startsWith("audio/")) {
                    audioTrackIndex = i;
                }
            }

            ByteBuffer byteBuffer = ByteBuffer.allocate(500 * 1024);
            //切换到视频信道
            mediaExtractor.selectTrack(videoTrackIndex);
            while (true) {
                int readSampleCount = mediaExtractor.readSampleData(byteBuffer, 0);
                if (readSampleCount < 0) {
                    break;
                }
                //保存视频信道信息
                byte[] buffer = new byte[readSampleCount];
                byteBuffer.get(buffer);
                videoOutputStream.write(buffer);
                byteBuffer.clear();
                mediaExtractor.advance();
            }
            //切换到音频信道
            mediaExtractor.selectTrack(audioTrackIndex);
            while (true) {
                int readSampleCount = mediaExtractor.readSampleData(byteBuffer, 0);
                if (readSampleCount < 0) {
                    break;
                }
                //保存音频信息
                byte[] buffer = new byte[readSampleCount];
                byteBuffer.get(buffer);
                audioOutputStream.write(buffer);
                byteBuffer.clear();
                mediaExtractor.advance();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IOError e1) {


        } catch (UnknownError e4) {

        } finally {
            mediaExtractor.release();
            try {
                videoOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @SuppressWarnings("WrongConstant")
    private void muxerAudio(String fromPath, String toPath) {
        MediaExtractor mediaExtractor = new MediaExtractor();
        int audioIndex = -1;
        try {
            mediaExtractor.setDataSource(fromPath);
            int trackCount = mediaExtractor.getTrackCount();
            for (int i = 0; i < trackCount; i++) {
                MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
                if (trackFormat.getString(MediaFormat.KEY_MIME).startsWith("audio/")) {
                    audioIndex = i;
                }
            }
            mediaExtractor.selectTrack(audioIndex);
            MediaFormat trackFormat = mediaExtractor.getTrackFormat(audioIndex);
            MediaMuxer mediaMuxer = new MediaMuxer(toPath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            int writeAudioIndex = mediaMuxer.addTrack(trackFormat);
            mediaMuxer.start();
            ByteBuffer byteBuffer = ByteBuffer.allocate(500 * 1024);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();

            long stampTime = 0;
            //获取帧之间的间隔时间
            {
                mediaExtractor.readSampleData(byteBuffer, 0);
                if (mediaExtractor.getSampleFlags() == MediaExtractor.SAMPLE_FLAG_SYNC) {
                    mediaExtractor.advance();
                }
                mediaExtractor.readSampleData(byteBuffer, 0);
                long secondTime = mediaExtractor.getSampleTime();
                mediaExtractor.advance();
                mediaExtractor.readSampleData(byteBuffer, 0);
                long thirdTime = mediaExtractor.getSampleTime();
                stampTime = Math.abs(thirdTime - secondTime);
                ///     Log.e("fuck", stampTime + "");
            }

            mediaExtractor.unselectTrack(audioIndex);
            mediaExtractor.selectTrack(audioIndex);
            while (true) {
                int readSampleSize = mediaExtractor.readSampleData(byteBuffer, 0);
                if (readSampleSize < 0) {
                    break;
                }
                mediaExtractor.advance();

                bufferInfo.size = readSampleSize;
                bufferInfo.flags = mediaExtractor.getSampleFlags();
                bufferInfo.offset = 0;
                bufferInfo.presentationTimeUs += stampTime;

                mediaMuxer.writeSampleData(writeAudioIndex, byteBuffer, bufferInfo);
            }
            mediaMuxer.stop();
            mediaMuxer.release();
            mediaExtractor.release();
            // Log.e("fuck", "finish");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @SuppressWarnings("WrongConstant")
    private void muxerMedia(String fromPath, String toPath) {
        MediaExtractor mediaExtractor1 = new MediaExtractor();
        int videoIndex = -1;
        try {
            mediaExtractor1.setDataSource(fromPath);
            int trackCount = mediaExtractor1.getTrackCount();
            for (int i = 0; i < trackCount; i++) {
                MediaFormat trackFormat = mediaExtractor1.getTrackFormat(i);
                String mimeType = trackFormat.getString(MediaFormat.KEY_MIME);
                // 取出视频的信号
                if (mimeType.startsWith("video/")) {
                    videoIndex = i;
                }
            }
            //切换道视频信号的信道
            mediaExtractor1.selectTrack(videoIndex);
            MediaFormat trackFormat = mediaExtractor1.getTrackFormat(videoIndex);
            MediaMuxer mediaMuxer = new MediaMuxer(toPath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            //追踪此信道
            int trackIndex = mediaMuxer.addTrack(trackFormat);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 500);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            mediaMuxer.start();
            long videoSampleTime;
            //获取每帧的之间的时间
            {
                mediaExtractor1.readSampleData(byteBuffer, 0);
                //skip first I frame
                if (mediaExtractor1.getSampleFlags() == MediaExtractor.SAMPLE_FLAG_SYNC)
                    mediaExtractor1.advance();
                mediaExtractor1.readSampleData(byteBuffer, 0);
                long firstVideoPTS = mediaExtractor1.getSampleTime();
                mediaExtractor1.advance();
                mediaExtractor1.readSampleData(byteBuffer, 0);
                long SecondVideoPTS = mediaExtractor1.getSampleTime();
                videoSampleTime = Math.abs(SecondVideoPTS - firstVideoPTS);
                //   Log.d("fuck", "videoSampleTime is " + videoSampleTime);
            }
            //重新切换此信道，不然上面跳过了3帧,造成前面的帧数模糊
            mediaExtractor1.unselectTrack(videoIndex);
            mediaExtractor1.selectTrack(videoIndex);
            while (true) {
                //读取帧之间的数据
                int readSampleSize = mediaExtractor1.readSampleData(byteBuffer, 0);
                if (readSampleSize < 0) {
                    break;
                }
                mediaExtractor1.advance();
                bufferInfo.size = readSampleSize;
                bufferInfo.offset = 0;
                bufferInfo.flags = mediaExtractor1.getSampleFlags();
                bufferInfo.presentationTimeUs += videoSampleTime;
                //写入帧的数据
                mediaMuxer.writeSampleData(trackIndex, byteBuffer, bufferInfo);
            }
            //release
            mediaMuxer.stop();
            mediaExtractor1.release();
            mediaMuxer.release();
            //  Log.e("TAG", "finish");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //TODO 视频处理　　AAAAAAAAAAAAAA

    /**
     * 获取视频文件截图
     *
     * @param path 视频文件的路径
     * @return Bitmap 返回获取的Bitmap
     */
    public static Bitmap getVideoThumb(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        return media.getFrameAtTime();
    }

    /**
     * 获取视频文件缩略图 API>=8(2.2)
     *
     * @param path 视频文件的路径
     * @param kind 缩略图的分辨率：MINI_KIND、MICRO_KIND、FULL_SCREEN_KIND
     * @return Bitmap 返回获取的Bitmap
     */
    public static Bitmap getVideoThumb2(String path, int kind) {
        return ThumbnailUtils.createVideoThumbnail(path, kind);
    }

    public static Bitmap getVideoThumb2(String path) {
        return getVideoThumb2(path, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
    }
}