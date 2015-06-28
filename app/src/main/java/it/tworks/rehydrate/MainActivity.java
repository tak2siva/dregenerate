package it.tworks.rehydrate;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class MainActivity extends Activity implements AfterAsyncTask {
    private TextView value;
    private int previousValue;
    private int currentValue;

    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPolling();
        value = (TextView) findViewById(R.id.value);
        previousValue = Integer.parseInt(String.valueOf(value.getText()));
    }

    private void initPolling() {
        int delay = 0; // intial delay for 0 sec.
        int period = 3000; // repeat every 3 sec.

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Fetching weight form bottle ");
                getCurrentValue();
            }
        }, delay, period);
    }


    private void getCurrentValue() {
        RequestTask requestTask;
        requestTask = new RequestTask();
        requestTask.delegate = this;
        requestTask.execute("http://192.168.0.102:4567/posts.json");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onComplete(String result) {
        currentValue = Integer.parseInt(result);
        System.out.println("Obtained weight is: " + currentValue);
        if (previousValue == currentValue) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Drink Water!!!")
                    .setMessage("You are Dehydrating....")
                    .create()
                    .show();
        } else {
            this.value.setText(String.valueOf(currentValue));
        }
    }
}
