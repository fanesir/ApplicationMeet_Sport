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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SportTypeRecyclerviewActivity extends AppCompatActivity {
    AboutAccountUsetDataset aboutAccountUsetDataset;
    static ADataModalDataSet sportTypedataModalDataSetA;
    static String SportEngName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_type);

        IFThisUserDontHaveData();


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


        NavigationView NavigationViewsporttype = findViewById(R.id.NavigationViewsporttype);
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);

        Menu menu = NavigationViewsporttype.getMenu();
        MenuItem nav_camara = menu.findItem(R.id.useremail);
        nav_camara.setTitle(googleSignInAccount.getEmail() + "");

        NavigationViewsporttype.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.itemlogout) {
                FirebaseAuth.getInstance().signOut();
                SportTypeRecyclerviewActivity.this.startActivity(new Intent(SportTypeRecyclerviewActivity.this, LoginActivity.class));
                LoginActivity.USER_EMAIL = null;
                finish();
            }
            return false;
        });
        authority();

    }

    private void IFThisUserDontHaveData() {
        DatabaseReference mbase2 = FirebaseDatabase.getInstance().getReference("user_Account_Data")
                .child("UserEmail").child(LoginActivity.USER_EMAIL.replace(".", ""));
        mbase2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                try {
                    aboutAccountUsetDataset = snapshot.getValue(AboutAccountUsetDataset.class);
                    Log.i("asd", LoginActivity.USER_EMAIL.replace(".", ""));
                } catch (NullPointerException e) {
                    startActivity(new Intent(SportTypeRecyclerviewActivity.this, UserAccountEditDataActivity.class));
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
//

    public class AdapterForMaimActivity extends FirebaseRecyclerAdapter
            <ADataModalDataSet, AdapterForMaimActivity.sportTypeRecyclerViewViewholder> {

        public AdapterForMaimActivity(@NonNull FirebaseRecyclerOptions options) {
            super(options);
        }

        protected void onBindViewHolder(@NonNull sportTypeRecyclerViewViewholder holder, int position, @NonNull ADataModalDataSet model) {
            holder.sportName.setText(model.getSportName());
            holder.sportEngName.setText(model.getSportEngName());
            Picasso.get().load(model.sportBackImage).into(holder.sportbackgroundImageView);
            SportEngName = model.sportEngName;

            holder.sportbackgroundImageView.setOnClickListener(view -> {

                sportTypedataModalDataSetA = model;
                Runnable runnable = () ->
                        startActivity(new Intent(SportTypeRecyclerviewActivity.this,
                                ThisSportTypeRecyclerviewActivity.class));
                new Thread(runnable).start();

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

            public sportTypeRecyclerViewViewholder(@NonNull View itemView) {
                super(itemView);
                sportName = itemView.findViewById(R.id.sportname);
                sportbackgroundImageView = itemView.findViewById(R.id.sportbackgroundImageView);
                sportEngName = itemView.findViewById(R.id.sportengname);
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


}