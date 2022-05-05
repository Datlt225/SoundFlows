package com.example.soundflows.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.soundflows.Model.Banner;
import com.example.soundflows.R;
import com.example.soundflows.databinding.ActivityPlaylistBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PlaylistActivity extends AppCompatActivity {
    private ActivityPlaylistBinding binding;
    private Banner banner;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private RecyclerView recyclerViewPlaylist;
    private FloatingActionButton fabPlayAllList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlaylistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DataIntent();
    }

    private void DataIntent() {
        Intent intent = getIntent();

        if (intent != null) {
            if (intent.hasExtra("banner")) {
                banner = (Banner) intent.getSerializableExtra("banner");
                Toast.makeText(this, banner.getNameSong(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}