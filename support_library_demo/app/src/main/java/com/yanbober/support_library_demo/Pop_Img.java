package com.yanbober.support_library_demo;

/*
        这个原本是要弹出一张图片作为图片显示器的，但被我拿来做了一些弹窗
        目前有Setting_
        Round_Video_

 */


import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.support.design.widget.*;
import android.util.*;
import android.view.*;
import android.view.ViewGroup.*;
import android.widget.*;

import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.yanbober.support_library_demo.Http_Util.*;
import java.util.*;

public class Pop_Img extends Dialog {

    public Pop_Img(Context context) {
        super(context);
    }

    public Pop_Img(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        ImageView Video_data_edit_close;
        Home home;
        Round_Video_ round_video_;
        Setting_ setting_;
        Collect_ collect_;
        Login_ login;
        MainActivity main;
        TimerTask http_uploadFile_;
        HashMap<String, Object> map;
        private String title, acco, pas;
        private String message;
        EditText loe, pae, Pay_pwd, round_title, round_money, round_ps, collect_enter;
        RelativeLayout indelayout, error_RelativeLayout;
        Button uploadbutton, Set_Pay_Pwd_sure, Set_Pay_Pwd_cancel, collect_button, collect_button2;
        RoundProgressBar bar;
        Handler mViewUpdateHandler;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        Bitmap btm;
        String ERror = null;


        final User user = new User();
        boolean red = true;
        boolean green = true;
        ImageView miusic, voice;
        TextView miusict, voicet, collect_paid_text, error_Text, Paid_pwd_text;
        File_with_ file_with;

        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;
        private DialogInterface.OnClickListener add_miusicOnclick;
        private DialogInterface.OnClickListener add_reviceOnclick;
        private DialogInterface.OnClickListener upload_click;
        private DialogInterface.OnClickListener ERRor_Click;

        public Builder(Context con) {
            this.context = context;

        }

        public Builder(Home home) {
            this.home = home;
        }

        public Builder(Round_Video_ round_video_, HashMap<String, Object> map, File_with_ file_with) {
            this.file_with = file_with;
            this.round_video_ = round_video_;
            this.map = map;
            //   this.http_uploadFile_=(Http_UploadFile_)httpLoad;
        }

        public Builder(Setting_ setting_, HashMap<String, Object> map) {
            this.setting_ = setting_;
            this.map = map;
        }

        public Builder(Collect_ collect_, HashMap<String, Object> map) {
            this.collect_ = collect_;
            this.map = map;
        }


        public Builder(Context context, String ERror, HashMap<String, Object> map) {
            //错误提示框
            this.context = context;
            this.map = map;
            this.ERror = ERror;

        }

        public Builder(Login_ login_, HashMap<String, Object> log_map) {
            login=login_;
            map=log_map;
        }

        public Builder(MainActivity mainActivity, HashMap<String, Object> main_map) {
            main=mainActivity;
            map=main_map;
        }


        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setUploadclick(int positiveButtonText,
                                      DialogInterface.OnClickListener listener) {
            //// TODO  上传
            //this.positiveButtonText = (String) context
            //	.getText(positiveButtonText);
            this.upload_click = listener;
            return this;
        }

        public Builder setERorOnClick(int positiveButtonText,
                                      DialogInterface.OnClickListener listener) {
            this.ERRor_Click = listener;
            return this;

        }

        public Builder setPositiveButton(String positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setaddMiusic(DialogInterface.OnClickListener listener) {
            this.add_miusicOnclick = listener;
            return this;
        }

        public Builder setrevioce(DialogInterface.OnClickListener listener) {
            this.add_reviceOnclick = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setbtm(Bitmap negativeButtonText) {
            this.btm = negativeButtonText;
            //this.negativeButtonClickListener = listener;
            return this;
        }

        public Pop_Img create() {
            final Pop_Img dialog;
            View layout;


            // set the dialog title
            //((TextView) layout.findViewById(R.id.title)).setText(title);
            // set the confirm button
            //	if (positiveButtonText != null) {
            //	((Button) layout.findViewById(R.id.btn_login))
            //	.setText(positiveButtonText);


			
			/* } else {
             // if no confirm button just set the visibility to GONE
			 layout.findViewById(R.id.positiveButton).setVisibility(
			 View.GONE);
			 }*/
            // set the cancel button
			/*if (negativeButtonText != null) {
			 ((Button) layout.findViewById(R.id.negativeButton))
			 .setText(negativeButtonText);
			 if (negativeButtonClickListener != null) {
			 ((Button) layout.findViewById(R.id.negativeButton))
			 .setOnClickListener(new View.OnClickListener() {
			 public void onClick(View v) {
			 negativeButtonClickListener.onClick(dialog,
			 DialogInterface.BUTTON_NEGATIVE);
			 }
			 });
			 }
			 } else {
			 // if no confirm button just set the visibility to GONE
			 layout.findViewById(R.id.negativeButton).setVisibility(
			 View.GONE);
			 }*/
            // set the content message
			/*if (message != null) {
			 ((TextView) layout.findViewById(R.id.message)).setText(message);
			 } else if (contentView != null) {
			 // if no message set
			 // add the contentView to the dialog body
			 ((LinearLayout) layout.findViewById(R.id.content))
			 .removeAllViews();
			 ((LinearLayout) layout.findViewById(R.id.content))
			 .addView(contentView, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
			 }*/
            if (main!=null)
            {
                LayoutInflater inflater = (LayoutInflater) main
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                // instantiate the dialog with the custom Theme
                dialog = new Pop_Img(main, R.style.Dialog);
                layout = inflater.inflate(R.layout.login_wait_dialog, null);
                dialog.addContentView(layout, new LayoutParams(
                        LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
                error_Text=(TextView)layout.findViewById(R.id.text_dialog);
                error_Text.setText("正在加载中..");
                return dialog;

            }
            if (login != null) {
                LayoutInflater inflater = (LayoutInflater) login
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                // instantiate the dialog with the custom Theme
                dialog = new Pop_Img(login, R.style.Dialog);
                layout = inflater.inflate(R.layout.login_wait_dialog, null);
                dialog.addContentView(layout, new LayoutParams(
                        LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

                return dialog;
            }
            if (setting_ != null) {
                if ((Integer) map.get("isprogress") == 0) {
                    LayoutInflater inflater = (LayoutInflater) setting_
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    // instantiate the dialog with the custom Theme
                    dialog = new Pop_Img(setting_, R.style.Dialog1);
                    layout = inflater.inflate(R.layout.enter_pay_pwd_dlg, null);

                    dialog.addContentView(layout, new LayoutParams(
                            LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
                    Set_Pay_Pwd_sure = (Button) layout.findViewById(R.id.enterpaypwddlgButton1);
                    Set_Pay_Pwd_cancel = (Button) layout.findViewById(R.id.enterpaypwddlgButton2);
                    Pay_pwd = (EditText) layout.findViewById(R.id.Pay_Pwd_Edit);
                    Paid_pwd_text = (TextView) layout.findViewById(R.id.paid_pwd_text);
                    if (map.get("?").equals("密码"))
                    {
                        Paid_pwd_text.setText("输入密码");
                        Set_Pay_Pwd_sure.setText("确定");
                        Set_Pay_Pwd_sure.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {

                                positiveButtonClickListener.onClick(dialog,
                                        DialogInterface.BUTTON_POSITIVE);
                                //将点击事件传出去

                            }
                        });


                            }else {
                        if (user.mydata.get("paypassword").toString().equals("")) {
                            Paid_pwd_text.setText("未设置支付密码");
                            Set_Pay_Pwd_sure.setText("马上填写");
                            Pay_pwd.setEnabled(false);

                        }
                        Set_Pay_Pwd_sure.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {

                                positiveButtonClickListener.onClick(dialog,
                                        DialogInterface.BUTTON_POSITIVE);
                                //将点击事件传出去

                                Log.e("PaidWord", user.mydata.get("paypassword").toString());//.equals("0"))
                                if (user.mydata.get("paypassword").toString() != null) {
                                    if (Pay_pwd.getText().toString().equals(user.mydata.get("paypassword").toString())) {//如果支付密码有且配对成功
                                        Intent intent = new Intent(setting_, Set_Pay_pwd_.class);
                                        setting_.startActivity(intent);
                                        setting_.
                                                finish();
                                        dialog.dismiss();

                                    } else {

                                        Snackbar.make(view, "you press key for sure  ,But it's error" + Pay_pwd.getText().toString(), Snackbar.LENGTH_SHORT).show();

                                    }

                                } else {//设置支付密码

                                }


                            }


                        });

                        Set_Pay_Pwd_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar.make(view, "you press key for cancel  " + Pay_pwd.getText().toString(), Snackbar.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    }

                    //loe=(EditText)layout.findViewById(R.id.regE);
                    //	pae=(EditText)layout.findViewById(R.id.pasE);
			/*	if (positiveButtonClickListener != null) {
					//确认
					
					((Button) layout.findViewById(R.id.videodataeditlayoutButton1))//.enterpaypwddlgButton1))
						.setOnClickListener(new View.OnClickListener()
						{
							public void onClick(View v) {
								positiveButtonClickListener.onClick(dialog,
																	DialogInterface.BUTTON_POSITIVE);
							    				
																	

							}
						});
						
				}*/

                    dialog.setContentView(layout);


                    return dialog;
                } else//if(isprogress)
                {
                    //TODO 显示成进度圈君
                    LayoutInflater inflater = (LayoutInflater) setting_
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    // instantiate the dialog with the custom Theme
                    dialog = new Pop_Img(setting_, R.style.Dialog);
                    layout = inflater.inflate(R.layout.custom_progressbar, null);//.enter_pay_pwd_dlg, null);

                    dialog.addContentView(layout, new LayoutParams(
                            LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

                    dialog.setContentView(layout);
                    RoundProgressBar bar = (RoundProgressBar) layout.findViewById(R.id.roundProgressBar2);
                    bar.setMax((Integer) map.get("max"));
                    bar.setTextSize((Integer) map.get("textsize"));
                    bar.setProgress((Integer) map.get("progress"));
                    bar.setCricleColor((Integer) map.get("color"));
                    return dialog;
                }//if(isprogress) else

            }

            if (collect_ != null) {

                LayoutInflater inflater = (LayoutInflater) collect_
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                // instantiate the dialog with the custom Theme
                dialog = new Pop_Img(collect_, R.style.Dialog1);
                layout = inflater.inflate(R.layout.enter_pay_pwd_dlg, null);
                dialog.addContentView(layout, new LayoutParams(
                        LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
                collect_enter = (EditText) layout.findViewById(R.id.Pay_Pwd_Edit);
                collect_button = (Button) layout.findViewById(R.id.enterpaypwddlgButton1);
                collect_button2 = (Button) layout.findViewById(R.id.enterpaypwddlgButton2);

                collect_paid_text = (TextView) layout.findViewById(R.id.paid_pwd_text);
                if (user.paidPwd.equals("null")) {//支付密码为空，也就是没有设置过
                    //设置标题显示什么
                    collect_paid_text.setText("设置支付密码");
                    collect_button.setText("马上设置");
                } else {//
                    collect_paid_text.setText("验证支付密码");
                    collect_button.setText("支付");

                }
                collect_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                collect_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (collect_enter.getText().toString().equals("")) {
                            if (user.mydata.get("paypassword") == null) {
                                Toast.makeText(collect_, "未输入任何字符!!", Toast.LENGTH_LONG).show();
                            } else {//已设置支付密码，所以判断
                                judgepaypassword(collect_enter.getText().toString(),map.get("video_id").toString());
                            }
                        }

                            //设置点击按钮做什么动作
                            HashMap<String, Object> map2 = new HashMap<String, Object>();
                            map2.put("context", collect_);

                            map2.put("handler", collect_.mHandler);
                        map2.put("video_id",map.get("cost").toString());
                            map2.put("paypassword", collect_enter.getText().toString());
                            String url = "http://trying-video.herokuapp.com/user/oldpayword?token=" + user.token;
                            Http_UploadFile_ http_uploadFile_ = new Http_UploadFile_(url
                                    , map2, "11");//直接验证

                            Thread xx = new Thread(http_uploadFile_);
                            xx.start();


                            // Intent intent = new Intent(collect_, Pay_Complete_.class);
                            //	startActivity(intent);


                    }
                });


                return dialog;

            }//if Collect_

            if (round_video_ != null) {
                if ((int) map.get("upload") == 0) {
                    //TODO 进度圈
                    LayoutInflater inflater = (LayoutInflater) round_video_
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    // instantiate the dialog with the custom Theme
                    dialog = new Pop_Img(round_video_, R.style.Dialog1);
                    layout = inflater.inflate(R.layout.round_video_sucess_progres, null);//.enter_pay_pwd_dlg, null);
                    dialog.addContentView(layout, new LayoutParams(
                            LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
                    dialog.setContentView(layout);
                     bar = (RoundProgressBar) layout.findViewById(R.id.round_bar);
                    bar.setMax((Integer) map.get("max"));
                    bar.setTextSize((Integer) map.get("textsize"));
                    bar.setProgress((Integer) map.get("progress"));
                    bar.setCricleColor((Integer) map.get("color"));
                    mViewUpdateHandler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            //接受请求设置Progressbar的进度
                            super.handleMessage(msg);
                            if (msg.arg1 == 101) {
                                dialog.dismiss();
                            }
                            bar.setProgress(msg.arg1);
                            bar.invalidate();
                        }
                    };
                } else {
                    //TODO 是一个编辑信息的对话框
                    LayoutInflater inflater = (LayoutInflater) round_video_
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    // instantiate the dialog with the custom Theme
                    dialog = new Pop_Img(round_video_, R.style.Dialog1);
                    layout = inflater.inflate(R.layout.video_data_edit_layout, null);//.enter_pay_pwd_dlg, null);

                    dialog.addContentView(layout, new LayoutParams(
                            LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

                    dialog.setContentView(layout);


                    //loe=(EditText)layout.findViewById(R.id.regE);
                    //	pae=(EditText)layout.findViewById(R.id.pasE);

                    //todo 控件初始化
                    round_title = (EditText) layout.findViewById(R.id.Round_Tile);
                    round_money = (EditText) layout.findViewById(R.id.Round_Money);
                    round_ps = (EditText) layout.findViewById(R.id.Round_Ps);
                    miusict = (TextView) layout.findViewById(R.id.video_edit_miusic_tv);
                    miusic = (ImageView) layout.findViewById(R.id.video_edit_miusic_img);//.enterpaypwddlgButton1))
                    voice = (ImageView) layout.findViewById(R.id.video_edit_voice_img);
                    voicet = (TextView) layout.findViewById(R.id.video_edit_voice_tv);
                    uploadbutton = (Button) layout.findViewById(R.id.videodataeditlayoutButton1);
                    bar = (RoundProgressBar) layout.findViewById(R.id.roundProgressBar2);
                    indelayout = (RelativeLayout) layout.findViewById(R.id.video_edit_hide_layout);
                    Video_data_edit_close = (ImageView) layout.findViewById(R.id.edi_video_data_close_button);
                    //初始进度圈是隐藏的
                    indelayout.setVisibility(View.INVISIBLE);
                    bar.setMax(100);
                    if (upload_click != null) {
                        //TODO 上传
                        uploadbutton.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                upload_click.onClick(dialog,
                                        DialogInterface.BUTTON_POSITIVE);
                                /*POST   http://localhost:1103/user/video/detail/:_vid?token=${token}    /_vid为视频的id/

                                    {
                                        "uploader" : ${uploader},    //上传者(string)
                                        "title" : ${title},    //标题(string)
                                        "introduction" : ${introduction},    //简介(string)
                                        "price" : ${price},    //价格(Number)
                                        "paidPerson" : ${paidPerson},    //付款人ID(string)
                                        "cocerPerson" : ${cocerPerson},    //收藏人名字(string)
                                        "paidppnumber" : ${paidppnumber},    //付款人数(Number)
                                        "concernednumber" : ${concernednumber}    //收藏人数(Number)
                                    }
                                    >>  返回 status: '信息已以相同id保存'
                                    */
                                User u=new User();
                                if (u.phone.equals("null"))
                                {
                                    Toast.makeText(round_video_,"请先登录",Toast.LENGTH_LONG).show();
                                    dialog.dismiss();

                                }else {
                                    HashMap<String, Object> maphttp = new HashMap<String, Object>();
                                    maphttp.put("uploader", u.mydata.get("_id").toString());
                                    maphttp.put("title", round_title.getText().toString());
                                    maphttp.put("introduction", round_ps.getText().toString());
                                    maphttp.put("price", round_money.getText().toString());
                                    maphttp.put("paidPerson", "");
                                    maphttp.put("cocerPerson", u.mydata.get("_id").toString());//
                                    maphttp.put("paidppnumber", "0");//购买人数
                                    maphttp.put("concernednumber", "0");//收藏人数
                                    maphttp.put("handler", round_video_.mHandler);
                                    maphttp.put("count", 0);
                                    maphttp.put("Context", round_video_);
                                    ArrayList<HashMap<String, Object>> datamaps = new ArrayList<HashMap<String, Object>>();
                                    datamaps.add(maphttp);
                                    u.notLoadforVideo_list = datamaps;

                                    Toast.makeText(round_video_, "已保存此信息", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                }
                               // indelayout.setVisibility(View.VISIBLE);
                                //解除隐藏布局的隐藏
                                //mViewUpdateHandler.sendEmptyMessage(0);
                              /*   URL url = null;
                                try {
                                    url = new URL("http://192.168.1.112:1103/user/video/all/detail");
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                Http_UploadFile_ http_uploadFile_ = new Http_UploadFile_(round_video_
                                        , round_video_.mHandler
                                        //, new File("/sdcard/RoundVideo/video.mp4")
                                        , "http://192.168.1.112:1103/user/video/all/detail"
                                        , "0"
                                        , "GET"
                                        , maphttp);*/
                            }
                        });
                    }
                    if (add_miusicOnclick != null) {
                        //TODO 添加音乐的按钮
                        miusic.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                add_miusicOnclick.onClick(dialog,
                                        DialogInterface.BUTTON_POSITIVE);
                                if (red == true) {
                                    miusic.setBackgroundResource(R.drawable.add_music);
                                    miusict.setTextColor(Color.rgb(160, 0, 250));
                                    voice.setBackgroundResource(R.drawable.remove_voice_black);
                                    red = false;
                                    green = true;
                                    //表示音乐不能再添加
                                    //而消音可以点击
                                } else {
                                    //已点击过就不会再做任何其他响应
                                }
                            }
                        });

                    }
                    if (add_reviceOnclick != null) {
                        //TODO 去除声音的按钮
                        //.enterpaypwddlgButton1))
                        voice.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                add_reviceOnclick.onClick(dialog,
                                        DialogInterface.BUTTON_POSITIVE);
                                if (green == true) {
                                    miusic.setBackgroundResource(R.drawable.add_music_black);
                                    miusict.setTextColor(Color.rgb(100, 100, 100));
                                    voice.setBackgroundResource(R.drawable.remove_voice);
                                    red = true;
                                    green = false;
                                    //表示音乐可以再添加
                                    //而消音不再可以点击
                                } else {
                                    //已点击过就不会再做任何其他响应
                                }
                            }
                        });
                    }
                    Video_data_edit_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    mViewUpdateHandler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            //接受请求设置Progressbar的进度
                            super.handleMessage(msg);
                            if (msg.arg1 == 101) {
                                dialog.dismiss();
                            }
                            bar.setProgress(msg.arg1);
                            bar.invalidate();
                        }
                    };
                }// 不是进度圈而是编辑


                return dialog;
            }//round_video_


            else {
                return null;
            }


        }//onCreate

        public void OnProgressChanged(int count) {
            Message msg = new Message();
            msg.arg1 = count;
            mViewUpdateHandler.sendMessage(msg);

        }

        public void judgepaypassword(String str,String po) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("context", collect_);

            map.put("handler", collect_.mHandler);
            map.put("video_id",po);
            map.put("paypassword", "");
            String url = "http://trying-video.herokuapp.com/user/oldpayword?token=" + user.token;
            Http_UploadFile_ http_uploadFile_ = new Http_UploadFile_(url
                    , map, "11");


            Thread xx = new Thread(http_uploadFile_);
            xx.start();
        }
    }


		

}
