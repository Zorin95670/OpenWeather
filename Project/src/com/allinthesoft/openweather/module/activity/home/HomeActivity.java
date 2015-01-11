package com.allinthesoft.openweather.module.activity.home;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.allinthesoft.openweather.R;
import com.allinthesoft.openweather.common.MyActivity;
import com.allinthesoft.openweather.core.exception.AndroidException;
import com.allinthesoft.openweather.core.weather.Data;
import com.allinthesoft.openweather.core.weather.adapter.DataAdapteur;
import com.allinthesoft.openweather.core.weather.adapter.PlacesAutoCompleteAdapter;
import com.allinthesoft.openweather.module.activity.information.SimpleWeatherInformation;
import com.allinthesoft.openweather.module.listener.DeleteItemOnListView;
import com.allinthesoft.openweather.module.listener.OnLocalizeListener;
import com.allinthesoft.openweather.module.listener.OnSwipeListener;
import com.allinthesoft.openweather.service.openweather.task.JSONWeatherTask;

public class HomeActivity extends MyActivity {

	private DataAdapteur adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		ListView list = (ListView) findViewById(R.id.list_city);
		adapter = new DataAdapteur(this, R.id.list_city, getBaseApplication());
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent myIntent = new Intent(HomeActivity.this,
						SimpleWeatherInformation.class);
				myIntent.putExtra("Data", position); // Optional parameters
				HomeActivity.this.startActivity(myIntent);
				overridePendingTransition(R.anim.main_out, R.anim.other_in);
			}
		});
		list.setOnItemLongClickListener(new DeleteItemOnListView(adapter, this));
		((AutoCompleteTextView) findViewById(R.id.city_search))
				.setOnEditorActionListener(new OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == 0) {
							JSONWeatherTask task = new JSONWeatherTask();
							task.execute(new String[]{v.getText() + ""});
							try {
								getBaseApplication().add(task.get());
								if(getBaseApplication().isDataChange()){
									adapter.notifyDataSetChanged();
								}
								v.setText("");
								v.clearFocus();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ExecutionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (AndroidException e) {
								Toast.makeText(getBaseContext(), getString(e.getMessageId()), Toast.LENGTH_LONG).show();
							}
						}
						return false;
					}
				});
		initAutoComplete();
		findViewById(R.id.ah_localisation).setOnClickListener(new OnLocalizeListener(getApplicationContext()) {
			
			@Override
			public void onLocalize(String location) {
				JSONWeatherTask task = new JSONWeatherTask();
				task.execute(new String[]{location});
				try {
					Data data = task.get();
					getBaseApplication().addCurent(data);
					if(getBaseApplication().isDataChange()){
						adapter.notifyDataSetChanged();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (AndroidException e) {
					Toast.makeText(getBaseContext(), getString(e.getMessageId()), Toast.LENGTH_LONG).show();
				}
			}
			
			@Override
			public void onException(AndroidException e) {
				// TODO Auto-generated method stub
				
			}
		});
		RelativeLayout view = (RelativeLayout) findViewById(R.id.aswi_content);
		view.setOnTouchListener(new OnSwipeListener() {
			
			@Override
			public void swipe() {
				if (getAction() == Action.SWIPE_RIGHT_TO_LEFT && getBaseApplication().getListData().size() != 0) {
					
					Intent myIntent = new Intent(HomeActivity.this,
							SimpleWeatherInformation.class);
					myIntent.putExtra("Data", 0);
					HomeActivity.this.startActivity(myIntent);
					overridePendingTransition(R.anim.main_out, R.anim.other_in);
				}
			}
		});
		
		initCities();
	}

	private void initCities() {
		List<Data> cities = getBaseApplication().getListData();
		if(cities != null){
			int len = cities.size();
			for(int i = 0 ; i < len ; i++){
				JSONWeatherTask task = new JSONWeatherTask();
				task.execute(new String[]{cities.get(i).getName()});
				try {
					Data data = task.get();
					cities.set(i, data);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(len > 0){
				adapter.notifyDataSetChanged();
			}
		} else {
			// TODO problem
		}
	}

	private void initAutoComplete() {

		AutoCompleteTextView autoCompView = (AutoCompleteTextView) findViewById(R.id.city_search);
	    autoCompView.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.list_item));
	}
	
	@Override
	protected void onStop() {
		getBaseApplication().maj();
		super.onStop();
	}
}
