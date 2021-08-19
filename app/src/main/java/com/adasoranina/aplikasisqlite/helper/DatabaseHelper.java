package com.adasoranina.aplikasisqlite.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.adasoranina.aplikasisqlite.model.Data;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "digitaltalent.db";
    private static final String TABLE_STUDENTS = "students";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ADDRESS = "address";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE_STUDENTS = "CREATE TABLE " + TABLE_STUDENTS
                + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_ADDRESS + " TEXT NOT NULL"
                + ");";

        sqLiteDatabase.execSQL(CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(sqLiteDatabase);
    }

    public List<Data> getAllData() {
        List<Data> listData = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_STUDENTS;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String address = cursor.getString(2);
                listData.add(new Data(id, name, address));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listData;
    }

    public void insert(String name, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertQuery = "INSERT INTO " + TABLE_STUDENTS
                + " (" + COLUMN_NAME + ", " + COLUMN_ADDRESS + ") "
                + "VALUES "
                + "('" + name + "', '" + address + "');";

        db.execSQL(insertQuery);
        db.close();
    }

    public void update(int id, String name, String address) {
        SQLiteDatabase db = this.getWritableDatabase();

        String updateQuery = "UPDATE " + TABLE_STUDENTS + " SET "
                + COLUMN_NAME + "='" + name + "', "
                + COLUMN_ADDRESS + "='" + address + "' "
                + "WHERE " + COLUMN_ID + "=" + "'" + id + "';";

        db.execSQL(updateQuery);
        db.close();
    }

    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String deleteQuery = "DELETE FROM " + TABLE_STUDENTS
                + " WHERE " + COLUMN_ID + "='" + id + "';";

        db.execSQL(deleteQuery);
        db.close();
    }

}
