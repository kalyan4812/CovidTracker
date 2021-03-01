package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    MyInterface response;
    RecyclerView listView;
    TextView lastupdated, confirmed, active, recover, decease;
   SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout=findViewById(R.id.swipeToRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getlist();
             //   swipeRefreshLayout.setRefreshing(false);
            }
        });
        lastupdated = findViewById(R.id.lastUpdatedTv);
        confirmed = findViewById(R.id.confirmedTv);
        active = findViewById(R.id.activeTv);
        recover = findViewById(R.id.recoveredTv);
        decease = findViewById(R.id.deceasedTv);
        listView = findViewById(R.id.recycler);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.hasFixedSize();

        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return null;
            }
        };
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.covid19india.org/").addConverterFactory(GsonConverterFactory.create()).
                client(okHttpClient).build();
        response = retrofit.create(MyInterface.class);
        getlist();
    }

    private void getlist() {
        Call<com.example.covidtracker.Response> call=response.getStatewise();
      call.enqueue(new Callback<com.example.covidtracker.Response>() {
          @Override
          public void onResponse(Call<com.example.covidtracker.Response> call, retrofit2.Response<com.example.covidtracker.Response> response) {
              Log.i("idd", response.body().toString());
              List<StatewiseItem> items=response.body().getStatewise();
              Log.i("idd", response.body().toString());
              Date d = new Date();
              SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" hh : mm  ");
              String time = simpleDateFormat.format(d);
              lastupdated.setText("LAST UPDATED \n " + time);
              confirmed.setText(items.get(0).getConfirmed());
              active.setText(items.get(0).getActive());
              recover.setText(items.get(0).getRecovered());
              decease.setText(items.get(0).getDeaths());
              listView.setAdapter(new MyAdapter(items, getApplicationContext()));
              swipeRefreshLayout.setRefreshing(false);
          }

          @Override
          public void onFailure(Call<com.example.covidtracker.Response> call, Throwable t) {

          }
      });
    }
    //  com.example.covidtracker.Response item = new Gson().fromJson(response.toString(), com.example.covidtracker.Response.class);
                  /* List<StatewiseItem> items=item.getStatewise();
                   Log.i("idd", response.body().toString());
                   Date d = new Date();
                   SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" hh : mm  ");
                   String time = simpleDateFormat.format(d);
                   lastupdated.setText("LAST UPDATED \n " + time);
                   confirmed.setText(items.get(0).getConfirmed());
                   active.setText(items.get(0).getActive());
                   recover.setText(items.get(0).getRecovered());
                   decease.setText(items.get(0).getDeaths());
                   listView.setAdapter(new MyAdapter(items, getApplicationContext()));*/
}
