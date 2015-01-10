package com.allinthesoft.openweather.core.data;

import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;

public interface ICoreData {

	void addData(ICoreData coreData);
	void addData(HashMap<String, Object> data);
	
	HashMap<String, Object> data();
	
	Object get(String key);
	Character getChar(String key);
	boolean getBoolean(String key);
	boolean getBoolean(String key, boolean defaultBoolean);
	java.sql.Date getSQLDate(String key);
	Date getDate(String key);
	Date getDate(String key, Date defaultDate);
	String getString(String key);
	String getString(String key, String defaultString);
	
	void set(String key, Object value);
	
	JSONObject convert();
}
