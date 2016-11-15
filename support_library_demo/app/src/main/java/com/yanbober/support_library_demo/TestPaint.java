package com.yanbober.support_library_demo;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.support.design.widget.FloatingActionButton;
import android.widget.Button;

/**
 * Created by cat6572325 on 16-11-13.
 */


public class TestPaint extends Activity {
    // handler类接收数据
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

           switch (msg.what)
           {
               case 1:
                if(count<100) {
                    count++;
                    textView1.setX(textView1.getX() - 2);
                }
                   break;
               case 2:
                   if(count>0) {
                       count--;
                       textView1.setX(textView1.getX() + 2);
                   }

                   break;
            }
        };
    };
    Button textView,textView1,textView2;
    Animation uti2,uti1,uti1back,uti2back;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t);
        textView=(Button) findViewById(R.id.text);
        textView1=(Button) findViewById(R.id.text1);
        textView2=(Button) findViewById(R.id.text2);
        Paint paint=new Paint();
        Animation uti=AnimationUtils.loadAnimation(this,R.anim.scale___);
        uti1=AnimationUtils.loadAnimation(this,R.anim.left_button);
        uti2=AnimationUtils.loadAnimation(this,R.anim.top_button);
        textView1.setVisibility(View.INVISIBLE);
        textView2.setVisibility(View.INVISIBLE);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count==100)
                {new Thread(new ThreadShowback()).start();}else

                {textView1.setVisibility(View.VISIBLE);new Thread(new ThreadShow()).start();}

            }
        });

    }
    // 线程类
    class ThreadShow implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (true) {
                try {
                    if (count<100) {
                        Thread.sleep(1);
                        mHandler.sendEmptyMessage(1);
                    }else {
                        break;
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println("thread error...");
                }
            }
        }
    }
    class ThreadShowback implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (true) {
                try {
                    if (count>0) {
                        Thread.sleep(1);
                        mHandler.sendEmptyMessage(2);
                    }else
                    {
                        textView1.setVisibility(View.INVISIBLE);
                        break;
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println("thread error...");
                }
            }
        }
    }


}
