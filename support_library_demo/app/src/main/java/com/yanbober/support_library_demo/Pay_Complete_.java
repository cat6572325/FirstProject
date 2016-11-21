package com.yanbober.support_library_demo;

import android.content.Intent;
import android.support.v7.app.*;
import android.os.*;
import android.view.View;
import android.widget.Button;

public class Pay_Complete_ extends AppCompatActivity
{
	Button btn_complete;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.pay_complete_layout);
		btn_complete=(Button)findViewById(R.id.complete_finish);
		btn_complete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent=new Intent(Pay_Complete_.this,MainActivity.class);
				startActivity(intent);
			}
		});
	}
	
}
