package com.yanbober.support_library_demo;


/**




 ***/


import android.app.ProgressDialog;
import android.content.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.support.v7.app.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.AdapterView.*;

import com.bumptech.glide.*;
import com.jph.takephoto.model.*;
import com.yanbober.support_library_demo.Http_Util.*;

import java.io.*;
import java.net.*;
import java.util.*;

import android.view.View.OnClickListener;

public class Setting_ extends AppCompatActivity {

    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    //user.headBitmap=bitmap;
                    //  Intent intent = getIntent();
                    //intent.putExtra("head",bitmap);

                    //setResult(0, intent);//返回页面1

                    //finish();
                    dialog.cancel();
                    break;
            }
        }
    };
    ListView rl;
    ProgressDialog dialog = null;
    Bitmap bitmap;
    MyChatAdapter ladapter;
    XCRoundImageView headImg = null;
    User user = new User();
    int[] layout = {R.layout.setting_list_item, R.layout.line_item};


    public ArrayList<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_);
        initView();

    }/////onCreate

    public void initView() {
        rl = (ListView) this.findViewById(R.id.tRecyclerView1);
        headImg = (XCRoundImageView) this.findViewById(R.id.drawer_headerImageView);
        headImg.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Setting_.this, SimpleActivity.class);
                startActivityForResult(intent, 1);





				/*Intent intent = new Intent();
                if (Build.VERSION.SDK_INT < 19) {//因为Android SDK在4.4版本后图片action变化了 所以在这里先判断一下
					intent.setAction(Intent.ACTION_GET_CONTENT);
				} else {
					intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
				}
				intent.setType("image/*");
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				startActivityForResult(intent, 1);
				
			}*/
            }
        });
        User u = new User();
        if (u.getBitmapurl != null && u.mydata.size() > 2)
            u.getBitmapurl.loadImageViewurl(u.mydata.get("headprturl").toString(), headImg, u.mydata);

        Bundle bun = this.getIntent().getExtras();
        if (bun != null && bun.containsKey("images")) {


        }
        addTextToList(user.mydata.get("nickname").toString(), "昵称", 0, R.drawable.home, 0);

        addTextToList("分割贱", "没有", 1, R.drawable.fab_bg_normal, 0);

        addTextToList("***", "登陆密码", 0, R.drawable.home, 1);
        addTextToList("***", "支付密码", 0, R.drawable.home, 1);


        ladapter = new MyChatAdapter(Setting_.this, lists, layout);
        rl.setAdapter(ladapter);
        rl.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view.findViewById(R.id.settinglistitemTextView1);
                String str = tv.getText().toString();
                if (str.equals("昵称")) {
                    Intent intent = new Intent(Setting_.this, Modify_Name_.class);
                    startActivity(intent);
                    finish();
                }
                if (str.equals("登陆密码")) {
                    Intent intent = new Intent(Setting_.this, Modify_Password_.class);
                    startActivity(intent);
                    finish();
                }
                if (str.equals("支付密码")) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("isprogress", 0);

                    Pop_Img.Builder p = new Pop_Img.Builder(Setting_.this, map);
                    p.setPositiveButton("[潮汕揭]初版\n问题反馈:(qq) 1213965634\n\n", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //	dialog.dismiss();
                            //login


                        }
                        //设置你的操作事项


                    });

                    p.create().show();
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO: Implement this method
        super.onActivityResult(requestCode, resultCode, data);
        User u = new User();
        String str = null;
        if (resultCode == 1) {//
            Bundle bundle = data.getExtras();
            if (bundle.containsKey("images")) {
                ArrayList<TImage> images;

                //String str=bun.getString("image");
                images = (ArrayList<TImage>) data.getExtras().get("images");
                Bitmap b = null;//(Bitmap)images.get(0);

                //Glide.with(this).load(new File(images.get(images.size() - 1).getPath())).asBitmap(b);
                File f = new File(images.get(images.size() - 1).getPath());
                ContentResolver cr = this.getContentResolver();
                Log.e("File", String.valueOf(f.length()));
                File dirFile;
                if (f.length() > 50000) {
                    Toast.makeText(Setting_.this, "文件过大", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        bitmap = BitmapFactory.decodeStream(cr.openInputStream(Uri.fromFile(f)));
                        dirFile = new File("/sdcard/180s/headpicture/" + randomProdoction() + ".png");
                        File file1 = new File("/sdcard/180s");
                        if (!file1.exists()) {
                            file1.mkdirs();
                        }
                        File file2 = new File("/sdcard/180s/headpicture");
                        if (!file2.exists()) {
                            file2.mkdirs();
                        }
                        if (!dirFile.exists()) {


                            dirFile.createNewFile();


                        } else {
                            dirFile.delete();
                            dirFile.createNewFile();
                        }
                        //	File myCaptureFile = new File(path + fileName);
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dirFile));
                        bitmap.compress(Bitmap.CompressFormat.PNG, 80, bos);


                        bos.flush();
                        bos.close();
                        //Bitmap b= toRoundBitmap(bitmap);
                        headImg.setImageBitmap(bitmap);
                        user.headBitmap = bitmap;
                /*上传文件
				 PATCH方法
				 */
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("handler", mHandler);
                        map.put("Context", Setting_.this);
                        map.put("key", "photofile");
                        map.put("count", 1);
                  /*  http_thread_ htt = new http_thread_(Setting_.this, "http://trying-video.herokuapp.com/user/image?token=" + user.token
                            , dirFile.getPath()
                            , mHandler
                            , map);
                    Thread c = new Thread(htt);
                    c.start();
                    */
                        dialog = new ProgressDialog(Setting_.this);
                        dialog.setTitle("上传中..");
                        dialog.show();

                        NewOkhttp n = new NewOkhttp("http://trying-video.herokuapp.com/user/image/replace?token=" + user.token
                                , dirFile
                                , map
                        );

                        Thread x = new Thread(n);
                        x.start();

                    } catch (IOException e) {
                        Toast.makeText(Setting_.this, e.toString(), Toast.LENGTH_LONG).show();

                    }
                }
            }


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = getIntent();
        setResult(1, intent);//返回页面1
        finish();

    }

    private String randomProdoction() {
        int random = 0;
        String str = null;
        Random ran = new Random(System.currentTimeMillis());
        for (int i = 0; i < 20; i++) {

            random = ran.nextInt(10);
            str += String.valueOf(random);
            //数字加一个字母
            switch (random) {
                case 0:
                    str += "a";
                    break;

                case 1:
                    str += "b";
                    break;

                case 2:
                    str += "c";
                    break;

                case 3:
                    str += "e";
                    break;

                case 4:
                    str += "f";
                    break;

                case 5:
                    str += "g";
                    break;

                case 6:
                    str += "h";
                    break;

                case 7:
                    str += "i";
                    break;
            }

        }


        return str;
    }

    public void addTextToList(String text, String name, int who, int id, int isarrow) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("who", who);
        map.put("image", id);
        map.put("text", text);
        map.put("name", name);
        map.put("isarrow", isarrow);
        map.put("layout", who);

        lists.add(map);
    }///addtextToList


    private class MyChatAdapter extends BaseAdapter {

        Context context = null;
        ArrayList<HashMap<String, Object>> chatList = null;
        int[] layout;
        String[] from;
        int[] to;


        public MyChatAdapter(Context context,
                             ArrayList<HashMap<String, Object>> chatList, int[] layout
        ) {
            super();
            this.context = context;
            this.chatList = chatList;
            this.layout = layout;

        }


        public int getCount() {
            // TODO Auto-generated method stub
            return chatList.size();
        }


        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }


        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }


        ////////////
        class ViewHolder {
            public ImageView imageView = null;
            public TextView textView = null;
            public String title;
            private ImageView i_icon, i_icon2, flag;
            public TextView name = null, data = null;
        }
        ////////////

        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder = new ViewHolder();

            int who = (Integer) chatList.get(position).get("who");


            switch (who) {
                case 0:

                    convertView = LayoutInflater.from(context).inflate(
                            layout[who], null);
                    holder.name = (TextView) convertView.findViewById(R.id.settinglistitemTextView1);
                    holder.data = (TextView) convertView.findViewById(R.id.settinglistitemTextView2);
                    holder.flag = (ImageView) convertView.findViewById(R.id.settinglistitemTextView3);


                    if ((Integer) chatList.get(position).get("isarrow") == 1) {//是否显示右边箭头
                        holder.flag.setVisibility(View.VISIBLE);
                    }
                    holder.data.setText((String) chatList.get(position).get("text"));
                    holder.name.setText((String) chatList.get(position).get("name"));
                    break;
                case 1:
                    isEnabled(position);
                    /////分隔线，挺大条的，但无伤大雅
                    convertView = LayoutInflater.from(context).inflate(
                            layout[who], null);
                    View v = (View) convertView.findViewById(R.id.lineitemView1);
                    v.setVisibility(View.INVISIBLE);
                    v.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {

                        }
                    });

                    break;


            }
            return convertView;

        }
    }///////MyChatAdapter


}
