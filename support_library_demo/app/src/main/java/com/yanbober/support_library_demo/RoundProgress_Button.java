package com.yanbober.support_library_demo;

import android.content.*;
import android.graphics.*;
import android.util.*;
import android.view.*;

public class RoundProgress_Button extends View
{
	public RoundProgress_Button(Context context) {
		this(context, null);
	}

	public RoundProgress_Button(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RoundProgress_Button(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		// TODO: Implement this method
		super.onDraw(canvas);
		Paint paint=new Paint();
		paint.setColor(Color.RED);
		 //设置圆环的颜色
		//paint.setStyle(Paint.Style.FILL_AND_STROKE); //设置空心
		//paint.setStrokeWidth(5); //设置圆环的宽度
		//paint.setAntiAlias(true);  //消除锯齿 
		paint.setShadowLayer(30,5,2,Color.GREEN);
		
		Paint pi1=new Paint();
		pi1.setColor(Color.GREEN);
		pi1.setStyle(Paint.Style.STROKE);
		RectF rect=new RectF(105,105,40,40);
		canvas.drawArc(rect,9,180,false,pi1);
		
		
		canvas.drawCircle(100,100,60,paint);
			Bitmap bit=BitmapFactory.decodeResource(getResources(),R.drawable.close);
		
		canvas.drawBitmap(bit,getWidth()+2,getHeight()+2,paint);
	}
	
}
