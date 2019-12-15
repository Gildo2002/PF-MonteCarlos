package com.decagon.pf_montecarlos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;

import com.decagon.pf_montecarlos.ClassHelper.SimpleValidator;
import com.decagon.pf_montecarlos.ClassModels.BuffonsNeedle;

public class BuffonNeedlesActivity extends AppCompatActivity {

    private BuffonNeedlesActivity mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffon_needles);

        setOnClickListeners();
        setupToolbar();
    }

    private void setOnClickListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String size = ((EditText) findViewById(R.id.et_sizeNeedles)).getText().toString();
                String space = ((EditText) findViewById(R.id.et_spaceNeedles)).getText().toString();
                String quantity = ((EditText) findViewById(R.id.et_quantityNeedles)).getText().toString();

                if (v.getId() == R.id.btn_accept) {
                    try {
                        boolean trust = (SimpleValidator.validate(SimpleValidator.NOT_EMPTY,
                                space)
                                && SimpleValidator.validate(SimpleValidator.NOT_EMPTY,
                                size)
                                && SimpleValidator.validate(SimpleValidator.NOT_EMPTY,
                                quantity)
                                && Integer.parseInt(space) > Integer.parseInt(size));

                        if (trust)
                            calculatePiEstimation();
                        else
                            Toast.makeText(mContext, "Error en los datos", Toast.LENGTH_SHORT).show();

                        InputMethodManager imm = (InputMethodManager) getSystemService(mContext.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        findViewById(R.id.btn_accept).setOnClickListener(listener);
    }

    private void calculatePiEstimation() {

        // space between two lines
        int space = Integer.parseInt(((EditText) findViewById(R.id.et_spaceNeedles)).getText().toString());
        // size of the needle
        int size = Integer.parseInt(((EditText) findViewById(R.id.et_sizeNeedles)).getText().toString());
        // total amount of needles thrown
        int needlesQty = Integer.parseInt(((EditText) findViewById(R.id.et_quantityNeedles)).getText().toString());

        BuffonsNeedle buffonsNeedle = new BuffonsNeedle();
        buffonsNeedle.size = size;
        buffonsNeedle.qty = needlesQty;
        buffonsNeedle.space = space;

        new AsyncTask<BuffonsNeedle, String, BuffonsNeedle>() {
            @Override
            protected BuffonsNeedle doInBackground(BuffonsNeedle... buffonsNeedles) {
                BuffonsNeedle buffonsNeedle = buffonsNeedles[0];

                int distance = buffonsNeedle.space;
                int needlesQty = buffonsNeedle.qty;
                int size = buffonsNeedle.size;

                double angle; // random angle of the thrown needle
                double position; // the position of the center of the pin (in the range of 0 to the distance between two lines)
                double piEstimate; // the result we will finally give (should be approximate to Pi)
                int intersections = 0; // number of times needle intersected with line, the number is a double due to calculation later

                for (int i = 0; i < needlesQty; i++) {
                    angle = Math.random() * Math.PI;
                    position = distance * Math.random();
                    // checking to see if there is indeed an intersection - using the intermediate value theorem.
                    if (((position + size * Math.sin(angle) / 2 >= distance) && (position - size * Math.sin(angle) / 2 <= distance)) ||
                            ((position + size * Math.sin(angle) / 2 >= 0) && (position - size * Math.sin(angle) / 2 <= 0))) {
                        intersections++;
                    }
                }

                piEstimate = (double) (2 * size * needlesQty) / (distance * intersections);

                buffonsNeedle.intersections = intersections;
                buffonsNeedle.piEstimate = piEstimate;

                return buffonsNeedle;
            }

            @Override
            protected void onPostExecute(BuffonsNeedle buffonsNeedle) {
                super.onPostExecute(buffonsNeedle);

                findViewById(R.id.tv_Result).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.tv_intersections)).setText( buffonsNeedle.intersections + " intersecciones.");
                ((TextView) findViewById(R.id.tv_piEstimation)).setText("Estimacion de pi: " + String.valueOf(buffonsNeedle.piEstimate));

            }
        }.execute(buffonsNeedle);

    }

    private void setupToolbar() {
        setTitle(R.string.title_BuffonNeedles);
    }
}
