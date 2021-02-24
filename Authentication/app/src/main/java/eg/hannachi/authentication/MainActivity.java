package eg.hannachi.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void auth(View view) {
        Thread t1 = new Thread(new Runnable() { //We have to create a thread because we can't run this on the main thread, otherwise the ui is not update
            @SuppressLint("SetTextI18n")
            String res;
            public void run() {
                URL url = null;
                try {
                    TextView login = findViewById(R.id.login);
                    TextView password = findViewById(R.id.password);
                    url = new URL("https://httpbin.org/basic-auth/bob/sympa"); // The URL where we try the log
                    HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();   // Create connecion
                    String log = login.getText().toString() + ":" + password.getText().toString();  // Convert the login in strings
                    String basicAuth = "Basic " + Base64.encodeToString(log.getBytes(), Base64.NO_WRAP);    // Convert the log in base64 befor sending
                    urlConnection.setRequestProperty ("Authorization", basicAuth); // Send the request
                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream()); // We get the content of the request
                        JSONObject s = readStream(in);  // We read the data and create a JSONObject
                        res = s.getString("authenticated") + ":" +  s.getString("user"); // We get the values that we want
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (
                        MalformedURLException e) {
                    e.printStackTrace();
                } catch (
                        IOException ignored) {
                }
                runOnUiThread(new Runnable() { // We have to run this in the main thread because it's edit the Ui
                    @Override
                    public void run() {
                        TextView result = findViewById(R.id.result);
                        result.setText(res);    // Display the result on the app
                    }
                });
            }
        });
        t1.start();
    }

    private JSONObject readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return new JSONObject(bo.toString());
        } catch (IOException | JSONException e) {
            return new JSONObject();
        }
    }
}