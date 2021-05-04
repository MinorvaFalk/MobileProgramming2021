package com.example.uas_mobile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ProfilePage extends AppCompatActivity {
    private TextView link1, link2;
    private String prev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        link1 = findViewById(R.id.link1);
        link2 = findViewById(R.id.link2);

        link1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link1.getText().toString()));
                startActivity(browserIntent);
            }
        });
        link2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link2.getText().toString()));
                startActivity(browserIntent);
            }
        });
    }

    public Intent handlePrev(){
        Intent intent = null;
        prev = getIntent().getStringExtra("from");

        if(prev.equals("MusicListPage")){
            intent = new Intent(this, MusicListPage.class);

        }else {
            intent = new Intent(this, MainActivity.class);
        }

        //reuse prev activity
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }

    @Nullable
    @Override
    public Intent getParentActivityIntent() {
        return handlePrev();
    }

    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        return handlePrev();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}