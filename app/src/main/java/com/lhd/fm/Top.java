package com.lhd.fm;

import android.os.Handler;
import android.os.Message;

import com.lhd.activity.Main;
import com.lhd.adaptor.ListSinhVien;
import com.lhd.obj.SinhVien;
import com.lhd.task.GetJSONByLink;
import java.util.ArrayList;
import duong.ChucNangPhu;

/**
 * Created by D on 16/03/2017.
 */

public class Top extends Frame {
    private ArrayList<SinhVien> sinhViens;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try{
                String json= (String) msg.obj;
                if(json!=null){
                    ChucNangPhu.showLog("json!=null " +json);
                    dulieu.insertDataTop(id_tab,json);
                    setRecyclerView();
                }else showTextNull();
            }catch (NullPointerException e){
                startParser();
            }
        }
    };
    private String urlAPI;
    private String id_tab;

    @Override
    protected void startParser() {
        GetJSONByLink getJSONByLink=new GetJSONByLink(handler);
        getJSONByLink.execute(urlAPI);
    }

    @Override
    public void setRecyclerView() {
        ArrayList<Object> objects=new ArrayList<>();
        objects.addAll(sinhViens);
        ListSinhVien listSinhVien=new ListSinhVien(recyclerView,objects,null,11,(Main) getActivity());
        recyclerView.setAdapter(listSinhVien);
    }

    @Override
    public void checkDatabase() {
         urlAPI=getArguments().getString(Main.LINK_TOP);
        id_tab=getArguments().getString(Main.ID_TAB);
        sinhViens=dulieu.getTopSinhVienTabTopByIdTab(id_tab);
        if (sinhViens!=null){
//            for (SinhVien sinhVien:sinhViens) {
//            }
            ChucNangPhu.showLog("size "+sinhViens.size());
            setRecyclerView();
        }else  loadData();
    }
}
