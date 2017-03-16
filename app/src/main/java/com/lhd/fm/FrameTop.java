package com.lhd.fm;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lhd.activity.Main;
import com.lhd.obj.He;
import com.lhd.obj.Khoa;
import com.lhd.obj.Lop;
import com.lhd.obj.Nganh;
import com.lhd.tophaui.R;

import java.util.ArrayList;

import duong.ChucNangPhu;
import duong.Communication;
import duong.Conections;

/**
 * Created by D on 12/03/2017.
 */

public class FrameTop extends Fragment{
    private LayoutInflater inflater;
    private View rootView;
    private Main main;
    private ArrayList<He> hes;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private String content;
    public static final String KEY_CONTENT="key content";
    private ArrayList<Nganh> nganhs;
    private ArrayList<Khoa> khoas;
    private TextView tvOff;
    private LinearLayout linearLayout;
    private ArrayList<Lop> lops;

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater=inflater;
        main = (Main) getActivity();
        content=getArguments().getString(KEY_CONTENT);
        ChucNangPhu.showLog("content "+content);
        rootView = inflater.inflate(R.layout.frame_top_frament, container, false);
        initView();
        return rootView;
    }
    private void initView() {
        hes= main.getHes();
        nganhs= main.getNganhs();
        khoas= main.getKhoas();
        lops= main.getLops();
         linearLayout= (LinearLayout) rootView.findViewById(R.id.layout_frame_top_fragment);
        tabLayout= (TabLayout) rootView.findViewById(R.id.tab_layout_fm_top);
        mViewPager = (ViewPager) rootView.findViewById(R.id.view_pager_frame_top_fragment);
         tvOff= (TextView) rootView.findViewById(R.id.tv_frame_top_fragment_off);
        mSectionsPagerAdapter = new SectionsPagerAdapter(main.getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setBackgroundColor(main.getTab_select_color());
        tabLayout.setupWithViewPager(mViewPager);
        try {
           setViewOnline();
        }catch (Exception e){
            setViewOffline();
            main.setFail(true);
        }


    }

    private void setViewOffline() {
        tvOff.setVisibility(View.VISIBLE);
        tvOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Conections.isOnline(main))
                main.initViewIntro();
//                setViewOnline();
                else Communication.showToast(main,"Bạn chưa bật kết nối internet!");
            }
        });
        linearLayout.setVisibility(View.GONE);
    }

    private void setViewOnline() {
        linearLayout.setVisibility(View.VISIBLE);
        tvOff.setVisibility(View.GONE);
        tabLayout.getTabAt(0).select();
        mViewPager.setCurrentItem(0);
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        public SectionsPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            ContentTop fragment = new ContentTop();

//            args.putSerializable(ARG_SECTION_NUMBER, categories.get(position));
//            args.putSerializable(MainActivity.LIST_DATA,danhNgonCategory);
//            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public int getCount() {
            try {
                if (content.contains(Main.KEY_TOP_HE))
                return hes.size()>0?hes.size():0;
                else if (content.contains(Main.KEY_TOP_NGANH))
                    return nganhs.size()>0?nganhs.size():0;
                else if (content.contains(Main.KEY_TOP_KHOA))
                    return khoas.size()>0?khoas.size():0;
                else if (content.contains(Main.KEY_TOP_LOP))
                    return lops.size()>0?lops.size():0;
                else
                    return hes.size()>0?hes.size():0;
            }catch (NullPointerException e){
                return 0;
            }
        }
        @Override
        public CharSequence getPageTitle(int position) {
            if (content.contains(Main.KEY_TOP_HE))
                return hes.get(position).getTenhe()+"\nnăm "+hes.get(position).getNam();
            else if (content.contains(Main.KEY_TOP_NGANH))
                return nganhs.get(position).getManganh()+"\nnăm "+nganhs.get(position).getNam();
            else if (content.contains(Main.KEY_TOP_KHOA))
                return khoas.get(position).getKhoa()+" ("+khoas.get(position).getNbatdau()+")\nnăm "+khoas.get(position).getNam();
            else if (content.contains(Main.KEY_TOP_LOP))
                return lops.get(position).getLop()+"\n"+lops.get(position).getKhoa();
            else
                return hes.get(position).getTenhe()+"\nnăm "+hes.get(position).getNam();
        }
    }
}
