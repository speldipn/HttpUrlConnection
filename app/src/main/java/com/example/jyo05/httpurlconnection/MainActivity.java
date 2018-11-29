package com.example.jyo05.httpurlconnection;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int PROGRESS_START = 1;
    private static final int PROGRESS_STOP = 2;

    EditText editUrl;
    TextView textSource;
    ProgressBar progressBar;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editUrl = findViewById(R.id.editText);
        textSource = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar);
        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case PROGRESS_START:
                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case PROGRESS_STOP:
                        progressBar.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        };
    }

    public void getUrl(View v) {
        String url = editUrl.getText().toString();
        if(!url.startsWith("http://")) {
            url = "http://" + url;
        }

        new AsyncTask<String, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                handler.sendEmptyMessage(PROGRESS_START);
            }

            @Override
            protected String doInBackground(String... strs) {
                Remote remote = new Remote();
                String result = "";
                for (int i = 0; i < strs.length; ++i) {
                    result += strs[i] + "\r\n";
                    result += remote.getHttp(strs[i]);
                }
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                handler.sendEmptyMessage(PROGRESS_STOP);
                textSource.setText(s);
            }
        }.execute(url);


    }
}
