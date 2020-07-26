package com.example.WithPet02.view.MyPet.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.WithPet02.R;

import com.example.WithPet02.dto.CalenderDTO;
import com.example.WithPet02.dto.DiagnosisDTO;
import com.example.WithPet02.view.MyPet.asynctask.CalenderGet;
import com.example.WithPet02.view.MyPet.asynctask.DiagnosisGet;
import com.example.WithPet02.view.MyPet.model.CalendarHeader;
import com.example.WithPet02.view.MyPet.model.Day;
import com.example.WithPet02.view.MyPet.model.EmptyDay;
import com.example.WithPet02.view.MyPet.spread.Spread;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static com.example.WithPet02.view.login.LoginActivity.loginDTO;

public class CalendarAdapter extends RecyclerView.Adapter {
    private static final String TAG = "CalendarAdapter";
    private final int HEADER_TYPE = 0;
    private final int EMPTY_TYPE = 1;
    private final int DAY_TYPE = 2;

    LayoutInflater inflater;
    ViewGroup context;
    Context frgContext;

    private List<Object> mCalendarList;

    //DB와 연결시켜서 값 가져 올 수 있게 선언
    ArrayList<DiagnosisDTO> list = null;
    ArrayList<CalenderDTO> calList = null;

    //해당 달력의 년 월 일
    int year;
    int month;
    int date;

    //일자타입의 view받기
    View view;

    //DateClickListener라는 listener를 사용할 수 있게 변수 선언
    private DateClickListener listener;

    //listener를 가져와서 DateClickListner에게 넣어줌
    public void setDateClickListener(DateClickListener listener) {
        this.listener = listener;
    }

    //interface선언하여 onDateClicked를 상속할 수 있도록 해줌
    public interface DateClickListener{
        void onDateClicked(int date, LayoutInflater inflaterContext, View day_layout, View showText, ArrayList<DiagnosisDTO> list, ArrayList<CalenderDTO> calList);
    }//DateClickListener

    //생성자
    public CalendarAdapter () {

    }

    //생성자 초기화
    public CalendarAdapter(List<Object> calendarList, Context context) {
        mCalendarList = calendarList;
        frgContext = context;

        //생성자 초기화 시킬 때 DB에서 날짜 값을 가져와서 검진날 출력 해줄 수 있게 하기\
        //생성자 초기화 시킬 때 DB에서 달력 입력 내용 가져와서 출력 할 수 있게 하기
        //임시로 1로 설정
        list = new ArrayList<>();

        DiagnosisGet diagnosisGet = new DiagnosisGet(Integer.parseInt(loginDTO.getM_tel()));
        CalenderGet calenderGet = new CalenderGet(loginDTO.getM_tel());

        try {
            list = diagnosisGet.execute().get();
            calList = calenderGet.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }//try

    }//CalendarAdapter()

    public void dataRefresh() {
        notifyDataSetChanged();
    }

    public void setCalendarList(List<Object> calendarList, int year, int month) {
        //달력의 년달일의 순서를 가지고 와서 mCalendarList에 넣어준다.
        mCalendarList = calendarList;
        //해당 달력의 년 월을 가져와서 전역변수로 바꿔주기
        this.year = year;
        this.month = month;

        //달력 정보 갱신
        notifyDataSetChanged();
    }//setCalendarList()

    //mCalendarList의 위치마다 아이템들의 ViewType을 설정해줌
    @Override
    public int getItemViewType(int position) { //뷰타입 나누기
        Object item = mCalendarList.get(position);
        if (item instanceof Long) {
            return HEADER_TYPE; //날짜 타입
        } else if (item instanceof String) {
            return EMPTY_TYPE; // 비어있는 일자 타입
        } else {
            return DAY_TYPE; // 일자 타입
        }//if
    }//getItemViewType()


    //viewHolder 생성
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        inflater = LayoutInflater.from(parent.getContext());
        context = parent;

        // 날짜 타입
        if (viewType == HEADER_TYPE) {

            HeaderViewHolder viewHolder = new HeaderViewHolder(inflater.inflate(R.layout.item_calendar_header, parent, false));

            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams)viewHolder.itemView.getLayoutParams();
            params.setFullSpan(true); //Span을 하나로 통합하기
            viewHolder.itemView.setLayoutParams(params);

            return viewHolder;

