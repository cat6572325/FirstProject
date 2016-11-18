package com.yanbober.support_library_demo.Http_Util;

import android.os.Handler;
import android.util.Log;

import com.yanbober.support_library_demo.Register_;
import com.yanbober.support_library_demo.Round_Video_;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by cat6572325 on 16-11-17.
 *
 *  上传文件的Http 请求信息分为4种

                                 1、分界符

                                 -- +"-------------数字"+"\r\n"


                                 2、上传文件的相关信息
                                 a-请求的参数名 b-上传的文件名 c-文件类型 d-
                                 Content-Disposition:form-data;
                                 name="file";
                                 filename="abc.jpg"


                                 3、上传文件内容的字节流形式
                                 Content-type:application/octet-stream
                                 charset:utf-8


                                 4、文件全部上传完成后的结束符
                                 -- +"-------------数字"+ -- +"\r\n"


 */
public class Http_UploadFile_ implements Runnable {
    Handler handler;
    File file;
    public URL url;
    String connectType=null,data=null;
    Register_ regis;
    Round_Video_ round_video_;


    private final static String LINEND = "\r\n";
    private final static String BOUNDARY = "---------------------------7da2137580612"; //数据分隔线
    private final static String PREFIX = "--";
    private final static String MUTIPART_FORMDATA = "multipart/form-data";
    private final static String CHARSET = "utf-8";
    private final static String CONTENTTYPE = "application/octet-stream";
public Http_UploadFile_(Handler handler, File file,URL url,String connectType,String Data) {
    this.handler = handler;
    this.file = file;
    this.url = url;
    this.connectType = connectType;
    this.data = Data;

}
    public
    Http_UploadFile_(Register_ regis, Handler handler, URL url, String connectType, String Data)
    {
        this.regis = regis;
        this.handler = handler;

        this.url = url;
        this.connectType = connectType;
        this.data = Data;

    }
    public
    Http_UploadFile_(Round_Video_ regis, Handler handler, URL url, String connectType, String Data)
    {
        this.round_video_ = regis;
        this.handler = handler;
        this.url = url;
        this.connectType = connectType;
        this.data = Data;
    }

    /**
     * HTTP上传单个文件
     *   请求服务器的路径
     *    传递的表单内容
     *       单个文件信息
     *
     */
    @Override
    public void run () {



        Log.i("post-------------", "postfile");
        HttpURLConnection urlConn = null;
        BufferedReader br = null;
        DataOutputStream dos = null;
        try {
            //新建url对象
            //通过HttpURLConnection对象,向网络地址发送请求
            urlConn = (HttpURLConnection) url.openConnection();
            //设置该连接允许读取
            urlConn.setDoOutput(true);
            //设置该连接允许写入
            urlConn.setDoInput(true);
            //设置不能适用缓存
            urlConn.setUseCaches(false);
            //设置连接超时时间
            urlConn.setConnectTimeout(3000);   //设置连接超时时间
            //设置读取时间
            urlConn.setReadTimeout(4000);   //读取超时
            //设置连接方法post
            urlConn.setRequestMethod("POST");
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
                    String Jsonarl =Register_Type(urlConn, data);
                
                   // DataOutputStream outputStream = new DataOutputStream(urlConn.getOutputStream());
                  //  outputStream.writeBytes(data);
                    break;
                case 1:
                    //登录
                    Login_Type(urlConn, data);

                    break;

                case 2:

                    urlConn.setRequestProperty("connection", "Keep-Alive");
                    //设置文件字符集
                    urlConn.setRequestProperty("Charset", CHARSET);
                    //设置文件类型
                    urlConn.setRequestProperty("Content-Type", MUTIPART_FORMDATA + ";boundary=" + BOUNDARY);
                    /********************************************************************/
                    dos = new DataOutputStream(urlConn.getOutputStream());


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

    public String Login_Type(HttpURLConnection conn,String data)  {
        DataOutputStream out= null;
        BufferedReader br=null;
        String breakStr=null;

        try {
            out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(data);
            //格式是"phone=???&password=????
            out.flush();
            out.close();
            //接收返回信息
            int code = conn.getResponseCode();
            if(code!=200){
                conn.disconnect();
                return breakStr="200";
            }else {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String result = "";
                String line = null;
                while ((line = br.readLine()) != null) {
                    result += line;
                }
                Log.i("post-------------", result);
                breakStr=result;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
        return breakStr;
    }





    public String Register_Type(HttpURLConnection conn,String data)  {
        DataOutputStream out= null;
        BufferedReader br=null;
       String breakStr=null;
        try {

            out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(data);
            //格式是"phone=???&password=????
            out.flush();
            out.close();
            //接收返回信息
            int code = conn.getResponseCode();
            if(code!=200){
                conn.disconnect();
                return breakStr="200";
            }else {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String result = "";
                String line = null;
                while ((line = br.readLine()) != null) {
                    result += line;
                }
                Log.i("post-------------", result);
                breakStr=result;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
return breakStr;
    }


}
