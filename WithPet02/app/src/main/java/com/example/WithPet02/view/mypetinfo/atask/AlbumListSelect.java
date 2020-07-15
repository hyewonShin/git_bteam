package com.example.WithPet02.view.mypetinfo.atask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import com.example.WithPet02.dto.AlbumDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import java.util.ArrayList;

import static com.example.WithPet02.common.CommonMethod.ipConfig;

public class AlbumListSelect extends AsyncTask<Void, Void, ArrayList<AlbumDTO>> {

    private int a_pet;
    ArrayList<AlbumDTO> list = null;

    public AlbumListSelect(int a_pet) {
        this.a_pet = a_pet;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected ArrayList<AlbumDTO> doInBackground(Void... voids) {
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            builder.addTextBody("a_pet", String.valueOf(a_pet), ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/albumList";

            //전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            // 응답
            list = readMessage(inputStream);
            inputStream.close();

        } catch (Exception e){
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
        return list;
    }

    private ArrayList<AlbumDTO> readMessage(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line + "\n");
        }
        inputStream.close();

        Gson gson = new Gson();
        String json = stringBuilder.toString();
        ArrayList<AlbumDTO> list = gson.fromJson(json, new TypeToken<ArrayList<AlbumDTO>>(){}.getType());

        return list;
    }

    @Override
    protected void onPostExecute(ArrayList<AlbumDTO> albumDTOS) {
        super.onPostExecute(albumDTOS);
    }
}
