package com.example.WithPet02.view.mypetinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.WithPet02.R;
import com.example.WithPet02.dto.MyPetDTO;
import com.example.WithPet02.view.mypage.MyUpdateActivity;
import com.example.WithPet02.view.mypetinfo.atask.MyPetListSelect;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.WithPet02.common.CommonMethod.ipConfig;
import static com.example.WithPet02.view.login.LoginActivity.loginDTO;

public class MyPetInfo extends AppCompatActivity {

    Button button1;
    ViewPager pager;

    public static ArrayList<MyPetDTO> myPetList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pet_info);

        //툴바
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //DB 에서 나의 동물 정보 목록 가져옴
        MyPetListSelect myPetListSelect = new MyPetListSelect(loginDTO.getM_tel());
        try {
            myPetListSelect.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        ViewPagerChangeListener viewPagerChangeListener = new ViewPagerChangeListener();
        pager.addOnPageChangeListener(viewPagerChangeListener);

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
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            //return super.instantiateItem(container, position);
            View view = null;
            view = mInflater.inflate(R.layout.layout_my_pet_info, null);

            //layout_my_pet_info.xml에서 찾기
            ImageView myPetPic = view.findViewById(R.id.myPetPic);
            TextView myPetName = view.findViewById(R.id.myPetName);
            TextView myPetGen = view.findViewById(R.id.myPetGen);
            TextView myPetBirth = view.findViewById(R.id.myPetBirth);
            TextView myPetAni = view.findViewById(R.id.myPetAni);

            String filePath = ipConfig + "/app/resources/pet/";

            //동물 사진 설정
            myPetPic.setImageResource(R.drawable.a_defalt);
            if(myPetList.get(position).getP_pic() != null){
                Glide.with(mContext).load(filePath + myPetList.get(position).getP_pic()).signature(new ObjectKey(System.currentTimeMillis())).into(myPetPic);
            }

            //동물별 정보 가져오기
            myPetName.setText(myPetList.get(position).getP_name());
            myPetGen.setText(myPetList.get(position).getP_gender());
            String birth = myPetList.get(position).getP_birth().substring(2, 10).replace("-", ".");
            myPetBirth.setText(birth);
            myPetAni.setText(myPetList.get(position).getP_animal());

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


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    // 툴바 메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar 의 back 키 눌렀을 때 동작
                finish();
                return true;
            }
            case R.id.btnPetInsert:{ //개인정보 수정
                //동물 정보 추가 화면으로

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
