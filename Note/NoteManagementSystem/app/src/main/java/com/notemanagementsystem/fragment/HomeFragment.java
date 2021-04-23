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
import com.notemanagementsystem.AppDatabase;
import com.notemanagementsystem.R;
import com.notemanagementsystem.SessionManager;
import com.notemanagementsystem.entity.Status;

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
        setupPieChart(view);
        return  view;
    }

    private  void setupPieChart(View view){
//        String [] status = {"Done","Pending","Processing"};
        SessionManager sessionManager = new SessionManager(getContext());
        int userId = sessionManager.getUserId();

        List<Status> status = new ArrayList<>();
        status = AppDatabase.getAppDatabase(view.getContext()).statusDAO().getAllStatusById(userId);


        List<DataEntry> dataEntries = new ArrayList<>();

        Pie pie = AnyChart.pie();
        for (int i=0;i<status.size();i++){
            dataEntries.add(new ValueDataEntry(status.get(i).getName(),
                    AppDatabase.getAppDatabase(view.getContext()).noteDAO().getStatusByNote(status.get(i).getName(),userId)
            ));
        }
        pie.data(dataEntries);

        anyChartView.setZoomEnabled(true);
        anyChartView.setChart(pie);
    }

}