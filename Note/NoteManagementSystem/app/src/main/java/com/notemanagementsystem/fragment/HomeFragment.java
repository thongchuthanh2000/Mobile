package com.notemanagementsystem.fragment;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.notemanagementsystem.AppDatabase;
import com.notemanagementsystem.R;
import com.notemanagementsystem.constant.SystemConstant;
import com.notemanagementsystem.entity.Status;
import com.notemanagementsystem.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;

import lecho.lib.hellocharts.view.PieChartView;

/*
 *StatusFragment
 *@author  Chu Thanh
 * @version 1.0
 * @since   2021-04-24
 */
public class HomeFragment extends Fragment {


    private PieChartView pieChartView;

    BiFunction<String,String,String> getLabel = (String name, String value)->{
        return  name + ": " + value+"%";
    };

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        pieChartView = view.findViewById(R.id.chart);

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

        List pieData = new ArrayList<>();



//        //Get list status by User
        List<Status> status = new ArrayList<>();
        status = AppDatabase.getAppDatabase(view.getContext()).statusDAO().getAllById(userId);

        Double sumNote = Double.valueOf(AppDatabase.getAppDatabase(view.getContext()).noteDAO().getCountByNote(userId));
        for (int i=0;i<status.size();i++){
            Integer value = AppDatabase.getAppDatabase(view.getContext()).noteDAO().getStatusByNote(status.get(i).getId(),userId);

            if (value==0){
                continue;
            }

            String name  =  status.get(i).getName();
            Integer color = SystemConstant.color[i % SystemConstant.lengthColor];
            Double percent = value/sumNote*100;

            pieData.add(new SliceValue(value, color).setLabel(getLabel.apply(name,String.format("%.1f",percent))));
        }

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true);
        pieChartData.setValueLabelTextSize(10);
        pieChartView.setPieChartData(pieChartData);
    }

}