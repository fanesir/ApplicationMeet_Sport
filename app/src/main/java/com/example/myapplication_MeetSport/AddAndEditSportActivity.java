package com.example.myapplication_MeetSport;

import androidx.appcompat.app.AppCompatActivity;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddAndEditSportActivity extends AppCompatActivity {

    TimePickerDialog timePickerDialog;
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_edit_sport);
        long twoYears = 1L * 365 * 1000 * 60 * 60 * 24L;

        EditText topiceditText = findViewById(R.id.editTextsetSportTitle);
        String sportType = SportTypeRecyclerviewActivity.SportTypedataModal.sportName;
        TextView sportNametextView = findViewById(R.id.sportNametextView);
        Button editTimebutton = findViewById(R.id.editTimebutton);

        sportNametextView.setText(sportType);
        topiceditText.setHint("下班一起來打" + sportType + "吧!");


        editTimebutton.setOnClickListener(view -> timePickerDialog.show(AddAndEditSportActivity.this.getSupportFragmentManager(), "year_month_day"));

        timePickerDialog = new TimePickerDialog.Builder()
                .setType(Type.ALL)
                .setTitleStringId("選擇開始時間")
                .setThemeColor(getResources().getColor(android.R.color.holo_blue_dark))
                .setMinMillseconds(setDataWanttheTime(Calendar.getInstance().get(Calendar.YEAR) + "-01-01 00:00:00").getTime())//setWanttheTime
                .setMaxMillseconds(setDataWanttheTime(Calendar.getInstance().get(Calendar.YEAR)+1 + "-12-31 00:00:00").getTime())
                .setCallBack((timePickerView, millseconds) -> editTimebutton.setText(getDateToString(millseconds)))
                .build();


    }

    public void onBackPressed() {
        startActivity(new Intent(AddAndEditSportActivity.this, ThisSportTypeRecyclerviewActivity.class));
        finish();

    }

    public Date setDataWanttheTime(String setTime) {
        try {
            date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(setTime);//
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String getDateToString(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date(time);
        Log.i("date", new Date() + "");
        return sf.format(d);//.substring(0, 10)

    }

}