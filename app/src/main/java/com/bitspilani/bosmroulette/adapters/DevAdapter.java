package com.bitspilani.bosmroulette.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitspilani.bosmroulette.R;
import com.bitspilani.bosmroulette.models.DevDesc;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DevAdapter extends RecyclerView.Adapter<DevAdapter.ViewHolder> {

    ArrayList<DevDesc> devDesc;
    Context context;
    public DevAdapter(Context context, ArrayList<DevDesc> devDesc){
        this.devDesc=devDesc;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.developer_item,parent,false);
        DevAdapter.ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(devDesc.get(position).getName());
        holder.desc.setText(devDesc.get(position).getDesc());
        String path=devDesc.get(position).getId();

        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        StorageReference storage=firebaseStorage.getReference().child("Pictures");
        StorageReference childreference=storage.child(path);


        childreference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("TAG","Heyy");
                        Picasso.with(context).load(uri.toString()).into(holder.imageView);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,desc;
        CircularImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.blaName);
            desc=itemView.findViewById(R.id.bladesc);
            imageView=itemView.findViewById(R.id.blapic);

        }
    }
}
