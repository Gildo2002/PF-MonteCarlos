package com.decagon.pf_montecarlos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableLayout;

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
import com.decagon.pf_montecarlos.ClassModels.FunctionTable;
import com.decagon.pf_montecarlos.ClassModels.Tabla;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FunctionEffectivinessActivity extends AppCompatActivity {

    private Tabla table;
    private FunctionTable value;
    private FunctionEffectivinessActivity mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_effectiviness);

        setTableData();
        setTableInfo();
        setupToolbar();
        setChartView01();
        setChartView02();
        setChartView03();
        setChartView04();
    }

    private void setTableData(){
        table = new Tabla(mContext,(TableLayout) findViewById(R.id.table_function_01));
        table.removeAll();
        table.addTitle(R.array.row_title_function_01);

        ArrayList<String> number = new ArrayList<>();
        number.add("500");
        number.add("1000");
        number.add("1500");
        number.add("3000");
        number.add("2500");
        number.add("1500");

        for(int i = 0; i < number.size(); i++){
            ArrayList<String> elementos = new ArrayList<>();
            elementos.add(String.valueOf(i));
            elementos.add(number.get(i));
            table.addRows(elementos);
        }

        table = new Tabla(mContext,(TableLayout) findViewById(R.id.table_function_02));
        table.removeAll();
        table.addTitle(R.array.row_title_function_02);

        ArrayList<String> valueX = new ArrayList<>();
        valueX.add("00-09");
        valueX.add("10-29");
        valueX.add("30-54");
        valueX.add("55-79");
        valueX.add("80-94");
        valueX.add("95-99");

        ArrayList<String> valueY = new ArrayList<>();
        valueY.add("00-24");
        valueY.add("25-54");
        valueY.add("55-79");
        valueY.add("80-99");
        valueY.add(null);
        valueY.add(null);

        ArrayList<String> valueZ = new ArrayList<>();
        valueZ.add("0-2");
        valueZ.add("3-7");
        valueZ.add("8-9");
        valueZ.add(null);
        valueZ.add(null);
        valueZ.add(null);

        for(int i = 0; i < valueX.size(); i++){
            ArrayList<String> elementos = new ArrayList<>();
            elementos.add(String.valueOf(i));
            elementos.add(valueX.get(i));

            if(valueY.get(i) != null){
                elementos.add(String.valueOf(i));
                elementos.add(valueY.get(i));}
            else{
                elementos.add("");
                elementos.add(""); }
            if(valueZ.get(i) != null){
                elementos.add(String.valueOf(i));
                elementos.add(valueZ.get(i));}
            else{
                elementos.add("");
                elementos.add(""); }

            table.addRows(elementos);
        }


    }

    private void setTableInfo(){
        table = new Tabla(mContext,(TableLayout) findViewById(R.id.table_function_03));
        table.removeAll();
        table.addTitle(R.array.row_title_function_03);

        ArrayList<String> number = new ArrayList<>();

        int iterations = 10;
        value = new FunctionTable(iterations);

        for(int i = 0; i < iterations; i++){
            int x = randomNumber(0,99);
            value.rX[i] = x;
            value.vX[i] = rangeX(x);
            int y = randomNumber(0,99);
            value.rY[i] = y;
            value.vY[i] = rangeY(y);
            int z = randomNumber(0,99);
            value.rZ[i] = z;
            value.vZ[i] = rangeY(z);
        }

        for(int i = 0; i < iterations; i++){
            ArrayList<String> elementos = new ArrayList<>();
            elementos.add(String.valueOf(i));
            elementos.add(String.valueOf(value.rX[i]));
            elementos.add(String.valueOf(value.vX[i]));
            elementos.add(String.valueOf(value.rY[i]));
            elementos.add(String.valueOf(value.vY[i]));
            elementos.add(String.valueOf(value.rZ[i]));
            elementos.add(String.valueOf(value.vZ[i]));
            table.addRows(elementos);
        }
    }

    private int rangeX(int i) {
        int v = 0;

        if(i>0 && i<10)     v = 1;
        if(i>9 && i<30)     v = 2;
        if(i>29 && i<55)    v = 3;
        if(i>54 && i<80)    v = 4;
        if(i>79 && i<95)    v = 5;
        if(i>94 && i<100)   v = 6;

        return v;
    }

    private int rangeY(int i) {
        int v = 0;

        if(i>0 && i<25)     v = 1;
        if(i>24 && i<55)    v = 2;
        if(i>54 && i<80)    v = 3;
        if(i>79 && i<100)   v = 4;

        return v;
    }

    private int rangeZ(int i) {
        int v = 0;

        if(i>0 && i<3)    v = 1;
        if(i>2 && i<8)    v = 2;
        if(i>7 && i<10)    v = 3;

        return v;
    }

    private void setChartView01(){

        AnyChartView chartView01 = findViewById(R.id.chart_function_01);
        APIlib.getInstance().setActiveAnyChartView(chartView01);

        List<DataEntry> data = new ArrayList<>();
        data.clear();

        data.add(new ValueDataEntry("1",0.1));
        data.add(new ValueDataEntry("2",0.2));
        data.add(new ValueDataEntry("3",0.25));
        data.add(new ValueDataEntry("4",0.25));
        data.add(new ValueDataEntry("5",0.15));
        data.add(new ValueDataEntry("6",0.05));

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
        cartesian.title("Distribuciones de X.");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        chartView01.setChart(cartesian);
    }

    private void setChartView02() {
        AnyChartView chartView02 = findViewById(R.id.chart_function_02);
        APIlib.getInstance().setActiveAnyChartView(chartView02);

        List<DataEntry> data = new ArrayList<>();
        data.clear();

        data.add(new ValueDataEntry("1",0.25));
        data.add(new ValueDataEntry("2",0.3));
        data.add(new ValueDataEntry("3",0.25));
        data.add(new ValueDataEntry("4",0.2));

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
        cartesian.title("Distribuciones de Y.");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        chartView02.setChart(cartesian);
    }

    private void setChartView03() {
        AnyChartView chartView03 = findViewById(R.id.chart_function_03);
        APIlib.getInstance().setActiveAnyChartView(chartView03);

        List<DataEntry> data = new ArrayList<>();
        data.clear();

        data.add(new ValueDataEntry("1",0.25));
        data.add(new ValueDataEntry("2",0.3));
        data.add(new ValueDataEntry("3",0.25));
        data.add(new ValueDataEntry("4",0.2));

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
        cartesian.title("Distribuciones de Z.");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        chartView03.setChart(cartesian);
    }

    private void setChartView04(){
        AnyChartView chartView04 = findViewById(R.id.chart_function_04);
        APIlib.getInstance().setActiveAnyChartView(chartView04);

        List<Integer> w = new ArrayList<>();

        for(int i=0; i < value.number; i++){
            w.add(value.vX[i] + value.vY[i] + value.vZ[i]);
        }

        Collections.sort(w);

        List<DataEntry> data = new ArrayList<>();
        data.clear();

        for(int i=0; i < value.number; i++){
            data.add(new ValueDataEntry(String.valueOf(i),w.get(i)));
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
        cartesian.title("Grafica De Distribucion de W.");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        chartView04.setChart(cartesian);
    }

    private void setupToolbar(){
        setTitle(R.string.title_Function_Effectiviness);
    }

    private int randomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

}
