package com.yanbober.support_library_demo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by cat6572325 on 16-11-12.
 * 发送验证码
 */
public class Send_random_ extends AppCompatActivity{
    TextView ReSend,title;
    Button back;
    public EditText e1,e2,e3,e4,e5,e6;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_randomarl_layout);
initView();

    }

    protected void initView()
    {
        setContentView(R.layout.enter_randomarl_layout);
        ReSend=(TextView)findViewById(R.id.resend);
        title=(TextView)findViewById(R.id.sendrandom);
        back=(Button) findViewById(R.id.Enter_Random_Back);
        e1=(EditText)findViewById(R.id.editText);
        e2=(EditText)findViewById(R.id.editText1);
        e3=(EditText)findViewById(R.id.editText2);
        e4=(EditText)findViewById(R.id.editText3);
        e5=(EditText)findViewById(R.id.editText4);
        e6=(EditText)findViewById(R.id.editText5);

        e1.addTextChangedListener(new Mytextwatcher());
        e2.addTextChangedListener(new Mytextwatcher());
        e3.addTextChangedListener(new Mytextwatcher());
        e4.addTextChangedListener(new Mytextwatcher());
        e5.addTextChangedListener(new Mytextwatcher());
        e6.addTextChangedListener(new Mytextwatcher());
       /* e2.setFocusable(false);
        e3.setFocusable(false);
        e4.setFocusable(false);
        e5.setFocusable(false);
        e6.setFocusable(false);
        */
        ReSend.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        back.setOnClickListener(new MyClickListener());
    }
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_DEL){
            int a=getCurrentFocus().getId();
            /*隐藏软键盘*/
            //TODO             后退
            if (e6.getId()==a)
            {
                e6.setBackgroundResource(R.drawable.random_style);
                e6.clearFocus();
                //  e6.setFocusable(false);
                e5.setFocusable(true);
                e5.requestFocus();
            }
            if (e5.getId()==a)
            {
                e5.setBackgroundResource(R.drawable.random_style);
                e5.clearFocus();
                //  e5.setFocusable(false);
                e4.setFocusable(true);
                e4.requestFocus();
            }
            if (e4.getId()==a)
            {
                e4.setBackgroundResource(R.drawable.random_style);
                e4.clearFocus();
                //  e4.setFocusable(false);
                e3.setFocusable(true);
                e3.requestFocus();
            }
            if (e3.getId()==a)
            {
                e3.setBackgroundResource(R.drawable.random_style);
                e3.clearFocus();
                //  e3.setFocusable(false);
                e2.setFocusable(true);
                e2.requestFocus();
            }
            if (e2.getId()==a)
            {
                e2.setBackgroundResource(R.drawable.random_style);
                e2.clearFocus();
                //  e2.setFocusable(false);
                e1.setFocusable(true);

                e1.requestFocus();

            }
            if (e1.getId()==a)
            {
                e1.setBackgroundResource(R.drawable.random_style);
                e1.clearFocus();

            }





            return true;
        }
        return super.dispatchKeyEvent(event);
    }
    class Mytextwatcher  implements TextWatcher {
        public void afterTextChanged(Editable s)
        {
// TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged
                (CharSequence s, int start, int count,int after)
        {

        }

        @Override
        public void onTextChanged
                (CharSequence s, int start, int before,int count) {

            int a = getCurrentFocus().getId();

            if (s.length() > 0) {

//TODO             输入
                if (e1.getId() == a) {
                    e2.setFocusable(true);
                    e1.setBackgroundResource(R.drawable.random_blak_style);
                    e1.clearFocus();
                    //   e1.setFocusable(false);

                    e2.requestFocus();

                }
                if (e2.getId() == a) {
                    e2.setBackgroundResource(R.drawable.random_blak_style);
                    e2.clearFocus();
                    //e2.setFocusable(false);
                    e3.setFocusable(true);
                    e3.requestFocus();
                }
                if (e3.getId() == a) {
                    e3.setBackgroundResource(R.drawable.random_blak_style);
                    e3.clearFocus();
                    // e3.setFocusable(false);
                    e4.setFocusable(true);
                    e4.requestFocus();
                }
                if (e4.getId() == a) {
                    e4.setBackgroundResource(R.drawable.random_blak_style);
                    e4.clearFocus();
                    //  e4.setFocusable(false);
                    e5.setFocusable(true);
                    e5.requestFocus();
                }
                if (e5.getId() == a) {
                    e5.setBackgroundResource(R.drawable.random_blak_style);
                    e5.clearFocus();
                    //  e5.setFocusable(false);
                    e6.setFocusable(true);
                    e6.requestFocus();
                }
                if (e6.getId() == a) {
                    e6.setBackgroundResource(R.drawable.random_blak_style);
                    e6.clearFocus();
                    Intent intent=new Intent(Send_random_.this,Register_sucess_Message_.class);
                    startActivity(intent);
                }
            }

        }
    }////////if input


    class MyClickListener  implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.Enter_Random_Back:
                    finish();;
                    break;
            }
        }
    }
}
