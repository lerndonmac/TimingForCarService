package me.donmac.timingforcar.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = DbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "carService.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = "CREATE TABLE rashodniki (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL," +
                "cost INTEGER,"+
                "probeg INTEGER," +
                "zamena INTEGER," +
                "car_id INTEGER);";
        sqLiteDatabase.execSQL(sqlQuery);
        sqlQuery = "CREATE TABLE cars(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "model TEXT," +
                "manufacturer TEXT," +
                "year INTEGER);";
        sqLiteDatabase.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int vers, int newVers) {

    }
}
