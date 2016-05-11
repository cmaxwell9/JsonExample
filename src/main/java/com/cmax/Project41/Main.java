package com.cmax.Project41;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Stream;

class HttpException extends IOException {
    public HttpException(HttpResponse response) {
        super(response.getStatusLine().getStatusCode() + ": "
                + response.getStatusLine().getReasonPhrase());
    }
}

class HttpRequests {
    private CloseableHttpClient client = HttpClientBuilder.create().build();

    public HttpRequests (String username, String password){
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));
        client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(credentialsProvider).build();
    }

    // return true if status is between 200 and 300
    private static boolean isSuccess(HttpResponse response) throws IOException {
        StatusLine status = response.getStatusLine();
        return (status.getStatusCode() >= 200 && status.getStatusCode() < 300);
    }

    // send specified request and return response as a string
    private String makeRequest(HttpRequestBase request) throws IOException {
        CloseableHttpResponse response = client.execute(request);
        try {
            if (!isSuccess(response)) {
                throw new HttpException(response);
            }

            return EntityUtils.toString(response.getEntity());
        }
        finally {
            response.close();
        }
    }

    private void addData(HttpEntityEnclosingRequestBase request, String contentType, String data)
            throws UnsupportedEncodingException {
        request.setHeader("Content-type", contentType);

        StringEntity requestData = new StringEntity(data);
        request.setEntity(requestData);
    }


    // send a GET request to the specified URL
    public String get(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        return makeRequest(request);

    }

    // send a DELETE request to the specified URL
    public String delete(String url) throws IOException {
        HttpDelete request = new HttpDelete(url);
        return makeRequest(request);
    }

    // send a POST request to the specified URL with the specified data
    // and the specified content-type
    public String post(String url, String contentType, String data) throws IOException{
        HttpPost request = new HttpPost(url);
        addData(request, contentType, data);
        return makeRequest(request);
    }

    // send a PUT request to the specified URL with the specified data
    // and the specified content-type
    public String put(String url, String contentType, String data) throws IOException{
        HttpPut request = new HttpPut(url);
        addData(request, contentType, data);
        return makeRequest(request);
    }
}

// a To-do class with a basic constructor, getters, and setters
class Todo implements Comparable<Todo>{
    private String title;
    private String body;
    private Integer priority;
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
    public void display(){
        System.out.println("Name: " + title + ", Desc: " + body + ", Pri: " + priority);
    }


    @Override
    public String toString() {
        return "TODO ID: " + id + ", Title: " + title + ", Body: " + body
                + ", Priority: " + priority;
    }
    @Override
    public int compareTo(Todo o) {
        if (!priority.equals(o.priority)) {
            return priority.compareTo(o.priority);
        } else {
            return title.compareTo(o.title);
        }
    }
}

// a collection of Todos; only support iteration
class TodoCollection implements Iterable<Todo> {
    private List<Todo> todos;

    @Override
    public Iterator<Todo> iterator() {
        return todos.iterator();
    }

}

class TodoAPIWrapper {
    Gson gson = new Gson();
    private HttpRequests requests;
    private String hostURL;

    TodoAPIWrapper(String username, String password, String hostURL) {
        requests = new HttpRequests(username, password);
        this.hostURL = hostURL;
    }

    // get all the todos
    public TodoCollection getTodos() {
        String url = hostURL + "/todos/api/v1.0/todos";
        try {
            String response = requests.get(url);
            return gson.fromJson(response, TodoCollection.class);
        } catch (IOException e) {
            System.out.println("Unable to get todos");
        }
        return null;
    }

    // create a new todo
    public Todo createTodo(String title, String body, int priority) {
        Todo newTodo = new Todo(title, body, priority);
        String url = hostURL + "/todos/api/v1.0/todo/create";
        String contentType  = "application/json";
        String postData = gson.toJson(newTodo);
        try {
            requests.post(url, contentType, postData);
            return newTodo;
        } catch (IOException e) {
            System.out.println("Unable to create new task: " + title);
        }
        return null;
    }

