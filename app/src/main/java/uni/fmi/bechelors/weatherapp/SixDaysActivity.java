package uni.fmi.bechelors.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class SixDaysActivity extends DBActivity{

    public Button sixDayForecastButton;
    public EditText sixDayET;
    public ListView sixDayLV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six_days);

        sixDayForecastButton = findViewById(R.id.sixDaysFButton);
        sixDayET = findViewById(R.id.sixDayET);
        final WeatherDataService weatherDataService = new WeatherDataService(SixDaysActivity.this);
        sixDayLV = findViewById(R.id.sixDayLV);


        sixDayForecastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weatherDataService.getCityForecastByName(sixDayET.getText().toString(), new WeatherDataService.ForecastByCityNameResponse() {
                    @Override
                    public void onError(String message) {

                        Toast.makeText(SixDaysActivity.this, "Something wrong!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) throws Exception {

                        ArrayAdapter arrayAdapter = new ArrayAdapter(SixDaysActivity.this, android.R.layout.simple_list_item_1, weatherReportModels);

                        sixDayLV.setAdapter(arrayAdapter);
                        initDB();
                        ExecSQL(
                                "INSERT INTO ForecastQueryHistory(Place, Type) " +
                                        "Values(?,?) ",
                                new Object[]{
                                        sixDayET.getText().toString(),
                                        "Six days"
                                },
                                () -> Toast.makeText(getApplicationContext(), "Record Inserted", Toast.LENGTH_SHORT).show()

                        );;
                    }
                });
            }
        });
    }
}