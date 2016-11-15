package com.yanbober.support_library_demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 注册
 *
 *
 */
public class Register_ extends AppCompatActivity {
ImageView Register_Enter;
Button backbtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
initView();

    }
    protected void initView()
    {
        Register_Enter=(ImageView)findViewById(R.id.Register_Button);
        backbtn=(Button)findViewById(R.id.Register_back);
        Register_Enter.setOnClickListener(new MyClickListener());

        backbtn.setOnClickListener(new MyClickListener());
    }
    class MyClickListener  implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO:注册后跳往
            switch (v.getId()) {
                case R.id.Register_Button:
                    Intent intent=new Intent(Register_.this,Send_random_.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.Register_back:
                    //TODO 左上角返回
                    finish();
                    break;
            }
        }
    }
}
