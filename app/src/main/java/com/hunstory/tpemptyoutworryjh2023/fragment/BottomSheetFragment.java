package com.hunstory.tpemptyoutworryjh2023.fragment;

import static android.app.Activity.RESULT_OK;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hunstory.tpemptyoutworryjh2023.R;
import com.hunstory.tpemptyoutworryjh2023.adapter.RecyclerForInFragmentAdapter;
import com.hunstory.tpemptyoutworryjh2023.data.MyDatabaseHelper;
import com.hunstory.tpemptyoutworryjh2023.data.SelectedImageData;
import com.hunstory.tpemptyoutworryjh2023.databinding.BottomsheetLayoutBinding;

import java.util.ArrayList;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    BottomsheetLayoutBinding binding;
     RecyclerForInFragmentAdapter adapter;
     ArrayList<String> uriList = new ArrayList<>();
    int isAnimating=1;
    AnimatorSet animatorSet = new AnimatorSet();
    MyDatabaseHelper myDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;
    int type = 3;
    ArrayList<SelectedImageData> selectedImageData = new ArrayList<>();
    int postingCount=0;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomsheetLayoutBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myDatabaseHelper = new MyDatabaseHelper(getContext());
        sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
        postingCount++;

        binding.toolbar.setNavigationOnClickListener(view1 -> {
            dismiss();
        });
        binding.etDate.setOnClickListener(view1 -> clickDate());
        binding.emoji.setOnClickListener(view1 -> emojiSelect());
        binding.ivSelectImg.setOnClickListener(view1 -> selectImg());
        binding.btnPosting.setOnClickListener(view1 -> {
                String date = binding.etDate.getText().toString();
                String title = binding.etTitle.getText().toString();
                String message = binding.etMessage.getText().toString();
                String emojiImg = type + "";


                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM fileImg",null);
                if (cursor.getCount()>0) {
                    cursor.moveToLast();
                    int cursorCount = cursor.getInt(0);
                    postingCount = postingCount + cursorCount;

                    String[] dates = new String[]{date, title, message, emojiImg};
                    sqLiteDatabase.execSQL("INSERT INTO member(date, title, message, em) VALUES(?,?,?,?)", dates);
                    for (int i=0; i<selectedImageData.size();i++) {
                        sqLiteDatabase.execSQL("INSERT INTO fileImg(num, filePath) VALUES('" +postingCount+ "','"+uriList.get(i)+"')");
                    } // for..
                    sqLiteDatabase.close();
                    dismiss();

                } else {
                    String[] dates = new String[]{date, title, message, emojiImg};
                    sqLiteDatabase.execSQL("INSERT INTO member(date, title, message, em) VALUES(?,?,?,?)", dates);
                    for (int i=0; i<selectedImageData.size();i++) {
                        sqLiteDatabase.execSQL("INSERT INTO fileImg(num, filePath) VALUES('" +postingCount+ "','"+uriList.get(i)+"')");
                    } // for..
                    sqLiteDatabase.close();
                    dismiss();
                } // else..
        });
        adapter = new RecyclerForInFragmentAdapter(getActivity(),uriList);
        binding.recyclerviewInBotoomsheet.setAdapter(adapter);
        binding.recyclerviewInBotoomsheet.setVisibility(View.VISIBLE);

    }
    void clickDate() {

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String selectDate = (year-2000) + "/" + (month + 1)+ "/" + day;
                binding.etDate.setText(selectDate);

            }
        });
        datePickerDialog.show();


    }
    void selectImg(){
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        intent.putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX,10);
        resultLauncher.launch(intent);


    }
    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode()==RESULT_OK) {
                Intent data = result.getData();
                ClipData clipData = result.getData().getClipData();
            int count = clipData.getItemCount();
            for (int i=0;i<count;i++) {
                ClipData.Item item = clipData.getItemAt(i);
                Uri uri = item.getUri();
                // 절대경로 uri
                String path = getImagePath(uri);
                uriList.add(path);
                selectedImageData.add(new SelectedImageData(path));
            }
                adapter.notifyDataSetChanged();
        }
    }
});
    void emojiSelect(){
        isAnimating++;
        if (isAnimating %2 == 0) {
            binding.smile.setVisibility(View.VISIBLE);
            binding.notbad.setVisibility(View.VISIBLE);
            binding.soso.setVisibility(View.VISIBLE);
            binding.sad.setVisibility(View.VISIBLE);
            binding.angry.setVisibility(View.VISIBLE);
            binding.ivBaloon.setVisibility(View.VISIBLE);
            // smile
            ObjectAnimator ani1= ObjectAnimator.ofFloat(binding.smile,"TranslationX",220f);
            // notBad
            ObjectAnimator ani2= ObjectAnimator.ofFloat(binding.notbad,"TranslationX",370f);
            // soso
            ObjectAnimator ani3= ObjectAnimator.ofFloat(binding.soso,"TranslationX",520f);
            // sad
            ObjectAnimator ani4= ObjectAnimator.ofFloat(binding.sad,"TranslationX",670f);
            // angry
            ObjectAnimator ani5 = ObjectAnimator.ofFloat(binding.angry,"TranslationX",820f);
            ani1.start();
            ani2.start();
            ani3.start();
            ani4.start();
            ani5.start();
            binding.etDate.setEnabled(false);
            binding.etTitle.setEnabled(false);

            binding.smile.setOnClickListener(view -> clickSmile());
            binding.notbad.setOnClickListener(view -> clickNotbad());
            binding.soso.setOnClickListener(view -> clickSoso());
            binding.sad.setOnClickListener(view -> clickSad());
            binding.angry.setOnClickListener(view -> clickAngry());

        } else if (isAnimating %2 == 1) {
            animatorSet.cancel();
            binding.smile.setVisibility(View.GONE);
            binding.notbad.setVisibility(View.GONE);
            binding.soso.setVisibility(View.GONE);
            binding.sad.setVisibility(View.GONE);
            binding.angry.setVisibility(View.GONE);
            binding.ivBaloon.setVisibility(View.GONE);

            binding.etDate.setEnabled(true);
            binding.etTitle.setEnabled(true);

            // smile
            ObjectAnimator ani1= ObjectAnimator.ofFloat(binding.smile,"TranslationX",0f);
            // notBad
            ObjectAnimator ani2= ObjectAnimator.ofFloat(binding.notbad,"TranslationX",0f);
            // soso
            ObjectAnimator ani3= ObjectAnimator.ofFloat(binding.soso,"TranslationX",0f);
            // sad
            ObjectAnimator ani4= ObjectAnimator.ofFloat(binding.sad,"TranslationX",0f);
            // angry
            ObjectAnimator ani5 = ObjectAnimator.ofFloat(binding.angry,"TranslationX",0f);
            ani1.start();
            ani2.start();
            ani3.start();
            ani4.start();
            ani5.start();
        }
    }

    private String getImagePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String imagePath = cursor.getString(columnIndex);
            cursor.close();
            return imagePath;
        }
        return null;
    }

    void clickSmile(){
        type = 1;
        isAnimating++;
        binding.emoji.setImageResource(R.drawable.smile);

        animatorSet.end();
        binding.smile.setVisibility(View.GONE);
        binding.notbad.setVisibility(View.GONE);
        binding.soso.setVisibility(View.GONE);
        binding.sad.setVisibility(View.GONE);
        binding.angry.setVisibility(View.GONE);
        binding.ivBaloon.setVisibility(View.GONE);

        binding.etDate.setEnabled(true);
        binding.etTitle.setEnabled(true);

        // smile
        ObjectAnimator ani1= ObjectAnimator.ofFloat(binding.smile,"TranslationX",0f);
        // notBad
        ObjectAnimator ani2= ObjectAnimator.ofFloat(binding.notbad,"TranslationX",0f);
        // soso
        ObjectAnimator ani3= ObjectAnimator.ofFloat(binding.soso,"TranslationX",0f);
        // sad
        ObjectAnimator ani4= ObjectAnimator.ofFloat(binding.sad,"TranslationX",0f);
        // angry
        ObjectAnimator ani5 = ObjectAnimator.ofFloat(binding.angry,"TranslationX",0f);
        ani1.start();
        ani2.start();
        ani3.start();
        ani4.start();
        ani5.start();
    }
    void clickSoso(){
        type =2;
        isAnimating++;
        binding.emoji.setImageResource(R.drawable.soso);

        animatorSet.end();
        binding.smile.setVisibility(View.GONE);
        binding.notbad.setVisibility(View.GONE);
        binding.soso.setVisibility(View.GONE);
        binding.sad.setVisibility(View.GONE);
        binding.angry.setVisibility(View.GONE);
        binding.ivBaloon.setVisibility(View.GONE);

        binding.etDate.setEnabled(true);
        binding.etTitle.setEnabled(true);

        // smile
        ObjectAnimator ani1= ObjectAnimator.ofFloat(binding.smile,"TranslationX",0f);
        // notBad
        ObjectAnimator ani2= ObjectAnimator.ofFloat(binding.notbad,"TranslationX",0f);
        // soso
        ObjectAnimator ani3= ObjectAnimator.ofFloat(binding.soso,"TranslationX",0f);
        // sad
        ObjectAnimator ani4= ObjectAnimator.ofFloat(binding.sad,"TranslationX",0f);
        // angry
        ObjectAnimator ani5 = ObjectAnimator.ofFloat(binding.angry,"TranslationX",0f);
        ani1.start();
        ani2.start();
        ani3.start();
        ani4.start();
        ani5.start();

    }
    void clickNotbad(){
        type = 3;
        isAnimating++;
        binding.emoji.setImageResource(R.drawable.notbad);

        animatorSet.end();
        binding.smile.setVisibility(View.GONE);
        binding.notbad.setVisibility(View.GONE);
        binding.soso.setVisibility(View.GONE);
        binding.sad.setVisibility(View.GONE);
        binding.angry.setVisibility(View.GONE);
        binding.ivBaloon.setVisibility(View.GONE);

        binding.etDate.setEnabled(true);
        binding.etTitle.setEnabled(true);

        // smile
        ObjectAnimator ani1= ObjectAnimator.ofFloat(binding.smile,"TranslationX",0f);
        // notBad
        ObjectAnimator ani2= ObjectAnimator.ofFloat(binding.notbad,"TranslationX",0f);
        // soso
        ObjectAnimator ani3= ObjectAnimator.ofFloat(binding.soso,"TranslationX",0f);
        // sad
        ObjectAnimator ani4= ObjectAnimator.ofFloat(binding.sad,"TranslationX",0f);
        // angry
        ObjectAnimator ani5 = ObjectAnimator.ofFloat(binding.angry,"TranslationX",0f);
        ani1.start();
        ani2.start();
        ani3.start();
        ani4.start();
        ani5.start();


    }
    void clickSad(){
        type = 4;
        isAnimating++;
        binding.emoji.setImageResource(R.drawable.sad);

        animatorSet.end();
        binding.smile.setVisibility(View.GONE);
        binding.notbad.setVisibility(View.GONE);
        binding.soso.setVisibility(View.GONE);
        binding.sad.setVisibility(View.GONE);
        binding.angry.setVisibility(View.GONE);
        binding.ivBaloon.setVisibility(View.GONE);

        binding.etDate.setEnabled(true);
        binding.etTitle.setEnabled(true);

        // smile
        ObjectAnimator ani1= ObjectAnimator.ofFloat(binding.smile,"TranslationX",0f);
        // notBad
        ObjectAnimator ani2= ObjectAnimator.ofFloat(binding.notbad,"TranslationX",0f);
        // soso
        ObjectAnimator ani3= ObjectAnimator.ofFloat(binding.soso,"TranslationX",0f);
        // sad
        ObjectAnimator ani4= ObjectAnimator.ofFloat(binding.sad,"TranslationX",0f);
        // angry
        ObjectAnimator ani5 = ObjectAnimator.ofFloat(binding.angry,"TranslationX",0f);
        ani1.start();
        ani2.start();
        ani3.start();
        ani4.start();
        ani5.start();

    }
    void clickAngry(){
        type = 5;
        isAnimating++;
        binding.emoji.setImageResource(R.drawable.angry);

        animatorSet.end();
        binding.smile.setVisibility(View.GONE);
        binding.notbad.setVisibility(View.GONE);
        binding.soso.setVisibility(View.GONE);
        binding.sad.setVisibility(View.GONE);
        binding.angry.setVisibility(View.GONE);
        binding.ivBaloon.setVisibility(View.GONE);

        binding.etDate.setEnabled(true);
        binding.etTitle.setEnabled(true);

        // smile
        ObjectAnimator ani1= ObjectAnimator.ofFloat(binding.smile,"TranslationX",0f);
        // notBad
        ObjectAnimator ani2= ObjectAnimator.ofFloat(binding.notbad,"TranslationX",0f);
        // soso
        ObjectAnimator ani3= ObjectAnimator.ofFloat(binding.soso,"TranslationX",0f);
        // sad
        ObjectAnimator ani4= ObjectAnimator.ofFloat(binding.sad,"TranslationX",0f);
        // angry
        ObjectAnimator ani5 = ObjectAnimator.ofFloat(binding.angry,"TranslationX",0f);
        ani1.start();
        ani2.start();
        ani3.start();
        ani4.start();
        ani5.start();

    }
}
