package com.example.soundflows.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.soundflows.Model.LoginResponse;
import com.example.soundflows.Model.Users;
import com.example.soundflows.R;
import com.example.soundflows.Services.APIService;
import com.example.soundflows.Services.Dataservice;
import com.example.soundflows.constant.LoginConstant;
import com.example.soundflows.constant.UserConstant;
import com.example.soundflows.databinding.ActivityLoginBinding;
import com.example.soundflows.utils.AppPrefsUtils;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    Dataservice dataservice = APIService.getService();

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

        /**
         * Method handling button login
         */
        binding.btnLogin.setOnClickListener(v -> {
            Users mUser = new Users();
            if (binding.txtUserName.getText() != null)
                mUser.setEmail(binding.txtUserName.getText().toString().trim());
            if (binding.txtPassWord.getText() != null)
                mUser.setPassword(binding.txtPassWord.getText().toString().trim());

            if (validateInput(mUser)) {
                dataservice.Login(mUser).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            loginUI(response.body(), mUser);
                        }
                        else {
                            Toast.makeText(LoginActivity.this,
                                    UserConstant.WRONG_ACCOUNT,
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

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



    private boolean validateInput(Users mUser) {
        if (!mUser.getEmail().matches(LoginConstant.emailPattern)) {
            binding.txtUserName.setError(getResources().getString(R.string.et_email_invalid));
            binding.txtUserName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(mUser.getPassword())) {
            Toast.makeText(this, getResources().getString(R.string.et_pwd_invalid), Toast.LENGTH_LONG).show();
            binding.txtPassWord.requestFocus();
            return false;
        }

        return true;
    }

    private void loginUI(LoginResponse body, Users mUser) {
        mUser.setName(body.getName());

        AppPrefsUtils.putString(UserConstant.KEY_USER_DATA, new Gson().toJson(mUser));

        if (binding.activityLoginChkRemember.isChecked()) {
            AppPrefsUtils.putString(UserConstant.KEY_REMEMBER_USER
                    , new Gson().toJson("true"));
        } else {
            AppPrefsUtils.putString(UserConstant.KEY_REMEMBER_USER
                    , new Gson().toJson("false"));
        }

        Toast.makeText(getApplication(), "Welcome " + mUser.getName() , Toast.LENGTH_SHORT).show();

        showMainActivity();
    }
}