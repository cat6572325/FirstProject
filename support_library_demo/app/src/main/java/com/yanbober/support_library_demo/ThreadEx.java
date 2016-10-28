package com.yanbober.support_library_demo;

import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.net.*;

public class ThreadEx extends Thread
{private Socket socketClien;
	Context con;
	String str;
	private DataInputStream in;
	private DataOutputStream out;
	Home home;
	MainActivity idf;
	View view;
	
	public ThreadEx(Context con,String str)
	{
		this.con=con;
		this.str=str;
	}
	public ThreadEx(Home home,String str)
	{
		this.home=home;
		this.str=str;
	}
	public ThreadEx(MainActivity idf,String str)
	{
		this.idf=idf;
	
		this.str=str;
	}
	public void run()
	{

		
		
		

		try
		{
			///create a new network connection
			socketClien = new Socket();


			SocketAddress sa=new InetSocketAddress("192.168.8.147", 7015);
			socketClien.connect(sa, 5000);
			in = new DataInputStream(socketClien.getInputStream());
			out = new DataOutputStream(socketClien.getOutputStream());
			out.writeUTF(str);
			//flag+account+passnuber
			String[] strs=str.split("\\|");
			str=null;
			str=in.readUTF();
			String[] datas=str.split("\\~");
			if(!str.equals("失败"))
				Toast.makeText(home,"登陆失败,用户名或密码错误",Toast.LENGTH_LONG).show();
			if(home!=null)
			{
				Message msg=new Message();
				Bundle bun=new Bundle();
				bun.putString("lo","登录信息"+str);
				msg.what=0;
				msg.setData(bun);
				home.mHandler.sendMessage(msg);

			}
			if(idf!=null)
			{
				Message msg=new Message();
				Bundle bun=new Bundle();
		
				bun.putString("group",datas[0]);
				bun.putString("phones",datas[1]);
				
				msg.what=0;
				msg.setData(bun);
				idf.mHandler.sendMessage(msg);
				
				
			}
			if(con!=null)
			{


			}
		}
		catch (IOException e)
		{/////if network fail
			
		}


	
	}//socket upaccodate thread
	/////threadEx
	
	
}       
