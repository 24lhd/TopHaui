package com.lhd.obj;

/**
 * Created by D on 14/03/2017.
 */

public class Khoa {
    private String khoa;
    private String nbatdau;

    @Override
    public String toString() {
        return "Khoa{" +
                "khoa='" + khoa + '\'' +
                ", nbatdau='" + nbatdau + '\'' +
                '}';
    }

    public String getKhoa() {
        return khoa;
    }

    public String getNbatdau() {
        return nbatdau;
    }

    public Khoa(String khoa, String nbatdau) {

        this.khoa = khoa;
        this.nbatdau = nbatdau;
    }
}
