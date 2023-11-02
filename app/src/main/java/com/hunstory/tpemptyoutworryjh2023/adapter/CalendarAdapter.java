package com.hunstory.tpemptyoutworryjh2023.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hunstory.tpemptyoutworryjh2023.R;
import com.hunstory.tpemptyoutworryjh2023.data.CalendarData;
import com.hunstory.tpemptyoutworryjh2023.data.NonMemberDatas;
import com.hunstory.tpemptyoutworryjh2023.databinding.RecyclerCarlendarBinding;
import com.hunstory.tpemptyoutworryjh2023.network.G;
import com.hunstory.tpemptyoutworryjh2023.network.RetrofitHelper;
import com.hunstory.tpemptyoutworryjh2023.network.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.VH> {
    Context context;
    ArrayList<CalendarData> list;

    public CalendarAdapter(Context context, ArrayList<CalendarData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_carlendar,parent
        ,false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        CalendarData data = list.get(position);



        switch (data.dayOfWeek){
            case 1+"" : holder.binding.tvDay.setText("일요일");
            break;
            case 2+"" : holder.binding.tvDay.setText("월요일");
            break;
            case 3+"" : holder.binding.tvDay.setText("화요일");
            break;
            case 4+"" : holder.binding.tvDay.setText("수요일");
            break;
            case 5+"" : holder.binding.tvDay.setText("목요일");
            break;
            case 6+"" : holder.binding.tvDay.setText("금요일");
            break;
            case 7+"" : holder.binding.tvDay.setText("토요일");
            break;
        }



        switch (data.emotion){
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
            case "noPost" :holder.binding.emoji.setVisibility(View.GONE);
            break;
            }
            holder.binding.tv.setText(data.day);
            holder.binding.emoji.setImageResource(R.drawable.angry);

    }



    @Override
    public int getItemCount() {
        return list.size();
    }


    class VH extends RecyclerView.ViewHolder{
        RecyclerCarlendarBinding binding;

        public VH(@NonNull View itemView) {
            super(itemView);
            binding= RecyclerCarlendarBinding.bind(itemView);
        }
    }
}
