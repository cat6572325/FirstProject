package com.yanbober.support_library_demo.Custom;

import android.net.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.jph.takephoto.app.*;
import com.jph.takephoto.compress.*;
import com.jph.takephoto.model.*;
import com.yanbober.support_library_demo.*;
import java.io.*;
import me.shaohui.advancedluban.*;

import com.yanbober.support_library_demo.R;


public class CustomHelper 
{
private View rootView;
    private RadioGroup rgCrop,rgCompress,rgFrom,rgCropSize,rgCropTool,rgShowProgressBar,rgPickTool,rgCompressTool,rgCorrectTool;
    private EditText etCropHeight,etCropWidth,etLimit,etSize,etHeightPx,etWidthPx;
    TakePhoto takePhoto;
    public static CustomHelper of(View rootView,TakePhoto takePhoto){
        return new CustomHelper(rootView,takePhoto);
    }
    private CustomHelper(View rootView,TakePhoto takePhoto) {
        this.rootView = rootView;
        this.takePhoto=takePhoto;
        init();
    }
    private void init(){
        rgCrop= (RadioGroup) rootView.findViewById(R.id.rgCrop);
        rgCompress= (RadioGroup) rootView.findViewById(R.id.rgCompress);
        rgCompressTool= (RadioGroup) rootView.findViewById(R.id.rgCompressTool);
        rgCropSize= (RadioGroup) rootView.findViewById(R.id.rgCropSize);
        rgFrom= (RadioGroup) rootView.findViewById(R.id.rgFrom);
        rgPickTool= (RadioGroup) rootView.findViewById(R.id.rgPickTool);
        rgCorrectTool= (RadioGroup) rootView.findViewById(R.id.rgCorrectTool);
        rgShowProgressBar= (RadioGroup) rootView.findViewById(R.id.rgShowProgressBar);
        rgCropTool= (RadioGroup) rootView.findViewById(R.id.rgCropTool);
        etCropHeight= (EditText) rootView.findViewById(R.id.etCropHeight);
        etCropWidth= (EditText) rootView.findViewById(R.id.etCropWidth);
        etLimit= (EditText) rootView.findViewById(R.id.etLimit);
        etSize= (EditText) rootView.findViewById(R.id.etSize);
        etHeightPx= (EditText) rootView.findViewById(R.id.etHeightPx);
        etWidthPx= (EditText) rootView.findViewById(R.id.etWidthPx);

        File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        int limit= Integer.parseInt(etLimit.getText().toString());
        if(limit>1){
            if(rgCrop.getCheckedRadioButtonId()==R.id.rbCropYes){
                takePhoto.onPickMultipleWithCrop(limit,getCropOptions());//不裁剪
            }else {
                takePhoto.onPickMultiple(limit);
            }
            return;
        }
        if(rgFrom.getCheckedRadioButtonId()==R.id.rbFile){
            if(rgCrop.getCheckedRadioButtonId()==R.id.rbCropYes){
                takePhoto.onPickFromDocumentsWithCrop(imageUri,getCropOptions());
            }else {
                takePhoto.onPickFromDocuments();
            }
            return;
        }else {
            if(rgCrop.getCheckedRadioButtonId()==R.id.rbCropYes){
                takePhoto.onPickFromGalleryWithCrop(imageUri,getCropOptions());//从相机
            }else {
                takePhoto.onPickFromGallery();
            }
        }

    }

