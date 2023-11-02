package com.hunstory.tpemptyoutworryjh2023.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.hunstory.tpemptyoutworryjh2023.R;
import com.hunstory.tpemptyoutworryjh2023.data.NonMemberDatas;
import com.hunstory.tpemptyoutworryjh2023.databinding.ActivityMainBinding;
import com.hunstory.tpemptyoutworryjh2023.fragment.BottomSheetFragment;
import com.hunstory.tpemptyoutworryjh2023.fragment.CalendarTabFragment;
import com.hunstory.tpemptyoutworryjh2023.fragment.SharedPostFragment;
import com.hunstory.tpemptyoutworryjh2023.fragment.StoryListFragment;
import com.hunstory.tpemptyoutworryjh2023.fragment.WorryDeleteFragment;
import com.hunstory.tpemptyoutworryjh2023.network.G;
import com.hunstory.tpemptyoutworryjh2023.network.RetrofitHelper;
import com.hunstory.tpemptyoutworryjh2023.network.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    SQLiteDatabase db;
    CalendarTabFragment calendarTabFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        calendarTabFragment = new CalendarTabFragment();


        if (G.email.equals("guest")) {
            db = openOrCreateDatabase("my_database.db", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS member(num INTEGER PRIMARY KEY AUTOINCREMENT, date STRING NOT NULL, title VARCHAR(20) NOT NULL, message TEXT, em STRING)");
            db.execSQL("CREATE TABLE IF NOT EXISTS fileImg(num INTEGER , filePath STRING)");
            db.close();
        } else{
            Retrofit retrofit = RetrofitHelper.getRetrofitInstance("http://jh2023.dothome.co.kr");
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);
            retrofitService.loadDBSPL(calendarTabFragment.dateFilter,G.email).enqueue(new Callback<ArrayList<NonMemberDatas>>() {
                @Override
                public void onResponse(Call<ArrayList<NonMemberDatas>> call, Response<ArrayList<NonMemberDatas>> response) {
                    ArrayList list = new ArrayList<>();
                    Gson gson = new Gson();
                    for (int i=0;i<response.body().size();i++){
                        String[] s = response.body().get(i).date.split("/");
                        list.add(s[2]);
                    }
                    String json =gson.toJson(list);
                    calendarTabFragment.setArguments(jjj(json));

                }

                @Override
                public void onFailure(Call<ArrayList<NonMemberDatas>> call, Throwable t) {

                }
            });

        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new StoryListFragment()).commit();
        binding.bnv.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id== R.id.storylist) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new StoryListFragment()).commit();
            } else if (id == R.id.calendar) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new CalendarTabFragment()).commit();
            } else if (id == R.id.sharedpost) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new SharedPostFragment()).commit();
            } else if (id == R.id.worrydelete) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new WorryDeleteFragment()).commit();
            }
            return true;
        });


    }
    Bundle jjj(String json){
        Bundle bundle = new Bundle();
        bundle.putString("jjj",json);
        return bundle;
    }

}