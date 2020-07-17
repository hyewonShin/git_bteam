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

public class AlbumInsert extends AsyncTask<Void, Void, String> {

    int a_pet;
    String a_title, a_content, imageRealPath, imageDbPath;

    String result;

    public AlbumInsert(int a_pet, String a_title, String a_content, String imageRealPath, String imageDbPath) {
        this.a_pet = a_pet;
        this.a_title = a_title;
        this.a_content = a_content;
        this.imageRealPath = imageRealPath;
        this.imageDbPath = imageDbPath;
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

            builder.addTextBody("a_pet", String.valueOf(a_pet), ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("a_title", a_title, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("a_content", a_content, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("imageDbPath", imageDbPath, ContentType.create("Multipart/related", "UTF-8"));

            if(imageRealPath != null){
                builder.addPart("image", new FileBody(new File(imageRealPath)));
            }

            String postURL = ipConfig + "/app/albumInsert";

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
            Log.d("main:albumInsert", result);

            inputStream.close();

        } catch (Exception e) {
            Log.d("main:albumInsert", e.getMessage());
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