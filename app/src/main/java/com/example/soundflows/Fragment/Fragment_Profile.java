package com.example.soundflows.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundflows.Activity.LoginActivity;
import com.example.soundflows.Activity.PlaySongActivity;
import com.example.soundflows.Activity.PlaylistActivity;
import com.example.soundflows.Adapter.FavoriteSongAdapter;
import com.example.soundflows.Adapter.SongAdapter;
import com.example.soundflows.Model.LikeSong;
import com.example.soundflows.Model.RegisterResponse;
import com.example.soundflows.Model.Song;
import com.example.soundflows.Model.Users;
import com.example.soundflows.R;
import com.example.soundflows.Services.APIService;
import com.example.soundflows.Services.Dataservice;
import com.example.soundflows.constant.UserConstant;
import com.example.soundflows.dialog.SettingDialog;
import com.example.soundflows.utils.AppPrefsUtils;
import com.example.soundflows.view.ViewAnimation;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Profile extends Fragment {
    View view;
    FloatingActionButton fabAdd, fabSetting, fabLogout;
    boolean isRotate = false;
    Toolbar toolbar;
    Users mUser;
    RecyclerView recyclerView;
    FavoriteSongAdapter favoriteSongAdapter;
    Dataservice dataservice = APIService.getService();
    ArrayList<Song> songArrayList;
    ImageButton imgPlayAllSong;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        getInfoLogin();

        recyclerView = view.findViewById(R.id.rv_favorite_song);
        fabAdd = view.findViewById(R.id.fabAdd);
        fabSetting = view.findViewById(R.id.fab_setting);
        fabLogout = view.findViewById(R.id.fab_logout);
        imgPlayAllSong = view.findViewById(R.id.img_play_all_fa_song);

        toolbar = view.findViewById(R.id.toolbar_profile);
        toolbar.setTitle("Hi " + mUser.getName());

        ViewAnimation.init(fabSetting);
        ViewAnimation.init(fabLogout);

        eventClickFloatingButton();
        getData();

        return view;
    }

    private void getInfoLogin() {
        mUser = new Gson().fromJson(AppPrefsUtils.getString(UserConstant.KEY_USER_DATA), Users.class);
    }

    private void getData() {
        Users users = new Users();
        users.setEmail(mUser.getEmail());

        Call<List<Song>> callback = dataservice.GetDataFavoriteSong(users);

        callback.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                if (response.body() != null){
                    songArrayList = (ArrayList<Song>) response.body();

//                    for (Song songcc : songArrayList)
//                        System.out.println("Link ne" + songcc.getLinkSong());

                    favoriteSongAdapter = new FavoriteSongAdapter(getActivity(), songArrayList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(favoriteSongAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Log.e("faSong", t.getMessage());
            }
        });
    }

    private void eventClickFloatingButton() {
        fabAdd.setOnClickListener(v -> {
            isRotate = ViewAnimation.rotateFab(v, !isRotate);
            if (isRotate) {
                ViewAnimation.showIn(fabSetting);
                ViewAnimation.showIn(fabLogout);
            } else {
                ViewAnimation.showOut(fabSetting);
                ViewAnimation.showOut(fabLogout);
            }
        });

        fabSetting.setOnClickListener(v -> {
            new SettingDialog().show(getChildFragmentManager(), SettingDialog.TAG);
        });

        fabLogout.setOnClickListener(v -> {
            logout();
        });

        imgPlayAllSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),
                        PlaySongActivity.class);
                intent.putExtra(UserConstant.KEY_ARRAY_SONGS, songArrayList);
                startActivity(intent);
            }
        });
    }

    private void logout() {
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(getContext());

        dialogBuilder.setMessage(R.string.confirm_logout)
                .setPositiveButton("Yes", (dialog, i) -> {
                    AppPrefsUtils.putString(UserConstant.KEY_USER_DATA, null);

                    AppPrefsUtils.putString(UserConstant.KEY_REMEMBER_USER,
                            new Gson().toJson("false"));

                    startActivity(new Intent(getContext(), LoginActivity.class));
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.RIGHT || direction == ItemTouchHelper.LEFT) {
                    deleteLike(viewHolder);
                }

            }
        }).attachToRecyclerView(recyclerView);
    }

    private void deleteLike(RecyclerView.ViewHolder viewHolder) {
        int position = viewHolder.getAdapterPosition();
        LikeSong song = new LikeSong();

        song.setEmail(mUser.getEmail());
        song.setIdSong(songArrayList.get(position).getIDSong());

        if (song != null){
            Log.d("Unlike", String.valueOf(song.getIdSong()));
        }

        new AlertDialog.Builder(getContext())
                .setTitle("Confirm Unlike!!")
                .setMessage("Do you really want to unlike this song?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    dataservice.UnLikeSong(song).enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call,
                                               Response<RegisterResponse> response) {

                            if (response.isSuccessful()) {

                                Toast.makeText(getContext(),
                                        UserConstant.UNLIKE,
                                        Toast.LENGTH_SHORT).show();

                                getData();
                            } else {
                                Toast.makeText(getContext(), "ko bo like dc", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {

                        }
                    });
                    getData();
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getData();
                    }
                }).show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
