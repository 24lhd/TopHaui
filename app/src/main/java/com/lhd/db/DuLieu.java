package com.lhd.db;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.util.Log;

import com.lhd.config.Config;

import duong.sqlite.DuongSQLite;

/**
 * Created by D on 08/03/2017.
 */

public class DuLieu {
    private  Context context;
    private String createTBDiemHocTap="CREATE TABLE IF NOT EXISTS `diem_hoc_tap` (" +
            "`stt`INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`msv`TEXT NOT NULL," +
            "`item`TEXT NOT NULL" +
            ");";
    private String createTBNganhs="CREATE TABLE IF NOT EXISTS `nganhs` (" +
            "`stt`INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`nganh`TEXT" +
            ");";
    private String createTBHes="CREATE TABLE IF NOT EXISTS `hes` (" +
            "`stt`INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`he`TEXT" +
            ");";
    private String createTBKhoas="CREATE TABLE IF NOT EXISTS `khoas` (" +
            "`stt`INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`khoa`TEXT" +
            ");";
    private String createTBTopHe="CREATE TABLE IF NOT EXISTS `top_he` (" +
            "`stt`INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`top_he`TEXT" +
            ");";
    private String createTBTopKhoa="CREATE TABLE IF NOT EXISTS `top_khoa` (" +
            "`stt`INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`top_khoa`TEXT" +
            ");";
    private String createTBTopNganh="CREATE TABLE IF NOT EXISTS `top_nganh` (" +
            "`stt`INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`top_nganh`TEXT" +
            ");";
    private String createTBTopLop="CREATE TABLE IF NOT EXISTS `top_lop` (" +
            "`stt`INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`top_lop`TEXT" +
            ");";
    private String diem_hoc_tap="diem_hoc_tap";
    private DuongSQLite duongSQLite;
    public DuLieu(Context context) {
        duongSQLite=new DuongSQLite();
        this.context=context;


    }
    public String getNotiDTTC() {
        try {
            openDatabases();
            Cursor cursor=duongSQLite.getDatabase().query("notidttc",null,null,null,null,null,null);
            cursor.getCount();// tra ve so luong ban ghi no ghi dc
            cursor.getColumnNames();// 1 mang cac cot
            cursor.moveToFirst(); // di chuyển con trỏ đến dòng đầu tiền trong bảng
            int tenSV=cursor.getColumnIndex("title");
            int maSV=cursor.getColumnIndex("link");
//            while (!cursor.isAfterLast()){
//                itemNotiDTTCs.add(new ItemNotiDTTC(cursor.getString(maSV),cursor.getString(tenSV)));
//                cursor.moveToNext();
//            }
            closeDatabases();
            return "" ;
        }catch (CursorIndexOutOfBoundsException e){
            Log.e("faker","getNotiDTTC");
            return null;
        }
    }

    private void closeDatabases() {
        duongSQLite.cloneDatabase();
    }

    private void openDatabases() {
        duongSQLite.createOrOpenDataBases(context, Config.DATABASE_NAME);
        runQuery(createTBDiemHocTap);
        runQuery(createTBNganhs);
        runQuery(createTBHes);
        runQuery(createTBKhoas);
        runQuery(createTBTopHe);
        runQuery(createTBTopKhoa);
        runQuery(createTBTopNganh);
        runQuery(createTBTopLop);
    }

    public void insertTopHe(String jsonTopHe) {
        openDatabases();
        duongSQLite.getDatabase().execSQL("DELETE FROM `top_he` ;");
        duongSQLite.getDatabase().execSQL("INSERT INTO `top_he`(`stt`,`top_he`) VALUES (NULL,'"+jsonTopHe+"');");
        closeDatabases();
    }
    public void insertTopKhoa(String jsonTopKhoa) {
        openDatabases();
        duongSQLite.getDatabase().execSQL("DELETE FROM `top_khoa` ;");
        duongSQLite.getDatabase().execSQL("INSERT INTO `top_khoa`(`stt`,`top_khoa`) VALUES (NULL,'"+jsonTopKhoa+"');");
        closeDatabases();
    }
    public void insertTopNganh(String jsonTopNganh) {
        openDatabases();
        duongSQLite.getDatabase().execSQL("DELETE FROM `top_nganh` ;");
        duongSQLite.getDatabase().execSQL("INSERT INTO `top_nganh`(`stt`,`top_nganh`) VALUES (NULL,'"+jsonTopNganh+"');");
        closeDatabases();
    }
    public void insertTopLop(String jsonTopLop) {
        openDatabases();
        duongSQLite.getDatabase().execSQL("DELETE FROM `top_lop` ;");
        duongSQLite.getDatabase().execSQL("INSERT INTO `top_lop`(`stt`,`top_lop`) VALUES (NULL,'"+jsonTopLop+"');");
        closeDatabases();
    }

    /**
     * chèn vào dữ liệu dạng json khi khỏi tạo dữ liệu
     * @param jsonNganhs
     * @param jsonHes
     * @param jsonKhoas
     */
    private void initData(String jsonNganhs, String jsonHes, String jsonKhoas) {
        duongSQLite.getDatabase().execSQL("DELETE FROM `nganhs` ;");
        duongSQLite.getDatabase().execSQL("DELETE FROM `khoas` ;");
        duongSQLite.getDatabase().execSQL("DELETE FROM `hes` ;");
        duongSQLite.getDatabase().execSQL("INSERT INTO `nganhs`(`stt`,`nganh`) VALUES (NULL,'"+jsonNganhs+"');");
        duongSQLite.getDatabase().execSQL("INSERT INTO `khoas`(`stt`,`khoa`) VALUES (NULL,'"+jsonKhoas+"');");
        duongSQLite.getDatabase().execSQL("INSERT INTO `hes`(`stt`,`he`) VALUES (NULL,'"+jsonHes+"');");
    }


    public void deleteItemDiemHocTap(String msv) {
        duongSQLite.getDatabase().execSQL("DELETE FROM `diem_hoc_tap` WHERE `msv`='"+msv+"';");
    }
    public void insertItemDiemHocTap(String msv,String item) {
        duongSQLite.getDatabase().execSQL("INSERT INTO `diem_hoc_tap`(`stt`,`msv`,`item`) VALUES (NULL,'"+msv+"','"+item+"');");
    }

    public void runQuery(String s) {
        duongSQLite.getDatabase().execSQL(s);
    }
}
