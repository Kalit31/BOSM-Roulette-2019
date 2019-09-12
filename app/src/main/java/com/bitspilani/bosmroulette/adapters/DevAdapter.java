package com.bitspilani.bosmroulette.adapters;

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

import java.util.ArrayList;

public class DevAdapter extends RecyclerView.Adapter<DevAdapter.ViewHolder> {

    ArrayList<DevDesc> devDesc;
    public DevAdapter(ArrayList<DevDesc> devDesc){
        this.devDesc=devDesc;
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
        holder.imageView.setImageResource(devDesc.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,desc;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.blaName);
            desc=itemView.findViewById(R.id.bladesc);
            imageView=itemView.findViewById(R.id.blapic);

        }
    }
}
