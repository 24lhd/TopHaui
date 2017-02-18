package duong.maps;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by D on 18/02/2017.
 */

public class BanDo {
    /**
     * cài đặt bản đồ hiển thị chế độ vễ tinh
     * @param googleMap một đối tượng google map
     */
    public void setBanDoVeTinh(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    /**
     * cài đặt bản đồ ban đầu
     * @param googleMap
     */
    public void caiDatBanDo(GoogleMap googleMap) {
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(false); // không sử dụng buttom my location mặc định của máy
        uiSettings.setMapToolbarEnabled(false); // không sử dụng toolbar hỗ trợ của gg map để bật ứng dụng bản đò
        googleMap.setMyLocationEnabled(true); // sử dụng vị trí của máy
    }
    /**
     * cài đặt bản đồ hiển thị chế độ giao thông
     * @param googleMap một đối tượng google map
     */
    public void setBanDoGiaoThong(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    /**
     * vị trí đã được bật
     * @param context ngữ cảnh gọi
     * @return đúng - bật , sai - tắt
     */
    public  boolean isLocationIsEnable(Context context) {
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}
        return gps_enabled;
    }

    /**
     * kiểm tra đã bật vị trí của máy, và thông báo cửa sổ bật GPS nếu vị trí chwua dc bật thì finish ngữ cảnh
     * @param context ngữ cảnh
     * @param title tiêu đề của thông báo
     * @param msg nội dung
     */
    public  void checkLocationIsEnable(final Context context, String title, String msg) {
        if(!isLocationIsEnable(context)) {
            AlertDialog.Builder turnOnLoactionDialog=new AlertDialog.Builder(context);
            turnOnLoactionDialog.setTitle(title);
            turnOnLoactionDialog.setMessage(msg);
            turnOnLoactionDialog.setPositiveButton("Bật", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    moCuaSoBatGPS(context);
                }
            });
            turnOnLoactionDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if(!isLocationIsEnable(context))  ((Activity) context).finish();
                }
            });
            turnOnLoactionDialog.show();
        }
    }

    /**
     * Di chuyển tới một vị trí trong bản đồ
     * @param googleMap đối tượng GoogleMap
     * @param location vị trí cần di chuyển tới
     * @param sizeZoom độ zoom của bản đồ khi di chuyển tới
     */
    public void diChuyenToiViTri(GoogleMap googleMap, Location location, int sizeZoom) {
        if (location instanceof Location){
            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, sizeZoom);// camera xem vị trí với tham số là kinh vĩ độ và độ zôm
            googleMap.animateCamera(cameraUpdate);
        }
    }
    /**
     * bật activity bật vị trí
     * @param context
     */
    public void moCuaSoBatGPS(Context context) {
        Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }
}
