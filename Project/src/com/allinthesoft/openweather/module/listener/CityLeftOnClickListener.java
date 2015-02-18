package com.allinthesoft.openweather.module.listener;

import android.view.View;
import android.view.View.OnClickListener;

import com.allinthesoft.openweather.core.weather.Data;
import com.allinthesoft.openweather.module.activity.information.SimpleWeatherInformation;

public class CityLeftOnClickListener implements OnClickListener {

	private SimpleWeatherInformation activity;
	private Data data;
	
	public CityLeftOnClickListener(SimpleWeatherInformation activity) {
		this.activity = activity;
		data = activity.getBaseApplication().getData();
	}
	
	@Override
	public void onClick(View v) {
		if (data.decrease()){
			activity.initData();	
		}
	}

}
