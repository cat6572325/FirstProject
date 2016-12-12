package com.yanbober.support_library_demo.Http_Util;

import android.content.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.*;
import android.util.*;
import android.widget.Toast;

import com.squareup.okhttp.*;
import com.yanbober.support_library_demo.*;
import com.yanbober.support_library_demo.Message_S.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import okhttp3.*;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.*;


/**
 * Created by cat6572325 on 16-11-17.
 * <p>
 * 上传文件的Http 请求信息分为4种
 * <p>
 * 1、分界符
 * <p>
 * -- +"-------------数字"+"\r\n"
 * <p>
 * <p>
 * 2、上传文件的相关信息
 * a-请求的参数名 b-上传的文件名 c-文件类型 d-
 * Content-Disposition:form-data;
 * name="file";
 * filename="abc.jpg"
 * <p>
 * <p>
 * 3、上传文件内容的字节流形式
 * Content-type:application/octet-stream
 * charset:utf-8
 * <p>
 * <p>
 * 4、文件全部上传完成后的结束符
 * -- +"-------------数字"+ -- +"\r\n"
 */
public class Http_UploadFile_ implements Runnable {
    Handler handler;
    File file;
    String sendMethod;
    public String url;
    String connectType = null, data = null, token = null;
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
    User user = new User();

    public Http_UploadFile_(Handler handler, File file, String url, String connectType, String Data) {
        this.handler = handler;
        this.file = file;
        this.url = url;
        this.connectType = connectType;
        this.data = Data;

    }

