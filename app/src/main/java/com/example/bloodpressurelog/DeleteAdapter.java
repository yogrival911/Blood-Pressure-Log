package com.example.bloodpressurelog;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DeleteAdapter extends RecyclerView.Adapter<DeleteAdapter.MyViewHolder> {
    List<Record> recordList;
    LayoutInflater inflater;
    int selectedPosition;

    public DeleteAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public List<Record> getRecordList() {
        return recordList;
    }

    public  Record getRecordAt(int poistion){
        return recordList.get(poistion);
    }

    public void setRecordList(List<Record> allRecordPass){
        recordList =allRecordPass;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);

        View rowView = inflater.inflate(R.layout.row_layout, parent, false);
        return new DeleteAdapter.MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        int sys = Integer.parseInt(recordList.get(position).getSys());
        int dia = Integer.parseInt(recordList.get(position).getDia());
        if(sys<120 && dia<80){
            holder.heart.setImageResource(R.drawable.heart_green);
            holder.textViewMark.setText("Normal");
        }
        if((sys>=120 && sys<=129) && dia<80){
            holder.heart.setImageResource(R.drawable.heart_yellow);
            holder.textViewMark.setText("Elevated");
        }
        if((sys>130 && sys<139) || (dia>80 && dia<=89)){
            holder.heart.setImageResource(R.drawable.heart_orange_light);
            holder.textViewMark.setText("High Stage-1");
        }
        if((sys>=140 && sys<180) || (dia>=90 && dia<=120)){
            holder.heart.setImageResource(R.drawable.heart_orange);
            holder.textViewMark.setText("High Stage-2");
        }
        if(sys>=180 || dia>120){
            holder.heart.setImageResource(R.drawable.heart_red);
            holder.textViewMark.setText("Emergency");
        }

        holder.textViewDate.setText(recordList.get(position).getDate());
        holder.textViewTime.setText(recordList.get(position).getTime());
        holder.textViewBp.setText(recordList.get(position).getSys() + " / "+ recordList.get(position).getDia());
        holder.textViewNumber.setText(String.valueOf(position+1));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Touched - "+holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();


            }
        });
    }

    @Override
    public int getItemCount() {
        if (recordList != null)
            return recordList.size();
        else return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate,textViewTime,textViewBp, textViewNumber, textViewMark;
        ImageView heart;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = (TextView)itemView.findViewById(R.id.textViewDate);
            textViewTime = (TextView)itemView.findViewById(R.id.textViewTime);
            textViewBp = (TextView)itemView.findViewById(R.id.textViewBp);
            textViewNumber = (TextView)itemView.findViewById(R.id.textViewNumber);
            textViewMark = (TextView)itemView.findViewById(R.id.textViewMark);
            heart = (ImageView)itemView.findViewById(R.id.heart);
        }
    }
}