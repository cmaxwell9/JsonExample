package com.cmax.week1404;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// a class representing a city's forecast
class Forecast {
    private String name;
    private List<Double> forecast;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getForecast() {
        return new ArrayList<>(forecast);
    }

    public void setForecast(List<Double> forecast) {
        this.forecast = forecast;
    }

    public String toString() {
        List<String> forecastStrings = new ArrayList<>();
        for (Double temp: forecast) {
            forecastStrings.add(temp.toString());
        }
        String forecastString = String.join(", ", forecastStrings);
        return String.format("The forecast for %s: is %s", name, forecastString);
    }
}

public class Main {
    public static void main(String[] args) {
        String jsonData = "[{\"name\": \"columbus\", \"forecast\": [40, 50, 65, 60, 70]},"
                + "{\"name\": \"cleveland\", \"forecast\": [35, 55, 60, 45, 65]},"
                + "{\"name\": \"cincinnati\", \"forecast\": [35, 60, 65, 45, 65]}]";

        Gson gson = new Gson();
        // create an array of Forecast elements
        Forecast[] forecasts = gson.fromJson(jsonData, Forecast[].class);

        for (Forecast forecast: forecasts) {
            System.out.println(forecast);
        }
    }
}
