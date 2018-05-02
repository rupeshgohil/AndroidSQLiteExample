package rps.androidsqliteexample;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;

import rps.androidsqliteexample.databinding.ActivitySplashscreenBinding;

public class ActivitySplashscreen extends BaseActivity{
    ActivitySplashscreenBinding mSpashcreenBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSpashcreenBinding = DataBindingUtil.setContentView(this,R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(ActivitySplashscreen.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },5000);
    }
}
