package com.example.myapplication_MeetSport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class AddAndEditSportActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_edit_sport);

        EditText topiceditText = findViewById(R.id.editTextsetSportTitle);
        String sportType = SportTypeRecyclerviewActivity.SportTypedataModal.sportName;
        TextView sportNametextView = findViewById(R.id.sportNametextView);

        sportNametextView.setText(sportType);
        topiceditText.setHint("下班一起來打"+sportType+"吧!");

    }

    public void onBackPressed() {
        startActivity(new Intent(AddAndEditSportActivity.this, ThisSportTypeRecyclerviewActivity.class));
        finish();

    }
}