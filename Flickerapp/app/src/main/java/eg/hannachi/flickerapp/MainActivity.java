package eg.hannachi.flickerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // We get all the elements of the layout
        Button button = findViewById(R.id.get_image);
        Button list = findViewById(R.id.redirect);
        Intent intent = new Intent(MainActivity.this, ListActivity.class); // Create an intent of the main activity with the list
        button.setOnClickListener(new GetImageOnClickListener(MainActivity.this)); // We call the GetImageOnClickListener when we click on the button
        list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent); // We start the new activity when we click on this button
            }
        });
    }
}