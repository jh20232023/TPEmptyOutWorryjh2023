package com.hunstory.tpemptyoutworryjh2023.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.GameManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hunstory.tpemptyoutworryjh2023.R;
import com.hunstory.tpemptyoutworryjh2023.databinding.FragmentWorryBinding;

public class WorryDeleteFragment extends Fragment {
    FragmentWorryBinding binding;
    ObjectAnimator animator;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWorryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.ivAngry.setOnClickListener(view1 -> clickIVAngry());
        binding.ivSad.setOnClickListener(view1 -> clickIVSad());
        binding.ivSick.setOnClickListener(view1 -> clickIVSick());
        binding.ivLongface.setOnClickListener(view1 -> clickIVLongface());
        binding.ivDespair.setOnClickListener(view1 -> clickIVDespair());

    }
    void clickIVAngry(){
        binding.ivEmoji.setImageResource(R.drawable.angry);
        binding.ivEmoji.setVisibility(View.VISIBLE);
        animator= ObjectAnimator.ofFloat(binding.ivEmoji,"translationY",  600f);
        animator.setDuration(150);
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(binding.ivImage,"translationX",0,-25f);
                animator1.setDuration(50).start();
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(binding.ivImage,"translationX",-25f,0);
                animator2.setDuration(50).start();
                binding.ivEmoji.setVisibility(View.GONE);
                ObjectAnimator.ofFloat(binding.ivEmoji,"translationY",-120f).start();
            }
        });
        animator.start();
    }
    void clickIVSad(){
        binding.ivEmoji.setImageResource(R.drawable.sad);
        binding.ivEmoji.setVisibility(View.VISIBLE);
        animator= ObjectAnimator.ofFloat(binding.ivEmoji,"translationY",  600f);
        animator.setDuration(150);
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(binding.ivImage,"translationX",0,-25f);
                animator1.setDuration(50).start();
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(binding.ivImage,"translationX",-25f,0);
                animator2.setDuration(50).start();
                binding.ivEmoji.setVisibility(View.GONE);
                ObjectAnimator.ofFloat(binding.ivEmoji,"translationY",-120f).start();
            }
        });
                animator.start();
    }
    void clickIVSick(){
        binding.ivEmoji.setImageResource(R.drawable.sosick);
        binding.ivEmoji.setVisibility(View.VISIBLE);
        animator= ObjectAnimator.ofFloat(binding.ivEmoji,"translationY",  600f);
        animator.setDuration(150);
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(binding.ivImage,"translationX",0,-25f);
                animator1.setDuration(50).start();
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(binding.ivImage,"translationX",-25f,0);
                animator2.setDuration(50).start();
                binding.ivEmoji.setVisibility(View.GONE);
                ObjectAnimator.ofFloat(binding.ivEmoji,"translationY",-120f).start();
            }
        });
        animator.start();
    }
    void clickIVLongface(){
        binding.ivEmoji.setImageResource(R.drawable.longface);
        binding.ivEmoji.setVisibility(View.VISIBLE);
        animator= ObjectAnimator.ofFloat(binding.ivEmoji,"translationY",  600f);
        animator.setDuration(150);
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(binding.ivImage,"translationX",0,-25f);
                animator1.setDuration(50).start();
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(binding.ivImage,"translationX",-25f,0);
                animator2.setDuration(50).start();
                binding.ivEmoji.setVisibility(View.GONE);
                ObjectAnimator.ofFloat(binding.ivEmoji,"translationY",-120f).start();
            }
        });
        animator.start();

    }
    void clickIVDespair(){
        binding.ivEmoji.setImageResource(R.drawable.despair_face);
        binding.ivEmoji.setVisibility(View.VISIBLE);
        animator= ObjectAnimator.ofFloat(binding.ivEmoji,"translationY",  600f);
        animator.setDuration(150);
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(binding.ivImage,"translationX",0,-25f);
                animator1.setDuration(50).start();
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(binding.ivImage,"translationX",-25f,0);
                animator2.setDuration(50).start();
                binding.ivEmoji.setVisibility(View.GONE);
                ObjectAnimator.ofFloat(binding.ivEmoji,"translationY",-120f).start();
            }
        });
        animator.start();

    }
}
