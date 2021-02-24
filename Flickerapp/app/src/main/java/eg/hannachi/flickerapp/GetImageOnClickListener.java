package eg.hannachi.flickerapp;

import android.os.AsyncTask;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class GetImageOnClickListener implements View.OnClickListener {

    private AppCompatActivity app;

    public GetImageOnClickListener(AppCompatActivity MainActivity){
        this.app = MainActivity; // We get the app to edit the content later
    }
    @Override
    public void onClick(View v) {
        AsyncFlickrJSONData task = new AsyncFlickrJSONData(this.app); // We create the task before the execution
        task.execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json"); // Execute the task and send the URI where we can find the images
    }

}
