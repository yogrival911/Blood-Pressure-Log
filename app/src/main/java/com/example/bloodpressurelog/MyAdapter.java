package com.example.bloodpressurelog;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
List<Record> recordList;
LayoutInflater inflater;
int selectedPosition;
SharedPreferences sharedpreferences;
int lowSys, lowDia,normalSys,normalDia,elevatedSys,elevatedDia,high1Sys,high1Dia,high2Sys,high2Dia;
    public MyAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);

        lowSys = sharedpreferences.getInt("lowSys", 90);
        lowDia = sharedpreferences.getInt("lowDia", 60);

        normalSys = sharedpreferences.getInt("normalSys", 120);
        normalDia = sharedpreferences.getInt("normalDia", 75);

        elevatedSys = sharedpreferences.getInt("elevatedSys", 130);
        elevatedDia = sharedpreferences.getInt("elevatedDia", 80);

        high1Sys = sharedpreferences.getInt("high1Sys", 140);
        high1Dia = sharedpreferences.getInt("high1Dia", 90);

        high2Sys = sharedpreferences.getInt("high2Sys", 180);
        high2Dia = sharedpreferences.getInt("high2Dia", 120);

        Log.i("yogshare", "MyAdapter" + lowSys);
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

        View rowView = inflater.inflate(R.layout.row_layout, parent, false);
        return new MyAdapter.MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        int sys = Integer.parseInt(recordList.get(position).getSys());
        int dia = Integer.parseInt(recordList.get(position).getDia());


        if(sys<=lowSys || dia<=lowDia){
            holder.heart.setImageResource(R.drawable.fav_icon);
            holder.textViewMark.setText("Low");
        }

        if((sys>lowSys && sys<=normalSys) &&(dia>lowDia && dia<=normalDia)){
            holder.heart.setImageResource(R.drawable.heart_green);
            holder.textViewMark.setText("Normal");
        }
        if((sys>normalSys && sys<=elevatedSys) &&(dia>normalDia && dia<=elevatedDia)){
            holder.heart.setImageResource(R.drawable.heart_yellow);
            holder.textViewMark.setText("Elevated");
        }
        if((sys>normalSys && sys<=elevatedSys) && (dia>lowDia && dia<=normalDia)){
            holder.heart.setImageResource(R.drawable.heart_yellow);
            holder.textViewMark.setText("Elevated");
        }
        if((sys>elevatedSys && sys<=high1Sys) || (dia>elevatedDia && dia<=high1Dia)){
            holder.heart.setImageResource(R.drawable.heart_orange_light);
            holder.textViewMark.setText("High Stage-1");
        }
        if((sys>high1Sys && sys<=high2Sys) || (dia>high1Dia && dia<=high2Dia)){
            holder.heart.setImageResource(R.drawable.heart_orange);
            holder.textViewMark.setText("High Stage-2");
        }
        if(sys>high2Sys || dia>high2Dia){
            holder.heart.setImageResource(R.drawable.heart_red);
            holder.textViewMark.setText("Emergency");
        }

        holder.textViewDate.setText(recordList.get(position).getDate());
        holder.textViewTime.setText(recordList.get(position).getTime());
        holder.textViewBp.setText(recordList.get(position).getSys() + " / "+ recordList.get(position).getDia());
        holder.textViewNumber.setText(String.valueOf(position+1));

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
