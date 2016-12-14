package com.andrewlane.pin_itweather.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andrewlane.pin_itweather.R;
import com.andrewlane.pin_itweather.model.WeatherOverview;

import java.util.List;

/**
 * Created by andrewlane on 12/12/16.
 */

public class ForecastRecyclerAdapter extends RecyclerView.Adapter<ForecastRecyclerAdapter.ViewHolder> {

    private List<WeatherOverview> weatherOverviewList;

    public ForecastRecyclerAdapter(Context context, List<WeatherOverview> weatherOverviewList) {
        Context context1 = context;
        this.weatherOverviewList = weatherOverviewList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_overview, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WeatherOverview weatherOverview = weatherOverviewList.get(position);
        holder.city.setText(String.valueOf(weatherOverview.getLocation()));
        holder.currentCondiiton.setText(String.valueOf(weatherOverview.getCurrentCondition()));
        holder.temp.setText(String.valueOf(weatherOverview.getCurrentTemperature()));
    }

    @Override
    public int getItemCount() {
        return weatherOverviewList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView temp;
        TextView currentCondiiton;
        TextView city;

        public ViewHolder(View v) {
            super(v);
            currentCondiiton = (TextView) v.findViewById(R.id.tvCurrentCondition);
            city = (TextView) v.findViewById(R.id.tvCity);
            temp = (TextView) v.findViewById(R.id.tvTemperature);
        }
    }
}
