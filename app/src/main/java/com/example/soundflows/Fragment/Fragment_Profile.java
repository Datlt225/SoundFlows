package com.example.soundflows.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.soundflows.Activity.LoginActivity;
import com.example.soundflows.Model.Users;
import com.example.soundflows.R;
import com.example.soundflows.constant.UserConstant;
import com.example.soundflows.dialog.SettingDialog;
import com.example.soundflows.utils.AppPrefsUtils;
import com.example.soundflows.view.CommunicateViewModel;
import com.example.soundflows.view.ViewAnimation;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.Objects;

public class Fragment_Profile extends Fragment {
    View view;
    FloatingActionButton fabAdd, fabSetting, fabLogout;
    boolean isRotate = false;
    Toolbar toolbar;
    Users mUser;
    private CommunicateViewModel mCommunicateViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        // get information user from login
        mUser = new Gson().fromJson(AppPrefsUtils.getString(UserConstant.KEY_USER_DATA), Users.class);

        fabAdd = view.findViewById(R.id.fabAdd);
        fabSetting = view.findViewById(R.id.fab_setting);
        fabLogout = view.findViewById(R.id.fab_logout);

        toolbar = view.findViewById(R.id.toolbar_profile);
        toolbar.setTitle("Hi " + mUser.getName());

        ViewAnimation.init(fabSetting);
        ViewAnimation.init(fabLogout);

        eventClickFloatingButton();

        return view;
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
}
