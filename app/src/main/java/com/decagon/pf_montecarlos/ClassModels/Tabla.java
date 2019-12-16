package com.decagon.pf_montecarlos.ClassModels;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.decagon.pf_montecarlos.R;

import java.util.ArrayList;

public class Tabla {

    private TableLayout table;
    private ArrayList<TableRow> rows;
    private Activity activity;
    private Resources rs;
    private int ROWS, COLUMN;

    public Tabla (Activity activity,TableLayout table){
        this.activity = activity;
        this.table = table;
        rs = this.activity.getResources();
        ROWS = COLUMN = 0;
        rows = new ArrayList<>();
    }

    public void addTitle(int number_title){
        TableRow.LayoutParams layoutParams;
        TableRow fila = new TableRow(activity);
        TableRow.LayoutParams layoutRow = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
        fila.setLayoutParams(layoutRow);

        String[] arraytitle = rs.getStringArray(number_title);
        COLUMN = arraytitle.length;

        for(int i = 0; i< arraytitle.length; i++){
            TextView title = new TextView(activity);
            layoutParams = new TableRow.LayoutParams(sizeTable(arraytitle[i]),TableRow.LayoutParams.WRAP_CONTENT);
            title.setText(arraytitle[i]);
            title.setPadding(0,0,5,5);
            title.setTextSize(18);
            title.setGravity(Gravity.CENTER_HORIZONTAL);
            title.setTypeface(Typeface.DEFAULT_BOLD);
            title.setTextColor(activity.getResources().getColor(R.color.white));
            title.setBackground(ContextCompat.getDrawable(activity,R.drawable.rectangle_title));
            title.setLayoutParams(layoutParams);
            fila.addView(title);
        }
        table.addView(fila);
        rows.add(fila);

        ROWS++;
    }

    public void addRows(ArrayList<String> element){
        TableRow.LayoutParams layoutParams;
        TableRow.LayoutParams LayoutRow = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
        TableRow fila = new TableRow(activity);

        for(int i = 0; i < element.size(); i++){
            TextView title = new TextView(activity);
            title.setText(String.valueOf(element.get(i)));
            title.setPadding(5,5,5,5);
            title.setTextSize(16);
            title.setGravity(Gravity.CENTER_HORIZONTAL);
            title.setBackground(ContextCompat.getDrawable(activity,R.drawable.rectangle));
            layoutParams = new TableRow.LayoutParams(sizeTable(title.getText().toString()),TableRow.LayoutParams.WRAP_CONTENT);
            title.setLayoutParams(layoutParams);
            fila.addView(title);
        }
        table.addView(fila);
        rows.add(fila);

        ROWS++;
    }

    private int sizeTable(String s) {
        Paint p = new Paint();
        Rect bounds = new Rect();
        p.setTextSize(50);

        p.getTextBounds(s, 0, s.length(), bounds);
        return bounds.width();
    }

    public void removeAll(){
        table.removeAllViews();
    }
}
