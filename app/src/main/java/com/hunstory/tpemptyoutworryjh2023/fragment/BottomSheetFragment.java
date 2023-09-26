package com.hunstory.tpemptyoutworryjh2023.fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

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

public class BottomSheetFragment extends BottomSheetDialogFragment {
    BottomsheetLayoutBinding binding;
    SQLiteDatabase db;
    String imageRealPath;
    Intent intent;
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
            if (result.getResultCode()==RESULT_OK) {
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
