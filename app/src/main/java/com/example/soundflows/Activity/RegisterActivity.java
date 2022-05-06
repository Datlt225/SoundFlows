package com.example.soundflows.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.soundflows.Model.RegisterResponse;
import com.example.soundflows.Model.Users;
import com.example.soundflows.R;
import com.example.soundflows.Services.APIService;
import com.example.soundflows.Services.Dataservice;
import com.example.soundflows.constant.UserConstant;
import com.example.soundflows.databinding.ActivityRegisterBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initEvent();
    }

    private void initEvent() {
        /**
         * This method handling button create new user
         */
        binding.btnRegisterNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.txtEmail.getText().toString().trim();
                String password = binding.txtPassWord.getText().toString().trim();
                String confirmPass = binding.txtConfirmPassword.getText().toString().trim();
                String name = binding.txtUserName.getText().toString().trim();

                if (!password.equals(confirmPass)) {
                    Toast.makeText(getApplication(),
                            UserConstant.WRONG_CONFIRM_PASSWORD,
                            Toast.LENGTH_SHORT).show();

                } else {
                    Users user = new Users(email, password, name);

                    Dataservice dataservice = APIService.getService();
                    dataservice.Register(user).enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<RegisterResponse> call,
                                               @NonNull Response<RegisterResponse> response) {
                            if (response.isSuccessful()) {

                                Toast.makeText(getApplication(),
                                        response.body().getMessage(),
                                        Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);

                                intent.putExtra(UserConstant.KEY_GMAIL, user.getEmail());
                                intent.putExtra(UserConstant.KEY_PASS, user.getPassword());

                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(getApplication(),
                                        UserConstant.ACCOUNT_ALREADY_EXIST,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<RegisterResponse> call,
                                              @NonNull Throwable t) {
                            Log.d("Register", "Fail to get API: " + t.getMessage());
                        }
                    });
                }
            }
        });

        /**
         * This method handling button back to login interface
         */
        binding.btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}