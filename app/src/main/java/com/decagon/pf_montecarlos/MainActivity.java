package com.decagon.pf_montecarlos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
                        intent = new Intent(mContext, AreaDownCurveActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.btn_number_exp_random:
                        intent = new Intent(mContext, NumberExpRandomActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.btn_breakdown_machine:
                        intent = new Intent(mContext, BreakdownMachineActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.btn_inventory:
                        intent = new Intent(mContext,InventoryActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.btn_function_effectiviness:
                        intent = new Intent(mContext,FunctionEffectivinessActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        };
        findViewById(R.id.btn_random_walk).setOnClickListener(listener);
        findViewById(R.id.btn_buffon_needles).setOnClickListener(listener);
        findViewById(R.id.btn_area_down_curve).setOnClickListener(listener);
        findViewById(R.id.btn_number_exp_random).setOnClickListener(listener);
        findViewById(R.id.btn_breakdown_machine).setOnClickListener(listener);
        findViewById(R.id.btn_inventory).setOnClickListener(listener);
        findViewById(R.id.btn_function_effectiviness).setOnClickListener(listener);
    }

}
