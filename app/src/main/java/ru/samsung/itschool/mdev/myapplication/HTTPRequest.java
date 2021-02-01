package ru.samsung.itschool.mdev.myapplication;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class HTTPRequest implements Runnable {

    private URL url;
    private Handler handler;

    HTTPRequest(Handler h) {
        try {
            this.url = new URL("http://api.openweathermap.org/data/2.5/weather?q=Dubai&appid=78f95c79f6feac6bf70f79bc09d47bab");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.handler = h;
    }

    @Override
    public void run() {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            Scanner scanner = new Scanner(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            while(scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();
            connection.disconnect();
            Message m = Message.obtain();
            m.obj = response.toString();
            handler.sendMessage(m);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
