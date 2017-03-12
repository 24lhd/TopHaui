package com.lhd.obj;

/**
 * Created by D on 12/03/2017.
 */

public class He {
    private String mahe;
    private String tenhe;

    @Override
    public String toString() {
        return "He{" +
                "mahe='" + mahe + '\'' +
                ", tenhe='" + tenhe + '\'' +
                '}';
    }

    public String getMahe() {
        return mahe;
    }

    public String getTenhe() {
        return tenhe;
    }

    public He(String mahe, String tenhe) {

        this.mahe = mahe;
        this.tenhe = tenhe;
    }
}
