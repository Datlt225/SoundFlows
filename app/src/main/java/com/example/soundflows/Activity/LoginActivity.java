package com.example.soundflows.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.soundflows.Model.LoginResponse;
import com.example.soundflows.Model.Users;
import com.example.soundflows.Services.APIService;
import com.example.soundflows.Services.Dataservice;
import com.example.soundflows.constant.UserConstant;
import com.example.soundflows.databinding.ActivityLoginBinding;
import com.example.soundflows.utils.AppPrefsUtils;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppPrefsUtils.createAppPrefs(this);

        if (isRememberUser(AppPrefsUtils.getString(UserConstant.KEY_REMEMBER_USER))) {
            showMainActivity();
        } else {
            initEvent();

            if (getIntent().hasExtra(UserConstant.KEY_GMAIL) &&
                    getIntent().hasExtra(UserConstant.KEY_PASS)) {
                Intent intent = getIntent();

                binding.txtUserName.setText(intent.getStringExtra(UserConstant.KEY_GMAIL));

                binding.txtPassWord.setText(intent.getStringExtra(UserConstant.KEY_PASS));
            }
        }
    }

    private void showMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isRememberUser(String isRememberTxt) {
        return TextUtils.equals("\"true\"", isRememberTxt);
    }

    private void initEvent() {
        EditText[] ettArr = {binding.txtUserName, binding.txtPassWord};
        hideKeyboardOnFocusChange(ettArr);

        /**
         * Method handling button login
         */
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.txtUserName.getText().toString().trim();
                String password = binding.txtPassWord.getText().toString().trim();

                Users users = new Users(email, password);
                Dataservice dataservice = APIService.getService();
                dataservice.Login(users).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginResponse> call,
                                           @NonNull Response<LoginResponse> response) {

                        if (response.isSuccessful()){
                            AppPrefsUtils.putString(UserConstant.KEY_USER_DATA,
                                    new Gson().toJson(users));

                            if (binding.activityLoginChkRemember.isChecked()) {
                                AppPrefsUtils.putString(UserConstant.KEY_REMEMBER_USER
                                        , new Gson().toJson("true"));
                            } else {
                                AppPrefsUtils.putString(UserConstant.KEY_REMEMBER_USER
                                        , new Gson().toJson("false"));
                            }

                            showMainActivity();

                            Toast.makeText(getApplication(),
                                    response.body().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplication(),
                                    UserConstant.NOT_ALLOW_EMPTY,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(getApplication(),
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        /**
         * Method handling button register
         */
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void hideKeyboardOnFocusChange(EditText[] ettArr) {
        for (EditText et : ettArr) {
            et.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            });
        }
    }
}