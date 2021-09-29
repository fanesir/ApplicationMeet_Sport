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
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

public class AddAndEditSportActivity extends AppCompatActivity {

    addAboutInfoSportDataSet addAboutInfoSportDataSet = new addAboutInfoSportDataSet() ;

    Date date;
    int index = -1;
    static String sportTitle, sportContent, howManyMan, whoPay, sportStartTime, sportEndTime, Map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_edit_sport);


        EditText topicEditText = findViewById(R.id.editTextsetSportTitle);
        String sportType = SportTypeRecyclerviewActivity.SportTypedataModal.sportName;
        TextView sportNametextView = findViewById(R.id.sportNametextView);
        TextView sportContentedit = findViewById(R.id.editTextTextPersonName);

        sportNametextView.setText(sportType);
        topicEditText.setHint("冰友啊有閒來打" + sportType + "喔!");


        CardView setCardViewInfo = findViewById(R.id.setcardViewinfo);
        TextView cardCardViewInfoTitle = findViewById(R.id.cardViewinfoTitle);
        TextView howManyPeopleTextView = findViewById(R.id.cardViewinfopeople);
        TextView aboutActivityMoney = findViewById(R.id.cardViewinfopay);
        howManyPeopleTextView.setVisibility(View.GONE);
        aboutActivityMoney.setVisibility(View.GONE);

        setCardViewInfo.setOnClickListener(view -> {

            String[] setPayWay = {"各出各的", "主辦方支付", "免費"};

            Dialog dialog = new Dialog(AddAndEditSportActivity.this);
            dialog.setContentView(R.layout.show_info_layout);
            TextView showPayWay = dialog.findViewById(R.id.textViewPayWay);
            EditText gteEditHowManyPeople = dialog.findViewById(R.id.editTextNumber);

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

                addAboutInfoSportDataSet.setHowManyMan("出席約:" + gteEditHowManyPeople.getText().toString()  + "人");
                howManyPeopleTextView.setText("出席約:" + gteEditHowManyPeople.getText().toString()+ "人");

                addAboutInfoSportDataSet.setWhoPay("費用方式:" + showPayWay.getText() + "");
                aboutActivityMoney.setText("費用方式:" + showPayWay.getText() + "");

                if (gteEditHowManyPeople.getText().toString().equals("") || showPayWay.getText().equals("")) {
                    Toast.makeText(AddAndEditSportActivity.this, "欄位為空", Toast.LENGTH_LONG).show();
                    return;
                }

                dialog.dismiss();
            });

            dialog.show();
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
                cardViewShowEndTime.setVisibility(View.VISIBLE);

                addAboutInfoSportDataSet.setSportStartTime(editStartTimebutton.getText()+"");
                addAboutInfoSportDataSet.setSportEndTime(editEndTimebutton.getText() + "");

                cardViewShowStartTime.setText("開始:" + editStartTimebutton.getText() + "");
                cardViewShowEndTime.setText("結束:" + editEndTimebutton.getText() + "");

                if (editStartTimebutton.getText().equals("") || editEndTimebutton.getText().equals("")) {
                    Toast.makeText(AddAndEditSportActivity.this, "欄位為空", Toast.LENGTH_LONG).show();
                    return;
                }


                dialog.dismiss();
            });


            dialog.show();

        });

        CardView setLocationCardView = findViewById(R.id.cardViewLocation);
        TextView cardViewLocationTextTitle = findViewById(R.id.cardViewLocationTitle);
        TextView LocationName = findViewById(R.id.LocationName);

        if (Map == null) {
            LocationName.setVisibility(View.GONE);
        } else {
            cardViewLocationTextTitle.setVisibility(View.GONE);
            LocationName.setVisibility(View.VISIBLE);
            LocationName.setText(Map);

        }


        setLocationCardView.setOnClickListener(view -> {


            addAboutInfoSportDataSet.setSportTitle(topicEditText.getText() + "");
            addAboutInfoSportDataSet.setSportContent(sportContentedit.getText() + "");

            startActivity(new Intent(AddAndEditSportActivity.this
                    , GoogleMapSystemMainActivity.class).putExtra("SportInfoData",addAboutInfoSportDataSet));

            finish();


            // AddAndEditSportActivity.this.finish();

        });

        if (GoogleMapSystemMainActivity.whenUseGoogleMapBack == 99) {
            addAboutInfoSportDataSet = (addAboutInfoSportDataSet) getIntent().getSerializableExtra("SportInfoDataAddMap");

            cardCardViewInfoTitle.setVisibility(View.GONE);
            cardViewTextTitle.setVisibility(View.GONE);
            cardViewLocationTextTitle.setVisibility(View.GONE);

            howManyPeopleTextView.setVisibility(View.VISIBLE);
            aboutActivityMoney.setVisibility(View.VISIBLE);
            cardViewShowStartTime.setVisibility(View.VISIBLE);
            cardViewShowEndTime.setVisibility(View.VISIBLE);
            LocationName.setVisibility(View.VISIBLE);


            topicEditText.setText(addAboutInfoSportDataSet.getSportTitle());
            sportContentedit.setText(addAboutInfoSportDataSet.getSportContent());
            howManyPeopleTextView.setText(addAboutInfoSportDataSet.getHowManyMan());
            aboutActivityMoney.setText(addAboutInfoSportDataSet.getWhoPay());
            cardViewShowStartTime.setText(addAboutInfoSportDataSet.getSportStartTime());
            cardViewShowEndTime.setText(addAboutInfoSportDataSet.getSportEndTime());
            LocationName.setText(addAboutInfoSportDataSet.getMap());


        }


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