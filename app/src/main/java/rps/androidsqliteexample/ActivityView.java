package rps.androidsqliteexample;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import rps.androidsqliteexample.Adapter.LocalContactAdapter;
import rps.androidsqliteexample.Database.LocalDataBase;
import rps.androidsqliteexample.Interface.onClick;
import rps.androidsqliteexample.Modal.Contact;
import rps.androidsqliteexample.databinding.ActivityViewBinding;
import static rps.androidsqliteexample.Utility.utility.ITEMPOS;
import static rps.androidsqliteexample.Utility.databaseconfig.Count;

public class ActivityView extends BaseActivity {
    public static ActivityViewBinding mActivityViewBinding;
    LocalDataBase db;
    ArrayList<Contact> arrayList = new ArrayList<>();
    LocalContactAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityViewBinding = DataBindingUtil.setContentView(this,R.layout.activity_view);
        db = new LocalDataBase(this);
        arrayList =  db.GetAllRecords();
        Log.e("array===>","===>"+new Gson().toJson(arrayList)+"SizeArray"+arrayList.size());
        mActivityViewBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mActivityViewBinding.recyclerview.setHasFixedSize(false);
        mActivityViewBinding.recyclerview.addItemDecoration(new DividerItemDecoration(ActivityView.this,1));
        setAdapter();
    }
    public void setAdapter(){
       final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Please wait... ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
        mAdapter = new LocalContactAdapter(ActivityView.this,arrayList);
        mAdapter.setListener(new onClick() {
            @Override
            public void onItemClick(final int position) {
                    final Contact mc = arrayList.get(position);

                AlertDialog.Builder dialog = new AlertDialog.Builder(ActivityView.this);
                dialog.setTitle(mc.getUsername());
                dialog.setMessage("Are you want to Delete or Update this User..");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // Toast.makeText(getApplicationContext(),"delete",Toast.LENGTH_SHORT).show();
                        int d_response = db.DeleleRecords(mc.getId());
                        ITEMPOS = position;
                        arrayList.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                dialog.setNegativeButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // Toast.makeText(getApplicationContext(),"update",Toast.LENGTH_SHORT).show();
                       Contact mContact =  db.GetSingleRecords(mc.getId());
                        Toast.makeText(getApplicationContext(),mContact.getUsername(),Toast.LENGTH_SHORT).show();
                        Intent update = new Intent(ActivityView.this,ActivitySignup.class);
                        update.putExtra("updatemodal",mContact);
                        startActivity(update);
                    }
                });

                dialog.show();
            }
        });
        mActivityViewBinding.recyclerview.setAdapter(mAdapter);
        if(arrayList.size() > 0){
            Log.e("TotalRow",String.valueOf(Count));
            progress.dismiss();
        }
    }
}
