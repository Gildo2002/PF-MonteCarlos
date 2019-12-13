package com.decagon.pf_montecarlos.ClassFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.decagon.pf_montecarlos.ClassHelper.SimpleValidator;
import com.decagon.pf_montecarlos.ClassModels.ExpRandom;
import com.decagon.pf_montecarlos.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.decagon.pf_montecarlos.R;

public class tabs2 extends Fragment {

    private Context context;
    private Button button;
    private EditText editText01;
    private EditText editText02;
    private EditText editText03;
    private View view;
    private AnyChartView anyChartView;

    private OnFragmentInteractionListener mListener;

    public tabs2() {

    }

    public static tabs2 newInstance(String param1, String param2) {
        tabs2 fragment = new tabs2();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tabs2, container, false);

        button = (Button) view.findViewById(R.id.btn_tabs2_show);
        editText01 = (EditText) view.findViewById(R.id.et_tabs2_v01);
        editText02 = (EditText) view.findViewById(R.id.et_tabs2_v02);

        context = container.getContext();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String value_a = editText01.getText().toString();
                    String value_b = editText02.getText().toString();

                    boolean trust =  (SimpleValidator.validate(SimpleValidator.NOT_EMPTY,
                            value_a)
                            && Double.valueOf(value_a) > 0 &&
                            SimpleValidator.validate(SimpleValidator.NOT_EMPTY,
                            value_b) &&
                            Double.valueOf(value_b) > 0);

                    if(trust){
                        calculateNumExp(Double.valueOf(value_a),Double.valueOf(value_b));
                    }
                    else
                        Toast.makeText(context, "Error en los datos", Toast.LENGTH_SHORT).show();

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    private void calculateNumExp(Double value_a, Double value_b){

        anyChartView = view.findViewById(R.id.any_chart_view_tabs2);
        APIlib.getInstance().setActiveAnyChartView(anyChartView);
        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar_tabs2));

        List<Double> number = new ArrayList<>();

        for(int i=0;i<100;i++) {
            double result = ExpRandom.generadorDistribucionUniformeAB(value_a,value_b);
            number.add(result);
        }

        Collections.sort(number,Collections.<Double>reverseOrder());

        List<DataEntry> data = new ArrayList<>();
        data.clear();

        for(Double result : number) {
            data.add(new ValueDataEntry(String.valueOf(result),result));
        }

        Cartesian cartesian = AnyChart.column();
        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(0d)
                .format("{%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Distribuccion Uniforme.");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.SINGLE);

        cartesian.xAxis(0).title("x");
        cartesian.yAxis(0).title("y");

        anyChartView.setChart(cartesian);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
