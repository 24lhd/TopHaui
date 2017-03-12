package com.lhd.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lhd.config.Config;
import com.lhd.fm.FrameTopFragment;
import com.lhd.obj.He;
import com.lhd.obj.Nganh;
import com.lhd.obj.SinhVien;
import com.lhd.task.TimeTask;
import com.lhd.tophaui.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import duong.AppLog;
import duong.ChucNangPhu;
import duong.http.DuongHTTP;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;

public class NaviActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String APP_LOG = "app_log";
    public static final String MSV = "msv";
    private static final int CODE_RESULT_LOGIN = 10;
    public static final String SINH_VIEN="sinh vien";
    private static final String LOG_MSV = "log_msv";
    private static final String LOG_INFOR_SV = "info_sinh_vien";
    private PackageInfo info;
    private AppLog appLog;
    private String msv;
    private SinhVien sinhVien;
    private TextView tvNameStudent;
    private TextView tvClassStudent;
    private TextView tietView;
    private LinearLayout layoutTime;
    private String soLuotTruyCap;
    private String soNguoiDung;
    private boolean fail;

    /**
     * KHỞI TẠO VIEW INTRO và lấy dữ liệu veef heej vaf nganh
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(FLAG_FULLSCREEN,FLAG_FULLSCREEN);
        getWindow().setFlags(FLAG_TRANSLUCENT_NAVIGATION,FLAG_TRANSLUCENT_NAVIGATION);
       initViewIntro();
    }

    private void setViewMain() {
        getWindow().clearFlags(FLAG_FULLSCREEN);
        setContentView(R.layout.activity_navi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setFitsSystemWindows(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.getHeaderView(0);
         tvNameStudent= (TextView) headerLayout.findViewById(R.id.hd_name_student);
         tvClassStudent= (TextView) headerLayout.findViewById(R.id.hd_name_class_student);
         layoutTime = (LinearLayout) headerLayout.findViewById(R.id.view_time);
        tietView= (TextView) headerLayout.findViewById(R.id.tv_tiet_hientai);
        timeView= (TextView) headerLayout.findViewById(R.id.tv_time_conlai);
        setContentViewHeaderNavi(sinhVien.getTen(),sinhVien.getLop());
        startTimeView();
    }

    /**
     * handle nhận giá trị time hiện tại
     */
    private boolean isCick;
    private TextView timeView;
    private Handler handlertime=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (isCick){
                TimeTask timeTask =new TimeTask(this);
                timeTask.execute();
            }
            String s= (String) msg.obj;
            tietView.setText(s.split("-")[0]);
            timeView.setText(s.split("-")[1]);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * chạy đếm thời gian tiết học
     */
    private void startTimeView() {
        isCick=true;
        layoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCick=!isCick;
                if (isCick){
                    TimeTask timeTask =new TimeTask(handlertime);
                    timeTask.execute();
                }
            }
        });
        TimeTask timeTask =new TimeTask(handlertime);
        timeTask.execute();
    }
    private void setContentViewHeaderNavi(String tenSV, String lopSV) {
        tvNameStudent.setText(tenSV);
        tvClassStudent.setText(lopSV+"\n"+"Tích lũy: "+sinhVien.getTl());
    }

    /**
     * chạy view intro và khởi tạo log ---- kiểm tra log đã có mã sv chưa
     */
    private void initViewIntro() {
        setContentView(R.layout.intro_layout);
        try {
            appLog=new AppLog();
            appLog.openLog(this,APP_LOG);
            msv=appLog.getValueByName(this,APP_LOG,LOG_MSV);
            PackageManager manager = getPackageManager();
            info = manager.getPackageInfo(getPackageName(), 0);
            String version = "Phiên bản "+info.versionName;
            TextView tvVersion= (TextView) findViewById(R.id.tv_version);
            tvVersion.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    String jsonHes = (new DuongHTTP()).getHTTP(Config.GET_HE);
                    String jsonNganhs = (new DuongHTTP()).getHTTP(Config.GET_NGANH);
                    String jsonLuotTruyCap = (new DuongHTTP()).getHTTP(Config.GET_LUOT_TRUY_CAP);
                    setNganhsAndHes(jsonNganhs,jsonHes,jsonLuotTruyCap);
                } catch (Exception e) {}
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                checkLog();
            }
        }.execute();
    }
    private ArrayList<He> hes;
    private ArrayList<Nganh> nganhs;
    private void setNganhsAndHes(String jsonNganhs, String jsonHes, String jsonLuotTruyCap) {
        hes=new ArrayList<>();
        this.fail=false;
        nganhs=new ArrayList<>();
        try {
            JSONObject jsonObjectHes=new JSONObject(jsonHes);
            int jsonObjectHesStatust= jsonObjectHes.getInt("status");
            ChucNangPhu.showLog("jsonObjectHesStatust "+jsonObjectHesStatust);
            if (jsonObjectHesStatust!=1){
                this.fail=true;
                return;
            }
            JSONArray jsonArrayHes=jsonObjectHes.getJSONArray("data");
            for (int i = 0; i < jsonArrayHes.length(); i++) {
                JSONObject jsonObject=jsonArrayHes.getJSONObject(i);
                He he=new He(jsonObject.getString("mahe"),jsonObject.getString("tenhe"));
                hes.add(he);
                ChucNangPhu.showLog(he.toString());
            }
            JSONObject jsonObjectNganhs=new JSONObject(jsonNganhs);
            int jsonObjectNganhsStatust=jsonObjectNganhs.getInt("status");
            JSONArray jsonArrayNganhs=jsonObjectNganhs.getJSONArray("data");
            for (int i = 0; i < jsonArrayNganhs.length(); i++) {
                JSONObject jsonObject=jsonArrayNganhs.getJSONObject(i);
               Nganh nganh=new Nganh(jsonObject.getString("manganh"));
                nganhs.add(nganh);
                ChucNangPhu.showLog(nganh.toString());
            }

            JSONObject jsonObjectLuotTruyCap=new JSONObject(jsonLuotTruyCap);
            int jsonObjectLuotTruyCapStatust=jsonObjectLuotTruyCap.getInt("status");
            JSONArray jsonArrayLuotTruyCap=jsonObjectLuotTruyCap.getJSONArray("data");
            JSONObject jsonObject=jsonArrayLuotTruyCap.getJSONObject(0);
            soLuotTruyCap=jsonObject.getString("soluot");
            soNguoiDung=jsonObject.getString("nguoidadung");
//            Toast.makeText(this,jsonObjectHes.getString("msg")+"\n"+jsonObjectNganhs.getString("msg"),Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            ChucNangPhu.showLog("JSONException setNganhsAndHes");

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==CODE_RESULT_LOGIN){
            if(resultCode == Activity.RESULT_OK){
                 sinhVien= (SinhVien) data.getSerializableExtra(NaviActivity.SINH_VIEN);
                appLog.putValueByName(this,APP_LOG,LOG_MSV,sinhVien.getMa());
                appLog.putValueByName(this,APP_LOG,LOG_INFOR_SV,ChucNangPhu.getJSONByObj(sinhVien));
                msv=sinhVien.getMa();
                checkLogAPI();
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

    /**
     * kiểm tra xem log đã có mã sinh viên hay chưa, chưa có thì chạy lân activity đăng nhập để nhập mã sinh viên
     * nếu có rồi thì chạy giao diện chính và lấy dữ liệu sinh viên ở trong
     */
    private void checkLog() {
        try{
            String jsonSinhVien=appLog.getValueByName(this,APP_LOG,LOG_INFOR_SV);
            if (msv!=null&&jsonSinhVien!=null){
                Gson gson=new Gson();
                sinhVien=gson.fromJson(appLog.getValueByName(this,APP_LOG,LOG_INFOR_SV),SinhVien.class);
                checkLogAPI();
            } else
                startActivityLogin();
        }catch (Exception e){
            appLog.removeAll();
        }


    }

    private void startActivityLogin() {
        Intent intentLogin=new Intent(this,LoginActivity.class);
        startActivityForResult(intentLogin,CODE_RESULT_LOGIN);
        overridePendingTransition(R.anim.left_end, R.anim.right_end);
    }

    private void checkLogDTTC(String msv) {

    }

    private void checkLogAPI() {
        ChucNangPhu.showLog("checkLogAPI "+msv);
        setViewMain();
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
        if (id == R.id.mn_log_out) {
            appLog.removeByName(LOG_MSV);
            appLog.removeByName(LOG_INFOR_SV);
            startActivityLogin();
        } else if (id == R.id.mn_top_he)
            setViewTopHe();
        else if (id == R.id.mn_top_khoa)
            setViewTopHe();
        else if (id == R.id.mn_top_nganh)
            setViewTopHe();
        else if (id == R.id.mn_top_lop)
            setViewTopHe();
        else if (id == R.id.mn_top_he)
            setViewTopHe();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setViewTopHe() {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FrameTopFragment frameTopFragment=new FrameTopFragment();
        transaction.replace(R.id.frame_fragment, frameTopFragment);
        transaction.commit();
    }

}
