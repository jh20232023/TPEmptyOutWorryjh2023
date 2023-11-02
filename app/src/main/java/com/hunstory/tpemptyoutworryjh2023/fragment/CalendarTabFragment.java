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
    public String dateFilter = (currentYear - 2000) + "/" + currentMonth + "%";
    public ArrayList<CalendarData> list;

    CalendarAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
//        Bundle bundle = getArguments();
//        String s= bundle.getString("jjj");
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
        adapterCreate("noPost");
        adapter = new CalendarAdapter(getContext(),list);
        binding.recyclerGirdCalendar.setAdapter(adapter);

//        Bundle bundle = getArguments();
//        String s= bundle.getString("jjj");
//        Log.i("2",list.size()+"");
//        Gson gson = new Gson();
//        Log.i("3",list.size()+"");
//        ArrayList<String> list = gson.fromJson(s,new TypeToken<ArrayList<String>>() {}.getType());
//        Log.i("4",list.size()+"");
//        list.size();
//        Log.i("5",list.size()+"");




    }
//

    void listRemove(ArrayList list){
        list.remove(0);
    }

    void adapterCreate(String em){
        int maxDay = getDaysInMouth()+1;
         list = new ArrayList<>();
        for (int i=1;i<maxDay;i++){
            String dayOfWeek= getWhatDayWeek(i)+"";
            list.add(new CalendarData(dayOfWeek,i+"",em,dateFilter));
        }

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
        }
        adapterCreate("noPost");
        adapter = new CalendarAdapter(getContext(),list);
        binding.recyclerGirdCalendar.setAdapter(adapter);


    }
    void clickForward(){
        currentMonth++;
        binding.date.setText(currentYear+"/"+currentMonth);
        if (currentMonth==13) {
            currentMonth = 1;
            currentYear++;
            binding.date.setText(currentYear + "/" + currentMonth);
        }
        // list.clear();
        adapterCreate("noPost");
        adapter = new CalendarAdapter(getContext(),list);
        binding.recyclerGirdCalendar.setAdapter(adapter);


    }
}
