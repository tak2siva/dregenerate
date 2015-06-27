package it.tworks.rehydrate;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.net.URI;

public class RequestTask extends AsyncTask<String, String, String> {

    public AfterAsyncTask delegate = null;

    @Override
    protected String doInBackground(String... uri) {
        HttpResponse response = null;
        HttpClient httpClient = new DefaultHttpClient();
        String responseString = null;
        int value = 0;
        try {
            response = httpClient.execute(new HttpGet(new URI(uri[0])));
            HttpEntity entity = response.getEntity();
            responseString = EntityUtils.toString(entity, "UTF-8");
            JSONObject jsonObject = new JSONObject(responseString);
            value = jsonObject.getInt("value");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(value);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        delegate.onComplete(result);
    }
}