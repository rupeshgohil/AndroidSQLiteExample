package rps.androidsqliteexample.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import rps.androidsqliteexample.Modal.Contact;

import static rps.androidsqliteexample.Utility.databaseconfig.CITY;
import static rps.androidsqliteexample.Utility.databaseconfig.DATABASENAME;
import static rps.androidsqliteexample.Utility.databaseconfig.DATABASEVERSION;
import static rps.androidsqliteexample.Utility.databaseconfig.DATE;
import static rps.androidsqliteexample.Utility.databaseconfig.EMAIL;
import static rps.androidsqliteexample.Utility.databaseconfig.GENDER;
import static rps.androidsqliteexample.Utility.databaseconfig.IMAGE;
import static rps.androidsqliteexample.Utility.databaseconfig.MOBILE;
import static rps.androidsqliteexample.Utility.databaseconfig.PASSWORD;
import static rps.androidsqliteexample.Utility.databaseconfig.SELECTTABLE;
import static rps.androidsqliteexample.Utility.databaseconfig.TABLENAME;
import static rps.androidsqliteexample.Utility.databaseconfig.USERNAME;
import static rps.androidsqliteexample.Utility.databaseconfig.getAllRecords;
import static rps.androidsqliteexample.Utility.databaseconfig.Count;

public class LocalDataBase extends SQLiteOpenHelper{

    Cursor mCursor;
    public static int count;
    ArrayList<Contact>  arraylist = new ArrayList<>();
    public LocalDataBase(Context activitySignup) {
        super(activitySignup,DATABASENAME,null,DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SELECTTABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +TABLENAME);
            onCreate(db);
    }

    public Long InsertData(Contact mContact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME,mContact.getUsername());
        values.put(GENDER,mContact.getGender());
        values.put(EMAIL,mContact.getEmail());
        values.put(MOBILE,mContact.getMobile());
        values.put(DATE,mContact.getDate());
        values.put(CITY,mContact.getCity());
        values.put(PASSWORD,mContact.getPassword());
        values.put(IMAGE,mContact.getImage());
        Long response = db.insert(TABLENAME,null,values);
        return response;

    }

    public ArrayList<Contact> GetAllRecords() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.isOpen();
        mCursor = db.rawQuery(getAllRecords,null);
        Count = mCursor.getCount();
        Log.e("Countrow====>",String.valueOf(count));
        try{
            if(mCursor.moveToFirst()){
                do{
                    Contact mContact = new Contact();
                    mContact.setId(Integer.parseInt(mCursor.getString(0)));
                    mContact.setUsername(mCursor.getString(1));
                    mContact.setGender(mCursor.getString(2));
                    mContact.setEmail(mCursor.getString(3));
                    mContact.setMobile(mCursor.getString(4));
                    mContact.setDate(mCursor.getString(5));
                    mContact.setCity(mCursor.getString(6));
                    mContact.setPassword(mCursor.getString(7));
                    mContact.setImage(mCursor.getBlob(8));
                    arraylist.add(mContact);
                }while (mCursor.moveToNext());
            }
            mCursor.close();
            db.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        return arraylist;
    }
}
