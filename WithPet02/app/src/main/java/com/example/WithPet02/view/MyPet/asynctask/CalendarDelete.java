package com.example.WithPet02.view.MyPet.asynctask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static com.example.WithPet02.common.CommonMethod.ipConfig;

public class CalendarDelete extends AsyncTask<Void, Void, Void> {
    private int num;
    private int year;
    private int month;
    private int date;

    public CalendarDelete(int num, int year, int month, int date) {
        this.num = num;
        this.year = year;
        this.month = month;
        this.date = date;
    }

    String state = "";
    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //무조건 필요(초기화해줌)
            //MultipartEntityBuild생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            //builder안에 데이터를 넣어준다.
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            //DB에 보내주는 방법
            //문자열 및 데이터 추가
            builder.addTextBody("num", String.valueOf(num), ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("year", String.valueOf(year), ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("month", String.valueOf(month), ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("date", String.valueOf(date), ContentType.create("Multipart/related", "UTF-8"));


            // /app = 앱이름, 서버에서 부를 때 사용
            String postURL = ipConfig + "/app/anCalenderDelete";

            //전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();


            //응답
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            state = stringBuilder.toString();

            inputStream.close();

        }catch (Exception e) {
            e.getMessage();
        }

        return null;
    }//doInBackground

}//class

