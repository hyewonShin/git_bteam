package com.example.WithPet02.view.mypetinfo.atask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import com.example.WithPet02.dto.MyPetDTO;
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
import static com.example.WithPet02.view.mypetinfo.MyPetInfo.myPetList;

public class MyPetListSelect extends AsyncTask<Void, Void, ArrayList<MyPetDTO>> {

    String m_tel;
    ArrayList<MyPetDTO> list = null;

    public MyPetListSelect(String m_tel) {
        this.m_tel = m_tel;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected ArrayList<MyPetDTO> doInBackground(Void... voids) {
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            builder.addTextBody("m_tel", m_tel, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/petList";

            //전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            // 응답
            myPetList = readMessage(inputStream);
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
        return myPetList;
    }

    private ArrayList<MyPetDTO> readMessage(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line);
        }
        inputStream.close();

        String json = stringBuilder.toString();
        if(!json.equals("[]")){
            Gson gson = new Gson();
            list = gson.fromJson(json, new TypeToken<ArrayList<MyPetDTO>>(){}.getType());
        }

        return list;
    }

    @Override
    protected void onPostExecute(ArrayList<MyPetDTO> list) {
        super.onPostExecute(list);
    }
}
