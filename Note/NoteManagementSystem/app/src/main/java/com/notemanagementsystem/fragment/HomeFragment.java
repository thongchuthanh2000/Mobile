package com.notemanagementsystem.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.notemanagementsystem.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {


    private AnyChartView anyChartView;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        anyChartView = view.findViewById(R.id.any_chart_view);
        setupPieChart();
        return  view;
    }

    private  void setupPieChart(){
        String [] status = {"Done","Pending","Processing"};
        int [] value = {1,2,3};
        List<DataEntry> dataEntries = new ArrayList<>();

        Pie pie = AnyChart.pie();
        for (int i=0;i<status.length;i++){
            dataEntries.add(new ValueDataEntry(status[i],value[i]));
        }
        pie.data(dataEntries);

        anyChartView.setZoomEnabled(true);
        anyChartView.setChart(pie);
    }

}