package com.hunstory.tpemptyoutworryjh2023.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hunstory.tpemptyoutworryjh2023.R;
import com.hunstory.tpemptyoutworryjh2023.data.NonMemberDatas;
import com.hunstory.tpemptyoutworryjh2023.databinding.CardviewStorylistBinding;
import com.hunstory.tpemptyoutworryjh2023.fragment.BottomSheetFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class StorylistCardViewAdapter extends RecyclerView.Adapter<StorylistCardViewAdapter.VH> {
    ArrayList<NonMemberDatas> nonMemberDatas;
    Context context;
    RecyclerInRecyclerForAdapter adapter;

    public StorylistCardViewAdapter(ArrayList<NonMemberDatas> nonMemberDatas, Context context) {
        this.nonMemberDatas = nonMemberDatas;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cardview_storylist,parent,false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        NonMemberDatas datas = nonMemberDatas.get(position);
        holder.binding.tvPostDate.setText("날짜 : "+datas.date);
        holder.binding.tvTitle.setText("제목 : "+datas.title);
        holder.binding.tvMessage.setText(datas.message);

        switch (datas.em){
            case 1+"" : Glide.with(context).load(R.drawable.smile).into(holder.binding.emoji);
            break;
            case 2+"" : Glide.with(context).load(R.drawable.notbad).into(holder.binding.emoji);
            break;
            case 3+"" : Glide.with(context).load(R.drawable.soso).into(holder.binding.emoji);
            break;
            case 4+"" : Glide.with(context).load(R.drawable.sad).into(holder.binding.emoji);
            break;
            case 5+"" : Glide.with(context).load(R.drawable.angry).into(holder.binding.emoji);
            break;
        }
        Log.i("dataPath",datas.imgPath.size()+"");
        adapter = new RecyclerInRecyclerForAdapter(context,datas.imgPath);
        holder.binding.recyclerviewStorylist.setAdapter(adapter);
        holder.binding.recyclerviewStorylist.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return nonMemberDatas.size();
    }


    class VH extends RecyclerView.ViewHolder{
        CardviewStorylistBinding binding;


        public VH(@NonNull View itemView) {
            super(itemView);
            binding = CardviewStorylistBinding.bind(itemView);
        }
    }
}

