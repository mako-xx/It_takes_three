package com.example.ittakesthree.map;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.ServiceSettings;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.example.ittakesthree.R;
import com.example.ittakesthree.map.adapter.BusResultListAdapter;
import com.example.ittakesthree.overlay.DrivingRouteOverlay;
import com.example.ittakesthree.overlay.WalkRouteOverlay;
import com.example.ittakesthree.util.ToastUtil;
import com.example.ittakesthree.util.AMapUtil;

import java.util.List;

public class RouteMapActivity extends Activity implements AMap.OnMapClickListener,
        AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter,
        RouteSearch.OnRouteSearchListener {

    private AMap aMap;
    private MapView mapView;
    private Context mContext;
    private RouteSearch routeSearch;
    private DriveRouteResult driveRouteResult;
    private BusRouteResult busRouteResult;
    private WalkRouteResult walkRouteResult;
    private LatLonPoint startPoint = new LatLonPoint(39.942295, 116.335891);//?????????116.335891,39.942295
    private LatLonPoint endPoint = new LatLonPoint(39.995576, 116.481288);//?????????116.481288,39.995576
    private LatLonPoint startPoint_bus = new LatLonPoint(40.818311, 111.670801);//?????????111.670801,40.818311
    private LatLonPoint endPoint_bus = new LatLonPoint(44.433942, 125.184449);//?????????
    private String currentCityName = "??????";
    private static final int ROUTE_TYPE_BUS = 1;
    private static final int ROUTE_TYPE_DRIVE = 2;
    private static final int ROUTE_TYPE_WALK = 3;
    private static final int ROUTE_TYPE_CROSSTOWN = 4;

    private LinearLayout busResultLayout;
    private RelativeLayout bottomLayout;
    private TextView routeTimeDes, routeDetailDes;
    private ImageView bus;
    private ImageView drive;
    private ImageView walk;
    private ListView busResultList;
    private ProgressDialog progDialog = null;// ??????????????????

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        MapsInitializer.updatePrivacyShow(getApplicationContext(),true,true);
        MapsInitializer.updatePrivacyAgree(getApplicationContext(),true);
        ServiceSettings.updatePrivacyShow(getApplicationContext(),true,true);
        ServiceSettings.updatePrivacyAgree(getApplicationContext(),true);
        final Intent intent = getIntent();
        double startLat = intent.getDoubleExtra("startLat", 0);
        double startLon = intent.getDoubleExtra("startLon", 0);
        double endLat = intent.getDoubleExtra("endLat", 0);
        double endLon = intent.getDoubleExtra("endLon", 0);
        startPoint = new LatLonPoint(startLat, startLon);
        endPoint = new LatLonPoint(endLat, endLon);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_map);
        mContext = this.getApplicationContext();
        mapView = findViewById(R.id.route_map);
        mapView.onCreate(savedInstanceState);
        init();
        setfromandtoMarker();
    }

    private void setfromandtoMarker() {
        aMap.addMarker(new MarkerOptions()
                .position(AMapUtil.convertToLatLng(startPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start)));
        aMap.addMarker(new MarkerOptions()
                .position(AMapUtil.convertToLatLng(endPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.end)));
    }

    /** ????????????????????? */
    private void init() {
        if(aMap == null)
            aMap = mapView.getMap();
        registerListener();
        try {
            routeSearch = new RouteSearch(this);
            routeSearch.setRouteSearchListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        bottomLayout = findViewById(R.id.bottom_layout);
        busResultLayout = findViewById(R.id.bus_result);
        routeTimeDes = findViewById(R.id.firstline);
        routeDetailDes = findViewById(R.id.secondline);
        drive = findViewById(R.id.route_drive);
        bus = findViewById(R.id.route_bus);
        walk = findViewById(R.id.route_walk);
        busResultList = findViewById(R.id.bus_result_list);
    }

    /** ??????????????? */
    private void registerListener() {
        aMap.setOnMapClickListener(RouteMapActivity.this);
        aMap.setOnMarkerClickListener(RouteMapActivity.this);
        aMap.setOnInfoWindowClickListener(RouteMapActivity.this);
        aMap.setInfoWindowAdapter(RouteMapActivity.this);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    /**
     * ??????????????????????????????
     * @param busRouteResult ????????????????????????
     * @param code ?????????
     * */
    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int code) {
        bottomLayout.setVisibility(View.GONE);
        aMap.clear();
        List<BusPath> paths;
        if(code == AMapException.CODE_AMAP_SUCCESS)
        {
            if(busRouteResult != null && (paths = busRouteResult.getPaths()) != null)
            {
                if(paths.size() > 0)
                {
                    this.busRouteResult = busRouteResult;
                    BusResultListAdapter busResultListAdapter = new BusResultListAdapter(mContext, busRouteResult);
                    busResultList.setAdapter(busResultListAdapter);
                }
                else if(busRouteResult != null && paths == null)
                {
                    ToastUtil.show(mContext, "??????????????????????????????");
                }
            }
            else
            {
                ToastUtil.show(mContext, "??????????????????????????????");
            }
        }
        else
        {
            ToastUtil.showerror(this.getApplicationContext(), code);
        }
    }

    /**
     * ????????????????????????
     * @param driveRouteResult ????????????
     * @param code ?????????
     * */
    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int code) {
        aMap.clear();
        if(code == AMapException.CODE_AMAP_SUCCESS)
        {
            if(driveRouteResult != null && driveRouteResult.getPaths() != null)
            {
                if(driveRouteResult.getPaths().size() > 0) {
                    this.driveRouteResult = driveRouteResult;
                    DrivePath drivePath = driveRouteResult.getPaths().get(0);
                    if (drivePath == null)
                        return;

                    DrivingRouteOverlay drivingRouteOverlay =
                            new DrivingRouteOverlay(mContext, aMap, drivePath,
                                    driveRouteResult.getStartPos(), driveRouteResult.getTargetPos(),
                                    null);
                    drivingRouteOverlay.setNodeIconVisibility(false);
                    drivingRouteOverlay.setIsColorfulline(true);
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                    bottomLayout.setVisibility(View.VISIBLE);
                    int dis = (int) drivePath.getDistance();
                    int dur = (int) drivePath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
                    routeTimeDes.setText(des);
                    routeDetailDes.setVisibility(View.VISIBLE);
                    //int taxiCost = (int) driveRouteResult.getTaxiCost();

                    bottomLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext,
                                    DriveRouteDetailActivity.class);
                            intent.putExtra("drive_path", drivePath);
                            intent.putExtra("drive_result",
                                    driveRouteResult);
                            startActivity(intent);
                        }
                    });


                }
                else if(driveRouteResult != null && driveRouteResult.getPaths() == null)
                    ToastUtil.show(mContext,  "???????????????????????????????????????");
            }
            else
                ToastUtil.show(mContext, "???????????????????????????????????????");
        }
        else
            ToastUtil.showerror(mContext, code);
    }

    /**
     * ??????????????????
     * @param walkRouteResult ????????????
     * @param code ?????????
     * */
    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int code) {
        aMap.clear();
        if(code == AMapException.CODE_AMAP_SUCCESS)
        {
            if(walkRouteResult != null && walkRouteResult.getPaths() != null)
            {
                if(walkRouteResult.getPaths().size() > 0)
                {
                    this.walkRouteResult = walkRouteResult;
                    WalkPath walkPath = walkRouteResult.getPaths().get(0);
                    if(walkPath == null)
                        return;
                    WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(this, aMap,
                            walkPath, walkRouteResult.getStartPos(), walkRouteResult.getTargetPos());
                    walkRouteOverlay.removeFromMap();
                    walkRouteOverlay.addToMap();
                    walkRouteOverlay.zoomToSpan();
                    bottomLayout.setVisibility(View.VISIBLE);
                    int dis = (int) walkPath.getDistance();
                    int dur = (int) walkPath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
                    routeTimeDes.setText(des);
                    routeDetailDes.setVisibility(View.GONE);

                    bottomLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext,
                                    WalkRouteDetailActivity.class);
                            intent.putExtra("walk_path", walkPath);
                            intent.putExtra("walk_result",
                                    walkRouteResult);
                            startActivity(intent);
                        }
                    });



                }
                else if(walkRouteResult != null && walkRouteResult.getPaths() == null){
                    ToastUtil.show(mContext, "???????????????????????????????????????");
                }
            }
            else
                ToastUtil.show(mContext, "???????????????????????????????????????");
        }
        else
            ToastUtil.showerror(this.getApplicationContext(), code);
    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    public void onDriveClick(View view) {
        searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT);
        drive.setImageResource(R.drawable.route_drive_select);
        bus.setImageResource(R.drawable.route_bus_normal);
        walk.setImageResource(R.drawable.route_walk_normal);
        mapView.setVisibility(View.VISIBLE);
        busResultLayout.setVisibility(View.GONE);
    }

    public void onBusClick(View view) {
        searchRouteResult(ROUTE_TYPE_BUS, RouteSearch.BUS_DEFAULT);
        drive.setImageResource(R.drawable.route_drive_normal);
        bus.setImageResource(R.drawable.route_bus_select);
        walk.setImageResource(R.drawable.route_walk_normal);
        mapView.setVisibility(View.GONE);
        busResultLayout.setVisibility(View.VISIBLE);

    }

    public void onWalkClick(View view) {
        searchRouteResult(ROUTE_TYPE_WALK, RouteSearch.WALK_DEFAULT);
        drive.setImageResource(R.drawable.route_drive_normal);
        bus.setImageResource(R.drawable.route_bus_normal);
        walk.setImageResource(R.drawable.route_walk_select);
        mapView.setVisibility(View.VISIBLE);
        busResultLayout.setVisibility(View.GONE);
    }

    public void onCrosstownBusClick(View view) {
    }

    /** ????????????????????????
     * @param routeType ????????????
     * @param mode ??????????????????
     * */
    public void searchRouteResult(int routeType, int mode)
    {
        if(startPoint == null)
        {
            ToastUtil.show(mContext, "???????????????");
            return;
        }
        if(endPoint == null)
        {
            ToastUtil.show(mContext, "???????????????");
            return;
        }
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(startPoint, endPoint);
        if(routeType == ROUTE_TYPE_BUS)
        {
            RouteSearch.BusRouteQuery query = new RouteSearch.BusRouteQuery(fromAndTo, mode,
                    currentCityName, 0);
            routeSearch.calculateBusRouteAsyn(query);
        }
        else if(routeType == ROUTE_TYPE_DRIVE)
        {
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null, null, "");
            routeSearch.calculateDriveRouteAsyn(query);
        }
        else if(routeType == ROUTE_TYPE_WALK)
        {
            RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo);
            routeSearch.calculateWalkRouteAsyn(query);
        }

    }
}