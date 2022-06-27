package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.SmsDialogue.NoticeDialogListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class viewContact extends AppCompatActivity
        implements NoticeDialogListener{
    private TextView phone;
    private String id;
    private String unformattedphone;
    private ConstraintLayout rootView;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private String message;
    FloatingActionButton send;
    FloatingActionButton callButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // initializes variables
        phone = (TextView) findViewById(R.id.phone);
        TextView address = (TextView) findViewById(R.id.address);
        TextView name = (TextView) findViewById(R.id.name);
        TextView email = (TextView) findViewById(R.id.email);
        send = (FloatingActionButton)findViewById(R.id.msg);

        //retrieves current contact information
        id = null;
        rootView = new ConstraintLayout(this);
        Intent i = this.getIntent();
        Bundle b = i.getExtras();
        if (b != null) {
            id = (String) b.get("id");
            // System.out.println(id);
            Contact c = null;
            Db db = new Db(this);
            c = db.getContact(Integer.parseInt(id));
            if (c == null) { // unable to retrieve contact
                Toast.makeText(this, "Error Retrieving Contact Info", Toast.LENGTH_SHORT);
            }
            else
            { //formats contact display text
            name.setText(c.getName());
            address.setText("Address:\n     " + c.getAddress());
            String phoneNumber1 = c.getPhoneNumber();
            unformattedphone = phoneNumber1;
            String area = phoneNumber1.substring(0, 3);
            String first_3 = phoneNumber1.substring(3, 6);
            String last_3 = phoneNumber1.substring(6, phoneNumber1.length());
            phoneNumber1 = "(" + area + ")" + " " + first_3 + " - " + last_3;
            phone.setText("Phone:\n    " + phoneNumber1);
            email.setText("Email:\n     " + c.getEmail());

            //initializes call button
            callButton = (FloatingActionButton) findViewById(R.id.call);
            callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    call(view);
                }
            });
            // stores contact info for edit activity
            FloatingActionButton edit = (FloatingActionButton) findViewById(R.id.edit1);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(viewContact.this, editContact.class);
                    i.putExtra("id", id);
                    startActivity(i);
                }
            });

        } }else { //if contact could not be retrieved
            Toast.makeText(this, "Error Processing Information", Toast.LENGTH_SHORT);
        }

        // inflates send text message view for user
        View v = LayoutInflater.from(this).inflate(R.layout.sndmsg, null, false);
        final PopupWindow pw = new PopupWindow(v, 900, 800, true);
        final FloatingActionButton send = (FloatingActionButton)findViewById(R.id.msg);
        EditText txt = v.findViewById(R.id.msgtxt);

        LinearLayout layout = new LinearLayout(this);
        txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String msg = charSequence.toString();
                if (msg.length() > 0) {
                    txt.setText(msg);
            }}

            @Override
            public void afterTextChanged(Editable editable) {

                }
            }
        );
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      showNoticeDialog();
                    }
                });}

       // checks call and texts user permissions
    public boolean checkPermission() {

        int CallPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);

        return CallPermissionResult == PackageManager.PERMISSION_GRANTED;

    }

// request permission if it has not been granted yet
    private void requestPermission() {

        ActivityCompat.requestPermissions(viewContact.this, new String[]
                {Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);

    }

    @Override  // checks permission request result
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {

                    boolean CallPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (CallPermission) {

                        Toast.makeText(viewContact.this,
                                "Permission accepted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(viewContact.this, "Permission denied", Toast.LENGTH_LONG).show();
                        callButton.setEnabled(false);
                    }
                    break;
                }
            } //if permission is granted for text
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    String smsNumber = String.format("smsto: %s",
                            unformattedphone);
                    smsManager.sendTextMessage(unformattedphone, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            break;
        }

    }
  //calls contact
    public void call(View view) {

        if (!TextUtils.isEmpty(unformattedphone)) {
            String dial = "tel:" + unformattedphone;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        } else {
            Toast.makeText(viewContact.this, "Please enter a valid telephone number", Toast.LENGTH_SHORT).show();
        }

    }
    // sends sms message
    protected void sendSMSMessage() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }



    @Override  //when user clicks send
    public void onDialogPositiveClick(DialogFragment dialog) {
        Dialog dialog1 = Dialog.class.cast(dialog);
        EditText txt = (EditText)dialog1.findViewById(R.id.msgtxt);
        message = txt.getText().toString();
        if(message.length()>0)
        {
            sendSMSMessage();
        }
    }

    @Override // when user cancels message
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

        public void showNoticeDialog() {
            // Create an instance of the dialog fragment and show it
            DialogFragment dialog = new SmsDialogue();
            dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
            dialog.onAttach(this);


        }
    @Override  // brings user back to main activity
    public void onBackPressed() {
        Intent i = new Intent(viewContact.this, MainActivity.class);
        startActivity(i);
    }
}






