package com.example.myapplication_MeetSport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.PopupMenu;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UserAccountEditDataActivity extends AppCompatActivity {
    Date date;
    AboutAccountUsetDataset aboutAccountUsetDataset;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_edit_data);


        EditText EditTextUserWantName = findViewById(R.id.editTextUserWantName);
        EditText EditTextUsertEmail = findViewById(R.id.editTextTextUserEmail);
        EditText EditTextUsertPerson = findViewById(R.id.editTextUserPerson);
        EditText EditTextUsertJob = findViewById(R.id.editTextUserJOB);
        EditText EditTextUserContent = findViewById(R.id.editTextUserContent);
        EditText EditTextUserBirthday = findViewById(R.id.editTextUserBirthday);
        Button okButton = findViewById(R.id.button2);
        EditTextUsertEmail.setText(LoginActivity.USER_EMAIL);
        EditTextUsertEmail.setEnabled(false);
        EditTextUsertPerson.setInputType(InputType.TYPE_NULL);
        EditTextUserBirthday.setInputType(InputType.TYPE_NULL);

        if (UserAccountInfo.USER_WANT_EDIT == 99) {
            EditTextUsertPerson.setEnabled(false);
            EditTextUserBirthday.setEnabled(false);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user_Account_Data")
                    .child(LoginActivity.USER_EMAIL.replace(".", ""));
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    aboutAccountUsetDataset = snapshot.getValue(AboutAccountUsetDataset.class);
                    EditTextUserWantName.setText(aboutAccountUsetDataset.getUserIDName());
                    EditTextUsertPerson.setText(aboutAccountUsetDataset.getUserPerson());
                    EditTextUsertJob.setText(aboutAccountUsetDataset.getUserJob());
                    EditTextUserContent.setText(aboutAccountUsetDataset.getUserContent());
                    EditTextUserBirthday.setText(aboutAccountUsetDataset.getUserBirthday());
                    userid = aboutAccountUsetDataset.getUserAccountUid();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


        EditTextUsertPerson.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(EditTextUsertPerson.getContext(), view);
            popup.getMenuInflater().inflate(R.menu.boyorgirllayout, popup.getMenu());
            popup.show();
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.item1:
                        EditTextUsertPerson.setText(R.string.USER_boy);
                        return true;

                    case R.id.item2:
                        EditTextUsertPerson.setText(R.string.USER_girl);
                        return true;

                }


                return false;
            });
            popup.show();
        });

        EditTextUserBirthday.setOnClickListener(view -> timePickerDialog(EditTextUserBirthday, "選擇生日").show(getSupportFragmentManager(), "year_month_day"));


        okButton.setOnClickListener(view -> {

            aboutAccountUsetDataset = new AboutAccountUsetDataset();

            aboutAccountUsetDataset.setUserIDName(EditTextUserWantName.getText().toString());
            aboutAccountUsetDataset.setUserEmail(LoginActivity.USER_EMAIL);
            aboutAccountUsetDataset.setUserPerson(EditTextUsertPerson.getText().toString());
            aboutAccountUsetDataset.setUserJob(EditTextUsertJob.getText().toString());
            aboutAccountUsetDataset.setUserContent(EditTextUserContent.getText().toString());
            aboutAccountUsetDataset.setUserBirthday(EditTextUserBirthday.getText().toString());


            if (EditTextUserWantName.getText().toString().matches("") || EditTextUsertEmail.getText().toString().matches("")
                    || EditTextUsertPerson.getText().toString().matches("") || EditTextUsertJob.getText().toString().matches("")
                    || EditTextUserContent.getText().toString().matches("") || EditTextUserBirthday.getText().toString().matches("")) {
                Toast.makeText(UserAccountEditDataActivity.this, "有欄位空白", Toast.LENGTH_LONG).show();
                return;
            }
            DatabaseReference mbase2 = FirebaseDatabase.getInstance().getReference("user_Account_Data")
                    .child(LoginActivity.USER_EMAIL.replace(".", ""));

            if (UserAccountInfo.USER_WANT_EDIT == 99) {//編輯用
                aboutAccountUsetDataset.setUserAccountUid(userid);
                mbase2.setValue(aboutAccountUsetDataset);
                Toast.makeText(UserAccountEditDataActivity.this, R.string.USER_Edit_sucess, Toast.LENGTH_LONG).show();
                startActivity(new Intent(UserAccountEditDataActivity.this, UserAccountInfo.class));
            } else {//新增用
                aboutAccountUsetDataset.setUserAccountUid(mbase2.push().getKey());
                mbase2.setValue(aboutAccountUsetDataset);
                startActivity(new Intent(UserAccountEditDataActivity.this, SportTypeRecyclerviewActivity.class));
            }



            UserAccountInfo.USER_WANT_EDIT = 0;
            finish();

        });

    }

    TimePickerDialog timePickerDialog(EditText editText, String Message) {
        TimePickerDialog timePickerDialog = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setMinMillseconds(new Long(31536000 / 1000))
                .setTitleStringId(Message)
                .setThemeColor(getResources().getColor(android.R.color.holo_blue_dark))
                .setCallBack((timePickerView, millseconds) -> {
                    getLongTime(millseconds);
                    editText.setText(getDateToString(millseconds));
                })
                .build();
        return timePickerDialog;
    }


    Long getLongTime(long millseconds) {
        return millseconds;
    }

    public String getDateToString(long time) {//顯示的時間
        Log.i("a", time + "");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date(time);
        return sf.format(d);

    }

    Date setDataWanttheTime(String setTime) {
        try {
            date = new SimpleDateFormat("yyyy-MM-dd ").parse(setTime);//setTime 可以設置2021-09-23 00:00:00

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public void onBackPressed() {
        startActivity(new Intent(UserAccountEditDataActivity.this, UserAccountInfo.class));
        finish();
    }

}