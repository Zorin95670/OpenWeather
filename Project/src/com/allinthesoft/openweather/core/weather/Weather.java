package com.allinthesoft.openweather.core.weather;

public class Weather {

	private int id;
	
	private String temp, tempMin, tempMax, pressure, humidity;
	
	private String windSpeed, windDeg;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		//TODO convert temp to string
		this.temp = temp +"";
	}

	public String getTempMin() {
		return tempMin;
	}

	public void setTempMin(double tempMin) {
		//TODO convert tempMin to string
		this.tempMin = tempMin + "";
	}

	public String getTempMax() {
		return tempMax;
	}

	public void setTempMax(double tempMax) {
		//TODO convert tempMax to string
		this.tempMax = tempMax + "";
	}

	public String getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		//TODO convert pressure to string
		this.pressure = pressure + "";
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		//TODO convert humidity to string
		this.humidity = humidity + "";
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
}
