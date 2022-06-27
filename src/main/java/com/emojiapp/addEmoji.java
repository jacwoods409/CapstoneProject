package com.emojiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.LinkedList;

public class addEmoji extends AppCompatActivity implements Serializable {
    LinkedList <Emoji> em = new LinkedList();
    Emoji emo;
    FloatingActionButton select;
    Button addEm;
    ImageView preview;
    Uri selectedImageUri;
    EditText name;
    int SELECT_PICTURE = 200;
    String file;
    //database db = new database(this);
    private static final int PICK_IMAGE_REQUEST = 9544;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emoji2);
        // register the UI widgets with their appropriate IDs
        select = findViewById(R.id.floatingActionButton);
        preview = findViewById(R.id.preview);
        addEm = findViewById(R.id.button2);

        name = findViewById(R.id.EnterEmojiName);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
                if(s.length()>0&s.length()>25)
                {
                    name.setText(s);
                }
                else
                {
                    Toast.makeText(addEmoji.this,"Invalid name",Toast.LENGTH_SHORT);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        addEm.setEnabled(false);
        addEm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(addEmoji.this, MainActivity.class);
                System.out.println(name.getText().toString());
                //i.putExtra("Emoji",file);
                i.setData(selectedImageUri);
                i.putExtra("EmojiName",name.getText().toString());

                startActivity(i);
            }
        });
        // handle the Choose Image button to trigger
        // the image chooser function
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();

}
        });
    }

    // this function is triggered when
    // the Select Image Button is clicked
    public void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Emoji"), SELECT_PICTURE);
        selectedImageUri = i.getData();



    }
    public static String getPath(Context context, Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        if(result == null) {
            result = "Not found";
        }
        return result;
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    preview.setImageURI(selectedImageUri);
                   file =getPath(this,selectedImageUri);

                    addEm.setEnabled(true);

                }
            }
        }
    }


    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}