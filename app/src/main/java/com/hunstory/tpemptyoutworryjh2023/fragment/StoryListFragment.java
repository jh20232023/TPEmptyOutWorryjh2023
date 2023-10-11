package com.hunstory.tpemptyoutworryjh2023.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
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
import com.hunstory.tpemptyoutworryjh2023.adapter.RecyclerForInFragmentAdapter;
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
    SQLiteDatabase db;
    ArrayList<NonMemberDatas> nonMemberDatas = new ArrayList<>();
    int memberNum = 0;



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
        binding.fab.setOnClickListener(view1 -> {clickFab();});
        db = getActivity().openOrCreateDatabase("my_database.db", Context.MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM member", null);


        if (cursor != null) {


            while (cursor.moveToNext()) {
                ArrayList<String> imgPathList = new ArrayList<>();
                memberNum++;
                Cursor fileImgCursor = db.rawQuery("SELECT * FROM fileImg WHERE num = '"+memberNum+"'",null);

                String date = cursor.getString(1);
                String title = cursor.getString(2);
                String message = cursor.getString(3);
                String em = cursor.getString(4);

                NonMemberDatas datas= new NonMemberDatas(date, title, message, em, new ArrayList<String>());

                if (fileImgCursor != null) {
                    while (fileImgCursor.moveToNext()) {
                        datas.imgPath.add(fileImgCursor.getString(1));
                    }
                }

                nonMemberDatas.add(datas);
                adapter.notifyDataSetChanged();
            }// while..
        } // if..

    }


    void clickFab(){
        bottomSheetFragment= new BottomSheetFragment();
        bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
    }
}
