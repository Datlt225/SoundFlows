package com.example.soundflows.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.soundflows.constant.UserConstant;
import com.example.soundflows.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initEvents();
    }

    private void initEvents() {
        binding.btnRegisterRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegisterActivity.this,
                        "Register successful",
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getBaseContext(), LoginActivity.class);

//                intent.putExtra(UserConstant.KEY_GMAIL
//                        , user.getEmail());
//
//                intent.putExtra(UserConstant.KEY_PASS
//                        , user.getPassword());

                startActivity(intent);

                finish();
            }
        });

        binding.btnRegisterBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}