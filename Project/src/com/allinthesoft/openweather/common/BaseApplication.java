package com.allinthesoft.openweather.common;

import java.io.File;
import java.io.IOException;

import android.app.Application;

import com.allinthesoft.openweather.R;
import com.allinthesoft.openweather.core.weather.Data;
import com.allinthesoft.openweather.tools.list.core.array.ArrayExtendedList;

public class BaseApplication extends Application {

	private ArrayExtendedList<Data> list;

	private boolean dataChange;

	@Override
	public void onCreate() {
		super.onCreate();
		try {
			File f = new File(getFilesDir(), "test.toto");
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setList(new ArrayExtendedList<Data>());
		setDataChange(false);
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
		}
		return R.string.type_info;
	}

	public void add(Data data) {
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

	public void addCurent(Data data) {
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
}
