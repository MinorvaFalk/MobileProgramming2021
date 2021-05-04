package com.example.uas_mobile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private final ArrayList<String> songList;
    private LayoutInflater mInflater;

    public MyAdapter(Context context, ArrayList<String> mSongList){
        mInflater = LayoutInflater.from(context);
        songList = mSongList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.songlist_item,
                parent, false);
        return new MyViewHolder(mItemView, this);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String title = songList.get(position);
        String mCurrent = title.substring(title.lastIndexOf("/") + 1);
        holder.musicItemView.setText(mCurrent);

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public final TextView musicItemView;
        final MyAdapter myAdapter;

        public MyViewHolder(@NonNull View itemView, MyAdapter mAdapter) {
            super(itemView);
            musicItemView = itemView.findViewById(R.id.song);
            this.myAdapter = mAdapter;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getLayoutPosition();

                    Context context = itemView.getContext();

                    Intent intent = new Intent(context, MusicPlayerPage.class);
                    intent.putStringArrayListExtra("MusicList", songList);
                    intent.putExtra("MusicPos", pos);
                    context.startActivity(intent);

                }
            });
        }
    }
}
