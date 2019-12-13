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

public class tabs1 extends Fragment{

    private Context context;
    private Button button;
    private EditText editText;
    private View view;
    private AnyChartView anyChartView;

    private OnFragmentInteractionListener mListener;

    public tabs1() {
    }

    // TODO: Rename and change types and number of parameters
    public static tabs1 newInstance() {
        tabs1 fragment = new tabs1();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tabs1, container, false);

        button = (Button) view.findViewById(R.id.btn_tabs1_show);
        editText = (EditText) view.findViewById(R.id.et_tabs1_v01);

        context = container.getContext();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String value = editText.getText().toString();
                    boolean trust =  (SimpleValidator.validate(SimpleValidator.NOT_EMPTY,
                            value)
                            && Double.valueOf(value) > 0);
                    if(trust){
                        calculateNumExp(Double.valueOf(value));
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

    private void calculateNumExp(Double value) {

        anyChartView = view.findViewById(R.id.any_chart_view_tabs1);
        APIlib.getInstance().setActiveAnyChartView(anyChartView);
        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar_tabs1));

        List<Double> number = new ArrayList<>();

        for(int i=0;i<100;i++) {
            double result = ExpRandom.generadorDistribucionExponencial(Double.valueOf(value));
            number.add(result);
        }
        Collections.sort(number);

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
        cartesian.title("Distribuccion Exponencial.");

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
