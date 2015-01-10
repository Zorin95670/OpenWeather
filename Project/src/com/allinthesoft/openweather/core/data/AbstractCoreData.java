package com.allinthesoft.openweather.core.data;

import java.util.Date;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class AbstractCoreData implements ICoreData {

	protected HashMap<String, Object> data;

	public AbstractCoreData() {
		this(new HashMap<String, Object>());
	}

	public AbstractCoreData(HashMap<String, Object> data) {
		this.data = data;
	}

	public AbstractCoreData(ICoreData coreData) {
		this.data = coreData.data();
	}

	@Override
	public void addData(ICoreData coreData) {
		addData(coreData.data());
	}

	@Override
	public void addData(HashMap<String, Object> data) {
		for (String key : data.keySet()) {
			this.set(key, data.get(key));
		}
	}

	@Override
	public HashMap<String, Object> data() {
		return data;
	}

	@Override
	public Object get(String key) {
		if (data.containsKey(key)) {
			return data.get(key);
		}
		return null;
	}

	@Override
	public Character getChar(String key) {
		String s = getString(key);
		if (s != null && s.length() == 1) {
			return s.charAt(0);
		}
		return null;
	}

	@Override
	public boolean getBoolean(String key) {
		if (data.containsKey(key)) {
			return (Boolean) data.get(key);
		}
		return false;
	}

	@Override
	public boolean getBoolean(String key, boolean defaultBoolean) {
		Boolean value = getBoolean(key);
		if (value != null) {
			return value;
		}
		return defaultBoolean;
	}

	@Override
	public java.sql.Date getSQLDate(String key) {
		if (data.containsKey(key)) {
			Date date = getDate(key);
			if (date != null) {
				return new java.sql.Date(date.getTime());
			}
		}
		return null;
	}

	@Override
	public Date getDate(String key) {
		if (data.containsKey(key)) {
			return (Date) data.get(key);
		}
		return null;
	}

	@Override
	public Date getDate(String key, Date defaultDate) {
		Date value = getDate(key);
		if (value != null) {
			return value;
		}
		return defaultDate;
	}

	@Override
	public String getString(String key) {
		Object o = data.get(key);
		if (o != null) {
			return o.toString();
		}
		return null;
	}

	@Override
	public String getString(String key, String defaultString) {
		String value = getString(key);
		if (value != null) {
			return value;
		}
		return defaultString;
	}

	@Override
	public void set(String key, Object value) {
		data.put(key, value);
	}

	@Override
	public JSONObject convert() {
		JSONObject object = null;
		try {
			object = new JSONObject();
			for (String key : data.keySet()) {
				object.put(key, getString(key));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String key : data.keySet()) {
			sb.append(key);
			sb.append(" = ");
			sb.append(get(key));
			sb.append("\n");
		}
		return sb.toString();
	}

}
