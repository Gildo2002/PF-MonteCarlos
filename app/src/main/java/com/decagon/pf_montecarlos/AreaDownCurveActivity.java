package com.decagon.pf_montecarlos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;
import android.util.Log;

import com.decagon.pf_montecarlos.ClassModels.AreaCurve;
import com.decagon.pf_montecarlos.ClassHelper.SimpleValidator;
import org.mariuszgromada.math.mxparser.*;

public class AreaDownCurveActivity extends AppCompatActivity {

    private AreaDownCurveActivity mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_down_curve);

        setupToolbar();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_accept:
                        boolean trust = SimpleValidator.validate(SimpleValidator.NOT_EMPTY,
                                ((EditText) findViewById(R.id.et_function)).getText().toString())
                                && SimpleValidator.validate(SimpleValidator.NOT_EMPTY,
                                ((EditText) findViewById(R.id.et_xmin)).getText().toString())
                                && SimpleValidator.validate(SimpleValidator.NOT_EMPTY,
                                ((EditText) findViewById(R.id.et_xmax)).getText().toString())
                                && SimpleValidator.validate(SimpleValidator.NOT_EMPTY,
                                ((EditText) findViewById(R.id.et_iterations)).getText().toString());

                        if (trust) {
                            calculateArea();
                        } else
                            Toast.makeText(mContext, "Error en los datos", Toast.LENGTH_SHORT).show();

                        try {
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        };
        findViewById(R.id.btn_accept).setOnClickListener(listener);
    }

    private void calculateArea() {

        String function = ((EditText) findViewById(R.id.et_function)).getText().toString().toLowerCase();
        double xmin = Double.parseDouble(((EditText) findViewById(R.id.et_xmin)).getText().toString());
        double xmax = Double.parseDouble(((EditText) findViewById(R.id.et_xmax)).getText().toString());
        int iterations = Integer.parseInt(((EditText) findViewById(R.id.et_iterations)).getText().toString());

        AreaCurve areaCurve = new AreaCurve();
        areaCurve.function = function;
        areaCurve.xmin = xmin;
        areaCurve.xmax = xmax;
        areaCurve.iterations = iterations;

        new AsyncTask<AreaCurve, String, AreaCurve>() {
            @Override
            protected AreaCurve doInBackground(AreaCurve... areaCurves) {
                AreaCurve areaCurve = areaCurves[0];

                String function = "f(x) = " + areaCurve.function;

                double xmin = areaCurve.xmin; //given xmin
                double xmax = areaCurve.xmax; //given xmax

                try {
                    // Find ymin and ymax
                    int numSteps = areaCurve.iterations; //bigger the better but slower!

                    Function f = new Function(function);
                    //((TextView) findViewById(R.id.tv_Answer)).setText( "Valor: " + e.calculate());

                    double ymin = f.calculate(xmin);
                    double ymax = ymin;

                    double x, y;

                    for (int i = 0; i < numSteps; i++) {
                        x = (xmin + (xmax - xmin) * (double) i) / numSteps;
                        y = f.calculate(x);
                        if (y < ymin) ymin = y;
                        if (y > ymax) ymax = y;
                    }

                    System.out.println(xmax + "," + xmin);

                    System.out.println(ymax + "," + ymin);

                    // Montecarlo
                    double rectArea = (xmax - xmin) * (ymax - ymin); // area of rectangle
                    int numPoints = areaCurve.iterations; //bigger the better but slower!
                    int success = 0;

                    for (int i = 0; i < numPoints; i++) {
                        // establish random coordinates
                        x = xmin + (xmax - xmin) * Math.random();
                        y = ymin + (ymax - ymin) * Math.random();

                        // checks if point is inside the area
                        double fx = f.calculate(x);
                        if (fx > 0 && y > 0 && y <= fx)
                            success += 1;
                        if (fx < 0 && y < 0 && y >= fx)
                            success += 1;
                    }

                    areaCurve.area = rectArea * (double) success / numPoints;

                    Log.e("AREA", String.valueOf(areaCurve.area));

                    return areaCurve;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(AreaCurve areaCurve) {
                super.onPostExecute(areaCurve);

                if (areaCurve == null) {
                    Toast.makeText(mContext, "Ha ocurrido un error. Verifique la función introducida.", Toast.LENGTH_SHORT).show();
                } else {
                    findViewById(R.id.tv_Result).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.tv_Answer)).setText(String.valueOf(areaCurve.area));
                }
            }
        }.execute(areaCurve);
    }

    private void setupToolbar() {
        setTitle(R.string.title_AreaDownCurve);
    }
}
