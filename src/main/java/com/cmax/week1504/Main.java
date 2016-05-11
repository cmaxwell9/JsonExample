package com.cmax.week1504;



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

public class Main {
    public static void main(String[] args) {
        // these examples may not work in the future as the RESTful service may not be running
        HttpRequests requests = new HttpRequests("clint", "password");
        String contentType = "application/json";
        String data = "{\"title\":\"dinner\", \"body\":\"prepare dinner\", \"priority\": 3}";

        // post example
        String url = "http://todo.eastus.cloudapp.azure.com/todo-android/todos/api/v1.0/todo/create";
        System.out.println("POST Example - add a todo");
        try {
            String response = requests.post(url, contentType, data);
            System.out.println("POST RESPONSE: " + response);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // put example
        data = "{\"title\":\"dinner\", \"body\":\"eat dinner\", \"priority\": 1}";
        url = "http://todo.eastus.cloudapp.azure.com/todo-android/todos/api/v1.0/todo/update/0";
        System.out.println("PUT Example - update a todo");
        try {
            String response = requests.put(url, contentType, data);
            System.out.println("PUT RESPONSE: " + response);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // get example
        url = "http://todo.eastus.cloudapp.azure.com/todo-android/todos/api/v1.0/todos";
        System.out.println("GET Example - list all todos");
        try {
            String response = requests.get(url);
            System.out.println("GET RESPONSE: " + response);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // delete example
        url = "http://todo.eastus.cloudapp.azure.com/todo-android/todos/api/v1.0/todo/delete/0";
        System.out.println("Delete Example - delete a todo");
        try {
            String response = requests.delete(url);
            System.out.println("DELETE RESPONSE: " + response);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}