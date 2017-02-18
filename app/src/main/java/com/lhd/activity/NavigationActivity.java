package com.lhd.activity;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.lhd.fragment.MapFm;
import com.lhd.myroute.R;

import duong.maps.BanDo;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,OnMapReadyCallback,GoogleMap.OnMyLocationChangeListener {
    public static final String MAP_FRAGMENT = "map_fragment";
    private GoogleMap mMap;
    private MapFm mapFragment;
    private BanDo banDo;
    private Location myLoction;
    private int sizeCamera=12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intiView();

//        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
    }

    /**
     * khởi tạo view ban đầu
     */
    private void intiView() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initObject();

    }

    /**
     * khởi tạo các đối tượng trong app
     */
    private void initObject() {
        mapFragment=new MapFm();
        mapFragment.setAsyn(this);
         banDo=new BanDo();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * khi trả về 1 bản đồ thì cài đặt cơ bản bản đồ và set sự kiện lắng nghe GPS thay đổi
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setOnMyLocationChangeListener(this);
        banDo.caiDatBanDo(mMap);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera)
            setMapFragment();
        else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /**
     * hiện google map
     */
    private void setMapFragment() {
        banDo.checkLocationIsEnable(this,"Bạn chưa bật vị trí","Bạn có muốn bật vị trí của mình");
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_fm,mapFragment);
        transaction.commit();
        diChuyenToiviTriCuaToi();
    }

    /**
     * di chuyển tới vị trí hiện tại
     */
    private void diChuyenToiviTriCuaToi() {
        banDo.diChuyenToiViTri(mMap,myLoction,sizeCamera);
    }

    /**
     * khi vị trí thay đổi sẽ cập nhật vị trí hiện tại . nếu vị trí hiện tại bị null sẽ di chuyển tới vị trí đó
     * @param location
     */
    @Override
    public void onMyLocationChange(Location location) {
        if (myLoction!=null) myLoction =location;
        else{
            myLoction=location;
            banDo.diChuyenToiViTri(mMap,myLoction,sizeCamera);
        }
    }
}