    public void onClick(View view,TakePhoto takePhoto) {
        File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        int limit= Integer.parseInt(etLimit.getText().toString());
        if(limit>1){
            if(rgCrop.getCheckedRadioButtonId()==R.id.rbCropYes){
                takePhoto.onPickMultipleWithCrop(limit,getCropOptions());//不裁剪
            }else {
                takePhoto.onPickMultiple(limit);
            }
            return;
        }
        if(rgFrom.getCheckedRadioButtonId()==R.id.rbFile){
            if(rgCrop.getCheckedRadioButtonId()==R.id.rbCropYes){
                takePhoto.onPickFromDocumentsWithCrop(imageUri,getCropOptions());
            }else {
                takePhoto.onPickFromDocuments();
            }
            return;
        }else {
            if(rgCrop.getCheckedRadioButtonId()==R.id.rbCropYes){
                takePhoto.onPickFromGalleryWithCrop(imageUri,getCropOptions());//从相机
            }else {
                takePhoto.onPickFromGallery();
            }
        }
        /*
        switch (view.getId()){
            case R.id.btnPickBySelect:
                int limit= Integer.parseInt(etLimit.getText().toString());
                if(limit>1){
                    if(rgCrop.getCheckedRadioButtonId()==R.id.rbCropYes){
                        takePhoto.onPickMultipleWithCrop(limit,getCropOptions());//不裁剪
                    }else {
                        takePhoto.onPickMultiple(limit);
                    }
                    return;
                }
                if(rgFrom.getCheckedRadioButtonId()==R.id.rbFile){
                    if(rgCrop.getCheckedRadioButtonId()==R.id.rbCropYes){
                        takePhoto.onPickFromDocumentsWithCrop(imageUri,getCropOptions());
                    }else {
                        takePhoto.onPickFromDocuments();
                    }
                    return;
                }else {
                    if(rgCrop.getCheckedRadioButtonId()==R.id.rbCropYes){
                        takePhoto.onPickFromGalleryWithCrop(imageUri,getCropOptions());//从相机
                    }else {
                        takePhoto.onPickFromGallery();
                    }
                }
                break;
            case R.id.btnPickByTake:
                if(rgCrop.getCheckedRadioButtonId()==R.id.rbCropYes){
                    takePhoto.onPickFromCaptureWithCrop(imageUri,getCropOptions());//裁剪
                }else {
                    takePhoto.onPickFromCapture(imageUri);
                }
                break;
            default:
                break;
        }*/
    }
    private void configTakePhotoOption(TakePhoto takePhoto){
        TakePhotoOptions.Builder builder=new TakePhotoOptions.Builder();
        if(rgPickTool.getCheckedRadioButtonId()==R.id.rbPickWithOwn){
            builder.setWithOwnGallery(true);
        }
        if(rgCorrectTool.getCheckedRadioButtonId()==R.id.rbCorrectYes){
          //  builder.setCorrectImage(true);
        }
        takePhoto.setTakePhotoOptions(builder.create());

    }
    private void configCompress(TakePhoto takePhoto){
        if(rgCompress.getCheckedRadioButtonId()!=R.id.rbCompressYes){
            takePhoto.onEnableCompress(null,false);
            return ;
        }
        int maxSize= Integer.parseInt(etSize.getText().toString());
        int width= Integer.parseInt(etCropWidth.getText().toString());
        int height= Integer.parseInt(etHeightPx.getText().toString());
        boolean showProgressBar=rgShowProgressBar.getCheckedRadioButtonId()==R.id.rbShowYes? true:false;
        CompressConfig config;
        if(rgCompressTool.getCheckedRadioButtonId()==R.id.rbCompressWithOwn){
            config=new CompressConfig.Builder()
				.setMaxSize(maxSize)
				.setMaxPixel(width>=height? width:height)
				.create();
        }else {
            LubanOptions option=new LubanOptions.Builder()
				.setGear(Luban.CUSTOM_GEAR)
				.setMaxHeight(height)
				.setMaxWidth(width)
				.setMaxSize(maxSize)
				.create();
            config=CompressConfig.ofLuban(option);
        }
        takePhoto.onEnableCompress(config,showProgressBar);

    }
    private CropOptions getCropOptions(){
        if(rgCrop.getCheckedRadioButtonId()!=R.id.rbCropYes)return null;
        int height= Integer.parseInt(etCropHeight.getText().toString());
        int width= Integer.parseInt(etCropWidth.getText().toString());
        boolean withWonCrop=rgCropTool.getCheckedRadioButtonId()==R.id.rbCropOwn? true:false;

        CropOptions.Builder builder=new CropOptions.Builder();

        if(rgCropSize.getCheckedRadioButtonId()==R.id.rbAspect){
            builder.setAspectX(width).setAspectY(height);
        }else {
            builder.setOutputX(width).setOutputY(height);
        }
        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }

}
