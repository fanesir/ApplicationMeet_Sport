package com.example.myapplication_MeetSport;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.cardview.widget.CardView;

public class AddAndEditSportActivity extends AppCompatActivity {
    static AboutInfoSportDataSet AboutInfoSportDataSet = new AboutInfoSportDataSet();
    Date date;
    int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_edit_sport);


        EditText topicEditText = findViewById(R.id.editTextsetSportTitle);
        String sportType = SportTypeRecyclerviewActivity.SportTypedataModal.sportName;
        TextView sportNameTextView = findViewById(R.id.sportNametextView);
        EditText sportContentEdit = findViewById(R.id.editTextTextPersonName);


        sportNameTextView.setText(sportType);
        topicEditText.setHint("冰友啊有閒來打" + sportType + "喔!");

        EdtiRealTimeChangeData(topicEditText, "Title");
        EdtiRealTimeChangeData(sportContentEdit, "Content");

        //第一列
        CardView setCardViewInfo = findViewById(R.id.setcardViewinfo);
        TextView cardCardViewInfoTitle = findViewById(R.id.cardViewinfoTitle);
        TextView howManyPeopleTextView = findViewById(R.id.cardViewinfopeople);
        TextView aboutActivityMoney = findViewById(R.id.cardViewinfopay);

        Button finishToFirebase = findViewById(R.id.finshbuttontofirebase);
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

                AboutInfoSportDataSet.setHowManyMan("出席約:" + gteEditHowManyPeople.getText().toString() + "人");
                howManyPeopleTextView.setText("出席約:" + gteEditHowManyPeople.getText().toString() + "人");

                AboutInfoSportDataSet.setWhoPay("費用方式:" + showPayWay.getText() + "");
                aboutActivityMoney.setText("費用方式:" + showPayWay.getText() + "");

                if (gteEditHowManyPeople.getText().toString().equals("") || showPayWay.getText().equals("")) {
                    Toast.makeText(AddAndEditSportActivity.this, "欄位為空", Toast.LENGTH_LONG).show();
                    return;
                }

                dialog.dismiss();
            });

            dialog.show();
        });

