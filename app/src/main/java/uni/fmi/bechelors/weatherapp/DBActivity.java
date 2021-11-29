package uni.fmi.bechelors.weatherapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBActivity extends AppCompatActivity {
    protected interface OnQuerySuccess{
        public void OnSuccess();
    }
    protected interface OnSelectSuccess{
        public void OnElementSelected(
                String ID, String Place, String Type
        );
    }

    protected  boolean matchString(String string_, String regexp){
        final String regex = regexp;
        final String string = string_;

        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            return true;
        }
        return false;
    }
/*
    protected void validation(EditText editPlace) throws Exception {
        if(!matchString(editPlace.getText().toString(),"[a-zA-Z]+")){
            throw new Exception("use only latin letters");
        }
    }
    */




    protected void SelectSQL(String SelectQ, String[] args, OnSelectSuccess success)
            throws Exception
    {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath()+"/ForecastQueryHistory.db", null);
        Cursor cursor = db.rawQuery(SelectQ, args);
        while (cursor.moveToNext()){
            String ID=cursor.getString(cursor.getColumnIndex("ID"));
            String Place=cursor.getString(cursor.getColumnIndex("Place"));
            String Type=cursor.getString(cursor.getColumnIndex("Type"));

            success.OnElementSelected(ID, Place, Type);
        }
        db.close();
    }

    protected void ExecSQL(String SQL, Object[] args, OnQuerySuccess success)
            throws Exception
    {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath()+"/ForecastQueryHistory.db", null);
        if(args!=null)
            db.execSQL(SQL, args);
        else
            db.execSQL(SQL);

        db.close();
        success.OnSuccess();
    }


    protected void initDB() throws Exception{
        ExecSQL(
                "CREATE TABLE if not exists ForecastQueryHistory(" +
                        "ID integer PRIMARY KEY AUTOINCREMENT, " +
                        "Place text not null, " +
                        "Type text not null " +

                        ")",
                null,
                ()-> Toast.makeText(getApplicationContext(),"DB init Successfull", Toast.LENGTH_SHORT).show()
        );
    }
}
