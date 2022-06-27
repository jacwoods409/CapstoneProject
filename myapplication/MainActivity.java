package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import android.widget.ImageView;
import android.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        LinkedList<Contact> myListData = new LinkedList();

        Db db = new Db(this);
        // for testing
       // db.clearDb();
       // db.insertContact("Bob", "7876665454", "bob10@gmail.com", "34 Bug St");
       // db.insertContact("Paul", "8765554322", "Paul85@yahoo.com", "65 Had St");
        myListData = db.getAllCotacts();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView); // loads recycler vew for contacts
        MyListAdapter adapter = new MyListAdapter(myListData, this.getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.addContact);
        View view = this.getCurrentFocus();

        // if nothing is currently in focus
        // focus then this will protect
        // the app from crash
        if (view != null) {
            // adapter listener
            InputMethodManager manager
                    = (InputMethodManager)
                    getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
            recyclerView.setVisibility(View.VISIBLE);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddContact(view);
            }  // detects when search criteria has been entered
        });
        SearchView sv = (SearchView)findViewById(R.id.searchView);
        int searchCloseIconButtonId = sv.getContext().getResources()
                .getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) this.findViewById(searchCloseIconButtonId);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sv.clearFocus();
                sv.setQuery("",true);
                adapter.displayContacts();
                closeButton.setVisibility(View.INVISIBLE);
            }
        });
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String text) {
                    sv.clearFocus();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String text) {
                    if(text.length()==0)
                    {
                        sv.clearFocus();
                        adapter.displayContacts();
                        closeButton.setVisibility(View.INVISIBLE);
                    }
                    adapter.getFilter().filter(text);
                    closeButton.setVisibility(View.VISIBLE);
                    return true;
                }
            });;



    }

    private void onAddContact(View v)  // moves to the add user window
    {
        Intent i = new Intent(MainActivity.this, addContact.class);
        startActivity(i);
    }
    @Override
    public void onBackPressed()
    {  System.exit(0);

    }
}