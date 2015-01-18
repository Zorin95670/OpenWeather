package com.allinthesoft.openweather.module.listener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

import com.allinthesoft.openweather.R;
import com.allinthesoft.openweather.common.DataActivity;
import com.allinthesoft.openweather.core.weather.adapter.DataAdapteur;

public class DeleteItemOnListView implements OnItemLongClickListener,OnClickListener {

	private DataAdapteur adaptateur;
	private DataActivity activity;
	private int position;
	
	public DeleteItemOnListView(DataAdapteur adaptateur, DataActivity activity) {
		this.adaptateur = adaptateur;
		this.activity = activity;
	}
	

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		this.position = position;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
		alertDialogBuilder.setTitle(activity.getString(R.string.dt_delete_item) + " : " + getCityName());
		alertDialogBuilder.setMessage(activity.getString(R.string.dm_delete_item) + "(" + getCityName() + ")");
		alertDialogBuilder.setPositiveButton(R.string.yes, this);
		alertDialogBuilder.setNegativeButton(R.string.no,
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						dialog.cancel();
					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		return false;
	}


	@Override
	public void onClick(DialogInterface dialog, int which) {
		
		Toast.makeText(activity.getApplicationContext(), "City delete (" + getCityName() + ")", 
				   Toast.LENGTH_SHORT).show();
		adaptateur.remove(position);
		dialog.cancel();
	}

	private String getCityName(){
		return activity.getBaseApplication().getData().getCities().get(position).getName();
	}
}
