package com.yanbober.support_library_demo.Custom;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.bumptech.glide.*;
import com.jph.takephoto.model.*;
import com.yanbober.support_library_demo.*;
import java.io.*;
import java.util.*;
public class ResultActivity extends Activity {
    ArrayList<TImage>images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultactivity_layout);
        images= (ArrayList<TImage>) getIntent().getSerializableExtra("images");
        showImg();
    }
    private void showImg() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llImages);
        for (int i = 0, j = images.size(); i < j - 1; i += 2) {
            View view = LayoutInflater.from(this).inflate(R.layout.image_show, null);
            ImageView imageView1 = (ImageView) view.findViewById(R.id.imgShow1);
            ImageView imageView2 = (ImageView) view.findViewById(R.id.imgShow2);
            Glide.with(this).load(new File(images.get(i).getPath())).into(imageView1);
            Glide.with(this).load(new File(images.get(i + 1).getPath())).into(imageView2);
            linearLayout.addView(view);
        }
        if (images.size() % 2 == 1) {
            View view = LayoutInflater.from(this).inflate(R.layout.image_show, null);
            ImageView imageView1 = (ImageView) view.findViewById(R.id.imgShow1);
            Glide.with(this).load(new File(images.get(images.size() - 1).getPath())).into(imageView1);
            linearLayout.addView(view);
        }

    }
}
