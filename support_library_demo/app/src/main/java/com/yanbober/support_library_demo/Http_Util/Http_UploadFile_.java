package com.yanbober.support_library_demo.Http_Util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.system.ErrnoException;
import android.util.Log;

import com.yanbober.support_library_demo.Login_;
import com.yanbober.support_library_demo.MainActivity;
import com.yanbober.support_library_demo.Modify_Password_;
import com.yanbober.support_library_demo.Register_;
import com.yanbober.support_library_demo.Round_Video_;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    String connectType = null, data = null;
    Register_ regis;
    Login_ login;
    Round_Video_ round_video_;
    Modify_Password_ p;
    MainActivity MA = null;
    HashMap<String, Object> maphttp = null;

    private final static String LINEND = "\r\n";
    private final static String BOUNDARY = "---------------------------7da2137580612"; //数据分隔线
    private final static String PREFIX = "--";
    private final static String MUTIPART_FORMDATA = "multipart/form-data";
    private final static String CHARSET = "utf-8";
    private final static String CONTENTTYPE = "application/octet-stream";

    public Http_UploadFile_(Handler handler, File file, String url, String connectType, String Data) {
        this.handler = handler;
        this.file = file;
        this.url = url;
        this.connectType = connectType;
        this.data = Data;

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
            , String data) {
        this.MA = regis;
        this.handler = handler;
        this.sendMethod = token;
        this.url = url;
        this.connectType = connectType;
        this.data = data;

    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public OkHttpClient client = new OkHttpClient();

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
                break;
            case 1:
                //登录
                try {
                    login_type(url, data);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                //删除帐号

                break;
            case 3:
                //上传视频

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
                Modify_name_PATCH("http://192.168.1.112:1103/user/nickname?token=" + sendMethod, data);
                break;
            case 7:
                //添加信息
                adddata_type(url, data);
                break;
            case 8:
                //查找个人信息
                try {
                    CheckData(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case 9:
                try {
                    GetHeadImage(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 10:

                break;
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
        String[] strs = data.split("\\|");
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("nickname", strs[0])
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
            msg.what = 4;
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

    public void login_type(String url, String json) throws IOException, JSONException {
        OkHttpClient htt;
        String[] strs = json.split("\\|");
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str = null;
        Response response = null;
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("phone", strs[0])
                    .add("userpassword", strs[1])
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException(str = response.body().toString());
            if (str == null) {
                try {
                    //如果返回的是error
                    jsonArray = new JSONArray(response.body().string());
                    str = "登录成功";
                    Bundle bundle = new Bundle();
                    Message msg = new Message();
                    msg.obj = jsonArray;
                    bundle.putString("?", str);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    Bundle bundle = new Bundle();
                    Message msg = new Message();

                    bundle.putString("?", "登录失败");
                    bundle.putString("!", response.body().toString());
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }

            } else {
                Bundle bundle = new Bundle();
                Message msg = new Message();
                msg.obj = jsonArray;
                bundle.putString("?", str);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            //登录

        }

    }

    public void register_type(String url, String json) throws IOException, JSONException {
        OkHttpClient htt;
        String[] strs = json.split("\\|");
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str = null;
        Response response = null;
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("phone", strs[0])
                    .add("userpassword", strs[1])
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException(str = response.body().toString());
            if (str == null) {
                try {
                    //如果返回的是error
                    jsonArray = new JSONArray(response.body().string());

                    Bundle bundle = new Bundle();
                    Message msg = new Message();
                    msg.obj = jsonArray;
                    msg.what = 0;
                    bundle.putString("?", "注册成功");
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    Bundle bundle = new Bundle();
                    Message msg = new Message();

                    bundle.putString("?", "注册失败");
                    bundle.putString("!", response.body().toString());
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }

            } else {
                Bundle bundle = new Bundle();
                Message msg = new Message();
                msg.obj = jsonArray;
                bundle.putString("?", str);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {


        }

    }

    public void adddata_type(String url, String json) {
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
                    .url(url)
                    .post(formBody)
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException(str = response.body().toString());
            if (str == null) {
                try {

                    jsonArray = new JSONArray(response.body().string());
                } catch (JSONException e) {

                    str = "添加成功";
                    Bundle bundle = new Bundle();
                    Message msg = new Message();
                    bundle.putString("?", str);
                    //  msg.what=
                    msg.setData(bundle);
                    //   handler.sendMessage(msg);
                    //登录

                }

            }


        } catch (IOException e) {
            e.printStackTrace();
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

    private void CheckData(String url) throws IOException {
        OkHttpClient htt;
        Response response;
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str = null;

        Request request = new Request.Builder().url(url).build();

        response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException(str = response.body().toString());
        if (str == null) {
            Bundle bundle;
            Message msg = null;
            try {

                jsonObject = new JSONObject(response.body().string());
                bundle = new Bundle();
                msg = new Message();
                bundle.putString("?", "获取成功");

                 msg.obj = jsonObject;
                msg.what = 5;
                msg.setData(bundle);
                handler.sendMessage(msg);
            } catch (JSONException e) {

                //  jsonArray = new JSONArray(response.body().string());
                bundle = new Bundle();
                msg = new Message();
                bundle.putString("?", "获取失败");
                msg.obj = jsonArray;
                msg.what = 5;
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        }


    }
    private void GetHeadImage(String url) throws IOException {
        OkHttpClient htt;
        Response response;
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str = null;
        try {
            Request request = new Request.Builder().url(url).build();

            response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException(str = response.body().toString());
            if (str == null) {
                Bundle bundle;
                Message msg = null;
                try {

                    jsonObject = new JSONObject(response.body().string());
                    bundle = new Bundle();
                    msg = new Message();
                    bundle.putString("?", "获取成功");

                    msg.obj = jsonObject;
                    msg.what = 6;
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                } catch (JSONException e) {

                    //  jsonArray = new JSONArray(response.body().string());
                    bundle = new Bundle();
                    msg = new Message();
                    bundle.putString("?", "获取失败");
                    msg.obj = jsonArray;
                    msg.what = 6;
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            }
        }catch (IOException er)
        {

        }
    }

    private void LoadHeadImage(String url) throws IOException {
        //异步上传图片
        File file=new File(url);
        OkHttpClient htt;
        Response response;

        JSONArray jsonArray = null;
        String str = null;

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Bundle bundle;
                Message msg = null;

                bundle = new Bundle();
                msg = new Message();
                bundle.putString("?", "上传失败");

                msg.what = 5;
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("wangshu",response.body().string());
                Bundle bundle;
                Message msg = null;
                try {
                   JSONObject jsonObject = new JSONObject(response.body().string());
                    bundle = new Bundle();
                    msg = new Message();
                    bundle.putString("?", "上传成功");

                    msg.obj = jsonObject;
                    msg.what = 5;
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }catch (JSONException e)
                {

                }

            }

        });


    }


    private void GetHead(String url, final String path) {
        //异步下载图片
        final String filepath=path;
        final File file=new File(path);

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