//第二列
        CardView setTimeCardView = findViewById(R.id.cardViewforSetTime);
        TextView cardViewTextTitle = findViewById(R.id.cardViewTimeTitle);
        TextView cardViewShowStartTime = findViewById(R.id.timestarttextView);
        TextView cardViewShowEndTime = findViewById(R.id.timeendtextView);
        cardViewShowStartTime.setVisibility(View.GONE);
        cardViewShowEndTime.setVisibility(View.GONE);

        setTimeCardView.setOnClickListener(view -> {
            Dialog dialog = new Dialog(AddAndEditSportActivity.this);
            dialog.setContentView(R.layout.show_settime_layout);

            Button editStartTimebutton = dialog.findViewById(R.id.editTimebutton);
            Button editEndTimebutton = dialog.findViewById(R.id.editEndTimebutton);
            Button okButton = dialog.findViewById(R.id.okbutton);

            editStartTimebutton.setOnClickListener(view1 -> AddAndEditSportActivity.this.timePickerDialog(editStartTimebutton, "選擇開始時間").show(AddAndEditSportActivity.this.getSupportFragmentManager(), "year_month_day"));
            editEndTimebutton.setOnClickListener(view1 -> AddAndEditSportActivity.this.timePickerDialog(editEndTimebutton, "選擇結束時間").show(AddAndEditSportActivity.this.getSupportFragmentManager(), "year_month_day"));
            okButton.setOnClickListener(view12 -> {
                cardViewTextTitle.setVisibility(View.GONE);
                cardViewShowStartTime.setVisibility(View.VISIBLE);
                cardViewShowEndTime.setVisibility(View.VISIBLE);

                AboutInfoSportDataSet.setSportStartTime("開始:" + editStartTimebutton.getText() + "");
                AboutInfoSportDataSet.setSportEndTime("結束:" + editEndTimebutton.getText() + "");

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
//第三列
        CardView setLocationCardView = findViewById(R.id.cardViewLocation);
        TextView cardViewLocationTextTitle = findViewById(R.id.cardViewLocationTitle);
        TextView LocationName = findViewById(R.id.LocationName);
        LocationName.setVisibility(View.GONE);


        setLocationCardView.setOnClickListener(view -> {

            startActivity(new Intent(AddAndEditSportActivity.this
                    , GoogleMapSystemMainActivity.class));

            finish();


        });

//從選擇地標回來
        if (GoogleMapSystemMainActivity.whenUseGoogleMapBack == 99) {//地圖返回
            TextView[] cardViewtextViews = {cardCardViewInfoTitle, cardViewTextTitle, cardViewLocationTextTitle};
            TextView[] cardViewCOntent = {howManyPeopleTextView, aboutActivityMoney, cardViewShowStartTime, cardViewShowEndTime, LocationName};
            for (TextView v : cardViewtextViews) {
                v.setVisibility(View.GONE);
            }
            for (TextView v : cardViewCOntent) {
                v.setVisibility(View.VISIBLE);
            }


            topicEditText.setText(AboutInfoSportDataSet.getSportTitle());
            sportContentEdit.setText(AboutInfoSportDataSet.getSportContent());
            howManyPeopleTextView.setText(AboutInfoSportDataSet.getHowManyMan());
            aboutActivityMoney.setText(AboutInfoSportDataSet.getWhoPay());
            LocationName.setText(AboutInfoSportDataSet.getMap());

            cardViewShowStartTime.setText(AboutInfoSportDataSet.getSportStartTime());
            cardViewShowEndTime.setText(AboutInfoSportDataSet.getSportEndTime());


            if (AboutInfoSportDataSet.getHowManyMan() == null | AboutInfoSportDataSet.getWhoPay() == null) {
                cardCardViewInfoTitle.setVisibility(View.VISIBLE);
                howManyPeopleTextView.setVisibility(View.GONE);
                aboutActivityMoney.setVisibility(View.GONE);
            }
            if (AboutInfoSportDataSet.getSportStartTime() == null | AboutInfoSportDataSet.getSportEndTime() == null) {
                cardViewTextTitle.setVisibility(View.VISIBLE);
                cardViewShowStartTime.setVisibility(View.GONE);
                cardViewShowEndTime.setVisibility(View.GONE);
            }

        }

//資料來自運動資訊編輯
        if (sportInfoMainActivity.SPORT_ACTIVITY_INFO_NUMBER == 98) {

            TextView[] cardViewtextViews = {cardCardViewInfoTitle, cardViewTextTitle, cardViewLocationTextTitle};
            TextView[] cardViewCOntent = {howManyPeopleTextView, aboutActivityMoney, cardViewShowStartTime, cardViewShowEndTime, LocationName};

            for (TextView v : cardViewtextViews) {
                v.setVisibility(View.GONE);
            }
            for (TextView v : cardViewCOntent) {
                v.setVisibility(View.VISIBLE);
            }

            topicEditText.setText(DataBasedirector.aboutInfoSportDataSet.getSportTitle() + "");
            EdtiRealTimeChangeDataa(topicEditText, 1);

            sportContentEdit.setText(DataBasedirector.aboutInfoSportDataSet.getSportContent());
            EdtiRealTimeChangeDataa(sportContentEdit, 2);

            howManyPeopleTextView.setText(DataBasedirector.aboutInfoSportDataSet.getHowManyMan());
            realEditTextviewInfo(howManyPeopleTextView, 3);

            aboutActivityMoney.setText(DataBasedirector.aboutInfoSportDataSet.getWhoPay());
            realEditTextviewInfo(aboutActivityMoney, 4);

            cardViewShowStartTime.setText(DataBasedirector.aboutInfoSportDataSet.getSportStartTime());
            realEditTextviewInfo(cardViewShowStartTime, 5);

            cardViewShowEndTime.setText(DataBasedirector.aboutInfoSportDataSet.getSportEndTime());
            realEditTextviewInfo(cardViewShowEndTime, 6);

            LocationName.setText(DataBasedirector.aboutInfoSportDataSet.getMap());
            realEditTextviewInfo(LocationName, 7);

        }


        finishToFirebase.setOnClickListener(view -> {
            if (sportInfoMainActivity.SPORT_ACTIVITY_INFO_NUMBER == 98) {
                String[] FirebaseIndex = {"sportTitle", "sportContent", "howManyMan", "whoPay"
                        , "sportStartTime", "sportEndTime", "map", "mapid"};
                String[] ForFirebaseData = {
                        DataBasedirector.aboutInfoSportDataSet.getSportTitle(),
                        DataBasedirector.aboutInfoSportDataSet.getSportContent(),
                        DataBasedirector.aboutInfoSportDataSet.getHowManyMan(),
                        DataBasedirector.aboutInfoSportDataSet.getWhoPay(),
                        DataBasedirector.aboutInfoSportDataSet.getSportStartTime(),
                        DataBasedirector.aboutInfoSportDataSet.getSportEndTime(),
                        DataBasedirector.aboutInfoSportDataSet.getMap(),
                        DataBasedirector.aboutInfoSportDataSet.getMapid()
                };

                PutEditFireBaseData(FirebaseIndex, ForFirebaseData);
                startActivity(new Intent(AddAndEditSportActivity.this, sportInfoMainActivity.class));
                finish();

            } else if (GoogleMapSystemMainActivity.whenUseGoogleMapBack == 99) {
                PutNewDataForFireBase();
            }


        });


    }

    private void EdtiRealTimeChangeData(EditText getEditText, String editName) {
        getEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editName == "Title") {
                    AboutInfoSportDataSet.setSportTitle(getEditText.getText().toString());

                } else if (editName == "Content") {
                    AboutInfoSportDataSet.setSportContent(getEditText.getText().toString());

                }

            }
        });
    }

    private void EdtiRealTimeChangeDataa(EditText getEditText, int i) {
        getEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (i == 1) {
                    DataBasedirector.aboutInfoSportDataSet.setSportTitle(getEditText.getText() + "");

                } else if (i == 2) {
                    DataBasedirector.aboutInfoSportDataSet.setSportContent(getEditText.getText() + "");

                }

            }
        });
    }

    public void realEditTextviewInfo(TextView textView, int i) {//我做到這
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (i == 3) {
                    DataBasedirector.aboutInfoSportDataSet.setHowManyMan(textView.getText() + "");
                } else if (i == 4) {
                    DataBasedirector.aboutInfoSportDataSet.setWhoPay(textView.getText() + "");
                } else if (i == 5) {
                    DataBasedirector.aboutInfoSportDataSet.setSportStartTime(textView.getText() + "");
                } else if (i == 6) {
                    DataBasedirector.aboutInfoSportDataSet.setSportEndTime(textView.getText() + "");
                } else if (i == 7) {
                    DataBasedirector.aboutInfoSportDataSet.setMap(textView.getText() + "");
                }
            }
        });
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

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String getDateToString(long time) {//顯示的時間
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = new Date(time);
        return sf.format(d);

    }

    public void PutNewDataForFireBase() {


        GoogleMapSystemMainActivity.whenUseGoogleMapBack = 0;

        DatabaseReference referenceDatabase = FirebaseDatabase.getInstance().getReference().child("user_Put_Sport")
                .child(SportTypeRecyclerviewActivity.SportTypedataModal.getSportEngName());
        String fuzzyID = referenceDatabase.push().getKey() + "";

        AboutInfoSportDataSet.setUserEmail(LoginActivity.USER_ID + "");
        AboutInfoSportDataSet.setFuzzyID(fuzzyID);
        referenceDatabase.child(fuzzyID).setValue(AboutInfoSportDataSet);

        if (AboutInfoSportDataSet.getSportTitle() == null || AboutInfoSportDataSet.getSportContent() == null || AboutInfoSportDataSet.getHowManyMan() == null || AboutInfoSportDataSet.getWhoPay() == null || AboutInfoSportDataSet.getSportStartTime() == null || AboutInfoSportDataSet.getSportEndTime() == null || AboutInfoSportDataSet.getMap() == null) {
            Toast.makeText(AddAndEditSportActivity.this, "有欄位空白", Toast.LENGTH_LONG).show();
            return;
        }

        AboutInfoSportDataSet = new AboutInfoSportDataSet();
        sportInfoMainActivity.SPORT_ACTIVITY_INFO_NUMBER = 0;
        startActivity(new Intent(AddAndEditSportActivity.this, ThisSportTypeRecyclerviewActivity.class));


        finish();


    }


    void PutEditFireBaseData(String[] FirebaseIndex, String[] ForFirebaseData) {

        DatabaseReference referenceDatabase = FirebaseDatabase.getInstance().getReference().child("user_Put_Sport")
                .child(SportTypeRecyclerviewActivity.SportTypedataModal.getSportEngName()).child(sportInfoMainActivity.THIS_SPORT_INFO_ID);

        for (int i = 0; i < FirebaseIndex.length; i++) {
            referenceDatabase.child(FirebaseIndex[i]).setValue(ForFirebaseData[i]);
        }
        sportInfoMainActivity.SPORT_ACTIVITY_INFO_NUMBER = 0;

    }

    public void onBackPressed() {
        startActivity(new Intent(AddAndEditSportActivity.this, ThisSportTypeRecyclerviewActivity.class));
        finish();

    }


}


