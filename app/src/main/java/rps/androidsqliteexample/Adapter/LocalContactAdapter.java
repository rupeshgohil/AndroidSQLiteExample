package rps.androidsqliteexample.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import rps.androidsqliteexample.Interface.onClick;
import rps.androidsqliteexample.Modal.Contact;
import rps.androidsqliteexample.R;

public class LocalContactAdapter extends RecyclerView.Adapter<LocalContactAdapter.MyAdapter>{
    public Context mContext;
    public Bitmap bitmap;
    ArrayList<Contact> myarray = new ArrayList<>();
    public onClick mListener;
    public LocalContactAdapter(Context activityView, ArrayList<Contact> arrayList) {
        this.mContext = activityView;
        this.myarray = arrayList;
    }

    public void setListener(onClick onClick) {
        this.mListener = onClick;
    }

    public static class MyAdapter extends RecyclerView.ViewHolder{
        TextView tvusername,tvemail,tvcity,tcdate;
        ImageView ivprofile;
        ConstraintLayout rootlayout;
        public MyAdapter(View itemView) {
            super(itemView);
            tvusername = (TextView)itemView.findViewById(R.id.txt_username);
            tvemail = (TextView)itemView.findViewById(R.id.txt_email);
            tvcity = (TextView)itemView.findViewById(R.id.txt_city);
            tcdate = (TextView)itemView.findViewById(R.id.txt_date);
            ivprofile = (ImageView)itemView.findViewById(R.id.profile_image);
            rootlayout = (ConstraintLayout)itemView.findViewById(R.id.viewitemrow_root);
        }
    }

    @NonNull
    @Override
    public LocalContactAdapter.MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contactviewitemrow,parent,false);
        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalContactAdapter.MyAdapter holder, final int position) {
                Contact mContact = myarray.get(position);
        holder.tvusername.setText(mContact.getUsername());
        holder.tvemail.setText(mContact.getEmail());
        holder.tvcity.setText(mContact.getCity());
        holder.tcdate.setText(mContact.getDate());
        bitmap = BitmapFactory.decodeByteArray(mContact.getImage(), 0, (mContact.getImage().length));
        holder.ivprofile.setImageBitmap(bitmap);
        holder.rootlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myarray.size();
    }
}

