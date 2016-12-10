package com.yanbober.support_library_demo.Http_Util;

import android.content.*;
import android.graphics.*;
import android.media.*;
import android.os.*;
import android.provider.*;
import android.util.*;
import com.google.gson.*;
import com.okhttplib.*;
import com.okhttplib.annotation.*;
import com.okhttplib.callback.*;
import com.socks.okhttp.plus.*;
import com.socks.okhttp.plus.listener.*;
import com.socks.okhttp.plus.model.*;
import com.yanbober.support_library_demo.*;
import com.yanbober.support_library_demo.Message_S.*;
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
	HashMap<String,Object> mapvideo=null;
	Context con;
	String _videoid=null;
	public OkHttpClient client = new OkHttpClient();
    User user=new User();
	View_One view_one=null;
    public com.squareup.okhttp.OkHttpClient client1=new com.squareup.okhttp.OkHttpClient();

    public http_thread_(Context con,String url,String path,Handler handler,HashMap<String,Object> data)
    {
		this.con=con;
        this.str=str;
        this.handler=handler;
        this.url=url;
        this.path=path;
        this.mapvideo=data;



    }
    OkHttpClient okHttpClient = OkHttpProxy.getInstance();
    @Override
    public void run() {
        super.run();
		switch((Integer)mapvideo.get("count"))
		{
			case 0:
				pul();
				break;
			case 1:
				videodata();
				break;
			case 2:
				loadvideopng("s");
				break;
		}

	   
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
		key="videofile";
       final Message msg=new Message();
        final Bundle bundle=new Bundle();
       final File file = new File(path);//Environment.getExternalStorageDirectory(), "jiandan02.jpg");
        if (!file.exists()) {
          //  Toast.makeText(con.this, "File not exits！", Toast.LENGTH_SHORT).show();
            return;
        }
		String str=path.substring(0,path.lastIndexOf(".")-1);
		File f=new File(str+((int)(1+Math.random()*(10-1+1)))+".png");
	//	compressImage(getVideoThumb(file.getPath()),f.getPath());
		//改后缀名
		bitmap2File(getVideoThumb(file.getPath()),f);
		//创建截图
		path=f.getPath();
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
                        Log.e("错误", e.toString());
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        String str=response.body().string();
						try{
						JSONObject js=new JSONObject(str);
                        Log.e("video",str);
                        Log.e("完成", String.valueOf(response.isSuccessful()));
                   		_videoid=js.getString("_id");
					url="http://trying-video.herokuapp.com/user/videophoto/"+js.getString("_id")+"?token="+user.token;
							mapvideo.put("key","vidphotofile");//改key上传视频截图

							loadvideopng(js.getString("_id"));
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
                            Log.e("vide进度",String.valueOf(pro));
							
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
	public void videodata()
	{
		JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str = null;

		Response response = null;
		
        try {
            /*  "uploader" : ${uploader},    //上传者(string)
			"title" : ${title},    //标题(string)
			"introduction" : ${introduction},    //简介(string)
			"price" : ${price},    //价格(Number)
			"paidppnumber" : ${paidppnumber},    //付款人数(Number)
			"concernednumber" : ${concernednumber}    //收藏人数(Number)
		}
		>>  返回 status: '信息已以相同id保存' */


			/*
			    "uploader" : ${uploader},    //上传者(string)
    "title" : ${title},    //标题(string)
    "introduction" : ${introduction},    //简介(string)
    "price" : ${price},    //价格(Number)
    "paidPerson" : ${paidPerson},    //付款人ID(Array)
    "cocerPerson" : ${cocerPerson},    //收藏人ID(Array)
    "paidppnumber" : ${paidppnumber},    //付款人数(Number)
    "concernednumber" : ${concernednumber}    //收藏人数(Number)
}
>>  返回 status: '信息已以相同id保存'
			 */
			//Log.e("uploader", mapvideo.get("uploader").toString());
					Log.e("title", mapvideo.get("title").toString());
					Log.e("introduction", mapvideo.get("introduction").toString());
					Log.e("price", mapvideo.get("price").toString());

			   /*POST   http://localhost:1103/user/video/detail/:_vid?token=${token}    /_vid为视频的id/

                                    {
                                        "uploader" : ${uploader},    //上传者(string)
                                        "title" : ${title},    //标题(string)
                                        "introduction" : ${introduction},    //简介(string)
                                        "price" : ${price},    //价格(Number)
                                        "paidPerson" : ${paidPerson},    //付款人ID(string)
                                        "cocerPerson" : ${cocerPerson},    //收藏人名字(string)
                                        "paidppnumber" : ${paidppnumber},    //付款人数(Number)
                                        "concernednumber" : ${concernednumber}    //收藏人数(Number)
                                    }
                                    >>  返回 status: '信息已以相同id保存'
                                    */
			JSONObject jsonObject1=new JSONObject();
			jsonObject1.put("introduction","");

			RequestBody formBody = new FormBody.Builder()
				.add("uploader","")// mapvideo.get("uploader").toString())
				.add("title", mapvideo.get("title").toString())
				.add("introduction", new JSONObject().put("introduction",mapvideo.get("introduction").toString()).toString())
				.add("price", mapvideo.get("price").toString())
				.add("paidppnumber","")// mapvideo.get("paidppnumber").toString())
				.add("concernednumber", "")//mapvideo.get("concernednumber").toString())
				
				
				.build();

            Request request = new Request.Builder()
				.url(url)
				.post(formBody)
				.build();
Log.e("url",url);
            try
			{
				response = client.newCall(request).execute();
			}
			catch (IOException e)
			{}
			//if (!response.isSuccessful())
          final String  str1 = response.body().string();
			Log.e("发送视频信息时返回了",str);
			jsonObject=new JSONObject(str);
		Bundle bundle=new Bundle();
			Message msg=new Message();
			bundle.putString("?","success");
			bundle.putString("!",str);
			msg.what=0;
			msg.setData(bundle);
			user.notLoadforVideo_list=null;//清空未上传视频信息,表示已上传
			handler.sendMessage(msg);






		} catch (IOException e) {
            e.printStackTrace();
			Log.e("videodata",e.toString());
			
			
        } catch(JSONException e)
		{
			Log.e("上传视频信息时",e.toString());
		}
		finally {


        }
		
	}
	public void loadvideopng(String vid)
    {
		final String v_id=vid;
		final Message msg=new Message();
        final Bundle bundle=new Bundle();
		final File file = new File(path);//Environment.getExternalStorageDirectory(), "jiandan02.jpg");
        //视频地址
			//暂时创建一个没有内容的视频截图路口
//		Log.e("截图路径",str);
		Log.e("文件路径",file.getPath());
		if (!file.exists()) {
			//  Toast.makeText(con.this, "File not exits！", Toast.LENGTH_SHORT).show();
			
            return;
        }

        Map<String, String> param = new HashMap<>();
       // param.put("file","videofile");
        Pair<String, File> pair = new Pair(mapvideo.get("key").toString(), file);
        OkHttpProxy
			.upload()
			.url(url)

			.file(pair)
			.setParams(param)
			.setWriteTimeOut(20)

			.start(new UploadListener() {
			@Override
			public void onFailure(Call call, IOException e) {
				Log.e("错误", e.toString());
			}
				
				@Override
				public void onResponse(Call call, okhttp3.Response response) throws IOException {
					String str=response.body().string();
					

                        Log.e("videophonto",str);
                        Log.e("完成", String.valueOf(response.isSuccessful()));
					HashMap<String,Object> map =new HashMap<String,Object>();
					handler=(Handler) mapvideo.get("handler");

					 url = "http://trying-video.herokuapp.com/user/video/detail/" + v_id + "?token=" + user.token;
					if((Integer)mapvideo.get("count")!=2)
					videodata();

						
				}


				@Override
				public void onSuccess(Response response) {
					Log.e("上传视频截图成功的时候","这个success可以用");
				}

				@Override
				public void onFailure(Exception e) {
					Log.e("上传视频截图的时候",e.toString());
				}

				@Override
				public void onUIProgress(Progress progress) {
					int pro = (int) ((progress.getCurrentBytes() + 0.0) / progress.getTotalBytes() * 100);
					Log.e("上传视频截图进度",String.valueOf(pro));
						}
				});
				
					
				}
				
				
	
	/**
	 * Bitmap保存成File
	 *
	 *
	 *
	 *
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
