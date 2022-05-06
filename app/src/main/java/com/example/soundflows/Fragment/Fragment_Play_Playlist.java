package com.example.soundflows.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundflows.Activity.PlaySongActivity;
import com.example.soundflows.Adapter.PlaylistInPlayAdapter;
import com.example.soundflows.R;

public class Fragment_Play_Playlist extends Fragment {
    private View view;
    private RecyclerView rvPlaylist;
    private PlaylistInPlayAdapter playlistInPlayAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_playlist, container, false);
        rvPlaylist= view.findViewById(R.id.rv_play_playlist);
        if (PlaySongActivity.arrayListSong.size() > 0){

            playlistInPlayAdapter = new PlaylistInPlayAdapter(getActivity(),
                    PlaySongActivity.arrayListSong);

            rvPlaylist.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvPlaylist.setAdapter(playlistInPlayAdapter);
        }

        return view;
    }
}
