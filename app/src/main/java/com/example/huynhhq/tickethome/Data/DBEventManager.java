package com.example.huynhhq.tickethome.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.huynhhq.tickethome.model.EventNotification;

/**
 * Created by HuynhHQ on 11/4/2017.
 */

public class DBEventManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "event_manager";
    private static final String TABLE_NAME = "event";
    private static final String ID = "id";
    private static final String REAL_ID = "real_id";
    private static final String NAME = "name";
    private static final String STARTDATE = "startdate";
    private static final int VERSION = 1;

    private String SQLCreateTable = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY, " +
            REAL_ID + " INTEGER, " +
            NAME + " TEXT, " +
            STARTDATE + " TIMESTAMP " +
            ")";

    private String SQLGetAll = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + STARTDATE + " DESC LIMIT 1";

    public DBEventManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Tao DB", "onCreate: da tao database");
        db.execSQL(SQLCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public EventNotification getLastedEvent(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SQLGetAll, null);
        if (cursor.moveToFirst()){
            EventNotification eventNotification = new EventNotification();
            eventNotification.setId(cursor.getInt(0));
            eventNotification.setRealId(cursor.getInt(1));
            eventNotification.setName(cursor.getString(2));
            eventNotification.setStartDate(cursor.getLong(3));
            return eventNotification;
        }
        return null;
    }

    public Cursor openReadableQuery (String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    public void inserEvent(EventNotification event){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(REAL_ID, event.getRealId());
        values.put(NAME, event.getName());
        values.put(STARTDATE, event.getStartDate());
        db.insert(TABLE_NAME, null, values);
    }

    public int deleteEvent(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, REAL_ID+"=?", new String[]{String.valueOf(id)});
    }

    public boolean checkDuplicatedWord (int id) {
        Cursor check = openReadableQuery("SELECT * FROM " + TABLE_NAME + " WHERE real_id = '"+ id +"'");
        if (check.moveToNext()) {
            return true;
        }
        else return false;
    }

}
