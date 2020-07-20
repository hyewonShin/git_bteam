package com.example.WithPet02.MainView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.WithPet02.R;
import com.example.WithPet02.view.login.LoginActivity;
import com.example.WithPet02.view.mypetinfo.MyPetInfo;
import com.example.WithPet02.view.mypetinfo.MyPetInfoAddActivity;
import com.example.WithPet02.view.mypetinfo.MyPetInfoEditActivity;
import com.example.WithPet02.view.mypetinfo.atask.MyPetListSelect;

import java.util.concurrent.ExecutionException;

import static com.example.WithPet02.common.CommonMethod.ipConfig;
import static com.example.WithPet02.view.login.LoginActivity.loginDTO;
import static com.example.WithPet02.view.mypetinfo.MyPetInfo.myPetList;

public class MainLogIn {

    Context context;
    LinearLayout myPetPager;

    ViewPager main_login;
    TextView notLogin, noPet;

    int pos;    //현재 뷰페이저 위치

    public MainLogIn(Context context, LinearLayout myPetPager) {
        this.context = context;
        this.myPetPager = myPetPager;
    }

    public void setMyPetPager (){
        main_login = myPetPager.findViewById(R.id.main_login);
        notLogin = myPetPager.findViewById(R.id.notLogin);
        noPet = myPetPager.findViewById(R.id.noPet);

        if(loginDTO == null){
            //비 로그인시 로그인 화면으로
            main_login.setVisibility(View.GONE);
            noPet.setVisibility(View.GONE);
            notLogin.setVisibility(View.VISIBLE);
            notLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }
            });
        }else{
            //로그인시 DB 에서 나의 동물 정보 목록 가져옴
            MyPetListSelect myPetListSelect = new MyPetListSelect(loginDTO.getM_tel());
            try {
                myPetListSelect.execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(myPetList.size() <= 0){
                //등록된 동물정보 없음. 클릭시 동물 추가 화면으로
                main_login.setVisibility(View.GONE);
                notLogin.setVisibility(View.GONE);
                noPet.setVisibility(View.VISIBLE);

                noPet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //동물 정보 추가 화면으로
                        Intent intent = new Intent(context, MyPetInfoAddActivity.class);
                        context.startActivity(intent);
                    }
                });
            } else {
                //등록된 동물 정보 있음. 클릭시 동물 정보 화면으로
                notLogin.setVisibility(View.GONE);
                noPet.setVisibility(View.GONE);
                main_login.setVisibility(View.VISIBLE);

                //ViewPager 설정
                main_login.setOffscreenPageLimit(3);
                main_login.setClipToPadding(false);
                int dpValue = 15;
                float d = context.getResources().getDisplayMetrics().density;
                int margin = (int) (dpValue * d);
                main_login.setPadding(margin + 30,40, margin + 30, 0);
                main_login.setPageMargin(40);

                //ViewPager 를 Adapter 에 연결
                MyPagerAdapter adapter = new MyPagerAdapter(context);
                main_login.setAdapter(adapter);

                //ViewPager 이벤트 처리용 리스너 설정
                ViewPagerChangeListener viewPagerChangeListener = new ViewPagerChangeListener(context);
                main_login.addOnPageChangeListener(viewPagerChangeListener);

            }

        }
    }

    public class MyPagerAdapter extends PagerAdapter {

        Context mContext = null;
        LayoutInflater mInflater;
        String animal;

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

            View view = null;
            view = mInflater.inflate(R.layout.layout_main_my_pet_info, null);

            //layout_my_pet_info.xml에서 찾기
            ImageView myPetPic = view.findViewById(R.id.myPetPic);
            TextView myPetName = view.findViewById(R.id.myPetName);
            TextView myPetGen = view.findViewById(R.id.myPetGen);
            TextView myPetBirth = view.findViewById(R.id.myPetBirth);
            TextView myPetAni = view.findViewById(R.id.myPetAni);
            LinearLayout myPetInfo = view.findViewById(R.id.myPetInfo);

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

            //동물 정보 화면으로
            myPetPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MyPetInfo.class);
                    intent.putExtra("pos", position);
                    context.startActivity(intent);
                }
            });
            myPetInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MyPetInfo.class);
                    intent.putExtra("pos", position);
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

        }

        @Override
        public void onPageSelected(int position) {
            //동물 정보 페이지로 이동
            pos = position;

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
