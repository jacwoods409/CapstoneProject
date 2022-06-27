
package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


import java.util.LinkedList;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class Db extends SQLiteOpenHelper {
    private SQLiteDatabase db;  // database variables
    private static final String DATABASE_NAME = "Contacts";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String Address = "address";
    public static final String PHONE = "phone";
    Context context;
    Db(Context context)
    {super(context, DATABASE_NAME , null, 1);
        this.context = context;


    }

    @Override    // creates sql database instance
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + CONTACTS_TABLE_NAME+ "("
                + ID + " INTEGER PRIMARY KEY, " + NAME + " TEXT,"
                + PHONE + " TEXT, " + Address + " TEXT," +EMAIL + " TEXT" +");";


        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ CONTACTS_TABLE_NAME);
        onCreate(db);
    }// adds contacts to data base
    protected boolean insertContact (String name, String phone, String street, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Contact c = new Contact("12",name,phone,street,email);

        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(PHONE, phone);
        contentValues.put(Address,street);
        contentValues.put(EMAIL, email);
        db.insert(CONTACTS_TABLE_NAME,null, contentValues);
        db.close();
        return true;
    }
// returns contact using id
@SuppressLint("Range")
protected Contact getContact(Integer id)
    {
        System.out.println(id+"before get");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "Select * from contacts where id = ?", new String[]{id.toString()});
        Contact c = null;
        while(res.moveToNext()){


            System.out.println(id +" in db");
            c =new Contact(String.valueOf(id));
            c.setName(res.getString(res.getColumnIndex(NAME)));
            System.out.println(c.getName());
            c.setPhoneNumber(res.getString(res.getColumnIndex(PHONE)));
            c.setAddress(res.getString(res.getColumnIndex(Address)));
            c.setEmail(res.getString(res.getColumnIndex(EMAIL)));

        }
        return c;
    }
   protected Cursor getData(int id) {  // returns cursor from query
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id ="+id+"", null );
        return res;
    }

    protected  int numberOfRows(){ // returns number of contacts in database
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    protected boolean updateContact (int id, String name, String phone,  String street,String email) { // updates contact info
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("address",street);
        contentValues.put("email", email);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    protected Integer deleteContact (int id) { // deletes contact
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }


    @SuppressLint("Range") //retrieves all contacts from database
    protected LinkedList<Contact> getAllCotacts() {
        LinkedList<Contact> contacts = new LinkedList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        //res.moveToFirst();
        Contact c;
        while(res.moveToNext()){

          int id =res.getColumnIndex(ID);
            c =new Contact(String.valueOf(id));
            c.setName(res.getString(res.getColumnIndex(NAME)));
            c.setPhoneNumber(res.getString(res.getColumnIndex(PHONE)));
            c.setAddress(res.getString(res.getColumnIndex(Address)));
            c.setEmail(res.getString(res.getColumnIndex(EMAIL)));
            contacts.add(c);

        }
        return contacts;
    }

   protected boolean clearDb() // clears database helpful when testing
   {
       SQLiteDatabase db = this.getReadableDatabase();
       db.execSQL("delete from "+ CONTACTS_TABLE_NAME);
       return true;
   }
}

