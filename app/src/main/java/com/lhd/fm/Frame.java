package com.lhd.fm;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.lhd.db.SQLiteManager;
import com.lhd.obj.SinhVien;
import com.lhd.tophaui.R;

import java.util.ArrayList;

import static duong.Conections.isOnline;


/**
 * Created by D on 12/19/2016.
 */

public abstract class Frame extends Fragment {
    public static final String KEY_OBJECT = "send_object";
    public static final String KEY_ACTIVITY = "key_start_activity";
    protected RecyclerView recyclerView;
    protected TextView tVnull;
    protected ProgressBar progressBar;
    protected LinearLayout toolbar;
    private WebView webView;
    private  AlertDialog.Builder builder;
    private View viewAd;
    protected SQLiteManager sqLiteManager;
    protected PullRefreshLayout pullRefresh;
    protected SinhVien sv;
    protected ArrayList<Object> objects;

    public void loadNativeExpressAt( NativeExpressAdView nativeExpressAdView) {
        if (isOnline(getActivity()))
        nativeExpressAdView.loadAd(new AdRequest.Builder().build());
    }



    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }
    private LayoutInflater layoutInflater;
    protected abstract void startParser();
    public void cantLoadData() {
        showTextNull();
        tVnull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline(getContext())){
                    showProgress();
                    loadData();
                }else {
                    final Snackbar snackbar= Snackbar.make(recyclerView, "Vui lòng bật kết nối internet!", Snackbar.LENGTH_SHORT);

                    snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    snackbar.setAction("Bật wifi", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            WifiManager wifiManager = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
//                            wifiManager.setWifiEnabled(true);
                            showProgress();
                            snackbar.dismiss();
                           loadData();
                        }
                    });
                    snackbar.show();


                }
            }
        });
    }
    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater=inflater;
        View view=inflater.inflate(R.layout.layout_frame_fragment,container,false);
        initView(view);
        return view;
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        tVnull.setVisibility(View.GONE);
    }
    public void showTextNull() {
        pullRefresh.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        tVnull.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
    public void loadData() {
//        if (!MainActivity.wifiIsEnable(getActivity())&&MainActivity.isOnline(getActivity())){
//            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
//            builder.setTitle("Cảnh báo !");
//            builder.setMessage("Bạn đang sử dụng dữ liệu di động.\n" +
//                    "Tránh tiêu tốn dữ liệu.\nTôi khuyên bạn nên dùng mạng wifi để sử dụng.");
//            builder.setPositiveButton("Bật wifi", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.dismiss();
//                    WifiManager wifiManager = (WifiManager)getActivity().getSystemService(Context.WIFI_SERVICE);
//                    if (!MainActivity.wifiIsEnable(getActivity())) wifiManager.setWifiEnabled(true);
//                    Toast.makeText(getActivity(), "Đã bật wifi", Toast.LENGTH_SHORT).show();
//                }
//            });
//            builder.setNegativeButton("Tiếp tục", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.dismiss();
//                    if (MainActivity.isOnline(getContext())){
//                        showProgress();
//                        startParser();
//                    }else cantLoadData();
//                }
//            });
//            builder.show();
//        }
        if (isOnline(getContext())){
            showProgress();
            startParser();
        }else cantLoadData();

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullRefresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    public void initView(View view) {
        pullRefresh= (PullRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        sqLiteManager=new SQLiteManager(getContext());
//        try {sv= (SinhVien) getArguments().getSerializable(MainActivity.SINH_VIEN);
//        }catch (NullPointerException e){}
        progressBar= (ProgressBar) view.findViewById(R.id.pg_loading);
        tVnull= (TextView) view.findViewById(R.id.text_null);
        recyclerView= (RecyclerView) view.findViewById(R.id.recle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        checkDatabase();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    public ListActivity getListActivity() {
        return listActivity;
    }
    public void setListActivity(ListActivity listActivity) {
        this.listActivity = listActivity;
    }
    private ListActivity listActivity;
    public abstract void setRecyclerView();
    public abstract void checkDatabase();
    public void showRecircleView() {
        pullRefresh.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        tVnull.setVisibility(View.GONE);
    }








}
