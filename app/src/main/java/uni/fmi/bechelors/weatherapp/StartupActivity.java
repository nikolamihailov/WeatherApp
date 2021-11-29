package uni.fmi.bechelors.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartupActivity extends AppCompatActivity {

    public Button oneDayButton, threeDayButton, sixDaysButton, historyButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        oneDayButton = findViewById(R.id.OneDayButton);
        threeDayButton = findViewById(R.id.ThreeDayButton);
        sixDaysButton = findViewById(R.id.SixDaysButton);
        historyButton = findViewById(R.id.historyButton);

        oneDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartupActivity.this, OneDayActivity.class);
                startActivity(intent);

            }
        });

        sixDaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartupActivity.this, SixDaysActivity.class);
                startActivity(intent);

            }
        });

        threeDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartupActivity.this, ThreeDayActivity.class);
                startActivity(intent);

            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartupActivity.this, HistoryActivity.class);
                startActivity(intent);

            }
        });





    }


}