package rps.androidsqliteexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import rps.androidsqliteexample.Database.LocalDataBase;
import rps.androidsqliteexample.Modal.Contact;
import rps.androidsqliteexample.databinding.ActivitySignupBinding;

import static rps.androidsqliteexample.Utility.Utility.REQUEST_CAMERA;
import static rps.androidsqliteexample.Utility.Utility.SELECTIMAGE;
import static rps.androidsqliteexample.Utility.Utility.SELECT_FILE;

public class ActivitySignup extends BaseActivity {
    public static ActivitySignupBinding mActivitySignupBinding;
    ArrayList<Contact> arrayList = new ArrayList<>();
    LocalDataBase db;
    Context mContext;
    Long insertResponse;
    public static String gender;
    public static byte[] imageInByte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mActivitySignupBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
       db = new LocalDataBase(this);
       mContext = this;
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
                    mContact.setImage(imageInByte);
                   insertResponse = db.InsertData(mContact);
                   if(insertResponse >0){
                       Toast.makeText(mContext, "Insert Success", Toast.LENGTH_SHORT).show();
                       Intent i = new Intent(ActivitySignup.this,MainActivity.class);
                       startActivity(i);
                       finish();
                   }else{
                       Toast.makeText(mContext, "Fail", Toast.LENGTH_SHORT).show();
                   }
               }
           }
       });
       mActivitySignupBinding.ivSelectimage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ActivitySignup.this);
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
                       }

                   }
               });
               ivcamera.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       if(isStoragePermissionGranted(mContext)){
                           OpenImageCamera();
                       }

                   }
               });
               dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int whichButton) {
                      dialog.dismiss();
                   }
               });

               AlertDialog b = dialogBuilder.create();
               b.show();
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