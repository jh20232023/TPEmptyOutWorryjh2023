package com.hunstory.tpemptyoutworryjh2023.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.hunstory.tpemptyoutworryjh2023.R;
import com.hunstory.tpemptyoutworryjh2023.databinding.ActivityLoginBinding;
import com.hunstory.tpemptyoutworryjh2023.network.G;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvGuest.setOnClickListener(view -> clickGuestLogin());
        binding.tvLoginSignup.setOnClickListener(view -> clickMemberSignup());
        binding.tvEmail.setOnClickListener(view -> clickEmailLogin());




    }
    void clickEmailLogin(){
        Intent intent = new Intent(LoginActivity.this, EmailLoginActivity.class);
        startActivity(intent);

    }
    void clickMemberSignup(){
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);

    }
    void clickGuestLogin(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("게스트 로그인 이용시 주의사항");
        builder.setMessage("주의사항 : 게스트 로그인시 데이터는 디바이스 내부에 저장되며 (데이터 손실시 복구 불가) 일부 기능 사용에 제한될 수 있습니다.");
        builder.setIcon(R.drawable.baseline_warning_24).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                G.email = "guest";
                finish();
            }
        }).create().show();


    }
}