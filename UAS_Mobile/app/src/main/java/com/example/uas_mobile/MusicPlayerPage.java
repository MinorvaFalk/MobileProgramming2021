package com.example.uas_mobile;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import java.io.IOException;

import java.util.ArrayList;

public class MusicPlayerPage extends AppCompatActivity {
    private String musicPath;
    private TextView songTitle;
    private SeekBar seekBar;
    private ImageView btnPlay, btnNext, btnPrev;

    private ArrayList<String> musicList;
    private int pos;

    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private Runnable runnable;

    private boolean state = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        songTitle = findViewById(R.id.songTitle);

        seekBar = findViewById(R.id.seekBar);
        btnPlay = findViewById(R.id.btnPlay);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);

        getData();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );

        setmediaPlayerSource(musicPath);
        playMusic();
        mediaPlayerHandler();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mediaPlayer.reset();
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        mediaPlayer.reset();
        super.onBackPressed();
    }

    public void getData(){
        Intent mainIntent = getIntent();
        musicList = mainIntent.getStringArrayListExtra("MusicList");
        pos = mainIntent.getIntExtra("MusicPos",0);

        musicPath = musicList.get(pos);
    }

    public void setmediaPlayerSource(String musicPath){
        songTitle.setText(musicPath.substring(musicPath.lastIndexOf("/") + 1));

        try {
            mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(musicPath));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mediaPlayerHandler(){
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state == false){
                    btnPlay.setImageResource(R.drawable.btn_pause);
                    mediaPlayer.start();
                    handler.postDelayed(runnable, 0);
                    state = true;
                }else {
                    btnPlay.setImageResource(R.drawable.btn_play);
                    mediaPlayer.pause();
                    handler.removeCallbacks(runnable);
                    state = false;
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.seekTo(0);
                if(pos == musicList.size()){
                    pos = 0;
                }else {
                    pos = pos + 1;
                }
                musicPath = musicList.get(pos);
                setmediaPlayerSource(musicPath);
                playMusic();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pos", String.valueOf(pos));
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.seekTo(0);
                if(pos == 0){
                    pos = musicList.size()-1;

                }else {
                    pos = pos - 1;
                }
                musicPath = musicList.get(pos);
                setmediaPlayerSource(musicPath);
                playMusic();
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btnPlay.setImageResource(R.drawable.btn_play);
                mediaPlayer.seekTo(0);
                state = false;
            }
        });
    }

    public void playMusic(){
        runnable = new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 500);
            }
        };

        seekBar.setMax(mediaPlayer.getDuration());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}