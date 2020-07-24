package com.example.WithPet02.view.MyPet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.WithPet02.R;
import com.example.WithPet02.dto.CalenderDTO;
import com.example.WithPet02.dto.DiagnosisDTO;
import com.example.WithPet02.view.MyPet.DiagnosisHistory;
import com.example.WithPet02.view.MyPet.adapter.CalendarAdapter;
import com.example.WithPet02.view.MyPet.adapter.DialogRecyclerViewAdapter;
import com.example.WithPet02.view.MyPet.asynctask.CalendarInsert;
import com.example.WithPet02.view.MyPet.asynctask.CalenderUpdate;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DiagnosisDialog {
    private static final String TAG = "DiagnosisDialog";
    private final String updateString = "수정";
    private final String submitString = "확인";
    private Context context;

    CalendarAdapter adapter;
    CalenderDTO calenderDialogDTO;

    //동적뷰 생성시 id를 위해 필요한 것들
    ArrayList<String> viewIdListContent = new ArrayList<String>();
    TextView dialogTextView = null;
    String dbContent = null;
    int id = 0;
    int j = 0;


    //RecyclerView사용
    RecyclerView calenderRecyclerView;

    //커스텀 종료시 작동되는 리스너 설정
    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }//setOnDismissListener()

    //커스텀 다이얼로그를 사용해주기 위한 환경 가져오기
    public DiagnosisDialog(Context context) {

        this.context = context;
    }//DiagnosisDialog


    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(final int year, final int month, final int date, final CalendarAdapter mAdapter,
                             final LayoutInflater inflaterContext, final View day_layouts, final View showText, final ArrayList<DiagnosisDTO> diaList, final ArrayList<CalenderDTO> calList) {
        adapter = mAdapter;

        int diaNoYear = 0;
        int diaNoMonth = 0;
        int diaNoDate = 0;

        Button diaNoButton = null;

        final int[] focusChecker = {0};   //message에 포커스를 했었는지 확인하는 변수

        final String[] content = {""};

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(this.context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.diagnosis_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final TextView title = dlg.findViewById(R.id.title);
        final EditText message = (EditText) dlg.findViewById(R.id.mesgase);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);

        //제목에 년월일 넣기
        title.setText("" + year + "년" + month + "월" + date + "일");
        //okButton에 확인넣기(직접 한번 버튼에 넣지 않으면 if문에서 확인이 안됨)
        okButton.setText("확인");

        //검진기록 버튼 찾기
        diaNoButton = dlg.findViewById(R.id.diaNoButton);

        //검진기록 있으면 띄워주기
        for(int i = 0; i<diaList.size(); i++) { //검진기록 DB에서 가져온 값에서 년월일 뽑기
            String[] getDate = diaList.get(i).getD_date().split("-");
            String[] cutingGetDay = getDate[2].split(" ");
            diaNoYear = Integer.parseInt(getDate[0]);
            diaNoMonth = Integer.parseInt(getDate[1]);
            diaNoDate = Integer.parseInt(cutingGetDay[0]);

            //눌러준 달력의 년달일이 검진기록에서 가져온 년달일과 맞으면 검진기록이라는 버튼이 보이게 하기
            if(year == diaNoYear && month == diaNoMonth) {
                if(date == diaNoDate){
                    diaNoButton.setVisibility(View.VISIBLE);
                }//if
            }//if
        }//for

        //검진기록 누르면 창띄우기
        diaNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DiagnosisHistory.class);
                //검진기록 있으면 띄워주기
                for(int i = 0; i < diaList.size(); i++) { //검진기록 DB에서 가져온 값에서 년월일 뽑기
                    String[] getDate = diaList.get(i).getD_date().split("-");
                    String[] cutingGetDay = getDate[2].split(" ");
                    int diaYear = Integer.parseInt(getDate[0]);
                    int diaMonth = Integer.parseInt(getDate[1]);
                    int diaDate = Integer.parseInt(cutingGetDay[0]);

                    //눌러준 달력의 년달일이 검진기록에서 가져온 년달일과 맞으면 검진기록이라는 버튼이 보이게 하기
                    if(year == diaYear && month == diaMonth) {
                        if(date == diaDate){
                            intent.putExtra("diagnosisList", diaList.get(i));
                        }//if
                    }//if
                }//for
                context.startActivity(intent);
            }//onClick()
        });//setOnClickListener()

        //RecyclerView사용
        calenderRecyclerView = dlg.findViewById(R.id.calendarRecyclerView);
        //RecyclerView설정하고 RecyclerView만들기
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        calenderRecyclerView.setLayoutManager(layoutManager);

        //년월일에 맞춰서 DB값 보여주기
        DialogRecyclerViewAdapter adapter = new DialogRecyclerViewAdapter(context);
        for(int i = 0; i < calList.size(); i++) {
            if(year == calList.get(i).getYear() && month == calList.get(i).getMonth()) {
                if(date == calList.get(i).getDate()) {
                    adapter.addItem(calList.get(i), dlg, diaList);
                }//if
            }//if
        }//for

        //RecyclerView실행
        calenderRecyclerView.setAdapter(adapter);

        //content 내용넣기
        adapter.setContentListener(new DialogRecyclerViewAdapter.ContentListener() {
            @Override
            public void onContent(CalenderDTO calenderDTO) {
                calenderDialogDTO = calenderDTO;
                //editText에 recyclerView누른 내용 넣기
                message.setText(calenderDTO.getContent());

                okButton.setText("수정");

            }//onContent
        });//setContentListener

        //Focus가 message에 했었는지를 통해 입력하려는지 아닌지 확인
        message.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, final boolean hasFocus) {
                if(hasFocus){//message에 focus가 있다면
                    focusChecker[0]++;
                }//if
            }//onFocusChange()
        });//setOnFocusChangeListener()

        //ok버튼 눌렀을 때 작동들 설정
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((focusChecker[0] % 2) == 1) {
                    if(okButton.getText().toString() == updateString) {
                        CalenderUpdate calenderUpdate = new CalenderUpdate(calenderDialogDTO.getNum(), calenderDialogDTO.getYear(),
                                calenderDialogDTO.getMonth(), calenderDialogDTO.getDate(), message.getText().toString());
                        try {
                            calenderUpdate.execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }//try
                    }else if (okButton.getText().toString() == submitString) {
                        // 커스텀 다이얼로그에서 입력한 메시지를 대입한다.
                        content[0] = (message.getText().toString());
                        Toast.makeText(DiagnosisDialog.this.context, "\"" +  message.getText().toString() + "\" 을 입력하였습니다.", Toast.LENGTH_SHORT).show();

                        //DB에 입력값 넣어주기
                        DBSetText("1", year, month, date, message.getText().toString());
                    }//if

                    //커스텀 다이얼로그 종료 시 작동되는 listener
                    onDismissListener.onDismiss(dlg);

                    // 커스텀 다이얼로그를 종료한다.
                    dlg.dismiss();
                }else {
                    Toast.makeText(context, "입력값이 없습니다.", Toast.LENGTH_SHORT).show();
                }//if

            }//onClick()
        });//setOnClickListener()




        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(okButton.getText().toString() == updateString) { //수정하는 걸 취소했을 때
                    message.setText("");
                    message.setHint("새 일정을 입력하세요");
                    okButton.setText("확인");

                }else{  //그냥 취소했을 때
                    Toast.makeText(DiagnosisDialog.this.context, "취소 했습니다.", Toast.LENGTH_SHORT).show();

                    //커스텀 다이얼로그 종료 시 작동되는 listener
                    onDismissListener.onDismiss(dlg);
                    // 커스텀 다이얼로그를 종료한다.
                    dlg.dismiss();
                }//if
            }//onClick()
        });//setOnClickListener()

    }//callFunction()

    //달력에 써준 텍스트 넣기
    public void set_text(String text, LayoutInflater inflaterContext, View day_layouts, View showText, Context context) {

        if(text != null) {
            //동적 뷰생성
            LinearLayout layout = day_layouts.findViewById(R.id.checked);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,1f);
            TextView viewText2 = new TextView(context);
            viewText2.setText(text);
            viewText2.setLayoutParams(layoutParams);
            layout.addView(viewText2);

            //뷰 삭제하기
            /*if (linearLayout.findViewById(R.id.extra_details) != null){
                linearLayout.removeView(details);
            }*/
        }//if
    }//set_text()

    //DB에 달력에 써준 텍스트 넣기
    public void DBSetText(String tel, int year, int month, int date, String content) {
        CalendarInsert calendarInsert = new CalendarInsert(tel, year, month, date, content);

        try {
            calendarInsert.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }//try

    }//DBSetText()

    public void refreshAdapter(){
        adapter.notifyDataSetChanged();
    }

}//class