    public Http_UploadFile_(String url, HashMap<String, Object> map, String cont) {
        this.url = url;
        this.maphttp = map;
        this.connectType = cont;
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
    public OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(3000, TimeUnit.MINUTES)
            .readTimeout(3000, TimeUnit.MINUTES)
            .writeTimeout(3000, TimeUnit.MINUTES)
            .build();


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
                register_type(url, data);
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
                Modify_name_PATCH(url, sendMethod); //"http://192.168.1.112:1103/user/nickname?token=" + sendMethod, data);

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
                //获取头像
                GetHeadImage(url);

                break;
            case 10:
                //修改支付密码
                newpaypassword();
                break;
            case 11:
                //验证支付密码
                oldpaypassword();

                break;
            case 12:
                //添加收藏
                addcollect();

                break;

            case 13:
                //获取收藏视频列表
                Get_collects();
                break;
            case 14:
                //发送通知
                postMymessage();break;
            case 15:
                //获取所有通知
                GetAllMessage();


                break;

            case 16:
                //支付视频
                payVideo();
                break;
            case 17:
                //查余额

                break;
            case 18:
            //获得已上传视频列表
                GetalreadyVideo();
            break;
            case 19:
                //获得未上传视频列表
                GetNotLoadVideo();
                break;
            case 20:
                //保存未上传视频
                savaNotLoadVideoData();
                break;
        }
    }
    /*
  保存未上传视频信息
   */
    public void savaNotLoadVideoData()
    {

            if (maphttp.get("handler") != null)
                handler = (Handler) maphttp.get("handler");
            JSONObject jsonObject = null;
            JSONArray jsonArray = null;
            String str = null, datas = null;
            Response response = null;
            RequestBody formBody = new FormBody.Builder()
                    .add("title", maphttp.get("title").toString())
                    .add("vdoPath",maphttp.get("vdoPath").toString())
                    .add("introduction",maphttp.get("introduction").toString())
                    .add("price",maphttp.get("price").toString())
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            try {
                response = client.newCall(request).execute();
            } catch (IOException er) {

                Log.e("未上传视频信息", er.toString());

            }
            try {
                if (!response.isSuccessful()) throw new IOException();
                str = response.body().string();
                //如果返回的是error
                jsonObject = new JSONObject(str);

                    Bundle bundle = new Bundle();
                    Message msg = new Message();
                    bundle.putString("?", "已保存");

                    msg.what = 3;
                    msg.setData(bundle);
                    handler.sendMessage(msg);





            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("发送未上传视频信息", e.toString());

            } catch (IOException e) {
                Log.e("发送未上传视频信息", e.toString());
            }

    }


    /*

获得已上传视频列表
 */
    private void GetalreadyVideo()
    {
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str = null;
        try {
            Request request = new Request.Builder().url(url).build();

            if (maphttp.get("handler") != null)
                handler = (Handler) maphttp.get("handler");
            Response response = client.newCall(request).execute();

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
                Log.e("获取已上传视频列表时",e.toString());



            }




        } catch (IOException e) {
            Log.e("在获取已上传视频列表的时候",e.toString());
        }
    }

    /*

    获得未上传视频列表
     */
    private void GetNotLoadVideo()
    {
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str = null;
        try {
            Request request = new Request.Builder().url(url).build();

            if (maphttp.get("handler") != null)
                handler = (Handler) maphttp.get("handler");
            Response response = client.newCall(request).execute();

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
                Log.e("获取未上传视频列表时",e.toString());



            }




        } catch (IOException e) {
            Log.e("在获取未上传视频列表的时候",e.toString());
        }
    }


    /*
    支付视频
     */
    public void payVideo() {
        if ((Integer) maphttp.get("balance") > 0) {
            if (maphttp.get("handler") != null)
                handler = (Handler) maphttp.get("handler");
            JSONObject jsonObject = null;
            JSONArray jsonArray = null;
            String str = null, datas = null;
            Response response = null;
            RequestBody formBody = new FormBody.Builder()
                    .add("cost", maphttp.get("price").toString())
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            try {
                response = client.newCall(request).execute();
            } catch (IOException er) {

                Log.e("支付视频", er.toString());

            }
            try {
                if (!response.isSuccessful()) throw new IOException();
                str = response.body().string();
                //如果返回的是error
                jsonObject = new JSONObject(str);
                if (jsonObject.getString("message").equals("paidVideos change")) {
                    Bundle bundle = new Bundle();
                    Message msg = new Message();
                    bundle.putString("?", "paidVideos change");
                    msg.arg1 = (Integer) maphttp.get("cost");
                    msg.what = 5;
                    msg.setData(bundle);
                    handler.sendMessage(msg);

                    view_one = null;
                    view_one = new View_One((Context) maphttp.get("context"), "支付成功");
                    view_one.viewcreate();


                }
                if (jsonObject.getString("message").equals("验证错误")) {
                    view_one = null;
                    view_one = new View_One((Context) maphttp.get("context"), "验证密码错误");
                    view_one.viewcreate();

                }
                Log.e("支付视频", str);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("支付视频", e.toString());

            } catch (IOException e) {
                Log.e("支付视频", e.toString());
            }
        } else {
            view_one = null;
            view_one = new View_One((Context) maphttp.get("context"), "余额不够");
            view_one.viewcreate();

        }

    }


    /*
    修改支付密码

    */
    public void oldpaypassword() {

        if (maphttp.get("handler") != null)
            handler = (Handler) maphttp.get("handler");
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str = null, datas = null;
        Response response = null;
        RequestBody formBody = new FormBody.Builder()
                .add("paypassword", maphttp.get("paypassword").toString())
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException er) {

            Log.e("在提交旧支付密码时", er.toString());

        }
        try {
            if (!response.isSuccessful()) throw new IOException();
            str = response.body().string();
            //如果返回的是error
            jsonObject = new JSONObject(str);
            if (jsonObject.getString("message").equals("继续下一步")) {
                Bundle bundle = new Bundle();
                Message msg = new Message();
                bundle.putString("?", "继续下一步");
                bundle.putString("video_id", maphttp.get("video_id").toString());
                msg.what = 4;
                msg.setData(bundle);
                handler.sendMessage(msg);

                view_one = null;
                view_one = new View_One((Context) maphttp.get("context"), "继续下一步");
                view_one.viewcreate();


            }
            if (jsonObject.getString("message").equals("验证错误")) {
                view_one = null;
                view_one = new View_One((Context) maphttp.get("context"), "验证密码错误");
                view_one.viewcreate();

            }
            Log.e("在提交旧支付密码", str);

        } catch (JSONException e) {
            try {
                e.printStackTrace();

                view_one = null;
                view_one = new View_One((Context) maphttp.get("context"), jsonObject.getString("error"));
                view_one.viewcreate();
            } catch (JSONException er) {

            }

            Log.e("在提交旧支付密码的时候", e.toString());

        } catch (IOException e) {
            Log.e("在提交旧支付密码的时候", e.toString());
        }


    }

    /*
    提交新支付密码

    */
    public void newpaypassword() {

        if (maphttp.get("handler") != null)
            handler = (Handler) maphttp.get("handler");
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str = null, datas = null;
        Response response = null;
        RequestBody formBody = new FormBody.Builder()
                .add("paypassword", maphttp.get("paypassword").toString())
                .build();
        Request request = new Request.Builder()
                .url(url)
                .patch(formBody)
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException er) {

            Log.e("在提交新支付密码时", er.toString());

        }
        try {
            if (!response.isSuccessful()) throw new IOException();
            str = response.body().string();
            //如果返回的是error
            jsonObject = new JSONObject(str);
            if (jsonObject.getString("message").equals("已更改支付密码")) {
                Message msg = new Message();
                Bundle bundle = new Bundle();
                msg.obj = jsonObject;
                msg.what = 0;
                bundle.putString("?", "成功");
                handler.sendMessage(msg);


            }
            if (jsonObject.getString("message").equals("验证错误")) {
                view_one = null;
                view_one = new View_One((Context) maphttp.get("context"), "验证密码错误");
                view_one.viewcreate();

            }
            Log.e("在提交新支付密码", str);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("在提交新支付密码的时候", e.toString());

        } catch (IOException e) {
            Log.e("在提交新支付密码的时候", e.toString());
        }


    }

    /*
    改余额


     */
    public void changebalance() {

        if (maphttp.get("handler") != null)
            handler = (Handler) maphttp.get("handler");
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str = null, datas = null;
        Response response = null;
        RequestBody formBody = new FormBody.Builder()
                .add("balance", maphttp.get("paypassword").toString())
                .build();
        Request request = new Request.Builder()
                .url(url)
                .patch(formBody)
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException er) {

            Log.e("更改余额时", er.toString());

        }
        try {
            if (!response.isSuccessful()) throw new IOException();
            str = response.body().string();
            //如果返回的是error
            jsonObject = new JSONObject(str);
            if (jsonObject.getString("message").equals("已更改余额")) {


            }
            if (jsonObject.getString("message").equals("验证错误")) {
                view_one = null;
                view_one = new View_One((Context) maphttp.get("context"), "验证密码错误");
                view_one.viewcreate();

            }
            Log.e("更改余额时", str);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("在更改余额的时候", e.toString());

        } catch (IOException e) {
            Log.e("在更改余额时候", e.toString());
        }


    }

	
	/*
    获取所有通知
	
	*/

    public void GetAllMessage() {
		/*>>  返回全部通知
		 {
		 {
		 "_id" : "***",
		 "videoTitle" : "***",
		 "outlay" : ***,
		 "costTF" : "***",
		 "operaTF" : "***",
		 "rmoveTF" : "***",
		 "IrrelevantTF" : "***",
		 "other" : "***"
		 },
		 {...},*/
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str = null;
        try {
            Request request = new Request.Builder().url(url).build();

            if (maphttp.get("handler") != null)
                handler = (Handler) maphttp.get("handler");
            Response response = client.newCall(request).execute();

            str = response.body().string();
            Bundle bundle;
            Message msg = null;
            try {
                jsonArray = new JSONArray(str);
                bundle = new Bundle();
                msg = new Message();
                bundle.putString("?", "获取成功");
                msg.obj = jsonArray;
                msg.what = 3;
                msg.setData(bundle);
                handler.sendMessage(msg);
            } catch (JSONException e) {
Log.e("获取所有通知时",e.toString());



            }




        } catch (IOException e) {

        }
    }

    public void GetBalance() {

        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str = null;
        try {
            Request request = new Request.Builder().url(url).build();

            if (maphttp.get("handler") != null)
                handler = (Handler) maphttp.get("handler");
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException();
            str = response.body().string();
            Bundle bundle;
            Message msg = null;

            jsonObject = new JSONObject(str);
            bundle = new Bundle();
            msg = new Message();
            bundle.putString("?", "获取成功");
            msg.obj = jsonObject;
            msg.what = 8;
            msg.setData(bundle);
            handler.sendMessage(msg);


        } catch (JSONException e) {

        } catch (IOException e) {

        }
    }


    /*

    发送通知内容

    */
    public void postMymessage() {
		/*提交新通知

		 POST   http://localhost:1103/user/notice?token=${token}
		 {
		 "videoTitle" : ${videoTitle},    //视频名(String)
		 "outlay" : ${outlay},    //支付收入数目(Number)
		 "costTF" : ${costTF},    //花费 收入判断(Boolean)
		 "operaTF" : ${operaTF},    //视频操作 或 花费判断(Boolean)
		 "rmoveTF" : ${rmoveTF},    //上传 删除判断(Boolean)
		 "IrrelevantTF" : ${IrrelevantTF},    //其他信息 或 相关信息判断(Boolean)
		 "other" : ${other}    //其他信息(String)
		 }
		 >>  返回 message: '通知已更新'
		 获取用户全部通知
		 */
        if (maphttp.get("handler") != null)
            handler = (Handler) maphttp.get("handler");
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str = null, datas = null;
        Response response = null;
          RequestBody formBody = new FormBody.Builder()
                .add("videoId", maphttp.get("videoId").toString())
                .add("outlay", maphttp.get("outlay").toString())
                .add("kinds", maphttp.get("kinds").toString())


                .add("IrrelevantTF", maphttp.get("IrrelevantTF").toString())
                .add("other", maphttp.get("other").toString())

                .build();

        Request request = new Request.Builder()
                .url(url)

                .post(formBody)
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException er) {

            Log.e("在提交通知时", er.toString());

        }
        try {

            if (!response.isSuccessful()) throw new IOException();
            str = response.body().string();
            //如果返回的是error
            jsonObject = new JSONObject(str);
            if (jsonObject.getString("message").equals("通知已更新")) {


                Bundle bundle = new Bundle();
                Message msg = new Message();
                msg.obj = jsonObject;
                msg.what = 0;
                bundle.putString("?", "获取成功");
                msg.setData(bundle);
                //   handler.sendMessage(msg);


            }
            Log.e("在提交通知的时候", str);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("在提交通知的时候", e.toString());

        } catch (IOException e) {
            Log.e("在提交通知的时候", e.toString());
        }


    }

    /*

    删除

    */
    public void Delete_Collect(String deletecollecturl) {
        String str = null;
        try {

            Request request = new Request.Builder()
                    .url(deletecollecturl)
                    .delete()
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException();
            str = response.body().string();
            JSONObject jsonObject = new JSONObject(str);
            Log.e("删除收藏", str);
            Log.e("删除收藏url", url);
            handler=(Handler)maphttp.get("handler");
            Message msg=new Message();
            msg.what=1;
            msg.arg1=1;
            handler.sendMessage(msg);

        } catch (IOException e) {
            Log.e("videodata1", e.toString());
            view_one = null;
            view_one = new View_One((Run_Video_) maphttp.get("context"), e.toString());
            view_one.viewcreate();

        } catch (JSONException e) {
            Log.e("videodata1", e.toString());
            view_one = null;
            view_one = new View_One((Run_Video_) maphttp.get("context"), e.toString());
            view_one.viewcreate();

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
            Log.e("videodata成功", str);
            Log.e("token", url);


        } catch (IOException e) {
            Log.e("videodata1", e.toString());
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
            Log.e("add", str);
            Log.e("add", url);
            Run_Video_ run = (Run_Video_) maphttp.get("context");
            Message msg = new Message();
            JSONObject jso = new JSONObject(str);
            if (jso.getString("message").equals("已添加收藏")) {
                msg.arg1 = 0;
                msg.what = 1;
                run.mHandler.sendMessage(msg);
            } else {
                /* {"message":"此收藏已存在","collectId":"584bbb4f721da600114d7a99"}*/
                if (jso.getString("message").equals("此收藏已存在")) {

                        Delete_Collect("http://trying-video.herokuapp.com/user/collect/" + jso.getString("collectId") + "?token=" + user.token);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) { //如果返回的不是message
            try {//服务器返回来的收藏id
                Message msg = new Message();
                String cid = null;
                msg.arg1 = 1;
                JSONObject jsonObject = new JSONObject(str);
                //在这里调用删除收藏的方法DeleteCollect
                cid=jsonObject.getString("cid");
             /*   if (maphttp.get("vid") != null) {
                    String vid = maphttp.get("vid").toString();
                    if (user.Collect_List != null) {
                        for (int i = 0; i < user.Collect_List.size(); i++) {
                            //获取
                            if (user.Collect_List.get(i).get("_id").toString() != null) {
                                HashMap<String, Object> vmap = user.Collect_List.get(i);
                                if (vid.equals(user.Collect_List.get(i).get("vdo_id"))) {
                                    //对比收藏中的视频id和视频id
                                    //如果收藏列表某项收藏中的视频id和本视频id对上了
                                    cid = user.Collect_List.get(i).get("_id").toString();
                                    //比较的是视频id  但赋值的是收藏id
                                }
                            }

                        }*/


            }catch (JSONException er)
            {

            }
        }

        return str;

    }

    public void Modify_PATCH(String url, String data) {
        //TODO 修改密码
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

            jsonObject = jsonArray.getJSONObject(0);
            token = jsonObject.getString("token");
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
                new Thread()
                {
                    @Override
                    public void run() {
                        super.run();
                        view_one = new View_One(login,"登录失败");
                        view_one.viewcreate();


                    }
                }.start();


            } catch (JSONException er) {
                Log.e("登录过程发生",er.toString());
                final String exception=er.toString();
                new Thread()
                {
                    @Override
                    public void run() {
                        super.run();
                        view_one = new View_One(login,"登录失败"+exception);
                        view_one.viewcreate();


                    }
                }.start();


            }

        } catch (IOException e) {
Log.e("在登录的时候",e.toString());
            final String exception=e.toString();
            new Thread()
            {
                @Override
                public void run() {
                    super.run();
                    view_one = new View_One(login,"登录失败"+exception);
                    view_one.viewcreate();


                }
            }.start();

        }
    }

    public void register_type(String url, String json) {
        OkHttpClient htt;
           JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str = null;
        Response response = null;
        handler=(Handler)maphttp.get("handler");
        Context context=(Context)maphttp.get("Context");
        Bundle bundle = new Bundle();
        Message msg = new Message();

        RequestBody formBody = new FormBody.Builder()

                .add("phone", maphttp.get("phone").toString())
                .add("userpassword", maphttp.get("password").toString())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        try{
            response = client.newCall(request).execute();

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
            try {
                if (jsonObject.getString("error").equals("该手机号已被使用过")) {
                    msg.obj = jsonObject;
                    msg.what = 0;
                    bundle.putString("?", "手机号已被注册过");
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                    view_one=null;
                    view_one = new View_One(context,jsonObject.getString("error") );
                    view_one.viewcreate();

                }
            } catch (JSONException e) {

                handler=(Handler)maphttp.get("handler");
                bundle = new Bundle();
                msg = new Message();
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
                if (jsonObject.getString("error").equals("昵称已存在，请重命名")) {


                }

                Log.e("添加个人信息", str);

            } catch (JSONException e) {
                str = "请求失败";
                Log.e("添加个人信息", e.toString());



            }


        } catch (IOException e) {
            e.printStackTrace();
            final String exc=e.toString();
                    view_one=null;
                    view_one = new View_One(login, exc);
                    view_one.viewcreate();


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

       final Context coll = (Context) maphttp.get("Context");
        if (coll != null)
            handler = (Handler)maphttp.get("handler");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(coll, "dddd", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        });
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
                msg.what = 7;
                msg.setData(bundle);
                handler.sendMessage(msg);
            } catch (JSONException e) {
                jsonObject = new JSONObject(str);
                bundle = new Bundle();
                msg = new Message();
                bundle.putString("?", "获取失败");
                bundle.putString("!", jsonObject.getString("error"));
                msg.obj = jsonObject;
                msg.what = 7;
                msg.setData(bundle);

                handler.sendMessage(msg);
            }


        } catch (JSONException e) {
            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText((Context)maphttp.get("Context"),"在获得所有收藏的时候",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            });


        } catch (IOException e) {
            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText((Context)maphttp.get("Context"),"在获得所有收藏的时候1",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            });
        }
    }//获取收藏

    private void CheckData(String url) {
        OkHttpClient htt;
        Response response;
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str1 = null;
        if (handler == null) {
            handler = (Handler) maphttp.get("handler");
        }
        try {

            Request request = new Request.Builder().url(url).build();


            response = client.newCall(request).execute();
            //Log.e("add",response.body().string());
            //  if (response.isSuccessful())
            str1 = response.body().string();
            Bundle bundle;
            Message msg = null;
            try {
                if (str1.equals("null")) {
                    //String strss=user.token;

                } else {

                    jsonObject = new JSONObject(str1);
                    if (jsonObject.getString("error").equals("个人信息获取失败")) {

                        view_one = null;
                        view_one = new View_One((Context) maphttp.get("Context"), jsonObject.getString("error"));
                        view_one.viewcreate();

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


        } catch (IOException e) {

        } catch (JSONException e) {

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

            }

            str = response.body().string();
            if (str.equals("null"))
            {//无设置头像，直接上传预置头像
                //发起请求
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("handler", maphttp.get("handler"));
                map.put("Context", maphttp.get("Context"));
                map.put("key", "photofile");
                map.put("count", 0);
                Context context=(Context)maphttp.get("Context");
                        NewOkhttp n=new NewOkhttp("http://trying-video.herokuapp.com/user/image?token=" + user.token
                        ,(File)maphttp.get("headfile")
                        ,map
                );

                Thread x=new Thread(n);
               x.start();

            }else {
                Bundle bundle;
                Message msg = null;
                try {
                    handler=(Handler)maphttp.get("handler");
                    jsonObject = new JSONObject(str);
                    bundle = new Bundle();
                    msg = new Message();
                    bundle.putString("?", "获取成功");
                    msg.obj = jsonObject;
                    msg.what = 6;
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                    Log.e("获取头像的线程中，获取成功", str);
                } catch (JSONException e) {
                    //  jsonArray = new JSONArray(response.body().string());
                    Log.e("头像获取失败:", e.toString());
                }
            }
        } catch (IOException er) {
            Log.e("头像获取失败:", er.toString());

        }


    }



}
