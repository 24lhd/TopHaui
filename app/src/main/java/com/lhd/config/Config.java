package com.lhd.config;

import com.google.gson.Gson;
import com.lhd.obj.He;
import com.lhd.obj.Khoa;
import com.lhd.obj.Lop;
import com.lhd.obj.Nganh;
import com.lhd.obj.SinhVien;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import duong.ChucNangPhu;

/**
 * Created by D on 07/03/2017.
 */

public class Config {
    public static final String LINK_GET_INFOR_SV = "http://localhost:3000/api/getsv/ma/";
    public static final String DATABASE_NAME = "topcn";
    /**
     * lick post 1 sinh viên lên server
     */
    public static final String POST_SINH_VIEN = "https://topcongnghiep.herokuapp.com/sv/";
    public static final String GET_HE = "https://topcongnghiep.herokuapp.com/api/he";
    public static final String GET_NGANH = "https://topcongnghiep.herokuapp.com/api/nganh";
    public static final String GET_LUOT_TRUY_CAP = "https://topcongnghiep.herokuapp.com/api/luottruycap";
    public static final String GET_KHOA = "https://topcongnghiep.herokuapp.com/api/khoa";
    public static final String GET_LOP = "https://topcongnghiep.herokuapp.com/api/lop";

    public static String getLinkMonHocBatBuoc(String msv) {
        return "https://dttc.haui.edu.vn/vn/s/sinh-vien/bang-mon-bat-buoc?action=p1&p=1&ps=500&exp=rownb&dir=1&s=" + msv;
    }

    public static String getLinkMonHocConThieu(String msv) {
        return "https://dttc.haui.edu.vn/vn/s/sinh-vien/bang-mon-con-thieu?action=p3&p=1&ps=500&exp=SubjectName&dir=1&s=" + msv;
    }

    public static String getLinkMonHocTuChon(String msv) {
        return "https://dttc.haui.edu.vn/vn/s/sinh-vien/bang-mon-tu-chon?action=p2&p=1&ps=500&exp=rownb&dir=1&s=" + msv;
    }

    public static ArrayList<Nganh> getNganhByJson(String jsonNganhs) {

        try {ArrayList<Nganh> nganhs=new ArrayList<>();
            JSONObject jsonObjectNganhs = new JSONObject(jsonNganhs);
            JSONArray jsonArrayNganhs = jsonObjectNganhs.getJSONArray("data");
            for (int i = 0; i < jsonArrayNganhs.length(); i++) {
                JSONObject jsonObject = jsonArrayNganhs.getJSONObject(i);
                Nganh nganh = new Nganh(jsonObject.getString("manganh"),jsonObject.getString("nam"));
                nganhs.add(nganh);
                ChucNangPhu.showLog(nganh.toString());
            }
            return nganhs;
        } catch (Exception e) {}
        return null;

    }

    public static ArrayList<Khoa> getKhoaByJson(String jsonKhoas) {
        try {ArrayList<Khoa> khoas=new ArrayList<>();
            JSONObject jsonObjectKhoas=new JSONObject(jsonKhoas);
            JSONArray jsonArrayKhoa=jsonObjectKhoas.getJSONArray("data");
            for (int i = 0; i < jsonArrayKhoa.length(); i++) {
                JSONObject jsonO=jsonArrayKhoa.getJSONObject(i);
                Khoa khoa=new Khoa(jsonO.getString("k"),jsonO.getString("nbatdau"),jsonO.getString("nam"));
                khoas.add(khoa);
                ChucNangPhu.showLog(khoa.toString());
            }
            return khoas;
        } catch (Exception e) {}
        return null;
    }

    public static ArrayList<Lop> getLopByJson(String jsonLop) {
        try {ArrayList<Lop> lops=new ArrayList<>();
            JSONObject jsonObjectKhoas=new JSONObject(jsonLop);
            JSONArray jsonArrayKhoa=jsonObjectKhoas.getJSONArray("data");
            for (int i = 0; i < jsonArrayKhoa.length(); i++) {
                JSONObject jsonO=jsonArrayKhoa.getJSONObject(i);
                Lop lop=new Lop(jsonO.getString("lop"),jsonO.getString("khoa"));
                lops.add(lop);
                ChucNangPhu.showLog(lop.toString());
            }
            return lops;
        } catch (Exception e) {}
        return null;
    }

    public static ArrayList<He> getHesByJson(String jsonHes) {
        ArrayList<He> hes = new ArrayList<>();
        try {
            JSONObject jsonObjectHes = new JSONObject(jsonHes);
            int jsonObjectHesStatust = jsonObjectHes.getInt("status");
            JSONArray jsonArrayHes = jsonObjectHes.getJSONArray("data");
            if (jsonObjectHesStatust != 1) {
                ChucNangPhu.showLog("getHesByJson fail true khong lay duoc du liẹu");
                return null;
            }
            for (int i = 0; i < jsonArrayHes.length(); i++) {
                JSONObject jsonObject = jsonArrayHes.getJSONObject(i);
                He he = new He(jsonObject.getString("mahe"), jsonObject.getString("tenhe"), jsonObject.getString("nam"));
                hes.add(he);
                ChucNangPhu.showLog(he.toString());
            }
            return hes;
        } catch (JSONException e) {
        }
        return null;
    }
    public static ArrayList<SinhVien> getArraySinhVienByJson(String jsonTop) {
        ArrayList<SinhVien> sinhViens = new ArrayList<>();
        try {
            Gson gson=new Gson();
            JSONObject jsonObject= new JSONObject(jsonTop);
            int jsonObjectHesStatust = jsonObject.getInt("status");
            String jsonObjectHesMSG = jsonObject.getString("msg");
            ChucNangPhu.showLog(jsonObjectHesMSG);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonObjectHesStatust != 1) {
                ChucNangPhu.showLog("getHesByJson fail true khong lay duoc du liẹu");
                return null;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                SinhVien sinhVien= gson.fromJson(object.toString(),SinhVien.class);
                sinhViens.add(sinhVien);
                ChucNangPhu.showLog(sinhVien.toString());
            }
            return sinhViens;
        } catch (JSONException e) {
        }
        return null;
    }

    public static void getSinhVienByJson(String jsonSinhVien) {
        try {

        } catch (Exception e) {
        }
    }


}
