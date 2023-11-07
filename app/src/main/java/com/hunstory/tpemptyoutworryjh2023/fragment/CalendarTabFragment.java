package com.hunstory.tpemptyoutworryjh2023.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hunstory.tpemptyoutworryjh2023.R;
import com.hunstory.tpemptyoutworryjh2023.adapter.CalendarAdapter;
import com.hunstory.tpemptyoutworryjh2023.data.CalendarData;
import com.hunstory.tpemptyoutworryjh2023.data.NonMemberDatas;
import com.hunstory.tpemptyoutworryjh2023.databinding.BottomsheetLayoutBinding;
import com.hunstory.tpemptyoutworryjh2023.databinding.FragmentCalendarBinding;
import com.hunstory.tpemptyoutworryjh2023.network.G;
import com.hunstory.tpemptyoutworryjh2023.network.RetrofitHelper;
import com.hunstory.tpemptyoutworryjh2023.network.RetrofitService;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CalendarTabFragment extends Fragment {
    FragmentCalendarBinding binding;
    public Calendar calendar = Calendar.getInstance();
    public int currentYear = calendar.get(Calendar.YEAR);
    public int currentMonth = calendar.get(Calendar.MONTH) + 1; // 월은 0부터 시작하므로 실제 월 값에 +1 해야합니다.
    public int devMonth = calendar.get(Calendar.MONTH);
    public String dateFilter = (currentYear - 2000) + "/" + currentMonth + "%";
    // int count=0;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Toast.makeText(getContext(), "기능 업데이트 예정입니다.", Toast.LENGTH_SHORT).show();
        binding.date.setText(currentYear + "/" + currentMonth);
        binding.ivBackArrow.setOnClickListener(view1 -> clickBack());
        binding.ivForward.setOnClickListener(view1 -> clickForward());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        binding.recyclerGirdCalendar.setLayoutManager(layoutManager);
        retrofit(dateFilter);





    }
    void retrofit(String dateFilter){
        // count++;
        // Toast.makeText(getActivity(), count+": ccc", Toast.LENGTH_SHORT).show();


        Retrofit retrofit = RetrofitHelper.getRetrofitInstance("http://jh2023.dothome.co.kr");
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        retrofitService.loadDBSPL(dateFilter,G.email).enqueue(new Callback<ArrayList<NonMemberDatas>>() {
            @Override
            public void onResponse(Call<ArrayList<NonMemberDatas>> call, Response<ArrayList<NonMemberDatas>> response) {
                G.stringArrayList = new ArrayList<>();
                if (response.body().size()!=0) {
//                    count++;
//                    Toast.makeText(getActivity(), count+": aaa", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < response.body().size(); i++) {
                        G.stringArrayList.add(new NonMemberDatas(
                                response.body().get(i).date,
                                response.body().get(i).title,
                                response.body().get(i).message,
                                response.body().get(i).em,
                                new ArrayList<String>()
                        ));
                        adapterCreate("noPost");
                        ArrayList<CalendarData> calendarDataList = adapterCreate("noPost");
                        CalendarAdapter adapter = new CalendarAdapter(getContext(), calendarDataList);
                        binding.recyclerGirdCalendar.setAdapter(adapter);
                    }
                } else {
//                    count++;
//                    Toast.makeText(getActivity(), count+": bbb", Toast.LENGTH_SHORT).show();
                    calendarCreate();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<NonMemberDatas>> call, Throwable t) {
//                count++;
//                Toast.makeText(getActivity(), count+": ddd", Toast.LENGTH_SHORT).show();
//                Log.e("Retrofit", "onFailure: " + t.getMessage()); // 추가
                calendarCreate();

            }
        });
    }
    void calendarCreate(){
        // count++;
        // Toast.makeText(getActivity(), count+"", Toast.LENGTH_SHORT).show();
        ArrayList<CalendarData> list;
        int maxDay = getDaysInMouth()+1;
        list = new ArrayList<>();
        list.add(new CalendarData());

        for (int i = 1; i < maxDay; i++) {
            String dayOfWeek = getWhatDayWeek(i) + "";
            list.add(new CalendarData(dayOfWeek, i + "", "noPost"));
        }
        list.remove(0);
        CalendarAdapter adapter = new CalendarAdapter(getContext(), list);
        binding.recyclerGirdCalendar.setAdapter(adapter);
    }

    ArrayList<CalendarData> adapterCreate(String em){
        ArrayList<CalendarData> list;
        int maxDay = getDaysInMouth()+1;
         list = new ArrayList<>();
         list.add(new CalendarData());
         if (G.stringArrayList.isEmpty()==false) {
             for (int i = 1; i < maxDay; i++) {
                 String dayOfWeek = getWhatDayWeek(i) + "";
                 list.add(new CalendarData(dayOfWeek, i + "", em));
             }
             for (int k = 0; k < G.stringArrayList.size(); k++) {
                 String[] s = G.stringArrayList.get(k).date.split("/");
                 int parseS = Integer.parseInt(s[2]);
                 list.set(parseS, new CalendarData(getWhatDayWeek(parseS) + "", s[2], G.stringArrayList.get(k).em));
             }
         } else {
             for (int i = 1; i < maxDay; i++) {
                 String dayOfWeek = getWhatDayWeek(i) + "";
                 list.add(new CalendarData(dayOfWeek, i + "", em));

             }
         }
        list.remove(0);
        return new ArrayList<CalendarData>(list);


    }
    int getWhatDayWeek(int date){
        calendar.set(currentYear,currentMonth-1,date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    int getDaysInMouth(){
        calendar.set(currentYear,currentMonth-1,1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    void clickBack(){
        currentMonth--;
        binding.date.setText(currentYear+"/"+currentMonth);
        if (currentMonth==0) {
            currentMonth=12;
            currentYear--;
            binding.date.setText(currentYear+"/"+currentMonth);
            dateFilter = (currentYear - 2000) + "/" + currentMonth + "%";
            retrofit(dateFilter);
        }
        dateFilter = (currentYear - 2000) + "/" + currentMonth + "%";
        retrofit(dateFilter);


    }
    void clickForward(){
        currentMonth++;
        binding.date.setText(currentYear+"/"+currentMonth);
        if (currentMonth==13) {
            currentMonth = 1;
            currentYear++;
            binding.date.setText(currentYear + "/" + currentMonth);
            dateFilter = (currentYear - 2000) + "/" + currentMonth + "%";
            retrofit(dateFilter);
        }
        dateFilter = (currentYear - 2000) + "/" + currentMonth + "%";
        retrofit(dateFilter);




    }
}
