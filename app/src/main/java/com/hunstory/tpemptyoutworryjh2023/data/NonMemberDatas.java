package com.hunstory.tpemptyoutworryjh2023.data;

import java.util.ArrayList;

public class NonMemberDatas {
    public String no;
    public String date;
    public String title;
    public String message;
    public String em;
    public ArrayList<String> imgPath;

    public NonMemberDatas(String date, String title, String message, String emotion, ArrayList<String> imgPath) {
        this.date = date;
        this.title = title;
        this.message = message;
        this.em = emotion;
        this.imgPath = imgPath;
    }

    public NonMemberDatas() {
    }
}
