package com.example.WithPet02.view.community.atask;

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

public class FreeBoardUpdate extends AsyncTask<Void, Void, String> {

    int f_num;
    String f_title, f_content, f_file, imageRealPath, imageDbPath;

    String result;

    public FreeBoardUpdate(int f_num, String f_title, String f_content, String f_file, String imageRealPath, String imageDbPath) {
        this.f_num = f_num;
        this.f_title = f_title;
        this.f_content = f_content;
        this.f_file = f_file;
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

            builder.addTextBody("f_num", String.valueOf(f_num), ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("f_title", f_title, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("f_content", f_content, ContentType.create("Multipart/related", "UTF-8"));

            if(f_file != null){
                builder.addTextBody("f_file", f_file, ContentType.create("Multipart/related", "UTF-8"));
            }

            if(imageDbPath != null){
                builder.addTextBody("imageDbPath", imageDbPath, ContentType.create("Multipart/related", "UTF-8"));
            }

            if(imageRealPath != null){
                builder.addPart("image", new FileBody(new File(imageRealPath)));
            }

            String postURL = ipConfig + "/app/freeBoardUpdate";

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
            Log.d("main:petUpdate", result);

            inputStream.close();

        } catch (Exception e) {
            Log.d("main:petUpdate", e.getMessage());
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