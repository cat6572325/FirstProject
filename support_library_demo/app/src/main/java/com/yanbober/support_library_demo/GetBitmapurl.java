package com.yanbober.support_library_demo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.util.LruCache;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yanbober.support_library_demo.DataHelpers.DataHelper;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
                    //写如数据库后加入到缓存
                    lruCache.put(url,bitmap);
                }
                break;
        }
    }
};
    String url;
    RelativeLayout relativeLayout;
    ImageView imageView=null;
    HashMap<String,Object> map;
    RecyclerView rv;
    Set<NewAsyncTask> mAsynctask;
    LruCache<String,Bitmap> lruCache;//
    Context context;
    public GetBitmapurl()
    {
        int maxMemory=(int)Runtime.getRuntime().maxMemory();
        int cacheSize=maxMemory/4;//指定使用可用内存4分之1
        lruCache=new LruCache<String,Bitmap>(cacheSize)
        {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //在每次存入缓存时调用，返回一个bitmap正确大小
                return value.getByteCount();
            }
        };//初始化缓存区

        mAsynctask=new HashSet<>();
    }
    public void addBitmapToCache(String url,Bitmap bitmap)
    {
        if (lruCache.get(url)==null)
        {//如果缓存中没有这张图片
            //加入到缓存
            lruCache.put(url,bitmap);//key和图片
        }
    }
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
    Bitmap bitmap=lruCache.get(url);
    //通过验证url来获取头像
    if (bitmap!=null)
    {//如果缓存中由这张图片
        if(imageView!=null)
            imageView.setImageBitmap(bitmap);
    }else {//否则通过联网下载
        new Thread() {
            @Override
            public void run() {
                super.run();
                Bitmap bitmap = getbitmap(url);
                if (bitmap != null) {
                    Message msg = Message.obtain();
                    msg.obj = bitmap;
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                }
            }
        }.start();
    }
}
//////////////////////////////////////////////////////以上设置头像/////////////////////////////////////////////////
//////////////////////////////////////////////////////以下设置可见item/////////////////////////////////////////////////
public void loadListViewImage(int start,int end,RecyclerView recyclerView,Context context,HashMap<String ,Object> map)
{
    this.rv=recyclerView;
    this.context=context;
for (int i=start;i<end;i++)
{
    String url=null;
    if(map.get("?").equals("AgendaFragment"))
     url=AgendaFragment.urls[i];
    if(map.get("?").equals("InfoDetailsFragment"))
        url=InfoDetailsFragment.urls[i];
    if(map.get("?").equals("AgendaFragment"))
        url=AgendaFragment.urls[i];

    if (url!=null) {
        Bitmap bitmap = lruCache.get(url);//尝试获取缓存中的某张图片
        if (bitmap == null) {//如果缓存中没有这张图片　
            NewAsyncTask newAsyncTask = new NewAsyncTask(url);
            newAsyncTask.execute(url);
            mAsynctask.add(newAsyncTask);//将加载网络的线程实例加入集中，一遍管理

        } else {
            RelativeLayout relativeLayout = (RelativeLayout) rv.findViewWithTag(url);//获得(url) 这个绑定这个tag的Relativelayout
            Drawable drawable = new BitmapDrawable(bitmap);
            relativeLayout.setBackground(drawable);//设置成功
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale___);
            relativeLayout.startAnimation(animation);
        }
    }
}
}
//////////////////////////////////////////////////////以上置可见item////////////////////////////////////////////////
//////////////////////////////////////////////////////取消所有正在进行的任务////////////////////////////////////////////////
    public void cancelAllTask()
    {
        if (mAsynctask!=null)
        {
            for (NewAsyncTask task : mAsynctask)
            {
                task.cancel(false);
            }
        }
    }
//////////////////////////////////////////////////////取消所有正在进行的任务////////////////////////////////////////////////
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

            Log.e("联网或图片加载失败",e.toString());


        }
return  null;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void ShowBitmapByAsyncTask(RecyclerView recyclerView,String url)
    {
     /*   Bitmap newBitmap=loadImageViewurl(url);
        if (newBitmap==null)
        {//如果缓存中没有这张图片，则去联网下载
            new NewAsyncTask(url).execute(url);
        }else
        {

        }*/

    }
    private class NewAsyncTask extends AsyncTask<String ,Integer, Bitmap>
    {

        String url;
        public NewAsyncTask(String url)
        {

            this.url=url;
        }
        @Override
        protected Bitmap doInBackground(String... strings) {
          Bitmap bitmap=getbitmap(strings[0]);
            //联网获取图片
            if (bitmap!=null)
            {//如果正确获得图片则加入缓存中，如果应用受到一些冲突或者kill就会清空所有内存，则程序崩溃
                addBitmapToCache(url,bitmap);
            }

            return bitmap;
            //返回最终参数
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //取得最终下载的图片
            RelativeLayout relativeLayout=(RelativeLayout)rv.findViewWithTag(url);
            //取得绑定该tag的relativelayout
            if (relativeLayout!=null && bitmap!=null)
            {//如果都取得
                Drawable drawable = new BitmapDrawable(bitmap);
                relativeLayout.setBackground(drawable);
                //设置成功
                Animation animation= AnimationUtils.loadAnimation(context,R.anim.scale___);
                relativeLayout.startAnimation(animation);

            }
            else
            {
                //图片加载失败
                Log.e("图片加载失败","控件或图片为空");
            }
        }
    }
}
