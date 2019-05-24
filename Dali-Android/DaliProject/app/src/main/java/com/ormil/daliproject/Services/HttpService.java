package com.ormil.daliproject.Services;


import android.os.StrictMode;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpService {

    public static HttpService INSTANCE = new HttpService();
    public static final String endPoint = "http://project-dali.com:5000";
    public static final String androidPath = "/android";
    public static final String userPath = "/user";
    public static String response;

    private HttpService(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public static String get(String urlStr) throws Exception{
        URL url = new URL(urlStr);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
            HttpService.response = readStream(in);
        } finally {
            urlConnection.disconnect();
        }

        return HttpService.response;
    }

    private static String readStream(BufferedInputStream in) throws Exception{
        String response ="";
        byte[] contents = new byte[1024];

        int bytesRead = 0;
        while((bytesRead = in.read(contents)) != -1) {
            response += new String(contents, 0, bytesRead);
        }

        return response;
    }
}
