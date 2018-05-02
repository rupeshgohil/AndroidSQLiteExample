package rps.androidsqliteexample;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.regex.Pattern;

import static rps.androidsqliteexample.ActivitySignup.mActivitySignupBinding;
import static rps.androidsqliteexample.MainActivity.mainBinding;
import static rps.androidsqliteexample.Utility.utility.EMAIL_PATTERN;

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
            }else{
                return true;
            }
        }
        else {
            return false;
        }

    }

}