    // get a todo by id
    public Todo getTodo(int id) {
        String url = hostURL + "/todos/api/v1.0/todo/" + id;
        try {
            String response = requests.get(url);
            return gson.fromJson(response, Todo.class);
        } catch (IOException e) {
            System.out.println("Unable to get todo with id" + id);
        }
        return null;
    }

    // get first todo that matches title
    public Todo getTodo(String title) {
        TodoCollection todos = getTodos();
        for (Todo todo: todos) {
            if (todo.getTitle().equals(title)) {
                return todo;
            }
        }
        return null;
    }

    // delete a todo by id
    public boolean removeTodo(int id) {
        String url = hostURL + "/todos/api/v1.0/todo/delete/" + id;
        try {
            requests.delete(url);
            return true;
        }
        catch (IOException e) {
            System.out.println("Unable to delete todo with ID " + id);
        }
        return false;
    }
}

public class Main {
    public static void main(String[] args) {
        TodoAPIWrapper todoAPI = new TodoAPIWrapper("clint", "clint",
                "http://todo.eastus.cloudapp.azure.com/todo-android");
        String userInput;
        userInput = "6";
        while (((int) Double.parseDouble(userInput)) != 0) {
            System.out.println("Menu");
            System.out.println("1) Add a task.");
            System.out.println("2) Remove a task.");
            System.out.println("3) Update a task.");
            System.out.println("4) List all tasks.");
            System.out.println("5) List all tasks of a certain priority.");
            System.out.println("6) Sort tasks by Pri, Name ");
            System.out.println("0) Exit.");
            System.out.println("Choose an Option.");
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            userInput = scanner.nextLine();

            try {
                int x = ((int) Double.parseDouble(userInput));
            } catch (NumberFormatException nfe) {
                userInput = "6";
            }

            switch ((int) Double.parseDouble(userInput)) {
                case 1:
                    System.out.println("Enter the new task's name.");
                    String title;
                    title = scanner.nextLine();
                    System.out.println("Enter the new task's description.");
                    String body;
                    body = scanner.nextLine();
                    System.out.println("Enter the new task's priority");
                    userInput = scanner.nextLine();
                    int pri =0;
                    try { pri = ((int) Double.parseDouble(userInput));}
                    catch(NumberFormatException nfe){
                        System.out.println("Pri must be between 1,2,3,4, or 5");
                        userInput = "6";};
                    todoAPI.createTodo(title, body, pri);
                    System.out.println("****** added ******");
                    break;
                case 2:
                    System.out.println("Enter the index of the contact to remove.");
                    userInput = scanner.nextLine();
                    try { int x = ((int) Double.parseDouble(userInput));
                        todoAPI.removeTodo(x);}
                    catch(NumberFormatException nfe){
                        System.out.println(" invalid index ");
                           }
                    break;
                case 3:
                     System.out.println("Update currently unavailable");
                     System.out.println("Use delete & add as substitute");
                    break;
                case 4:
                    System.out.println("Getting a list of all tasks");
                    TodoCollection todos = todoAPI.getTodos();
                    for (Todo todo: todos) {
                        System.out.println(todo);
                    }
                    break;
                case 5:
                    System.out.println("Enter the priority you want to list:");
                    userInput = scanner.nextLine();
                    try { int x = ((int) Double.parseDouble(userInput));

                        TodoCollection todos2 = todoAPI.getTodos();
                        for (Todo todo: todos2) {
                            int getpri = todo.getPriority();
                            if (x == getpri) {
                            System.out.println(todo);}
                        }
                    }
                    catch(NumberFormatException nfe){
                        System.out.println(" invalid index ");
                    }
                    break;
                case 6:
                    List<Todo> strList = new ArrayList<Todo>();
                    todos = todoAPI.getTodos();
                    for (Todo todo : todos) {
                        strList.add(todo);
                    }
                    Collections.sort(strList);
                    for(Todo str: strList){
                        System.out.println(str);
                    }
                    System.out.println("****** sorted ******");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid Entry, Please try again...");
                    break;
            }
        }
    }
}
