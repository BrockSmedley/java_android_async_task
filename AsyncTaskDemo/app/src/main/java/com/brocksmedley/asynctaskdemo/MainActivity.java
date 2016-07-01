package com.brocksmedley.asynctaskdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button button;
    int counter;
    int duration = 3000; //in millis
    int interval = 1000; //1 second intervals
    TimerAsyncTask task = null;
    Boolean isTaskRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.button1);
        textView = (TextView)findViewById(R.id.textView1);
    }

    private class TimerAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String[] params) {
            while (counter > 0) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(counter);
                counter -= interval;
            }
            return "success";
        }

        @Override
        protected void onPreExecute() {
            counter = duration;
            textView.setText(toString().valueOf(counter/interval));
        }

        @Override
        protected void onPostExecute(String s) {
            textView.setText(s);
            isTaskRunning = false;
        }

        @Override
        protected void onProgressUpdate(Integer... progress){
            textView.setText(toString().valueOf(counter/interval));
        }
    }

    public void OnClick(View view){
        if (task == null || !isTaskRunning) {
            isTaskRunning = true;
            task = new TimerAsyncTask();
            task.execute(new String[]{});
        }
    }
}
