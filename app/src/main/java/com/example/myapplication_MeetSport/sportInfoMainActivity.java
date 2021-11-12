package com.example.myapplication_MeetSport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class sportInfoMainActivity extends AppCompatActivity {
    AboutInfoSportDataSet aboutInfoSportDataSet;
    static int SPORT_ACTIVITY_INFO_NUMBER = 0;
    static String THIS_SPORT_INFO_ID;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_info_main);

        DatabaseReference mbase2 = FirebaseDatabase.getInstance().getReference("user_Put_Sport")
                .child(SportTypeRecyclerviewActivity.sportTypedataModalDataSetA.getSportEngName()).child(ThisSportTypeRecyclerviewActivity.THIS_SPORT_INFO_FUZZY_ID);


        mbase2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                aboutInfoSportDataSet = snapshot.getValue(AboutInfoSportDataSet.class);
                ImageView edit_ImageView = findViewById(R.id.imageView3);
                edit_ImageView.setVisibility(View.GONE);

                RecyclerView recyclerView = findViewById(R.id.sport_account_inforecyclerView);

                if (aboutInfoSportDataSet.getUserEmail().equals(LoginActivity.USER_EMAIL)) {
                    edit_ImageView.setVisibility(View.VISIBLE);
                }

                TextView userEmail = findViewById(R.id.email_textView);
                TextView sportTopic_Textview = findViewById(R.id.sport_info_topic);
                TextView sportTime_Textview = findViewById(R.id.sport_info_time);
                TextView sportContent_Textview = findViewById(R.id.sport_info_content);
                TextView sportlocation_Textview = findViewById(R.id.sport_info_location);
                TextView sportmoney_Textview = findViewById(R.id.sport_info_money);

                userEmail.setText(aboutInfoSportDataSet.getUserHostName() + getText(R.string.USER_host));
                sportTopic_Textview.setText(aboutInfoSportDataSet.getSportTitle());
                sportTime_Textview.setText(aboutInfoSportDataSet.getSportStartTime() + "\n" + aboutInfoSportDataSet.getSportEndTime());
                sportContent_Textview.setText(aboutInfoSportDataSet.getSportContent());
                sportlocation_Textview.setText(Html.fromHtml("<u>" + aboutInfoSportDataSet.getMap() + "</u>"));
                sportmoney_Textview.setText(aboutInfoSportDataSet.getHowManyMan() + "\n" + aboutInfoSportDataSet.getWhoPay());

                sportlocation_Textview.setOnClickListener(view -> {
                    Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=qwerty&query_place_id=" + aboutInfoSportDataSet.getMapid());
                    startActivity(new Intent(Intent.ACTION_VIEW, gmmIntentUri).setPackage("com.google.android.apps.maps"));
                });

                edit_ImageView.setOnClickListener(view -> {
                    PopupMenu popup = new PopupMenu(edit_ImageView.getContext(), view);
                    popup.getMenuInflater().inflate(R.menu.edit_menu, popup.getMenu());
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.item1:
                                    ALLDataBasedirector.aboutInfoSportDataSet = aboutInfoSportDataSet;
                                    SPORT_ACTIVITY_INFO_NUMBER = 98;
                                    THIS_SPORT_INFO_ID = aboutInfoSportDataSet.getFuzzyID();
                                    startActivity(new Intent(sportInfoMainActivity.this, AddAndEditSportActivity.class));
                                    finish();
                                    return true;

                                case R.id.item2:
                                    mbase2.removeValue();
                                    startActivity(new Intent(sportInfoMainActivity.this, ThisSportTypeRecyclerviewActivity.class));
                                    finish();
                                    return true;

                            }


                            return false;
                        }
                    });

                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void onBackPressed() {


        startActivity(new Intent(sportInfoMainActivity.this, ThisSportTypeRecyclerviewActivity.class));
        finish();

    }
}















