package com.hunstory.tpemptyoutworryjh2023.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hunstory.tpemptyoutworryjh2023.R;
import com.hunstory.tpemptyoutworryjh2023.databinding.ActivityMainBinding;
import com.hunstory.tpemptyoutworryjh2023.fragment.BottomSheetFragment;
import com.hunstory.tpemptyoutworryjh2023.fragment.CalendarTabFragment;
import com.hunstory.tpemptyoutworryjh2023.fragment.SharedPostFragment;
import com.hunstory.tpemptyoutworryjh2023.fragment.StoryListFragment;
import com.hunstory.tpemptyoutworryjh2023.fragment.WorryDeleteFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

}