            //비어있는 일자 타입
        } else if (viewType == EMPTY_TYPE) {
            return new EmptyViewHolder(inflater.inflate(R.layout.item_day_empty, parent, false));

        }
        // 일자 타입
        else {
            LayoutInflater inflaterContext = LayoutInflater.from(parent.getContext());
            View showText= inflater.inflate(R.layout.item_showtext, parent, false);

            return new DayViewHolder(inflater.inflate(R.layout.item_day, parent, false), inflaterContext, showText);

        }//if

    }//onCreateViewHolder()


    // 데이터 넣어서 완성시키는것
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        //위치에 따른 viewType을 설정해서 viewType변수에 넣어준다.
        int viewType = getItemViewType(position);

        /**날짜 타입 꾸미기*/
        /** EX : 2018년 8월*/
        if (viewType == HEADER_TYPE) {
            HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
            Object item = mCalendarList.get(position);
            CalendarHeader model = new CalendarHeader();

            // long type의 현재시간
            if (item instanceof Long) {
                // 현재시간 넣으면, 2017년 7월 같이 패턴에 맞게 model에 데이터들어감.
                model.setHeader((Long) item);
            }
            // view에 표시하기
            holder.bind(model);
        }
        /** 비어있는 날짜 타입 꾸미기 */
        /** EX : empty */
        else if (viewType == EMPTY_TYPE) {
            EmptyViewHolder holder = (EmptyViewHolder) viewHolder;
            EmptyDay model = new EmptyDay();
            holder.bind(model);
        }
        /** 일자 타입 꾸미기 */
        /** EX : 22 */
        else if (viewType == DAY_TYPE) {
            DayViewHolder holder = (DayViewHolder) viewHolder;
            Object item = mCalendarList.get(position);
            Day model = new Day();

            if (item instanceof Calendar) {
                // Model에 Calendar값을 넣어서 일자 타입형식 지정
                model.setCalendar((Calendar) item);
            }//if

            // Model의 데이터를 View에 표현하기
            holder.bind(model);

            //검진기록 넣는 메소드
            medical_examin();

        }//if

    }//onBindViewHolder()


    // 개수구하기
    @Override
    public int getItemCount() {
        if (mCalendarList != null) {
            return mCalendarList.size();
        }
        return 0;
    }//getItemCount()


    //HeaderViewHolder
    private class HeaderViewHolder extends RecyclerView.ViewHolder { //날짜 타입 ViewHolder

        TextView itemHeaderTitle;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            initView(itemView);
        }//HeaderViewHolder()


        public void initView(View v){

            itemHeaderTitle = (TextView)v.findViewById(R.id.item_header_title);

        }//initView()

        public void bind(CalendarHeader model){

            // 일자 값 가져오기
            String header = ((CalendarHeader)model).getHeader();

            // header에 표시하기, ex : 2018년 8월
            itemHeaderTitle.setText(header);

        };//bind

    }//HeaderViewHolder()


    //EmptyViewHolder
    private class EmptyViewHolder extends RecyclerView.ViewHolder { // 비어있는 요일 타입 ViewHolder


        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);

            initView(itemView);
        }//EmptyViewHolder()

        public void initView(View v){

        }//initView()

        public void bind(EmptyDay model){

        };//bind()
    }//EmptyViewHolder()


    //요일 일 ViewHolder
    // TODO : item_day와 매칭
    private class DayViewHolder extends RecyclerView.ViewHolder {// 요일 입 ViewHolder

        TextView itemDay;

        public DayViewHolder(@NonNull View itemView, LayoutInflater inflaterContext, View showText) {
            super(itemView);

            initView(itemView, inflaterContext, showText);

        }//DayViewHolder()

        public void initView(View v, final LayoutInflater inflaterContext, final View showText){

            itemDay = (TextView)v.findViewById(R.id.item_day);

            //item_layout을 가져와서 클릭할 수 있도록 해줌
            v.findViewById(R.id.item_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //listener로 변수선언한 DateClickListener의 onDateClicked이라는 메소드를 통해서 itemDay(달력의 일짜)를 가져오도록 해줌
                    listener.onDateClicked(Integer.valueOf(itemDay.getText().toString()),
                            inflaterContext, v.findViewById(R.id.item_layout), showText, list, calList);

                }//onClick()
            });//setOnClickListener

            //Day view타입을 전역변수로 빼주기
            view = v.findViewById(R.id.checked);

        }//initView()

        //일자값을 model에 넣어서 형식맞춰서 Text에 넣어주기
        public void bind(Day model){

            // 일자 값 가져오기
            String day = ((Day)model).getDay();

            //GregorianCalendar는 (년도, 월, 일, 시, 분, 초)형식으로 입력하는 생성자 제공
            GregorianCalendar cal = new GregorianCalendar();
            int cal_year = cal.get(Calendar.YEAR);
            int cal_month = cal.get(Calendar.MONTH) + 1;
            int cal_day = cal.get(Calendar.DAY_OF_MONTH);   //int로 선언한 변수에 넣어야 int형으로 들어간다.

            if(cal_year == year && cal_month == month) {
                if (cal_day == Integer.valueOf(day)) {  //오늘과 일자가 맞으면 색깔을 변경하여 넣어준다.
                    //SpannableStringBuilder ssb = new SpannableStringBuilder(day);
                    //ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#ff8f1c")), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    itemDay.setTextColor(Color.parseColor("#ffffff"));
                    itemDay.setBackgroundColor(Color.parseColor("#ff8f1c"));
                    // 일자 값 View에 보이게하기
                    itemDay.setText(day);
                }else {     //오늘 일자가 맞지 않으면 그냥 넣어준다.
                    // 일자 값 View에 보이게하기
                    itemDay.setText(day);
                }//if
            }else { //년월일이 맞지 않은 것들은 바로 보이게
                // 일자 값 View에 보이게하기
                itemDay.setText(day);
            }//if


            //일자 값을 전역변수로 빼주기
            date = Integer.valueOf(itemDay.getText().toString());
        };//bind

    }//DayViewHolder()

    //검진기록 넣기
    public void medical_examin () {
        //검진기록 표시 넣기 객체 가져오기
        Spread spread = new Spread(year, month, date);
        spread.medical_examination(inflater, view, frgContext, list, calList);
    }//medical_examin()

}//class
