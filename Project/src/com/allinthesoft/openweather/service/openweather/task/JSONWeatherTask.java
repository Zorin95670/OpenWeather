package com.allinthesoft.openweather.service.openweather.task;

import org.json.JSONException;

import android.os.AsyncTask;

import com.allinthesoft.openweather.core.weather.CityData;
import com.allinthesoft.openweather.service.openweather.http.HttpClient;
import com.allinthesoft.openweather.service.openweather.parserjson.JSONConverter;

public class JSONWeatherTask extends AsyncTask<String, Void, CityData> {

	@Override
	protected CityData doInBackground(String... params) {
		String json = ((new HttpClient()).getWeatherData(params[0]));
		JSONConverter converter = new JSONConverter(json);
		CityData data = null;
		try {
			data = converter.getWeatherData();
			json = ((new HttpClient()).getDailyWeather(params[0]));
			converter = new JSONConverter(json);
			data.setDailyWeather(converter.getDailyWeather());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;

	}

	@Override
	protected void onPostExecute(CityData data) {
		super.onPostExecute(data);
	}
}