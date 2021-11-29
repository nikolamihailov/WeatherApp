package uni.fmi.bechelors.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ThreeDayActivity extends DBActivity{

    public Button threeDayForecastButton;
    public EditText threeDayET;
    public ListView threeDayLV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_day);

        threeDayForecastButton = findViewById(R.id.threeDayFButton);
        threeDayET = findViewById(R.id.threeDayET);
        final WeatherDataService weatherDataService = new WeatherDataService(ThreeDayActivity.this);
        threeDayLV = findViewById(R.id.threeDayLV);

        threeDayForecastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weatherDataService.getCityForecastByNameThree(threeDayET.getText().toString(), new WeatherDataService.ForecastByCityNameResponse() {
                    @Override
                    public void onError(String message) {

                        Toast.makeText(ThreeDayActivity.this, "Something wrong!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) throws Exception {

                        ArrayAdapter arrayAdapter = new ArrayAdapter(ThreeDayActivity.this, android.R.layout.simple_list_item_1, weatherReportModels);

                        threeDayLV.setAdapter(arrayAdapter);
                        initDB();
                        ExecSQL(
                                "INSERT INTO ForecastQueryHistory(Place, Type) " +
                                        "Values(?,?) ",
                                new Object[]{
                                        threeDayET.getText().toString(),
                                        "Three days"
                                },
                                () -> Toast.makeText(getApplicationContext(), "Record Inserted", Toast.LENGTH_SHORT).show()

                        );;
                    }
                });
            }
        });
    }
}