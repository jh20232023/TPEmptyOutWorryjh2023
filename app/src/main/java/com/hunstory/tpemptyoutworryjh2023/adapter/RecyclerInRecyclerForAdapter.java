package com.hunstory.tpemptyoutworryjh2023.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hunstory.tpemptyoutworryjh2023.R;
import com.hunstory.tpemptyoutworryjh2023.data.NonMemberDatas;
import com.hunstory.tpemptyoutworryjh2023.databinding.RecyclerInFragmentStorylistBinding;
import com.hunstory.tpemptyoutworryjh2023.network.G;

import java.util.ArrayList;

public class RecyclerInRecyclerForAdapter extends RecyclerView.Adapter<RecyclerInRecyclerForAdapter.VH> {
    Context context;
    ArrayList<String> imgPath;

    public RecyclerInRecyclerForAdapter(Context context, ArrayList<String> imgPath) {
        this.context = context;
        this.imgPath = imgPath;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_in_fragment_storylist,parent,false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if (!G.email.equals("guest")){
        String path = "http://jh2023.dothome.co.kr/TPEmptyOutWorry" + imgPath.get(position);
        Glide.with(context).load(path).into(holder.binding.iv);
        } else if (G.email.equals("guest")) {
            String path = imgPath.get(position);
            Glide.with(context).load(path).override(200,200).into(holder.binding.iv);
        }
    }
    @Override
    public int getItemCount() {
        return imgPath.size();
    }




    class VH extends RecyclerView.ViewHolder {
        RecyclerInFragmentStorylistBinding binding;
        public VH(@NonNull View itemView) {
            super(itemView);
            binding=RecyclerInFragmentStorylistBinding.bind(itemView);
        }
    }
}
