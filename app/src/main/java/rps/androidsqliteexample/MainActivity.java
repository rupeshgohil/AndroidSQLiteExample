package rps.androidsqliteexample;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import rps.androidsqliteexample.Database.LocalDataBase;
import rps.androidsqliteexample.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
    public static ActivityMainBinding mainBinding;
    LocalDataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        db = new LocalDataBase(this);
        mainBinding.txtSugnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ActivitySignup.class);
                startActivity(i);
            }
        });
        mainBinding.btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent i = new Intent(MainActivity.this,ActivityView.class);
                //startActivity(i);
               if(isValid("MainActivity")){
                   if(db.checkUserNameLogin(mainBinding.edmainusername.getText().toString().trim())){
                       if(db.isLogin(mainBinding.edmainusername.getText().toString().trim(),
                               mainBinding.edmainPassword.getText().toString().trim())){
                           Intent i = new Intent(MainActivity.this,ActivityView.class);
                           startActivity(i);
                       }else{
                           Toast.makeText(MainActivity.this, R.string.loginfalis, Toast.LENGTH_SHORT).show();
                       }
                   }else{
                       Toast.makeText(MainActivity.this, R.string.vluser, Toast.LENGTH_SHORT).show();
                   }

                }
            }
        });
    }
}
