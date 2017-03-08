package com.lhd.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;
import com.lhd.obj.SinhVien;
import com.lhd.tophaui.R;
import com.mancj.slideup.SlideUp;

import java.util.ArrayList;

import duong.AppLog;
import duong.ChucNangPhu;

public class NaviActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String APP_LOG = "app_log";
    public static final String MSV = "msv";
    private static final int CODE_RESULT_LOGIN = 10;
    public static final String SINH_VIEN="sinh vien";
    private static final String LOG_MSV = "log_msv";
    private PackageInfo info;
    private AppLog appLog;
    private ChucNangPhu chucNangPhu;
    private ArrayList<Marker> markerSelects;
    private View sliderView;
    private SlideUp slideUp;
    FloatingActionButton fab;
    private TabLayout tabUI;
    private int tabUISelect;
    private boolean isShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       initViewIntro();
    }

    private void setViewMain() {
        setContentView(R.layout.activity_navi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    /**
     * chạy view intro và khởi tạo log ---- kiểm tra log đã có mã sv chưa
     */
    private void initViewIntro() {
        setContentView(R.layout.intro_layout);
        try {
             appLog=new AppLog();
            appLog.openLog(this,APP_LOG);
            PackageManager manager = getPackageManager();
            info = manager.getPackageInfo(getPackageName(), 0);
            String version = "Phiên bản "+info.versionName;
            TextView tvVersion= (TextView) findViewById(R.id.tv_version);
            tvVersion.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLog();
            }
        }, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==CODE_RESULT_LOGIN){
            if(resultCode == Activity.RESULT_OK){
                SinhVien sinhVien= (SinhVien) data.getSerializableExtra(NaviActivity.SINH_VIEN);
                appLog.putValueByName(this,APP_LOG,LOG_MSV,sinhVien.getMa());
                checkLogAPI(sinhVien.getMa());
            }else if (resultCode ==Activity.RESULT_CANCELED)
                finish();



        }else if (requestCode==1){
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra(MSV);
//                getSV(result);
            }else if (resultCode ==Activity.RESULT_CANCELED)
                finish();

        }
    }

    private void checkLog() {
        String msv=appLog.getValueByName(this,APP_LOG,MSV);
        if (!msv.contains("")){
            setViewMain();
            checkLogAPI("");
        }
        else{
            Intent intentLogin=new Intent(this,LoginActivity.class);
            startActivityForResult(intentLogin,CODE_RESULT_LOGIN);
        }
    }

    private void checkLogDTTC(String msv) {

    }

    private void checkLogAPI(String msv) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

      
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
