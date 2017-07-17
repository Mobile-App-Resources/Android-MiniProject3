package com.tistory.puzzleleaf.androidminiproject3.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tistory.puzzleleaf.androidminiproject3.MainActivity;
import com.tistory.puzzleleaf.androidminiproject3.R;
import com.tistory.puzzleleaf.androidminiproject3.item.MarkerData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cmtyx on 2017-07-16.
 */
//MapView 클래스 사용자는 모든 액티비티 수명 주기 메서드를 MapView 클래스의 해당 메서드로 전달해야 합니다.
//수명 주기 메서드의 예로는 onCreate(), onDestroy(), onResume(), onPause() 등이 있습니다.
//MapView 메모 - https://developers.google.com/maps/documentation/android-api/lite?hl=ko#_5

public class MapFragment extends BaseFragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    @BindView(R.id.map) MapView mapView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map,container,false);
        ButterKnife.bind(this,view);
        mapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng mkLatLng = null;
        ArrayList<MarkerData> markerDatas = MainActivity.dbHelper.getResult();
        for(MarkerData temp : markerDatas) {
            mkLatLng = new LatLng(temp.getLatitude(),temp.getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions().position(mkLatLng)
                    .title(temp.getName()).snippet(temp.getAddress()+"\n"+temp.getNumber()+"\n"+temp.getDescription()).snippet("qwe"));
            marker.showInfoWindow();
        }
        if(mkLatLng!=null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mkLatLng,15f));

        }
    }



    //MapView를 위한 수명주기 설정
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mapView!=null){
            mapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


}
