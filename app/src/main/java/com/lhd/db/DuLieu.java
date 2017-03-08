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
    private String diem_hoc_tap="diem_hoc_tap";
    private DuongSQLite duongSQLite;
    public DuLieu(Context context) {
        duongSQLite=new DuongSQLite();
        this.context=context;
        duongSQLite.createOrOpenDataBases(context, Config.DATABASE_NAME);
        runQuery(createTBDiemHocTap);
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
