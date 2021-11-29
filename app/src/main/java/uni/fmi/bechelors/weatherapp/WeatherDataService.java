package uni.fmi.bechelors.weatherapp;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataService {

    public static final String QUERY_FOR_CITY_ID = "https://www.metaweather.com/api/location/search/?query=";
    public static final String QUERY_FOR_WEATHER_BY_CITY_ID = "https://www.metaweather.com/api/location/";

    Context context;
    String cityID;

    public WeatherDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(String cityID);
    }

    public void getCityID(String cityName, VolleyResponseListener volleyResponseListener) {

        String url = QUERY_FOR_CITY_ID + cityName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                cityID = "";
                try {
                    JSONObject cityInfo = response.getJSONObject(0);
                    cityID = cityInfo.getString("woeid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                volleyResponseListener.onResponse(cityID);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                volleyResponseListener.onError("Something wrong!");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);


    }

    public interface ForecastByCityIDResponse {
        void onError(String message);

        void onResponse(List<WeatherReportModel> weatherReportModels) throws Exception;
    }

    public void getForecastByCityID(String cityID, ForecastByCityIDResponse forecastByCityIDResponse){

        List<WeatherReportModel> weatherReportModels = new ArrayList<>();
        String url = QUERY_FOR_WEATHER_BY_CITY_ID + cityID;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {




                try {
                    JSONArray consolidated_weather_list = response.getJSONArray("consolidated_weather");




                    for(int i = 0; i < consolidated_weather_list.length();i++) {

                        WeatherReportModel one_day_weather = new WeatherReportModel();
                        JSONObject first_day_api = consolidated_weather_list.getJSONObject(i);
                        one_day_weather.setId(first_day_api.getInt("id"));
                        one_day_weather.setWeather_state_name(first_day_api.getString("weather_state_name"));
                        one_day_weather.setWeather_state_abbr(first_day_api.getString("weather_state_abbr"));
                        one_day_weather.setWind_direction_compass(first_day_api.getString("wind_direction_compass"));
                        one_day_weather.setCreated(first_day_api.getString("created"));
                        one_day_weather.setApplicable_date(first_day_api.getString("applicable_date"));
                        one_day_weather.setMin_temp(first_day_api.getLong("min_temp"));
                        one_day_weather.setMax_temp(first_day_api.getLong("max_temp"));
                        one_day_weather.setThe_temp(first_day_api.getLong("the_temp"));
                        one_day_weather.setWind_speed(first_day_api.getLong("wind_speed"));
                        one_day_weather.setWind_direction(first_day_api.getLong("wind_direction"));
                        one_day_weather.setAir_pressure(first_day_api.getInt("air_pressure"));
                        one_day_weather.setHumidity(first_day_api.getInt("humidity"));
                        one_day_weather.setVisibility(first_day_api.getLong("visibility"));
                        one_day_weather.setPredictability(first_day_api.getInt("predictability"));

                        if(WeatherReportModel.weather_state_name == "Heavy Cloud"){

                            
                        }

                        weatherReportModels.add(one_day_weather);
                    }

                    forecastByCityIDResponse.onResponse(weatherReportModels);




                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                forecastByCityIDResponse.onError("Something wrong!");
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public interface ForecastByCityNameResponse {
        void onError(String message);

        void onResponse(List<WeatherReportModel> weatherReportModels) throws Exception;
    }

    public void getCityForecastByName(String cityName, ForecastByCityNameResponse forecastByCityNameResponse){

        getCityID(cityName, new VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(String cityID) {

                getForecastByCityID(cityID, new ForecastByCityIDResponse() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) throws Exception {


                        forecastByCityNameResponse.onResponse(weatherReportModels);
                    }
                });
            }
        });
    }

    public void getForecastByCityIDOne(String cityID, ForecastByCityIDResponse forecastByCityIDResponse){

        List<WeatherReportModel> weatherReportModels = new ArrayList<>();
        String url = QUERY_FOR_WEATHER_BY_CITY_ID + cityID;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {




                try {
                    JSONArray consolidated_weather_list = response.getJSONArray("consolidated_weather");




                    for(int i = 0; i < 1;i++) {

                        WeatherReportModel one_day_weather = new WeatherReportModel();
                        JSONObject first_day_api = consolidated_weather_list.getJSONObject(i);
                        one_day_weather.setId(first_day_api.getInt("id"));
                        one_day_weather.setWeather_state_name(first_day_api.getString("weather_state_name"));
                        one_day_weather.setWeather_state_abbr(first_day_api.getString("weather_state_abbr"));
                        one_day_weather.setWind_direction_compass(first_day_api.getString("wind_direction_compass"));
                        one_day_weather.setCreated(first_day_api.getString("created"));
                        one_day_weather.setApplicable_date(first_day_api.getString("applicable_date"));
                        one_day_weather.setMin_temp(first_day_api.getLong("min_temp"));
                        one_day_weather.setMax_temp(first_day_api.getLong("max_temp"));
                        one_day_weather.setThe_temp(first_day_api.getLong("the_temp"));
                        one_day_weather.setWind_speed(first_day_api.getLong("wind_speed"));
                        one_day_weather.setWind_direction(first_day_api.getLong("wind_direction"));
                        one_day_weather.setAir_pressure(first_day_api.getInt("air_pressure"));
                        one_day_weather.setHumidity(first_day_api.getInt("humidity"));
                        one_day_weather.setVisibility(first_day_api.getLong("visibility"));
                        one_day_weather.setPredictability(first_day_api.getInt("predictability"));

                        if(WeatherReportModel.weather_state_name == "Heavy Cloud"){


                        }

                        weatherReportModels.add(one_day_weather);
                    }

                    forecastByCityIDResponse.onResponse(weatherReportModels);




                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                forecastByCityIDResponse.onError("Something wrong!");
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }




    public void getCityForecastByNameOne(String cityName, ForecastByCityNameResponse forecastByCityNameResponse){

        getCityID(cityName, new VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(String cityID) {

                getForecastByCityIDOne(cityID, new ForecastByCityIDResponse() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) throws Exception {


                        forecastByCityNameResponse.onResponse(weatherReportModels);
                    }
                });
            }
        });
    }

    public void getForecastByCityIDThree(String cityID, ForecastByCityIDResponse forecastByCityIDResponse){

        List<WeatherReportModel> weatherReportModels = new ArrayList<>();
        String url = QUERY_FOR_WEATHER_BY_CITY_ID + cityID;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {




                try {
                    JSONArray consolidated_weather_list = response.getJSONArray("consolidated_weather");




                    for(int i = 0; i < 3;i++) {

                        WeatherReportModel one_day_weather = new WeatherReportModel();
                        JSONObject first_day_api = consolidated_weather_list.getJSONObject(i);
                        one_day_weather.setId(first_day_api.getInt("id"));
                        one_day_weather.setWeather_state_name(first_day_api.getString("weather_state_name"));
                        one_day_weather.setWeather_state_abbr(first_day_api.getString("weather_state_abbr"));
                        one_day_weather.setWind_direction_compass(first_day_api.getString("wind_direction_compass"));
                        one_day_weather.setCreated(first_day_api.getString("created"));
                        one_day_weather.setApplicable_date(first_day_api.getString("applicable_date"));
                        one_day_weather.setMin_temp(first_day_api.getLong("min_temp"));
                        one_day_weather.setMax_temp(first_day_api.getLong("max_temp"));
                        one_day_weather.setThe_temp(first_day_api.getLong("the_temp"));
                        one_day_weather.setWind_speed(first_day_api.getLong("wind_speed"));
                        one_day_weather.setWind_direction(first_day_api.getLong("wind_direction"));
                        one_day_weather.setAir_pressure(first_day_api.getInt("air_pressure"));
                        one_day_weather.setHumidity(first_day_api.getInt("humidity"));
                        one_day_weather.setVisibility(first_day_api.getLong("visibility"));
                        one_day_weather.setPredictability(first_day_api.getInt("predictability"));

                        if(WeatherReportModel.weather_state_name == "Heavy Cloud"){


                        }

                        weatherReportModels.add(one_day_weather);
                    }

                    forecastByCityIDResponse.onResponse(weatherReportModels);




                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                forecastByCityIDResponse.onError("Something wrong!");
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }




    public void getCityForecastByNameThree(String cityName, ForecastByCityNameResponse forecastByCityNameResponse){

        getCityID(cityName, new VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(String cityID) {

                getForecastByCityIDThree(cityID, new ForecastByCityIDResponse() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) throws Exception {


                        forecastByCityNameResponse.onResponse(weatherReportModels);
                    }
                });
            }
        });
    }
}
