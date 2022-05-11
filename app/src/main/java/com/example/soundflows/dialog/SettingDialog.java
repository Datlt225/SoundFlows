package com.example.soundflows.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.soundflows.Activity.LoginActivity;
import com.example.soundflows.Model.RegisterResponse;
import com.example.soundflows.Model.Users;
import com.example.soundflows.Services.APIService;
import com.example.soundflows.Services.Dataservice;
import com.example.soundflows.constant.UserConstant;
import com.example.soundflows.databinding.DialogEditInfoBinding;
import com.example.soundflows.utils.AppPrefsUtils;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingDialog extends DialogFragment {
    public static final String TAG = "SettingDialog";
    private DialogEditInfoBinding binding;
    private Users mUser;
    private Dataservice dataservice = APIService.getService();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUser = new Gson().fromJson(AppPrefsUtils.getString(UserConstant.KEY_USER_DATA), Users.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DialogEditInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Users users = new Users();
                    users.setEmail(mUser.getEmail());
                    users.setName(binding.etName.getText().toString().trim());
                    users.setPassword(binding.etPassWord.getText().toString().trim());



                    dataservice.ChangInformation(users).enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                            Log.d("dialog1", users.getEmail());
                            Log.d("dialog1", users.getName());
                            Log.d("dialog1", users.getPassword());

                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(),
                                        UserConstant.UPDATE_SUCCESSFUL,
                                        Toast.LENGTH_SHORT).show();

                                dismiss();

                                Intent intent = new Intent(getContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {
                            Log.e("dialog", t.getMessage());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
