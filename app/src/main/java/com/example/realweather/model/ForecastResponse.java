package com.example.realweather.model;

import com.example.realweather.model.Forecast;

import java.util.ArrayList;
import java.util.List;

public class ForecastResponse {

    private int count;
    private List<Forecast> list = new ArrayList<>();


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Forecast> getList() {
        return list;
    }

    public void setList(List<Forecast> list) {
        this.list = list;
    }
}
