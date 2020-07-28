package com.example.WithPet02.view.mypetinfo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.WithPet02.R;
import com.example.WithPet02.dto.AlbumDTO;
import com.example.WithPet02.view.mypetinfo.atask.AlbumDelete;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

import static com.example.WithPet02.common.CommonMethod.ipConfig;

public class AlbumDetailActivity extends AppCompatActivity {

    Context context;
    AlbumDTO dto;
    String state;
    String filePath = ipConfig + "/app/resources/upload/album/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);

        context = this;
        dto = (AlbumDTO) getIntent().getSerializableExtra("dto");

        //툴바
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageView uploadPic = findViewById(R.id.uploadPic);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvContent = findViewById(R.id.tvContent);
        TextView tvDate = findViewById(R.id.tvDate);

        Glide.with(this).load(filePath + dto.getA_pet() + "/" + dto.getA_file()).into(uploadPic);
        tvTitle.setText(dto.getA_title());
        tvContent.setText(dto.getA_content());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
        tvDate.setText(sdf.format(dto.getA_date()));

    }

    // 툴바 메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_delete_menu, menu);
        return true;
    }

    // 툴바 메뉴 선택
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar 의 back 키 눌렀을 때 동작
                finish();
                return true;
            }
            case R.id.delete: {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("정말 앨범을 삭제하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //삭제처리
                        AlbumDelete albumDelete = new AlbumDelete(dto.getA_num(), dto.getA_pet(), dto.getA_file());
                        try {
                            state = albumDelete.execute().get().trim();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (state.equals("1")) {
                            Toast.makeText(context, "정상적으로 삭제되었습니다", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, MyPetInfo.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(context, "삭제에 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}