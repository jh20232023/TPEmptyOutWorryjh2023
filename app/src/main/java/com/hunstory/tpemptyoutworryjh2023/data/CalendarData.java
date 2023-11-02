package com.hunstory.tpemptyoutworryjh2023.data;

public class CalendarData {
    public String date;
    public String dayOfWeek;
    public String day;
    public String emotion;



    public CalendarData(String dayOfWeek, String day, String emotion, String date) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.day = day;
        this.emotion = emotion;
    }

    public CalendarData() {
    }
}
