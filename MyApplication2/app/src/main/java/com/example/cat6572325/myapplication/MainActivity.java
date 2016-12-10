package com.example.cat6572325.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Handler mHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            img.setImageBitmap((Bitmap)msg.obj);
        }
    };
ImageView img;
    Bitmap bitmap2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img=(ImageView)findViewById(R.id.img);
img.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        new Thread()
        {
            @Override
            public void run() {
                super.run();

                Message msg=new Message();
                msg.obj=decodeSampleBitmapFromStream(BitmapFactory.decodeResource(MainActivity.this.getResources(),R.drawable.image));
                mHandler.sendMessage(msg);


            }
        }.start();

    }
});
    }
    /*
压缩
 */
    private Bitmap decodeSampleBitmapFromStream(Bitmap bitmap)
    {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        int options =80;
        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        while (baos.toByteArray().length/1024>100)
        {
            baos.reset();
            options-=10;
            bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        }
        ByteArrayInputStream isBm=new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap1=BitmapFactory.decodeStream(isBm,null,null);

        return bitmap1;
    }

}