/*//년월일에 맞춰 DB값을 다이얼로그에 보여주기
        for(int i = 0; i < calList.size(); i++) {
            if(year == calList.get(i).getYear() && month == calList.get(i).getMonth()) {
                if(date == calList.get(i).getDate()) {
                    LinearLayout layout = dlg.findViewById(R.id.dialog_layout);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT,1f);
                    dialogTextView = new TextView(context);

                    //일정 글
                    dbContent = calList.get(i).getContent();
                    dialogTextView.setText(dbContent);
                    //TextView에 아이디 주기
                    id = j;
                    dialogTextView.setId(id);
                    //id를 ArrayList에 담기
                    viewIdListContent.add(dbContent);
                    //텍스트뷰에 layout 설정 한 것 넣기
                    dialogTextView.setLayoutParams(layoutParams);
                    //텍스트 뷰 생성
                    layout.addView(dialogTextView);

                    dialogTextView.findViewById(id).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Intent intent = new Intent(context, CalenderContent.class);
                            *//*for (int i = 0; i < viewIdListContent.size(); i++) {

                            }*//*
                            Log.d(TAG, "id : " + id);
                            //intent.putExtra("Content", viewIdListContent.get(0));

                        }//onClick()
                    });//setOnClickListener()

                    j++;
                }//if
            }//if
        }//for*/

/*
okButton.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {


        // 커스텀 다이얼로그에서 입력한 메시지를 대입한다.
        content[0] = (message.getText().toString());
        Toast.makeText(DiagnosisDialog.this.context, "\"" +  message.getText().toString() + "\" 을 입력하였습니다.", Toast.LENGTH_SHORT).show();
        //달력에 입력값 넣어주기
        //set_text(message.getText().toString(), inflaterContext, day_layouts, showText, context);

        //DB에 입력값 넣어주기
        DBSetText("1", year, month, date, message.getText().toString());

        //커스텀 다이얼로그 종료 시 작동되는 listener
        onDismissListener.onDismiss(dlg);

        // 커스텀 다이얼로그를 종료한다.
        dlg.dismiss();

        }//onClick()
        });//setOnClickListener()*/
