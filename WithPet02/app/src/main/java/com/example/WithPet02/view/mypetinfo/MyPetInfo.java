package com.example.WithPet02.view.mypetinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.WithPet02.Adapter.GridRecyclerViewAdapter;
import com.example.WithPet02.MainActivity;
import com.example.WithPet02.R;
import com.example.WithPet02.dto.AlbumDTO;
import com.example.WithPet02.dto.MyPetDTO;
import com.example.WithPet02.view.join.JoinActivity;
import com.example.WithPet02.view.login.LoginActivity;
import com.example.WithPet02.view.mypetinfo.atask.AlbumListSelect;
import com.example.WithPet02.view.mypetinfo.atask.MyPetListSelect;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.WithPet02.common.CommonMethod.ipConfig;
import static com.example.WithPet02.view.login.LoginActivity.loginDTO;

public class MyPetInfo extends AppCompatActivity {

    public static ArrayList<MyPetDTO> myPetList = null;
    ArrayList<AlbumDTO> albumList;

    AlbumListSelect albumListSelect;
    ViewPager pager;

    RecyclerView recyclerView;
    GridRecyclerViewAdapter gridAdapter;
    GridLayoutManager gridLayoutManager;

    LinearLayout hasPet, noPet;

    Context context;

    String animal;
    int cur = 0;

    ImageView btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pet_info);

        context = this;

        //툴바
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        hasPet = findViewById(R.id.hasPet);
        noPet = findViewById(R.id.noPet);

        //DB 에서 나의 동물 정보 목록 가져옴
        MyPetListSelect myPetListSelect = new MyPetListSelect(loginDTO.getM_tel());
        try {
            myPetListSelect.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(myPetList.size() > 0) {
            //뷰페이저
            pager = findViewById(R.id.viewpager);
            pager.setOffscreenPageLimit(3);
            pager.setClipToPadding(false);
            int dpValue = 15;
            float d = getResources().getDisplayMetrics().density;
            int margin = (int) (dpValue * d);
            pager.setPadding(margin + 30,40, margin + 30, 0);
            pager.setPageMargin(40);

            //ViewPager 를 Adapter 에 연결
            MyPagerAdapter adapter = new MyPagerAdapter(this);
            pager.setAdapter(adapter);

            //ViewPager 이벤트 처리용 리스너 설정
            ViewPagerChangeListener viewPagerChangeListener = new ViewPagerChangeListener(this);
            pager.addOnPageChangeListener(viewPagerChangeListener);

            //동물별 앨범 리스트 가져오기
            albumListSelect = new AlbumListSelect(myPetList.get(0).getP_num());
            try {
                albumList = albumListSelect.execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            //리사이클러뷰
            recyclerView = findViewById(R.id.petAlbum);
            gridAdapter = new GridRecyclerViewAdapter(getApplicationContext(), albumList);
            gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(gridAdapter);


            //앨범사진 등록하기
            btnUpload = findViewById(R.id.btnUpload);
            btnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AlbumAddActivity.class);
                    intent.putExtra("a_pet", myPetList.get(cur).getP_num());
                    startActivity(intent);
                }
            });
        } else {
            hasPet.setVisibility(View.GONE);
            noPet.setVisibility(View.VISIBLE);
        }




    }

    // 툴바 메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_pet_menu, menu);
        return true;
    }

    // 툴바 메뉴 선택
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar 의 back 키 눌렀을 때 동작
                finish();
                return true;
            }
            case R.id.btnPetInsert:{    //동물 정보 추가 화면으로
                Intent intent = new Intent(context, MyPetInfoAddActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    class MyPagerAdapter extends PagerAdapter {

        Context mContext = null;
        LayoutInflater mInflater;

        //생성자로 Inflater 참조변수 세팅
        public MyPagerAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return myPetList.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            //return super.instantiateItem(container, position);
            cur = position;

            View view = null;
            view = mInflater.inflate(R.layout.layout_my_pet_info, null);

            //layout_my_pet_info.xml에서 찾기
            ImageView myPetPic = view.findViewById(R.id.myPetPic);
            TextView myPetName = view.findViewById(R.id.myPetName);
            TextView myPetGen = view.findViewById(R.id.myPetGen);
            TextView myPetBirth = view.findViewById(R.id.myPetBirth);
            TextView myPetAni = view.findViewById(R.id.myPetAni);

            String filePath = ipConfig + "/app/resources/upload/pet/";

            //동물 사진 설정
            myPetPic.setImageResource(R.drawable.a_defalt);
            if(myPetList.get(position).getP_pic() != null){
                Glide.with(mContext).load(filePath + myPetList.get(position).getP_pic()).signature(new ObjectKey(System.currentTimeMillis())).into(myPetPic);
            }

            //동물별 정보 가져오기
            myPetName.setText(myPetList.get(position).getP_name());
            myPetGen.setText(myPetList.get(position).getP_gender());
            String birth = myPetList.get(position).getP_birth().substring(0, 10).replace("-", ".");
            myPetBirth.setText(birth);
            animal = myPetList.get(position).getP_animal();
            if(myPetList.get(position).getP_a_animal() != null){
                animal += "-" + myPetList.get(position).getP_a_animal();
            }
            myPetAni.setText(myPetList.get(position).getP_animal());

            //동물 정보 수정화면 버튼
            LinearLayout btnPetMod = view.findViewById(R.id.btnPetMod);
            btnPetMod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MyPetInfoEditActivity.class);
                    intent.putExtra("p_num", myPetList.get(position).getP_num());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });

            container.addView(view);
            return view;
        }

        @Override
        public float getPageWidth(int position) {
            return super.getPageWidth(position);
            //return (0.98f);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View)object);
        }
    }

    class ViewPagerChangeListener implements ViewPager.OnPageChangeListener{

        Context context;

        public ViewPagerChangeListener(Context context) {
            this.context = context;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //동물별 앨범 리스트 가져오기
            albumListSelect = new AlbumListSelect(myPetList.get(position).getP_num());
            try {
                albumList = albumListSelect.execute().get();

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //리사이클러뷰
            recyclerView = findViewById(R.id.petAlbum);
            gridAdapter = new GridRecyclerViewAdapter(context, albumList);
            gridLayoutManager = new GridLayoutManager(context, 3);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(gridAdapter);
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        //DB 에서 나의 동물 정보 목록 가져옴
        MyPetListSelect myPetListSelect = new MyPetListSelect(loginDTO.getM_tel());
        try {
            myPetListSelect.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (myPetList != null){
            albumListSelect = new AlbumListSelect(myPetList.get(0).getP_num());
            try {
                albumList = albumListSelect.execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            MyPagerAdapter adapter = new MyPagerAdapter(this);
            pager.setAdapter(adapter);
            ViewPagerChangeListener viewPagerChangeListener = new ViewPagerChangeListener(this);
            pager.addOnPageChangeListener(viewPagerChangeListener);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(MyPetInfo.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
