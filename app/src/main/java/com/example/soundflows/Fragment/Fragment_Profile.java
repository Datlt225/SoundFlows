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
import com.example.soundflows.utils.AppPrefsUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

import java.util.Objects;

public class Fragment_Profile extends Fragment {
    View view;
    private Toolbar toolbar;
    private Users users = new Users();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        toolbar = view.findViewById(R.id.toolbar_profile);

//        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
//        toolbar.setTitle("Hi " + users.getName());
//        toolbar.setTitleTextColor(Color.WHITE);
//        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.profile_view, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();

                break;
        }
        return super.onOptionsItemSelected(item);
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
