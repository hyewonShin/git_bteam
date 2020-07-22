package com.example.WithPet02.view.MyPet.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.WithPet02.R;
import com.example.WithPet02.dto.CalenderDTO;
import com.example.WithPet02.dto.DiagnosisDTO;
import com.example.WithPet02.view.MyPet.Util.Keys;
import com.example.WithPet02.view.MyPet.adapter.CalendarAdapter;
import com.example.WithPet02.view.MyPet.dialog.DiagnosisDialog;
import com.example.WithPet02.view.MyPet.model.CalendarHeader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TabFragment extends Fragment {
    private static final String TAG = "tabFragment";
    public int mCenterPosition;
    private long mCurrentTime;
    //달력의 필요한 부분을 계산해주고 넣어주기 위한 ArrayList
    public ArrayList<Object> mCalendarList = new ArrayList<>();
    //달력의 달을 바꿔주기 위한 num변수 선언
    public int num = 0;
    //달력에 직접 들어가는 year, month, date
    public int year = 0;
    public int month = 0;
    public int date = 0;
    //DB에서 가져온 year, month, date
    public int Dyear = 0;
    public int Dmonth = 0;
    public int Ddate = 0;

    public TextView textView;
    public RecyclerView recyclerView;
    public Button before, after;
    public CalendarAdapter mAdapter;
    public StaggeredGridLayoutManager manager;
    public GregorianCalendar calendar = new GregorianCalendar();
    ArrayList<Object> calendarList;


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Fragment 연결
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_tab, container, false);

        //Fragment에서 initView메소드를 사용하여 TextView와 recyclerView사용 할 수 있게 하기
        initView(rootView);

        //이전 버튼 누르면 num에 -1 하기
        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num--;
                Log.d(TAG, "numBefore: " + num);
                //initSet메소드 사용
                initSet();
                //setRecycler()메소드 사용하기
                setRecycler();
            }//onClick()
        });//setOnClickListener

        //이후 버튼 누르면 num에 +1 하기
        after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                Log.d(TAG, "numAfter: " + num);
                //initSet메소드 사용
                initSet();
                //setRecycler()메소드 사용하기
                setRecycler();
            }//onClick
        });//setOnClickListener

        //initSet메소드 사용 → 달력을 생성하기 위한 기본 계산을 담은 메소드
        initSet();
        //setRecycler()메소드 사용하기 → RecyclerView사용
        setRecycler();

        //Fragment화면 출력
        return rootView;
    }//onCreateView()

    public void dataRefresh() {
        initSet();
        setRecycler();
    }


    public void initView(View v){

        textView = v.findViewById(R.id.title);
        recyclerView = v.findViewById(R.id.calendar);
        before = v.findViewById(R.id.before);
        after = v.findViewById(R.id.after);

    }//initView()

    //리사이클러 뷰 생성
    public void setRecycler() {

        if (mCalendarList == null) {
            Log.w(TAG, "No Query, not initializing RecyclerView");
        }//if

        manager = new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL);

        mAdapter = new CalendarAdapter(mCalendarList, getContext());

        mAdapter.setCalendarList(mCalendarList, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);

        //item_layout눌렀을 때 setDateClickListener를 통해 알고 Toast를 띄워 주는 메소드는 여기에 설정
        //위치에 따른 date값을 가져와야 하기 때문에
        mAdapter.setDateClickListener(new CalendarAdapter.DateClickListener() {

            @Override
            public void onDateClicked(int ADate, LayoutInflater inflaterContext, View day_layouts, View showText, ArrayList<DiagnosisDTO> DiaList, ArrayList<CalenderDTO> calList) {

                //가져온 위치의 Date와 month, year를 변수에 넣어주기
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH) + 1;
                date = ADate;
                //커스텀 다이얼로그를 생성
                //fragment는 Context가 getContext이다.
                final DiagnosisDialog diagnosisDialog = new DiagnosisDialog(getContext());
                //커스텀 다이얼로그를 호출
                //커스텀 다이얼로그이 결과를 출력할 TextView를 매개변수로 같이 넘겨주다.
                String text = "";

                diagnosisDialog.callFunction(year, month, date, mAdapter, inflaterContext, day_layouts, showText, DiaList, calList);

                //커스텀 다이얼 로그 종료시 작동되는 리스너 부른 후 작동 시킬 것 적어주기
                diagnosisDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        //달력 다시 만들 수 있도록 함수 써주기
                        initSet();
                        setRecycler();
                    }//onDismiss()
                });//setOnDismissListener()


                Log.d(TAG, "Text: " + text);

                Toast.makeText(getContext(), year + "년 " + month + "월 " + ADate + "일", Toast.LENGTH_SHORT).show();
            }//onDateClicked()

        });//setDateClickListener()



    }//setRecycler()

    //initCalendarList가져오는 메소드
    public void initSet(){

        initCalendarList();
    }//initSet()

    ////GregorianCalendar객체 생성해서 달력날들을 계산한다.
    private void initCalendarList() {
        //GregorianCalendar객체 생성
        //GregorianCalendar는 (년도, 월, 일, 시, 분, 초)형식으로 입력하는 생성자 제공
        GregorianCalendar cal = new GregorianCalendar();
        //setCalendarList()함수를 사용하는데 GregorianCalendar객체 넣어줌
        setCalendarList(cal);
    }//initCalendarList()


    //달력의 빈칸과 모든 날들을 계산하여 ArrayList에 넣어주기
    private void setCalendarList(GregorianCalendar cal) {
        calendarList = new ArrayList<>();

            try {
                calendar = new GregorianCalendar();
                //GregorianCalendar는 (년도, 월, 일, 시, 분, 초)형식으로 입력가능
                //현재달만 가져온다.
                calendar = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + num, 1, 0, 0, 0);
                Log.d(TAG, "numCheck: " + num);

                /*//i == 0 이면 이번해의 이번달 때를 의미함
                if (i == 0) {
                    mCenterPosition = calendarList.size();
                }//if*/

                //캘린더에 제목 넣기
                CalendarHeader model = new CalendarHeader();
                String title = model.setHeader2((Long) calendar.getTimeInMillis());
                textView.setText(title);

                //캘린더에서 현재 날짜와 시간을 가져와서 calendarList의 ArrayList에 넣어줌
                calendarList.add(calendar.getTimeInMillis());
                //해당하는 월에 시작하는 요일을 알려주기 때문에 그 전까지는 빈칸이 된다.
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; //해당 월에 시작하는 요일 -1 을 하면 빈칸을 구할 수 있겠죠 ?
                //해당하는 월의 마지막요일을 가져와서 넣어줌
                int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 해당 월에 마지막 요일

                //ArrayList에 빈칸과 날짜들을 순서대로 넣어준다.
                //EMPTY 생성
                for (int j = 0; j < dayOfWeek; j++) {   //해당월의 시작하는 요일 전까지를 빈칸으로 뿌려준다.
                    calendarList.add(Keys.EMPTY);
                }//for
                for (int j = 1; j <= max; j++) {    //해당월의 마지막 요일까지의 날짜를 뿌려준다.
                    calendarList.add(new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), j));
                }//for

                // TODO : 결과값 넣을때 여기다하면될듯

            } catch (Exception e) {
                e.printStackTrace();
            }//try

        //전부 계산해준 빈칸과 달력의 날짜를 mCalendarList에 넣어준다.
        mCalendarList = calendarList;
    }//setCalendarList()



    /*//달력의 빈칸과 모든 날들을 계산하여 ArrayList에 넣어주기
    public void setCalendarList(GregorianCalendar cal) {

        //setTitle(cal.getTimeInMillis());

        ArrayList<Object> calendarList = new ArrayList<>();

        for (int i = -300; i < 300; i++) {
            try {
                //GregorianCalendar는 (년도, 월, 일, 시, 분, 초)형식으로 입력가능
                //MONTH를 가져온 것에 + i 해줘서 -300 ~ 300까지의 달들을 표시 가능하다.
                GregorianCalendar calendar = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + i, 1, 0, 0, 0);

                //i == 0 이면 이번해의 이번달 때를 의미함
                if (i == 0) {
                    mCenterPosition = calendarList.size();
                }//if


                //캘린더에서 현재 날짜와 시간을 가져와서 calendarList의 ArrayList에 넣어줌
                calendarList.add(calendar.getTimeInMillis());
                //해당하는 월에 시작하는 요일을 알려주기 때문에 그 전까지는 빈칸이 된다.
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; //해당 월에 시작하는 요일 -1 을 하면 빈칸을 구할 수 있겠죠 ?
                //해당하는 월의 마지막요일을 가져와서 넣어줌
                int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 해당 월에 마지막 요일

                //ArrayList에 빈칸과 날짜들을 순서대로 넣어준다.
                // EMPTY 생성
                for (int j = 0; j < dayOfWeek; j++) {   //해당월의 시작하는 요일 전까지를 빈칸으로 뿌려준다.
                    calendarList.add(Keys.EMPTY);
                }//for
                for (int j = 1; j <= max; j++) {    //해당월의 마지막 요일까지의 날짜를 뿌려준다.
                    calendarList.add(new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), j));
                }//for

                // TODO : 결과값 넣을때 여기다하면될듯

            } catch (Exception e) {
                e.printStackTrace();
            }//try
        }//for

        //전부 계산해준 빈칸과 달력의 날짜를 mCalendarList에 넣어준다.
        mCalendarList = calendarList;
    }//setCalendarList()*/

}//class
