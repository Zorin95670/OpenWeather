package com.allinthesoft.openweather.module.listener;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class BackToHome implements OnClickListener {

	private Activity activity;
	
	public BackToHome(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		activity.finish();
	}

}
