package rps.androidsqliteexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mActivitySignupBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
       db = new LocalDataBase(this);
       mContext = this;
       mActivitySignupBinding.btnsubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(isValid("ActivitySignup")){
                    Contact mContact = new Contact();
               }
           }
       });
       mActivitySignupBinding.ivSelectimage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ActivitySignup.this);
               dialogBuilder.setMessage("Choose Image");
               dialogBuilder.setCancelable(false);
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
        Toast.makeText(this, "gallery", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void OpenImageCamera() {
        Toast.makeText(this, "Camera", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, SELECTIMAGE),SELECT_FILE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mActivitySignupBinding.profileImage.setImageBitmap(bm);
    }
    private void onCaptureImageResult(Intent data) {
    }
}