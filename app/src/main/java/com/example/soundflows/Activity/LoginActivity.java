package com.example.soundflows.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.soundflows.R;
import com.example.soundflows.constant.UserConstant;
import com.example.soundflows.databinding.ActivityLoginBinding;
import com.example.soundflows.utils.AppPrefsUtils;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppPrefsUtils.createAppPrefs(this);

        if (isRememberUser(AppPrefsUtils.getString(UserConstant.KEY_REMEMBER_USER))) {
            showMainActivity();
        } else {

            initEvents();

            if (getIntent().hasExtra(UserConstant.KEY_GMAIL)
                    && getIntent().hasExtra(UserConstant.KEY_PASS)) {
                Intent intent = getIntent();

                binding.etLoginEmail.setText(intent.getStringExtra(UserConstant.KEY_GMAIL));

                binding.etLoginPassword.setText(intent.getStringExtra(UserConstant.KEY_PASS));
            }
        }
    }

    private void initEvents() {
        binding.btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this,
                        "Login successful",
                        Toast.LENGTH_SHORT).show();
                showMainActivity();
            }
        });

        binding.btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }

    private void showMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public Boolean isRememberUser(String isRememberTxt) {
        return TextUtils.equals("\"true\"", isRememberTxt);
    }
}