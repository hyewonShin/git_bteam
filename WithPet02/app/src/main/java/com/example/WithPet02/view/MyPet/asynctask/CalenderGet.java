package com.example.WithPet02.view.MyPet.asynctask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.WithPet02.dto.CalenderDTO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;

import static com.example.WithPet02.common.CommonMethod.ipConfig;

public class CalenderGet extends AsyncTask<Void, Void, ArrayList<CalenderDTO>> {
    private static final String TAG = "telCalenderGet";
    ArrayList<CalenderDTO> list = null;
    private String tel;

    public CalenderGet (String tel) {
        this.tel = tel;
    }//CalenderGet()

    String state = "";
    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected ArrayList<CalenderDTO> doInBackground(Void... voids) {
        try {
            //무조건 필요(초기화해줌)
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();//여기 빌더 안에 데이터를 넣어서 보내는 것
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            //DB에 보내주기 위함(보내는 방법)
            // 문자열 및 데이터 추가\
            builder.addTextBody("tel", tel, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anCalenderGet";


            //전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost); //서버로 보내고 받음
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            //json읽고 ArrayList에 넣기
            readJsonStream(inputStream);

            //inputStream종료
            inputStream.close();

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(httpEntity != null){
                httpEntity = null;
            }
            if(httpResponse != null){
                httpResponse = null;
            }
            if(httpPost != null){
                httpPost = null;
            }
            if(httpClient != null){
                httpClient = null;
            }
        }//try


        return list;
    }//doInBackground()

    //json가져오기
    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            readMessagesArray(reader);
        } finally {
            reader.close();
        }//try
    }//readJsonStream()

    //json Array형태 읽기
    public ArrayList<CalenderDTO> readMessagesArray(JsonReader reader) throws IOException {
        list = new ArrayList<CalenderDTO>();

        reader.beginArray();
        while (reader.hasNext()) {
            list.add(readMessage(reader));
        }
        reader.endArray();
        return list;
    }//readMessagesArray()

    //json Object형태 읽기
    public CalenderDTO readMessage(JsonReader reader) throws IOException {
        Date birthD = null;
        String tel = "", num = "", year = "", month = "", date = "", content = "";

        reader.beginObject();

        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("c_tel")) {
                tel = reader.nextString();
            } else if (readStr.equals("c_num")) {
                num = reader.nextString();
            }else if (readStr.equals("c_year")) {
                year = reader.nextString();
            } else if (readStr.equals("c_month")) {
                month = reader.nextString();
            } else if (readStr.equals("c_date")) {
                date = reader.nextString();
            }else if (readStr.equals("c_content")) {
                content = reader.nextString();
            }else {
                reader.skipValue();
            }//if
        }//while

        reader.endObject();

        int num2 = Integer.parseInt(num);
        int year2 = Integer.parseInt(year);
        int month2 = Integer.parseInt(month);
        int date2 = Integer.parseInt(date);

        Log.d(TAG, "mainReadMessage: " + num + date + content);
        return new CalenderDTO(tel, num2, year2, month2, date2, content);
    }//readMessage()

}//class
