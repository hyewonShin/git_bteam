package com.example.WithPet02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.WithPet02.CheckDangerousPermissions.Internet;
import com.example.WithPet02.MainView.ad.MainAd1;
import com.example.WithPet02.MainView.ad.MainAd2;
import com.example.WithPet02.MainView.ad.MainAd3;
import com.example.WithPet02.MainView.ad.MainAd4;
import com.example.WithPet02.MainView.ad.MainAd5;
import com.example.WithPet02.MainView.MainAdSlide;
import com.example.WithPet02.MainView.MainLogIn;
import com.example.WithPet02.view.MyPet.MyPetCheckList;
import com.example.WithPet02.view.login.LoginActivity;
import com.example.WithPet02.view.mypage.MyPageInfoActivity;
import com.example.WithPet02.view.MyPet.MypetHospital;
import com.example.WithPet02.view.customerc_service.SiteCsActivity;
import com.example.WithPet02.view.mypetinfo.MyPetInfo;
import com.example.WithPet02.view.mypetinfo.MyPetInfoAddActivity;
import com.example.WithPet02.view.mypetinfo.atask.MyPetListSelect;
import com.example.WithPet02.view.pet_Characteristic.PetCharacteristic;
import com.example.WithPet02.view.site.SiteInfoActivity;
import com.example.WithPet02.view.site.SitePetCharActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.WithPet02.common.CommonMethod.ipConfig;
import static com.example.WithPet02.view.login.LoginActivity.loginDTO;
import static com.example.WithPet02.view.mypetinfo.MyPetInfo.myPetList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "mainActivity";

    private DrawerLayout drawerLayout;
    private View drawerView;
    private ViewPager main_login, main_ad;
    private LinearLayout pet_Characteristic, fitness, check_list, hospital, qna, main_community;
    private ImageView hamburger;
    private MenuItem searchbar;
    private Toolbar toolbar;
    ListView listView;
    TextView notLogin, noPet;
    LinearLayout myPetPager;
    Button logincheck;
    CircleImageView loginImage;
    TextView nickname;
    private long backKeyPressedTime = 0;
    private Toast toast;

    private AdView adView;

    ViewFlipper v_flipper;

    ImageView myPic;
    String filePath = ipConfig + "/app/resources/member/";

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Main activity와 context
        MainActivity activity = MainActivity.this;
        context = MainActivity.this;

        //인터넷 권한 가져오기
        Internet internet = new Internet(context, activity);
        internet.checkDangerousPermissions();

        //툴바찾기
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        drawerLayout = findViewById(R.id.drawer_layout);
        drawerView = findViewById(R.id.drawer);


        //메인화면 내 동물정보
        myPetPager = findViewById(R.id.myPetPager);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_main_login, myPetPager, true);

        MainLogIn mainLogIn = new MainLogIn(context, myPetPager);
        mainLogIn.setMyPetPager();


        //메인 ad(광고) 슬라이드
        final int images[] = {R.drawable.slider1, R.drawable.slider2, R.drawable.slider3, R.drawable.slider4, R.drawable.slider5};

        v_flipper = findViewById(R.id.v_flipper);

        for(int image: images){
            flipperImages(image);
        }
        v_flipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = v_flipper.getDisplayedChild();

                Intent intent;
                switch (position){
                    case 0 :
                        intent = new Intent(getApplicationContext(), Community.class);
                        startActivity(intent);
                        break;

                    case 1 :
                        intent = new Intent(getApplicationContext(), SiteInfoActivity.class);
                        startActivity(intent);
                        break;

                    case 2 :
                        intent = new Intent(getApplicationContext(), SiteCsActivity.class);
                        startActivity(intent);
                        break;

                    case 3 :
                        intent = new Intent(getApplicationContext(), MyPageInfoActivity.class);
                        startActivity(intent);
                        break;

                    case 4 :
                        intent = new Intent(getApplicationContext(), MyPetInfo.class);
                        startActivity(intent);
                        break;
                }
            }
        });




        //메인 햄버거버튼
        hamburger = findViewById(R.id.hamburger);
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        //매인 아이콘 반려 동물 특징 정보
        pet_Characteristic = findViewById(R.id.pet_Characteristic);
        pet_Characteristic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginDTO == null){
                    // 비 로그인시 로그인 화면으로
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                } else {
                    // 반려동물 특징 화면!!!!
                    Intent intent = new Intent(getApplicationContext(), PetCharacteristic.class);
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
                    Intent intent = new Intent(getApplicationContext(), MyPetCheckList.class);
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
                Intent intent = new Intent(getApplicationContext(), Community.class);
                startActivity(intent);
            }
            }
        });


        /*배너광고(테스트)*/
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

/*----------------------------------------------------------------------------------------------------------------*/


        nickname = findViewById(R.id.nickname);
        logincheck = findViewById(R.id.logincheck);
        myPic = findViewById(R.id.myPic);

        if(loginDTO == null){  //로그아웃 상태
            myPic.setImageResource(R.drawable.ic_log_in_40dp);  //로그아웃 이미지

            //logincheck 버튼 눌렀을 때 로그인화면으로 넘어감
            logincheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }else{  //로그인 되었을 때
            logincheck.setText("logout");
            nickname.setText(loginDTO.getM_name() + " 님 반갑습니다.");

            //이미지 서버에서 가져오기
            myPic.setImageResource(0);
            Log.d("nav_drawer", "m_pic: " + loginDTO.getM_pic());
            if(loginDTO.getM_pic() == null){    //사용자 프로필사진 등록 여부 확인
                //프로필 사진없음
                Glide.with(this).load(filePath + "defalt.jpg").into(myPic);
            } else {
                //프로필 사진 있음
                Glide.with(this).load(filePath + loginDTO.getM_pic()).signature(new ObjectKey(System.currentTimeMillis())).into(myPic);
            }

            //logincheck 버튼
            logincheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //자동로그인 파일 내용 삭제
                    SharedPreferences sf = getSharedPreferences("WithPetM", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sf.edit();
                    editor.clear();
                    editor.commit();

                    loginDTO = null;
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
        }


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
                    //고객센터 화면!!!!
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
                    //반려 동물 특징
                    //list내 반려 동물 특징
                    Intent intent = new Intent(getApplicationContext(),PetCharacteristic.class);
                    startActivity(intent);

                }else if(i == 1) {
                    //list내 내 동물정보
                    if(loginDTO == null) {
                        //비로그인시
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                    }else if(loginDTO != null){
                        //list내 내 동물정보
                        Intent intent = new Intent(getApplicationContext(),MyPetInfo.class);
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
                        Intent intent = new Intent(getApplicationContext(),Community.class);
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

        list.add("반려 동물 특징");
        list.add("내 동물 정보");
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

    //메인 광고판
    public void flipperImages(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(4000);    //4sec
        v_flipper.setAutoStart(true);

        //animation
        v_flipper.setInAnimation(this, android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }

    @Override
    protected void onResume() {
        if(loginDTO == null){
            Glide.with(this).load(filePath + "defalt.jpg").into(myPic);
            logincheck.setText("Login");
            nickname.setText("로그인하세요");
        }
        super.onResume();
    }

    //두번 back버튼 누를시 앱종료
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        }
    }


}
