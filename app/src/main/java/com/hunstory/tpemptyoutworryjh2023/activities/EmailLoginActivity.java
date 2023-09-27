package com.hunstory.tpemptyoutworryjh2023.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hunstory.tpemptyoutworryjh2023.R;
import com.hunstory.tpemptyoutworryjh2023.databinding.ActivityEmailLoginBinding;

public class EmailLoginActivity extends AppCompatActivity {
    ActivityEmailLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmailLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }
}