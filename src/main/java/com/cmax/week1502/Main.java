package com.cmax.week1502;

/**
 * Created by Clint on 5/4/2016.
 */

import org.apache.http.HttpResponse;
        import org.apache.http.StatusLine;
        import org.apache.http.client.methods.CloseableHttpResponse;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.impl.client.CloseableHttpClient;
        import org.apache.http.impl.client.HttpClientBuilder;
        import org.apache.http.impl.client.HttpClients;
        import org.apache.http.util.EntityUtils;

        import java.io.IOException;

class HttpException extends IOException {
    public HttpException(HttpResponse response) {
        super(response.getStatusLine().getStatusCode() + ": "
                + response.getStatusLine().getReasonPhrase());
    }
}

class HttpRequests {
    private CloseableHttpClient client = HttpClientBuilder.create().build();

    // return true if status is between 200 and 300
    private static boolean isSuccess(HttpResponse response) throws IOException {
        StatusLine status = response.getStatusLine();
        return (status.getStatusCode() >= 200 && status.getStatusCode() < 300);
    }

    public String get(String url) throws IOException {
        HttpGet request = new HttpGet(url);
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
}

public class Main {
    public static void main(String[] args) {
        HttpRequests requests = new HttpRequests();
        try {
            System.out.println(requests.get("http://www.example.com"));
        } catch (IOException e) {
            System.out.println("Unable to open URL");
        }
    }
}
