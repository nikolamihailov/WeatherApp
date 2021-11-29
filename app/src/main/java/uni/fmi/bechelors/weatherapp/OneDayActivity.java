package uni.fmi.bechelors.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class OneDayActivity extends DBActivity {

    public Button oneDayForecastButton;
    public EditText oneDayET;
    public ListView oneDayLV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_day);

        oneDayForecastButton = findViewById(R.id.oneDayFButton);
        oneDayET = findViewById(R.id.oneDayET);
        final WeatherDataService weatherDataService = new WeatherDataService(OneDayActivity.this);
        oneDayLV = findViewById(R.id.oneDayLV);


        oneDayForecastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weatherDataService.getCityForecastByNameOne(oneDayET.getText().toString(), new WeatherDataService.ForecastByCityNameResponse() {
                    @Override
                    public void onError(String message) {

                        Toast.makeText(OneDayActivity.this, "Something wrong!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) throws Exception {

                        ArrayAdapter arrayAdapter = new ArrayAdapter(OneDayActivity.this, android.R.layout.simple_list_item_1, weatherReportModels);


                        oneDayLV.setAdapter(arrayAdapter);
                        initDB();
                        ExecSQL(
                                "INSERT INTO ForecastQueryHistory(Place, Type) " +
                                        "Values(?,?) ",
                                new Object[]{
                                        oneDayET.getText().toString(),
                                        "One day"
                                },
                                () -> Toast.makeText(getApplicationContext(), "Record Inserted", Toast.LENGTH_SHORT).show()

                        );;


                    }
                });
            }
        });
    }
}