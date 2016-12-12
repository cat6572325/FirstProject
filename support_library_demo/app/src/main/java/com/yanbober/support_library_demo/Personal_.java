package com.yanbober.support_library_demo;

import android.support.v7.app.*;
import android.os.*;
import android.view.View;
import android.widget.ImageView;

public class Personal_ extends AppCompatActivity
{
	ImageView backimg;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.personal_layout);
		
	}
	private void initView()
	{
		backimg=(ImageView)this.findViewById(R.id.personal_back);
		backimg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
	}
	
	
}
