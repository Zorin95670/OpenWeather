package com.allinthesoft.openweather.core.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.allinthesoft.openweather.R;
import com.allinthesoft.openweather.core.weather.CityData;
import com.allinthesoft.openweather.core.weather.Data;
import com.allinthesoft.openweather.core.weather.DataHolder;

public class DataAdapteur extends ArrayAdapter<CityData> {

	private LayoutInflater layoutInflater;

	private Data data;

	public DataAdapteur(Context context, int textViewResourceId, Data data) {
		super(context, textViewResourceId, data.getCities());
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DataHolder holder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.data_row, null);
		} else{
			holder = (DataHolder)convertView.getTag();
		}
		
		if(holder == null){
			holder = initDataHolder(convertView, position);
		}
		
		convertView.setTag(holder);

		holder.mainView.setImageResource(getItem(position).getPicture());
		holder.tempView.setText(getItem(position).getShortTemperature(
				data.isFahrenheit()));
		holder.cityView.setText(getItem(position).getName());
		if (getItem(position).isCooler()) {
			holder.tokenView.setImageResource(R.drawable.cold);
		} else if (getItem(position).isHooter()) {
			holder.tokenView.setImageResource(R.drawable.hot);
		}
		if (getItem(position).isMyLocation()) {
			holder.locateView.setImageResource(R.drawable.localisation);
		}
		return convertView;
	}

	public void remove(int position) {
		remove(getItem(position));
	}

	public DataHolder initDataHolder(View v, int position) {
		DataHolder holder = new DataHolder();
		holder.mainView = (ImageView) v.findViewById(R.id.data_main);
		holder.tempView = (TextView) v.findViewById(R.id.data_temp);
		holder.cityView = (TextView) v.findViewById(R.id.data_city);
		//holder.tokenView = (ImageView) v.findViewById(R.id.data_token);
		holder.locateView = (ImageView) v.findViewById(R.id.data_locate);
		holder.position = position;
		
		return holder;
	}

}
