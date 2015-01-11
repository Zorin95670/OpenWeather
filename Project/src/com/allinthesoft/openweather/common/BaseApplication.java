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

import com.allinthesoft.openweather.R;
import com.allinthesoft.openweather.core.exception.AndroidException;
import com.allinthesoft.openweather.core.weather.Data;
import com.allinthesoft.openweather.tools.list.core.array.ArrayExtendedList;

public class BaseApplication extends Application {

	private ArrayExtendedList<Data> list;

	private boolean dataChange;
	private boolean fahrenheit;

	@Override
	public void onCreate() {
		super.onCreate();
		setList(new ArrayExtendedList<Data>());
		setDataChange(false);
		init();
	}

	public boolean isFahrenheit(){
		return fahrenheit;
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
	
	private void init(String json) throws AndroidException {
		if (json != null && json.length() != 0) {
			try {
				JSONObject root = new JSONObject(json);
				String tmp = root.getString("fahrenheit");
				fahrenheit = "yes".equals(tmp);
				JSONArray array = root.getJSONArray("cities");
				Data data;
				for (int i = 0; i < array.length(); i++) {
					data = new Data();
					data.setName(array.getString(i));
					list.add(data);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				throw new AndroidException(R.string.error_malformed_conf);
			}
		}
	}

	public ArrayExtendedList<Data> getListData() {
		return list;
	}

	public void setList(ArrayExtendedList<Data> list) {
		this.list = list;
	}

	public int getType(int indexTypeData) {
		if (indexTypeData == 0) {
			return R.string.type_temp;
		} else if (indexTypeData == 1) {
			return R.string.type_wind;
		} else if(indexTypeData == 2){
			return R.string.type_humidity;
		}
		return R.string.type_info;
	}

	public void add(Data data) throws AndroidException {
		if (data != null) {
			boolean check = true;
			for (int index = 0; index < list.size(); index++) {
				if (data.getName().equals(list.get(index).getName())) {
					check = false;
					break;
				}
			}
			if (check) {
				list.add(data);
			}
		}
	}

	public void addCurent(Data data) throws AndroidException {
		if (data != null) {
			boolean check = true;
			for (int index = 0; index < list.size(); index++) {
				if (data.getName().equals(list.get(index).getName())) {
					check = false;
					if (!list.get(index).isMyLocation()) {
						list.get(index).setMyLocation(true);
						setDataChange(true);
					}
					break;
				}
			}
			if (check) {
				data.setMyLocation(true);
				list.add(data);
				setDataChange(true);
			}
		}
	}

	public boolean isDataChange() {
		Boolean tmp = dataChange;
		setDataChange(false);
		return tmp;
	}

	public void setDataChange(boolean dataChange) {
		this.dataChange = dataChange;
	}
	
	public void maj(){
		String texte = toJSON();
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

	private String toJSON() {
		JSONObject root = new JSONObject();
		try {
			if (fahrenheit) {
				root.put("fahrenheit", "yes");
			} else {
				root.put("fahrenheit", "no");
			}
			JSONArray array = new JSONArray();
			if (list.size() != 0) {
				for (int i = 0 ; i < list.size() ; i++) {
					array.put(list.get(i).toString());
				}
				root.put("cities", array);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return root.toString();
	}
}
