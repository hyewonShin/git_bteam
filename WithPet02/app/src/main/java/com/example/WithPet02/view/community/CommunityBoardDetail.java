package com.example.WithPet02.view.community;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.WithPet02.R;
import com.example.WithPet02.dto.FreeBoardDTO;
import com.example.WithPet02.view.community.atask.FreeBoardDelete;
import com.example.WithPet02.view.mypetinfo.MyPetInfo;
import com.example.WithPet02.view.mypetinfo.atask.AlbumDelete;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

import static com.example.WithPet02.common.CommonMethod.ipConfig;
import static com.example.WithPet02.view.login.LoginActivity.loginDTO;

public class CommunityBoardDetail extends AppCompatActivity {

    Context context;
    FreeBoardDTO dto;
    String filePath = ipConfig + "/app/resources/upload/freeboard/";
    String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_board_detail);

        context = this;
        dto = (FreeBoardDTO) getIntent().getSerializableExtra("dto");

        //툴바
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageView uploadPic = findViewById(R.id.uploadPic);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvContent = findViewById(R.id.tvContent);
        TextView tvDate = findViewById(R.id.tvDate);
        TextView tvName = findViewById(R.id.tvName);
        LinearLayout isWriter = findViewById(R.id.isWriter);
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnUpdate = findViewById(R.id.btnUpdate);

        //글의 작성자일때만 수정 삭제 버튼 보이게
        if (!loginDTO.getM_name().equals(dto.getF_tel())){
            isWriter.setVisibility(View.GONE);
        }

        //정보 출력
        if(dto.getF_file() == null){
            uploadPic.setVisibility(View.GONE);
        } else {
            Glide.with(this).load(filePath + dto.getF_file()).into(uploadPic);
        }

        tvTitle.setText(dto.getF_title());
        tvName.setText(dto.getF_tel());
        tvContent.setText(dto.getF_content());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
        tvDate.setText(sdf.format(dto.getF_date()));

        //삭제 버튼 클릭
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //삭제 여부 묻기
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("정말 앨범을 삭제하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //삭제처리
                        FreeBoardDelete freeBoardDelete = new FreeBoardDelete(dto.getF_num(), dto.getF_file());
                        try {
                            state = freeBoardDelete.execute().get().trim();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (state.equals("1")) {
                            Toast.makeText(context, "정상적으로 삭제되었습니다", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, CommunityActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
        });

        //수정 버튼 클릭
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //수정 화면으로
                Intent intent = new Intent(context, CommunityBoardUpdate.class);
                intent.putExtra("dto", dto);
                startActivity(intent);
            }
        });
    }
}
