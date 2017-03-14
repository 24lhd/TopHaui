package com.lhd.config;

/**
 * Created by D on 07/03/2017.
 */

public class Config {
    public static final String LINK_GET_INFOR_SV="http://localhost:3000/api/getsv/ma/";
    public static final String DATABASE_NAME="topcn";
    /**
     *
     * lick post 1 sinh viên lên server
     *
     */
    public static final String POST_SINH_VIEN = "https://topcongnghiep.herokuapp.com/sv/";
    public static final String GET_HE = "https://topcongnghiep.herokuapp.com/api/he";
    public static final String GET_NGANH = "https://topcongnghiep.herokuapp.com/api/nganh";
    public static final String GET_LUOT_TRUY_CAP = "https://topcongnghiep.herokuapp.com/api/luottruycap";
    public static final String GET_KHOA= "https://topcongnghiep.herokuapp.com/api/khoa";

    public static String getLinkMonHocBatBuoc(String msv){
        return "https://dttc.haui.edu.vn/vn/s/sinh-vien/bang-mon-bat-buoc?action=p1&p=1&ps=500&exp=rownb&dir=1&s=" + msv;
    }
    public static String getLinkMonHocConThieu(String msv){
        return "https://dttc.haui.edu.vn/vn/s/sinh-vien/bang-mon-con-thieu?action=p3&p=1&ps=500&exp=SubjectName&dir=1&s=" + msv;
    }
    public static String getLinkMonHocTuChon(String msv){
        return "https://dttc.haui.edu.vn/vn/s/sinh-vien/bang-mon-tu-chon?action=p2&p=1&ps=500&exp=rownb&dir=1&s=" + msv;
    }
}
