package com.allinthesoft.openweather.service.openweather;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

	private static String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
	private static String CITY_URL = "http://maps.googleapis.com/maps/api/geocode/json?latlng=";

	public String getWeatherData(String location) {
		return getData(WEATHER_URL + location);
	}
	
	public String getCityName(String location){
		return getData(CITY_URL + location);
	}
	
	public String getData(String url){
		HttpURLConnection con = null;
		InputStream is = null;

		// try {
		try {
			con = (HttpURLConnection) (new URL(url))
					.openConnection();
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();

			// Let's read the response
			StringBuffer buffer = new StringBuffer();
			is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = br.readLine()) != null)
				buffer.append(line + "\r\n");

			is.close();
			con.disconnect();
			return buffer.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Throwable t) {
			}
			try {
				con.disconnect();
			} catch (Throwable t) {
			}
		}

		return null;
	}
}