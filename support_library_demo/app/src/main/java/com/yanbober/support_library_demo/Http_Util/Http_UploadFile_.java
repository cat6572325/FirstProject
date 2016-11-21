package com.yanbober.support_library_demo.Http_Util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.system.ErrnoException;
import android.util.Log;

import com.yanbober.support_library_demo.Login_;
import com.yanbober.support_library_demo.MainActivity;
import com.yanbober.support_library_demo.Register_;
import com.yanbober.support_library_demo.Round_Video_;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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
    public URL url;
    String connectType = null, data = null;
    Register_ regis;
    Login_ login;
    Round_Video_ round_video_;
    MainActivity MA=null;
    HashMap<String ,Object> maphttp=null;

    private final static String LINEND = "\r\n";
    private final static String BOUNDARY = "---------------------------7da2137580612"; //数据分隔线
    private final static String PREFIX = "--";
    private final static String MUTIPART_FORMDATA = "multipart/form-data";
    private final static String CHARSET = "utf-8";
    private final static String CONTENTTYPE = "application/octet-stream";

    public Http_UploadFile_(Handler handler, File file, URL url, String connectType, String Data) {
        this.handler = handler;
        this.file = file;
        this.url = url;
        this.connectType = connectType;
        this.data = Data;

    }

    public Http_UploadFile_(Register_ regis, Handler handler, URL url, String connectType, String Sendmethod, String data) {
        this.regis = regis;
        this.handler = handler;
        this.sendMethod = Sendmethod;
        this.url = url;
        this.connectType = connectType;
        this.data = data;

    }

    public Http_UploadFile_(Login_ regis, Handler handler, URL url, String connectType, String Sendmethod, String data) {
        this.login = regis;
        this.handler = handler;
        this.sendMethod = Sendmethod;
        this.url = url;
        this.connectType = connectType;
        this.data = data;

    }
    public Http_UploadFile_(MainActivity regis, Handler handler, URL url, String connectType, String Sendmethod, String data) {
        this.MA = regis;
        this.handler = handler;
        this.sendMethod = Sendmethod;
        this.url = url;
        this.connectType = connectType;
        this.data = data;

    }

    public Http_UploadFile_(Round_Video_ regis, Handler handler, URL url, String connectType, String sendMethod,HashMap<String,Object> Data) {
        this.round_video_ = regis;
        this.handler = handler;
        this.url = url;
        this.connectType = connectType;
        this.maphttp = Data;
        this.sendMethod=sendMethod;
    }

    /**
     * HTTP上传单个文件
     * 请求服务器的路径
     * 传递的表单内容
     * 单个文件信息
     */
    @Override
    public void run() {
        Log.i("post-------------", "postfile");
        HttpURLConnection urlConn = null;
        BufferedReader br = null;
        DataOutputStream dos = null;
        try {
            //新建url对象
            //通过HttpURLConnection对象,向网络地址发送请求
            urlConn = (HttpURLConnection) url.openConnection();
            //设置该连接允许读取
          //  urlConn.setDoOutput(true);
            //设置该连接允许写入
          //  urlConn.setDoInput(true);
            //设置不能适用缓存
            urlConn.setUseCaches(false);
            //设置连接超时时间
            urlConn.setConnectTimeout(3000);   //设置连接超时时间
            //设置读取时间
            urlConn.setReadTimeout(4000);   //读取超时
            //设置连接方法post
            urlConn.setRequestMethod(sendMethod);
            //设置维持长连接

            //构建表单数据
            /*

    private final static String LINEND = "\r\n";
    private final static String BOUNDARY = "---------------------------7da2137580612"; //数据分隔线
    private final static String PREFIX = "--";
    private final static String MUTIPART_FORMDATA = "multipart/form-data";
    private final static String CHARSET = "utf-8";
    private final static String CONTENTTYPE = "application/octet-stream";
             */
            switch (Integer.parseInt(connectType)) {
                case 0:
                    //注册
                    JSONObject jsonObject = Register_Type(urlConn, data);

                    // DataOutputStream outputStream = new DataOutputStream(urlConn.getOutputStream());
                    //  outputStream.writeBytes(data);
                    break;
                case 1:
                    //登录
                    login_Type(urlConn, data);

                    break;
                case 2:
                    //删除帐号
                    DeleteAccount(urlConn, data);
                    urlConn.connect();

                    break;
                case 3:
                    //上传视频

                    break;
                case 4:
                    //获得视频列表


                    break;
                case 5:

                       break;
            }
           /* if(connectType.equals("Login"))
            {
                String[] phoAndpas=data.split("\\|");

            }else {
                //= bulidFormText(params);
                dos.writeBytes(PREFIX + BOUNDARY + LINEND);
                int index = 1;
                // for (FileInfo fileInfo : fileInfos){
                //   StringBuffer sb = new StringBuffer("");
                //   sb.append(PREFIX+BOUNDARY+LINEND)
                //           .append("Content-Disposition: form-data;" +
                //                    " name=\""+fileInfo.getFileTextName()+(++index)+"\";" +
                //                   " filename=\""+fileInfo.getImgFile().getAbsolutePath()+"\""+LINEND)
                //           .append("Content-Type:"+CONTENTTYPE+";" +
                //                  "charset="+CHARSET+LINEND)
                //          .append(LINEND);
                dos.writeBytes("Content-Disposition:form-data;" + "name=\" file\";+filename=\"文件名.mp4" + "\"" + "\r\n");//sb.toString().getBytes());
                //文件正体
                dos.writeBytes(LINEND);
                FileInputStream fis = new FileInputStream("文件路径");//fileInfo.getImgFile());
                byte[] buffer = new byte[10000];
                int len = 0;
                while ((len = fis.read(buffer)) != -1) {
                    dos.write(buffer, 0, len);
                }
                dos.write(LINEND.getBytes());
                fis.close();
                // }
                //请求的结束标志
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();

            dos.write(end_data);
            dos.flush();
            dos.close();
            // 发送请求数据结束

            //接收返回信息
            int code = urlConn.getResponseCode();
            if(code!=200){
                urlConn.disconnect();
                return code;
            }else{
                br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                String result = "";
                String line = null;
                while((line = br.readLine())!=null){
                    result += line;
                }
                Log.i("post-------------", result);
                if("true".equals(result)){
                    return 200;
                }else{
                    return 500;
                }
            } }//if else
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("--------上传图片错误--------", e.getMessage());
            return -1;
        }finally{
            try {
                if(br!=null){
                    br.close();
                }
                if(urlConn!=null){
                    urlConn.disconnect();
                }*/
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void LoadVideo(HttpURLConnection conn,String data) {
        DataOutputStream dos;
        BufferedReader br;
        try {
            dos=new DataOutputStream(conn.getOutputStream());
            conn.setRequestProperty("connection", "Keep-Alive");
            //设置文件字符集
            conn.setRequestProperty("Charset", CHARSET);
            //设置文件类型
            conn.setRequestProperty("Content-Type", MUTIPART_FORMDATA + ";boundary=" + BOUNDARY);
            /********************************************************************/
            //  dos = new DataOutputStream(urlConn.getOutputStream());

            dos.writeBytes(PREFIX + BOUNDARY + LINEND);
            int index = 1;
            // for (FileInfo fileInfo : fileInfos){
            //   StringBuffer sb = new StringBuffer("");
            //   sb.append(PREFIX+BOUNDARY+LINEND)
            //           .append("Content-Disposition: form-data;" +
            //                    " name=\""+fileInfo.getFileTextName()+(++index)+"\";" +
            //                   " filename=\""+fileInfo.getImgFile().getAbsolutePath()+"\""+LINEND)
            //           .append("Content-Type:"+CONTENTTYPE+";" +
            //                  "charset="+CHARSET+LINEND)
            //          .append(LINEND);
            dos.writeBytes("Content-Disposition:form-data;" + "name=\" file\";+filename=\"文件名.mp4" + "\"" + "\r\n");//sb.toString().getBytes());
            //文件信息
            dos.writeBytes(LINEND);
            FileInputStream fis = new FileInputStream("文件路径");//fileInfo.getImgFile());
            byte[] buffer = new byte[10000];
            int len = 0;
            //文件主体
            while ((len = fis.read(buffer)) != -1) {
                dos.write(buffer, 0, len);
            }
            dos.write(LINEND.getBytes());
            fis.close();
            // }
            //请求的结束标志
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();

            dos.write(end_data);
            dos.flush();
            dos.close();
            // 发送请求数据结束
            //接收返回信息
            int code = conn.getResponseCode();
            if (code != 200) {


            } else {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String result = "";
                String line = null;
                while ((line = br.readLine()) != null) {
                    result += line;
                }
                Log.i("post-------------", result);
                if ("true".equals(result)) {

                } else {

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("--------上传视频错误--------", e.getMessage());
           // return -1;
        } finally {
            if (conn != null)
                conn.disconnect();

            ;
        }

    }
    public String DeleteAccount(HttpURLConnection conn, String data) {
        String breakStr = null;
        DataOutputStream out = null;
        BufferedReader br = null;


        return breakStr;

    }

    public String Login_Type(HttpURLConnection conn, String data) {
        DataOutputStream out = null;
        BufferedReader br = null;
        String breakStr = null;

        try {
            out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(data);
            //格式是"phone=???&password=????
            out.flush();
            out.close();
            //接收返回信息
            int code = conn.getResponseCode();
            if (code != 200) {
                conn.disconnect();
                return breakStr = "200";
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String result = "";
                String line = null;
                while ((line = br.readLine()) != null) {
                    result += line;
                }
                Log.i("post-------------", result);
                breakStr = result;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return breakStr;
    }


    public JSONObject Register_Type(HttpURLConnection conn, String data) throws JSONException {
        DataOutputStream out = null;
        BufferedReader br = null;
        String breakStr = null;
        JSONObject jsonObject = null;
        Message msg = new Message();
        Bundle bundle = new Bundle();
        try {

            out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(data);
            //格式是"phone=???&password=????
            out.flush();
            out.close();
            //接收返回信息
            int code = conn.getResponseCode();
            if (code != 200) {
                conn.disconnect();

            } else {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String result = "";
                String line = null;
                while ((line = br.readLine()) != null) {
                    result += line;
                }
                Log.i("post-------------", result);
                breakStr = result;
                jsonObject = new JSONObject(breakStr);

                try {
                    jsonObject.get("_id");
                    bundle.putString("?", "注册成功");
                } catch
                        (JSONException je) {
                    bundle.putString("?", "注册失败");
                    bundle.putString("!", breakStr);
                }

                //JSONArray array=jsonObject.getJSONArray("result");
                //获取复数表单


                breakStr += jsonObject.getString("phone");
                breakStr += jsonObject.getString("_id");

                msg.obj = jsonObject;
                msg.what = 0;


                msg.setData(bundle);
                //发送数据并提示注册成功
                handler.sendMessage(msg);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return jsonObject;
    }

    public JSONObject login_Type(HttpURLConnection conn, String data)  {
        DataOutputStream out = null;
        BufferedReader br = null;
        String breakStr = null;
        JSONObject jsonObject = null;
        Message msg = new Message();
        Bundle bundle = new Bundle();
        try {

            out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(data);
            //格式是"phone=???&password=????
            out.flush();
            out.close();
            //接收返回信息
            int code = conn.getResponseCode();
            if (code != 200) {
                conn.disconnect();

            } else {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String result = "";
                String line = null;
                while ((line = br.readLine()) != null) {
                    result += line;
                }
                Log.i("post-------------", result);
                breakStr = result;
               // jsonObject=new JSONObject(breakStr);
                JSONArray array=new JSONArray(breakStr);
                breakStr=null;
                //获取复数表单
                for(int i=0;i<array.length();i++)
                {
                    breakStr+=array.get(i);
                }

                jsonObject=array.getJSONObject(0);
               try {
                    jsonObject.get("token");
                    bundle.putString("?", "登录成功");
                } catch
                        (JSONException je) {
                    bundle.putString("?", "登录失败");
                    bundle.putString("!", breakStr);
                }

                //JSONArray array=jsonObject.getJSONArray("result");
                //获取复数表单


              //  breakStr += jsonObject.getString("phone");
               // breakStr += jsonObject.getString("_id");

                msg.obj = array;
                msg.what = 0;


                msg.setData(bundle);
                //发送数据并提示登录成功
                handler.sendMessage(msg);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {

        }
        return jsonObject;
    }
    private void GetVideos(HttpURLConnection conn,String data)
    {
        DataOutputStream out = null;
        BufferedReader br = null;
        String breakStr = null;
        JSONObject jsonObject = null;
        Message msg = new Message();
        Bundle bundle = new Bundle();
        try {

            out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(data);
            //格式是"phone=???&password=????
            out.flush();
            out.close();
            //接收返回信息
            int code = conn.getResponseCode();
            if (code != 200) {
                conn.disconnect();

            } else {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String result = "";
                String line = null;
                while ((line = br.readLine()) != null) {
                    result += line;
                }
                Log.i("post-------------", result);
                breakStr = result;
                // jsonObject=new JSONObject(breakStr);
                JSONArray array=new JSONArray(breakStr);
                breakStr=null;
                //获取复数表单
                for(int i=0;i<array.length();i++)
                {
                    breakStr+=array.get(i);
                }

                jsonObject=array.getJSONObject(0);
                try {
                    jsonObject.get("videos");
                    bundle.putString("?", "获取成功");
                } catch
                        (JSONException je) {
                    bundle.putString("?", "获取失败");
                    bundle.putString("!", breakStr);
                }

                //JSONArray array=jsonObject.getJSONArray("result");
                //获取复数表单


                breakStr += jsonObject.getString("phone");
                breakStr += jsonObject.getString("_id");

                msg.obj = jsonObject;
                msg.what = 0;


                msg.setData(bundle);
                //发送数据并提示登录成功
                handler.sendMessage(msg);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {

        }

    }

}
