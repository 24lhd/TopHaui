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

import com.lhd.activity.NaviActivity;
import com.lhd.obj.He;
import com.lhd.obj.Khoa;
import com.lhd.obj.Lop;
import com.lhd.obj.Nganh;
import com.lhd.tophaui.R;

import java.util.ArrayList;

import duong.Communication;
import duong.Conections;

/**
 * Created by D on 15/03/2017.
 */

public class FrameMoreFragment extends Fragment {
    private LayoutInflater inflater;
    private View rootView;
    private NaviActivity naviActivity;
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
        naviActivity= (NaviActivity) getActivity();
        rootView = inflater.inflate(R.layout.frame_top_frament, container, false);
        initView();
        return rootView;
    }
    private void initView() {
        hes=naviActivity.getHes();
        nganhs=naviActivity.getNganhs();
        khoas=naviActivity.getKhoas();
        lops=naviActivity.getLops();
        linearLayout= (LinearLayout) rootView.findViewById(R.id.layout_frame_top_fragment);
        tabLayout= (TabLayout) rootView.findViewById(R.id.tab_layout_fm_top);
        mViewPager = (ViewPager) rootView.findViewById(R.id.view_pager_frame_top_fragment);
        tvOff= (TextView) rootView.findViewById(R.id.tv_frame_top_fragment_off);
        mSectionsPagerAdapter = new SectionsPagerAdapter(naviActivity.getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setBackgroundColor(naviActivity.getTab_select_color());
        tabLayout.setupWithViewPager(mViewPager);
        try {
            setViewOnline();
        }catch (Exception e){
            setViewOffline();
            naviActivity.setFail(true);
        }


    }

    private void setViewOffline() {
        tvOff.setVisibility(View.VISIBLE);
        tvOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Conections.isOnline(naviActivity))
                    naviActivity.initViewIntro();
//                setViewOnline();
                else Communication.showToast(naviActivity,"Bạn chưa bật kết nối internet!");
            }
        });
        linearLayout.setVisibility(View.GONE);
    }

    private void setViewOnline() {
        linearLayout.setVisibility(View.VISIBLE);
        tvOff.setVisibility(View.GONE);
        tabLayout.setBackgroundColor(naviActivity.getTab_select_color());
        tabLayout.getTabAt(0).select();
        mViewPager.setCurrentItem(0);
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        public SectionsPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new ThongBaoDtttcFragment();
//                case 1:
//                    return ketQuaThiFragment;
//                case 2:
//                    return lichThiFragment;
//                case 3:
//                    return radarChartFragment;
//                case 4:
//                    return thongBaoDtttcFragment;
//                case 5:default:
//                    return moreFragment;
            }
            return new ContentTopFragment();
        }
        @Override
        public int getCount() {
                return 4;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 5:
                    break;
                case 4:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    return "Thông báo dttc";
            }
            return "More";
        }
    }
}
