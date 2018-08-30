package me.black.demolib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.black.demolib.view.chart.Chart;
import me.black.demolib.view.chart.data.AbstractData;
import me.black.demolib.view.chart.data.Calory;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        Chart chart = findViewById(R.id.chart);
        Button btnDay = findViewById(R.id.btn_day);
        Button btnWeek = findViewById(R.id.btn_week);
        Button btnMonth = findViewById(R.id.btn_month);
        btnDay.setOnClickListener(v -> {

        });
        btnWeek.setOnClickListener(v -> {
            chart.showMonth(false);
        });
        btnMonth.setOnClickListener(v -> {
            chart.showMonth(true);
        });

        List<AbstractData> abstractDatas = new ArrayList<>();
        Calory calory = new Calory();
        List<Float> floats = new ArrayList<>();
        for(int i = 0; i < 30; i ++) {
            floats.add((float) (100f * Math.random()));
        }
        calory.setValues(floats);
        Calendar now = Calendar.getInstance();
        abstractDatas.add(calory);
        chart.setData(abstractDatas);
    }
}
