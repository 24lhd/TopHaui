package com.lhd.fragment;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by D on 18/02/2017.
 */

public class MapFm extends SupportMapFragment{
    public void  setAsyn(OnMapReadyCallback onMapReadyCallback) {
        getMapAsync(onMapReadyCallback);
    }
}
