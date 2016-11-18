package com.yanbober.support_library_demo.Http_Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cat6572325 on 16-11-17.
 */
public class http_thread_ extends Thread {
        String str;
    Handler handler;
    URL url;


    public http_thread_(URL url,String str,Handler handler)
    {
        this.str=str;
        this.handler=handler;
        this.url=url;
    }
    @Override
    public void run() {
        super.run();
        try {
            //TODO 请求一张图片
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();

            conn.setReadTimeout(5000);
            //设置链接超时
            conn.setRequestMethod("POST");
            //设置请求方式
            conn.setDoInput(true);
            // 设置为输入流
            InputStream inputStream=conn.getInputStream();
            String FileName=String.valueOf(System.currentTimeMillis());
            //获得系统时间
            int length=conn.getContentLength();
            //获得文件总长度
          //  conn.setRequestProperty("Range","bytes="+"从哪个位置写入"+"-"+"到哪个位置");
            //请求，
            conn.setRequestProperty("Content-Type","multipart/from-data;boundy="+"分割线");
            //最后的参数是指开始拼接数据，和结束拼接数据的标识符
            //RandomAccessFile accessFile=new RandomAccessFile(new File("/sdcard/RoundVideo/video.mp4"),"rwd");
            //accessFile.seek(0);;
            //从文件的０位开始输入
            Bitmap bitmap= BitmapFactory.decodeFile("/sdcard/Httpbitmao");
            //获得bitmap
            FileOutputStream fileOutputStream=null;
            fileOutputStream=new FileOutputStream("/sdcard/httpbitmap");
            //获得文件输出路径
            int readLeng;
            byte[] bt=new byte[5*1024];
            if(fileOutputStream!=null)
            {//如果文件不为空
                while ((readLeng=inputStream.read(bt))!=-1)
                    //判断是否到输入流最后
                    fileOutputStream.write(bt,0,readLeng);
                //不断写入文件
            }



            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            {//判断sdcard1是否存在

            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    //向接口发送数据
                    //webView.LoadData(sb.toString,"text/html;charset=utf-8",null);
                    //显示一个页面，，，不需要接口方设置方法，可直接调用接口方的对象并操作逻辑
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
