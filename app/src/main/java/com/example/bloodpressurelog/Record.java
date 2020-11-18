package com.example.bloodpressurelog;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reading_record")
public class Record {
    @PrimaryKey(autoGenerate = true)
    int id;

    String sys, dia,pulse,posture, position, breakfast, lunch, dinner, med, salt, sym, remark, date, time;



    public Record(String sys, String dia, String pulse, String posture, String position, String breakfast, String lunch, String dinner, String med, String salt, String sym, String remark, String date, String time) {
        this.sys = sys;
        this.dia = dia;
        this.pulse = pulse;
        this.posture = posture;
        this.position = position;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        this.med = med;
        this.salt = salt;
        this.sym = sym;
        this.remark = remark;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getSys() {
        return sys;
    }

    public String getDia() {
        return dia;
    }

    public String getPulse() {
        return pulse;
    }

    public String getPosture() {
        return posture;
    }

    public String getPosition() {
        return position;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public String getDinner() {
        return dinner;
    }

    public String getMed() {
        return med;
    }

    public String getSalt() {
        return salt;
    }

    public String getSym() {
        return sym;
    }

    public String getRemark() {
        return remark;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setId(int id) {
        this.id = id;
    }
}
