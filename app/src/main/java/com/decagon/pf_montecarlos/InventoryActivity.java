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
import com.anychart.core.ui.Table;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.decagon.pf_montecarlos.ClassModels.ExpRandom;
import com.decagon.pf_montecarlos.ClassModels.Tabla;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class InventoryActivity extends AppCompatActivity {

    private AnyChartView chartView01;
    private AnyChartView chartView02;
    private Tabla table;
    private InventoryActivity mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        setTableData();
        setTableInfo();
        setupToolbar();
        setChartView01();
        setChartView02();
    }

    private void setChartView01() {
        chartView01 = findViewById(R.id.chart_invetory_01);
        APIlib.getInstance().setActiveAnyChartView(chartView01);

        List<DataEntry> data = new ArrayList<>();
        data.clear();

        data.add(new ValueDataEntry("1",0.05));
        data.add(new ValueDataEntry("2",0.10));
        data.add(new ValueDataEntry("3",0.15));
        data.add(new ValueDataEntry("4",0.30));
        data.add(new ValueDataEntry("5",0.25));
        data.add(new ValueDataEntry("6",0.15));

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
        cartesian.title("Gráfica de Distribución de la Demanda.");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("x");
        cartesian.yAxis(0).title("y");

        chartView01.setChart(cartesian);
    }

    private void setChartView02() {
        chartView02 = findViewById(R.id.chart_invetory_02);
        APIlib.getInstance().setActiveAnyChartView(chartView02);

        List<DataEntry> data = new ArrayList<>();
        data.clear();

        data.add(new ValueDataEntry("1",0.05));
        data.add(new ValueDataEntry("2",0.15));
        data.add(new ValueDataEntry("3",0.30));
        data.add(new ValueDataEntry("4",0.60));
        data.add(new ValueDataEntry("5",0.85));
        data.add(new ValueDataEntry("6",1.00));

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
        cartesian.title("Gráfica de Distribución Acumulada de la Demanda.");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("x");
        cartesian.yAxis(0).title("y");

        chartView02.setChart(cartesian);
    }

    private void setTableData() {
        table = new Tabla(mContext,(TableLayout) findViewById(R.id.tablelayoutData));
        table.removeAll();
        table.addTitle(R.array.row_title_invetory);

        ArrayList<String> number = new ArrayList<>();
        number.add("00-04");
        number.add("05-14");
        number.add("15-29");
        number.add("30-59");
        number.add("60-84");
        number.add("85-99");

        for(int i = 0; i < number.size(); i++){
            ArrayList<String> elementos = new ArrayList<>();
            elementos.add(String.valueOf(i));
            elementos.add(number.get(i));
            table.addRows(elementos);
        }
    }

    private void setTableInfo() {
        table = new Tabla(mContext,(TableLayout) findViewById(R.id.tablelayoutSimulate));
        table.removeAll();
        table.addTitle(R.array.row_title_invetory);

        ArrayList<String> number = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            number.add(String.valueOf(randomNumber(1, 5)));
        }

        for(int i = 0; i < number.size(); i++){
            ArrayList<String> elementos = new ArrayList<>();
            elementos.add(String.valueOf(i));
            elementos.add(number.get(i));
            table.addRows(elementos);
        }
    }

    private void setupToolbar() {
        setTitle(R.string.title_Invetory);
    }

    private int randomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
