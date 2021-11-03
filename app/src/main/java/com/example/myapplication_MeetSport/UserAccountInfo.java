package com.example.myapplication_MeetSport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class UserAccountInfo extends AppCompatActivity {
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_info);


        TextView textViewUserName = findViewById(R.id.textViewUserName);
        TextView textViewUserPerson = findViewById(R.id.textViewUserGender);
        TextView textViewUSER_Job = findViewById(R.id.textViewUSER_Job);
        TextView textViewUSER_Content = findViewById(R.id.textViewUSER_Content);//user_Account_Data
        ImageButton imageButtonEditAccount = findViewById(R.id.imageButtonEditAccount);


        ImageView userIconImageView = findViewById(R.id.userInfoEditImageView);
        ProgressBar imageviewProgressBar = findViewById(R.id.userInfoEditImageViewBar);


    databaseReference =FirebaseDatabase.getInstance().

    getReference("user_Account_Data").child(LoginActivity.USER_EMAIL.replace(".", ""));
    databaseReference.addListenerForSingleValueEvent(new

    ValueEventListener() {
        @Override
        public void onDataChange (@NonNull DataSnapshot snapshot){
            AboutAccountUsetDataset aboutAccountUsetDataset = snapshot.getValue(AboutAccountUsetDataset.class);
            textViewUserName.setText(aboutAccountUsetDataset.getUserIDName());
            textViewUserPerson.setText(aboutAccountUsetDataset.getUserPerson() + " " + UserAge(aboutAccountUsetDataset.getUserBirthday().substring(0, 4), Calendar.getInstance().get(Calendar.YEAR)) + getString(R.string.USER_Age));
            textViewUSER_Job.setText(aboutAccountUsetDataset.getUserJob());
            textViewUSER_Content.setText(aboutAccountUsetDataset.getUserContent());
            Picasso.get().load(aboutAccountUsetDataset.getUserImage()).into(userIconImageView, new Callback() {
                @Override
                public void onSuccess() {
                    imageviewProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }
            });

        }

        @Override
        public void onCancelled (@NonNull DatabaseError error){

        }


    });


        imageButtonEditAccount.setOnClickListener(view ->

    {
        ALLDataBasedirector.USER_WANT_NEW_EDIT = 99;
        startActivity(new Intent(UserAccountInfo.this, UserAccountEditDataActivity.class));
        finish();
    });
}


    public int UserAge(String Barday, int Thisyear) {
        int thisyear = Integer.parseInt(String.valueOf(Thisyear));
        int barday = Integer.parseInt(Barday);
        return thisyear - barday;
    }

    public void onBackPressed() {
        AddAndEditSportActivity.AboutInfoSportDataSet.setMap(getString(R.string.when_Map_Info_Null));
        startActivity(new Intent(UserAccountInfo.this, SportTypeRecyclerviewActivity.class));
        finish();
    }
}
