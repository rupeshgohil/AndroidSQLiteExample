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
import static rps.androidsqliteexample.Utility.databaseconfig.ID;
import static rps.androidsqliteexample.Utility.databaseconfig.IMAGE;
import static rps.androidsqliteexample.Utility.databaseconfig.MOBILE;
import static rps.androidsqliteexample.Utility.databaseconfig.PASSWORD;
import static rps.androidsqliteexample.Utility.databaseconfig.SELECTTABLE;
import static rps.androidsqliteexample.Utility.databaseconfig.TABLENAME;
import static rps.androidsqliteexample.Utility.databaseconfig.USERNAME;
import static rps.androidsqliteexample.Utility.databaseconfig.getAllRecords;
import static rps.androidsqliteexample.Utility.databaseconfig.Count;
import static rps.androidsqliteexample.Utility.utility.ITEMPOS;

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

    public boolean isLogin(String email, String password) {
        String[] columns = {ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String slect = USERNAME + " = ?" + " AND " + PASSWORD + " = ?";
        String[] selectargument = {email,password};
        mCursor = db.query(TABLENAME,columns,slect,selectargument,null,null,null);
        int count = mCursor.getCount();
        mCursor.close();
        db.close();
        if(count == 0){
            return false;
        }
        return true;
    }

    public boolean checkUserName(String username) {
        String[] clomnsid = {ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String[] arg_user = {username};
        String select = USERNAME +" = ?";
        mCursor = db.query(TABLENAME,clomnsid,select,arg_user,null,null,null);
        int count = mCursor.getCount();
        mCursor.close();
        db.close();
        if(count == 0){
            return true;
        }
        return false;
    }
    public boolean checkUserNameLogin(String username) {
        String[] clomnsid = {ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String[] arg_user = {username};
        String select = USERNAME +" = ?";
        mCursor = db.query(TABLENAME,clomnsid,select,arg_user,null,null,null);
        int count = mCursor.getCount();
        mCursor.close();
        db.close();
        if(count == 0){
            return false;
        }
        return true;
    }

    public int DeleleRecords(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int d_response = db.delete(TABLENAME,ID + " = ?",new String[]{String.valueOf(id)});
        return d_response;
    }

    public Contact GetSingleRecords(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLENAME, new String[] { ID,
                        USERNAME, GENDER, EMAIL,MOBILE,DATE,CITY,PASSWORD,IMAGE }, ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact();
        contact.setId(Integer.parseInt(cursor.getString(0)));
        contact.setUsername(cursor.getString(1));
        contact.setGender(cursor.getString(2));
        contact.setEmail(cursor.getString(3));
        contact.setMobile(cursor.getString(4));
        contact.setDate(cursor.getString(5));
        contact.setCity(cursor.getString(6));
        contact.setPassword(cursor.getString(7));
        contact.setImage(cursor.getBlob(8));
        cursor.close();
        db.close();
        return contact;

    }

    public int UpdateRecords(Contact mContact) {
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
        int u_res = db.update(TABLENAME,values,ID + " = ?",new String[]{String.valueOf(ITEMPOS)});
        return u_res;
    }
}
