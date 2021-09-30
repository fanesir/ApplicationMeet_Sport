package com.example.myapplication_MeetSport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ThisSportTypeRecyclerviewActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_this_sport_type_recyclerview);

        RecyclerView recyclerView = findViewById(R.id.this_sport_RecyclerView);


        DatabaseReference mbase = FirebaseDatabase.getInstance().getReference("user_Put_Sport").child(SportTypeRecyclerviewActivity.SportTypedataModal.getSportEngName());
        Query query = mbase.orderByChild("sportStartTime");

        FirebaseRecyclerOptions<addAboutInfoSportDataSet> options =
                new FirebaseRecyclerOptions.Builder<addAboutInfoSportDataSet>()
                        .setQuery(query, addAboutInfoSportDataSet.class).build();

        adapterForDateRecyview foreMainAdpter = new adapterForDateRecyview(options);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(foreMainAdpter);
        foreMainAdpter.startListening();


        FloatingActionButton addsportfloatingActionButton = findViewById(R.id.addsportfloatingActionButton);
        addsportfloatingActionButton.setOnClickListener(view -> {

            startActivity(new Intent(ThisSportTypeRecyclerviewActivity.this, AddAndEditSportActivity.class));
            finish();

        });

    }


    class adapterForDateRecyview extends FirebaseRecyclerAdapter<addAboutInfoSportDataSet,
            adapterForDateRecyview.forRecyViewUi> {


        public adapterForDateRecyview(@NonNull FirebaseRecyclerOptions<addAboutInfoSportDataSet> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull forRecyViewUi holder, int position, @NonNull addAboutInfoSportDataSet model) {
            holder.title.setText(model.getSportTitle() + "");
            holder.timeTitle.setText(model.getSportStartTime() + " ~ " + model.getSportEndTime() + "");
        }

        @NonNull
        @Override
        public forRecyViewUi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_rv_this_type_layout, parent, false
            );
            return new forRecyViewUi(view);
        }

        class forRecyViewUi extends RecyclerView.ViewHolder {
            TextView title, timeTitle;

            public forRecyViewUi(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.sportTitle_Textview);
                timeTitle = itemView.findViewById(R.id.sporTime_Textview);

            }
        }


    }


    public void onBackPressed() {
        startActivity(new Intent(ThisSportTypeRecyclerviewActivity.this, SportTypeRecyclerviewActivity.class));
        ThisSportTypeRecyclerviewActivity.this.finish();

    }
}