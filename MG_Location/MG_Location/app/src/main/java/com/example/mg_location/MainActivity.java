package com.example.mg_location;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;

import net.daum.mf.map.api.MapView;


public class MainActivity extends AppCompatActivity {

    ActionBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_main);
        bar = getSupportActionBar();
        // bar.hide();
        bar.setLogo(R.drawable.back);
        bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        bar.setTitle("우리동네 동물병원 찾기");

        bar.setDisplayHomeAsUpEnabled(true);


        // java code
        MapView mapView = new MapView(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
