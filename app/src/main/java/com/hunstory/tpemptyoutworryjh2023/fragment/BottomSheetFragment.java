package com.hunstory.tpemptyoutworryjh2023.fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hunstory.tpemptyoutworryjh2023.R;
import com.hunstory.tpemptyoutworryjh2023.adapter.RecyclerForInFragmentAdapter;
import com.hunstory.tpemptyoutworryjh2023.data.SelectedImageData;
import com.hunstory.tpemptyoutworryjh2023.databinding.BottomsheetLayoutBinding;

import java.util.ArrayList;
import java.util.Objects;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    BottomsheetLayoutBinding binding;
    boolean isAnimating= false;
    SQLiteDatabase db;
    Intent intent;
    String imageRealPath;
    Uri uri;
    // ArrayList<NonMemberDatas> memberDatas = new ArrayList<>();
     RecyclerForInFragmentAdapter adapter;
     ArrayList<SelectedImageData> selectedImageData= new ArrayList<>();
     ArrayList<String> uriList;
    String pickImg;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomsheetLayoutBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.emoji.setOnClickListener(view1 -> emojiSelect());
        binding.ivSelectImg.setOnClickListener(view1 -> selectImg());
        binding.btnPosting.setOnClickListener(view1 -> {
            String title = binding.etTitle.getText().toString();
            String message = binding.etMessage.getText().toString();
            String emojiImg= binding.emoji.toString();
            String date = binding.etDate.getText().toString();
            String[] dates= new String[]{title,message,emojiImg,date};
            db.execSQL("INSERT INTO nonmember(date, title, message, em) VALUES(?,?,?,?)",dates);




        });
        adapter = new RecyclerForInFragmentAdapter(getActivity(),selectedImageData);
        binding.recyclerviewInBotoomsheet.setAdapter(adapter);
    }
    void selectImg(){
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        intent.putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX,10);
        resultLauncher.launch(intent);


    }
    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode()==RESULT_OK && intent != null) {
            intent = result.getData();
            ClipData clipData = intent.getClipData();
            int count = clipData.getItemCount();
            for (int i=0;i<count;i++) {
                // imageRealPath = getRealPathFromUri(uri);
                Uri uri = clipData.getItemAt(i).getUri();
                uriList = getRealPathFromUri(uri);
                selectedImageData.add(new SelectedImageData());


                    db.execSQL("INSERT INTO fileImg(filePath) VALUES('" + pickImg + "')");


            }
                adapter.notifyDataSetChanged();
        }


        }
    });
    void emojiSelect(){
        if (binding.ivBaloon.getVisibility()==View.VISIBLE){
            binding.smile.setVisibility(View.GONE);
            binding.notbad.setVisibility(View.GONE);
            binding.soso.setVisibility(View.GONE);
            binding.sad.setVisibility(View.GONE);
            binding.angry.setVisibility(View.GONE);
            binding.ivBaloon.setVisibility(View.GONE);
        } else if (isAnimating) {
            
        }
        binding.smile.setVisibility(View.VISIBLE);
        binding.notbad.setVisibility(View.VISIBLE);
        binding.soso.setVisibility(View.VISIBLE);
        binding.sad.setVisibility(View.VISIBLE);
        binding.angry.setVisibility(View.VISIBLE);
        binding.ivBaloon.setVisibility(View.VISIBLE);
        // smile

        ObjectAnimator.ofFloat(binding.smile,"TranslationX",220f).addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                binding.smile.setVisibility(View.GONE);
                isAnimating = false;
            }
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
            }
        });
        // notBad
        ObjectAnimator.ofFloat(binding.notbad,"TranslationX",370f).start();
        // soso
        ObjectAnimator.ofFloat(binding.soso,"TranslationX",520f).start();
        // sad
        ObjectAnimator.ofFloat(binding.sad,"TranslationX",670f).start();
        // angry
        ObjectAnimator.ofFloat(binding.angry,"TranslationX",820f).start();






    }
    ArrayList<String> getRealPathFromUri(Uri uri){
        String [] projection = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.RELATIVE_PATH};
        String orderBy = MediaStore.Images.Media.DATE_MODIFIED;
        Cursor cursor = getActivity().getContentResolver().query(uri , projection, null , null , orderBy + " DESC");
        String absolutePathOfImage;
        ArrayList<String> ImagesList = new ArrayList<>();
        int columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        while (cursor.moveToNext()){
            absolutePathOfImage = cursor.getString(columnIndexData);
            ImagesList.add(absolutePathOfImage);

        }
        return ImagesList;
    }



}
