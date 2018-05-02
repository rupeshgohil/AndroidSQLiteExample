package rps.androidsqliteexample.Database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import rps.androidsqliteexample.ActivitySignup;

public class LocalDataBase extends SQLiteOpenHelper{

    public static final String DATABASENAME = "MyContact";
    public static final int DATABASEVERSION = 1;
    public LocalDataBase(ActivitySignup activitySignup) {
        super(activitySignup,DATABASENAME,null,DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
