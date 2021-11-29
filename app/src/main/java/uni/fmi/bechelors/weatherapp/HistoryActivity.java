package uni.fmi.bechelors.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HistoryActivity extends DBActivity {

    protected ListView historyList;
    //private LinearLayout linearLayout;
    //private TextView history;


    protected  void FillHistoryListView() throws Exception{
        final ArrayList<String> listResults= new ArrayList<>();
        SelectSQL(
                "SELECT * FROM ForecastQueryHistory ORDER BY ID DESC",
                null,
                (ID, Place, Type)->{
                    listResults.add(ID + ". Searched location: " + Place + "\n" + "\t" + " Type of forecast: " + Type + "\n" );
                }
        );
        historyList.clearChoices();

        ArrayAdapter arrayAdapter = new ArrayAdapter(HistoryActivity.this, android.R.layout.simple_list_item_1, listResults);
        historyList.setAdapter(arrayAdapter);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);



       // history = findViewById(R.id.historyTextView);
       // linearLayout = findViewById(R.id.LinearLayout);


        historyList = findViewById(R.id.historyLV);

            try {
                initDB();
                FillHistoryListView();
            } catch (Exception exception) {
                exception.printStackTrace();
            }


        }
    }
