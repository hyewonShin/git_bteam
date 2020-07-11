package com.example.WithPet02.view.login.atask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.WithPet02.dto.MemberDTO;

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
import static com.example.WithPet02.view.login.LoginActivity.loginDTO;

// 카카오 로그인 AsyncTask
public class LoginSnsSelect extends AsyncTask<Void, Void, Void> {

    String kakaoId;

    public LoginSnsSelect(String kakaoId) {
        this.kakaoId = kakaoId;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            builder.addTextBody("kakaoId", kakaoId, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anKakaoLogin";

            //전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            // 응답
            loginDTO = readMessage(inputStream);
            inputStream.close();

        } catch (Exception e){
            Log.d("main:LoginSns", e.getMessage());
            e.printStackTrace();
        } finally {
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
        }
        return null;
    }

    private MemberDTO readMessage(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

        String m_tel = "", m_email = "", m_name = "", m_kakao = "", m_naver = "", m_pic = "", m_grade = "";

        reader.beginObject();
        while (reader.hasNext()){
            String readStr = reader.nextName();
            if (readStr.equals("m_tel")){
                m_tel = reader.nextString();
            } else if (readStr.equals("m_email")){
                m_email = reader.nextString();
            } else if (readStr.equals("m_name")){
                m_name = reader.nextString();
            } else if (readStr.equals("m_kakao")){
                m_kakao = reader.nextString();
            } else if (readStr.equals("m_naver")){
                m_naver = reader.nextString();
            } else if (readStr.equals("m_pic")){
                m_pic = reader.nextString();
            } else if (readStr.equals("m_grade")){
                m_grade = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("main:loginSelect",  m_tel + ","
                + m_email + "," + m_name + "," + m_kakao
                + "," + m_naver + "," + m_pic + "," + m_grade);
        return new MemberDTO(m_tel, m_email, m_name, m_kakao, m_naver, m_pic, m_grade);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
