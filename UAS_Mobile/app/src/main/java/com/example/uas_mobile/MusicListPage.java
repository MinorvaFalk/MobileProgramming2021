package com.example.uas_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MusicListPage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private final ArrayList<String> songList = new ArrayList<>();
    private int backpressed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        handleDialog();
        getAllSong();
        handleRecyclerView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.musiclist_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.show_profile:
                Intent intent = new Intent(MusicListPage.this, ProfilePage.class);
                intent.putExtra("from", "MusicListPage");
                startActivity(intent);
                break;
            case R.id.log_out:
                super.onBackPressed();
                break;
            default: return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(backpressed == 0){
            Toast.makeText(this, "Press the back button once again to exit", Toast.LENGTH_SHORT).show();
            new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    backpressed = 0;
                }
            }.start();
        }else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        backpressed++;
    }

    public void handleRecyclerView(){
        recyclerView = findViewById(R.id.recyclerView);
        myAdapter = new MyAdapter(this, songList);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void handleDialog(){
        PopUp popup = new PopUp();
        popup.show(getSupportFragmentManager(),"Welcome");
    }

    public void getAllSong(){
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, selection, null, null);

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    String songPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    songList.add(songPath);

                }while(cursor.moveToNext());
            }
        }
        cursor.close();
    }
}