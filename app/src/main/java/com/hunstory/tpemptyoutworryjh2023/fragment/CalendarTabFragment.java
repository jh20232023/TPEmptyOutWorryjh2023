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

import com.hunstory.tpemptyoutworryjh2023.R;
import com.hunstory.tpemptyoutworryjh2023.adapter.CalendarAdapter;
import com.hunstory.tpemptyoutworryjh2023.data.CalendarData;
import com.hunstory.tpemptyoutworryjh2023.databinding.BottomsheetLayoutBinding;
import com.hunstory.tpemptyoutworryjh2023.databinding.FragmentCalendarBinding;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarTabFragment extends Fragment {
    FragmentCalendarBinding binding;
    Calendar calendar = Calendar.getInstance();
    int currentYear = calendar.get(Calendar.YEAR);
    int currentMonth = calendar.get(Calendar.MONTH) + 1; // 월은 0부터 시작하므로 실제 월 값에 +1 해야합니다.
    String dateFilter = (currentYear-2000)+"/"+currentMonth+"%";
    ArrayList<CalendarData> list = new ArrayList<>();
    CalendarAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Toast.makeText(getContext(), "기능 업데이트 예정입니다.", Toast.LENGTH_SHORT).show();
        binding.date.setText(currentYear +"/"+currentMonth);
        binding.ivBackArrow.setOnClickListener(view1 -> clickBack());
        binding.ivForward.setOnClickListener(view1 -> clickForward());


        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        binding.recyclerGirdCalendar.setLayoutManager(layoutManager);

        int maxDay = getDaysInMouth();
        for (int i=1;i<maxDay;i++){
            String dayOfWeek= getWhatDayWeek(i)+"";
            list.add(new CalendarData(dayOfWeek,i+"",null));
            Log.i("size",maxDay+"");
        }
        adapter = new CalendarAdapter(getContext(),list);
        binding.recyclerGirdCalendar.setAdapter(adapter);

    }
    int getWhatDayWeek(int date){
        calendar.set(currentYear,currentMonth,date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    int getDaysInMouth(){
        calendar.set(currentYear,currentMonth,1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    void clickBack(){

    }
    void clickForward(){

    }
}
