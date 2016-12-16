package com.yanbober.support_library_demo;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.provider.SyncStateContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import java.io.File;

/**
 * Created by cat6572325 on 16-11-11.
 */
public class Message_Dialog  extends DialogFragment
{
    Button sure,cancel;
    String file=null;
    Context con;
    View view,button;
    public Message_Dialog(Context con,String file,View view)
    {
        super();
        this.file=file;
        this.con=con;
        this.button=view;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    // Get the layout inflater
    LayoutInflater inflater = getActivity().getLayoutInflater();
   view = inflater.inflate(R.layout.message_delete, null);
    // Inflate and set the layout for the dialog
    // Pass null as the parent view because its going in the dialog layout
    builder.setView(view)
        // Add action buttons
        .setPositiveButton("删除", new DialogInterface.OnClickListener()
{
    @Override
    public void onClick(DialogInterface dialog, int id)
    {
        File fi=new File(file);
        if (fi.exists()) { // 判断文件是否存在
            if (fi.isFile()) { // 判断是否是文件
                fi.delete(); // delete()方法 你应该知道 是删除的意思;

                Snackbar.make(button,"已删除",Snackbar.LENGTH_SHORT).show();
            } else if (fi.isDirectory()) { // 否则如果它是一个目录
            }
            fi.delete();
        } else {
            //文件不存在
            Snackbar.make(view,"录制时长过短已被删除",Snackbar.LENGTH_SHORT).show();
        }

    }
}).setNegativeButton("Cancel", null);
    return builder.create();
}

}