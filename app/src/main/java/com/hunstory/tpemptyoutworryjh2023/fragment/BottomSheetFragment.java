package com.hunstory.tpemptyoutworryjh2023.fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hunstory.tpemptyoutworryjh2023.R;
import com.hunstory.tpemptyoutworryjh2023.adapter.RecyclerForInFragmentAdapter;
import com.hunstory.tpemptyoutworryjh2023.data.MyDatabaseHelper;
import com.hunstory.tpemptyoutworryjh2023.data.SelectedImageData;
import com.hunstory.tpemptyoutworryjh2023.databinding.BottomsheetLayoutBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    BottomsheetLayoutBinding binding;
    Intent intent;
    String imageRealPath;
    Uri uri;
    // ArrayList<NonMemberDatas> memberDatas = new ArrayList<>();
     RecyclerForInFragmentAdapter adapter;
     ArrayList<SelectedImageData> selectedImageData= new ArrayList<>();
     ArrayList<String> uriList = new ArrayList<>();
    String pickImg;
    int isAnimating=1;
    AnimatorSet animatorSet = new AnimatorSet();
    MyDatabaseHelper myDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;


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

        binding.toolbar.setNavigationOnClickListener(view1 -> {
            dismiss();
        });
        binding.etDate.setOnClickListener(view1 -> clickDate());
        binding.emoji.setOnClickListener(view1 -> emojiSelect());
        binding.ivSelectImg.setOnClickListener(view1 -> selectImg());
        binding.btnPosting.setOnClickListener(view1 -> {
            String title = binding.etTitle.getText().toString();
            String message = binding.etMessage.getText().toString();
            String emojiImg= binding.emoji.toString();
            String date = binding.etDate.getText().toString();
            String[] dates= new String[]{date,title,message,emojiImg};

            sqLiteDatabase.execSQL("INSERT INTO member(date, title, message, em) VALUES(?,?,?,?)",dates);
            sqLiteDatabase.close();
            dismiss();

        });
        adapter = new RecyclerForInFragmentAdapter(getActivity(),selectedImageData);
        binding.recyclerviewInBotoomsheet.setAdapter(adapter);

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
                uriList.add(i,getImagePath(uri));
                selectedImageData.add(new SelectedImageData(uriList.get(i)));
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


    ArrayList<String> getRealPathFromUri(Uri uri){
        String [] projection = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.RELATIVE_PATH};
        String orderBy = MediaStore.Images.Media.DATE_MODIFIED;
        //
        Cursor cursor = getActivity().getContentResolver().query(uri , projection, null , null , orderBy + " DESC");
        //
        String absolutePathOfImage;
        ArrayList<String> ImagesList = new ArrayList<>();
        int columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        while (cursor.moveToNext()){
            absolutePathOfImage = cursor.getString(columnIndexData);
            ImagesList.add(absolutePathOfImage);

        }
        return ImagesList;
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
