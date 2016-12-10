package com.yanbober.support_library_demo;

import android.app.Activity;
import android.content.*;
import android.os.*;
import android.view.*;
import com.jph.takephoto.app.*;
import com.jph.takephoto.model.*;
import com.yanbober.support_library_demo.Custom.*;
import java.util.*;

public class SimpleActivity extends TakePhotoActivity {
    private CustomHelper customHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView=LayoutInflater.from(this).inflate(R.layout.custom_photo,null);
        setContentView(contentView);
        customHelper=CustomHelper.of(contentView);
    }

    public void onClick(View view) {
        customHelper.onClick(view,getTakePhoto());
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        showImg(result.getImages());
    }

    private void showImg(ArrayList<TImage> images) {
        Intent intent = this.getIntent();
        intent.putExtra("images",images);

        this.setResult(1, intent);//返回页面1
        finish();

    }
}
