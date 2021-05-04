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
import com.notemanagementsystem.utils.SessionManager;
import com.notemanagementsystem.entity.Status;

import java.util.ArrayList;
import java.util.List;

/*
 *StatusFragment
 *@author  Chu Thanh
 * @version 1.0
 * @since   2021-04-24
 */
public class HomeFragment extends Fragment {


    private AnyChartView anyChartView;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        anyChartView = view.findViewById(R.id.any_chart_view);
        //Setup Pie Chart
        setupPieChart(view);
        return  view;
    }

    /*
    Setup pie chart
    @Parem View
    @Return null
     */
    private  void setupPieChart(View view){
        //Get userID by session
        int userId = SessionManager.getInstance().getUserId();

        //Get list status by User
        List<Status> status = new ArrayList<>();
        status = AppDatabase.getAppDatabase(view.getContext()).statusDAO().getAllById(userId);


        List<DataEntry> dataEntries = new ArrayList<>();

        //Get value
        Pie pie =  AnyChart.pie();
        for (int i=0;i<status.size();i++){
            dataEntries.add(new ValueDataEntry(status.get(i).getName(),
                    AppDatabase.getAppDatabase(view.getContext()).noteDAO().getStatusByNote(status.get(i).getName(),userId)
            ));
        }
        pie.data(dataEntries);
        //Set chart view
        anyChartView.setZoomEnabled(true);
        anyChartView.setChart(pie);
    }

}