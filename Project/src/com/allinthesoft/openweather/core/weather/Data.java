package com.allinthesoft.openweather.core.weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.allinthesoft.openweather.core.exception.AndroidException;
import com.allinthesoft.openweather.tools.list.core.array.ArrayExtendedList;

public class Data {

	private ArrayExtendedList<CityData> cities;
	private int position;
	private boolean fahrenheit, dataChanged;

	public Data() {
		cities = new ArrayExtendedList<CityData>();
	}
	
	public ArrayExtendedList<CityData> getCities(){
		return cities;
	}
	
	public boolean isFahrenheit() {
		return fahrenheit;
	}

	public void setFahrenheit(boolean fahrenheit) {
		this.fahrenheit = fahrenheit;
	}

	public boolean isDataChanged() {
		return dataChanged;
	}

	public void setDataChanged(boolean dataChanged) {
		this.dataChanged = dataChanged;
	}
	
	public String toJSON() {
		JSONObject root = new JSONObject();
		try {
			if (fahrenheit) {
				root.put("fahrenheit", "yes");
			} else {
				root.put("fahrenheit", "no");
			}
			JSONArray array = new JSONArray();
			if (cities.size() != 0) {
				for (int i = 0 ; i < cities.size() ; i++) {
					array.put(cities.get(i).getId());
				}
				root.put("cities", array);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return root.toString();
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public boolean increase() {
		if (position < cities.size() - 1) {
			position++;
			return true;
		}
		return false;
	}

	public boolean decrease() {
		if (position > 0) {
			position--;
			return true;
		}
		return true;
	}
	
	public void add(CityData data) throws AndroidException {
		if (data != null) {
			boolean check = true;
			for (int index = 0; index < cities.size(); index++) {
				if (data.getName() != null && data.getName().equals(cities.get(index).getName())) {
					check = false;
					break;
				}
			}
			if (check) {
				data.setMyLocation(false);
				cities.add(data);
			}
			setDataChanged(check);
		}
	}

	public void addCurent(CityData data) throws AndroidException {
		if (data != null) {
			boolean check = true;
			for (int index = 0; index < cities.size(); index++) {
				if (data.getName().equals(cities.get(index).getName())) {
					check = false;
					cities.get(index).setMyLocation(true);
					setDataChanged(true);
				} else {
					cities.get(index).setMyLocation(false);
				}
			}
			
			if (check) {
				data.setMyLocation(true);
				cities.add(data);
			}
			setDataChanged(check);
		}
	}
}
