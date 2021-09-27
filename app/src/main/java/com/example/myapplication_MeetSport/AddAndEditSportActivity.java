package com.example.myapplication_MeetSport;

import androidx.appcompat.app.AppCompatActivity;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

public class AddAndEditSportActivity extends AppCompatActivity {


    Date date;
    int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_edit_sport);


        EditText topiceditText = findViewById(R.id.editTextsetSportTitle);
        String sportType = SportTypeRecyclerviewActivity.SportTypedataModal.sportName;
        TextView sportNametextView = findViewById(R.id.sportNametextView);

        sportNametextView.setText(sportType);
        topiceditText.setHint("冰友啊有閒來打" + sportType + "喔!");


        CardView setCardViewInfo = findViewById(R.id.setcardViewinfo);
        TextView cardCardViewInfoTitle = findViewById(R.id.cardViewinfoTitle);
        TextView howManyPeopleTextView = findViewById(R.id.cardViewinfopeople);
        TextView aboutActivityMoney = findViewById(R.id.cardViewinfopay);
        howManyPeopleTextView.setVisibility(View.GONE);
        aboutActivityMoney.setVisibility(View.GONE);

        setCardViewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] setPayWay = {"各出各的", "主辦方支付", "免費"};

                Dialog dialog = new Dialog(AddAndEditSportActivity.this);
                dialog.setContentView(R.layout.show_info_layout);
                TextView showPayWay = dialog.findViewById(R.id.textViewPayWay);
                EditText gteHowManyPeople = dialog.findViewById(R.id.editTextNumber);

                Button nextButton = dialog.findViewById(R.id.nextbutton);
                nextButton.setOnClickListener(view13 -> {
                    if (index < setPayWay.length - 1) {
                        index = index + 1;
                        showPayWay.setText(setPayWay[index]);
                    }
                });

                Button backButton = dialog.findViewById(R.id.backbutton);
                backButton.setOnClickListener(view14 -> {
                    if (index > 0) {
                        index = index - 1;
                        showPayWay.setText(setPayWay[index]);

                    }
                });

                Button okButton = dialog.findViewById(R.id.okbutton);
                okButton.setOnClickListener(view15 -> {
                    cardCardViewInfoTitle.setVisibility(View.GONE);
                    howManyPeopleTextView.setVisibility(View.VISIBLE);
                    aboutActivityMoney.setVisibility(View.VISIBLE);

                    howManyPeopleTextView.setText("出席約:" + gteHowManyPeople.getText() + "人");
                    aboutActivityMoney.setText("費用方式:" + showPayWay.getText() + "");
                    dialog.dismiss();
                });

                dialog.show();
            }
        });


        CardView setTimeCardView = findViewById(R.id.cardViewforSetTime);
        TextView cardViewTextTitle = findViewById(R.id.cardViewTimeTitle);
        TextView cardViewShowStartTime = findViewById(R.id.timestarttextView);
        TextView cardViewShowEndTime = findViewById(R.id.timeendtextView);
        cardViewShowStartTime.setVisibility(View.GONE);
        cardViewShowEndTime.setVisibility(View.GONE);

        setTimeCardView.setOnClickListener(view -> {
            Dialog dialog = new Dialog(AddAndEditSportActivity.this);
            dialog.setContentView(R.layout.show_settime_layout);

            Button editStartTimebutton = dialog.findViewById(R.id.editTimebutton);//editEndTimebutton
            Button editEndTimebutton = dialog.findViewById(R.id.editEndTimebutton);
            Button okButton = dialog.findViewById(R.id.okbutton);

            editStartTimebutton.setOnClickListener(view1 -> AddAndEditSportActivity.this.timePickerDialog(editStartTimebutton, "選擇開始時間").show(AddAndEditSportActivity.this.getSupportFragmentManager(), "year_month_day"));
            editEndTimebutton.setOnClickListener(view1 -> AddAndEditSportActivity.this.timePickerDialog(editEndTimebutton, "選擇結束時間").show(AddAndEditSportActivity.this.getSupportFragmentManager(), "year_month_day"));
            okButton.setOnClickListener(view12 -> {
                cardViewTextTitle.setVisibility(View.GONE);

                cardViewShowStartTime.setVisibility(View.VISIBLE);
                cardViewShowStartTime.setText("開始:" + editStartTimebutton.getText() + "");
                cardViewShowEndTime.setVisibility(View.VISIBLE);
                cardViewShowEndTime.setText("結束:" + editEndTimebutton.getText() + "");


                dialog.dismiss();
            });


            dialog.show();

        });

        CardView setLocationCardView = findViewById(R.id.cardViewLocation);
        TextView cardViewLocationTextTitle = findViewById(R.id.cardViewLocationTitle);
        TextView LocationName = findViewById(R.id.LocationName);
        LocationName.setVisibility(View.GONE);

        setLocationCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddAndEditSportActivity.this,GoogleMapSystemMainActivity.class));
            }
        });


    }

    public void onBackPressed() {
        startActivity(new Intent(AddAndEditSportActivity.this, ThisSportTypeRecyclerviewActivity.class));
        finish();

    }

    TimePickerDialog timePickerDialog(Button button, String Message) {
        TimePickerDialog timePickerDialog = new TimePickerDialog.Builder()
                .setType(Type.ALL)
                .setTitleStringId(Message)
                .setThemeColor(getResources().getColor(android.R.color.holo_blue_dark))//
                .setMinMillseconds(setDataWanttheTime(Calendar.getInstance().get(Calendar.YEAR) + "-" + new String(Calendar.getInstance().get(Calendar.MONTH) + 1 + "") + "-" + Calendar.getInstance().get(Calendar.DATE) + " 00:00:00").getTime())
                .setMaxMillseconds(setDataWanttheTime(Calendar.getInstance().get(Calendar.YEAR) + 1 + "-12-31 00:00:00").getTime())
                .setCallBack((timePickerView, millseconds) -> {
                    getLongTime(millseconds);
                    button.setText(getDateToString(millseconds));
                })
                .build();
        return timePickerDialog;
    }

    Long getLongTime(long millseconds) {
        return millseconds;
    }


    Date setDataWanttheTime(String setTime) {
        try {
            date = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(setTime);//setTime 可以設置2021-09-23 00:00:00
            //date.getTime()
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String getDateToString(long time) {//顯示的時間
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = new Date(time);
        return sf.format(d);//.substring(0, 10)

    }

}