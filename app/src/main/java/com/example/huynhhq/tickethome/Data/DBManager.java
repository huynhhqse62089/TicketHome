package com.example.huynhhq.tickethome.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.huynhhq.tickethome.model.User;

/**
 * Created by HuynhHQ on 10/17/2017.
 */

public class DBManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user_manager";
    private static final String TABLE_NAME = "user";
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String FULLNAME = "fullname";
    private static final String PHONE = "phone";
    private static final String EMAIL = "email";
    private static final String DOB = "dob";
    private static final String ROLE = "role";
    private static final int VERSION = 1;

    private String SQLCreateTable = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY, " +
            USERNAME + " TEXT, " +
            PASSWORD + " TEXT, " +
            FULLNAME + " TEXT, " +
            PHONE + " TEXT, " +
            EMAIL + " TEXT, " +
            DOB + " TEXT, " +
            ROLE + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    private String SQLGetAll = "SELECT * FROM " + TABLE_NAME + " LIMIT 1";

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("Tao DB", "onCreate: da tao database");
        sqLiteDatabase.execSQL(SQLCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME, user.getUsername());
        values.put(PASSWORD, user.getPassword());
        values.put(FULLNAME, user.getFullname());
        values.put(PHONE, user.getPhone());
        values.put(EMAIL, user.getEmail());
        values.put(DOB, user.getDob());
        values.put(ROLE, user.getRole());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public User getUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SQLGetAll, null);
        if (cursor.moveToFirst()){
            User user = new User();
            user.setId(cursor.getInt(0));
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setFullname(cursor.getString(3));
            user.setPhone(cursor.getString(4));
            user.setEmail(cursor.getString(5));
            user.setDob(cursor.getString(6));
            return user;
        }
        return null;
    }

    public void deleteUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_NAME);
    }
}
