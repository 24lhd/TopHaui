package com.lhd.fm;

import android.os.Handler;
import android.os.Message;

import com.lhd.activity.Main;
import com.lhd.adaptor.ListDiemHocTap;
import com.lhd.obj.DiemHocTap;
import com.lhd.obj.SinhVien;
import com.lhd.task.ParserSinhVien;

import java.util.ArrayList;

import duong.ChucNangPhu;

import static com.lhd.activity.Main.SINH_VIEN;
import static duong.Conections.isOnline;

/**
 * Created by D on 17/03/2017.
 */

public class KetQuaHocTapSinhVien extends Frame {
    private ArrayList<DiemHocTap> diemHocTaps;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
//                SinhVien sinhVien = (SinhVien) msg.obj;
                if (msg.obj instanceof SinhVien) {
                    geted = true;
                    checkDatabase();
                    showRecircleView();
                    return;
                }
            } catch (NullPointerException e) {
                startParser();
            }
        }
    };
    private boolean geted;
    private Main main;
    private String msv;
    private SinhVien sinhVien;

    @Override
    protected void startParser() {
        showProgress();
        ParserSinhVien parserSinhVien = new ParserSinhVien(handler, getActivity());
        parserSinhVien.execute(msv);
    }

    @Override
    public void setRecyclerView() {
        showRecircleView();
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(diemHocTaps);
        ChucNangPhu.showLog("diemHocTaps  setRecyclerView"+diemHocTaps.size());
        ListDiemHocTap listDiemHocTap = new ListDiemHocTap(recyclerView, objects, null,Main.ADS_INDEX_ITEM, (Main) getActivity(), diemHocTaps);
        recyclerView.setAdapter(listDiemHocTap);
    }

    @Override
    public void checkDatabase() {
        showProgress();
        main = (Main) getActivity();
        sinhVien= (SinhVien) getArguments().getSerializable(SINH_VIEN);
        msv = sinhVien.getMa();
        ChucNangPhu.showLog("checkDatabase "+sinhVien.toString());
        if (isOnline(getActivity()) && geted == false) {
            loadData();
            return;
        }
        diemHocTaps = dulieu.getListDiemHocTap(msv);
        if (diemHocTaps != null) {
            ChucNangPhu.showLog("diemHocTaps " + diemHocTaps.size());
            setRecyclerView();
        } else loadData();
    }

}