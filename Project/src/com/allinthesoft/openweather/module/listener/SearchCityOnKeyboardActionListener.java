package com.allinthesoft.openweather.module.listener;


import java.util.concurrent.ExecutionException;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AutoCompleteTextView;

import com.allinthesoft.openweather.core.exception.AndroidException;
import com.allinthesoft.openweather.core.weather.Data;
import com.allinthesoft.openweather.core.weather.adapter.DataAdapteur;
import com.allinthesoft.openweather.service.openweather.task.JSONWeatherTask;

public class SearchCityOnKeyboardActionListener implements OnKeyListener {

	private AutoCompleteTextView text;
	private DataAdapteur adapter;
	private Data data;
	public SearchCityOnKeyboardActionListener(AutoCompleteTextView text, DataAdapteur adapter, Data data) {
		this.text = text;
		this.adapter = adapter;
		this.data = data;
	}
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_ENTER) {
			JSONWeatherTask task = new JSONWeatherTask(null, null);
			task.execute(new String[]{text.getText() + ""});
			try {
				data.add(task.get());
				if(data.isDataChanged()){
					adapter.notifyDataSetChanged();
				}
				text.setText("");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AndroidException e) {
				e.printStackTrace();
			}
		}
		return false;
	}



}
