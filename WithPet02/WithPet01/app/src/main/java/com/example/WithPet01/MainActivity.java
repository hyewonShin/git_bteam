package com.example.WithPet02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.WithPet02.MainView.main_login.MainNotLogIn;
import com.example.WithPet02.view.MainAdSlide;
import com.example.WithPet02.view.MainLogIn;
import com.example.WithPet02.view.login.LoginActivity;
import com.example.WithPet02.view.mypage.MyPageInfoActivity;
import com.example.WithPet02.view.MyPet.MyPetMedicalActivity;
import com.example.WithPet02.view.MyPet.MypetHospital;
import com.example.WithPet02.view.customerc_service.SiteCsActivity;
import com.example.WithPet02.view.mypetinfo.MyPetInfo;
import com.example.WithPet02.view.site.SiteInfoActivity;
import com.example.WithPet02.view.site.SitePetCharActivity;

import java.util.ArrayList;

import static com.example.WithPet02.view.login.LoginActivity.loginDTO;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "mainActivity";

    private DrawerLayout drawerLayout;
    private View drawerView;
    private ViewPager main_login, main_ad;
    private LinearLayout mypetInfo, fitness, check_list, hospital, qna, main_community;
    private ImageView hamburger, loginImage;
    private MenuItem searchbar;
    private Toolbar toolbar;
    ListView listView;
    MainNotLogIn mainNotLogIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkDangerousPermissions();

        //툴바찾기
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        drawerLayout = findViewById(R.id.drawer_layout);
        drawerView = findViewById(R.id.drawer);

        //메인 로그인 화면
        main_login = findViewById(R.id.main_login);

        if(loginDTO != null){
            //로그인시
            main_login.setOffscreenPageLimit(3);
            MainLogIn.MyPagerAdapter loginadapter = new MainLogIn.MyPagerAdapter(getSupportFragmentManager());
            MainLogIn mainLogIn = new MainLogIn();
            mainLogIn.Mli(loginadapter, main_login);
        }



       /* main_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);  //액티비티 이동
            }
        });*/



        //메인 ad(광고) 슬라이드
        main_ad = findViewById(R.id.main_ad);
        main_ad.setOffscreenPageLimit(3); //페이져에게 화면이 3개인 것을 알려줌
        MainAdSlide.MyPagerAdapter pageradapter = new MainAdSlide.MyPagerAdapter(getSupportFragmentManager());
        MainAdSlide adSlide = new MainAdSlide();
        adSlide.Fts(pageradapter, main_ad);


        //메인 햄버거버튼
        hamburger = findViewById(R.id.hamburger);
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        //매인 아이콘 내 동물 정보
        mypetInfo = findViewById(R.id.mypetInfo);
        mypetInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginDTO == null){
                    // 비 로그인시 로그인 화면으로
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                } else {
                    // 커뮤니티 화면!!!!
                    Intent intent = new Intent(getApplicationContext(), MyPetInfo.class);
                    startActivity(intent);
                }
            }
        });

        //매인 아이콘 내 검진기록
        check_list = findViewById(R.id.check_list);
        check_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginDTO == null){
                    // 비 로그인시 로그인 화면으로
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                } else {
                    // 검진기록 화면!!!!
                    Intent intent = new Intent(getApplicationContext(), MyPetMedicalActivity.class);
                    startActivity(intent);
                }
            }
        });

        //매인 아이콘 내 운동기록
        fitness = findViewById(R.id.fitness);
        fitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //테스트용으로 반려동물특징 띄움!!
                if(loginDTO == null){
                    // 비 로그인시 로그인 화면으로
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                } else {
                    // 운동기록 화면!!!!
                    Intent intent = new Intent(getApplicationContext(), SitePetCharActivity.class);
                    startActivity(intent);
                }
            }
        });

        //매인 아이콘 내 가까운 동물병원
        hospital = findViewById(R.id.hospital);
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MypetHospital.class);
                startActivity(intent);
            }
        });

        //매인 아이콘 내 전문가 QnA
        qna = findViewById(R.id.qna);
        qna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 테스트용으로 고객센터 띄움!!
                if(loginDTO == null){
                    // 비 로그인시 로그인 화면으로
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                } else {
                    // QnA 화면!!!!
                    Intent intent = new Intent(getApplicationContext(), SiteCsActivity.class);
                    startActivity(intent);
                }

            }
        });
        //매인 아이콘 내 커뮤니티
        main_community = findViewById(R.id.main_community);
        main_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //테스트용으로 로그인창 띄움!!
                if(loginDTO == null){
                    // 비 로그인시 로그인 화면으로
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                } else {
                    // 커뮤니티 화면!!!!
                    //Intent intent = new Intent(getApplicationContext(), SiteInfoActivity.class);
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });


/*----------------------------------------------------------------------------------------------------------------*/

        loginImage = findViewById(R.id.loginImage);
        loginImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loginDTO == null){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });




        //네비 마이페이지
        LinearLayout nav_mypage = findViewById(R.id.nav_mypage);
        nav_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginDTO == null){
                    // 비 로그인시 로그인 화면으로
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                } else {
                    // 유저 마이페이지 액티비티 만든 후 수정하기!!!!
                    Intent intent = new Intent(getApplicationContext(), MyPageInfoActivity.class);
                    startActivity(intent);
                }
            }
        });

        //네비 아이콘 사이트 소개
        LinearLayout nav_site = findViewById(R.id.nav_site);
        nav_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SiteInfoActivity.class);
                startActivity(intent);
            }
        });


        //네비 아이콘 고객센터
        LinearLayout nav_community = findViewById(R.id.nav_community);
        nav_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginDTO == null){
                    // 비 로그인시 로그인 화면으로
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                } else {
                    // 커뮤니티 화면!!!!
                    Intent intent = new Intent(getApplicationContext(),SiteCsActivity.class);
                    startActivity(intent);
                }
            }
        });


        //Navi 내 ListView
        listView = findViewById(R.id.drawerlistView);
        ArrayList<String> list = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Log.d(TAG, "listviewClick: " + i);
                if(i == 0) {
                    //list내 사이트 소개
                    Intent intent = new Intent(getApplicationContext(),SiteInfoActivity.class);
                    startActivity(intent);
                }else if(i == 1) {
                    //list내 마이페이지
                    if(loginDTO == null) {
                        //비로그인시
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                    }else if(loginDTO != null){
                        //로그인시 마이페이지로 넘어감
                        Intent intent = new Intent(getApplicationContext(), MyPageInfoActivity.class);
                        startActivity(intent);
                    }
                }else if(i == 2) {
                    //list내 커뮤니티
                    if(loginDTO == null) {
                        //비로그인시
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                    }else if(loginDTO != null){
                        //로그인시 커뮤니티 넘어감(없음)
                        Intent intent = new Intent(getApplicationContext(),SiteCsActivity.class);
                        startActivity(intent);
                    }
                }else if( i == 3) {
                    //고객센터
                    if(loginDTO == null) {
                        //비로그인시
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                    }else if(loginDTO != null){
                        //로그인시 고객센터로 넘어감
                        Intent intent = new Intent(getApplicationContext(),SiteCsActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        list.add("사이트 소개");
        list.add("마이페이지");
        list.add("커뮤니티");
        list.add("고객센터");
        adapter.notifyDataSetChanged();




    }

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };

    //옵션바
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.board_list1_action, menu);

        searchbar = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) searchbar.getActionView();
        searchView.setSubmitButtonEnabled(true);






        searchbar.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                ImageView pet_title = findViewById(R.id.pet_title);
                pet_title.setImageResource(0);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {

                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
        return true;
    }

    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
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
