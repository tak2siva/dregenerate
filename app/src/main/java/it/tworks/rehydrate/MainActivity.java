package it.tworks.rehydrate;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;


public class MainActivity extends Activity implements AfterAsyncTask{
    private TextView value;
    private int previousValue;
    private int currentValue;
    private RequestTask requestTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestTask = new RequestTask();
        requestTask.delegate = this;
        getCurrentValue();
        value = (TextView) findViewById(R.id.value);
        previousValue = Integer.parseInt(String.valueOf(value.getText()));
    }


    private void getCurrentValue() {
        requestTask.execute("http://10.16.23.195:3000/posts.json");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onComplete(String result) {
        currentValue = Integer.parseInt(result);
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
