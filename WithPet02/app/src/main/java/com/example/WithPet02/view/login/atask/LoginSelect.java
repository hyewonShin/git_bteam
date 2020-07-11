package com.example.WithPet02.view.login.atask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.WithPet02.dto.MemberDTO;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.example.WithPet02.common.CommonMethod.ipConfig;
import static com.example.WithPet02.view.login.LoginActivity.loginDTO;

// 로그인 AsyncTask
public class LoginSelect extends AsyncTask<Void, Void, MemberDTO> {

    String m_email, m_pw;

    public LoginSelect(String m_email, String m_pw) {
        this.m_email = m_email;
        this.m_pw = m_pw;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    MemberDTO dto;

    @Override
    protected MemberDTO doInBackground(Void... voids) {

        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            builder.addTextBody("m_email", m_email, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("m_pw", m_pw, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anLogin";

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
            dto = loginDTO;
            inputStream.close();

        } catch (Exception e){
            Log.d("main:loginSelect", e.getMessage());
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
        return dto;
    }

    private MemberDTO readMessage(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line + "\n");
        }
        inputStream.close();

        Gson gson = new Gson();
        String json = stringBuilder.toString();
        MemberDTO dto = gson.fromJson(json, MemberDTO.class);

        return dto;
    }

    @Override
    protected void onPostExecute(MemberDTO memberDTO) {
        super.onPostExecute(memberDTO);
    }
}
