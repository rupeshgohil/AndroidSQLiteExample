package rps.androidsqliteexample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import rps.androidsqliteexample.Database.LocalDataBase;
import rps.androidsqliteexample.Modal.Contact;
import rps.androidsqliteexample.databinding.ActivitySignupBinding;

public class ActivitySignup extends BaseActivity {
    public static ActivitySignupBinding mActivitySignupBinding;
    ArrayList<Contact> arrayList = new ArrayList<>();
    LocalDataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mActivitySignupBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
       db = new LocalDataBase(this);
       mActivitySignupBinding.btnsubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(isValid("ActivitySignup")){

               }
           }
       });
    }
}