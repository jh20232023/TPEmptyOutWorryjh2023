package com.hunstory.tpemptyoutworryjh2023.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.hunstory.tpemptyoutworryjh2023.R;
import com.hunstory.tpemptyoutworryjh2023.databinding.ActivitySignupBinding;
import com.hunstory.tpemptyoutworryjh2023.network.RetrofitHelper;
import com.hunstory.tpemptyoutworryjh2023.network.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnLogin.setOnClickListener(view -> clickBtnSignup());
        binding.toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }
    void clickBtnSignup(){
        String id = binding.inputLayoutId.getEditText().getText().toString();
        String pw = binding.inputLayoutPW.getEditText().getText().toString();;
        String pwConfirm = binding.inputLayoutPWConfirm.getEditText().getText().toString();
        String nickName =binding.inputLayoutNinkname.getEditText().getText().toString();

        if (!pw.equals(pwConfirm)) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다. 다시 확인해 주세요.", Toast.LENGTH_SHORT).show();
            binding.inputLayoutPWConfirm.getEditText().requestFocus();
            binding.inputLayoutPWConfirm.getEditText().selectAll();
            return;
        }
            Retrofit retrofit = RetrofitHelper.getRetrofitInstance("http://jh2023.dothome.co.kr");
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);
            Call<String> call = retrofitService.signUp(id,pw,nickName);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String s = response.body();
                    if (response.body().equals("성공")) {
                        Toast.makeText(SignupActivity.this, "회원이 되신걸 축하합니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (s.equals("id")){
                        Toast.makeText(SignupActivity.this, "중복된 id 가 이미 존재합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                    } else if (s.equals("nickName")) {
                        Toast.makeText(SignupActivity.this, "중복된 닉네임이 이미 존재합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(SignupActivity.this, "회원가입에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                }
            });











    }
}