package com.yanbober.support_library_demo;

/*
        这个原本是要弹出一张图片作为图片显示器的，但被我拿来做了一些弹窗
        目前有Setting_
        Round_Video_

 */


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.*;
import android.graphics.*;
import android.os.*;

import com.yanbober.support_library_demo.Http_Util.Http_UploadFile_;

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
        HashMap<String, Object> map;
        private String title, acco, pas;
        private String message;
        EditText loe, pae, Pay_pwd;
        RelativeLayout indelayout;
        Button uploadbutton, Set_Pay_Pwd_sure, Set_Pay_Pwd_cancel;
        RoundProgressBar bar;
        Handler mViewUpdateHandler;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        Bitmap btm;

        boolean red = true;
        boolean green = true;
        ImageView miusic, voice;
        TextView miusict, voicet;
        Http_UploadFile_ http_uploadFile_;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;
        private DialogInterface.OnClickListener add_miusicOnclick;
        private DialogInterface.OnClickListener add_reviceOnclick;
        private DialogInterface.OnClickListener upload_click;

        public Builder(Context con) {
            this.context = context;

        }

        public Builder(Home home) {
            this.home = home;
        }

        public Builder(Round_Video_ round_video_, HashMap<String, Object> map,Object httpLoad) {

            this.round_video_ = round_video_;
            this.map = map;
            this.http_uploadFile_=(Http_UploadFile_)httpLoad;
        }

        public Builder(Setting_ setting_, HashMap<String, Object> map) {
            this.setting_ = setting_;
            this.map = map;
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
                    Set_Pay_Pwd_sure.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            positiveButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_POSITIVE);
                            //将点击事件传出去

                            Snackbar.make(view, "you press key for sure  " + Pay_pwd.getText().toString(), Snackbar.LENGTH_SHORT).show();
                        }


                    });

                    Set_Pay_Pwd_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Snackbar.make(view, "you press key for cancel  " + Pay_pwd.getText().toString(), Snackbar.LENGTH_SHORT).show();
                        }
                    });


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
                    RoundProgressBar bar = (RoundProgressBar) layout.findViewById(R.id.round_bar);
                    bar.setMax((Integer) map.get("max"));
                    bar.setTextSize((Integer) map.get("textsize"));
                    bar.setProgress((Integer) map.get("progress"));
                    bar.setCricleColor((Integer) map.get("color"));
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
                    miusict = (TextView) layout.findViewById(R.id.video_edit_miusic_tv);
                    miusic = (ImageView) layout.findViewById(R.id.video_edit_miusic_img);//.enterpaypwddlgButton1))
                    voice = (ImageView) layout.findViewById(R.id.video_edit_voice_img);
                    voicet = (TextView) layout.findViewById(R.id.video_edit_voice_tv);
                    uploadbutton = (Button) layout.findViewById(R.id.videodataeditlayoutButton1);
                    bar = (RoundProgressBar) layout.findViewById(R.id.roundProgressBar2);
                    indelayout = (RelativeLayout) layout.findViewById(R.id.video_edit_hide_layout);
                    Video_data_edit_close=(ImageView)layout.findViewById(R.id.edi_video_data_close_button);
                    //初始进度圈是隐藏的
                    indelayout.setVisibility(View.INVISIBLE);
                    bar.setMax(100);
                    if (upload_click != null) {
                        //TODO 上传
                        uploadbutton.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                upload_click.onClick(dialog,
                                        DialogInterface.BUTTON_POSITIVE);
                                indelayout.setVisibility(View.VISIBLE);
                                //解除隐藏布局的隐藏
                                //mViewUpdateHandler.sendEmptyMessage(0);
                                // 每次调用增加max for %1
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
                            bar.setProgress(bar.getProgress() + 1);
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
            mViewUpdateHandler.sendEmptyMessage(0);

        }

    }
}
