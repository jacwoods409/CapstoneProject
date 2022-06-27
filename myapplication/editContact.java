package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

public class editContact extends AppCompatActivity {
    private EditText email;
    Contact c;
    String id;
    Button save;
    EditText address;
    EditText phone;
    EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_edit_contact);
       //initializing variables
        name = (EditText)findViewById(R.id.editName);
         phone = (EditText)findViewById(R.id.editPhone);
         address = (EditText)findViewById(R.id.editAddress);
        email = (EditText)findViewById(R.id.editEmailAddress);

        // retrieves contact info from view contact activity
        id = null;
        Intent i =this.getIntent();
        Bundle b = i.getExtras();
        if(b!=null)
        {
            id =(String) b.get("id");
            // System.out.println(id);
            Contact c=null;
            Db db = new Db(this);
            c = db.getContact(Integer.parseInt(id));
            if(c == null) //if contact is unable to be retrieved
            {
                Toast.makeText(this,"Error Retrieving Contact Info",Toast.LENGTH_SHORT);
            }

            // save is disabled until text has been altered or is not null
            save = (Button)findViewById(R.id.save);
            save.setEnabled(false);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(editContact.this, viewContact.class);
                    db.updateContact(Integer.parseInt(id),name.getText().toString(),phone.getText().toString(),
                            address.getText().toString(),email.getText().toString()
                    );
                    i.putExtra("id", id);
                    startActivity(i);
                }
            });

            // retrieves text box strings
           name.setText(c.getName());
            phone.setText(c.getPhoneNumber());
            address.setText(c.getAddress());
            email.setText(c.getEmail());
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // get the content of both the edit text
                    String nameInput = name.getText().toString();
                    String phoneInput =phone.getText().toString();
                    String addInput =address.getText().toString();
                    String emailInput =email.getText().toString();


                    // check whether both the fields are empty or not
                    save.setEnabled(!name.getText().toString().isEmpty() && !phone.getText().toString().isEmpty()
                            &&!address.getText().toString().isEmpty() && !email.getText().toString().isEmpty());
                }


                @Override
                public void afterTextChanged(Editable s) {

                }
            };
            phone.addTextChangedListener(textWatcher);
            email.addTextChangedListener(textWatcher);
            address.addTextChangedListener(textWatcher);
            name.addTextChangedListener(textWatcher);
            //save is enabled as long as all entries are not void
            save.setEnabled(!name.getText().toString().isEmpty() && !phone.getText().toString().isEmpty()
                    &&!address.getText().toString().isEmpty() && !email.getText().toString().isEmpty());




    }}
// brings user back to view contact screen without any changes being made
    @Override
    public void onBackPressed() {
        Intent i = new Intent(editContact.this, viewContact.class);
        i.putExtra("id", id);
        startActivity(i);
    }
}





