/*
package com.emojiapp;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.test.espresso.remote.EspressoRemoteMessage;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.LinkedList;

public class database  extends SQLiteOpenHelper {
        private static final int DB_VERSION = 1;
        private static final String DB_NAME = "EmojiDB";
        private static final String EMOJIS = "Emojis";
        private static final String KEY_ID = "id";
        private static final String KEY_NAME = "name";
        private static final String KEY_Image = "emoji";
        private ByteArrayOutputStream objectbyteArrayOutputStream;
        byte[] imageInBytes;
          Context c;

        public database(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            this.c = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_TABLE = "CREATE TABLE " + "Emojis"+ "("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                    + KEY_Image + " BLOB"  + ")";
            db.execSQL(CREATE_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if exist
            db.execSQL("DROP TABLE IF EXISTS " + EMOJIS);  //for items
            onCreate(db);
        }

       // Adding new Item Details
       void insertItemDetails(String name, String image) {
           //Get the Data Repository in write mode
           SQLiteDatabase db = this.getWritableDatabase();
           ContentValues cValues = new ContentValues();
           cValues.put(KEY_NAME, name);
           cValues.put(KEY_Image, image);

           long newRowId = db.insert(EMOJIS, null, cValues);
           System.out.println(newRowId);
           db.close();
       }

    // Gets All Items in Item table
    public LinkedList<Emoji> GetItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        LinkedList<Emoji> itemList = new LinkedList<>();
        String query = "SELECT name, emoji FROM " + EMOJIS;
        Cursor cursor = db.rawQuery(query, null);
        Emoji em = null;
        while (cursor.moveToNext()) {


            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));

            @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex(KEY_Image));
            Uri uri = Uri.parse(image);
            em = new Emoji(1, name);
            em.setUri(uri);
            itemList.add(em);


        }

        return itemList;
    }

    public void clearTable() //clears item table
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ EMOJIS);
    }
    // Get Item by id
    public Emoji GetItemByID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT name, emoji FROM "+ EMOJIS;
        Emoji em =null;
        Cursor cursor = db.query(EMOJIS, new String[]{KEY_ID, KEY_NAME, KEY_Image}, KEY_NAME+ "=?",new String[]{String.valueOf(name)},null, null, null, null);
        if (cursor.moveToNext()){

           @SuppressLint("Range") String emname =cursor.getString(cursor.getColumnIndex(KEY_NAME));
            @SuppressLint("Range") String image =cursor.getString(cursor.getColumnIndex(KEY_Image));
            Uri uri = Uri.parse(image);
            em = new Emoji(1, name);
            em.setUri(uri);

        }
        return  em;
    }
    // Delete Item
    public void DeleteItem(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EMOJIS, KEY_NAME+" = ?",new String[]{String.valueOf(name)});
        db.close();

    }
    public int UpdateItemName(String name, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_NAME, name);
        int count = db.update(EMOJIS, cVals, KEY_NAME+" = ?",new String[]{String.valueOf(oldName)});
        return  count;
    }


}




*/
