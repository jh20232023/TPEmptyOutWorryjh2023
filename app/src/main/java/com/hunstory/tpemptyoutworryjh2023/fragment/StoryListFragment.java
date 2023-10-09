package com.hunstory.tpemptyoutworryjh2023.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hunstory.tpemptyoutworryjh2023.adapter.StorylistCardViewAdapter;
import com.hunstory.tpemptyoutworryjh2023.data.MyDatabaseHelper;
import com.hunstory.tpemptyoutworryjh2023.data.NonMemberDatas;
import com.hunstory.tpemptyoutworryjh2023.databinding.ActivityMainBinding;
import com.hunstory.tpemptyoutworryjh2023.databinding.BottomsheetLayoutBinding;
import com.hunstory.tpemptyoutworryjh2023.databinding.FragmentStroylistBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StoryListFragment extends Fragment {
    FragmentStroylistBinding binding;
    BottomSheetFragment bottomSheetFragment;
    StorylistCardViewAdapter adapter;
    MyDatabaseHelper myDatabaseHelper;
    SQLiteDatabase db;
    ArrayList<NonMemberDatas> nonMemberDatas = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStroylistBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM");
        String formattedDate = dateFormat.format(currentDate);
        binding.date.setText(formattedDate);
        binding.date.setOnClickListener(view1 -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
            datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    binding.ivBackArrow.setOnClickListener(view2 -> {
                        
                    });

                }
            });
        });




        myDatabaseHelper = new MyDatabaseHelper(getContext());
        db = myDatabaseHelper.getWritableDatabase();

//        db.execSQL("SELECT my_database.db FROM member()");

        binding.fab.setOnClickListener(view1 -> {clickFab();});
        adapter = new StorylistCardViewAdapter();
        binding.cardviewStorylist.setAdapter(adapter);


    }
    void clickFab(){
        bottomSheetFragment= new BottomSheetFragment();
        bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());

    }
}
