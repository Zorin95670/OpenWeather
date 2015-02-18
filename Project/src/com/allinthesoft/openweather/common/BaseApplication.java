package com.allinthesoft.openweather.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;

import com.allinthesoft.openweather.core.exception.AndroidException;
import com.allinthesoft.openweather.core.weather.CityData;
import com.allinthesoft.openweather.core.weather.Data;

public class BaseApplication extends Application {

	private Data data;

	@Override
	public void onCreate() {
		super.onCreate();
		//deleteFile("data.json");
		initData();
		init();
	}
	
	private void initData(){
		if(data == null){
			data = new Data();
		}
	}
	
	private void init() {
		try {
			FileInputStream fis = openFileInput("data.json");
			InputStreamReader isr = new InputStreamReader(fis);
			char[] buf = new char[1024];
			StringBuilder sb = new StringBuilder();
			while(isr.read(buf) >= 0 ) {
			  sb.append(buf);
			}
			init(sb.toString());
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void init(String json) {
		initData();
		if (json != null && json.length() != 0) {
			try {
				JSONObject root = new JSONObject(json);
				String tmp = root.getString("fahrenheit");
				data.setFahrenheit("yes".equals(tmp));
				JSONArray array = root.getJSONArray("cities");
				CityData cityData;
				for (int i = 0; i < array.length(); i++) {
					cityData = new CityData();
					cityData.setId(array.getString(i));
					data.add(cityData);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (AndroidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	public void maj(){
		initData();
		String texte = data.toJSON();
		try {
			FileOutputStream fOut = openFileOutput("data.json", MODE_WORLD_WRITEABLE);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			osw.write(texte);
			osw.flush();
            osw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
}
