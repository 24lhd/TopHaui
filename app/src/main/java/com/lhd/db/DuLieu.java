package com.lhd.db;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.util.Log;

import com.google.gson.Gson;
import com.lhd.config.Config;
import com.lhd.obj.DiemHocTap;
import com.lhd.obj.He;
import com.lhd.obj.Khoa;
import com.lhd.obj.Lop;
import com.lhd.obj.Nganh;
import com.lhd.obj.SinhVien;

import java.util.ArrayList;

import duong.ChucNangPhu;
import duong.sqlite.DuongSQLite;

/**
 * Created by D on 08/03/2017.
 */

public class DuLieu {
    private Context context;


    private Gson gson;

    //------------------------------------------------------Điểm học tập-------------------------------------------------------------
    /**
     * query tạo bảng điểm học tập
     */
    private String createTBDiemHocTap = "CREATE TABLE IF NOT EXISTS `diem_hoc_tap` (" +
            "`stt`INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`msv`TEXT NOT NULL," +
            "`item`TEXT NOT NULL" +
            ");";
    public void deleteItemDiemHocTap(String msv) { // xóa dữ liệu kết quả học tâp của một sinh viên bằng mã sinh viên
        openDatabases();
        duongSQLite.getDatabase().execSQL("DELETE FROM `diem_hoc_tap` WHERE `msv`='" + msv + "';");
        closeDatabases();
    }

    public void insertItemDiemHocTap(String msv, String item) {// chèn thêm 1 item kết quả học tâp của một sinh viên qua mã sinh viên là kóa chính
        openDatabases();
        duongSQLite.getDatabase().execSQL("INSERT INTO `diem_hoc_tap`(`stt`,`msv`,`item`) VALUES (NULL,'" + msv + "','" + item + "');");
        closeDatabases();
    }

