package com.decagon.pf_montecarlos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.decagon.pf_montecarlos.ClassHelper.SimpleValidator;
import com.decagon.pf_montecarlos.ClassModels.Queue;
import com.decagon.pf_montecarlos.ClassModels.Tabla;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SimulationBreakdownActivity extends AppCompatActivity {

    private Tabla table;
    private SimulationBreakdownActivity mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation_breakdown);
        mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        table = new Tabla(mContext,(TableLayout) findViewById(R.id.tablelayout));

        setOnClickListeners();
        setupToolbar();
    }

    private void setupToolbar() {
        setTitle(R.string.title_BreakdownMachine);
    }

    private void setOnClickListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = ((EditText) findViewById(R.id.et_value)).getText().toString();
                switch (view.getId()){
                    case R.id.btn_showSimulation:
                        try {
                            boolean trust =  (SimpleValidator.validate(SimpleValidator.NOT_EMPTY,
                                    value)
                                    && Integer.parseInt(value) > 0);

                            if(trust)
                                runSimulation();
                            else
                                Toast.makeText(mContext, "Error en los datos", Toast.LENGTH_SHORT).show();

                            InputMethodManager imm = (InputMethodManager) getSystemService(mContext.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                }
            }
        };
        findViewById(R.id.btn_showSimulation).setOnClickListener(listener);
    }

    private void runSimulation() {
        table.removeAll();
        table.addTitle(R.array.row_title_breakdown);

        int iterations = Integer.parseInt(((EditText) findViewById(R.id.et_value)).getText().toString());

        Queue queue = new Queue(iterations);

        for(int i = 0; i <= iterations; i++) {
            if (i == 0) continue;

            queue.timeFail[i]   = randomTime(10,19);
            queue.timeRepair[i] = randomTime(8,18);
            queue.arrive[i] = queue.arrive[i - 1] + queue.timeFail[i];

            if(queue.timeFinal[i-1] > queue.arrive[i])
                queue.timeInit[i] = queue.timeFinal[i-1];
            else
                queue.timeInit[i] = queue.arrive[i];

            queue.timeFinal[i]  = queue.timeInit[i] + queue.timeRepair[i];

            queue.timeWait[i] = queue.timeInit[i] - queue.arrive[i];

            if(queue.arrive[i] > queue.timeFinal[i-1])
                queue.timeIdle[i] = queue.arrive[i] - queue.timeFinal[i -1];
            else
                queue.timeIdle[i] = 0;

        }

        for(int i = 0; i <= iterations; i++)
        {
            ArrayList<String> elementos = new ArrayList<>();
            elementos.add(format(queue.timeFail[i]));
            elementos.add(format(queue.timeRepair[i]));
            elementos.add(format(queue.arrive[i]));
            elementos.add(format(queue.timeInit[i]));
            elementos.add(format(queue.timeFinal[i]));
            elementos.add(format(queue.timeWait[i]));
            elementos.add(format(queue.timeIdle[i]));
            table.addRows(elementos);
        }
    }

    private int randomTime(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public String format(int minutos) {
        String formato = "%02d:%02d";
        long horasReales = TimeUnit.MINUTES.toHours(minutos);
        long minutosReales = TimeUnit.MINUTES.toMinutes(minutos) - TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours(minutos));
        return String.format(formato, horasReales, minutosReales);
    }

}
