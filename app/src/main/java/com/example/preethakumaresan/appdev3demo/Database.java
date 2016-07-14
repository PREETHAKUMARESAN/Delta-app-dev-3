package com.example.preethakumaresan.appdev3demo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by PREETHA KUMARESAN on 28-06-2016.
 */
public class Database extends SQLiteOpenHelper {

    public static final String NameDataBase = "contacts.data";
    public static final String NameOfTable = "contacts";
    public static final String First = "Name";
    public static final String Second = "Number";
    public static final String Third = "Img";

    //constructor
    public Database(Context context) {super(context, NameDataBase, null, 1);}

    //Override methods-SQL Statements :(
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("create table contact (NAME TEXT, NUMBER TEXT,IMG BLOB)");
    }

    //Get all contacts to populate the listView
    public Cursor getAllData(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cur = database.rawQuery("select * from contacts order by "+First, null);
        return cur;
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(database);
    }

    //Edit the contact
    public boolean edit_data(String name, String number, byte[] img){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(First,name);
        values.put(Second,number);
        values.put(Third,img);

        //Convenience method for updating rows in the database.
        long LONG = database.update(NameOfTable,values, "Name = ?", new String[]{name});
        return LONG != 0;
    }

    //Add a new contact
    public boolean insertContact(String name, String number,byte[] img){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(First,name);
        values.put(Second,number);
        values.put(Third,img);
        long LONG = database.insert(NameOfTable,null,values);
        if(LONG==-1)
        {
            return false;
        }
        return true;
    }



}
