package com.example.cal_24point;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.util.AppUtil;

import org.htmlcleaner.XPatherException;

import java.io.IOException;

import static com.example.util.AppUtil.pachongtest;


public class MainActivity extends AppCompatActivity {

    private Button btn_m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            AppUtil.pachongtest();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPatherException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
        btn_m = findViewById(R.id.m_btn);
        btn_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,GameActivity.class);
                startActivity(intent);
            }
        });
    }
}