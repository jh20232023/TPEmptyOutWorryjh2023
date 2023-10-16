package com.hunstory.tpemptyoutworryjh2023.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.hunstory.tpemptyoutworryjh2023.R;
import com.hunstory.tpemptyoutworryjh2023.databinding.ActivityEmailLoginBinding;
import com.hunstory.tpemptyoutworryjh2023.network.G;
import com.hunstory.tpemptyoutworryjh2023.network.RetrofitHelper;
import com.hunstory.tpemptyoutworryjh2023.network.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EmailLoginActivity extends AppCompatActivity {
    ActivityEmailLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmailLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnLogin.setOnClickListener(view -> clickBtnLogin());

        binding.toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }
    void clickBtnLogin(){
        String id = binding.inputLayoutId.getEditText().getText().toString();
        String pw = binding.inputLayoutPW.getEditText().getText().toString();

        Retrofit retrofit = RetrofitHelper.getRetrofitInstance("http://jh2023.dothome.co.kr/");
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        retrofitService.signIn(id,pw).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String s = response.body();
                AlertDialog.Builder builder = new AlertDialog.Builder(EmailLoginActivity.this);
                builder.setMessage(s).create().show();
                if (s.equals("true")){
                    G.email = id;
                    Intent intent = new Intent(EmailLoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);


                }else {
                    Toast.makeText(EmailLoginActivity.this, "입력한 정보가 일치하지 않아 로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    binding.inputLayoutId.getEditText().requestFocus();
                    binding.inputLayoutId.getEditText().selectAll();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(EmailLoginActivity.this, "네트워크 통신에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();


            }
        });


    }
}