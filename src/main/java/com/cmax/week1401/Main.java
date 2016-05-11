package com.cmax.week1401;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String jsonData = "{\"name\": \"columbus\", \"forecast\": " +
                "[40, 50, 65, 60, 70]}";

        JsonParser parser = new JsonParser();

        // parse the JSON data and return a JSON object for the top-level data
        JsonObject jsonObject = parser.parse(jsonData).getAsJsonObject();

        // Iterate through the JSON object
        // Each key/value in the object can be represented as a
        // String/JSON element pair
        for (Map.Entry<String, JsonElement> entry: jsonObject.entrySet() ) {

            // if the value is a JSON array, convert the JSON element to
            // JSON array and iterate
            if (entry.getValue().isJsonArray()) {
                System.out.println(entry.getKey() + ":");

                JsonArray jsonArray = entry.getValue().getAsJsonArray();
                for (JsonElement element : jsonArray) {
                    System.out.println("\t" + element);
                }
            }

            // if the value is not a JSON array, get it's value
            else {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }

    }
}

