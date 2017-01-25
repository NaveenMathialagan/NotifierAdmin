package com.example.naveenkumar.notifieradmin;

/**
 * Created by VSRK on 12/21/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.sax.StartElementListener;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.naveenkumar.notifieradmin.Notify;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
Context c;
    // Database Name
    private static final String DATABASE_NAME = "CLUSTER";

    // Contacts table name
    private static final String TABLE_NOTIFICATIONS = "CLUSTER";

    // Contacts Table Columns names
    private static final String KEY_ID="app";
    private static final String KEY_PACKAGE = "package";
    private static final String KEY_TITLE = "title";
    private static final String KEY_TEXT = "text";
    private static final String KEY_HOUR= "hour";
    private static final String KEY_MINIT = "minit";
    private static final String KEY_SECOND = "second";
    private static final String KEY_DATE = "date";
    private static final String KEY_MONTH = "month";
    private static final String KEY_YEAR = "year";
    private static final String KEY_NOTI_TYPE = "notitype";



    public DatabaseHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NOTIFICATIONS + "("
                + KEY_PACKAGE + " TEXT,"
                + KEY_TITLE + " TEXT,"
                + KEY_TEXT + " TEXT, "
                + KEY_ID + " TEXT,"
                + KEY_HOUR + " TEXT,"
                + KEY_MINIT + " TEXT,"
                + KEY_SECOND + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_MONTH + " TEXT,"
                + KEY_YEAR +" TEXT,"
                + KEY_NOTI_TYPE +" TEXT"+")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void addNotify(Notify notify ) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PACKAGE, notify.getPackage());
         Log.v("PACK_TEST",notify.getPackage());// Contact Name
        values.put(KEY_TITLE, notify.getTitle()); // Contact Phone
        values.put(KEY_TEXT,notify.getText());
        values.put(KEY_ID,notify.getApp());

        values.put(KEY_HOUR,notify.getHour());
        values.put(KEY_MINIT,notify.getMinit());
        values.put(KEY_SECOND,notify.getSecond());
        values.put(KEY_DATE,notify.getDate());
        values.put(KEY_MONTH,notify.getMonth());
        values.put(KEY_YEAR,notify.getYear());
        values.put(KEY_NOTI_TYPE,notify.getNotiType());
        // Inserting Row
        db.insert(TABLE_NOTIFICATIONS, null, values);
        db.close();

    }





    // Getting All Contacts
    public List<Notify> getAllNotify() {
        List<Notify> contactList = new ArrayList<Notify>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTIFICATIONS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToLast()) {
            do {
                Notify notify=new Notify();
               //  Log.d("Noti",cursor.getString(0)+"\t"+cursor.getString(1));

              //  notify.setID((cursor.getString(0)));
                notify.setPackage(cursor.getString(0));
                notify.setTitle(cursor.getString(1));
                notify.setText(cursor.getString(2));
                notify.setApp(cursor.getString(3));

                notify.setHour(cursor.getString(4));
                notify.setMinit(cursor.getString(5));
                notify.setSecond(cursor.getString(6));
                notify.setDate(cursor.getString(7));
                notify.setMonth(cursor.getString(8));
                notify.setYear(cursor.getString(9));
                notify.setNotiType(cursor.getString(10));
                // Adding contact to list
                contactList.add(notify);
            } while (cursor.moveToPrevious());
        }

        // return contact list
        return contactList;
    }

    public void deleteNotify(String h, String m, String s, String  d,String txt){

     //Log.d("Noti",app) ;
        String[] tim={h,m,s,d,txt};
        int count=0;
        SQLiteDatabase db=this.getWritableDatabase();
        try {
            count = db.delete(TABLE_NOTIFICATIONS,
                      KEY_HOUR + " = ? AND "
                    + KEY_MINIT + " = ? AND "
                    + KEY_SECOND + " = ? AND "
                    + KEY_DATE + " = ? AND "
                              + KEY_TEXT + " = ? ", tim);

            Log.d("Noti","vcount value"+count);

        }
        catch (Exception e){
            e.printStackTrace();
            Log.d("Noti","Exception"+e);
        }
        if(count!=0){
          //  Toast.makeText(c,"Row  deleted count"+count,Toast.LENGTH_LONG).show();
        }else{
            //Toast.makeText(c," No Row was deleted",Toast.LENGTH_LONG).show();

        }
    }

public void deleteitem(int pos)
{

}


}