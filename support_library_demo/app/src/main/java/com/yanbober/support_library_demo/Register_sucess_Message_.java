package com.yanbober.support_library_demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by cat6572325 on 16-11-12.
 */
public class Register_sucess_Message_ extends AppCompatActivity {
    TextView tv;
    Button Complete_Finish;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_complete_layout);
        tv=(TextView)findViewById(R.id.paycompletelayoutTextView1);
        Complete_Finish=(Button)findViewById(R.id.complete_finish);

        tv.setText("注册成功");
        Complete_Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
