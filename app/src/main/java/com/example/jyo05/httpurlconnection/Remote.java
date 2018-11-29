package com.example.jyo05.httpurlconnection;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author: Oh, Joon young (speldipn)
 * @Since: 2018-11-29
 */
public class Remote {
    public static final String LOG = "speldipn";

    public String getHttp(String urlString) {
        String result = "";
        int resCode = 0;
        try {
            URL url = new URL(urlString);
            // url로 네트워크 연결
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 연결에 대한 옵션 설정
            connection.setRequestMethod("GET");
            // 서버로부터 응답코드 회신
            resCode = connection.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                // 서버연결로부터 스트림을 열고 버퍼 처리를 해서 값을 꺼낼 준비를 한다.
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String temp = null;
                while ((temp = reader.readLine()) != null) {
                    result += temp;
                }
            } else {
                Log.d(LOG, "Http Error: " + resCode);
            }
        } catch (Exception e) {
            Log.d(LOG, e.getMessage());
        }

        return result;
    }
}
