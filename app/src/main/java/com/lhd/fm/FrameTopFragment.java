package com.lhd.fm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lhd.activity.NaviActivity;
import com.lhd.tophaui.R;

/**
 * Created by D on 12/03/2017.
 */

public class FrameTopFragment extends Fragment{
    private LayoutInflater inflater;
    private NaviActivity mainActivity;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater=inflater;
        mainActivity= (NaviActivity) getActivity();
        rootView = inflater.inflate(R.layout.frame_top_frament, container, false);
        initView();
        return rootView;
    }

    private void initView() {

    }
}
