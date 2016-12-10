package com.yanbober.support_library_demo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yanbober.support_library_demo.DataHelpers.DataHelper;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by cat6572325 on 16-12-9.
 */
public class GetBitmapurl {
public Handler mHandler=new Handler()
{

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 0:
            if (relativeLayout.getTag().equals(url)) {
                Drawable drawable = new BitmapDrawable((Bitmap) msg.obj);
                relativeLayout.setBackground(drawable);

            }
                break;
            case 1:
                //设置Imageview
                if (imageView.getTag().equals(url))
                {
                    User user=new User();
                    Bitmap bitmap=(Bitmap)msg.obj;
                    SQLiteDatabase db = (SQLiteDatabase)map.get("sql");
                    DataHelper dataserver=(DataHelper)map.get("dataserver");

                    imageView.setImageBitmap(bitmap);
                    user.headBitmap = bitmap;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] bytes1 = baos.toByteArray();
                    String bitmapstr = Base64.encodeToString(bytes1, Base64.DEFAULT);

                    dataserver.inst(db, user.phone
                            + "|" + user.pas
                            + "|" + user.name
                            + "|" + bitmapstr
                            + "|" + user.token
                            + "|1|"
                            + user._id, (Context)map.get("Context"));
                }
                break;
        }
    }
};
    String url;
    RelativeLayout relativeLayout;
    ImageView imageView=null;
    HashMap<String,Object> map;
    public void loadurl(final String url, RelativeLayout imageView)
    {

        this.relativeLayout=imageView;
        this.url=url;
        new Thread(){
            @Override
            public void run() {
                super.run();
                Bitmap bitmap=getbitmap(url);
                if (bitmap!=null) {
                    Message msg = Message.obtain();
                    msg.obj = bitmap;
                    msg.what=0;
                    mHandler.sendMessage(msg);
                }
            }
        }.start();
    }
///////////////////////////////////////////////////////以下设置ImageView///////////////////////////////////////////
public void loadImageViewurl(final String url, ImageView imageView, HashMap<String,Object> map)
{

    this.imageView=imageView;
    this.url=url;
    this.map=map;
    new Thread(){
        @Override
        public void run() {
            super.run();
            Bitmap bitmap=getbitmap(url);
            if (bitmap!=null) {
                Message msg = Message.obtain();
                msg.obj = bitmap;
                msg.what=1;
                mHandler.sendMessage(msg);
            }
        }
    }.start();
}



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Bitmap getbitmap(String urlstring)
    {
        Bitmap bitmap;
        try {
            URL url = new URL(urlstring);

            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = new BufferedInputStream(conn.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
            conn.disconnect();
            return bitmap;
        } catch (IOException e) {

            Log.e("在获取首页的列表截图时",e.toString());


        }
return  null;
    }
}
