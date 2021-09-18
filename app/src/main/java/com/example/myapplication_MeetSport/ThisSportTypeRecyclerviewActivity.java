package com.example.myapplication_MeetSport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ThisSportTypeRecyclerviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_this_sport_type_recyclerview);

        FloatingActionButton addsportfloatingActionButton = findViewById(R.id.addsportfloatingActionButton);
        addsportfloatingActionButton.setOnClickListener(view -> {

            startActivity(new Intent(ThisSportTypeRecyclerviewActivity.this,AddAndEditSportActivity.class));


        });

    }


    public void onBackPressed() {
        startActivity(new Intent(ThisSportTypeRecyclerviewActivity.this, SportTypeRecyclerviewActivity.class));
        ThisSportTypeRecyclerviewActivity.this.finish();

    }
}