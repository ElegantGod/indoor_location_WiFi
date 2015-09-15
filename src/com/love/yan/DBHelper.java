package com.love.yan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "datastorage";// �������ݿ�����
    private static final int DATABASE_VERSION = 1;// �������ݿ�汾��
    private static final String TABLE_NAME = "wifi_location7";// ���������
    private static final String[] COLUMNS = { "_id", "ssid", "level", "bssid", "number"};
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + " ( " + COLUMNS[0] + " integer primary key autoincrement, " + COLUMNS[1]
            + " text not null, " + COLUMNS[2] + " integer, " + COLUMNS[3] + " text not null, " + COLUMNS[4] + " text not null);";// ���崴������SQL���
    public  SQLiteDatabase db;

    public DBHelper(Context context) {
    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);// �������
        Log.i("onCreate", "�������");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);// ɾ���ɰ���
        onCreate(db);// �������
    }

    public void add(DataWifi data) {// �����в�������
    	db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMNS[1], data.getssid());
        values.put(COLUMNS[2], data.getlevel());
        values.put(COLUMNS[3], data.getbssid());
        values.put(COLUMNS[4], data.getnumber());
        db.insert(TABLE_NAME, null, values);
    }
  //��ѯ
    public Cursor select(){
    	db = getReadableDatabase();
    	Cursor cursor = db.query(TABLE_NAME, COLUMNS, null,null,null,null,null);
    	return cursor;
    }
    public Cursor selectId(int id)
    {
    	db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, "_id = "+id, null, null, null, null);
        //Cursor cursor = db.query(TABLE_NAME, COLUMNS, "number=?",new String[]{number},null,null,null );
        return cursor;
    }
    public Cursor selectNumber(String number){
    	db = getReadableDatabase();
    	Cursor cursor = db.query(TABLE_NAME, COLUMNS, "number=?",new String[]{number},null,null,null );
    	return cursor;
    }
    public String getMaxNum(){
    	db = getReadableDatabase();
    	Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
    	cursor.moveToLast();
    	String maxNum = cursor.getString(4);
    	return maxNum;
    }
    
    public void deleteNumber(String num){
    	db=getWritableDatabase();  
    	db.delete(TABLE_NAME, "number=?", new String []{num});
    }   
    public void deleteSsid(String ssid){
    	db=getWritableDatabase(); 
    	db.delete(TABLE_NAME, "ssid=?", new String []{ssid});
    }
}