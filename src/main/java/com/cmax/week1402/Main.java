package com.cmax.week1402;
/*
/**
 * Created by Clint on 4/28/2016.

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

        JsonParser parser = new JsonParser();

        // a Forecast object
        Forecast forecast = new Forecast();

        JsonObject jsonObject = parser.parse(jsonData).getAsJsonObject();

        for (Map.Entry<String, JsonElement> entry: jsonObject.entrySet() ) {

            // if the value is a JSON array, create a list of values and
            // assign it to the forecast
            if (entry.getValue().isJsonArray()) {
                List<Double> forecastData = new ArrayList<>();
                JsonArray jsonArray = entry.getValue().getAsJsonArray();
                for (JsonElement element : jsonArray) {
                    forecastData.add(element.getAsDouble());
                }
                forecast.setForecast(forecastData);
            }

            // if the value is not a JSON array, it's the forecast's name
            else {
                forecast.setName(entry.getValue().getAsString());
            }
        }

        //display forecast
        System.out.println(forecast);
    }
}
*/