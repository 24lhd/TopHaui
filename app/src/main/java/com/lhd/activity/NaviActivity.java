package com.lhd.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lhd.config.Config;
import com.lhd.db.DuLieu;
import com.lhd.fm.FrameMoreFragment;
import com.lhd.fm.FrameTopFragment;
import com.lhd.fm.ThongBaoDtttcFragment;
import com.lhd.obj.He;
import com.lhd.obj.Khoa;
import com.lhd.obj.Lop;
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
import duong.Communication;
import duong.Conections;
import duong.DiaLogThongBao;
import duong.http.DuongHTTP;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;

public class NaviActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String APP_LOG = "app_log";
    public static final String MSV = "msv";
    private static final int CODE_RESULT_LOGIN = 10;
    public static final String SINH_VIEN="sinh vien";
    private static final String LOG_INFOR_SV = "info_sinh_vien";
    public static final String KEY_TOP_HE = "top he";
    private FrameMoreFragment frameMoreFragment;

    public ArrayList<Lop> getLops() {
        return lops;
    }

    public static final String KEY_TOP_KHOA = "top khoa";
    public static final String KEY_TOP_NGANH = "top nganh";
    public static final String KEY_TOP_LOP = "top lop";
    private static final String STATE_UI = "state_ui";
    private PackageInfo info;
    private AppLog appLog;
    private SinhVien sinhVien;
    private TextView tvNameStudent;
    private TextView tvClassStudent;
    private TextView tietView;
    private LinearLayout layoutTime;
    private String soLuotTruyCap;
    private String soNguoiDung;
    private boolean fail;
    private TabLayout tabUI;
    private AlertDialog alertDialog;
    private int tabUISelect;
    private Toolbar toolbar;
    private int colorApp;
    private int tab_select_color;
    private DuLieu duLieu;
    private FrameLayout frameLayout;
    private FrameTopFragment frameTopFragment;
    private ArrayList<Khoa> khoas;
    private ThongBaoDtttcFragment thongBaoDtttcFragment;
    private ArrayList<Lop> lops;

    /**
     * KHỞI TẠO VIEW INTRO và lấy dữ liệu veef heej vaf nganh
     * @param savedInstanceState
     * kiểm tra xem log đã có sinh viên hay chưa nếu
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLog();

    }

    private void setViewMain() {
        getWindow().clearFlags(FLAG_FULLSCREEN);
        setContentView(R.layout.activity_navi);
        frameTopFragment=new FrameTopFragment();
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {}
            @Override
            public void onDrawerOpened(View drawerView) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            @Override
            public void onDrawerStateChanged(int newState) {}
        });
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
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }else{
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        }
        frameLayout= (FrameLayout) findViewById(R.id.frame_fragment);
        setUI(appLog.getValueByName(NaviActivity.this,STATE_UI,"StatusBar"),
                appLog.getValueByName(NaviActivity.this,STATE_UI,"toolbar"),
                appLog.getValueByName(NaviActivity.this,STATE_UI,"tab_selecter"),
                appLog.getValueByName(NaviActivity.this,STATE_UI,"frameLayout"));
        setViewTop(KEY_TOP_HE);
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
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_noti_dttc) {
            setViewMoreFragmetnt();
            return true;
        }else  if (id == R.id.action_view_change_ui) {
            showDialogSetUI();
            return true;
        }
        return false;
    }

    private void setViewMoreFragmetnt() {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         frameMoreFragment=new FrameMoreFragment();
        transaction.replace(R.id.frame_fragment, frameMoreFragment);
        transaction.commitAllowingStateLoss();
    }


    private void showDialogSetUI() {
        alertDialog = null;
        LayoutInflater layoutInflater=getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.layout_change_ui,null);
        tabUI= (TabLayout) view.findViewById(R.id.tab_ui_layout);
        tabUI.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabUI.setTabGravity(TabLayout.GRAVITY_FILL);
        tabUI.setSelectedTabIndicatorColor(Color.WHITE);
        ((ImageButton) view.findViewById(R.id.imb_close_change_ui)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        TabLayout.Tab tabStatus=tabUI.newTab();
        tabStatus.setText("Status Bar");
        tabUI.addTab(tabStatus);
        TabLayout.Tab tabBar=tabUI.newTab();
        tabBar.setText("Action Bar");
        tabUI.addTab(tabBar);
        TabLayout.Tab tabCategory=tabUI.newTab();
        tabCategory.setText("Tab Bar");
        tabUI.addTab(tabCategory);
        TabLayout.Tab tabBg=tabUI.newTab();
        tabBg.setText("Background");
        tabUI.addTab(tabBg);
        tabUI.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabUISelect=tab.getPosition();
            }
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        tabUI.getTabAt(0).select();
        alertDialog= DiaLogThongBao.createDiaLogView(this, view, null , null, null,
                getResources().getColor(R.color.colorPrimary),null, null);
        alertDialog.show();
        tabUISelect=0;
    }
    /**
     * chạy đếm thời gian tiết học
     */
    private void startTimeView() {
        isCick=true;
        layoutTime.setPadding(0,getStatusBarHeight(),getStatusBarHeight(),0);
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

    /**
     * set thông tin của sinh viên lên header view khi đã có thông tin
     * @param tenSV
     * @param lopSV
     */
    private void setContentViewHeaderNavi(String tenSV, String lopSV) {
        tvNameStudent.setText(tenSV);
        tvClassStudent.setText(lopSV+" "+sinhVien.getK()+" ("+sinhVien.getNbatdau()+")"+"\n"+"Tích lũy: "+sinhVien.getTl());
    }

    /**
     * chạy view intro và khởi tạo log ---- kiểm tra log đã có mã sv chưa
     */
    public void initViewIntro() {
        setContentView(R.layout.intro_layout);
        try {
            getWindow().setFlags(FLAG_FULLSCREEN,FLAG_FULLSCREEN);
            getWindow().setFlags(FLAG_TRANSLUCENT_NAVIGATION,FLAG_TRANSLUCENT_NAVIGATION);
            RelativeLayout relativeLayout= (RelativeLayout) findViewById(R.id.layout_intro);
            tab_select_color=getResources().getColor(R.color.colorPrimary);
            colorApp=getResources().getColor(R.color.colorPrimary);
            if (appLog.getValueByName(this,STATE_UI,"toolbar")!=null){
                colorApp=Integer.parseInt(appLog.getValueByName(this,STATE_UI,"toolbar"));
                relativeLayout.setBackgroundColor(colorApp);
            }
            appLog.openLog(this,APP_LOG);
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
                    String jsonKhoas = (new DuongHTTP()).getHTTP(Config.GET_KHOA);
                    String jsonLops = (new DuongHTTP()).getHTTP(Config.GET_LOP);
                    setNganhsAndHes(jsonNganhs,jsonHes,jsonLuotTruyCap,jsonKhoas,jsonLops);
                } catch (Exception e) {
                    fail=true;
                    ChucNangPhu.showLog("Exception doInBackground");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                ChucNangPhu.showLog("checkLog");
                setViewMain();
            }
        }.execute();

    }
    private ArrayList<He> hes;
    private ArrayList<Nganh> nganhs;

    public String getSoLuotTruyCap() {
        return soLuotTruyCap;
    }

    public String getSoNguoiDung() {
        return soNguoiDung;
    }

    public ArrayList<He> getHes() {
        return hes;
    }

    public ArrayList<Nganh> getNganhs() {
        return nganhs;
    }

    public ArrayList<Khoa> getKhoas() {
        return khoas;
    }

    private void setNganhsAndHes(String jsonNganhs, String jsonHes, String jsonLuotTruyCap, String jsonKhoas,String jsonLops) {
        this.fail=false;
        try {
            // dịch các dữ liệu lấy từ api và trả về 1 mảng những đối tượng
            hes=Config.getHesByJson(jsonHes);
            nganhs=Config.getNganhByJson(jsonNganhs);
            khoas=Config.getKhoaByJson(jsonKhoas);
            lops=Config.getLopByJson(jsonLops);
            // chèn json dữ liệu vào csdl
            duLieu.insertTopHe(jsonHes);
            duLieu.insertTopKhoa(jsonKhoas);
            duLieu.insertTopLop(jsonLops);
            duLieu.insertTopNganh(jsonNganhs);
            //lấy cá lượt truy cập và ng dùng
            JSONObject jsonObjectLuotTruyCap=new JSONObject(jsonLuotTruyCap);
            JSONArray jsonArrayLuotTruyCap=jsonObjectLuotTruyCap.getJSONArray("data");
            JSONObject jsonObject=jsonArrayLuotTruyCap.getJSONObject(0);
            soLuotTruyCap=jsonObject.getString("soluot");
            soNguoiDung=jsonObject.getString("nguoidadung");
//            Toast.makeText(this,jsonObjectHes.getString("msg")+"\n"+jsonObjectNganhs.getString("msg"),Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            ChucNangPhu.showLog("JSONException setNganhsAndHes khong lay duoc du liẹu");
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==CODE_RESULT_LOGIN){
            if(resultCode == Activity.RESULT_OK){
                 sinhVien= (SinhVien) data.getSerializableExtra(NaviActivity.SINH_VIEN);
                appLog.putValueByName(this,APP_LOG,LOG_INFOR_SV,ChucNangPhu.getJSONByObj(sinhVien));
               initViewIntro();// nếu có dữ liệu trả về từ LoginAcivity thì chạy chương trình chính
            }else if (resultCode ==Activity.RESULT_CANCELED)
                finish();
        }else if (requestCode==1){
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra(MSV);
            }else if (resultCode ==Activity.RESULT_CANCELED)
                finish();

        }
    }

    public void setFail(boolean fail) {
        this.fail = fail;
    }

    public boolean isFail() {
        return fail;
    }

    /**
     * kiểm tra xem log đã có mã sinh viên hay chưa, chưa có thì chạy lân activity đăng nhập để nhập mã sinh viên
     * nếu có rồi thì chạy giao diện chính và lấy dữ liệu sinh viên ở trong
     *
     *
     */
    private void checkLog() {
        try{
            appLog=new AppLog();
            duLieu=new DuLieu(this);
            String jsonSinhVien=appLog.getValueByName(this,APP_LOG,LOG_INFOR_SV);
            if ( jsonSinhVien!=null){ // nếu có sinh viên thì lấy các dữ liệu cần thiêt về
                Gson gson=new Gson();
                sinhVien=gson.fromJson(appLog.getValueByName(this,APP_LOG,LOG_INFOR_SV),SinhVien.class);
                initViewIntro();
               // nếu có dữ liệu ở log thì chạy chương trình chính
            } else{
                ChucNangPhu.showLog("else checkLog");
                startActivityLogin();
            }

        }catch (Exception e){
            ChucNangPhu.showLog("Exception checkLog");
            startActivityLogin();
        }
    }

    /**
     * bật activity đăng nhập, nếu online thì xóa hết dữ liệu ở log
     */
    private void startActivityLogin() {

        Intent intentLogin=new Intent(this,LoginActivity.class);
        startActivityForResult(intentLogin,CODE_RESULT_LOGIN);
        overridePendingTransition(R.anim.left_end, R.anim.right_end);
    }

    private void checkLogDTTC(String msv) {

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
            if (Conections.isOnline(this))
                appLog.removeByName(this,APP_LOG,LOG_INFOR_SV);
            startActivityLogin();
        } else if (id == R.id.mn_top_he)
            setViewTop(KEY_TOP_HE);
        else if (id == R.id.mn_top_khoa)
            setViewTop(KEY_TOP_KHOA);
        else if (id == R.id.mn_top_nganh)
            setViewTop(KEY_TOP_NGANH);
        else if (id == R.id.mn_top_lop)
            setViewTop(KEY_TOP_LOP);
        else if (id == R.id.mn_ca_nhan)
            setViewTop(KEY_TOP_HE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public SinhVien getSinhVien() {
        return sinhVien;
    }

    private void setViewTop(String keyTop) {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         frameTopFragment=new FrameTopFragment();
        Bundle bundle=new Bundle();
        bundle.putString(FrameTopFragment.KEY_CONTENT,keyTop);
        frameTopFragment.setArguments(bundle);
        transaction.replace(R.id.frame_fragment, frameTopFragment);
        transaction.commitAllowingStateLoss();
//        transaction.commit();
    }
    private void setUI(String status, String bar, String tabs , String bg) {
        if (status!=null) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(Integer.parseInt(status));
        if (bar!=null){
            colorApp=Integer.parseInt(bar);
            toolbar.setBackgroundColor(colorApp);
        }
        if (tabs!=null) {
//            if (frameTopFragment!=null)
//                frameTopFragment.getTabLayout().setBackgroundColor(tab_select_color);
            tab_select_color=Integer.parseInt(tabs);
        }
        if (bg!=null) frameLayout.setBackgroundColor(Integer.parseInt(bg));
    }
    private void loadColorTab() {
        if (frameTopFragment!=null)
        frameTopFragment.getTabLayout().setBackgroundColor(tab_select_color);
        tabUI.setBackgroundColor(tab_select_color);
    }

    public int getTab_select_color() {
        return tab_select_color;
    }

    /**
     *  phương thức nơi các button màu dc chọn và set  cho các đối tượng
     * @param v
     */
    public void onSelectUI(View v) {
        Button button= (Button) v;
        ColorDrawable buttonColor = (ColorDrawable) button.getBackground();
        switch (tabUISelect){
            case 1:
                toolbar.setBackgroundColor(buttonColor.getColor());
                colorApp=buttonColor.getColor();
                appLog.putValueByName(this,STATE_UI,"toolbar",""+buttonColor.getColor());
                break;
            case 2:
                 tab_select_color=buttonColor.getColor();
                appLog.putValueByName(this,STATE_UI,"tab_selecter",""+buttonColor.getColor());
                loadColorTab();
                break;
            case 3:
                frameLayout.setBackgroundColor(buttonColor.getColor());
                appLog.putValueByName(this,STATE_UI,"frameLayout",""+buttonColor.getColor());
                break;
            default:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(buttonColor.getColor());
                    appLog.putValueByName(this,STATE_UI,"StatusBar",""+buttonColor.getColor());
                }else Communication.showToast(this,"Phiên bản hệ điều hành không hỗ trợ");
                break;
        }
    }

}
