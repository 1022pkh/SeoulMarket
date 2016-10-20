package com.project.seoulmarket.detail.maps;

import android.os.Bundle;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.project.seoulmarket.detail.maps.naver.NMapPOIflagType;
import com.project.seoulmarket.detail.maps.naver.NMapViewerResourceProvider;

public class MapActivity extends NMapActivity {


    private NMapView mMapView;
    private String clientId = "DmRSEpOBaGz0reHEHBXD";
    private NMapController mMapController;
    private NMapViewerResourceProvider mMapViewerResourceProvider;
    private NMapOverlayManager mOverlayManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_map);

//        getActionBar().show();

//        getSupportActionBar().setDisplayShowHomeEnabled(false);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//
////        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
////        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
//
//        // ActionBar의 배경색 변경
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));
//
//        getSupportActionBar().setElevation(0); // 그림자 없애기
//
//        LayoutInflater mInflater = LayoutInflater.from(this);
//        View mCustomView = mInflater.inflate(R.layout.actionbar_map_layout, null);
//
//        ImageView closeBtn = (ImageView) mCustomView.findViewById(R.id.closeMap);
//
//        closeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//
//        getSupportActionBar().setCustomView(mCustomView);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);


//        mMapView = (NMapView)findViewById(R.id.mapView);

        mMapView = new NMapView(this);
    // set Client ID for Open MapViewer Library
        mMapView.setClientId(clientId);

        // initialize map view
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();


        // set the activity content to the map view
        setContentView(mMapView);


        mMapView.setScalingFactor(2.5F, false);


        // use map controller to zoom in/out, pan and set map center, zoom level etc.
        mMapController = mMapView.getMapController();

        NGeoPoint point = new NGeoPoint(127.0793428,37.5407625);
        mMapController.animateTo(point);


        /**
         * 오버레이 표시
//         */
//        // create resource provider

        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);

        // create overlay manager
        mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);

        int markerId = NMapPOIflagType.PIN;

        // set POI data
        NMapPOIdata poiData = new NMapPOIdata(2, mMapViewerResourceProvider);
        poiData.beginPOIdata(2);
        poiData.addPOIitem(127.0793428,37.5407625, null, markerId, 0);
        poiData.endPOIdata();

        // create POI data overlay
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
    }


}
