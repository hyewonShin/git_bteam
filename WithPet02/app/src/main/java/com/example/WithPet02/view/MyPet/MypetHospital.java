package com.example.WithPet02.view.MyPet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.Touch;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.WithPet02.R;
import com.example.WithPet02.dto.LocationDTO;
import com.example.WithPet02.view.MyPet.webview.Webview;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MypetHospital extends AppCompatActivity implements MapView.POIItemEventListener, MapView.CurrentLocationEventListener {
    Toolbar toolbar;
    Button nowLocation;
    ArrayList<LocationDTO> dtos;
    MapView mapView;
    ArrayList<LocationDTO> balloon;
    private static final String TAG = "MainActivity";
    String hospital;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypet_hospital);

        //툴바를 액션바 대신 사용
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //지도 띄우기
        mapView = new MapView(this);

        //권한
        checkDangerousPermissions();
        //말풍선 터치 이벤트
        mapView.setPOIItemEventListener((MapView.POIItemEventListener) this);

        //현재위치 찾는거
        nowLocation= findViewById(R.id.nowLocation);
        //트래킹 모드(동물 운동시 움직임과 거리 구하는거) 자동으로 폴리라인 하는거 구하기 7/18.
        //mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeadingWithoutMapMoving);

        //현위치 폴리라인 (강아지가 움직일시 거리 재기위한 폴리라인 자동으로 구해지는거 구해야함 7/18)
        MapPolyline polyline = new MapPolyline();
        polyline.setTag(1000);
        polyline.setLineColor(Color.argb(128,255,51,0));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(35.153523, 126.888039));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(35.146648,126.881132));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(35.13254561,126.8754073));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(35.12791922,126.8832065  ));

        mapView.addPolyline(polyline);

        MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
        int padding = 100; // px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));


        //내현재위치 클릭시 현재위치 띄우기 (자동으로 경도, 위도 찾아보기)
        nowLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d(TAG, "onClick: 1");

               double nowlatitude1 = 35.153523;
               double nowlongtitude1 = 126.888039;
               Log.d(TAG, "onClick: "+nowlatitude1+":"+nowlongtitude1);
                //중심점 변경
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord( 35.153523, 126.888039), true);
                Log.d(TAG, "onClick:2 ");

                showAll();
            }
        });

        //서클뷰 만들어주기
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        //dtos를 메소드로
        dtos =method();


        //범위내에 있는거만 마커찍기!!!
        for(int i=1;i<dtos.size();i++){
            Log.d(TAG, "radius: "+i);
            Log.d(TAG, "radius: 마커찍는중"+i);
            MapPOIItem marker = new MapPOIItem();
            marker.setItemName(dtos.get(i).getName());
            marker.setTag(i);
            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(dtos.get(i).getLatitude(),dtos.get(i).getLongtitude()));
            marker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
            mapView.addPOIItem(marker);
            LocationDTO dto = new LocationDTO(dtos.get(i).getName(),dtos.get(i).getNum(),dtos.get(i).getAddr());
            balloon = new ArrayList<>();
        }
        //지도중심점 마커 표시
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;   //10초 마다 갱신
        float minDistance = 0;  //위치변화가 없더라도(0) 갱신한다
        Double nowlatitude;
        Double nowlongtitude;
        try {
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,minTime,minDistance,gpsListener );  //위성
            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,minTime,minDistance,gpsListener ); //기지국
            Location lastLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            nowlatitude = lastLocation.getLatitude();
            nowlongtitude = lastLocation.getLongitude();
            // 중심점 변경
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(nowlatitude, nowlongtitude), true);
            //현재 위치 마커 표시
            MapPOIItem marker = new MapPOIItem();
            marker.setItemName("현재위치");
            marker.setTag(0);
            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(nowlatitude, nowlongtitude));
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
            mapView.addPOIItem(marker);
            //반경표시
            MapCircle circle1 = new MapCircle(
                    MapPoint.mapPointWithGeoCoord(nowlatitude, nowlongtitude), // center
                    5000, // radius // 반경 5km안에 원 둘레 그리기
                    Color.argb(128, 255, 0, 0), // strokeColor
                    Color.argb(128, 0, 255, 0) // fillColor
            );
            circle1.setTag(1234);
            mapView.addCircle(circle1);
            }catch (Exception e){
            e.printStackTrace();
            }
        }//onCreate()
    //지도 클래스

    //터치시 이벤트 눌러주기
    public void touch(String itemName) {
        if(itemName != null) {
            Intent intent = new Intent(getApplicationContext(), Touch.class);
            startActivity(intent);
        }
    }
    // 줌 크기
    private void showAll() {
        int padding = 20;
        float minZoomLevel = 7;
        float maxZoomLevel = 10;
    }
    //엑셀에서 정보를 가져와 list형식으로 반환하는 메소드
    public ArrayList<LocationDTO> method(){
        ArrayList<LocationDTO> dtos = new ArrayList<>();
        try {
            InputStream is = getBaseContext().getResources().getAssets().open("data.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if(wb != null) {
                Sheet sheet = wb.getSheet(0);   // 시트 불러오기
                if(sheet != null) {
                    int colTotal = sheet.getColumns();    // 전체 컬럼
                    int rowIndexStart = 1;                  // row 인덱스 시작
                    int rowTotal = sheet.getRows();

                    StringBuilder sb;

                    Log.d(TAG, "method: 반복문 전");
                    Log.d(TAG, "method: 칼럼토탈"+colTotal);
                    Log.d(TAG, "method: 로우토탈"+rowTotal);

                    for(int row=rowIndexStart;row<rowTotal;row++) {

                        DecimalFormat formLong = new DecimalFormat("###,#######"); // 경도
                        DecimalFormat formLat = new DecimalFormat("##,########");  // 위도


                        String name = sheet.getCell(0,row).getContents();
                        String addr = sheet.getCell(1,row).getContents();

                        String strLongtitude = formLong.format(Double.parseDouble(sheet.getCell(3,row).getContents())).replace(",",".");
                        String strLatitude = formLat.format(Double.parseDouble(sheet.getCell(4,row).getContents())).replaceAll(",",".");
                        String num = sheet.getCell(2,row).getContents();
                        Double longtitude = Double.parseDouble(strLongtitude);
                        Double latitude = Double.parseDouble(strLatitude);

                        Log.d(TAG, "이름: "+name);
                        Log.d(TAG, "경도: "+longtitude);
                        Log.d(TAG, "위도: "+ latitude);
                        Log.d(TAG, "번호: "+num);

                        dtos.add(new LocationDTO(name,longtitude,latitude,num,addr));

                        Log.d(TAG, "method: "+dtos.size());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "method: catch1 Exception!");
        } catch (BiffException e) {
            e.printStackTrace();
            Log.d(TAG, "method: catch2 Exception!!!");
        }

        return dtos;
    }
    //MapViewPointerListner (마커 터치시 필요한 4개 메소드)
    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        Log.d(TAG, "onPOIItemSelected: 마커가 클릭되었습니다.");
    }
    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        location(dtos,String.valueOf(mapPOIItem.getTag()));
    }
    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
    }
    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    //GPS를 읽어주는 메소드
    private class GPSListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();   //위도
            Double longtitude = location.getLongitude();    //경도
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        @Override
        public void onProviderEnabled(String provider) {
        }
        @Override
        public void onProviderDisabled(String provider) {
        }
    }
        //마커 클릭시 엑셀을 불러와 주소 , 전화번호 가져오는 메소드
        public void location(final ArrayList<LocationDTO> locationDTOArrayList, final String tag) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("상세정보");
            hospital = locationDTOArrayList.get(Integer.parseInt(tag)).getName();
            builder.setMessage("의료기관명: " + locationDTOArrayList.get(Integer.parseInt(tag)).getName() +
                "\n주소: " + locationDTOArrayList.get(Integer.parseInt(tag)).getAddr() +
                "\n전화번호: " + locationDTOArrayList.get(Integer.parseInt(tag)).getNum());
            builder.setIcon(R.drawable.marker);
            builder.setPositiveButton("길찾기", new DialogInterface.OnClickListener() {

                // 다이얼로그 클릭시 웹뷰 띄우기
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(getApplicationContext(), Webview.class);
                intent.putExtra("hospital", hospital);
                startActivity(intent);

                builder.setIcon(android.R.drawable.ic_dialog_alert);
            }
        });
       //다이얼로그 시작
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    // 툴바 뒤로가기 클릭시 액티비티 finish
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home ){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //  권한 (GPS , 위치 정보)
    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,

        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }
}
