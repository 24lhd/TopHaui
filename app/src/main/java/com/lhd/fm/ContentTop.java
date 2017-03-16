package com.lhd.fm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lhd.tophaui.R;

/**
 * Created by D on 12/03/2017.
 */

public class ContentTop extends Fragment {
    private static final ContentTop ourInstance = new ContentTop();
    private RecyclerView recyclerView;

    public static ContentTop getInstance() {

        return ourInstance;
    }

    public ContentTop() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = new RecyclerView(getActivity());
        recyclerView.setBackgroundColor(getResources().getColor(R.color.colorFrey));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        Category category = (Category) getArguments().getSerializable(FmDanhNgon.ARG_SECTION_NUMBER);
//        ArrayList<Object> objects = new ArrayList<>();
//        objects.addAll((ArrayList<DanhNgon>) getArguments().getSerializable(MainActivity.LIST_DATA));
//        recyclerView.setAdapter(new AdaptorDanhNgon(recyclerView, objects, category,6, (MainActivity) getActivity()));
        return recyclerView;
    }
}
