package com.decagon.pf_montecarlos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BreakdownMachineActivity extends AppCompatActivity {

    private BreakdownMachineActivity mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakdown_machine);
        setupToolbar();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case(R.id.btn_showSimulation):
                        Intent intent = new Intent(mContext,SimulationBreakdownActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + view.getId());
                }
            }
        };
        findViewById(R.id.btn_showSimulation).setOnClickListener(listener);
    }

    private void setupToolbar() {
        setTitle(R.string.title_BreakdownMachine);
    }
}
