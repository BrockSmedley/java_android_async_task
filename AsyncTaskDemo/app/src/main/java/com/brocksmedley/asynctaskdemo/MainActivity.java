package com.brocksmedley.asynctaskdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //view instances
    TextView textView;
    Button button;

    //logic vars
    int counter;
    int duration = 3000; //in millis
    int interval = 1000; //1 second intervals

    //task vars
    TimerAsyncTask task = null;
    Boolean isTaskRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.button1);
        textView = (TextView)findViewById(R.id.textView1);
    }

    /*
    AsyncTask that counts down from 3
     */
    private class TimerAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            while (counter > 0) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress();
                counter -= interval;
            }
            return "success";
        }

        @Override
        protected void onPreExecute() {
            //set timer
            counter = duration;
            //update UI
            textView.setText(toString().valueOf(counter/interval));
        }

        @Override
        protected void onPostExecute(String s) {
            //display success message
            textView.setText(s);
            //stop timer... in a way
            isTaskRunning = false;
        }

        @Override
        protected void onProgressUpdate(Void... voids){ //not sure why I need to add "voids" but OK
            textView.setText(toString().valueOf(counter/interval));
        }
    }

    public void OnClick(View view){
        //run timer if not already running
        if (task == null || !isTaskRunning) {
            isTaskRunning = true;
            task = new TimerAsyncTask();
            task.execute();
        }
    }
}
