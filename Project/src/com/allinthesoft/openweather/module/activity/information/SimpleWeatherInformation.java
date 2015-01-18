package com.allinthesoft.openweather.module.activity.information;

import java.util.List;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_weather_information);
		
		type = new Type();
		findViewById(R.id.aswi_home).setOnClickListener(new BackToHome(this));
		initView();
		initData();
		RelativeLayout view = (RelativeLayout) findViewById(R.id.aswi_content);
		view.setOnTouchListener(new OnSwipeListener() {
			
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

	public void initView() {
		city = (TextView) findViewById(R.id.aswi_city);
		cityLeft = (TextView) findViewById(R.id.aswi_city_left);
		cityRight = (TextView) findViewById(R.id.aswi_city_right);
		data = (TextView) findViewById(R.id.aswi_data);
		information = (TextView) findViewById(R.id.aswi_information);
		main = (ImageView) findViewById(R.id.aswi_main);

		cityLeft.setOnClickListener(new CityLeftOnClickListener(this));
		cityRight.setOnClickListener(new CityRightOnCLickOnListener(this));
	}

	public void initData() {
		int index = getBaseApplication().getData().getPosition();
		List<CityData> cities = getBaseApplication().getData().getCities();
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
		if (type.getPosition() == 0) {
			information.setText(type.getTitle());
			main.setImageResource(cities.get(index).getPicture());
			data.setText(cities.get(index).getTemperature(getBaseApplication().getData().isFahrenheit()));
		} else if (type.getPosition() == 1) {
			information.setText(type.getTitle());
			main.setImageResource(R.drawable.wind);
			data.setText(cities.get(index).getWind());
		} else if(type.getPosition() == 2){
			information.setText(type.getTitle());
			main.setImageResource(R.drawable.humidity);
			data.setText(cities.get(index).getHumidity());
		}
	}

	/*
	public void increaseTypeData() {
		if (type < 2) {
			type++;
		}
	}

	public void decreaseTypeData() {
		if (type > 0) {
			type--;
		}
	}*/
	/*
	private float startX, startY, min = 100;
	private Action action;

	public enum Action {
		NO_SWIPE, SWIPE_UP_TO_DOWN, SWIPE_DOWN_TO_UP, SWIPE_RIGHT_TO_LEFT, SWIPE_LEFT_TO_RIGHT
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			startX = event.getX();
			startY = event.getY();
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			action = Action.NO_SWIPE;
			float x = startX - event.getX();
			float y = startY - event.getY();

			if (x < -min && -x > min) {
				action = Action.SWIPE_LEFT_TO_RIGHT;
			} else if (x > min) {
				action = Action.SWIPE_RIGHT_TO_LEFT;
			}

			if (y < -min && action == Action.NO_SWIPE) {
				action = Action.SWIPE_UP_TO_DOWN;
			} else if (y < -min && -y > x) {
				action = Action.SWIPE_UP_TO_DOWN;
			} else if (y > min && action == Action.NO_SWIPE) {
				action = Action.SWIPE_DOWN_TO_UP;
			} else if (y > min && y > x) {
				action = Action.SWIPE_DOWN_TO_UP;
			}
			if (action != Action.NO_SWIPE) {
				swipe();
			}
		}
		return false;
	}*/
}
