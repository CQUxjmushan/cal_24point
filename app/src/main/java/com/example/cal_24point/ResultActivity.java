package com.example.cal_24point;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private ArrayList<String> result;
    private Button btn_back;
    private RecyclerView recyclerView;
    private ImageView iv_1;
    private ImageView iv_2;
    private ImageView iv_3;
    private ImageView iv_4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        iv_1=findViewById(R.id.res_img_1);
        iv_2=findViewById(R.id.res_img_2);
        iv_3=findViewById(R.id.res_img_3);
        iv_4=findViewById(R.id.res_img_4);
        this.setTitle("计算结果");
        Intent intent=getIntent();
        result=(ArrayList<String>)intent.getSerializableExtra("result");
        btn_back=findViewById(R.id.btn_back);
        recyclerView=findViewById(R.id.rev_back);
        ResultAdapter adapter= new ResultAdapter(ResultActivity.this,result);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(ResultActivity.this,GameActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
            }
        });
        set_card(iv_1,intent.getStringExtra("img1"));
        set_card(iv_2,intent.getStringExtra("img2"));
        set_card(iv_3,intent.getStringExtra("img3"));
        set_card(iv_4,intent.getStringExtra("img4"));
    }
    /*
根据图片名称为相应view修改背景图片(输入框)
 */
    private void set_card(ImageView imageView,String s) {
        int resid=getResources().getIdentifier(s,"drawable",getPackageName());
        imageView.setTag(s);
        imageView.setImageResource(resid);
    }
}