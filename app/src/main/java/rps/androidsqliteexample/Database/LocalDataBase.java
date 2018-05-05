package rps.androidsqliteexample.Database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import rps.androidsqliteexample.ActivitySignup;
import rps.androidsqliteexample.Modal.Contact;

import static rps.androidsqliteexample.Utility.databaseconfig.CITY;
import static rps.androidsqliteexample.Utility.databaseconfig.DATABASENAME;
import static rps.androidsqliteexample.Utility.databaseconfig.DATABASEVERSION;
import static rps.androidsqliteexample.Utility.databaseconfig.EMAIL;
import static rps.androidsqliteexample.Utility.databaseconfig.GENDER;
import static rps.androidsqliteexample.Utility.databaseconfig.IMAGE;
import static rps.androidsqliteexample.Utility.databaseconfig.MOBILE;
import static rps.androidsqliteexample.Utility.databaseconfig.PASSWORD;
import static rps.androidsqliteexample.Utility.databaseconfig.SELECTTABLE;
import static rps.androidsqliteexample.Utility.databaseconfig.TABLENAME;
import static rps.androidsqliteexample.Utility.databaseconfig.USERNAME;

public class LocalDataBase extends SQLiteOpenHelper{



    public LocalDataBase(ActivitySignup activitySignup) {
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
        values.put(CITY,mContact.getCity());
        values.put(PASSWORD,mContact.getPassword());
        values.put(IMAGE,mContact.getImage());
        Long response = db.insert(TABLENAME,null,values);
        return response;

    }
}
