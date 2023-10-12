package com.hunstory.tpemptyoutworryjh2023.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.GameManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hunstory.tpemptyoutworryjh2023.R;
import com.hunstory.tpemptyoutworryjh2023.databinding.AlertdialogLayoutBinding;
import com.hunstory.tpemptyoutworryjh2023.databinding.FragmentWorryBinding;

public class WorryDeleteFragment extends Fragment {
    FragmentWorryBinding binding;
    ObjectAnimator animator;
    int emotionCount = 50;
    int angry, sad, sick, longFace,despair;
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
    void emotionCountZero(){
        if (emotionCount==0){
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.alertdialog_layout,null);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(dialogView);
            builder.setPositiveButton("확인",null);
            builder.setIcon(R.drawable.dialog);
            builder.setTitle("오늘 하루도 고생하셨어요!");
            builder.create().show();
            AlertdialogLayoutBinding alertdialogLayoutBinding= AlertdialogLayoutBinding.bind(dialogView);
            alertdialogLayoutBinding.tvAngryCount.setText("X"+angry);
            alertdialogLayoutBinding.tvSadCount.setText("X"+sad);
            alertdialogLayoutBinding.tvSickCount.setText("X"+sick);
            alertdialogLayoutBinding.tvLongfaceCount.setText("X"+longFace);
            alertdialogLayoutBinding.tvDespairCount.setText("X"+despair);
            emotionCount = 50;
            angry=0;
            sad=0;
            sick=0;
            longFace=0;
            despair=0;
        }
    }
    void clickIVAngry(){
        binding.tvEmotionCount.setVisibility(View.VISIBLE);
        emotionCount--;
        angry++;
        binding.tvEmotionCount.setText("남은 용량 : "+emotionCount);
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
        emotionCountZero();
    }
    void clickIVSad(){
        binding.tvEmotionCount.setVisibility(View.VISIBLE);
        emotionCount--;
        sad++;
        binding.tvEmotionCount.setText("남은 용량 : "+emotionCount);

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
        emotionCountZero();
    }
    void clickIVSick(){
        binding.tvEmotionCount.setVisibility(View.VISIBLE);
        emotionCount--;
        sick++;
        binding.tvEmotionCount.setText("남은 용량 : "+emotionCount);

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
        emotionCountZero();
    }
    void clickIVLongface(){
        binding.tvEmotionCount.setVisibility(View.VISIBLE);
        emotionCount--;
        longFace++;
        binding.tvEmotionCount.setText("남은 용량 : "+emotionCount);

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
        emotionCountZero();

    }
    void clickIVDespair(){
        binding.tvEmotionCount.setVisibility(View.VISIBLE);
        emotionCount--;
        despair++;
        binding.tvEmotionCount.setText("남은 용량 : "+emotionCount);

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
        emotionCountZero();

    }
}
