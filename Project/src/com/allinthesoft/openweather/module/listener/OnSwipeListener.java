package com.allinthesoft.openweather.module.listener;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public abstract class OnSwipeListener implements OnTouchListener {
	private float startX, startY, min = 100;
	private Action action;

	public enum Action {
		NO_SWIPE, SWIPE_UP_TO_DOWN, SWIPE_DOWN_TO_UP, SWIPE_RIGHT_TO_LEFT, SWIPE_LEFT_TO_RIGHT
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			startX = event.getX();
			startY = event.getY();
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			action = Action.NO_SWIPE;
			float x = startX - event.getX();
			float y = startY - event.getY();

			if (x < -min && -x > min) {
				action = Action.SWIPE_LEFT_TO_RIGHT;
			} else if (x > min) {
				action = Action.SWIPE_RIGHT_TO_LEFT;
			}

			if (y < -min && action == Action.NO_SWIPE) {
				action = Action.SWIPE_UP_TO_DOWN;
			} else if (y < -min && -y > x) {
				action = Action.SWIPE_UP_TO_DOWN;
			} else if (y > min && action == Action.NO_SWIPE) {
				action = Action.SWIPE_DOWN_TO_UP;
			} else if (y > min && y > x) {
				action = Action.SWIPE_DOWN_TO_UP;
			}
			if (action != Action.NO_SWIPE) {
				swipe();
			}
		}
		return false;
	}
	
	public Action getAction(){
		return action;
	}
	
	public abstract void swipe();
}
