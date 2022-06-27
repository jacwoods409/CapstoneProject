package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.Callable;

public class addContact extends AppCompatActivity {
    private String id;
    private Contact c;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //sets keyboard to be hidden unless directed to show
        // initializing layout features
        EditText name = (EditText) findViewById(R.id.addName);
        EditText phone = (EditText) findViewById(R.id.addPhone);
        EditText address = (EditText) findViewById(R.id.addAddress);
        EditText email = (EditText) findViewById(R.id.addEmailAddress);

        Db db = new Db(this);
        //disables add button from being pressed if no data has been entered
        add = (Button) findViewById(R.id.addContact_add);
        add.setEnabled(false);

        TextWatcher textWatcher = new TextWatcher() {  // listens for txt to be entered
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // gets content from the edit text boxes
                String nameInput = name.getText().toString();
                String phoneInput = phone.getText().toString();
                String addInput = address.getText().toString();
                String emailInput = email.getText().toString();


                // check whether the fields are empty or not, if they are the user can not add contact
                add.setEnabled(!name.getText().toString().isEmpty() && !phone.getText().toString().isEmpty()
                        && !address.getText().toString().isEmpty() && !email.getText().toString().isEmpty());
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        phone.addTextChangedListener(textWatcher);
        email.addTextChangedListener(textWatcher);
        address.addTextChangedListener(textWatcher);
        name.addTextChangedListener(textWatcher);

        add.setOnClickListener(new View.OnClickListener() {  //the creation of a contact is to test the input before it reached the database
            @Override
            public void onClick(View view) {
                try { //starts process of adding a new contact
                    Contact c = new Contact("000",name.getText().toString(), phone.getText().toString(),address.getText().toString(),email.getText().toString());

                    db.insertContact(name.getText().toString(),phone.getText().toString(), address.getText().toString()
                            ,email.getText().toString());

                    //for testing
                    //Contact c = db.getContact(db.numberOfRows()+1);
                   // LinkedList<Contact> list = db.getAllCotacts();
                  //  for(Contact c: list)
                 //   {
                      //  System.out.println(c.getName());
                   // }

                    //moves user back to main screen
                    Intent i = new Intent(addContact.this, MainActivity.class);
                    startActivity(i); // moves back to main screen
                } catch (SQLiteException sql) {  // in case insertion fails
                    Toast.makeText(addContact.this, sql.getMessage(), Toast.LENGTH_SHORT);
                } catch (Exception ex) {  // if invalid input is entered
                    Toast.makeText(addContact.this, ex.getMessage(), Toast.LENGTH_SHORT);
                }

            }
        });


    }

    @Override   // moves back to main screen if user doesnt want to add contact
    public void onBackPressed() {
        Intent i = new Intent(addContact.this, MainActivity.class);
        startActivity(i);
    }
}


