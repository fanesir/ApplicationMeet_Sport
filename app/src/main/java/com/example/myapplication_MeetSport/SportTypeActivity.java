package com.example.myapplication_MeetSport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SportTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sport_type);
        RecyclerView recyclerView = findViewById(R.id.sportTypeRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference mbase = FirebaseDatabase.getInstance().getReference("Sport_Type");
        FirebaseRecyclerOptions<DataModal> options = new FirebaseRecyclerOptions.Builder<DataModal>()
                .setQuery(mbase, DataModal.class)
                .build();

        AdapterForMaimActivity foreMainAdpter = new AdapterForMaimActivity(options);
        recyclerView.setAdapter(foreMainAdpter);
        foreMainAdpter.startListening();
    }


    public class AdapterForMaimActivity extends FirebaseRecyclerAdapter
            <DataModal, AdapterForMaimActivity.sportTypeRecyclerViewViewholder> {

        public AdapterForMaimActivity(@NonNull FirebaseRecyclerOptions options) {
            super(options);
        }

        protected void onBindViewHolder(@NonNull sportTypeRecyclerViewViewholder holder, int position, @NonNull DataModal model) {
            holder.sportName.setText(model.getSportName());
            Picasso.get().load(model.sportBackImage).into(holder.sportbackgroundImageView);

        }

        public sportTypeRecyclerViewViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_type_layout, parent, false);
            return new sportTypeRecyclerViewViewholder(view);
        }


        class sportTypeRecyclerViewViewholder extends RecyclerView.ViewHolder {
            TextView sportName;
            ImageView sportbackgroundImageView;

            public sportTypeRecyclerViewViewholder(@NonNull View itemView) {
                super(itemView);
                sportName = itemView.findViewById(R.id.sportname);
                sportbackgroundImageView = itemView.findViewById(R.id.sportbackgroundImageView);
            }
        }
    }


}