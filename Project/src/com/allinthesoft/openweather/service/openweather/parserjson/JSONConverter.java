package com.allinthesoft.openweather.service.openweather.parserjson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.allinthesoft.openweather.core.weather.CityData;
import com.allinthesoft.openweather.core.weather.WeatherData;

public class JSONConverter {

	private String json;

	public JSONConverter(String json) {
		this.json = json;
	}

	public CityData getWeatherData() throws JSONException {
		if (json != null) {
			JSONObject root = new JSONObject(json);

			CityData data = new CityData();
			WeatherData weather = new WeatherData();
			data.setName(root.getString("name"));
			weather.setId(root.getJSONArray("weather").getJSONObject(0)
					.getInt("id"));

			JSONObject sys = root.getJSONObject("sys");
			data.setCountry(sys.getString("country"));
			data.setSunrise(sys.getDouble("sunrise") + "");
			data.setSunset(sys.getDouble("sunset") + "");
			data.setId(root.getString("name") + "," + sys.getString("country"));

			JSONObject main = root.getJSONObject("main");
			weather.setTemp(main.getDouble("temp"));
			weather.setTempMin(main.getDouble("temp_min"));
			weather.setTempMax(main.getDouble("temp_max"));
			weather.setPressure(main.getDouble("pressure"));
			weather.setHumidity(main.getDouble("humidity"));

			JSONObject wind = root.getJSONObject("wind");
			weather.setWindSpeed(wind.getDouble("speed"));
			weather.setWindDeg(wind.getDouble("deg"));

			data.setMainWeather(weather);
			return data;
		}
		return null;
	}

	public String getCityName() throws JSONException {
		if(json != null){
			JSONObject root = new JSONObject(json);
			JSONArray a = root.getJSONArray("results");
			if(a.length() > 0){
			JSONObject object = root.getJSONArray("results").getJSONObject(0);
			JSONArray array = object.getJSONArray("address_components");
			String city = array.getJSONObject(2).getString("long_name");
			String country = array.getJSONObject(5).getString("short_name");
			return city + "," + country;
			}
		}
		return null;
	}

	public WeatherData[] getDailyWeather() throws JSONException {
		WeatherData[] list = new WeatherData[7];
		if(json == null){
			for(int i = 0 ; i < 7 ; i++)
				list[i] = new WeatherData();
			return list;
		}
		JSONObject root = new JSONObject(json);
		JSONArray listWeather = root.getJSONArray("list");
		JSONObject weather;
		for(int i = 0 ; i < list.length ; i++){
			weather = listWeather.getJSONObject(i);
			list[i] = new WeatherData();
			list[i].setDate(weather.getLong("dt"));
			list[i].setHumidity(weather.getDouble("humidity"));
			list[i].setId(weather.getJSONArray("weather").getJSONObject(0).getInt("id"));
			list[i].setPressure(weather.getDouble("pressure"));
			list[i].setTemp(weather.getJSONObject("temp").getDouble("eve"));
			list[i].setTempMax(weather.getJSONObject("temp").getDouble("night"));
			list[i].setTempMin(weather.getJSONObject("temp").getDouble("morn"));
			list[i].setWindDeg(weather.getDouble("deg"));
			list[i].setWindSpeed(weather.getDouble("speed"));
		}
		return list;
	}

}
