package com.hunstory.tpemptyoutworryjh2023.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hunstory.tpemptyoutworryjh2023.R;
import com.hunstory.tpemptyoutworryjh2023.activities.MainActivity;
import com.hunstory.tpemptyoutworryjh2023.adapter.RecyclerForInFragmentAdapter;
import com.hunstory.tpemptyoutworryjh2023.adapter.StorylistCardViewAdapter;
import com.hunstory.tpemptyoutworryjh2023.data.MyDatabaseHelper;
import com.hunstory.tpemptyoutworryjh2023.data.MyPicker;
import com.hunstory.tpemptyoutworryjh2023.data.NonMemberDatas;
import com.hunstory.tpemptyoutworryjh2023.databinding.ActivityMainBinding;
import com.hunstory.tpemptyoutworryjh2023.databinding.BottomsheetLayoutBinding;
import com.hunstory.tpemptyoutworryjh2023.databinding.FragmentStroylistBinding;
import com.hunstory.tpemptyoutworryjh2023.network.G;
import com.hunstory.tpemptyoutworryjh2023.network.RetrofitHelper;
import com.hunstory.tpemptyoutworryjh2023.network.RetrofitService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StoryListFragment extends Fragment {
    FragmentStroylistBinding binding;
    BottomSheetFragment bottomSheetFragment;
    StorylistCardViewAdapter adapter;
    SQLiteDatabase db;
    ArrayList<NonMemberDatas> nonMemberDatas = new ArrayList<>();
    int memberNum = 0;
    Calendar calendar = Calendar.getInstance();
    int currentYear = calendar.get(Calendar.YEAR);
    int currentMonth = calendar.get(Calendar.MONTH) + 1; // 월은 0부터 시작하므로 실제 월 값에 +1 해야합니다.
    String dateFilter = (currentYear-2000)+"/"+currentMonth+"%";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStroylistBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new StorylistCardViewAdapter(nonMemberDatas, getContext());
        binding.cardviewStorylist.setAdapter(adapter);
        binding.date.setText(currentYear+"/"+currentMonth);
        binding.ivBackArrow.setOnClickListener(view1 -> {
            currentMonth--;
            binding.date.setText(currentYear+"/"+currentMonth);
            if (currentMonth==0) {
                currentMonth=12;
                currentYear--;
                binding.date.setText(currentYear+"/"+currentMonth);
            }
                nonMemberDatas.clear();
                createDataBaseAndAdapter();
        });
        binding.ivForward.setOnClickListener(view1 -> {
            currentMonth++;
            binding.date.setText(currentYear+"/"+currentMonth);
            if (currentMonth==13) {
                currentMonth = 1;
                currentYear++;
                binding.date.setText(currentYear + "/" + currentMonth);
            }
                nonMemberDatas.clear();
                createDataBaseAndAdapter();

        });
        binding.date.setOnClickListener(view1 -> {
            MyPicker dialog = new MyPicker();
            dialog.setListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                binding.date.setText(year+"/"+month);
                currentMonth = month;
                currentYear = year;
                nonMemberDatas.clear();
                createDataBaseAndAdapter();

                }});
                dialog.show(getActivity().getSupportFragmentManager(), getTag());
            });

        binding.fab.setOnClickListener(view1 -> {clickFab();});
        createDataBaseAndAdapter();
    } // onViewCreated..





    void createDataBaseAndAdapter() {
        if (G.email.equals("guest")) {
            dateFilter = (currentYear - 2000) + "/" + currentMonth + "%";
            db = getActivity().openOrCreateDatabase("my_database.db", Context.MODE_PRIVATE, null);
            Cursor cursor = db.rawQuery("SELECT * FROM member WHERE date LIKE '" + dateFilter + "'", null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    memberNum = cursor.getInt(0);
                    Cursor fileImgCursor = db.rawQuery("SELECT * FROM fileImg WHERE num = '" + memberNum + "'", null);

                    String date = cursor.getString(1);
                    String title = cursor.getString(2);
                    String message = cursor.getString(3);
                    String em = cursor.getString(4);

                    NonMemberDatas datas = new NonMemberDatas(date, title, message, em, new ArrayList<String>());

                    if (fileImgCursor != null) {
                        while (fileImgCursor.moveToNext()) {
                            datas.imgPath.add(fileImgCursor.getString(1));
                        }
                    }
                    nonMemberDatas.add(datas);
                    adapter.notifyDataSetChanged();
                }// while..
            }//if..
        }// if is guest?
            if (!G.email.equals("guest")) {
                Retrofit retrofit = RetrofitHelper.getRetrofitInstance("http://jh2023.dothome.co.kr");
                RetrofitService retrofitService = retrofit.create(RetrofitService.class);
                retrofitService.loadDBSPL(dateFilter,G.email).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Gson gson = new Gson();
                        String s = gson.toJson(response);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getActivity(), "실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
    }// createDataBaseAndAdapter method..
    void clickFab(){
        bottomSheetFragment= new BottomSheetFragment();
        bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
    }
}
