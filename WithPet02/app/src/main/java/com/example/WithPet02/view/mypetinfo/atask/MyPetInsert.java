package com.example.WithPet02.view.mypetinfo.atask;

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
import org.apache.http.entity.mime.content.FileBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static com.example.WithPet02.common.CommonMethod.ipConfig;

public class MyPetInsert extends AsyncTask<Void, Void, String> {

    String p_tel, p_name, p_animal, p_a_animal, p_birth, p_gender;
    String imageDbPath = null, imageRealPath = null;
    String result;

    public MyPetInsert(String p_tel, String p_name, String p_animal, String p_a_animal, String p_birth, String p_gender, String imageDbPath, String imageRealPath) {
        this.p_tel = p_tel;
        this.p_name = p_name;
        this.p_animal = p_animal;
        this.p_a_animal = p_a_animal;
        this.p_birth = p_birth;
        this.p_gender = p_gender;
        this.imageDbPath = imageDbPath;
        this.imageRealPath = imageRealPath;
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

            builder.addTextBody("p_tel", p_tel, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("p_name", p_name, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("p_animal", p_animal, ContentType.create("Multipart/related", "UTF-8"));
            //builder.addTextBody("p_a_animal", p_a_animal, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("p_birth", p_birth, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("p_gender", p_gender, ContentType.create("Multipart/related", "UTF-8"));

            if(imageDbPath != null) {
                builder.addTextBody("imageDbPath", imageDbPath, ContentType.create("Multipart/related", "UTF-8"));
            }

            if(imageRealPath != null){
                builder.addPart("image", new FileBody(new File(imageRealPath)));
            }

            String postURL = ipConfig + "/app/petInsert";

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
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            inputStream.close();

            // 응답결과
            result = stringBuilder.toString();
            Log.d("main:MyPetInsert", result);

            inputStream.close();

        } catch (Exception e) {
            Log.d("main:MyPetInsert", e.getMessage());
            e.printStackTrace();
        } finally {
            if (httpEntity != null) {
                httpEntity = null;
            }
            if (httpResponse != null) {
                httpResponse = null;
            }
            if (httpPost != null) {
                httpPost = null;
            }
            if (httpClient != null) {
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