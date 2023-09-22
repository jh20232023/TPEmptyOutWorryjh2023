package com.hunstory.tpemptyoutworryjh2023.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hunstory.tpemptyoutworryjh2023.databinding.CardviewStorylistBinding;

import java.util.ArrayList;

public class StorylistCardViewAdapter extends RecyclerView.Adapter<StorylistCardViewAdapter.VH> {
    Context context;

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    //    ArrayList

    class VH extends RecyclerView.ViewHolder{
        CardviewStorylistBinding binding;
        public VH(@NonNull View itemView) {

            super(itemView);
        }
    }
}
