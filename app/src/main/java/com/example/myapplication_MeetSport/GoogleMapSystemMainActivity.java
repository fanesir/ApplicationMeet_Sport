package com.example.myapplication_MeetSport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GoogleMapSystemMainActivity extends AppCompatActivity {
    Apilnterface apilnterface;
    RelativeLayout relativeLayout;
    RecyclerView suchRecyclerview;
    static int whenUseGoogleMapBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map_system_main);


        EditText sucheditText = findViewById(R.id.editTextTextPersonName2);
        suchRecyclerview = findViewById(R.id.suchrecyclerview);
        suchRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        relativeLayout = findViewById(R.id.notdata_found);

        relativeLayout.setVisibility(View.VISIBLE);
        suchRecyclerview.setVisibility(View.VISIBLE);


        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://maps.googleapis.com/maps/api/")
                .build();
        apilnterface = retrofit.create(Apilnterface.class);

        sucheditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getData(editable.toString());

            }
        });

    }

    public class RecyclerViewAdpter extends RecyclerView.Adapter<RecyclerViewAdpter.Viewholder> {
        ArrayList<Listclass> list;


        public RecyclerViewAdpter(ArrayList<Listclass> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerViewAdpter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.googlemap_rv_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull Viewholder holder, @SuppressLint("RecyclerView") int position) {


            holder.textView.setText(list.get(position).getDescription().replace(",", "\n"));

            holder.textView.setOnClickListener(view -> {

                if (sportInfoMainActivity.SPORT_ACTIVITY_INFO_NUMBER == 98) {
                    DataBasedirector.aboutInfoSportDataSet.setMap(list.get(position).getDescription() + "");
                    DataBasedirector.aboutInfoSportDataSet.setMapid(list.get(position).getPlace_id() + "");
                } else {
                    AddAndEditSportActivity.AboutInfoSportDataSet.setMap(list.get(position).getDescription() + "");
                    AddAndEditSportActivity.AboutInfoSportDataSet.setMapid(list.get(position).getPlace_id() + "");
                    whenUseGoogleMapBack = 99;
                }


                startActivity(new Intent(GoogleMapSystemMainActivity.this, AddAndEditSportActivity.class));
                finish();

            });
        }

        @Override
        public int getItemCount() {

            return list.size();

        }

        class Viewholder extends RecyclerView.ViewHolder {
            TextView textView;

            public Viewholder(View view) {
                super(view);
                textView = view.findViewById(R.id.textView4);
            }
        }
    }


    public void getData(String text) {
        apilnterface.getplace(text, getString(R.string.api_key)).enqueue(new Callback<MainPojo>() {
            @Override
            public void onResponse(Call<MainPojo> call, Response<MainPojo> response) {
                if (response.isSuccessful()) {
                    relativeLayout.setVisibility(View.GONE);
                    suchRecyclerview.setVisibility(View.VISIBLE);


                    RecyclerViewAdpter recyclerViewAdpter = new RecyclerViewAdpter(response.body().getPredictions());//response.body().getPredictions()
                    Log.i("Map_Json_Message", response + "");
                    suchRecyclerview.setAdapter(recyclerViewAdpter);


                } else {
                    relativeLayout.setVisibility(View.VISIBLE);
                    suchRecyclerview.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MainPojo> call, Throwable t) {

                relativeLayout.setVisibility(View.VISIBLE);
                suchRecyclerview.setVisibility(View.GONE);
                Toast.makeText(GoogleMapSystemMainActivity.this, "Error XD", Toast.LENGTH_LONG).show();
            }
        });
    }


    public class MainPojo {
        String status;
        ArrayList<Listclass> predictions;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public ArrayList<Listclass> getPredictions() {
            return predictions;
        }

        public void setPredictions(ArrayList<Listclass> predictions) {
            this.predictions = predictions;
        }
    }

    public class Listclass {
        private String description;
        private String place_id;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

    }

    public void onBackPressed() {
        AddAndEditSportActivity.AboutInfoSportDataSet.setMap(getString(R.string.when_Map_Info_Null));
        whenUseGoogleMapBack = 99;
        startActivity(new Intent(GoogleMapSystemMainActivity.this, AddAndEditSportActivity.class));
        finish();
    }

}