package com.allinthesoft.openweather.core.weather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherData {

	private int id;
	
	private long date;
	
	private double temp, tempMin, tempMax, pressure, humidity;
	
	private String windSpeed, windDeg;

	public WeatherData() {
		id = -1;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public double getTempMin() {
		return tempMin;
	}

	public void setTempMin(double tempMin) {
		//TODO convert tempMin to string
		this.tempMin = tempMin;
	}

	public double getTempMax() {
		return tempMax;
	}

	public void setTempMax(double tempMax) {
		//TODO convert tempMax to string
		this.tempMax = tempMax;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		//TODO convert pressure to string
		this.pressure = pressure;
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		//TODO convert humidity to string
		this.humidity = humidity;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(double windSpeed) {
		//TODO convert windSpeed to string
		this.windSpeed = windSpeed + "";
	}

	public String getWindDeg() {
		return windDeg;
	}

	public void setWindDeg(double windDeg) {
		//TODO convert windDeg to string
		this.windDeg = windDeg + "";
	}

	public CharSequence getDate() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE",Locale.getDefault());
		String txt = sdf.format(new Date(date));
		return txt.substring(0,1).toUpperCase(Locale.getDefault()) + txt.substring(1);
	}

	public void setDate(long date) {
		this.date = date*1000;
	}
}
