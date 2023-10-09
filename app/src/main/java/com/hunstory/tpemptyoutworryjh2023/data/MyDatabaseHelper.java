package com.hunstory.tpemptyoutworryjh2023.data;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_database.db";
    private  static final int DATABASE_VERSION = 1;

    public MyDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS member(date STRING PRIMARY KEY, title VARCHAR(20) NOT NULL, message TEXT, em STRING)");
        db.execSQL("CREATE TABLE IF NOT EXISTS fillImg(num INTEGER PRIMARY KEY AUTOINCREMENT, filePath STRING)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
