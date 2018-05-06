package rps.androidsqliteexample;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.regex.Pattern;

import static rps.androidsqliteexample.ActivitySignup.SelectDate;
import static rps.androidsqliteexample.ActivitySignup.gender;
import static rps.androidsqliteexample.ActivitySignup.imageInByte;
import static rps.androidsqliteexample.ActivitySignup.mActivitySignupBinding;
import static rps.androidsqliteexample.MainActivity.mainBinding;
import static rps.androidsqliteexample.Utility.utility.EMAIL_PATTERN;
import static rps.androidsqliteexample.Utility.utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;

public class BaseActivity extends AppCompatActivity {

    public boolean isValid(String str){
        if(str.equals("MainActivity")){
            if(mainBinding.edmainusername.length() == 0){
                Toast.makeText(this, R.string.entuser, Toast.LENGTH_SHORT).show();
                return false;
            }else if(mainBinding.edmainPassword.length() == 0 ){
                Toast.makeText(this, R.string.entpsw, Toast.LENGTH_SHORT).show();
                return false;
            }else if(mainBinding.edmainPassword.length() < 6){
                Toast.makeText(this, R.string.lpsw, Toast.LENGTH_SHORT).show();
                return false;
            }else {
                return true;
            }

        }else if(str.equals("ActivitySignup")){
            if(TextUtils.isEmpty(mActivitySignupBinding.edUserName.getText())){
                Toast.makeText(this, R.string.entuser, Toast.LENGTH_SHORT).show();
                return false;
            }else if(TextUtils.isEmpty(mActivitySignupBinding.edEmail.getText())){
                Toast.makeText(this, R.string.entemail, Toast.LENGTH_SHORT).show();
                return false;
            }else if(!Pattern.compile(EMAIL_PATTERN).matcher(mActivitySignupBinding.edEmail.getText().toString().trim()).matches()){
                Toast.makeText(this, R.string.entvemail, Toast.LENGTH_SHORT).show();
                return false;
            }else if(TextUtils.isEmpty(mActivitySignupBinding.edMobile.getText())){
                Toast.makeText(this, R.string.entvmobile, Toast.LENGTH_SHORT).show();
                return false;
            }else if(mActivitySignupBinding.edMobile.length() < 10){
                Toast.makeText(this, R.string.entmobile, Toast.LENGTH_SHORT).show();
                return false;
            }else if(TextUtils.isEmpty(mActivitySignupBinding.edCity.getText())){
                Toast.makeText(this, R.string.entcity, Toast.LENGTH_SHORT).show();
                return false;
            }else if(TextUtils.isEmpty(mActivitySignupBinding.edPassword.getText())){
                Toast.makeText(this, R.string.entpsw, Toast.LENGTH_SHORT).show();
                return false;
            }else if(mActivitySignupBinding.edPassword.length() < 6){
                Toast.makeText(this, R.string.lpsw, Toast.LENGTH_SHORT).show();
                return false;
            }else if(TextUtils.isEmpty(mActivitySignupBinding.edCPassword.getText())){
                Toast.makeText(this, R.string.entcpsw, Toast.LENGTH_SHORT).show();
                return false;
            }else if(!mActivitySignupBinding.edPassword.getText().toString().trim().equals(mActivitySignupBinding.edCPassword.getText().toString().trim())){
                Toast.makeText(this, R.string.cvpsw, Toast.LENGTH_SHORT).show();
                return false;
            }else if(imageInByte == null){
                Toast.makeText(this, R.string.vimg, Toast.LENGTH_SHORT).show();
                return false;
            }else if(TextUtils.isEmpty(gender)){
                Toast.makeText(this, R.string.vgender, Toast.LENGTH_SHORT).show();
                return false;
            }else if(TextUtils.isEmpty(SelectDate)){
                Toast.makeText(this, R.string.vdate, Toast.LENGTH_SHORT).show();
                    return false;
            }else{
                return true;
            }
        }
        else {
            return false;
        }

    }
    public  static boolean isStoragePermissionGranted(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

}
