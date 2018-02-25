package com.example.degus.accesspedia;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dominik Nowak on 26.07.2017.
 */

public class ApiAdapter extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... uri) {
        return getJsonFromUrl(uri[0]);
    }

    public String getJsonFromUrl(String url) {
        HttpURLConnection connection;
        BufferedReader reader;

        try {
            URL urlObj = new URL(url);
            connection = (HttpURLConnection) urlObj.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
                Log.d("Response: ", "> " + line);
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
