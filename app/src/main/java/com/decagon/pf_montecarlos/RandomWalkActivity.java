package com.decagon.pf_montecarlos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import com.decagon.pf_montecarlos.ClassHelper.SimpleValidator;
import com.decagon.pf_montecarlos.ClassModels.RandomWalk;
import com.decagon.pf_montecarlos.ClassModels.Step;
import com.robinhood.spark.*;

import java.util.ArrayList;

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

                        if (trust) {
                            calculateRandomWalk();
                        } else
                            Toast.makeText(mContext, "Error en los datos", Toast.LENGTH_SHORT).show();
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

        Integer steps = Integer.parseInt(((EditText) findViewById(R.id.et_steps)).getText().toString());

        new AsyncTask<Integer, String, RandomWalk>() {
        @Override
        protected RandomWalk doInBackground(Integer... steps) {
            int iterations = steps[0];

            RandomWalk randomWalk = new RandomWalk(iterations);

            try {
                //Montecarlo
                for (int i = 0; i < iterations; i++) {
                    double dx = 1 - 2 * Math.random();
                    double dy = 1 - 2 * Math.random();

                    float x = (float) (randomWalk.xArray[i] + dx);
                    float y = (float) (randomWalk.yArray[i] + dy);

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

//                    showGraph(randomWalk);

                showResults(randomWalk);
            }
        }
    }.execute(steps);
}

    private void showResults(RandomWalk randomWalk) {
        if (mTimer != null) mTimer.cancel();

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
        duration *= 1000; //convert to milliseconds
        startAnimation(duration);
    }

    public void startAnimation(int duration) {
        long dummyTime = System.currentTimeMillis();

        int countdown = duration / mRandomWalk.steps;

        mTimer = new CountDownTimer(dummyTime, countdown) {

            public void onTick(long millis) {
                //OBTIENE LOS VALORES ALEATORIOS
                nextStep(stepCounter);

                stepCounter++;

                if (stepCounter == mRandomWalk.xArray.length) {
                    String coordinates = "(" + mRandomWalk.xArray[mRandomWalk.xArray.length - 1] + ", "
                            + mRandomWalk.yArray[mRandomWalk.yArray.length - 1] + ")";
                    ((TextView) findViewById(R.id.tv_Answer)).setText(coordinates);

                    mTimer.cancel();
                }
            }

            public void onFinish() {

            }
        };

        mTimer.start();
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
