package com.yanbober.support_library_demo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.*;
import android.graphics.*;
import android.os.*;



public class Pop_Img extends Dialog {

	public Pop_Img(Context context) {
		super(context);
	}

	public Pop_Img(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		Home home;
		Setting_ setting_;
		private String title,acco,pas;
		private String message;
		EditText loe,pae;
		
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		Bitmap btm;

		private DialogInterface.OnClickListener positiveButtonClickListener;
		private DialogInterface.OnClickListener negativeButtonClickListener;

		public Builder(Context con) {
			this.context = context;
			
		}
		public Builder(Home home)
		{
			this.home=home;
		}
		public Builder(Setting_ setting_)
		{
			this.setting_=setting_;
		}
		
		
		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		/**
		 * Set the Dialog message from resource
		 * 
		 * @param title
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
		public Builder setPositiveButton(int positiveButtonText,
										 DialogInterface.OnClickListener listener) {
			this.positiveButtonText = (String) context
				.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(String positiveButtonText,
										 DialogInterface.OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
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
			View layout ;
			if(home!=null)
			{
				
			LayoutInflater inflater = (LayoutInflater) home
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			dialog = new Pop_Img(home,R.style.Dialog);
			layout= inflater.inflate(R.layout.login_dialog, null);
			dialog.addContentView(layout, new LayoutParams(
									  LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

			
				loe=(EditText)layout.findViewById(R.id.regE);
				pae=(EditText)layout.findViewById(R.id.pasE);

				

			// set the dialog title
			//((TextView) layout.findViewById(R.id.title)).setText(title);
			// set the confirm button
	//	if (positiveButtonText != null) {
		//	((Button) layout.findViewById(R.id.btn_login))
		//	.setText(positiveButtonText);
		
		
			if (positiveButtonClickListener != null) {
				((Button) layout.findViewById(R.id.btn_login))
					.setOnClickListener(new View.OnClickListener()
					{
						public void onClick(View v) {
							positiveButtonClickListener.onClick(dialog,
										DialogInterface.BUTTON_POSITIVE);
							Toast.makeText(home,"ii",Toast.LENGTH_SHORT).show();				
							
							ThreadEx t=new ThreadEx(home,"login"+"|"+loe.getText().toString()+"|"+pae.getText().toString());
							Thread x=new Thread(t);
							x.start();						
																
						}
					});
			}
			
			
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

			dialog.setContentView(layout);
			return dialog;
			}//ifhome!=null
		
			if(setting_!=null)
			{

				LayoutInflater inflater = (LayoutInflater) setting_
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				// instantiate the dialog with the custom Theme
				dialog = new Pop_Img(setting_,R.style.Dialog);
				layout= inflater.inflate(R.layout.enter_pay_pwd_dlg, null);
				dialog.addContentView(layout, new LayoutParams(
										  LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));


				//loe=(EditText)layout.findViewById(R.id.regE);
			//	pae=(EditText)layout.findViewById(R.id.pasE);
				if (positiveButtonClickListener != null) {
					//确认
					
					((Button) layout.findViewById(R.id.enterpaypwddlgButton1))
						.setOnClickListener(new View.OnClickListener()
						{
							public void onClick(View v) {
								positiveButtonClickListener.onClick(dialog,
																	DialogInterface.BUTTON_POSITIVE);
							    				
																	

							}
						});
				}
				
				dialog.setContentView(layout);
				return dialog;
				}else
				{
					return null;
				}
				
		}
	}
}
