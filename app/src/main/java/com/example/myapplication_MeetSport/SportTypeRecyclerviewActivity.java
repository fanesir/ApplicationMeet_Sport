package com.example.myapplication_MeetSport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class SportTypeRecyclerviewActivity extends AppCompatActivity {

    static ADataModalDataSet sportTypedataModalDataSetA;
    static String SportEngName, UserNAME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_type);

        RecyclerView recyclerView = findViewById(R.id.sportTypeRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference mbase = FirebaseDatabase.getInstance().getReference("Sport_Type");
        FirebaseRecyclerOptions<ADataModalDataSet> options = new FirebaseRecyclerOptions.Builder<ADataModalDataSet>()
                .setQuery(mbase, ADataModalDataSet.class)
                .build();

        AdapterForMaimActivity foreMainAdpter = new AdapterForMaimActivity(options);
        recyclerView.setAdapter(foreMainAdpter);
        foreMainAdpter.startListening();


        ImageButton NavigationButton = findViewById(R.id.NavigationButton);
        NavigationButton.setOnClickListener(view -> {
            DrawerLayout drawer = findViewById(R.id.drawer_layoutt);
            drawer.openDrawer(GravityCompat.START);
        });


        ShowMenuData();
        authority();

    }


    public class AdapterForMaimActivity extends FirebaseRecyclerAdapter
            <ADataModalDataSet, AdapterForMaimActivity.sportTypeRecyclerViewViewholder> {

        public AdapterForMaimActivity(@NonNull FirebaseRecyclerOptions options) {
            super(options);
        }

        protected void onBindViewHolder(@NonNull sportTypeRecyclerViewViewholder holder, int position, @NonNull ADataModalDataSet model) {
            holder.sportName.setText(model.getSportName());
            holder.sportEngName.setText(model.getSportEngName());


            SportEngName = model.sportEngName;

            Picasso.get().load(model.sportBackImage).into(holder.sportbackgroundImageView, new Callback() {
                @Override
                public void onSuccess() {
                    holder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }
            });


            holder.sportbackgroundImageView.setOnClickListener(view -> {
                sportTypedataModalDataSetA = model;
                startActivity(new Intent(SportTypeRecyclerviewActivity.this,
                        ThisSportTypeRecyclerviewActivity.class));
                finish();
            });

        }

        public sportTypeRecyclerViewViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_type_layout, parent, false);
            return new sportTypeRecyclerViewViewholder(view);
        }


        class sportTypeRecyclerViewViewholder extends RecyclerView.ViewHolder {
            TextView sportName, sportEngName;
            ImageView sportbackgroundImageView;
            ProgressBar progressBar;

            public sportTypeRecyclerViewViewholder(@NonNull View itemView) {
                super(itemView);
                sportName = itemView.findViewById(R.id.sportname);
                sportbackgroundImageView = itemView.findViewById(R.id.sportbackgroundImageView);
                sportEngName = itemView.findViewById(R.id.sportengname);
                progressBar = itemView.findViewById(R.id.progressBar231);
            }
        }
    }

    public void authority() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)//取得權限

        {//確認使否有授權 good
            if (ContextCompat.checkSelfPermission(SportTypeRecyclerviewActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {//跳出來給選擇
                ActivityCompat.requestPermissions(SportTypeRecyclerviewActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            } else {

            }
        }
    }

    private void ShowMenuData() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user_Account_Data")
                .child(LoginActivity.USER_EMAIL.replace(".", ""));
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                AboutAccountUsetDataset aboutAccountUsetDataset = snapshot.getValue(AboutAccountUsetDataset.class);

                NavigationView NavigationViewsporttype = findViewById(R.id.NavigationViewsporttype);

                Menu menu = NavigationViewsporttype.getMenu();
                MenuItem useridName = menu.findItem(R.id.useremail);


                try {
                    useridName.setTitle("HI " + aboutAccountUsetDataset.getUserIDName() + "");
                    UserNAME = aboutAccountUsetDataset.getUserIDName();

                } catch (NullPointerException nullPointerException) {//新增
                    ALLDataBasedirector.USER_WANT_NEW_EDIT = 21;
                    startActivity(new Intent(SportTypeRecyclerviewActivity.this, AddNewAccount.class));

                }


                NavigationViewsporttype.setNavigationItemSelectedListener(item -> {
                    int id = item.getItemId();
                    if (id == R.id.useremail) {
                        startActivity(new Intent(SportTypeRecyclerviewActivity.this, UserAccountInfo.class));
                        finish();
                    }
                    if (id == R.id.itemlogout) {
                        FirebaseAuth.getInstance().signOut();
                        SportTypeRecyclerviewActivity.this.startActivity(new Intent(SportTypeRecyclerviewActivity.this, LoginActivity.class));
                        LoginActivity.USER_EMAIL = null;
                        finish();
                    }
                    return false;
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}