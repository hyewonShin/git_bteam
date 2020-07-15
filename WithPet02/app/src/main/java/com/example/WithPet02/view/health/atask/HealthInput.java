package com.example.WithPet02.view.health.atask;

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

public class HealthInput extends AsyncTask<Void, Void, String> {
    //DTO변수들
    private int num, pet;
    private String location, date;

    //생성자 초기화
    public HealthInput(int num, int pet, String location, String date) {
        this.num = num;
        this.pet = pet;
        this.location = location;
        this.date = date;
    }

    //무조건 필요!!!!
    // 데이터베이스에 삽입결과 0보다크면 삽입성공, 같거나 작으면 실패
    String state = "";
    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected String doInBackground(Void... voids) {
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
            builder.addTextBody("pet", String.valueOf(pet), ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("location", location, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("date", date, ContentType.create("Multipart/related", "UTF-8"));


            // /app = 앱이름, 서버에서 부를 때 사용
            String postURL = ipConfig + "/app/anHealth";

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

        return state;
    }//doInBackground()


}//class
