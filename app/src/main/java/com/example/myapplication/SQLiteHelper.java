package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "AccountBook.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "Account_book";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CHECK_EXPENSE = "check_expense";
    public static final String COLUMN_DATE = "dates";
    public static final String COLUMN_ITEM = "item";
    public static final String COLUMN_PRICE = "price";

    private static final String DATABASE_CREATE_QUERY = "CREATE TABLE "
            + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CHECK_EXPENSE + " INTEGER, "
            + COLUMN_DATE + " DATE, "
            + COLUMN_ITEM + " TEXT, "
            + COLUMN_PRICE + " INTEGER);";


    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(DATABASE_CREATE_QUERY);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
