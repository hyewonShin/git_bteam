package com.example.WithPet02.view.MyPet;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.WithPet02.R;
import com.example.WithPet02.dto.CalenderDTO;
import com.example.WithPet02.view.MyPet.asynctask.CalenderUpdate;
import java.util.concurrent.ExecutionException;


public class CalenderContent extends AppCompatActivity {
    private static final String TAG = "CalenderContent";

    Button submit, cancel;
    EditText calenderContent;
    CalenderDTO calenderDTO;
    Context context;
    int num;
    int year;
    int month;
    int date;

    private IntentUpdateListener listener;

    public void setIntentUpdateListener (IntentUpdateListener listener) {
        this.listener = listener;
    }

    public interface IntentUpdateListener {
        void IntentUpdate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_content);

        //Intent될 때 값 가져오기
        calenderDTO = (CalenderDTO) getIntent().getSerializableExtra("calenderContent");

        //calenderDTO에서 num, year, month, date가져오기
        num = calenderDTO.getNum();
        year = calenderDTO.getYear();
        month = calenderDTO.getMonth();
        date = calenderDTO.getDate();

        calenderContent = findViewById(R.id.calenderContent);
        cancel = findViewById(R.id.calender_cancel);
        submit = findViewById(R.id.calender_submit);

        calenderContent.setText(calenderDTO.getContent());

        //저장버튼 눌렀을 때
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderUpdate calenderUpdate = new CalenderUpdate(num, year, month, date, calenderContent.getText().toString());
                try {
                    calenderUpdate.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //DiagnosisDialog diagnosisDialog = new DiagnosisDialog(context);
                /*dialog.onContentChanged();*/

                /*DialogRecyclerViewAdapter adapter = new DialogRecyclerViewAdapter(getApplicationContext());
                adapter.notifyDataSetChanged();*/

                //diagnosisDialog.refreshAdapter();

                //listener.IntentUpdate();

                finish();

            }//onClick()
        });//setOnClickListener()


        //취소버튼 눌렀을 때
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }//onClick()
        });//setOnClickListener

    }//onCreate

}//class
