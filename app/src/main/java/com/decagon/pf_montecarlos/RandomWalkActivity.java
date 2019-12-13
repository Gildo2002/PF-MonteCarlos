package com.decagon.pf_montecarlos;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import com.decagon.pf_montecarlos.ClassHelper.SimpleValidator;
import com.decagon.pf_montecarlos.ClassModels.RandomWalk;
import com.decagon.pf_montecarlos.ClassModels.Step;
import com.robinhood.spark.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomWalkActivity extends AppCompatActivity {

    private RandomWalkActivity mContext = this;
    private RandomWalk mRandomWalk;
    private SparkView mSparkView;
    private CustomSparkAdapter mSparkAdapter;
    private int stepCounter;
    private CountDownTimer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_walk);

        setOnClickListeners();
        setupToolbar();
    }

    private void setupToolbar() {
        setTitle(R.string.title_RandomWalk);
    }

    private void setOnClickListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                case R.id.btn_accept:
                    try {
                        boolean trust = SimpleValidator.validate(SimpleValidator.NOT_EMPTY,
                                ((EditText) findViewById(R.id.et_steps)).getText().toString())
                                && SimpleValidator.validate(SimpleValidator.NOT_EMPTY,
                                        ((EditText) findViewById(R.id.et_time)).getText().toString());

                        if (trust)
                            calculateRandomWalk();
                        else
                            Toast.makeText(mContext, "Error en los datos", Toast.LENGTH_SHORT).show();

                        InputMethodManager imm = (InputMethodManager)getSystemService(mContext.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        };
        findViewById(R.id.btn_accept).setOnClickListener(listener);
    }

    private void calculateRandomWalk() {

        final int maxRangeAleatory = 100;

        Integer steps = Integer.parseInt(((EditText) findViewById(R.id.et_steps)).getText().toString());

        new AsyncTask<Integer, String, RandomWalk>() {
            @Override
            protected RandomWalk doInBackground(Integer... steps) {
                int iterations = steps[0];
                Random random = new Random();
                RandomWalk randomWalk = new RandomWalk(iterations);

                try {
                    // Montecarlo
                    for (int i = 0; i < iterations; i++) {

                        int dx = 0;
                        int dy = 0;

                        int r = random.nextInt(maxRangeAleatory);

                        if(r >= 00 && r <25) dy =   1;
                        if(r >= 25 && r <50) dy =  -1;
                        if(r >= 50 && r <75) dx =   1;
                        if(r >= 75 && r <100) dx = -1;

                        System.out.println(dx + "," + dy + "," + r);

                        int x =  (randomWalk.xArray[i] + dx);
                        int y =  (randomWalk.yArray[i] + dy);

                        randomWalk.xArray[i + 1] = x;
                        randomWalk.yArray[i + 1] = y;
                    }

                    return randomWalk;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(RandomWalk randomWalk) {
                super.onPostExecute(randomWalk);

                if (randomWalk == null) {
                    Toast.makeText(mContext, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                } else {
                    findViewById(R.id.tv_Result).setVisibility(View.VISIBLE);
                    findViewById(R.id.tv_Displacement).setVisibility(View.INVISIBLE);
                    showResults(randomWalk);
                }
            }
        }.execute(steps);
    }

    private void showResults(RandomWalk randomWalk) {
        if (mTimer != null)
            mTimer.cancel();

        mRandomWalk = randomWalk;
        stepCounter = 0;
        ((TextView) findViewById(R.id.tv_Answer)).setText("Calculando...");

        ArrayList<Step> steps = new ArrayList<>();
        Step step = new Step();
        step.x = randomWalk.xArray[0];
        step.y = randomWalk.yArray[0];

        steps.add(step);

        if (mSparkView == null) {
            mSparkView = findViewById(R.id.spark_view);
        }

        mSparkAdapter = new CustomSparkAdapter(steps);

        mSparkView.setAdapter(mSparkAdapter);

        int duration = Integer.parseInt(((EditText) findViewById(R.id.et_time)).getText().toString());
        duration *= 1000; // convert to milliseconds
        startAnimation(duration);
    }

    public void startAnimation(int duration) {
        long dummyTime = System.currentTimeMillis();

        int countdown = duration / mRandomWalk.steps;

        mTimer = new CountDownTimer(dummyTime, countdown) {

            public void onTick(long millis) {
                // OBTIENE LOS VALORES ALEATORIOS
                nextStep(stepCounter);

                stepCounter++;

                if (stepCounter == mRandomWalk.xArray.length) {
                    String coordinates = "(" + mRandomWalk.xArray[mRandomWalk.xArray.length - 1] + ", "
                            + mRandomWalk.yArray[mRandomWalk.yArray.length - 1] + ")";
                    ((TextView) findViewById(R.id.tv_Answer)).setText(coordinates);
                    calculateDisplacement();
                    mTimer.cancel();
                }
            }

            public void onFinish() {
            }
        };

        mTimer.start();
    }

    private void calculateDisplacement() {

        DecimalFormat formato = new DecimalFormat("#.000000");
        findViewById(R.id.tv_Displacement).setVisibility(View.VISIBLE);

        double x = Math.pow(mRandomWalk.xArray[mRandomWalk.xArray.length -1],2);
        double y = Math.pow(mRandomWalk.yArray[mRandomWalk.yArray.length -1],2);

        String displacement = "Desplazamiento: (" + formato.format(Math.sqrt(x+y)) + ").";
        ((TextView) findViewById(R.id.tv_Displacement)).setText(displacement);
    }

    private void nextStep(int index) {
        if (index < mRandomWalk.xArray.length) {
            Step step = new Step();

            step.x = mRandomWalk.xArray[index];
            step.y = mRandomWalk.yArray[index];

            mSparkAdapter.addValue(step);
        }
    }

    public class CustomSparkAdapter extends SparkAdapter {
        private ArrayList<Step> mDataset;

        public CustomSparkAdapter(ArrayList<Step> dataset) {
            mDataset = dataset;
        }

        public void addValue(Step step) {
            mDataset.add(step);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mDataset.size();
        }

        @Override
        public Object getItem(int index) {
            return mDataset.get(index);
        }

        @Override
        public float getY(int index) {
            return mDataset.get(index).y;
        }

        @Override
        public float getX(int index) {
            return mDataset.get(index).x;
        }
    }

}
