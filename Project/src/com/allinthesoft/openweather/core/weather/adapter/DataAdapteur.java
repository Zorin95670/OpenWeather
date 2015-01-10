package com.allinthesoft.openweather.core.weather.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.allinthesoft.openweather.R;
import com.allinthesoft.openweather.core.weather.Data;
import com.allinthesoft.openweather.core.weather.DataHolder;

public class DataAdapteur extends ArrayAdapter<Data> {

	private LayoutInflater layoutInflater;

	public DataAdapteur(Context context, int textViewResourceId, List<Data> list) {
		super(context, textViewResourceId, list);
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DataHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.data_row, null);
			holder = new DataHolder();

			// initialisation des vues
			holder.mainView = (ImageView) convertView
					.findViewById(R.id.data_main);
			holder.tempView = (TextView) convertView
					.findViewById(R.id.data_temp);
			holder.cityView = (TextView) convertView
					.findViewById(R.id.data_city);
			holder.tokenView = (ImageView) convertView
					.findViewById(R.id.data_token);
			holder.locateView = (ImageView) convertView
					.findViewById(R.id.data_locate);
			holder.position = position;
			convertView.setTag(holder);
		} else {
			holder = (DataHolder) convertView.getTag();
		}
		holder.mainView.setImageResource(getItem(position).getPicture());
		holder.mainView.setId(position);
		holder.tempView.setText(getItem(position).getMainWeather().getTemp());
		holder.cityView.setText(getItem(position).getName());
		if (getItem(position).isCooler()) {
			holder.tokenView.setImageResource(R.drawable.cold);
		} else if (getItem(position).isHooter()) {
			holder.tokenView.setImageResource(R.drawable.hot);
		}
		if(getItem(position).isMyLocation()){
			holder.locateView.setImageResource(R.drawable.localisation);
		}
		return convertView;
	}

	public void remove(int position) {
		remove(getItem(position));
	}

}
