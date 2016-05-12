package com.cmax.Exercise14;

/**
 * Created by Clint on 5/11/2016.
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// a To-do class with a basic constructor, getters, and setters
class Todo {
    private String title;
    private String body;
    private int priority;
    private Integer id = null; //only set an int when the server assigns an ID

    public Todo(String title, String body, int priority) {
        this.title = title;
        this.body = body;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TODO ID: " + id + ", Title: " + title + ", Body: " + body
                + ", Priority: " + priority;
    }
}

class TodoCollection extends ArrayList<Todo> {}

public class Main {
    public static void main(String[] args) {
        String jsonData = "[\n" +
                "        {\"body\": \"Walk the dog\",\"done\": false,\"id\": 0,\"priority\": 3,\"title\": \"dog\"},\n" +
                "        {\"body\": \"Pay the bills\",\"done\": false,\"id\": 1,\"priority\": 1,\"title\": \"bills\"        }\n" +
                "        ]";


        Gson gson = new Gson();
        TodoCollection todos = gson.fromJson(jsonData, TodoCollection.class);
        for (Todo todo: todos) {
            System.out.println(todo);
        }
// create a string with JSON data
        System.out.println(gson.toJson(todos));
    }
}

