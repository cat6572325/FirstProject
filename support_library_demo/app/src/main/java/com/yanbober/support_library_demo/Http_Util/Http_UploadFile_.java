package com.yanbober.support_library_demo.Http_Util;

import android.os.*;
import android.util.*;
import com.yanbober.support_library_demo.*;
import com.yanbober.support_library_demo.Message_S.*;
import com.zhy.http.okhttp.*;
import java.io.*;
import java.util.*;
import okhttp3.*;
import org.json.*;


/**
 * Created by cat6572325 on 16-11-17.
 * <p/>
 * 上传文件的Http 请求信息分为4种
 * <p/>
 * 1、分界符
 * <p/>
 * -- +"-------------数字"+"\r\n"
 * <p/>
 * <p/>
 * 2、上传文件的相关信息
 * a-请求的参数名 b-上传的文件名 c-文件类型 d-
 * Content-Disposition:form-data;
 * name="file";
 * filename="abc.jpg"
 * <p/>
 * <p/>
 * 3、上传文件内容的字节流形式
 * Content-type:application/octet-stream
 * charset:utf-8
 * <p/>
 * <p/>
 * 4、文件全部上传完成后的结束符
 * -- +"-------------数字"+ -- +"\r\n"
 */
public class Http_UploadFile_ implements Runnable {
    Handler handler;
    File file;
    String sendMethod;
    public String url;
    String connectType = null, data = null ,token=null;
    Register_ regis;
    Login_ login;
    Round_Video_ round_video_;
    Modify_Password_ p;
    MainActivity MA = null;
    HashMap<String, Object> maphttp = null;
	View_One view_one;
    private final static String LINEND = "\r\n";
    private final static String BOUNDARY = "---------------------------7da2137580612"; //数据分隔线
    private final static String PREFIX = "--";
    private final static String MUTIPART_FORMDATA = "multipart/form-data";
    private final static String CHARSET = "utf-8";
    private final static String CONTENTTYPE = "application/octet-stream";
	User user=new User();
	
    public Http_UploadFile_(Handler handler, File file, String url, String connectType, String Data) {
        this.handler = handler;
        this.file = file;
        this.url = url;
        this.connectType = connectType;
        this.data = Data;

    }
public Http_UploadFile_(String url,HashMap<String ,Object> map,String cont)
{
	this.url=url;
	this.maphttp=map;
	this.connectType=cont;
}
    public Http_UploadFile_(Handler handler, String url, String path, String connectType) {
        this.handler = handler;
        this.url = url;
        this.sendMethod = path;
        this.connectType = connectType;
    }

    public Http_UploadFile_(Modify_Password_ p, Handler handler, String url, String connectType, String sendMethod, String data) {
        this.handler = handler;
        this.p = p;
        this.url = url;
        this.connectType = connectType;
        this.data = data;
        this.sendMethod = sendMethod;
    }


    public Http_UploadFile_(Register_ regis, Handler handler, String url, String connectType, String Sendmethod, String data) {
        this.regis = regis;
        this.handler = handler;
        this.sendMethod = Sendmethod;
        this.url = url;
        this.connectType = connectType;
        this.data = data;

    }

    public Http_UploadFile_(Login_ regis, Handler handler, String url, String connectType, String Sendmethod, String data) {
        this.login = regis;
        this.handler = handler;
        this.sendMethod = Sendmethod;
        this.url = url;
        this.connectType = connectType;
        this.data = data;

    }

