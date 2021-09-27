package com.example.myapplication_MeetSport;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Apilnterface {
    @GET("place/queryautocomplete/json")

    Call<GoogleMapSystemMainActivity.MainPojo> getplace(@Query("input") String text, @Query("key") String key);
}
