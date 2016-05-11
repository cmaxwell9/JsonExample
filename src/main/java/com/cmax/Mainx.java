package com.cmax;


import com.google.gson.*;
import java.util.Map;

public class Mainx {
    public static void main(String[] args) {

        String jsonData = "{\"name\": \"columbus\",v" +
                "\"forcast\": [70,80,70,65,75]}";

        JsonParser parser= new JsonParser();
        JsonObject jsonObject = parser.parse(jsonData).getAsJsonObject();

        for(Map.Entry<String, JsonElement> entry: jsonObject.entrySet()) {
        if (entry.getValue().isJsonArray()){
            JsonArray jsonArray = entry.getValue().getAsJsonArray();
            System.out.println("Key:"+ entry.getKey());
            System.out.println("Value:");
            for(JsonElement element: jsonArray){
                System.out.println(element);
            }

        }
        else {
            System.out.println(("key: " +  entry.getKey()));
            System.out.println("value: " + entry.getValue().getAsString());
        }

        System.out.println("Hello, world!");
    }
}}

