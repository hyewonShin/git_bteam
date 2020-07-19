package com.example.WithPet02.view.customerc_service.atask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.nio.charset.Charset;

import static com.example.WithPet02.common.CommonMethod.ipConfig;

public class BoardInsert extends AsyncTask <Void, Void, Void> {
    private String nickname;
    private String title;
    private String content;
    private String  imageDbPath;
    private String imageRealPath;

    public BoardInsert(String nickname, String title, String content, String imageDbPath, String imageRealPath) {
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.imageDbPath = imageDbPath;
        this.imageRealPath = imageRealPath;
    }

    //무조건 필요!!!!
    // 데이터베이스에 삽입결과 0보다크면 삽입성공, 같거나 작으면 실패
    String state = "";
    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            // 문자열 및 데이터 추가
            builder.addTextBody("name", nickname, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("title", title, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("content", content, ContentType.create("Multipart/related", "UTF-8"));
            //보낼 때와 받을 때가 같아야 해서 → ("dbImgPath", imageDbPath, 로 설정
            builder.addTextBody("dbImgPath", imageDbPath, ContentType.create("Multipart/related", "UTF-8"));
            //파일 전송시(이미지와 텍스트를 동시에 보낼경우)에는 Multipart를 사용하여 보내는 것이다.
            //imageRealPath의 실제 경로를 통해서 파일을 보내주는 것이다.(여러개 보낼 경우는 name을 다르게 해서 보내준다.)
            builder.addPart("image", new FileBody(new File(imageRealPath)));

            //전송 시켜 줄 때 anInsertMulti로 해서보내준다.
            String postURL = ipConfig + "/app/anBoardInsert";

            // 전송
            //InputStream inputStream = null;
            HttpClient httpClient = AndroidHttpClient.newInstance("Android");
            HttpPost httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            //inputStream = httpEntity.getContent();

            // 응답
            /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            inputStream.close();*/

            // 응답결과
            /*String result = stringBuilder.toString();
            Log.d("response", result);*/

        } catch (Exception e) {
            e.printStackTrace();
        }//try


        return null;
    }//doInBackground()

}//class
