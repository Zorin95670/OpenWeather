package com.allinthesoft.openweather.module.activity.home;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.allinthesoft.openweather.R;
import com.allinthesoft.openweather.common.DataActivity;
import com.allinthesoft.openweather.core.weather.CityData;
import com.allinthesoft.openweather.core.weather.Location;
import com.allinthesoft.openweather.core.weather.adapter.DataAdapteur;
import com.allinthesoft.openweather.core.weather.adapter.PlacesAutoCompleteAdapter;
import com.allinthesoft.openweather.module.activity.information.SimpleWeatherInformation;
import com.allinthesoft.openweather.module.listener.ChangeActivityOnItemListener;
import com.allinthesoft.openweather.module.listener.DeleteItemOnListView;
import com.allinthesoft.openweather.module.listener.OnSwipeListener;
import com.allinthesoft.openweather.module.listener.SearchCityOnKeyboardActionListener;
import com.allinthesoft.openweather.service.openweather.gps.GPSTracker;
import com.allinthesoft.openweather.service.openweather.task.JSONCityTask;
import com.allinthesoft.openweather.service.openweather.task.JSONWeatherTask;

public class HomeActivity extends DataActivity implements OnClickListener {

	private DataAdapteur adapter;
	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		ListView list = (ListView) findViewById(R.id.ah_list_city);
		adapter = new DataAdapteur(this, R.id.ah_list_city,
				getBaseApplication().getData());
		list.setAdapter(adapter);

		list.setOnItemClickListener(new ChangeActivityOnItemListener(this,
				SimpleWeatherInformation.class));
		list.setOnItemLongClickListener(new DeleteItemOnListView(adapter, this));

		AutoCompleteTextView text = ((AutoCompleteTextView) findViewById(R.id.ah_city_search));
		text.setOnKeyListener(new SearchCityOnKeyboardActionListener(text,
				this, getBaseApplication().getData().getCities()));
		initAutoComplete();
		findViewById(R.id.ah_localisation).setOnClickListener(this);
		RelativeLayout view = (RelativeLayout) findViewById(R.id.ah_content);
		view.setOnTouchListener(new OnSwipeListener() {

			@Override
			public void swipe() {
				if (getAction() == Action.SWIPE_RIGHT_TO_LEFT
						&& getBaseApplication().getData().getCities().size() != 0) {

					Intent myIntent = new Intent(HomeActivity.this,
							SimpleWeatherInformation.class);
					myIntent.putExtra("Data", 0);
					HomeActivity.this.startActivity(myIntent);
					overridePendingTransition(R.anim.main_out, R.anim.other_in);
				}
			}
		});
		findViewById(R.id.ah_refresh).setOnClickListener(this);
		findViewById(R.id.ah_settings).setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		dialog = new Dialog(HomeActivity.this);
		dialog.setContentView(R.layout.dialog_loading);
		dialog.setTitle(R.string.default_wait);
		dialog.show();
		initCities();
	}

	public void refreshData() {
		dialog = new Dialog(HomeActivity.this);
		dialog.setContentView(R.layout.dialog_loading);
		dialog.setTitle(R.string.default_wait);
		dialog.show();
		initCities();

	}

	private void initCities() {
		List<CityData> cities = getBaseApplication().getData().getCities();
		JSONWeatherTask task = new JSONWeatherTask(cities, this);
		task.execute(new String[0]);
	}

	private void initAutoComplete() {

		AutoCompleteTextView autoCompView = (AutoCompleteTextView) findViewById(R.id.ah_city_search);
		autoCompView.setAdapter(new PlacesAutoCompleteAdapter(this,
				R.layout.list_item));
	}

	@Override
	protected void onStop() {
		getBaseApplication().maj();
		super.onStop();
	}

	public void changeTemperature(boolean b) {
		getBaseApplication().getData().setFahrenheit(b);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.ah_settings) {
			dialog = new Dialog(HomeActivity.this);
			dialog.setContentView(R.layout.dialog_settings);
			dialog.setTitle(R.string.settings);
			if (!getBaseApplication().getData().isFahrenheit()) {
				((RadioButton) dialog.findViewById(R.id.do_celcius))
						.setChecked(true);
				((RadioButton) dialog.findViewById(R.id.do_fahrenheit))
						.setChecked(false);
			}
			((RadioGroup) dialog.findViewById(R.id.do_group))
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(RadioGroup group,
								int checkedId) {
							if (checkedId == R.id.do_celcius) {
								changeTemperature(false);
							} else {
								changeTemperature(true);
							}
						}
					});
			((Button) dialog.findViewById(R.id.do_close))
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
			dialog.show();
		} else if (v.getId() == R.id.ah_refresh) {
			refreshData();
		} else if (v.getId() == R.id.ah_localisation) {
			dialog = new Dialog(HomeActivity.this);
			dialog.setContentView(R.layout.dialog_loading);
			dialog.setTitle(R.string.default_wait);
			dialog.show();
			GPSTracker gpsTracker = new GPSTracker(HomeActivity.this);

			if (gpsTracker.canGetLocation()) {
				Location location = new Location();
				location.setLatitude(gpsTracker.latitude);
				location.setLongitude(gpsTracker.longitude);
				JSONCityTask task = new JSONCityTask();
				task.execute(new String[] { location.toString() });
				String city = null;
				try {
					city = task.get();
					if (city != null) {
						List<CityData> list = getBaseApplication().getData()
								.getCities();
						for (int i = 0; i < list.size(); i++) {
							list.get(i).setMyLocation(false);
							if (list.get(i).getId().equals(city)) {
								list.get(i).setId(city);
								list.get(i).setMyLocation(true);
							}
						}
						JSONWeatherTask weatherTask = new JSONWeatherTask(list,
								this);
						weatherTask.execute(new String[0]);
					} else {
						dialog.dismiss();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void refreshEnd(boolean state, List<CityData> cities) {
		if (dialog != null) {
			dialog.dismiss();
		}
		if (state) {
			for (int i = 0; i < cities.size(); i++) {
				adapter.getItem(i).setCity(cities.get(i));
			}
		}
	}

	public void notidyAdapter() {
		adapter.notifyDataSetChanged();
	}
}
