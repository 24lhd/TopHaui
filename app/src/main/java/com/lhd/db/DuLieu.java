package com.lhd.db;

import android.content.Context;

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

    private void insertTopHe(String jsonTopHe) {
        duongSQLite.getDatabase().execSQL("DELETE FROM `top_he` ;");
        duongSQLite.getDatabase().execSQL("INSERT INTO `top_he`(`stt`,`top_he`) VALUES (NULL,'"+jsonTopHe+"');");
    }
    private void insertTopKhoa(String jsonTopKhoa) {
        duongSQLite.getDatabase().execSQL("DELETE FROM `top_khoa` ;");
        duongSQLite.getDatabase().execSQL("INSERT INTO `top_khoa`(`stt`,`top_khoa`) VALUES (NULL,'"+jsonTopKhoa+"');");
    }
    private void insertTopNganh(String jsonTopNganh) {
        duongSQLite.getDatabase().execSQL("DELETE FROM `top_nganh` ;");
        duongSQLite.getDatabase().execSQL("INSERT INTO `top_nganh`(`stt`,`top_nganh`) VALUES (NULL,'"+jsonTopNganh+"');");
    }
    private void insertTopLop(String jsonTopLop) {
        duongSQLite.getDatabase().execSQL("DELETE FROM `top_lop` ;");
        duongSQLite.getDatabase().execSQL("INSERT INTO `top_lop`(`stt`,`top_lop`) VALUES (NULL,'"+jsonTopLop+"');");
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
