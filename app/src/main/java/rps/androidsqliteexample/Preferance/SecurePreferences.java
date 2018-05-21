package rps.androidsqliteexample.Preferance;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SecurePreferences {

    public static String MOBILE_NUMBER = "MOBILE_NUMBER";

    public static String MOBILE_NUMBER_CHANGE = "MOBILE_NUMBER_CHANGE";

    public static String MEMBER_ID = "MEMBER_ID";

    public static String first_name = "first_name";

    public static String last_name = "last_name";

    public static String email_id = "email_id";

    public static String mobile_no = "mobile_no";

    public static String address = "address";

    public static String is_notification_on = "is_notification_on";

    public static String is_first_open = "is_first_open";


    static SharedPreferences sh_Pref;
    public static String Preference_Name = "AndroidTest";

    public static String FCM_TOKEN = "FCM_TOKEN";

    public static String IS_LOGIN = "IS_LOGIN";

    public static String getPreference(String key, String Default,
                                         Context activity) {
        sh_Pref = activity.getSharedPreferences(Preference_Name,
                Context.MODE_PRIVATE);
        return sh_Pref.getString(key, Default);
    }

//    public static HashMap<String, String> getPreferencefornotify(String key, String Default,
//                                                                 Context activity) {
//        sh_Pref = activity.getSharedPreferences(Preference_Name,
//                Context.MODE_PRIVATE);
//        return sh_Pref.getString(key, Default);
//    }
    public static boolean getPreferenceBoolean(String key, boolean Default,
                                               Context activity) {
        sh_Pref = activity.getSharedPreferences(Preference_Name,
                Context.MODE_PRIVATE);
        return sh_Pref.getBoolean(key, Default);
    }

    public static int getPreferenceInt(String key, int Default, Context activity) {
        sh_Pref = activity.getSharedPreferences(Preference_Name,
                Context.MODE_PRIVATE);
        return sh_Pref.getInt(key, Default);
    }

    public static boolean setPreference(String key, String value, Context activity) {
        if (value != null) {
            if (getPreference(key, "", activity).equals(value)) {
                return false;
            } else {
                sh_Pref = activity.getSharedPreferences(Preference_Name, Context.MODE_PRIVATE);
                Editor editor = sh_Pref.edit();
                editor.putString(key, value);
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public static void setPreferenceBoolean(String key, boolean value,
                                            Context activity) {
        sh_Pref = activity.getSharedPreferences(Preference_Name,
                Context.MODE_PRIVATE);
        Editor editor = sh_Pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void setPreferenceInt(String key, int value, Context activity) {
        sh_Pref = activity.getSharedPreferences(Preference_Name,
                Context.MODE_PRIVATE);
        Editor editor = sh_Pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void deletePref(Context activity) {
        sh_Pref = activity.getSharedPreferences(Preference_Name,
                Context.MODE_PRIVATE);
        sh_Pref.edit().clear().commit();
    }

}
