package rps.androidsqliteexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import rps.androidsqliteexample.Database.LocalDataBase;
import rps.androidsqliteexample.Modal.Contact;
import rps.androidsqliteexample.databinding.ActivitySignupBinding;

import static rps.androidsqliteexample.Utility.utility.REQUEST_CAMERA;
import static rps.androidsqliteexample.Utility.utility.SELECTIMAGE;
import static rps.androidsqliteexample.Utility.utility.SELECT_FILE;

public class ActivitySignup extends BaseActivity {
    public static ActivitySignupBinding mActivitySignupBinding;
    ArrayList<Contact> arrayList = new ArrayList<>();
    LocalDataBase db;
    Context mContext;
    Long insertResponse;
    public static String gender;
    public static byte[] imageInByte;
    public int mYear,mDay,mMonth;
    public static String SelectDate;
    AlertDialog.Builder dialogBuilder;
    AlertDialog mDialog;
    Intent mIntent;
    Contact mContact;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mActivitySignupBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
       db = new LocalDataBase(this);
       mContext = this;
       mIntent = getIntent();
       mContact = (Contact) mIntent.getSerializableExtra("updatemodal");
       if(mContact != null){
           Log.e("CallIntent","CallIntent");
           mActivitySignupBinding.edUserName.setText(mContact.getUsername());
//           Log.e("getUsername======>",mContact.getUsername());
           mActivitySignupBinding.edEmail.setText(mContact.getEmail());
//           Log.e("getEmail======>",mContact.getEmail());
           mActivitySignupBinding.edCity.setText(mContact.getCity());
//           Log.e("getCity======>",mContact.getCity());
           mActivitySignupBinding.edMobile.setText(mContact.getMobile());
//           Log.e("getMobile======>",mContact.getMobile());
           mActivitySignupBinding.edDate.setText(mContact.getDate());
//           Log.e("getDate======>",mContact.getDate());
           mActivitySignupBinding.edPassword.setText(mContact.getPassword());
//           Log.e("getPassword======>",mContact.getPassword());
           mActivitySignupBinding.edCPassword.setText(mContact.getPassword());
//           Log.e("getPassword======>",mContact.getPassword());
//           Log.e("getGender======>",mContact.getGender());
           bitmap = BitmapFactory.decodeByteArray(mContact.getImage(), 0, (mContact.getImage().length));
           mActivitySignupBinding.profileImage.setImageBitmap(bitmap);
           if(mContact.getGender().equals("Male")){
               RadioButton mbtn = (RadioButton)findViewById(R.id.rbMale);
               mbtn.setChecked(true);
           }else{
               RadioButton mbtn = (RadioButton)findViewById(R.id.rbFemale);
               mbtn.setChecked(true);
           }
           mActivitySignupBinding.btnsubmit.setVisibility(View.GONE);
           mActivitySignupBinding.btUpdate.setVisibility(View.VISIBLE);
       }
        mActivitySignupBinding.btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int u_res = db.UpdateRecords(mContact);
                if(u_res == 1){
                    Toast.makeText(ActivitySignup.this, "UpdateSucess", Toast.LENGTH_SHORT).show();
                }
            }
        });
       mActivitySignupBinding.radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               RadioButton rb = (RadioButton) group.findViewById(checkedId);
               if (null != rb) {
                 gender = rb.getText().toString();
                  // Toast.makeText(mContext, gender, Toast.LENGTH_SHORT).show();
               }
           }
       });
       mActivitySignupBinding.btnsubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(isValid("ActivitySignup")){
                    Contact mContact = new Contact();
                    mContact.setUsername(mActivitySignupBinding.edUserName.getText().toString().trim());
                    mContact.setEmail(mActivitySignupBinding.edEmail.getText().toString().trim());
                    mContact.setMobile(mActivitySignupBinding.edMobile.getText().toString().trim());
                    mContact.setCity(mActivitySignupBinding.edCity.getText().toString().trim());
                    mContact.setPassword(mActivitySignupBinding.edPassword.getText().toString().trim());
                    mContact.setGender(gender);
                    mContact.setDate(mActivitySignupBinding.edDate.getText().toString().trim());
                    mContact.setImage(imageInByte);
                   insertResponse = db.InsertData(mContact);
                   if(insertResponse == -1){
                       Toast.makeText(mContext, "Fail", Toast.LENGTH_SHORT).show();
                   }else{
                       Log.e("insert","InsertSucess");
                       Log.e("insertRecords",String.valueOf(insertResponse));
                       Toast.makeText(mContext, "Insert Success", Toast.LENGTH_SHORT).show();
                       Intent i = new Intent(ActivitySignup.this,MainActivity.class);
                       startActivity(i);
                       finish();

                   }
               }
           }
       });
       mActivitySignupBinding.ivSelectimage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialogBuilder = new AlertDialog.Builder(ActivitySignup.this);
               dialogBuilder.setMessage("Choose Image");
               dialogBuilder.setCancelable(true);
               LayoutInflater inflater = ActivitySignup.this.getLayoutInflater();
               final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
               dialogBuilder.setView(dialogView);
               ImageView ivgallery = (ImageView) dialogView.findViewById(R.id.iv_selectimagegallery);
               ImageView ivcamera = (ImageView) dialogView.findViewById(R.id.iv_selectimagecamra);
               ivgallery.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       if(isStoragePermissionGranted(mContext)){
                           OpenImageGallery();
                           mDialog.dismiss();
                       }

                   }
               });
               ivcamera.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       if(isStoragePermissionGranted(mContext)){
                           OpenImageCamera();
                           mDialog.dismiss();
                       }

                   }
               });
               dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int whichButton) {
                      dialog.dismiss();
                   }
               });

               mDialog = dialogBuilder.create();
               mDialog.show();
           }
       });
       mActivitySignupBinding.ivDate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final Calendar c = Calendar.getInstance();
               mYear = c.get(Calendar.YEAR);
               mMonth = c.get(Calendar.MONTH);
               mDay = c.get(Calendar.DAY_OF_MONTH);
               DatePickerDialog datePickerDialog = new DatePickerDialog(ActivitySignup.this,
                       new DatePickerDialog.OnDateSetListener() {

                           @Override
                           public void onDateSet(DatePicker view, int year,
                                                 int monthOfYear, int dayOfMonth) {
                               Calendar cal = Calendar.getInstance();
                               cal.setTimeInMillis(0);
                               cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                               Date chosenDate = cal.getTime();
                               DateFormat df_medium_us = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
                               SelectDate = df_medium_us.format(chosenDate);
                              // Log.e("Date1",df_medium_us_str);
                               //SelectDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                               Log.e("Date2",SelectDate);
                               mActivitySignupBinding.edDate.setText(SelectDate);
                           }
                       }, mYear, mMonth, mDay);
               datePickerDialog.show();
           }
       });
    }
    private void OpenImageGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, SELECTIMAGE),REQUEST_CAMERA);
    }

    private void OpenImageCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, SELECT_FILE);
     }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE){
                onCaptureImageResult(data);
                Log.e("Camera","camera");

            }else{
                onSelectFromGalleryResult(data);
                Log.e("Gallery","Gallery");
            }
           //else if (requestCode == REQUEST_CAMERA)

        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                imageInByte = stream.toByteArray();
                Log.e("outputbefore", imageInByte.toString());
                mActivitySignupBinding.profileImage.setImageBitmap(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private void onCaptureImageResult(Intent data) {
        Bundle extras = data.getExtras();

        if (extras != null) {
            Bitmap yourImage = extras.getParcelable("data");
            // convert bitmap to byte
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imageInByte= stream.toByteArray();
            mActivitySignupBinding.profileImage.setImageBitmap(yourImage);
            Log.e("output before", imageInByte.toString());
        }

    }
}