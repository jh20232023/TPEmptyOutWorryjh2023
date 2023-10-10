package com.hunstory.tpemptyoutworryjh2023.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hunstory.tpemptyoutworryjh2023.R;
import com.hunstory.tpemptyoutworryjh2023.data.SelectedImageData;
import com.hunstory.tpemptyoutworryjh2023.databinding.RecyclerInFragmentStorylistBinding;

import java.util.ArrayList;

public class RecyclerForInFragmentAdapter extends RecyclerView.Adapter<RecyclerForInFragmentAdapter.VH> {
    Context context;
    ArrayList<String> uriList;

    public RecyclerForInFragmentAdapter(Context context, ArrayList<String> uriList) {
        this.context = context;
        this.uriList = uriList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_in_fragment_storylist,parent,false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        String imagePath = uriList.get(position);
        Glide.with(context).load(imagePath).into(holder.binding.iv);
    }

    @Override
    public int getItemCount() {
        return uriList.size();
    }


    class VH extends RecyclerView.ViewHolder {
        RecyclerInFragmentStorylistBinding binding;
        public VH(@NonNull View itemView) {
            super(itemView);
            binding=RecyclerInFragmentStorylistBinding.bind(itemView);
        }
    }
}