    public Http_UploadFile_(MainActivity regis
            , Handler handler
            , String url
            , String connectType
            , String token
            , String data
			) {
        this.MA = regis;
		
        this.handler = handler;
        this.sendMethod = token;
        this.url = url;
        this.connectType = connectType;
        this.data = data;

    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public OkHttpClient client = new OkHttpClient();
    public com.squareup.okhttp.OkHttpClient client1 = new com.squareup.okhttp.OkHttpClient();

    public Http_UploadFile_(Round_Video_ regis, Handler handler, String url, String connectType, String sendMethod, HashMap<String, Object> Data) {
        this.round_video_ = regis;
        this.handler = handler;
        this.url = url;
        this.connectType = connectType;
        this.maphttp = Data;
        this.sendMethod = sendMethod;
    }

    public void Http_UploadFile(Handler handler, HashMap<String, Object> map) {
        this.maphttp = map;
        this.handler = handler;
    }

    /**
     * HTTP上传单个文件
     * 请求服务器的路径
     * 传递的表单内容
     * 单个文件信息
     */
    @Override
    public void run() {

        switch (Integer.parseInt(connectType)) {
            case 0:
                //注册
				register_type(url,data);
                break;
            case 1:
                //登录

                try {
                    login_type(url, data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } 

                break;
            case 2:
                //删除帐号

                break;
            case 3:
                //上传视频信息
				videodata();
                break;

            case 4:
                //获得视频列表
                try {
                    GetAllVideo(url, data);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case 5:
                //修改密码
                Modify_PATCH(url, data);
                break;
            case 6:
                //修改昵称
                Modify_name_PATCH(url,sendMethod); //"http://192.168.1.112:1103/user/nickname?token=" + sendMethod, data);
				
                break;
            case 7:
                //添加信息
                adddata_type(url, data);
                break;
            case 8:
                //查找个人信息
                
                    CheckData(url);
               

                break;
            case 9:

                    GetHeadImage(url);

                break;
            case 10:
               
                break;
            case 11:
                //修改支付密码


                break;
				case 12:
					//添加收藏
					addcollect();
					
					break;
					
					case 13:
						//获取收藏视频列表
						Get_collects();
						break;
        }
    }
	
	
	
	
	public void videodata() {
        OkHttpClient htt;
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str = null;
        try {
            //  "nickname" : ${nickname},    //昵称(String)
            //  "paypassword" : ${paypassword},    //支付密码(String)
            // "balance" : ${balance},    //余额(Number)
            //"notices" : ${notices},    //通知(String)  [存的只是通知id]
            //"collects" : ${collects}    //收藏(String)  [存的只是收藏id]
            RequestBody formBody = new FormBody.Builder()
				.add("uploader", maphttp.get("uploader").toString())
				.add("title", maphttp.get("title").toString())
				.add("introduction", maphttp.get("introduction").toString())
				.add("price", maphttp.get("price").toString())
				.add("paidppnumber", "0")
				.add("concernednumber", "0")

				.build();
            Request request = new Request.Builder()
				.url(url)
				.post(formBody)
				.build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException();
			str = response.body().string();
                    Log.e("videodata成功",str);
                    Log.e("token",url);

               
				}catch(IOException e)
				{
					Log.e("videodata1",e.toString());
				}
	}
	public String addcollect() {
        OkHttpClient htt;
        String str = null;
        try {
            RequestBody formBody = new FormBody.Builder()
				.add("cost", "0")
				
				.build();

            Request request = new Request.Builder()
				.url(url)
				.post(formBody)
				.build();

            Response response = client.newCall(request).execute();

            str = response.body().string();
Log.e("add",str);
Log.e("add",url);
Run_Video_ run=(Run_Video_)maphttp.get("context");
Message msg=new Message();
JSONObject jso=new JSONObject(str);
if(jso.getString("message").equals("已添加进收藏"))
{
msg.arg1=0;

}else
{
	msg.arg1=1;
}
msg.what=1;
run.mHandler.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
			view_one=new View_One((Run_Video_)maphttp.get("context"),e.toString());
			view_one.viewcreate();
        }catch(JSONException e)
		{
			view_one=new View_One((Run_Video_)maphttp.get("context"),e.toString());
			view_one.viewcreate();
		}
        return str;

    }
    public void Modify_paidPWD_PATCH() {
        JSONObject jsonObject = null;
        String breakStr = null;
        String[] strs = data.split("\\|");
        String urls = url;
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("paypassword", sendMethod)
					
                    .build();
            Request request = new Request.Builder()
                    .url(urls)
                    .tag(p)
					.head()
					
                    .patch(formBody)
                    .build();

            Bundle bundle = new Bundle();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful())
                throw new IOException(breakStr = response.body().toString());
            if (breakStr != null) {

                bundle.putString("?", "修改失败");
                bundle.putString("!", response.body().string());
            } else

            {

                jsonObject = new JSONObject(response.body().string());
                try {
                    bundle.putString("?", "修改失败");
                    bundle.putString("!", jsonObject.get("error").toString());
                } catch (JSONException e) {
                    bundle.putString("?", "修改成功");
                    //  bundle.putString("!",jsonObject.get("error").toString());

                }

            }
            //
            //JSONArray array=jsonObject.getJSONArray("result");
            //获取复数表单


            Message msg = new Message();
            msg.setData(bundle);
            //绑定一个数据集，显示成功与否
            msg.obj = jsonObject;
            //将一个JSON的对象发送
            msg.what = 0;
            handler.sendEmptyMessage(0);
            //System.out.println(response.body().string());
        } catch (IOException e) {

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void Modify_PATCH(String url, String data) {
        JSONObject jsonObject = null;
        String breakStr = null;
        String[] strs = data.split("\\|");
        String urls = url;
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("oldpassword", strs[0])
                    .add("userpassword", strs[1])

                    .build();
            Request request = new Request.Builder()
                    .url(urls)
                    .tag(p)
                    .patch(formBody)
                    .build();

            Bundle bundle = new Bundle();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful())
                throw new IOException(breakStr = response.body().toString());
            if (breakStr != null) {

                bundle.putString("?", "修改失败");
                bundle.putString("!", response.body().string());
            } else

            {

                jsonObject = new JSONObject(response.body().string());
                try {
                    bundle.putString("?", "修改失败");
                    bundle.putString("!", jsonObject.get("error").toString());
                } catch (JSONException e) {
                    bundle.putString("?", "修改成功");
                    //  bundle.putString("!",jsonObject.get("error").toString());

                }

            }
            //
            //JSONArray array=jsonObject.getJSONArray("result");
            //获取复数表单


            Message msg = new Message();
            msg.setData(bundle);
            //绑定一个数据集，显示成功与否
            msg.obj = jsonObject;
            //将一个JSON的对象发送
            msg.what = 0;
			
            handler.sendEmptyMessage(0);
            //System.out.println(response.body().string());
        } catch (IOException e) {

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void Modify_name_PATCH(String url, String data) {
        JSONObject jsonObject = null;
        String breakStr = null;
     //   String[] strs = data.split("\\|");
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("nickname", data)
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .patch(formBody)
                    .build();

            Bundle bundle = new Bundle();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful())
                throw new IOException(breakStr = response.body().toString());
            if (breakStr != null) {
                bundle.putString("?", "修改失败");
                bundle.putString("!", response.body().string());
            } else

            {
                jsonObject = new JSONObject(response.body().string());
                bundle.putString("?", "查找成功");
            }

            //JSONArray array=jsonObject.getJSONArray("result");
            //获取复数表单

            Message msg = new Message();
            msg.setData(bundle);
            //绑定一个数据集，显示成功与否
            msg.obj = jsonObject;
            //将一个JSON的对象发送
            msg.what = 0;
            msg.setData(bundle);
            handler.sendMessage(msg);
            //System.out.println(response.body().string());
        } catch (IOException e) {
            e.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");

    public void postFile(String filepath) {
        try {
            File file = new File("README.md");

            Request request = new Request.Builder()
			
                    .url("https://api.github.com/markdown/raw")
                    .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                    .build();

            Response response = client.newCall(request).execute();
            //  if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            //System.out.println(response.body().string());
        } catch (IOException e) {

        }
    }

    public String post(String url, String json) {
        OkHttpClient htt;
        String str = null;
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("phone", "")
                    .add("userpassword", "110")
                    .add("subject", "XXXXXXXXXXXXXXX")
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            Response response = client.newCall(request).execute();

            str = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;

    }

    public void login_type(String url, String json) throws InterruptedException {
        OkHttpClient htt;
        String[] strs = json.split("\\|");
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str = null, datas = null;
        Response response = null;
        Bundle bundleError = new Bundle();
        Message msgError = new Message();
        RequestBody formBody = new FormBody.Builder()
                .add("phone", strs[0])
                .add("userpassword", strs[1])
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException er) {

            View_One view_one = new View_One(login, er.toString());
        }
        try {
            if (!response.isSuccessful()) throw new IOException();
            str = response.body().string();
            //如果返回的是error
            jsonArray = new JSONArray(str);
		
			jsonObject=jsonArray.getJSONObject(0);
			token=jsonObject.getString("token");
            Bundle bundle = new Bundle();
            Message msg = new Message();
            msg.obj = jsonArray;
            msg.what = 0;
            bundle.putString("?", "登录成功");
            msg.setData(bundle);
            handler.sendMessage(msg);
        } catch (JSONException e) {
            e.printStackTrace();
             try {
				jsonObject = new JSONObject(str);
				
				 view_one = new View_One(login, jsonObject.getString("error"));
				 view_one.viewcreate();
				
					
				
			}
			catch (JSONException er) {}

        }catch( IOException e)
		{
			
		}
    }

    public void register_type(String url, String json) {
        OkHttpClient htt;
        String[] strs = json.split("\\|");
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str = null;
        Response response = null;

        RequestBody formBody = new FormBody.Builder()
		
                .add("phone", strs[0])
                .add("userpassword", strs[1])
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {

            //View_One view_one = new View_One(regis, e.toString());
        }
        try {
            if (!response.isSuccessful()) throw new IOException();
            str = response.body().string();


            //如果返回的是error
            jsonObject = new JSONObject(str);
			//  "nickname" : ${nickname},    //昵称(String)
			//  "paypassword" : ${paypassword},    //支付密码(String)
			// "balance" : ${balance},    //余额(Number)
			//"notices" : ${notices},    //通知(String)  [存的只是通知id]
			//"collects" : ${collects}    //收藏(String)  [存的只是收藏id]
			//                                                   MainActivity,       handler,  url,connectType,token,data) {
				try{
				if(jsonObject.getString("error").equals("该手机号已被使用过"))
					{
							view_one= new View_One(regis, jsonObject.getString("error"));
			
							view_one.viewcreate();
						
					}
					}catch(JSONException e)
					{
						Bundle bundle = new Bundle();
						Message msg = new Message();
						msg.obj = jsonObject;
						msg.what = 0;
						bundle.putString("?", "注册成功");
						msg.setData(bundle);
						handler.sendMessage(msg);
						
					}
			
			
        } catch (JSONException e) {
            //View_One view_one = new View_One(login, str);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void adddata_type(String urladdtype, String json) {
        OkHttpClient htt;
        String[] strs = json.split("\\|");
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str = null;
        try {
            //  "nickname" : ${nickname},    //昵称(String)
            //  "paypassword" : ${paypassword},    //支付密码(String)
            // "balance" : ${balance},    //余额(Number)
            //"notices" : ${notices},    //通知(String)  [存的只是通知id]
            //"collects" : ${collects}    //收藏(String)  [存的只是收藏id]
            RequestBody formBody = new FormBody.Builder()
                    .add("nickname", strs[0])
                    .add("paypassword", strs[1])
                    .add("balance", strs[2])
                    .add("notices", strs[3])
                    .add("collects", strs[4])

                    .build();

            Request request = new Request.Builder()
                    .url(urladdtype)
                    .post(formBody)
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException();
			
			str = response.body().string();
			try {
				jsonObject = new JSONObject(str);
			if(jsonObject.getString("error").equals("昵称已存在，请重命名"))
			{
				
				
			}
				
				Log.e("添加个人信息",str);
				view_one=new View_One(login,str);
				
			}
			catch (JSONException e) {
				str = "请求成功";
				Log.e("添加个人信息",e.toString());
				
				view_one=new View_One(login,e.toString());
				
			}

                

            


        } catch (IOException e) {
            e.printStackTrace();
			view_one=new View_One(login,e.toString());
			
			
        } finally {


        }

    }

    private void GetAllVideo(String url, String json) throws IOException {
        OkHttpClient htt;
        String[] strs = json.split("\\|");
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str = null;
        try {
            Request request = new Request.Builder().url(url).build();


            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException(str = response.body().toString());
            if (str == null) {
                Bundle bundle;
                Message msg = null;
                try {
                    jsonArray = new JSONArray(response.body().string());
                    bundle = new Bundle();
                    msg = new Message();
                    bundle.putString("?", "获取成功");
                    msg.obj = jsonArray;
                    msg.what = 0;
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    jsonObject = new JSONObject(response.body().string());
                    bundle = new Bundle();
                    msg = new Message();
                    bundle.putString("?", "获取失败");
                    bundle.putString("!", jsonObject.getString("error"));
                    msg.obj = jsonObject;
                    msg.what = 0;
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }

            }

        } catch (JSONException e) {

        }
    }
/*获取收藏


*/

	private void Get_collects() {
		Collect_ coll=(Collect_)maphttp.get("context");
		if(coll!=null)
			handler=coll.mHandler;
        OkHttpClient htt;
            JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str = null;
        try {
            Request request = new Request.Builder().url(url).build();


            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException();
			str = response.body().string();
                Bundle bundle;
                Message msg = null;
                try {
                    jsonArray = new JSONArray(str);
                    bundle = new Bundle();
                    msg = new Message();
                    bundle.putString("?", "获取成功");
                    msg.obj = jsonArray;
                    msg.what = 0;
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    jsonObject = new JSONObject(str);
                    bundle = new Bundle();
                    msg = new Message();
                    bundle.putString("?", "获取失败");
                    bundle.putString("!", jsonObject.getString("error"));
                    msg.obj = jsonObject;
                    msg.what = 0;
                    msg.setData(bundle);
				
                    handler.sendMessage(msg);
                }

            

        } catch (JSONException e) {

        }
		catch(IOException e)
		{
			
		}
    }//获取收藏

    private void CheckData(String url)  {
        OkHttpClient htt;
        Response response;
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str1 = null;
		try{

        Request request = new Request.Builder().url(url).build();

	

        response = client.newCall(request).execute();
		//Log.e("add",response.body().string());
      //  if (response.isSuccessful())
	str1=response.body().string();
            Bundle bundle;
            Message msg = null;
           try {
				if(str1.equals("null"))
				{
						//String strss=user.token;
		
			}else{
				
               jsonObject = new JSONObject(str1);
			   if(jsonObject.getString("error").equals("个人信息获取失败"))
				  
				   {
					   
					   
				   }
                if(jsonObject.getString("error").equals("个人信息已存在，保存失败"))

				{


				}
				   
				}
           } catch (JSONException e) {
				
                  jsonObject = new JSONObject(str1);
                bundle = new Bundle();
                msg = new Message();
                bundle.putString("?", "获取成功");
                msg.obj = jsonObject;
                msg.what = 5;
                msg.setData(bundle);
               handler.sendMessage(msg);
           }
        
		
}catch(IOException e)
{
	
}catch(JSONException e)
{
	
}


    }

    private void GetHeadImage(String url) {
        OkHttpClient htt;
        Response response;
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str = null;
        try {
            Request request = new Request.Builder().url(url).build();

            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                View_One view_one = new View_One(MA, "错误");
            }

            str = response.body().string();
            Bundle bundle;
            Message msg = null;
            try {
                jsonObject = new JSONObject(str);
                bundle = new Bundle();
                msg = new Message();
                bundle.putString("?", "获取成功");
                msg.obj = jsonObject;
                msg.what = 6;
                msg.setData(bundle);
                handler.sendMessage(msg);
            } catch (JSONException e) {
                //  jsonArray = new JSONArray(response.body().string());
                View_One view_one = new View_One(MA, e.toString());
            }
        }catch (IOException er)
        {
            Log.e("头像获取失败:",er.toString());
            View_One view_one = new View_One(MA, er.toString());
        }


    }

    


    private void GetHead(String url, final String path) {
        //异步下载图片
        final String filepath = path;
        final File file = new File(path);

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(file);
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    fileOutputStream.flush();
                } catch (IOException e) {
                    Log.i("wangshu", "IOException");
                    e.printStackTrace();
                }

                Log.d("wangshu", "文件下载成功");
            }
        });
    }


}
