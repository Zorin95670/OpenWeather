package com.allinthesoft.openweather.module.listener;


import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AutoCompleteTextView;

import com.allinthesoft.openweather.core.weather.CityData;
import com.allinthesoft.openweather.module.activity.home.HomeActivity;
import com.allinthesoft.openweather.service.openweather.task.JSONWeatherTask;
import com.allinthesoft.openweather.tools.list.core.array.ArrayExtendedList;

public class SearchCityOnKeyboardActionListener implements OnKeyListener {

	private AutoCompleteTextView text;
	private HomeActivity activity;
	private ArrayExtendedList<CityData> cities;
	
	public SearchCityOnKeyboardActionListener(AutoCompleteTextView text, HomeActivity activity, ArrayExtendedList<CityData> cities) {
		this.text = text;
		this.activity = activity;
		this.cities = cities;
	}
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_ENTER) {
			CityData city = new CityData();
			String s =  text.getText() + "";
			if(s.contains(", ")){
				String[] split = s.split(", ");
				city.setId(s);
				city.setName(split[0]);
				if(split.length > 0)
					city.setCountry(split[1]);
				
			} else {
				city.setId(s);
				city.setName(s);
			}
			
			cities.add(city);
			JSONWeatherTask task = new JSONWeatherTask(cities, activity);
			task.execute(new String[0]);
			text.setText("");
		}
		return false;
	}



}
