package com.yanbober.support_library_demo;

import android.content.Intent;
import android.support.v7.app.*;
import android.os.*;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yanbober.support_library_demo.DataHelpers.DataHelper;
import com.yanbober.support_library_demo.Http_Util.Http_UploadFile_;

import java.io.IOError;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Modify_Password_ extends AppCompatActivity
{
	public Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
				case 0:
					Bundle bun=new Bundle();
					bun=msg.getData();
					if (bun.getString("?").equals("修改成功"))
					{
						Intent intent=new Intent(Modify_Password_.this,MainActivity.class);
						startActivity(intent);
						finish();
					}
					break;

			}
		}
	};
	User user=new User();
	ImageView Modify;
	EditText Modify_Pass,oldpass;
	DataHelper dataserver;
	String[] str1;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		//数据库操作
		dataserver=new DataHelper(Modify_Password_.this);
		str1=dataserver.readData("flag|").split("\\|");

		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_password_layout);
		Modify=(ImageView)findViewById(R.id.Modify_Pass);
		oldpass=(EditText)findViewById(R.id.Modify_Pass_e);
		Modify_Pass=(EditText)findViewById(R.id.newpass);
		Modify.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//if(cose()==true)
				try {
					//Handler, String, String, String  String) {
					Http_UploadFile_ http_uploadFile_ = new Http_UploadFile_(Modify_Password_.this,mHandler
							, "http://192.168.1.112:1103/reg/password?token=" + str1[4]
							, "5"
							, "POST"
							, oldpass.getText().toString() + "|" + Modify_Pass.getText().toString());
					Thread x = new Thread(http_uploadFile_);
					x.start();
				}catch (IOError e)
				{

				}

			}
		});
	}
	public boolean cose() {
		if (Modify_Pass.getText().toString().length() < 8) {
			Toast.makeText(Modify_Password_.this, "密码必须长于8个", Toast.LENGTH_SHORT).show();
			return false;
		} else {
			if (oldpass.getText().toString().length() < 8) {
				Toast.makeText(Modify_Password_.this, "密码必须长于8个", Toast.LENGTH_SHORT).show();
				return false;
			}else {
				return true;
			}


	}

	}
	
}
