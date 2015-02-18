package com.allinthesoft.openweather.module.listener;

import android.content.Intent;

import com.allinthesoft.openweather.R;
import com.allinthesoft.openweather.common.DataActivity;

public class ChangeActivityOnSwipeListener extends OnSwipeListener {
	private DataActivity activity;
	private Class<?> clazz;
	
	public ChangeActivityOnSwipeListener(DataActivity activity, Class<?> launch) {
		this.activity = activity;
		this.clazz = launch;
	}

	@Override
	public void swipe() {
		Intent myIntent = new Intent(activity,clazz);
		activity.getBaseApplication().getData().setPosition(0);
		activity.startActivity(myIntent);
		activity.overridePendingTransition(R.anim.main_out, R.anim.other_in);
	}

}
