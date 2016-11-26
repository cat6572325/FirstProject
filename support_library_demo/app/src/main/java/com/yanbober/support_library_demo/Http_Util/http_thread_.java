package com.yanbober.support_library_demo.Http_Util;

import android.content.*;
import android.graphics.*;
import android.media.*;
import android.os.*;
import android.provider.*;
import android.util.*;
import com.okhttplib.*;
import com.okhttplib.annotation.*;
import com.okhttplib.callback.*;
import com.socks.okhttp.plus.*;
import com.socks.okhttp.plus.listener.*;
import com.socks.okhttp.plus.model.*;
import com.yanbober.support_library_demo.*;
import java.io.*;
import java.util.*;
import okhttp3.*;
import org.json.*;

/**
 * Created by cat6572325 on 16-11-17.
 */
public class http_thread_ extends Thread {
        String str;
    Handler handler;
    String url,path,key;
	Context con;

    User user=new User();
    public com.squareup.okhttp.OkHttpClient client1=new com.squareup.okhttp.OkHttpClient();

    public http_thread_(Context con,String url,String path,Handler handler,String key)
    {
		this.con=con;
        this.str=str;
        this.handler=handler;
        this.url=url;
        this.path=path;
        this.key=key;



    }
    OkHttpClient okHttpClient = OkHttpProxy.getInstance();
    @Override
    public void run() {
        super.run();
       pul();
    }
    private void doUploadImg() {

        OkHttpUtilInterface okHttpUtil = OkHttpUtil.Builder()
                .setCacheLevel(CacheLevel.FIRST_LEVEL)
                .setConnectTimeout(25).build(this);
//一个okHttpUtil即为一个网络连接

        HttpInfo info = HttpInfo.Builder()
                .setUrl(url+user.token)
                .addUploadFile("file", path, new ProgressCallback() {
                    @Override

                    public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
                        // uploadProgress.setProgress(percent);
                        Log.e("ssss","上传进度：" + percent);

                        int i=percent;
                    }
                    @Override
                    public void onResponseMain(String filePath,HttpInfo info)
                    {
                        String str=info.getRetDetail();
                        Log.e("上传信息",str);
                    }
                })
                .build();
        OkHttpUtil.getDefault(this).doUploadFileAsync(info);
    }


    public void pul()
    {
       final Message msg=new Message();
        final Bundle bundle=new Bundle();
       final File file = new File(path);//Environment.getExternalStorageDirectory(), "jiandan02.jpg");
        if (!file.exists()) {
          //  Toast.makeText(con.this, "File not exits！", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> param = new HashMap<>();
        param.put("file","videofile");
        Pair<String, File> pair = new Pair(key, file);
        OkHttpProxy
                .upload()
                .url(url)
                .file(pair)
                .setParams(param)
                .setWriteTimeOut(20)
                .start(new UploadListener() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("完成", e.toString());
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        String str=response.body().string();
						try{
						JSONObject js=new JSONObject(str);
						
						
                        Log.e("aaaa",str);
                        Log.e("完成", String.valueOf(response.isSuccessful()));
                        bundle.putString("?","success");
                        bundle.putString("!",str);
                        msg.what=0;
						msg.setData(bundle);
                        handler.sendMessage(msg);
						File f=new File("/sdcard/RoundVideo/RoundImage.png");
						//暂时创建一个没有内容的视频截图路口

						bitmap2File(getVideoThumb(file.getPath()),f);
						//创建截图
						Http_UploadFile_ http=new Http_UploadFile_(handler,f,"http://trying-video.herokuapp.com/user/videophoto/"+js.getString("_id")+"&token="+user.token,"3","");
						Thread x=new Thread(http);
						x.start();
						//开始上传截图
						}catch(JSONException e)
						{
							
						}
                    }


                    @Override
                    public void onSuccess(okhttp3.Response response) {
                        try {

                            String str=response.body().string();
							
								JSONObject js=new JSONObject(str);
							
							  bundle.putString("?", "success");
                            bundle.putString("!",str);
                            msg.what=0;
							msg.setData(bundle);
                          //  handler.sendMessage(msg);
							
							
							
                        } catch (IOException e) {
                            e.printStackTrace();
                        }catch (JSONException e) {}
						
                    }

                    @Override
                    public void onFailure(Exception e) {
                      //  tv_response.setText(e.getMessage());
                        Log.e("bbbb",String.valueOf(e));
                        bundle.putString("?","error");
                       bundle.putString("!",e.toString());
                        msg.what=0;
						msg.setData(bundle);
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onUIProgress(Progress progress) {
                        int pro = (int) ((progress.getCurrentBytes() + 0.0) / progress.getTotalBytes() * 100);
                        if (pro > 0) {
							Bundle b=new Bundle();
                         int o=pro;
                            Log.e("sssss",String.valueOf(pro));
							
							//View_One view_one = new View_One(con, String.valueOf(pro));
							
							Round_Video_ roundv=(Round_Video_)con;
							roundv.p.OnProgressChanged(pro);
                           b.putString("?","ing");
                            msg.arg1=pro;
                            msg.what=0;
							msg.setData(b);
								
                          //  handler.sendMessage(msg);
                        }
                      //  KLog.d("pro = " + pro + " getCurrentBytes = " + progress.getCurrentBytes() + " getTotalBytes = " + progress.getTotalBytes());
                    }
                });
				
				
    }
	/**
	 * Bitmap保存成File
	 *
	 * @param bitmap input bitmap
	 * @param name output file's name
	 * @return String output file's path
	 */
	public static String bitmap2File(Bitmap bitmap,File f) {
		//File f = new File(Environment.getExternalStorageDirectory() + name + ".jpg");
		if (f.exists()) f.delete();
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			return null;
		}
		return f.getAbsolutePath();
	}
	
/*
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
	public interface OnInvalitorProgress
    {
       // public void Onebent(String str);
    }
}
