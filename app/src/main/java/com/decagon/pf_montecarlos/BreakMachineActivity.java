package com.decagon.pf_montecarlos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BreakMachineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break_machine);

        setupToolbar();
    }

    private void setupToolbar() {
        setTitle(R.string.title_BreakdownMachine);
    }
}
