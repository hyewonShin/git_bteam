package com.example.WithPet02.view.customerc_service.atask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;

import com.example.WithPet02.dto.BoardDTO;

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

            readJsonStream(inputStream);

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

    //json가져오기
    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            readMessagesArray(reader);
        } finally {
            reader.close();
        }//try
    }//readJsonStream()

    //json Array형태 읽기
    public ArrayList<BoardDTO> readMessagesArray(JsonReader reader) throws IOException {
        list = new ArrayList<BoardDTO>();

        reader.beginArray();
        while (reader.hasNext()) {
            list.add(readMessage(reader));
        }
        reader.endArray();
        return list;
    }//readMessagesArray()

    //json Object형태 읽기
    public BoardDTO readMessage(JsonReader reader) throws IOException {
        String num = "", group = "", name = "", title = "", content = "", pic = "", seq = "", like = "", date = "";

        reader.beginObject();

        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("num")) {
                num = reader.nextString();
            } else if (readStr.equals("group")) {
                group = reader.nextString();
            } else if (readStr.equals("name")) {
                name = reader.nextString();
            } else if (readStr.equals("title")) {
                title = reader.nextString();
            }else if (readStr.equals("content")) {
                content = reader.nextString();
            }else if (readStr.equals("pic")) {
                pic = reader.nextString();
            }else if (readStr.equals("seq")) {
                seq = reader.nextString();
            }else if (readStr.equals("like")) {
                like = reader.nextString();
            }else if (readStr.equals("date")) {
                date = reader.nextString();
            }else {
                reader.skipValue();
            }//if
        }//while

        reader.endObject();


        /*try {
            birthD = new SimpleDateFormat("yyMMdd").parse(birth);
        } catch (android.net.ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }*/

        int num2 = Integer.parseInt(num);
        int seq2 = Integer.parseInt(seq);
        int like2 = Integer.parseInt(like);

        return new BoardDTO(num2, group ,name, title, content, pic, seq2, like2, date);
    }//readMessage()

}//BoardGet
