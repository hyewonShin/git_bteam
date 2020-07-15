package com.example.WithPet02.view.health.atask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;


import com.example.WithPet02.dto.HealthDTO;

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

import static com.example.WithPet02.common.CommonMethod.ipConfig;

public class HealthGet extends AsyncTask<Void, Void, HealthDTO> {
    HealthDTO healthDTO = null;
    //동물번호를 통해서 운동기록 테이블을 조회한다.
    private int pet;

    //생성자 초기화
    public HealthGet(int pet) {
        this.pet = pet;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected HealthDTO doInBackground(Void... voids) {
        try {
            //무조건 필요(초기화해줌)
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();//여기 빌더 안에 데이터를 넣어서 보내는 것
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            //DB에 보내주기 위함(보내는 방법)
            // 문자열 및 데이터 추가
            builder.addTextBody("pet", String.valueOf(pet), ContentType.create("Multipart/related", "UTF-8"));
            String postURL = ipConfig + "/app/anHealthGet";

            //전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost); //서버로 보내고 받음
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            healthDTO = readMessage(inputStream);

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

        return healthDTO;
    }//doInBackground()


    //DTO
    public HealthDTO readMessage(InputStream inputStream) throws IOException {
        //Json형태의 것을 가져오는 것(JsonObject에 대한 것만 있음)
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

        String num = "", pet = "", location = "", date = "";

        //Date birth = null;

        reader.beginObject();

        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("num")) {
                num = reader.nextString();
            } else if (readStr.equals("pet")) {
                pet = reader.nextString();
            } else if (readStr.equals("location")) {
                location = reader.nextString();
            } else if (readStr.equals("date")) {
                date = reader.nextString();
            }else {
                reader.skipValue();
            }//if
        }//while

        reader.endObject();


        int num2 = Integer.parseInt(num);
        int pet2 = Integer.parseInt(pet);

        return new HealthDTO(num2, pet2, location, date);

    }//readMessage()

}//class
