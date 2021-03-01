package com.example.covidtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    List<StatewiseItem> items;
    Context context;
    public MyAdapter(List<StatewiseItem> items, Context context){
        this.items=items;
        this.context=context;

    }
    @NonNull
    @Override
    public MyAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.mylayout,null);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        StatewiseItem item=items.get(position);
        holder.state.setText(item.getState());
        holder.confirmed.setText(item.getConfirmed());
        holder.active.setText(item.getActive());
        holder.decease.setText(item.getDeaths());
        holder.recover.setText(item.getRecovered());
    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView  confirmed, active, recover, decease,state;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            state=itemView.findViewById(R.id.stateTv);
            confirmed = itemView.findViewById(R.id.confirmedTv);
            active = itemView.findViewById(R.id.activeTv);
            recover = itemView.findViewById(R.id.recoveredTv);
            decease = itemView.findViewById(R.id.deceasedTv);

        }
    }
}
