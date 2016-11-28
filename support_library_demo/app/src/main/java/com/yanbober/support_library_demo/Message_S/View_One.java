package com.yanbober.support_library_demo.Message_S;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by cat6572325 on 16-11-25.
 */
public class View_One {
	Context con;String data;
    public View_One(Context context,String Message)
    {
       this.con=context;
	   this.data=Message;
    }
	public void viewcreate()
	{
		Looper.prepare();
        Toast.makeText(con,data,Toast.LENGTH_LONG).show();
        Looper.loop();
	}
}
