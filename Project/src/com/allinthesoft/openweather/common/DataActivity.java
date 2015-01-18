package com.allinthesoft.openweather.common;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.allinthesoft.openweather.R;

public class DataActivity extends Activity {
	
	public BaseApplication getBaseApplication(){
		return (BaseApplication) getApplication();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActionBar().hide();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.simple_weather_information, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void refreshData(){
		Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_loading);
		dialog.setTitle(R.string.default_wait);
		dialog.show();
		// TODO initCities();
		dialog.dismiss();
	}
	
}
