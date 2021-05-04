package com.example.week11_33031;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvPostList;
    PostAdapter adapter;

    ArrayList<RetrofitModel> posts;

    NetInterface netInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvPostList = findViewById(R.id.rvPostsList);
        rvPostList.setLayoutManager(new LinearLayoutManager(this));

        netInterface = configRetro.getCliet().create(NetInterface.class);
        Call<ArrayList<RetrofitModel>> postModelCall = netInterface.getPosts();
        postModelCall.enqueue(new Callback<ArrayList<RetrofitModel>>() {
            @Override
            public void onResponse(Call<ArrayList<RetrofitModel>> call, Response<ArrayList<RetrofitModel>> response) {
                posts = response.body();
                adapter = new PostAdapter(posts);
                rvPostList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<RetrofitModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Internet not available", Toast.LENGTH_LONG).show();
            }
        });
    }
}