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
        
    }


}
