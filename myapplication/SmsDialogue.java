package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class SmsDialogue extends DialogFragment {
    NoticeDialogListener listener;
    String message;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //inflates pop up message for sms text
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflates and sets sms dialog box
        builder.setView(inflater.inflate(R.layout.sndmsg, null))

                .setPositiveButton("Send message", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        message = dialog.toString();

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SmsDialogue.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
    //listens for user actions
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    //attaches to view contact activity
    public void onAttach(Context context) {
        super.onAttach(context);

        try {

            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(" The view contact class must implement NoticeDialogListener");
        }
    }
}
