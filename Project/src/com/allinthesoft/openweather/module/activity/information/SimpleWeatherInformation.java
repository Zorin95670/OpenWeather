package com.allinthesoft.openweather.module.activity.information;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allinthesoft.openweather.R;
import com.allinthesoft.openweather.common.MyActivity;
import com.allinthesoft.openweather.core.weather.Data;
import com.allinthesoft.openweather.module.activity.home.HomeActivity;
import com.allinthesoft.openweather.module.listener.BackToHome;

public class SimpleWeatherInformation extends MyActivity implements
		OnTouchListener {

	private int index = 0;
	private int type = 0;

	private TextView information, data, city, cityLeft, cityRight;
	private ImageView main;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_weather_information);
		findViewById(R.id.aswi_home).setOnClickListener(new BackToHome(this));
		initView();
		index = getIntent().getIntExtra("Data", 0);
		initData();
		RelativeLayout view = (RelativeLayout) findViewById(R.id.aswi_content);
		view.setOnTouchListener(this);

	}

	public void initView() {
		city = (TextView) findViewById(R.id.aswi_city);
		cityLeft = (TextView) findViewById(R.id.aswi_city_left);
		cityRight = (TextView) findViewById(R.id.aswi_city_right);
		data = (TextView) findViewById(R.id.aswi_data);
		information = (TextView) findViewById(R.id.aswi_information);
		main = (ImageView) findViewById(R.id.aswi_main);

		cityLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (index > 0) {
					decreaseData();
					initData();
				}
			}
		});
		cityRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (index < getBaseApplication().getListData().size() - 1) {
					increaseData();
					initData();
				}
			}
		});
	}

	public void initData() {
		if (index > 0) {
			cityLeft.setVisibility(TextView.VISIBLE);
			cityLeft.setText(getData(index - 1).getName());
		} else {
			cityLeft.setVisibility(TextView.INVISIBLE);
		}
		if (index < getBaseApplication().getListData().size() - 1) {
			cityRight.setVisibility(TextView.VISIBLE);
			cityRight.setText(getData(index + 1).getName());
		} else {
			cityRight.setVisibility(TextView.INVISIBLE);
		}
		city.setText(getData().getName());
		if (type == 0) {
			information.setText(getBaseApplication().getType(type));
			main.setImageResource(getData().getPicture());
			data.setText(getData().getTemperature(getBaseApplication().isFahrenheit()));
		} else if (type == 1) {
			information.setText(getBaseApplication().getType(type));
			main.setImageResource(R.drawable.wind);
			data.setText(getData().getWind());
		}
	}

	public Data getData() {
		return getData(index);
	}

	public Data getData(int index) {
		return getBaseApplication().getListData().get(index);

	}

	public void swipe() {
		boolean back = false;
		if (action == Action.SWIPE_DOWN_TO_UP) {
			increaseTypeData();
		} else if (action == Action.SWIPE_UP_TO_DOWN) {
			decreaseTypeData();
		} else if (action == Action.SWIPE_RIGHT_TO_LEFT) {
			increaseData();
		} else if (action == Action.SWIPE_LEFT_TO_RIGHT) {
			if(index == 0)
				back = true;
			decreaseData();
		}
		if(back){
			
			this.finish();
			SimpleWeatherInformation.this.overridePendingTransition(R.anim.other_out,R.anim.main_in);
		} else {
			initData();
		}
	}

	public void increaseTypeData() {
		if (type < 1) {
			type++;
		}
	}

	public void decreaseTypeData() {
		if (type > 0) {
			type--;
		}
	}

	private void increaseData() {
		if (index < getBaseApplication().getListData().size() - 1) {
			index++;
		}
	}

	private void decreaseData() {
		if (index > 0) {
			index--;
		}
	}

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
	}
}
