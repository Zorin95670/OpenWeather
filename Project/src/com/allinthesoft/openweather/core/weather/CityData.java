package com.allinthesoft.openweather.core.weather;

import java.text.DecimalFormat;

import com.allinthesoft.openweather.R;

public class CityData {
	private Location location;
	private boolean myLocation;
	private String name, country;
	
	private String id;
	
	private String sunrise, sunset;
	
	private WeatherData mainWeather;

	private boolean hooter, cooler;
	
	private WeatherData[] dailyWeather;
	
	public CityData() {
		setHooter(false);
		setCooler(false);
		setMyLocation(false);
		mainWeather = new WeatherData();
		dailyWeather = new WeatherData[7];
		for(int i = 0 ; i < 7 ; i++){
			dailyWeather[i] = new WeatherData();
		}
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
		if(sunrise != null)
			return sunrise;
		return "X";
	}

	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	public String getSunset() {
		if(sunset != null)
		return sunset;
		return sunset;
	}

	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	public WeatherData getMainWeather() {
		return mainWeather;
	}

	public void setMainWeather(WeatherData mainWeather) {
		this.mainWeather = mainWeather;
	}

	public String getWind() {
		String speed = "X";
		String deg = "X";
		if(getMainWeather().getWindSpeed() != null)
			speed = getMainWeather().getWindSpeed();
		if(getMainWeather().getWindDeg() != null)
			deg = getMainWeather().getWindDeg();
		return speed + "m/s\nDeg : " + deg +"°";
	}

	public String getTemperature(boolean fahrenheit) {
		DecimalFormat d = new DecimalFormat("##,#");
		double t = getMainWeather().getTemp();
		double tmi = getMainWeather().getTempMin();
		double tma = getMainWeather().getTempMax();
		if(!fahrenheit){
			return d.format((t - 273.15)) + "°C\nMin : " + d.format((tmi - 273.15)) + "°C\nMax : " + d.format((tmi - 273.15)) + "°C";
		}
		t = (t - 273.15)* 1.8000 + 32.00;
		tmi = (tmi - 273.15)* 1.8000 + 32.00;
		tma = (tma - 273.15)* 1.8000 + 32.00;
		return d.format(t) + "°F\nMin : " + d.format(tmi) + "°F\nMax : " + d.format(tmi) + "°F";
	}
	
	public String getShortTemperature(boolean fahrenheit){
		DecimalFormat d = new DecimalFormat("##,#");
		double t = getMainWeather().getTemp();
		if(!fahrenheit){
			return d.format((t - 273.15)) + "°C";
		}
		t = (t - 273.15)* 1.8000 + 32.00;
		return d.format(t) + "°F";
	}
	
	public int getPictureById(int id){
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
		} else if(id == -1){
			return R.drawable.network_error;
		}
		return R.drawable.run;
	}
	
	public int getPicture(){
		int id = getMainWeather().getId();
		
		return getPictureById(id);
	}
	
	public int getPicture(int view){
		int id = dailyWeather[view].getId();
		
		return getPictureById(id);
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

	public String getCountry() {
		if(country != null)
		return country;
		return "X";
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override
	public String toString(){
		return getName() + "," + getCountry();
	}

	public String getHumidity() {
		// TODO Auto-generated method stub
		return new DecimalFormat("##").format(mainWeather.getHumidity()) + " %";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDailyWeather(WeatherData[] dailyWeather) {
		this.dailyWeather = dailyWeather;
	}
	
	public CharSequence getDate(int index){
		return dailyWeather[index].getDate();
	}

	public CharSequence getTempEve(int index, boolean fahrenheit) {
		DecimalFormat d = new DecimalFormat("##,#");
		double t = dailyWeather[index].getTemp();
		if(!fahrenheit){
			return d.format((t - 273.15)) + "°C";
		}
		t = (t - 273.15)* 1.8000 + 32.00;
		return d.format(t) + "°F";
	}

	public CharSequence getTempMor(int index, boolean fahrenheit) {
		DecimalFormat d = new DecimalFormat("##,#");
		double t = dailyWeather[index].getTempMin();
		if(!fahrenheit){
			return d.format((t - 273.15)) + "°C";
		}
		t = (t - 273.15)* 1.8000 + 32.00;
		return d.format(t) + "°F";
	}

	public CharSequence getTempNight(int index, boolean fahrenheit) {
		DecimalFormat d = new DecimalFormat("##,#");
		double t = dailyWeather[index].getTempMax();
		if(!fahrenheit){
			return d.format((t - 273.15)) + "°C";
		}
		t = (t - 273.15)* 1.8000 + 32.00;
		return d.format(t) + "°F";
	}

	public void setCity(CityData cityData) {
		location = cityData.getLocation();
		myLocation = cityData.isMyLocation();
		name = cityData.getName();
		country = cityData.getCountry();
		id = cityData.getId();
		sunrise = cityData.getSunrise();
		sunset = cityData.getSunset();
		mainWeather = cityData.getMainWeather();
		hooter = cityData.isHooter();
		cooler = cityData.isCooler();
		dailyWeather = cityData.getDailyWeather();
	}
	public WeatherData[] getDailyWeather(){
		return dailyWeather;
	}
}
