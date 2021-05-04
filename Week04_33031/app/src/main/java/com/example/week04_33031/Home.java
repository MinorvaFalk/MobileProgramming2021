package com.example.week04_33031;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Home extends AppCompatActivity {
    private Button halaman_1, halaman_2, halaman_3;
    private EditText nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        halaman_1 = findViewById(R.id.main_button_change1);
        halaman_2 = findViewById(R.id.main_button_change2);
        halaman_3 = findViewById(R.id.main_button_change3);
        nama = findViewById(R.id.nama);

        halaman_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, MainActivity.class);
                startActivity(intent);
            }
        });
        halaman_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        halaman_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, ThirdActivity.class);
                startActivity(intent);
            }
        });

    }
}