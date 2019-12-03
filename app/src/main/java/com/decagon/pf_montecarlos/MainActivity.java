package com.decagon.pf_montecarlos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private MainActivity mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setOnClickListeners();
    }

    private void setOnClickListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                switch (view.getId()) {
                    case R.id.btn_random_walk:
                        intent = new Intent(mContext, RandomWalkActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.btn_buffon_needles:
                        intent = new Intent(mContext, BuffonNeedlesActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.btn_area_down_curve:
                        intent = new Intent(mContext, AreaDownCurve.class);
                        startActivity(intent);
                        break;
                }
            }
        };
        findViewById(R.id.btn_random_walk).setOnClickListener(listener);
        findViewById(R.id.btn_buffon_needles).setOnClickListener(listener);
        findViewById(R.id.btn_area_down_curve).setOnClickListener(listener);
    }

}
