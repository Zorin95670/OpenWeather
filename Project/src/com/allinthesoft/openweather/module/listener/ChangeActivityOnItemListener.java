package com.allinthesoft.openweather.module.listener;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.allinthesoft.openweather.R;
import com.allinthesoft.openweather.common.DataActivity;

public class ChangeActivityOnItemListener implements OnItemClickListener {

	private DataActivity activity;
	private Class<?> clazz;
	
	public ChangeActivityOnItemListener(DataActivity activity, Class<?> launch) {
		this.activity = activity;
		this.clazz = launch;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent myIntent = new Intent(activity,clazz);
		activity.getBaseApplication().getData().setPosition(position);
		activity.startActivity(myIntent);
		activity.overridePendingTransition(R.anim.main_out, R.anim.other_in);
	}
}
