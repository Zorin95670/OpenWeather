package com.allinthesoft.openweather.service.openweather.task;

import java.util.List;

import org.json.JSONException;

import android.os.AsyncTask;

import com.allinthesoft.openweather.core.weather.CityData;
import com.allinthesoft.openweather.module.activity.home.HomeActivity;
import com.allinthesoft.openweather.service.openweather.http.HttpClient;
import com.allinthesoft.openweather.service.openweather.parserjson.JSONConverter;
import com.allinthesoft.openweather.tools.list.core.array.ArrayExtendedList;

public class JSONWeatherTask extends AsyncTask<String, Void, CityData> {

	private List<CityData> cities;
	private HomeActivity activity;

	public JSONWeatherTask(List<CityData> cities, HomeActivity activity) {
		this.cities = cities;
		if (this.cities == null) {
			this.cities = new ArrayExtendedList<CityData>();
		}
		this.activity = activity;
	}

	@Override
	protected CityData doInBackground(String... params) {
		if (cities == null) {
			String json = ((new HttpClient()).getWeatherData(params[0]));
			JSONConverter converter = new JSONConverter(json);
			CityData data = null;
			try {
				data = converter.getWeatherData();
				if (data != null) {
					json = ((new HttpClient()).getDailyWeather(params[0]));
					converter = new JSONConverter(json);
					data.setDailyWeather(converter.getDailyWeather());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			activity.refreshEnd(false, null);
			return data;
		}
		int len = cities.size();
		for (int i = 0; i < len; i++) {
			String json = ((new HttpClient()).getWeatherData(cities.get(i)
					.getId()));
			JSONConverter converter = new JSONConverter(json);
			CityData data = null;
			try {
				data = converter.getWeatherData();
				if (data != null) {
					json = ((new HttpClient()).getDailyWeather(cities.get(i)
							.getId()));
					converter = new JSONConverter(json);
					data.setDailyWeather(converter.getDailyWeather());
					cities.set(i, data);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (activity != null) {
			if (len > 0) {
				activity.refreshEnd(true, cities);
			} else {
				activity.refreshEnd(false, null);
			}
		}
		return null;

	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(CityData data) {
		super.onPostExecute(data);
		if (activity != null)
			activity.notidyAdapter();
	}
}