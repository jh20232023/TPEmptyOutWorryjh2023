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
            new AlertDialog.Builder(this).setMessage("비밀번호 확인에 문제가 있습니다. 다시 확인하여 입력해주시기 바랍니다.").create().show();
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
                    Toast.makeText(SignupActivity.this, "회원이 되신걸 축하합니다.", Toast.LENGTH_SHORT).show();
                    finish();

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(SignupActivity.this, "회원가입에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                }
            });











    }
}