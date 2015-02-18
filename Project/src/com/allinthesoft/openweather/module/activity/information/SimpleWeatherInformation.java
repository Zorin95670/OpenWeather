package com.allinthesoft.openweather.module.activity.information;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allinthesoft.openweather.R;
import com.allinthesoft.openweather.common.DataActivity;
import com.allinthesoft.openweather.core.weather.CityData;
import com.allinthesoft.openweather.core.weather.Type;
import com.allinthesoft.openweather.module.listener.BackToHome;
import com.allinthesoft.openweather.module.listener.CityLeftOnClickListener;
import com.allinthesoft.openweather.module.listener.CityRightOnCLickOnListener;
import com.allinthesoft.openweather.module.listener.OnSwipeListener;

public class SimpleWeatherInformation extends DataActivity {

	private Type type;

	private TextView information, data, city, cityLeft, cityRight;
	private ImageView main;
	
	private boolean change;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_weather_information);
		change = false;
		type = new Type();
		initView();
		initData();
		initOnswipListener(findViewById(R.id.aswi_content));
	}

	public void initView() {
		city = (TextView) findViewById(R.id.aswi_city);
		cityLeft = (TextView) findViewById(R.id.aswi_city_left);
		cityRight = (TextView) findViewById(R.id.aswi_city_right);
		data = (TextView) findViewById(R.id.aswi_data);
		information = (TextView) findViewById(R.id.aswi_information);
		main = (ImageView) findViewById(R.id.aswi_main);

		findViewById(R.id.aswi_home).setOnClickListener(new BackToHome(this));
		cityLeft.setOnClickListener(new CityLeftOnClickListener(this));
		cityRight.setOnClickListener(new CityRightOnCLickOnListener(this));
	}

	public void initData() {
		int index = getBaseApplication().getData().getPosition();
		List<CityData> cities = getBaseApplication().getData().getCities();
		if (type.getPosition() == 0) {
			main.setImageResource(cities.get(index).getPicture());
			data.setText(cities.get(index).getTemperature(getBaseApplication().getData().isFahrenheit()));
		} else if (type.getPosition() == 1) {
			main.setImageResource(R.drawable.wind);
			data.setText(cities.get(index).getWind());
		} else if(type.getPosition() == 2){
			if(change){
				setContentView(R.layout.activity_simple_weather_information);
				change = false;
				initOnswipListener(findViewById(R.id.aswi_content));
				initView();
			}
			main.setImageResource(R.drawable.humidity);
			data.setText(cities.get(index).getHumidity());
		} else if(type.getPosition() == 3){
			change = true;
			setContentView(R.layout.layout_daily_weather);
			initOnswipListener(findViewById(R.id.aswi_content));
			LinearLayout l = (LinearLayout)findViewById(R.id.aswi_daily);
			initView();			
			for(int j = 1 ; j < l.getChildCount() ; j++){
				int i = j -1;
				((ImageView)l.getChildAt(j).findViewById(R.id.part_main)).setImageResource(cities.get(index).getPicture(i));
				((TextView)l.getChildAt(j).findViewById(R.id.part_date)).setText(cities.get(index).getDate(i));
				((TextView)l.getChildAt(j).findViewById(R.id.part_temp_eve)).setText(cities.get(index).getTempEve(i, getBaseApplication().getData().isFahrenheit()));
				((TextView)l.getChildAt(j).findViewById(R.id.part_temp_mor)).setText(cities.get(index).getTempMor(i, getBaseApplication().getData().isFahrenheit()));
				((TextView)l.getChildAt(j).findViewById(R.id.part_temp_night)).setText(cities.get(index).getTempNight(i, getBaseApplication().getData().isFahrenheit()));
			}
			
		}
		if (index > 0) {
			cityLeft.setVisibility(TextView.VISIBLE);
			cityLeft.setText(cities.get(index - 1).getName());
		} else {
			cityLeft.setVisibility(TextView.INVISIBLE);
		}
		if (index < cities.size() - 1) {
			cityRight.setVisibility(TextView.VISIBLE);
			cityRight.setText(cities.get(index + 1).getName());
		} else {
			cityRight.setVisibility(TextView.INVISIBLE);
		}
		city.setText(cities.get(index).getName());
		information.setText(type.getTitle());
	}

	private void initOnswipListener(View v){
		v.setOnTouchListener(new OnSwipeListener() {
			
			@Override
			public void swipe() {
				boolean back = false;
				if (getAction() == Action.SWIPE_DOWN_TO_UP) {
					type.increase();
				} else if (getAction() == Action.SWIPE_UP_TO_DOWN) {
					type.decrease();
				} else if (getAction() == Action.SWIPE_RIGHT_TO_LEFT) {
					getBaseApplication().getData().increase();
				} else if (getAction() == Action.SWIPE_LEFT_TO_RIGHT) {
					if(getBaseApplication().getData().getPosition() == 0)
						back = true;
					getBaseApplication().getData().decrease();
				}
				if(back){
					
					SimpleWeatherInformation.this.finish();
					SimpleWeatherInformation.this.overridePendingTransition(R.anim.other_out,R.anim.main_in);
				} else {
					initData();
				}
			}
		});
	}
	
}
