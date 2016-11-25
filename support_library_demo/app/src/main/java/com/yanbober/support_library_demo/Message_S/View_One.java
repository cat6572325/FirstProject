package com.yanbober.support_library_demo.Message_S;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by cat6572325 on 16-11-25.
 */
public class View_One {
    public View_One(Context context,String Message)
    {
        Looper.prepare();
        Toast.makeText(context,Message,Toast.LENGTH_LONG).show();
        Looper.loop();
    }
}
