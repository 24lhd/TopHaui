package com.lhd.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lhd.obj.ItemNotiDTTC;

import java.util.ArrayList;

/**
 * Created by Faker on 9/5/2016.
 */
public class SQLiteManager {
    public static final String CREATE_TABLE_NOTIDTTC="CREATE TABLE IF NOT EXISTS `notidttc`(" +
            "`stt`INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`link`TEXT ," +
            "`title`TEXT );";
    private Context context;
    private SQLiteDatabase database;
    public SQLiteManager(Context context) {
        this.context = context;
        openDatabases();
    }
    private void closeDatabases() {
        database.close();
    }
    private void openDatabases() {
        database=context.openOrCreateDatabase("data.sqlite", Context.MODE_APPEND,null);
        database.execSQL(CREATE_TABLE_NOTIDTTC);
    }
    public long insertItemNotiDTTC(ItemNotiDTTC itemNotiDTTC){
        long id = 0;
            ContentValues contentValues=new ContentValues();
            contentValues.put("link",itemNotiDTTC.getLink());
            contentValues.put("title",itemNotiDTTC.getTitle());
            openDatabases();
            id=database.insert("notidttc", null, contentValues);
            closeDatabases();
        return id;
    }
    public void deleteItemNotiDTTC() {
        try {
            openDatabases();
            database.delete("notidttc",null,null);
            closeDatabases();
        }catch (Exception e){}

    }
    public ArrayList<ItemNotiDTTC> getNotiDTTC() {
        try {
            ArrayList<ItemNotiDTTC> itemNotiDTTCs=new ArrayList<>();
            openDatabases();
            Cursor cursor=database.query("notidttc",null,null,null,null,null,null);
            cursor.getCount();// tra ve so luong ban ghi no ghi dc
            cursor.getColumnNames();// 1 mang cac cot
            cursor.moveToFirst(); // di chuyển con trỏ đến dòng đầu tiền trong bảng
            int tenSV=cursor.getColumnIndex("title");
            int maSV=cursor.getColumnIndex("link");
            while (!cursor.isAfterLast()){
                itemNotiDTTCs.add(new ItemNotiDTTC(cursor.getString(maSV),cursor.getString(tenSV)));
                cursor.moveToNext();
            }
            closeDatabases();
            return itemNotiDTTCs ;
        }catch (CursorIndexOutOfBoundsException e){
            Log.e("faker","getNotiDTTC");
            return null;
        }
    }



}
