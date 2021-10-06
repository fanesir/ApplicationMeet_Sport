package com.example.myapplication_MeetSport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserAccountEditDataActivity extends AppCompatActivity {
    AboutInfoSportDataSet aboutInfoSportDataSet;
    AboutAccountUsetDataset aboutAccountUsetDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_edit_data);

        DatabaseReference mbase2 = FirebaseDatabase.getInstance().getReference("user_Account_Data")
                .child("UserEmail").child("a0936597756@gmailcom");


        mbase2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                aboutInfoSportDataSet = snapshot.getValue(AboutInfoSportDataSet.class);
                aboutAccountUsetDataset = snapshot.getValue(AboutAccountUsetDataset.class);
                Log.i("xxx", aboutAccountUsetDataset.getUuu()+"" );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}