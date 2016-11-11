package com.yanbober.support_library_demo;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by cat6572325 on 16-11-11.
 */
public class Message_Dialog  extends DialogFragment
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    // Get the layout inflater
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View view = inflater.inflate(R.layout.message_delete, null);
    // Inflate and set the layout for the dialog
    // Pass null as the parent view because its going in the dialog layout
    builder.setView(view)
        // Add action buttons
        .setPositiveButton("Sign in", new DialogInterface.OnClickListener()
{
    @Override
    public void onClick(DialogInterface dialog, int id)
    {
    }
}).setNegativeButton("Cancel", null);
    return builder.create();
}

}