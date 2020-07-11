package com.example.WithPet02.view.join.atask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

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

public class JoinInsert extends AsyncTask<Void, Void, String> {

    String m_tel, m_email, m_name, m_pw, m_kakao, m_naver;
    String result;

    public JoinInsert(String m_tel, String m_email, String m_name, String m_pw, String m_kakao, String m_naver) {
        this.m_tel = m_tel;
        this.m_email = m_email;
        this.m_name = m_name;
        this.m_pw = m_pw;
        this.m_kakao = m_kakao;
        this.m_naver = m_naver;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected String doInBackground(Void... voids) {
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            builder.addTextBody("m_tel", m_tel, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("m_email", m_email, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("m_name", m_name, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("m_pw", m_pw, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("m_kakao", m_kakao, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("m_naver", m_naver, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anJoin";

            //전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            // 응답
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            inputStream.close();

            // 응답결과
            result = stringBuilder.toString();
            Log.d("main:JoinInsert", result);

            inputStream.close();

        } catch (Exception e){
            Log.d("main:JoinInsert", e.getMessage());
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
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
