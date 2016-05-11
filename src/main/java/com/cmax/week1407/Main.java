package com.cmax.week1407;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
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

class ForecastCollection extends ArrayList<Forecast> {}


public class Main {
    public static void main(String[] args) {
        String jsonData = "[{\"name\": \"columbus\", \"forecast\": [40, 50, 65, 60, 70]},"
                + "{\"name\": \"cleveland\", \"forecast\": [35, 55, 60, 45, 65]},"
                + "{\"name\": \"cincinnati\", \"forecast\": [35, 60, 65, 45, 65]}]";

        Gson gson = new Gson();

        ForecastCollection forecasts = gson.fromJson(jsonData, ForecastCollection.class);

        for (Forecast forecast: forecasts) {
            System.out.println(forecast);
        }

        // create a string with JSON data
        System.out.println(gson.toJson(forecasts));
    }
}
