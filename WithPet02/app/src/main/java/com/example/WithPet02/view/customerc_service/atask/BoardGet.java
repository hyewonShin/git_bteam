package com.example.WithPet02.view.customerc_service.atask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;

import com.example.WithPet02.dto.AlbumDTO;
import com.example.WithPet02.dto.BoardDTO;
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
import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.example.WithPet02.common.CommonMethod.ipConfig;

public class BoardGet extends AsyncTask<Void, Void, ArrayList<BoardDTO>> {
    ArrayList<BoardDTO> list = new ArrayList<>();
    private String name;

    public BoardGet(String name) {
        this.name = name;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected ArrayList<BoardDTO> doInBackground(Void... voids) {
        try {
            //무조건 필요(초기화해줌)
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();//여기 빌더 안에 데이터를 넣어서 보내는 것
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            //DB에 보내주기 위함(보내는 방법)
            // 문자열 및 데이터 추가
            builder.addTextBody("name", name, ContentType.create("Multipart/related", "UTF-8"));
            String postURL = ipConfig + "/app/anBoardGet";

            //전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost); //서버로 보내고 받음
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            list = readMessage(inputStream);

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

        return list;

    }//doInBackground

    private ArrayList<BoardDTO> readMessage(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line + "\n");
        }
        inputStream.close();

        Gson gson = new Gson();
        String json = stringBuilder.toString();
        ArrayList<BoardDTO> list = gson.fromJson(json, new TypeToken<ArrayList<BoardDTO>>(){}.getType());

        return list;
    }

    @Override
    protected void onPostExecute(ArrayList<BoardDTO> boardDTOS) {
        super.onPostExecute(boardDTOS);
    }

}//BoardGet
