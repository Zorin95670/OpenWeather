package com.allinthesoft.openweather.core.exception;

import java.io.IOException;

public class AndroidException extends IOException {

	static final long serialVersionUID = 0L;

	private int messageId;
	
	public AndroidException(int messageId) {
		setMessage(messageId);
	}
	
	public int getMessageId() {
		return messageId;
	}

	public void setMessage(int messageId) {
		this.messageId = messageId;
	}
}
