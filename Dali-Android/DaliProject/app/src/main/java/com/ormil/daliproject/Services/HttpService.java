package com.ormil.daliproject.Services;


import android.os.StrictMode;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpService {

    public static HttpService INSTANCE = new HttpService();
    public static String userID = "";
    public static final String endPoint = "http://project-dali.com:5000";
    public static final String androidPath = "/android";
    public static final String userPath = "/user";
    public static final String systemPath = "/system";
    public static String response;

    private HttpService(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private static String get(String urlStr) throws Exception{
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

    public static String signInApp(String id, String name, String pictureUrl) throws Exception {
        userID = id;
        return get(endPoint + userPath + "/signInApp?" + "id=" + id + "&name=" + name + "&picture=" + pictureUrl);
    }

    public static String getArtworkByLocation(double latitude, double longitude) throws Exception {
        return get(endPoint + userPath + "/getArtsByLocation?" + "lat=" + latitude + "&lng=" + longitude);
    }

    public static String getProfileById(String id) throws Exception {
        return get(HttpService.endPoint + HttpService.userPath + "/getProfileById?" + "id=" + id);
    }

    public static String getNotificationById(String id) throws Exception {
        return get(endPoint + userPath + "/getNotification?" + "id=" + id);
    }

    public static String searchProfiles(CharSequence charSequence) throws Exception {
        return get(endPoint + userPath + "/search?" + "str=" + charSequence);
    }

    public static String updateTask(int taskCompleted, int taskUndertaken) throws Exception {
        return get(endPoint + systemPath + "/updateTask?" + "taskCompleted=" + taskCompleted + "&taskUndertaken=" + taskUndertaken);
    }

    public static String likeArtwork(int artworkId) throws Exception {
        return get(endPoint + userPath + "/likeArtwork?" + "userId=" + userID + "&artworkId=" + artworkId);
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
