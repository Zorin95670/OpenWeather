package com.allinthesoft.openweather.service.openweather.task;

import org.json.JSONException;

import android.os.AsyncTask;

import com.allinthesoft.openweather.core.weather.Data;
import com.allinthesoft.openweather.service.openweather.HttpClient;
import com.allinthesoft.openweather.service.openweather.JSONConverter;

public class JSONWeatherTask extends AsyncTask<String, Void, Data> {

	@Override
	protected Data doInBackground(String... params) {
		String json = ((new HttpClient()).getWeatherData(params[0]));
		JSONConverter converter = new JSONConverter(json);
		Data data = null;
		try {
			data = converter.getWeatherData();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;

	}

	@Override
	protected void onPostExecute(Data data) {
		super.onPostExecute(data);
	}
}