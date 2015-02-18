package com.allinthesoft.openweather.core.weather;


import com.allinthesoft.openweather.R;

public class Type {

	private int position;

	public Type() {
		// TODO Auto-generated constructor stub
	}
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getTitle() {
		if (getPosition() == 0) {
			return R.string.type_temp;
		} else if (getPosition() == 1) {
			return R.string.type_wind;
		} else if(getPosition() == 2){
			return R.string.type_humidity;
		} else if(getPosition() == 3){
			return R.string.type_daily;
		}
		return R.string.type_info;
	}
	
	public void increase() {
		if (position < 3) {
			position++;
		}
	}

	public void decrease() {
		if (position > 0) {
			position--;
		}
	}
}
