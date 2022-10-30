package com.hutcwp.live.livebiz.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hutcwp.live.livebiz.ui.view.danmu.Barrage;
import com.hutcwp.live.livebiz.ui.view.danmu.BarrageView;
import com.hutcwp.livebiz.R;

import androidx.appcompat.app.AppCompatActivity;


public class SelfDanmuActivity extends AppCompatActivity {

    private BarrageView barrageView;

    private int count = 0;
    private int textSize = 14;

    public Button btnDanmu;

    int pos = 0;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
//        Injector.inject(this);
        btnDanmu = findViewById(R.id.btn_danmu);
        barrageView = findViewById(R.id.barrageView);
        btnDanmu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchBarragePos();
            }
        });
        handler.post(new Runnable() {
            @Override
            public void run() {
                autoSendDanmu();
            }
        });
    }

    public void autoSendDanmu() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                autoSendDanmu();
                addDanMu();
            }
        }, 500);
    }


    public void addDanMu() {
        Barrage barrage = new Barrage();
        barrage.setColor(Color.WHITE);
        barrage.setTextSize(textSize);
        barrage.setContent("发送的弹幕" + (count++));
        barrageView.showBarrage(barrage);
    }

    public void switchBarragePos() {
        if (pos == 3) {
            pos = 0;
        }
        Toast.makeText(this, "点击切换弹幕位置 ->" + pos, Toast.LENGTH_LONG).show();
        barrageView.setBarragePos(pos++);
    }
}
