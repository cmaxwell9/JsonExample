package com.cmax.week1403;



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
        String jsonData = "{\"name\": \"columbus\", \"forecast\": " +
                "[40, 50, 65, 60, 70]}";

        Gson gson = new Gson();
        Forecast forecast = gson.fromJson(jsonData, Forecast.class);
        System.out.println(forecast);
    }
}
