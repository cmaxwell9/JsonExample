package com.cmax.week1501;



        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;



public class Main {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://www.example.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStreamReader inputStreamReader
                    = new InputStreamReader(connection.getInputStream());
            BufferedReader input = new BufferedReader(inputStreamReader);

            System.out.println("RESPONSE STATUS: "
                    + connection.getResponseCode()
                    + " " + connection.getResponseMessage());

            String line;
            System.out.println("RESPONSE BODY");
            do {
                line = input.readLine();
                System.out.println(line);
            } while (line != null);
            System.out.println("END OF RESPONSE BODY");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
