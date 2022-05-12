package com.example.soundflows.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.example.soundflows.Model.RegisterResponse;
import com.example.soundflows.Model.Users;
import com.example.soundflows.R;
import com.example.soundflows.Services.APIService;
import com.example.soundflows.Services.Dataservice;
import com.example.soundflows.constant.LoginConstant;
import com.example.soundflows.constant.UserConstant;
import com.example.soundflows.databinding.ActivityRegisterBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private Dataservice dataservice = APIService.getService();

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
        binding.btnRegisterNew.setOnClickListener(v -> {

            Users user = new Users();

            if (binding.txtEmail.getText() != null)
                user.setEmail(binding.txtEmail.getText().toString().trim());

            if (binding.txtPassWord.getText() != null)
                user.setPassword(binding.txtPassWord.getText().toString().trim());

            if (binding.txtConfirmPassword.getText() != null){
                String confirmPass = binding.txtConfirmPassword.getText().toString().trim();
            }

            if (binding.txtUserName.getText() != null)
                user.setName(binding.txtUserName.getText().toString().trim());

            if (validateInput(user)) {
                dataservice.Register(user).enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful()) {
                            registerUI(response.body(), user);
                        }
                        else {
                            Toast.makeText(getApplication(),
                                    UserConstant.ACCOUNT_ALREADY_EXIST,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {

                    }
                });
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

    private void registerUI(RegisterResponse body, Users user) {
        Toast.makeText(this,
                getResources().getString(R.string.user_created),
                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getBaseContext(), LoginActivity.class);

        intent.putExtra(UserConstant.KEY_GMAIL, user.getEmail());
        intent.putExtra(UserConstant.KEY_PASS, user.getPassword());

        startActivity(intent);
        finish();
    }

    private boolean validateInput(Users user) {
        String passwordConfirm = "";
        if (binding.txtConfirmPassword.getText() != null) {
            passwordConfirm = binding.txtConfirmPassword.getText().toString().trim();
        }

        if (TextUtils.isEmpty(user.getEmail())) {
            binding.txtEmail.setError(getResources().getString(R.string.et_email_empty));
            binding.txtEmail.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(user.getPassword())) {
            binding.txtPassWord.setError(getResources().getString(R.string.et_pass_empty));
            binding.txtPassWord.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(user.getName())) {
            binding.txtUserName.setError(getResources().getString(R.string.et_lName_empty));
            binding.txtUserName.requestFocus();
            return false;
        }
        if (!user.getEmail().matches(LoginConstant.emailPattern)) {
            binding.txtEmail.setError(getResources().getString(R.string.et_email_invalid));
            binding.txtEmail.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(passwordConfirm) || !TextUtils.equals(user.getPassword(), passwordConfirm)) {
            binding.txtConfirmPassword.setError(getResources().getString(R.string.et_pwd_confirm_invalid));
            binding.txtConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }
}