    /**
     * lấy list diểm học tập của một sinh viên từ json databse trả về 1 arr diem học tap
     *
     * @param masv
     * @return
     */
    public ArrayList<DiemHocTap> getListDiemHocTap(String masv) {
        ArrayList<DiemHocTap> diemHocTaps = new ArrayList<>();
        try {
            openDatabases();
            String[] s = {masv};
            Cursor cursor = duongSQLite.getDatabase().query("sv", null, "msv=?", s, null, null, null);
            cursor.moveToFirst(); // di chuyển con trỏ đến dòng đầu tiền trong bảng
            int item = cursor.getColumnIndex("item");
            while (!cursor.isAfterLast()) {
                String jsonDiemHocTap = cursor.getString(item);
                DiemHocTap diemHocTap = gson.fromJson(jsonDiemHocTap, DiemHocTap.class);
                diemHocTaps.add(diemHocTap);
            }
            closeDatabases();
            return diemHocTaps;
        } catch (CursorIndexOutOfBoundsException e) {
            ChucNangPhu.showLog("getListDiemHocTap() CursorIndexOutOfBoundsException");
            return null;
        }
    }
//-----------------------------------------------------Chèn dữ liệu các tab vào cơ sở dữ liệu-------------------------------------------------------------
   // chèn vào dữ liệu dạng json khi khỏi tạo dữ liệu
public void insertTabs(int tab_item,String jsonTabTop) {
        openDatabases();
        duongSQLite.getDatabase().execSQL("DELETE FROM `tab_top` WHERE `id_item`='" + tab_item + "';");
        duongSQLite.getDatabase().execSQL("INSERT INTO `tab_top`(`stt`,`id_item`,`tab_top`) VALUES (NULL,'" + tab_item + "','" + jsonTabTop + "');");
        closeDatabases();
    }
    private String createTBTabTop = "CREATE TABLE IF NOT EXISTS `tab_top` (" +
            "`stt`INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`id_item`TEXT, "  +
            "`tab_top`TEXT" +
            ");";
    public ArrayList<He> getTabTopHes() {
        try {
            openDatabases();
            String[] id_item = {"1"};
            Cursor cursor = duongSQLite.getDatabase().query("tab_top", null, "id_item=?",id_item, null, null, null);
            cursor.moveToFirst(); // di chuyển con trỏ đến dòng đầu tiền trong bảng
            int item = cursor.getColumnIndex("tab_top");
            ArrayList<He> hes = Config.getHesByJson(cursor.getString(item));
            closeDatabases();
            return hes;
        } catch (CursorIndexOutOfBoundsException e) {
            ChucNangPhu.showLog("getTabTopHes() CursorIndexOutOfBoundsException");
            return null;
        }
    }
    public ArrayList<Khoa> getTabTopKhoa() {
        try {
            openDatabases();
            String[] id_item = {"2"};
            Cursor cursor = duongSQLite.getDatabase().query("tab_top", null, "id_item=?",id_item, null, null, null);
            cursor.moveToFirst(); // di chuyển con trỏ đến dòng đầu tiền trong bảng
            int item = cursor.getColumnIndex("tab_top");
            ArrayList<Khoa> khoas = Config.getKhoaByJson(cursor.getString(item));
            closeDatabases();
            return khoas;
        } catch (CursorIndexOutOfBoundsException e) {
            ChucNangPhu.showLog("getTabTopKhoa() CursorIndexOutOfBoundsException");
            return null;
        }
    }
    public ArrayList<Nganh> getTabTopNganh() {
        try {
            openDatabases();
            String[] id_item = {"3"};
            Cursor cursor = duongSQLite.getDatabase().query("tab_top", null, "id_item=?",id_item, null, null, null);
            cursor.moveToFirst(); // di chuyển con trỏ đến dòng đầu tiền trong bảng
            int item = cursor.getColumnIndex("tab_top");
            ArrayList<Nganh> nganhs = Config.getNganhByJson(cursor.getString(item));
            closeDatabases();
            return nganhs;
        } catch (CursorIndexOutOfBoundsException e) {
            ChucNangPhu.showLog("getTabTopNganh() CursorIndexOutOfBoundsException");
            return null;
        }
    }
    public ArrayList<Lop> getTabTopLop() {
        try {
            openDatabases();
            String[] id_item = {"4"};
            Cursor cursor = duongSQLite.getDatabase().query("tab_top", null, "id_item=?",id_item, null, null, null);
            cursor.moveToFirst(); // di chuyển con trỏ đến dòng đầu tiền trong bảng
            int item = cursor.getColumnIndex("tab_top");
            ArrayList<Lop> lops = Config.getLopByJson(cursor.getString(item));
            closeDatabases();
            return lops;
        } catch (CursorIndexOutOfBoundsException e) {
            ChucNangPhu.showLog("getTabTopLop() CursorIndexOutOfBoundsException");
            return null;
        }
    }
    //-----------------------------------------------------Quản lý data top-------------------------------------------------------------------------
    private String createTBDataTop = "CREATE TABLE IF NOT EXISTS `data_top` (" +
            "`stt`INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`id_tab`TEXT," +
            "`data_top`TEXT" +
            ");";
    public void insertDataTop(String id_tab,String jsonTopKhoa) {
        openDatabases();
        duongSQLite.getDatabase().execSQL("DELETE FROM `data_top` WHERE `id_tab`='" + id_tab + "';");
        duongSQLite.getDatabase().execSQL("INSERT INTO `data_top`(`stt`,`id_tab`,`data_top`) VALUES (NULL,'" + id_tab + "','" + jsonTopKhoa + "');");
        closeDatabases();
    }
    public ArrayList<SinhVien> getTopSinhVienTabTopByIdTab(String id_tab) {
        try {
            String[] idTab = {id_tab};
            openDatabases();
            Cursor cursor = duongSQLite.getDatabase().query("data_top", null, "id_tab=?",idTab , null, null, null);
            cursor.moveToFirst(); // di chuyển con trỏ đến dòng đầu tiền trong bảng
            int item = cursor.getColumnIndex("data_top");
            ArrayList<SinhVien> sinhViens = Config.getArraySinhVienByJson(cursor.getString(item));
            closeDatabases();
            return sinhViens;
        } catch (CursorIndexOutOfBoundsException e) {
            ChucNangPhu.showLog("getTopSinhVienTabTopByIdTab() CursorIndexOutOfBoundsException");
        }
        return null;
    }
    //---------------------------------------------------------------------------------------------
    private DuongSQLite duongSQLite;

    public DuLieu(Context context) {
        duongSQLite = new DuongSQLite();
        this.context = context;
        openDatabases();
        gson = new Gson();
    }

    public String getNotiDTTC() {
        try {
            openDatabases();
            Cursor cursor = duongSQLite.getDatabase().query("notidttc", null, null, null, null, null, null);
            cursor.getCount();// tra ve so luong ban ghi no ghi dc
            cursor.getColumnNames();// 1 mang cac cot
            cursor.moveToFirst(); // di chuyển con trỏ đến dòng đầu tiền trong bảng
            int tenSV = cursor.getColumnIndex("title");
            int maSV = cursor.getColumnIndex("link");
            closeDatabases();
            return "";
        } catch (CursorIndexOutOfBoundsException e) {
            Log.e("faker", "getNotiDTTC");
            return null;
        }
    }

    private void closeDatabases() {
        duongSQLite.cloneDatabase();
    }

    private void openDatabases() {
        duongSQLite.createOrOpenDataBases(context, Config.DATABASE_NAME);
        duongSQLite.runQuery(createTBDiemHocTap);

        duongSQLite. runQuery(createTBDataTop);

        duongSQLite. runQuery(createTBTabTop);
    }




}
