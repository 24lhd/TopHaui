package com.lhd.fm;

import android.os.Handler;
import android.os.Message;

import com.lhd.activity.Main;
import com.lhd.adaptor.ListSinhVien;
import com.lhd.obj.SinhVien;
import com.lhd.task.GetJSONByLink;

import java.util.ArrayList;

import duong.ChucNangPhu;
import duong.Conections;

import static com.lhd.activity.Main.ADS_INDEX_ITEM;

/**
 * Created by D on 16/03/2017.
 */

public class TopCacLoai extends Frame {
    private ArrayList<SinhVien> sinhViens;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try{
                String json= (String) msg.obj;
                if(json!=null){
                    ChucNangPhu.showLog("json!=null " +json);
                    dulieu.insertDataTop(id_tab,json);
                    geted=true;
                    checkDatabase();
                    showRecircleView();
                }else showTextNull();
            }catch (NullPointerException e){
                startParser();
            }
        }
    };
    private String urlAPI;
    private String id_tab;
    private boolean geted;

    @Override
    protected void startParser() {
        showProgress();
        GetJSONByLink getJSONByLink=new GetJSONByLink(handler);
        getJSONByLink.execute(urlAPI);
    }

    @Override
    public void setRecyclerView() {
        showRecircleView();
        ArrayList<Object> objects=new ArrayList<>();
        objects.addAll(sinhViens);
        ListSinhVien listSinhVien=new ListSinhVien(recyclerView,objects,null,ADS_INDEX_ITEM,(Main) getActivity(),sinhViens);
        recyclerView.setAdapter(listSinhVien);
    }

    @Override
    public void checkDatabase() {
        showProgress();
        urlAPI=getArguments().getString(Main.LINK_TOP);
        id_tab=getArguments().getString(Main.ID_TAB);
        if (Conections.isOnline(getActivity())&&geted==false){
            startParser();
            return;
        }
        sinhViens=dulieu.getTopSinhVienTabTopByIdTab(id_tab);
        if (sinhViens!=null){
            ChucNangPhu.showLog("sinhViens "+sinhViens.size());
            setRecyclerView();
        }else  loadData();
    }
}
