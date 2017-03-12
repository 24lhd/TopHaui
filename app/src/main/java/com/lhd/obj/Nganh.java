package com.lhd.obj;

/**
 * Created by D on 12/03/2017.
 */

public class Nganh {
    private String manganh;

    @Override
    public String toString() {
        return "Nganh{" +
                "manganh='" + manganh + '\'' +
                '}';
    }

    public String getManganh() {
        return manganh;
    }

    public Nganh(String manganh) {

        this.manganh = manganh;
    }
}
