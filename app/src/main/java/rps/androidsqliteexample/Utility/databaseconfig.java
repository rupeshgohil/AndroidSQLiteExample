package rps.androidsqliteexample.Utility;

public class databaseconfig {

    /*database fielsd*/
    public static final String DATABASENAME = "MyContact";
    public static final int DATABASEVERSION = 1;
    public static final String TABLENAME = "Mydata";
    public static final String ID = "id";
    public static final String USERNAME = "Username";
    public static final String GENDER = "Gender";
    public static final String EMAIL = "Email";
    public static final String MOBILE = "Mobile";
    public static final String CITY = "City";
    public static final String PASSWORD = "Password";
    public static final String IMAGE = "Image";

    public static String SELECTTABLE =  "CREATE TABLE " + TABLENAME + "("
            + ID + " INTEGER PRIMARY KEY," + USERNAME + " TEXT,"
            + GENDER + " TEXT," + EMAIL + " TEXT,"
            + MOBILE + " TEXT,"+ CITY + " TEXT," + PASSWORD + " TEXT,"+ IMAGE + " BLOB" + ")";
}
