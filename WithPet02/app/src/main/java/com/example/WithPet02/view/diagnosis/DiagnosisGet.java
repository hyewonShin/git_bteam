package com.example.WithPet02.view.diagnosis;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;

import com.example.WithPet02.dto.DiagnosisDTO;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.WithPet02.common.CommonMethod.ipConfig;


public class DiagnosisGet extends AsyncTask<Void, Void, Void> {
    DiagnosisDTO diagnosisDTO = null;
    private int d_pet;

    //생성자 초기화
    public DiagnosisGet (int d_pet) {
        this.d_pet = d_pet;
    }//DiagnosisGet()

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            //무조건 필요(초기화해줌)
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();//여기 빌더 안에 데이터를 넣어서 보내는 것
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            //DB에 보내주기 위함(보내는 방법)
            // 문자열 및 데이터 추가
            builder.addTextBody("pet", String.valueOf(d_pet), ContentType.create("Multipart/related", "UTF-8"));
            String postURL = ipConfig + "/app/anDiagnosisGet";

            //전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost); //서버로 보내고 받음
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            diagnosisDTO = readMessage(inputStream);

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


        return null;
    }//doInBackground()

    //DTO
    public DiagnosisDTO readMessage(InputStream inputStream) throws IOException {
        //Json형태의 것을 가져오는 것(JsonObject에 대한 것만 있음)
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

        //int num = 0;
        Date birthD = null;
        String num = "", pet = "", title = "", content = "", hname = "", date = "";

        //Date birth = null;

        reader.beginObject();

        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("num")) {
                num = reader.nextString();
            } else if (readStr.equals("pet")) {
                pet = reader.nextString();
            } else if (readStr.equals("title")) {
                title = reader.nextString();
            } else if (readStr.equals("content")) {
                content = reader.nextString();
            }else if (readStr.equals("hname")) {
                hname = reader.nextString();
            }else if (readStr.equals("date")) {
                date = reader.nextString();
            }else {
                reader.skipValue();
            }//if
        }//while

        reader.endObject();


        try {
            birthD = new SimpleDateFormat("yyMMdd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int num2 = Integer.parseInt(num);
        int pet2 = Integer.parseInt(pet);

        return new DiagnosisDTO(num2, pet2, title, content, hname, date);

    }//readMessage()


}//class
