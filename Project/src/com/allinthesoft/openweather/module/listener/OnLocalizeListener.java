package com.allinthesoft.openweather.module.listener;

import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.allinthesoft.openweather.R;
import com.allinthesoft.openweather.core.exception.AndroidException;
import com.allinthesoft.openweather.core.weather.Location;
import com.allinthesoft.openweather.service.openweather.gps.GPSTracker;
import com.allinthesoft.openweather.service.openweather.task.JSONCityTask;

public abstract class OnLocalizeListener implements OnClickListener {
	
	private Context context;
	
	public OnLocalizeListener(Context context) {
		setContext(context);
	}
	
	@Override
	public void onClick(View v) {
		GPSTracker gpsTracker = new GPSTracker(
				getContext());

		if (gpsTracker.canGetLocation()) {
			Location location = new Location();
			location.setLatitude(gpsTracker.latitude);
			location.setLongitude(gpsTracker.longitude);
			JSONCityTask task = new JSONCityTask(); 
			task.execute(new String[] { location.toString() });
			String city = null;
			try {
				city = task.get();
				onLocalize(city);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			gpsTracker.showSettingsAlert();
			onException(new AndroidException(R.string.error_get_location));
		} 
	}

	public abstract void onLocalize(String location);
	
	public abstract void onException(AndroidException e);
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
}
