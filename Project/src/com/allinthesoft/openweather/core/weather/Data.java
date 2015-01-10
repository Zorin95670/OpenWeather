package com.allinthesoft.openweather.core.weather;

import com.allinthesoft.openweather.R;


public class Data {
	private Location location;
	private boolean myLocation;
	private String name;
	
	private String sunrise, sunset;
	
	private Weather mainWeather;

	private boolean hooter, cooler;
	
	public Data() {
		setHooter(false);
		setCooler(false);
		setMyLocation(false);
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSunrise() {
		return sunrise;
	}

	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	public String getSunset() {
		return sunset;
	}

	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	public Weather getMainWeather() {
		return mainWeather;
	}

	public void setMainWeather(Weather mainWeather) {
		this.mainWeather = mainWeather;
	}

	public String getWind() {
		// TODO Auto-generated method stub
		return getMainWeather().getWindSpeed() + "Km/H\nDeg : " + getMainWeather().getWindDeg();
	}

	public String getTemperature() {
		// TODO Auto-generated method stub
		return getMainWeather().getTemp() + "°C\nMin : " + getMainWeather().getTempMin() + "°C\nMax : " + getMainWeather().getTempMax() + "°C";
	}
	public int getPicture(){
		int id = getMainWeather().getId();
		if(id >= 200 && id < 300){
			return R.drawable.storm;
		} else if(id >= 300 && id < 400){
			return R.drawable.bigrain;
		} else if(id >= 500 && id < 600){
			return R.drawable.rain;
		} else if(id >= 600 && id < 700){
			return R.drawable.snow;
		} else if(id >= 700 && id < 800){
			return R.drawable.fog;
		} else if(id == 800){
			return R.drawable.day;// TODO or night
		} else if(id == 801){
			return R.drawable.cloudday;// TODO or night
		} else if(id > 801 && id < 900 ){
			return R.drawable.overcast;
		}
		return R.drawable.run;
	}

	public boolean isHooter() {
		return hooter;
	}

	public void setHooter(boolean hooter) {
		this.hooter = hooter;
	}

	public boolean isCooler() {
		return cooler;
	}

	public void setCooler(boolean cooler) {
		this.cooler = cooler;
	}

	public boolean isMyLocation() {
		return myLocation;
	}

	public void setMyLocation(boolean myLocation) {
		this.myLocation = myLocation;
	}
}
