package com.allinthesoft.openweather.service.openweather.task;

import org.json.JSONException;

import android.os.AsyncTask;
import android.util.Log;

import com.allinthesoft.openweather.service.openweather.http.HttpClient;
import com.allinthesoft.openweather.service.openweather.parserjson.JSONConverter;

public class JSONCityTask extends AsyncTask<String, Void, String> {

	@Override
	protected String doInBackground(String... params) {
		String json = ((new HttpClient()).getCityName(params[0]));
		JSONConverter converter = new JSONConverter(json);
		String data = null;
		Log.i("TEST", json);
		try {
			data = converter.getCityName();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;

	}

	@Override
	protected void onPostExecute(String data) {
		super.onPostExecute(data);
	}
}