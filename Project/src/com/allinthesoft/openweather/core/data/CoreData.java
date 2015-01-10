package com.allinthesoft.openweather.core.data;

import java.util.HashMap;

public class CoreData extends AbstractCoreData {
	
	public CoreData() {
		super();
	}
	
	public CoreData(HashMap<String, Object> data) {
		super(data);
	}
	
	public CoreData(ICoreData coreData) {
		super(coreData);
	}

}
