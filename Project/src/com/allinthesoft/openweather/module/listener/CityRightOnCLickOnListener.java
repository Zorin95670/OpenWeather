package com.allinthesoft.openweather.module.listener;

import android.view.View;
import android.view.View.OnClickListener;

import com.allinthesoft.openweather.core.weather.Data;
import com.allinthesoft.openweather.module.activity.information.SimpleWeatherInformation;

public class CityRightOnCLickOnListener implements OnClickListener {

	private SimpleWeatherInformation activity;
	private Data data;
	
	public CityRightOnCLickOnListener(SimpleWeatherInformation activity) {
		this.activity = activity;
		data = activity.getBaseApplication().getData();
	}
	
	@Override
	public void onClick(View v) {
		if (data.increase()){
			activity.initData();	
		}
	}
}
