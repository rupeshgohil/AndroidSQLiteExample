package rps.androidsqliteexample;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import rps.androidsqliteexample.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
    public static ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

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
                if(isValid("MainActivity")){
                    Log.e("call","call");
                }
            }
        });
    }
}
